package net.pd.ethraa.business;

import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Account;

public interface AuthenticationService {

    Account findByUserName(String userName) throws EthraaException;

    Account authenticate(String userName);

}
