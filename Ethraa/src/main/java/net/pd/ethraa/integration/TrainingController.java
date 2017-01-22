package net.pd.ethraa.integration;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import net.pd.ethraa.business.TrainingService;
import net.pd.ethraa.common.EthraaConstants;
import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.Training;
import net.pd.ethraa.integration.jackson.Views;
import net.pd.ethraa.integration.response.BaseResponse;

@RestController()
@RequestMapping(path = "api/training")
public class TrainingController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(TrainingController.class);

    @Autowired
    private TrainingService trainingService;

    @PostMapping(path = "/add")
    public BaseResponse addTraining(@RequestBody Training training) throws EthraaException {

	BaseResponse response = new BaseResponse();

	training = trainingService.saveTraining(training);
	handleSuccessResponse(response, training);

	return response;

    }

    @PostMapping(path = "/edit")
    public BaseResponse editTraining(@RequestBody Training training) throws EthraaException {

	BaseResponse response = new BaseResponse();
	if (training.isNew()) {
	    throw new EthraaException(EthraaConstants.ERROR_MSG_ID_CAN_T_BE_NULL);
	}

	training = trainingService.saveTraining(training);
	handleSuccessResponse(response, training);

	return response;

    }

    @GetMapping(path = "/list")
    public BaseResponse listAllTrainings() throws EthraaException {

	BaseResponse response = new BaseResponse();
	List<Training> allTrainings = trainingService.getAllTrainings();
	handleSuccessResponse(response, allTrainings);

	return response;

    }

    @GetMapping(path = "/list/{groupId}")
    public BaseResponse listAssignedBooks(@PathVariable("groupId") Long groupId) throws EthraaException {

	handleNullID(groupId);

	BaseResponse response = new BaseResponse();
	List<Training> trainings = trainingService.getAssignedTrainings(groupId);
	handleSuccessResponse(response, trainings);

	return response;

    }

    @GetMapping(path = "/list/members/{trainingId}")
    @JsonView(Views.Public.class)
    public BaseResponse listMeetingMembers(@PathVariable("trainingId") Long trainingId) throws EthraaException {

	handleNullID(trainingId);

	BaseResponse response = new BaseResponse();
	List<Account> accounts = trainingService.getMeetingMembers(trainingId);
	handleSuccessResponse(response, accounts);

	return response;

    }

}
