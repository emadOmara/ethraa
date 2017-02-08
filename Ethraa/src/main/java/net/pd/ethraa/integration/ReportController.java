package net.pd.ethraa.integration;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.pd.ethraa.business.AccountService;
import net.pd.ethraa.business.BookService;
import net.pd.ethraa.business.MessageService;
import net.pd.ethraa.business.ReportService;
import net.pd.ethraa.business.TrainingService;
import net.pd.ethraa.common.EthraaConstants;
import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.integration.response.BaseResponse;

@RestController()
@RequestMapping(path = "api/reports")
public class ReportController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(ReportController.class);

	@Autowired
	private MessageService messageService;

	@Autowired
	private BookService bookService;

	@Autowired
	private TrainingService trainingService;
	@Autowired
	private AccountService accountService;

	@Autowired
	private ReportService reportService;

	@GetMapping(path = "/summary/{userId}")
	public BaseResponse getSummaryReport(@PathVariable("userId") Long userId) throws EthraaException {

		handleNullID(userId);
		BaseResponse response = new BaseResponse();

		Map<Long, Long> result = reportService.summarizeUserReport(userId);
		handleSuccessResponse(response, result);

		return response;

	}

	@GetMapping(path = "/training/{userId}/{type}")
	public BaseResponse addTrainingExraPoints(@PathVariable("userId") Long userId, @PathVariable("type") Long type)
			throws EthraaException {

		handleNullID(userId);
		BaseResponse response = new BaseResponse();

		Map<String, Long> result = reportService.getTrainingReport(userId, type);
		handleSuccessResponse(response, result);

		return response;

	}

	@GetMapping(path = "/books/{userId}")
	public BaseResponse addTrainingExraPoints(@PathVariable("userId") Long userId) throws EthraaException {

		handleNullID(userId);
		BaseResponse response = new BaseResponse();

		Map<String, Long> result = reportService.getBookReadingReport(userId);
		handleSuccessResponse(response, result);

		return response;

	}

	@GetMapping(path = "/home/admin")
	public BaseResponse adminHome() throws EthraaException {

		BaseResponse response = new BaseResponse();
		Map<String, Long> result = new HashMap<String, Long>();

		Long count = messageService.countNewAdminMessages();
		result.put("NEW_MSG_COUNT", count);

		Long pendingUsersCount = accountService.countPendingUsers();
		result.put("PENDING_USRS_COUNT", pendingUsersCount);

		handleSuccessResponse(response, result);

		return response;

	}

	@GetMapping(path = "/home/user/{userId}")
	public BaseResponse userHome(@PathVariable("userId") Long userId) throws EthraaException {

		handleNullID(userId);

		BaseResponse response = new BaseResponse();
		Map<String, Long> result = new HashMap<String, Long>();

		Long count = messageService.countNewUserMessages(userId);
		result.put("NEW_MSG_COUNT", count);

		Long userUnreadBooks = bookService.countUserUnreadBooks(userId);
		result.put("USER_UNREAD_BOOKS", userUnreadBooks);

		Long latesUserCourses = trainingService.countLastTrainings(userId, 3, EthraaConstants.ACTIVITY_TYPE_COURSE);
		result.put("COURSE_COUNT", latesUserCourses);

		Long latesUserMeetings = trainingService.countLastTrainings(userId, 3, EthraaConstants.ACTIVITY_TYPE_MEETING);
		result.put("MEETING_COUNT", latesUserMeetings);

		handleSuccessResponse(response, result);

		return response;

	}

}
