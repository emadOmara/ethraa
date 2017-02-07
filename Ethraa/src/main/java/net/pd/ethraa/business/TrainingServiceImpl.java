package net.pd.ethraa.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.pd.ethraa.common.CommonUtil;
import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.NullAwareBeanUtilsBean;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.Group;
import net.pd.ethraa.common.model.Point;
import net.pd.ethraa.common.model.Training;
import net.pd.ethraa.common.model.TrainingAttendence;
import net.pd.ethraa.common.model.TrainingDay;
import net.pd.ethraa.dao.AccountDao;
import net.pd.ethraa.dao.TrainingDao;
import net.pd.ethraa.integration.request.AttendenceRequest;
import net.pd.ethraa.integration.request.EvaluationPoint;
import net.pd.ethraa.integration.request.UserPointsRequest;

@Service
@Transactional
public class TrainingServiceImpl implements TrainingService {

	@Autowired
	private TrainingDao trainingDao;
	@Autowired
	private AccountDao accountDao;

	@Autowired
	private NullAwareBeanUtilsBean beanUtilService;

	@Override
	public Training saveTraining(Training training) throws EthraaException {
		try {
			if (training.isNew()) {
				return trainingDao.save(training);
			} else {// update
				if (!training.getTrainingDays().isEmpty()) {
					for (TrainingDay td : training.getTrainingDays()) {
						trainingDao.removeTrainingDays(td.getDay().getId());
					}
				}
				Training fetchedTraining = trainingDao.findOne(training.getId());
				beanUtilService.copyProperties(fetchedTraining, training);
				return trainingDao.save(fetchedTraining);
			}
		} catch (Exception e) {
			throw new EthraaException(e);
		}
	}

	@Override
	public List<Training> getAllTrainings(Long type) throws EthraaException {
		try {
			List<Training> trainings = null;
			if (CommonUtil.isEmpty(type)) {
				trainings = trainingDao.findAll();
			} else {
				trainings = trainingDao.findByType(type);
			}
			return trainings;
		} catch (Exception e) {
			throw new EthraaException(e);
		}
	}

	@Override
	public List<Training> getAssignedTrainings(Long groupId, Long type) throws EthraaException {
		try {
			Group g = new Group();
			g.setId(groupId);
			List<Training> books = trainingDao.findByGroup(g, type);
			return books;
		} catch (Exception e) {
			throw new EthraaException(e);
		}
	}

	@Override
	public List<Account> getMeetingMembers(Long trainingId, Long type) throws EthraaException {
		try {
			List<Account> members = trainingDao.getMeetingMembers(trainingId);
			Long totalDays = countTotalMeetingDays(trainingId);
			for (Account account : members) {
				Long trainingAttendence = getUserAttendenceCount(account.getId());
				boolean isAttendedToday = isAttendedToday(account.getId());
				account.setTotalTrainingDays(totalDays);
				account.setTrainingAttendence(trainingAttendence);
				account.setAttendedTrainingToday(isAttendedToday);
				Point evaluationPoint = accountDao.findEvaluationPoint(trainingId, type, account.getId());
				if (evaluationPoint != null) {
					account.setTrainingPoints(evaluationPoint.getPoints());
				}
			}
			return members;
		} catch (Exception e) {
			throw new EthraaException(e);
		}
	}

	private boolean isAttendedToday(Long userId) {
		Long attendence = trainingDao.isAttendedOnDay(userId, Calendar.getInstance().getTime());
		return attendence > 0;
	}

	private Long getUserAttendenceCount(Long userId) {
		Long attendence = trainingDao.countUserTrainingAttendence(userId);
		return attendence;
	}

	private Long countTotalMeetingDays(Long trainingId) throws EthraaException {

		Training training = trainingDao.findOne(trainingId);

		if (training == null) {
			throw new EthraaException("No training defined for the specified Id" + trainingId);
		}
		Date startDate = training.getStartDate();
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);

		Date endDate = training.getEndDate();
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);

		List<TrainingDay> trainingDays = training.getTrainingDays();
		List<Integer> days = new ArrayList<>();
		for (TrainingDay trainingDay : trainingDays) {
			days.add(trainingDay.getId().intValue());
		}
		Long counter = 0l;
		while (startCalendar.before(endCalendar)) {
			int weekDay = startCalendar.get(Calendar.DAY_OF_WEEK);
			if (days.contains(weekDay)) {
				counter++;
			}
			startCalendar.add(Calendar.DAY_OF_MONTH, 1);
		}

		return counter;

	}

	@Override
	public void addAttendence(AttendenceRequest request) throws EthraaException {
		try {

			TrainingDay trainingDay = trainingDao.getTrainingDay(request.getDayId(), request.getTrainingId());

			Account account = new Account();
			account.setId(request.getUserId());

			TrainingAttendence attendence = new TrainingAttendence();
			attendence.setAccount(account);
			attendence.setTrainingDay(trainingDay);

			trainingDay.getAttendence().add(attendence);
		} catch (Exception e) {
			throw new EthraaException(e);
		}
	}

	@Override
	public Training getTraining(Long trainingId) throws EthraaException {
		try {
			return trainingDao.findOne(trainingId);
		} catch (Exception e) {
			throw new EthraaException(e);
		}
	}

	@Override
	public void addTrainingBonous(UserPointsRequest request) throws EthraaException {
		try {
			List<EvaluationPoint> evaluations = request.getEvaluations();
			for (EvaluationPoint evaluation : evaluations) {
				Long userId = evaluation.getUserId();

				Point point = accountDao.findEvaluationPoint(request.getTrainingId(), request.getType(), userId);

				if (point != null) {
					point.setPoints(evaluation.getPoint());
				} else {
					Account account = accountDao.findOne(userId);
					point = createPoint(userId, evaluation, request);

					account.getPoints().add(point);
					accountDao.save(account);

				}

			}
		} catch (Exception e) {
			throw new EthraaException(e);
		}

	}

	private Point createPoint(Long accountId, EvaluationPoint evaluation, UserPointsRequest request) {
		Point p = new Point();

		Account account = new Account();
		account.setId(accountId);

		p.setAccount(account);
		p.setPoints(evaluation.getPoint());
		p.setEntityId(request.getTrainingId());
		p.setType(request.getType());
		return p;
	}

}
