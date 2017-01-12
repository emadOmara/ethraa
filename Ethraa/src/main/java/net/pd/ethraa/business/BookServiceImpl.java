package net.pd.ethraa.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.NullAwareBeanUtilsBean;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.Book;
import net.pd.ethraa.common.model.Group;
import net.pd.ethraa.dao.AccountDao;
import net.pd.ethraa.dao.BookDao;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private NullAwareBeanUtilsBean beanUtilService;

    @Override
    public Book saveBook(Book book) throws EthraaException {
	try {
	    if (book.isNew()) {
		return bookDao.save(book);
	    } else {// update
		Book fetchedBook = bookDao.findOne(book.getId());
		beanUtilService.copyProperties(fetchedBook, book);
		return bookDao.save(fetchedBook);
	    }
	} catch (Exception e) {
	    throw new EthraaException(e);
	}
    }

    @Override
    public void deleteBook(Long id) throws EthraaException {
	try {
	    bookDao.delete(id);
	} catch (Exception e) {
	    throw new EthraaException(e);
	}

    }

    @Override
    public List<Book> getAllBooks() throws EthraaException {
	try {
	    return bookDao.findAll();
	} catch (Exception e) {
	    throw new EthraaException(e);
	}
    }

    @Override
    public List<Book> getAssignedBooks(Long groupId) throws EthraaException {
	try {
	    Group g = new Group();
	    g.setId(groupId);
	    return bookDao.findByGroup(g);
	} catch (Exception e) {
	    throw new EthraaException(e);
	}
    }

    @Override
    public Book getBookDetails(Long id) throws EthraaException {
	try {

	    return bookDao.findOne(id);
	} catch (Exception e) {
	    throw new EthraaException(e);
	}
    }

    @Override
    public List<Account> listBookReaders(Long bookId, boolean read) throws EthraaException {
	try {
	    if (read) {
		return accountDao.listBookReaders(bookId);
	    } else {
		return accountDao.listBookMissingReaders(bookId);
	    }
	} catch (Exception e) {
	    throw new EthraaException(e);
	}

    }

}
