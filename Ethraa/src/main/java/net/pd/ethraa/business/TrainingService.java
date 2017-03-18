package net.pd.ethraa.business;

import java.util.List;

import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.Training;
import net.pd.ethraa.integration.request.AttendenceRequest;
import net.pd.ethraa.integration.request.UserPointsRequest;

public interface TrainingService {

	Training saveTraining(Training training) throws EthraaException;

	List<Training> getAllTrainings(Long type) throws EthraaException;

	List<Training> getAssignedTrainings(Long groupId, Long type) throws EthraaException;

	List<Account> getMeetingMembers(Long trainingId, Long type) throws EthraaException;

	void addAttendence(AttendenceRequest request) throws EthraaException;

	Training getTraining(Long trainingId) throws EthraaException;

	void addTrainingBonous(UserPointsRequest request) throws EthraaException;

	Long countLastTrainings(Long userId, int period, Long type) throws EthraaException;

	void deleteTraining(Long id) throws EthraaException;

}
