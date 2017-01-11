package net.pd.ethraa.business;

import java.util.List;

import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.Book;
import net.pd.ethraa.integration.request.AssignBookRequest;

public interface BookService {

    List<Book> getAllBooks() throws EthraaException;

    List<Book> getAssignedBooks(Long groupId) throws EthraaException;

    Book saveBook(Book book) throws EthraaException;

    void deleteBook(Long id) throws EthraaException;

    Book getBookDetails(Long id) throws EthraaException;

    List<Account> listBookReaders(Long bookId, boolean b) throws EthraaException;

    void assignBook(AssignBookRequest request) throws EthraaException;

}
