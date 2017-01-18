package net.pd.ethraa.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.NullAwareBeanUtilsBean;
import net.pd.ethraa.common.model.Group;
import net.pd.ethraa.common.model.Training;
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
	    List<Training> books = trainingDao.findByGroup(groupId);
	    return books;
	} catch (Exception e) {
	    throw new EthraaException(e);
	}
    }

}
