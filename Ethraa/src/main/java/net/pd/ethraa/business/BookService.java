package net.pd.ethraa.business;

import java.util.List;

import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Book;
import net.pd.ethraa.integration.request.EvaluationRequest;
import net.pd.ethraa.integration.response.BookReaderWrapper;

public interface BookService {

	List<Book> getAllBooks() throws EthraaException;

	List<Book> getAssignedBooks(Long groupId) throws EthraaException;

	Book saveBook(Book book) throws EthraaException;

	void deleteBook(Long id) throws EthraaException;

	Book getBookDetails(Long id) throws EthraaException;

	List<BookReaderWrapper> listBookReaders(Long bookId, boolean b) throws EthraaException;

	void readBook(Book book) throws EthraaException;

	void evaluate(EvaluationRequest evaluation) throws EthraaException;

	List<Book> listUserBooks(Long userId) throws EthraaException;

	Long countUserUnreadBooks(Long userId) throws EthraaException;

}
