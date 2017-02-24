package net.pd.ethraa.business;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.pd.ethraa.common.CommonUtil;
import net.pd.ethraa.common.EthraaConstants;
import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.Answer;
import net.pd.ethraa.common.model.Exam;
import net.pd.ethraa.common.model.Group;
import net.pd.ethraa.common.model.Question;
import net.pd.ethraa.common.model.Solution;
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
			if (!exam.isNew()) {
				deleteExam(exam.getId());
			}
			return examDao.save(exam);
		} catch (Exception e) {
			throw new EthraaException(e);
		}

	}

	@Override
	public List<Exam> getAllExams(Long type) throws EthraaException {
		try {
			List<Exam> exams = examDao.findByType(type);

			addExamSolutionsAndEvaluations(exams);

			return exams;
		} catch (Exception e) {
			throw new EthraaException(e);
		}
	}

	private void addExamSolutionsAndEvaluations(List<Exam> exams) {
		if (CommonUtil.isEmpty(exams)) {
			return;
		}
		for (Exam exam : exams) {
			Long examMembers = examDao.countExamMembers(exam.getId());
			exam.setTotalExamMembers(examMembers);

			Object[] result = examDao.countExamSolutions(exam.getId());
			if (CommonUtil.isEmpty(result)) {
				return;
			}
			Object[] entry = (Object[]) result[0];

			if (CommonUtil.isEmpty(entry[0]) || CommonUtil.isEmpty(entry[1]))
				continue;
			if (EthraaConstants.EXAM_STATUS_ANSWERED.equals(entry[1])) {
				exam.setExamUserSolutions((Long) entry[0]);
				exam.setExamAdminEvaluations(0l);
			} else if (EthraaConstants.EXAM_STATUS_EVALUATED.equals(entry[1])) {
				exam.setExamAdminEvaluations((Long) entry[0]);
				exam.setExamUserSolutions(0l);
			}
			exam.setExamStatus((Long) entry[1]);

		}
	}

	@Override
	public List<Exam> getAssignedExams(Long groupId, Long type) throws EthraaException {
		try {
			Group g = new Group();
			g.setId(groupId);

			List<Exam> exams = examDao.findByGroupAndType(g, type);
			addExamSolutionsAndEvaluations(exams);
			return exams;
		} catch (Exception e) {
			throw new EthraaException(e);
		}
	}

	@Override
	public List<Exam> listUserExams(Long userId, Long type) throws EthraaException {

		try {

			Account account = accountDao.findOne(userId);
			if (CommonUtil.isEmpty(account))
				throw new EthraaException("No account defined for the id " + userId);
			Group g = account.getGroup();

			List<Exam> assignedExams = examDao.findByGroupAndType(g, type);
			if (CommonUtil.isEmpty(assignedExams)) {
				return Collections.emptyList();
			}
			for (Exam exam : assignedExams) {
				Long examId = exam.getId();
				Long fullMark = getExamFullMark(examId);
				exam.setExamFullMark(fullMark);

				Object[] result = getUserScore(account.getId(), examId);
				if (!CommonUtil.isEmpty(result)) {
					Object[] entry = (Object[]) result[0];

					if (entry[1] == null) {
						exam.setExamStatus(EthraaConstants.EXAM_STATUS_Not_ANSWERED.longValue());
					} else {
						Long status = (Long) entry[2];
						exam.setExamStatus(status);
						Long score = (Long) entry[0];
						exam.setExamScore(score);
					}
				}

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
				Object[] result = getUserScore(account.getId(), examId);
				account.setExamFullMark(examFullMark);
				if (!CommonUtil.isEmpty(result)) {
					Object[] entry = (Object[]) result[0];

					if (entry[1] == null) {
						account.setExamStatus(EthraaConstants.EXAM_STATUS_Not_ANSWERED.longValue());
					} else {
						Long status = (Long) entry[2];
						account.setExamStatus(status);
						Long score = (Long) entry[0];
						account.setExamScore(score);
					}
				}
			}
			return members;
		} catch (Exception e) {
			throw new EthraaException(e);
		}
	}

	private Object[] getUserScore(Long accountId, Long examId) {

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
		List<Question> questions = exam.getQuestions();
		for (Question question : questions) {
			fullMark += question.getScore();
		}
		return fullMark;
	}

	@Override
	public void answerExam(UserExam userExam) throws EthraaException {
		try {
			UserExam fetchedUserExam = userExamDao.findOne(userExam.getId());

			if (!CommonUtil.isEmpty(fetchedUserExam)
					&& EthraaConstants.EXAM_STATUS_ANSWERED.equals(fetchedUserExam.getStatus()))
				throw new EthraaException("exam already answered");
			userExam.setStatus(EthraaConstants.EXAM_STATUS_ANSWERED.longValue());
			userExamDao.save(userExam);

		} catch (Exception e) {
			throw new EthraaException(e);
		}
	}

	@Override
	public void evaluateExam(UserExam userExam) throws EthraaException {
		try {
			UserExam fetchedUserExam = userExamDao.findOne(userExam.getId());
			if (CommonUtil.isEmpty(fetchedUserExam)) {
				throw new EthraaException("User Solution not found");
			}
			userExam.setStatus(EthraaConstants.EXAM_STATUS_EVALUATED.longValue());

			if (!CommonUtil.isEmpty(userExam) && !CommonUtil.isEmpty(userExam.getSolutions())) {

				for (Solution solution : fetchedUserExam.getSolutions()) {
					Question question = solution.getQuestion();
					Long type = question.getType().longValue();
					if (!EthraaConstants.EXAM_QUESTION_TYPE_TEXT.equals(type)) {
						if (solution.getAnswer().isCorrect()) {
							solution.setScore(question.getScore().longValue());
						} else {
							solution.setScore(0l);
						}
					} else {
						int index = userExam.getSolutions().indexOf(solution);
						Solution evalSolution = userExam.getSolutions().get(index);
						solution.setScore(evalSolution.getScore());
					}
				}
			}
			userExamDao.save(fetchedUserExam);

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

	@Override
	public Exam getExam(Long examId) throws EthraaException {
		try {

			Exam exam = examDao.findOne(examId);
			if (!CommonUtil.isEmpty(exam)) {
				if (EthraaConstants.POLL_TYPE.equals(exam.getType())) {
					List<Question> questions = exam.getQuestions();
					for (Question question : questions) {
						Object[] result = examDao.countExamQuestionSolutions(examId, question.getId());
						if (!CommonUtil.isEmpty(result)) {
							for (int j = 0; j < result.length; j++) {
								Object[] entry = (Object[]) result[j];
								if (!CommonUtil.isEmpty(entry[0]) && !CommonUtil.isEmpty(entry[1])) {
									Long solutionCount = (Long) entry[0];
									Long answerId = (Long) entry[1];
									List<Answer> answers = question.getAnswers();

									for (Answer answer : answers) {
										if (answer.getId().equals(answerId)) {
											answer.setPollTotalSelection(solutionCount);
										}
									}

								}
							}
						}

					}

				}
			}

			return exam;
		} catch (Exception e) {
			throw new EthraaException(e);
		}
	}

	@Override
	public Long countExams(Long type, Long status) throws EthraaException {
		try {
			return examDao.countExams(type, status);

		} catch (Exception e) {
			throw new EthraaException(e);
		}
	}

	@Override
	public Long countPendingExam(Long userId, Long examType) throws EthraaException {
		try {
			Account account = accountDao.findOne(userId);
			if (CommonUtil.isEmpty(account))
				throw new EthraaException("No account defined for the id " + userId);
			Group g = account.getGroup();

			return examDao.countUserExams(examType, g);

		} catch (Exception e) {
			throw new EthraaException(e);
		}
	}

}
