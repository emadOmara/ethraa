package net.pd.ethraa.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.NullAwareBeanUtilsBean;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.Exam;
import net.pd.ethraa.common.model.Group;
import net.pd.ethraa.dao.AccountDao;
import net.pd.ethraa.dao.ExamDao;

@Service
@Transactional
public class ExamServiceImpl implements ExamService {

	@Autowired
	private ExamDao examDao;

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private NullAwareBeanUtilsBean beanUtilService;

	@Override
	public Exam saveExam(Exam exam) throws EthraaException {
		try {
			if (exam.isNew()) {
				return examDao.save(exam);
			} else {// update
				Exam fetchedExam = examDao.findOne(exam.getId());
				// if (!CommonUtil.isEmpty(exam.getQuestions())) {
				// fetchedExam.getQuestions().clear();
				// for (Question q : exam.getQuestions()) {
				// fetchedExam.getQuestions().add(q);
				// }
				// }
				fetchedExam.getQuestions().clear();
				beanUtilService.copyProperties(fetchedExam, exam);
				return examDao.save(exam);
			}
		} catch (Exception e) {
			throw new EthraaException(e);
		}

	}

	@Override
	public List<Exam> getAllExams() throws EthraaException {
		try {
			List<Exam> exams = examDao.findAll();
			return exams;
		} catch (Exception e) {
			throw new EthraaException(e);
		}
	}

	@Override
	public List<Exam> getAssignedExams(Long groupId) throws EthraaException {
		try {
			Group g = new Group();
			g.setId(groupId);

			List<Exam> exams = examDao.findByGroup(g);
			return exams;
		} catch (Exception e) {
			throw new EthraaException(e);
		}
	}

	@Override
	public List<Exam> listUserExams(Long userId) throws EthraaException {

		try {

			Account account = accountDao.findOne(userId);
			Group g = account.getGroup();

			List<Exam> assignedExams = examDao.findByGroup(g);
			return assignedExams;
		} catch (Exception e) {
			throw new EthraaException(e);
		}
	}

}
