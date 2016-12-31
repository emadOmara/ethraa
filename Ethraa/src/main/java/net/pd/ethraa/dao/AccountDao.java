package net.pd.ethraa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.AccountStatus;

@Repository
public interface AccountDao extends CrudRepository<Account, Long> {

    Account findByUserName(String userName);

    List<Account> findByGroupId(Long groupID);

    @Query("SELECT a FROM Account a left outer JOIN FETCH a.permissions WHERE a.mobile = :mobile and a.password=:password and a.accountStatus=:status")
    public Account findUserWithPermissions(@Param("mobile") String findUserWithPermissions,
	    @Param("password") String password, @Param("status") AccountStatus status);

    @Query("SELECT a FROM Account a left outer JOIN FETCH a.permissions WHERE a.mobile = :mobile ")
    Account findUserWithPermissions(String mobile);

}
