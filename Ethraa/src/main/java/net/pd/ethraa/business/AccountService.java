package net.pd.ethraa.business;

import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.AccountStatus;

public interface AccountService {

    Account findByUserName(String userName) throws EthraaException;

    Account findUserWithPermissions(String mobile, String password, AccountStatus status) throws EthraaException;

    void saveAccount(Account account) throws EthraaException;

    void deleteAccount(Long id) throws EthraaException;

}
