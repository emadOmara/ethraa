package net.pd.ethraa.business;

import java.util.List;

import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.Exam;
import net.pd.ethraa.common.model.UserExam;

public interface ExamService {

	Exam saveExam(Exam exam) throws EthraaException;

	List<Exam> getAllExams() throws EthraaException;

	List<Exam> getAssignedExams(Long groupId) throws EthraaException;

	List<Exam> listUserExams(Long userId) throws EthraaException;

	void deleteExam(Long examId) throws EthraaException;

	List<Account> getExamMembers(Long examId) throws EthraaException;

	void answerExam(UserExam userExam) throws EthraaException;

	void evaluateExam(UserExam userExam) throws EthraaException;

	UserExam getUserExam(Long userId, Long examId) throws EthraaException;

}
