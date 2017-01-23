package net.pd.ethraa.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.NullAwareBeanUtilsBean;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.Group;
import net.pd.ethraa.common.model.Training;
import net.pd.ethraa.common.model.TrainingDay;
import net.pd.ethraa.dao.TrainingDao;
import net.pd.ethraa.integration.request.AttendenceRequest;

@Service
@Transactional
public class TrainingServiceImpl implements TrainingService {

    @Autowired
    private TrainingDao trainingDao;

    @Autowired
    private NullAwareBeanUtilsBean beanUtilService;

    @Override
    public Training saveTraining(Training training) throws EthraaException {
	try {
	    if (training.isNew()) {
		return trainingDao.save(training);
	    } else {// update
		Training fetchedTraining = trainingDao.findOne(training.getId());
		beanUtilService.copyProperties(fetchedTraining, training);
		return trainingDao.save(fetchedTraining);
	    }
	} catch (Exception e) {
	    throw new EthraaException(e);
	}
    }

    @Override
    public List<Training> getAllTrainings() throws EthraaException {
	try {
	    List<Training> trainings = trainingDao.findAll();
	    return trainings;
	} catch (Exception e) {
	    throw new EthraaException(e);
	}
    }

    @Override
    public List<Training> getAssignedTrainings(Long groupId) throws EthraaException {
	try {
	    Group g = new Group();
	    g.setId(groupId);
	    List<Training> books = trainingDao.findByGroup(g);
	    return books;
	} catch (Exception e) {
	    throw new EthraaException(e);
	}
    }

    @Override
    public List<Account> getMeetingMembers(Long trainingId) throws EthraaException {
	try {
	    List<Account> members = trainingDao.getMeetingMembers(trainingId);
	    Long totalDays = countTotalMeetingDays(trainingId);
	    for (Account account : members) {
		Long trainingAttendence = getUserAttendenceCount(account.getId());
		account.setTotalTrainingDays(totalDays);
		account.setTrainingAttendence(trainingAttendence);
	    }
	    return members;
	} catch (Exception e) {
	    throw new EthraaException(e);
	}
    }

    private Long getUserAttendenceCount(Long userId) {
	Long attendence = trainingDao.countUserTrainingAttendence(userId);
	return attendence;
    }

    private Long countTotalMeetingDays(Long trainingId) throws EthraaException {

	Training training = trainingDao.findOne(trainingId);

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

	    // Training fetchedTraining = trainingDao.findOne(training.getId());
	    // beanUtilService.copyProperties(fetchedTraining, training);
	    // return trainingDao.save(fetchedTraining);

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

}
