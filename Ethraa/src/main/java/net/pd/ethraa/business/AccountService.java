package net.pd.ethraa.business;

import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Account;

public interface AccountService {

    Account findByUserName(String userName) throws EthraaException;

    Account findUserWithPermissions(String userName) throws EthraaException;

}
