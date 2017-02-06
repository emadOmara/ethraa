package net.pd.ethraa.business;

import java.util.List;

import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Exam;

public interface ExamService {

    Exam saveExam(Exam exam) throws EthraaException;

	List<Exam> getAllExams()throws EthraaException;

	List<Exam> getAssignedExams(Long groupId)throws EthraaException;
 
	List<Exam> listUserExams(Long userId)throws EthraaException;

}
