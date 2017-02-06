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

import net.pd.ethraa.business.ExamService;
import net.pd.ethraa.common.EthraaConstants;
import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Exam;
import net.pd.ethraa.integration.jackson.Views;
import net.pd.ethraa.integration.response.BaseResponse;

@RestController()
@RequestMapping(path = "api/exam")
public class ExamController extends BaseController {

	Logger logger = LoggerFactory.getLogger(ExamController.class);

	@Autowired
	private ExamService examService;

	@JsonView(Views.Public.class)
	@GetMapping(path = "/list")
	public BaseResponse listAllExams() throws EthraaException {
		BaseResponse response = new BaseResponse();
		List<Exam> allExams = examService.getAllExams();
		handleSuccessResponse(response, allExams);
		return response;
	}

	@JsonView(Views.Public.class)
	@GetMapping(path = "/list/{groupId}")
	public BaseResponse listAssignedBooks(@PathVariable("groupId") Long groupId) throws EthraaException {

		handleNullID(groupId);

		BaseResponse response = new BaseResponse();
		List<Exam> assignedExams = examService.getAssignedExams(groupId);
		handleSuccessResponse(response, assignedExams);

		return response;

	}

	@GetMapping(path = "/list/user/{userId}")
	public BaseResponse listUserExams(@PathVariable("userId") Long userId) throws EthraaException {

		handleNullID(userId);

		BaseResponse response = new BaseResponse();
		List<Exam> exams = examService.listUserExams(userId);
		handleSuccessResponse(response, exams);

		return response;

	}

	@PostMapping(path = "/add")
	public BaseResponse addExam(@RequestBody Exam exam) throws EthraaException {

		BaseResponse response = new BaseResponse();

		exam = examService.saveExam(exam);
		handleSuccessResponse(response, exam);

		return response;

	}

	@PostMapping(path = "/edit")
	public BaseResponse editBook(@RequestBody Exam exam) throws EthraaException {

		BaseResponse response = new BaseResponse();
		if (exam.isNew()) {
			throw new EthraaException(EthraaConstants.ERROR_MSG_ID_CAN_T_BE_NULL);
		}

		examService.saveExam(exam);
		handleSuccessResponse(response, null);

		return response;

	}

}
