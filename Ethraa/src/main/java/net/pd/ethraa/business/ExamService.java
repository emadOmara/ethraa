package net.pd.ethraa.business;

import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Exam;

public interface ExamService {

    Exam saveExam(Exam exam) throws EthraaException;

}
