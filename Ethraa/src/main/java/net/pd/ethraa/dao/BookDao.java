package net.pd.ethraa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.Book;
import net.pd.ethraa.common.model.Group;

@Repository
public interface BookDao extends JpaRepository<Book, Long> {

    @Query("select b from Book b where :group member of  b.groups")
    List<Book> findByGroup(@Param("group") Group group);

    @Query("SELECT a FROM Book b join b.accounts a where b.id=:bookId")
    List<Account> listBookReaders(@Param("bookId") Long bookId);

    @Query("SELECT count(a) FROM Book b join b.accounts a where b.id=:bookId")
    Long countBookReaders(@Param("bookId") Long bookId);

    // @Query("select acc from Account acc inner join acc.group g ,Book bk where
    // g.id in (select gr.id from Book b inner join b.groups gr where
    // b.id=:bookId) and acc not member of bk.accounts")
    @Query("select acc from Account acc , Group g ,Book b  where acc.group.id=g.id and g member of  b.groups and b.id=:bookId  and acc not member of b.accounts")
    List<Account> listBookMissingReaders(@Param("bookId") Long bookId);

    @Query("select count(acc) from Account acc , Group g ,Book b  where acc.group.id=g.id and g member of  b.groups and b.id=:bookId  and acc not member of b.accounts")
    Long countBookMissingReaders(@Param("bookId") Long bookId);

}
