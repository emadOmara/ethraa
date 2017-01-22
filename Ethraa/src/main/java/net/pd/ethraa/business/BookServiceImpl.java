package net.pd.ethraa.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.pd.ethraa.common.CommonUtil;
import net.pd.ethraa.common.EthraaConstants;
import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.NullAwareBeanUtilsBean;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.Book;
import net.pd.ethraa.common.model.Group;
import net.pd.ethraa.common.model.Point;
import net.pd.ethraa.dao.AccountDao;
import net.pd.ethraa.dao.BookDao;
import net.pd.ethraa.integration.request.EvaluationRequest;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private BookDao bookDao;

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
    public void readBook(Book book) throws EthraaException {
	try {
	    if (CommonUtil.isEmpty(book.getAccounts())) {
		return;
	    }
	    Book fetchedBook = bookDao.findOne(book.getId());

	    for (Account acc : book.getAccounts()) {
		fetchedBook.getAccounts().add(acc);
	    }
	    bookDao.save(fetchedBook);
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
	    List<Book> books = bookDao.findAll();
	    populateBookReadersCount(books);
	    return books;
	} catch (Exception e) {
	    throw new EthraaException(e);
	}
    }

    private void populateBookReadersCount(List<Book> books) {
	for (Book book : books) {
	    Long actualReaders = bookDao.countBookReaders(book.getId());
	    Long missingReaders = bookDao.countBookMissingReaders(book.getId());

	    book.setActualReaders(actualReaders);
	    book.setMissingReaders(missingReaders);
	}

    }

    @Override
    public List<Book> getAssignedBooks(Long groupId) throws EthraaException {
	try {
	    Group g = new Group();
	    g.setId(groupId);
	    List<Book> books = bookDao.findByGroup(g);
	    populateBookReadersCount(books);
	    return books;
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
		return bookDao.listBookReaders(bookId);
	    } else {
		return bookDao.listBookMissingReaders(bookId);
	    }
	} catch (Exception e) {
	    throw new EthraaException(e);
	}

    }

    @Override
    public void evaluate(EvaluationRequest evaluation) throws EthraaException {

	Long bookId = evaluation.getBookId();
	Book book = bookDao.findOne(bookId);
	Integer maxPoints = book.getPoints();
	Long grade = evaluation.getGrade();
	if (grade > maxPoints) {
	    throw new EthraaException("Grade can't be more than the max allowed per book");
	}

	Long userId = evaluation.getUserId();
	Point p = accountDao.findBookEvaluation(evaluation.getBookId(), EthraaConstants.POINT_TYPE_READ_BOOK,
		evaluation.getUserId());

	if (p != null) {
	    p.setPoints(grade);
	} else {
	    Account account = accountDao.findOne(userId);

	    Point point = new Point();
	    point.setAccount(account);
	    point.setPoints(grade);
	    point.setType(EthraaConstants.POINT_TYPE_READ_BOOK);
	    point.setEntityId(bookId);

	    account.getPoints().add(point);
	    accountDao.save(account);

	}

    }

    @Override
    public List<Book> listUserBooks(Long userId) throws EthraaException {
	try {

	    Account account = accountDao.findOne(userId);
	    Group g = account.getGroup();

	    List<Book> books = bookDao.findByGroup(g);
	    for (Book book : books) {
		Long count = bookDao.isBookRead(account, book.getId());
		if (count > 0) {
		    book.setRead(true);
		}
	    }
	    return books;
	} catch (Exception e) {
	    throw new EthraaException(e);
	}
    }

}
