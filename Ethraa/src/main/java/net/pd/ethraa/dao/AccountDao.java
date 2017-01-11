package net.pd.ethraa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.AccountType;
import net.pd.ethraa.common.model.Permission;

@Repository
public interface AccountDao extends CrudRepository<Account, Long> {

    Account findByUserName(String userName);

    List<Account> findByGroupId(Long groupID);

    List<Account> findByAccountType(AccountType type);

    @Query("SELECT a FROM Account a left outer JOIN FETCH a.permissions WHERE a.mobile = :mobile and a.password=:password and a.accountStatus=:status")
    public Account findUserWithPermissions(@Param("mobile") String findUserWithPermissions,
	    @Param("password") String password, @Param("status") int status);

    @Query("SELECT a FROM Account a left outer JOIN FETCH a.permissions WHERE a.id = :id ")
    Account findUserWithPermissions(@Param("id") Long id);

    @Query("SELECT p FROM Permission p ")
    List<Permission> getAllPermissions();

    List<Account> findByGroupIdIn(List<Long> recipientGroups);

    List<Account> findByAccountTypeAndUserNameContainingIgnoreCase(AccountType type, String userName);

    @Query("SELECT a FROM Account a join a.books b  where b.id=:bookId")
    List<Account> listBookReaders(@Param("bookId") Long bookId);

    @Query("select acc from Account acc inner join acc.group g where g.id in (select gr.id from Book b inner join b.groups gr where b.id=:bookId) and acc not member of acc.books ")
    List<Account> listBookMissingReaders(@Param("bookId") Long bookId);

}