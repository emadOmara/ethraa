package net.pd.ethraa.integration;

import java.util.List;

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

import net.pd.ethraa.business.ExamService;
import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.Exam;
import net.pd.ethraa.common.model.UserExam;
import net.pd.ethraa.integration.jackson.Views;
import net.pd.ethraa.integration.response.BaseResponse;

@RestController()
@RequestMapping(path = "api/exam")
public class ExamController extends BaseController {

	Logger logger = LoggerFactory.getLogger(ExamController.class);

	@Autowired
	private ExamService examService;

	@JsonView(Views.ExamPublic.class)
	@GetMapping(path = "/list/{type}")
	public BaseResponse listAllExams(@PathVariable("type") Long type) throws EthraaException {
		BaseResponse response = new BaseResponse();
		List<Exam> allExams = examService.getAllExams(type);
		handleSuccessResponse(response, allExams);
		return response;
	}

	@JsonView(Views.ExamPublic.class)
	@GetMapping(path = "/list/{groupId}/{type}")
	public BaseResponse listAssignedExams(@PathVariable("groupId") Long groupId, @PathVariable("type") Long type)
			throws EthraaException {

		handleNullID(groupId);
		handleNullID(type);

		BaseResponse response = new BaseResponse();
		List<Exam> assignedExams = examService.getAssignedExams(groupId, type);
		handleSuccessResponse(response, assignedExams);

		return response;

	}

	@GetMapping(path = "/list/user/{userId}/{type}")
	@JsonView(Views.ExamPublic.class)
	public BaseResponse listUserExams(@PathVariable("userId") Long userId, @PathVariable("type") Long type)
			throws EthraaException {

		handleNullID(userId);

		BaseResponse response = new BaseResponse();
		List<Exam> exams = examService.listUserExams(userId, type);
		handleSuccessResponse(response, exams);

		return response;

	}

	@JsonView(Views.ExamPublic.class)
	@GetMapping(path = "/get/{examId}")
	public BaseResponse getExam(@PathVariable("examId") Long examId) throws EthraaException {

		handleNullID(examId);

		BaseResponse response = new BaseResponse();
		Exam exam = examService.getExam(examId);
		handleSuccessResponse(response, exam);

		return response;
	}

	@GetMapping(path = "/user/answer/{userId}/{examId}")
	public BaseResponse getUserAnswer(@PathVariable("userId") Long userId, @PathVariable("examId") Long examId)
			throws EthraaException {

		handleNullID(userId);
		handleNullID(examId);

		BaseResponse response = new BaseResponse();
		UserExam userExam = examService.getUserExam(userId, examId);
		handleSuccessResponse(response, userExam);

		return response;

	}

	@PostMapping(path = "/save")
	@JsonView(Views.Public.class)
	public BaseResponse addExam(@RequestBody Exam exam) throws EthraaException {

		BaseResponse response = new BaseResponse();

		exam = examService.saveExam(exam);
		handleSuccessResponse(response, exam);

		return response;

	}

	@PostMapping(path = "/answer")
	public BaseResponse answerExam(@RequestBody UserExam userExam) throws EthraaException {

		BaseResponse response = new BaseResponse();

		examService.answerExam(userExam);
		handleSuccessResponse(response, null);

		return response;

	}

	@PostMapping(path = "/evaluate")
	public BaseResponse evaluateExam(@RequestBody UserExam userExam) throws EthraaException {

		BaseResponse response = new BaseResponse();

		examService.evaluateExam(userExam);
		handleSuccessResponse(response, null);

		return response;

	}

	@DeleteMapping(path = "/delete/{examId}")
	public BaseResponse deleteExam(@PathVariable("examId") Long examId) throws EthraaException {

		handleNullID(examId);

		BaseResponse response = new BaseResponse();
		examService.deleteExam(examId);
		handleSuccessResponse(response, null);

		return response;

	}

	@GetMapping(path = "/list/members/{examId}")
	@JsonView(Views.ExamPublic.class)
	public BaseResponse listExamMembers(@PathVariable("examId") Long examId) throws EthraaException {

		handleNullID(examId);

		BaseResponse response = new BaseResponse();
		List<Account> accounts = examService.getExamMembers(examId);
		handleSuccessResponse(response, accounts);

		return response;

	}

}
