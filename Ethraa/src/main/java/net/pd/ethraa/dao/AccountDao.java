package net.pd.ethraa.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.pd.ethraa.common.model.Account;

@Repository
public interface AccountDao extends CrudRepository<Account, Long> {

    Account findByUserName(String userName);

    @Query("SELECT a FROM Account a JOIN FETCH a.permissions WHERE a.mobile = (:mobile)")
    public Account findUserWithPermissions(@Param("mobile") String findUserWithPermissions);

}
