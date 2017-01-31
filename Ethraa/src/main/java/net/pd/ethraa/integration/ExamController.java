package net.pd.ethraa.integration;

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
import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Book;
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
    public BaseResponse listAllBooks() throws EthraaException {
	BaseResponse response = new BaseResponse();
	// handleSuccessResponse(response, books);

	return response;
    }

    @JsonView(Views.Public.class)
    @GetMapping(path = "/list/{groupId}")
    public BaseResponse listAssignedBooks(@PathVariable("groupId") Long groupId) throws EthraaException {

	handleNullID(groupId);

	BaseResponse response = new BaseResponse();
	// List<Book> books = bookService.getAssignedBooks(groupId);
	// handleSuccessResponse(response, books);

	return response;

    }

    @GetMapping(path = "/list/user/{userId}")
    public BaseResponse listUserBooks(@PathVariable("userId") Long userId) throws EthraaException {

	handleNullID(userId);

	BaseResponse response = new BaseResponse();
	// List<Book> books = bookService.listUserBooks(userId);
	// handleSuccessResponse(response, books);

	return response;

    }

    @PostMapping(path = "/add")
    public BaseResponse addExam(@RequestBody Exam exam) throws EthraaException {

	BaseResponse response = new BaseResponse();

	exam = examService.saveExam(exam);
	// handleSuccessResponse(response, book);

	return response;

    }

    @PostMapping(path = "/edit")
    public BaseResponse editBook(@RequestBody Book book) throws EthraaException {

	BaseResponse response = new BaseResponse();
	// if (book.isNew()) {
	// throw new
	// EthraaException(EthraaConstants.ERROR_MSG_ID_CAN_T_BE_NULL);
	// }
	//
	// bookService.saveBook(book);
	handleSuccessResponse(response, null);

	return response;

    }

    @PostMapping(path = "/readBook")
    public BaseResponse readBook(@RequestBody Book book) throws EthraaException {

	BaseResponse response = new BaseResponse();

	handleSuccessResponse(response, null);

	return response;

    }

    @JsonView(Views.Details.class)
    @GetMapping(path = "/get/{id}")
    public BaseResponse get(@PathVariable("id") Long id) throws EthraaException {

	BaseResponse response = new BaseResponse();
	handleNullID(id);

	// Book book = bookService.getBookDetails(id);
	// handleSuccessResponse(response, book);

	return response;

    }

}
