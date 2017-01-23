package net.pd.ethraa.business;

import java.util.List;

import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.AccountType;
import net.pd.ethraa.common.model.Permission;

public interface AccountService {

    /**
     * search for accounts with some type and like some user name , <br/>
     * if the user name is empty it will retrieve all users of that type
     *
     * @param type
     * @param userName
     * @return
     * @throws EthraaException
     */
    List<Account> findByUserName(AccountType type, String userName) throws EthraaException;

    Account findUserWithPermissions(String mobile, String password, int status) throws EthraaException;

    List<Account> getAllAccounts(AccountType type) throws EthraaException;

    Account saveAccount(Account account) throws EthraaException;

    void deleteAccount(Long id) throws EthraaException;

    Account findUserWithPermissions(Long id) throws EthraaException;

    List<Permission> getAllPermissions() throws EthraaException;

    Account find(Long id);

    void activateAccount(Account account) throws EthraaException;

    void forgetPassword(Account account) throws EthraaException;

}
