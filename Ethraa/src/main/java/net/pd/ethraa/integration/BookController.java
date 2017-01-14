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

import net.pd.ethraa.business.BookService;
import net.pd.ethraa.common.EthraaConstants;
import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.Book;
import net.pd.ethraa.integration.jackson.Views;
import net.pd.ethraa.integration.request.EvaluationRequest;
import net.pd.ethraa.integration.response.BaseResponse;

@RestController()
@RequestMapping(path = "api/books")
public class BookController extends BaseController {

    Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @JsonView(Views.Public.class)
    @GetMapping(path = "/list")
    public BaseResponse listAllBooks() throws EthraaException {

	BaseResponse response = new BaseResponse();
	List<Book> allBooks = bookService.getAllBooks();
	handleSuccessResponse(response, allBooks);

	return response;

    }

    @JsonView(Views.Public.class)
    @GetMapping(path = "/list/{groupId}")
    public BaseResponse listAssignedBooks(@PathVariable("groupId") Long groupId) throws EthraaException {

	handleNullID(groupId);

	BaseResponse response = new BaseResponse();
	List<Book> books = bookService.getAssignedBooks(groupId);
	handleSuccessResponse(response, books);

	return response;

    }

    @PostMapping(path = "/add")
    @JsonView(Views.Public.class)
    public BaseResponse addBook(@RequestBody Book book) throws EthraaException {

	BaseResponse response = new BaseResponse();

	book = bookService.saveBook(book);
	handleSuccessResponse(response, book);

	return response;

    }

    @PostMapping(path = "/edit")
    public BaseResponse editBook(@RequestBody Book book) throws EthraaException {

	BaseResponse response = new BaseResponse();
	if (book.isNew()) {
	    throw new EthraaException(EthraaConstants.ERROR_MSG_ID_CAN_T_BE_NULL);
	}

	bookService.saveBook(book);
	handleSuccessResponse(response, null);

	return response;

    }

    @PostMapping(path = "/readBook")
    public BaseResponse readBook(@RequestBody Book book) throws EthraaException {

	BaseResponse response = new BaseResponse();
	if (book.isNew()) {
	    throw new EthraaException(EthraaConstants.ERROR_MSG_ID_CAN_T_BE_NULL);
	}

	bookService.readBook(book);
	handleSuccessResponse(response, null);

	return response;

    }

    @JsonView(Views.BookDetails.class)
    @GetMapping(path = "/get/{id}")
    public BaseResponse get(@PathVariable("id") Long id) throws EthraaException {

	BaseResponse response = new BaseResponse();
	handleNullID(id);

	Book book = bookService.getBookDetails(id);
	handleSuccessResponse(response, book);

	return response;

    }

    @JsonView(Views.Public.class)
    @GetMapping(path = "/usersRead/{bookId}")
    public BaseResponse getUsersReadBook(@PathVariable("bookId") Long bookId) throws EthraaException {

	BaseResponse response = new BaseResponse();
	handleNullID(bookId);

	List<Account> users = bookService.listBookReaders(bookId, true);
	handleSuccessResponse(response, users);

	return response;

    }

    @JsonView(Views.Public.class)
    @GetMapping(path = "/usersNotRead/{bookId}")
    public BaseResponse getUsersNotReadBook(@PathVariable("bookId") Long bookId) throws EthraaException {

	BaseResponse response = new BaseResponse();
	handleNullID(bookId);

	List<Account> users = bookService.listBookReaders(bookId, false);
	handleSuccessResponse(response, users);

	return response;

    }

    @DeleteMapping(path = "/delete/{id}")
    public BaseResponse deleteAccount(@PathVariable("id") Long id) throws EthraaException {
	handleNullID(id);

	BaseResponse response = new BaseResponse();
	bookService.deleteBook(id);
	handleSuccessResponse(response, null);

	return response;

    }

    @PostMapping(path = "/evaluate")
    public BaseResponse evaluateUser(@Valid @RequestBody EvaluationRequest evaluation) throws EthraaException {

	BaseResponse response = new BaseResponse();
	bookService.evaluate(evaluation);
	handleSuccessResponse(response, null);
	return response;
    }

}
