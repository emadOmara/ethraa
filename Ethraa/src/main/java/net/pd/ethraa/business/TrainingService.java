package net.pd.ethraa.business;

import java.util.List;

import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.Training;

public interface TrainingService {

    Training saveTraining(Training training) throws EthraaException;

    List<Training> getAllTrainings() throws EthraaException;

    List<Training> getAssignedTrainings(Long groupId) throws EthraaException;

    List<Account> getMeetingMembers(Long trainingId) throws EthraaException;

}
