package net.pd.ethraa.business;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.pd.ethraa.common.CommonUtil;
import net.pd.ethraa.common.EthraaConstants;
import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.Exam;
import net.pd.ethraa.common.model.Group;
import net.pd.ethraa.common.model.Question;
import net.pd.ethraa.common.model.UserExam;
import net.pd.ethraa.common.model.UserExamKey;
import net.pd.ethraa.dao.AccountDao;
import net.pd.ethraa.dao.ExamDao;
import net.pd.ethraa.dao.UserExamDao;

@Service
@Transactional
public class ExamServiceImpl implements ExamService {

	@Autowired
	private ExamDao examDao;
	@Autowired
	private UserExamDao userExamDao;

	@Autowired
	private AccountDao accountDao;

	@Override
	public Exam saveExam(Exam exam) throws EthraaException {
		try {
			if (exam.isNew()) {
				deleteExam(exam.getId());
			}
			return examDao.save(exam);
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
			if (CommonUtil.isEmpty(account))
				throw new EthraaException("No account defined for the id " + userId);
			Group g = account.getGroup();

			List<Exam> assignedExams = examDao.findByGroup(g);
			if (CommonUtil.isEmpty(assignedExams)) {
				return Collections.emptyList();
			}
			for (Exam exam : assignedExams) {
				Long examId = exam.getId();
				Long fullMark = getExamFullMark(examId);
				exam.setExamFullMark(fullMark);
				Long userScore = getUserScore(userId, examId);
				exam.setExamScore(userScore);
			}

			return assignedExams;
		} catch (Exception e) {
			throw new EthraaException(e);
		}
	}

	@Override
	public void deleteExam(Long examId) throws EthraaException {
		try {

			deleteUserExams(examId);
			deleteQuestions(examId);
			examDao.delete(examId);

		} catch (Exception e) {
			throw new EthraaException(e);
		}

	}

	private void deleteQuestions(Long examId) {
		examDao.deleteAnswers(examId);
		examDao.deleteQuestions(examId);
	}

	private void deleteUserExams(Long examId) {
		examDao.deleteSolutions(examId);
		examDao.deleteUserExams(examId);

	}

	@Override
	public List<Account> getExamMembers(Long examId) throws EthraaException {
		try {
			List<Account> members = examDao.getExamMembers(examId);
			if (CommonUtil.isEmpty(members)) {
				return Collections.emptyList();
			}
			Long examFullMark = getExamFullMark(examId);
			for (Account account : members) {
				Long userScore = getUserScore(account.getId(), examId);

				account.setExamFullMark(examFullMark);
				account.setExamScore(userScore);
			}
			return members;
		} catch (Exception e) {
			throw new EthraaException(e);
		}
	}

	private Long getUserScore(Long accountId, Long examId) {

		UserExamKey key = new UserExamKey();
		Account acc = new Account();
		acc.setId(accountId);
		Exam exam = new Exam();
		exam.setId(examId);
		key.setAccount(acc);
		key.setExam(exam);
		return examDao.getUserScore(key);
	}

	private Long getExamFullMark(Long examId) {
		Exam exam = examDao.findOne(examId);
		long fullMark = 0l;
		Set<Question> questions = exam.getQuestions();
		for (Question question : questions) {
			fullMark += question.getScore();
		}
		return fullMark;
	}

	@Override
	public void answerExam(UserExam userExam) throws EthraaException {
		try {
			UserExam fetchedUserExam = userExamDao.findOne(userExam.getId());
			if (CommonUtil.isEmpty(fetchedUserExam))
				throw new EthraaException("no user exam found for specified id");

			if (fetchedUserExam.getStatus() == EthraaConstants.EXAM_STATUS_ANSWERED)
				throw new EthraaException("exam already answered");
			userExam.setStatus(EthraaConstants.EXAM_STATUS_ANSWERED);
			userExamDao.save(userExam);

		} catch (Exception e) {
			throw new EthraaException(e);
		}
	}

	@Override
	public void evaluateExam(UserExam userExam) throws EthraaException {
		try {
			userExam.setStatus(EthraaConstants.EXAM_STATUS_EVALUATED);
			userExamDao.save(userExam);

		} catch (Exception e) {
			throw new EthraaException(e);
		}

	}

	@Override
	public UserExam getUserExam(Long userId, Long examId) throws EthraaException {
		try {
			UserExamKey key = new UserExamKey();
			Account account = new Account();
			account.setId(userId);
			Exam exam = new Exam();
			exam.setId(examId);
			key.setAccount(account);
			key.setExam(exam);

			return userExamDao.findOne(key);

		} catch (Exception e) {
			throw new EthraaException(e);
		}
	}
}
