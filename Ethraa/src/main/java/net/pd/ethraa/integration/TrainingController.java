package net.pd.ethraa.integration;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import net.pd.ethraa.integration.request.AttendenceRequest;
import net.pd.ethraa.integration.request.UserPointsRequest;
import net.pd.ethraa.integration.response.BaseResponse;

@RestController()
@RequestMapping(path = "api/training")
public class TrainingController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(TrainingController.class);

	@Autowired
	private TrainingService trainingService;

	@PostMapping(path = "/add")
	@JsonView(Views.Public.class)
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

		trainingService.saveTraining(training);
		handleSuccessResponse(response, null);

		return response;

	}

	@DeleteMapping(path = "/delete/{id}")
	public BaseResponse deleteTraining(@PathVariable("id") Long id) throws EthraaException {

		BaseResponse response = new BaseResponse();

		handleNullID(id);

		trainingService.deleteTraining(id);
		handleSuccessResponse(response, null);

		return response;

	}

	@GetMapping(path = "/list/{type}")
	@JsonView(Views.Public.class)
	public BaseResponse listAllTrainings(@PathVariable("type") Long type) throws EthraaException {

		handleNullID(type);
		BaseResponse response = new BaseResponse();
		List<Training> allTrainings = trainingService.getAllTrainings(type);
		handleSuccessResponse(response, allTrainings);

		return response;

	}

	@GetMapping(path = "/list")
	@JsonView(Views.Public.class)
	public BaseResponse listAllTrainings() throws EthraaException {

		BaseResponse response = new BaseResponse();
		List<Training> allTrainings = trainingService.getAllTrainings(null);
		handleSuccessResponse(response, allTrainings);

		return response;

	}

	@GetMapping(path = "/get/{id}")
	@JsonView(Views.Details.class)
	public BaseResponse getTrainingDetails(@PathVariable("id") Long trainingId) throws EthraaException {

		handleNullID(trainingId);

		BaseResponse response = new BaseResponse();
		Training training = trainingService.getTraining(trainingId);
		handleSuccessResponse(response, training);

		return response;

	}

	@GetMapping(path = "/list/{type}/{groupId}")
	@JsonView(Views.Public.class)
	public BaseResponse listAssignedTrainings(@PathVariable("type") Long type, @PathVariable("groupId") Long groupId)
			throws EthraaException {

		handleNullID(groupId, type);

		BaseResponse response = new BaseResponse();
		List<Training> trainings = trainingService.getAssignedTrainings(groupId, type);
		handleSuccessResponse(response, trainings);

		return response;

	}

	@GetMapping(path = "/list/members/{trainingId}/{type}")
	@JsonView(Views.Details.class)
	public BaseResponse listMeetingMembers(@PathVariable("trainingId") Long trainingId, @PathVariable("type") Long type)
			throws EthraaException {

		handleNullID(trainingId);

		BaseResponse response = new BaseResponse();
		List<Account> accounts = trainingService.getMeetingMembers(trainingId, type);
		handleSuccessResponse(response, accounts);

		return response;

	}

	@PostMapping(path = "/attendence/add")
	public BaseResponse addAttendence(@Valid @RequestBody AttendenceRequest request) throws EthraaException {

		BaseResponse response = new BaseResponse();

		trainingService.addAttendence(request);
		handleSuccessResponse(response, null);

		return response;

	}

	@PostMapping(path = "/bonous/add")
	public BaseResponse addTrainingExraPoints(@Valid @RequestBody UserPointsRequest request) throws EthraaException {

		BaseResponse response = new BaseResponse();

		trainingService.addTrainingBonous(request);
		handleSuccessResponse(response, null);

		return response;

	}

}
