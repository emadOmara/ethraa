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
	    countMeetingDays(trainingId);
	    List<Account> members = trainingDao.getMeetingMembers(trainingId);
	    return members;
	} catch (Exception e) {
	    throw new EthraaException(e);
	}
    }

    private int countMeetingDays(Long trainingId) throws EthraaException {
	try {

	    Training training = trainingDao.findOne(trainingId);

	    Date startDate = training.getStartDate();
	    Calendar startCalendar = Calendar.getInstance();
	    startCalendar.setTime(startDate);

	    Date endDate = training.getEndDate();
	    Calendar endCalendar = Calendar.getInstance();
	    endCalendar.setTime(endDate);

	    int endDay = endCalendar.get(Calendar.DAY_OF_WEEK);

	    List<TrainingDay> trainingDays = training.getTrainingDays();
	    List<Integer> days = new ArrayList<>();
	    for (TrainingDay trainingDay : trainingDays) {
		days.add(trainingDay.getId().intValue());
	    }
	    int counter = 0;
	    while (startCalendar.before(endCalendar)) {
		int weekDay = startCalendar.get(Calendar.DAY_OF_WEEK);
		if (days.contains(weekDay)) {
		    counter++;
		}
		startCalendar.add(Calendar.DAY_OF_MONTH, 1);
	    }

	    return counter;
	} catch (Exception e) {
	    throw new EthraaException(e);
	}
    }

}
