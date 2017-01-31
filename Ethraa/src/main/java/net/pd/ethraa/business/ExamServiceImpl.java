package net.pd.ethraa.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.NullAwareBeanUtilsBean;
import net.pd.ethraa.common.model.Exam;
import net.pd.ethraa.dao.ExamDao;

@Service
@Transactional
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamDao examDao;

    @Autowired
    private NullAwareBeanUtilsBean beanUtilService;

    @Override
    public Exam saveExam(Exam exam) throws EthraaException {
	try {
	    if (exam.isNew()) {
		return examDao.save(exam);
	    } else {// update
		Exam fetchedExam = examDao.findOne(exam.getId());
		beanUtilService.copyProperties(fetchedExam, exam);
		return examDao.save(exam);
	    }
	} catch (Exception e) {
	    throw new EthraaException(e);
	}

    }

}
