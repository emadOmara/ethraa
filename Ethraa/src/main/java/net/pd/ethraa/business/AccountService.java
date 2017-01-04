package net.pd.ethraa.business;

import java.util.List;

import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.AccountType;
import net.pd.ethraa.common.model.Permission;

public interface AccountService {

    Account findByUserName(String userName) throws EthraaException;

    Account findUserWithPermissions(String mobile, String password, int status) throws EthraaException;

    List<Account> getAllAccounts(AccountType type) throws EthraaException;

    void saveAccount(Account account) throws EthraaException;

    void deleteAccount(Long id) throws EthraaException;

    Account findUserWithPermissions(Long id) throws EthraaException;

    List<Permission> getAllPermissions() throws EthraaException;

}
