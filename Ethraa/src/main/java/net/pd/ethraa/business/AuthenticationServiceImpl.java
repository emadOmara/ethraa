package net.pd.ethraa.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.dao.AccountDao;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public Account findByUserName(String userName) {
	return accountDao.findByUserName(userName);
    }

    @Override
    public Account authenticate(String userName) {

	Account acc = null;
	try {
	    acc = accountDao.findUserWithPermissions(userName);

	} catch (Exception e) {

	    e.printStackTrace();
	}

	return acc;
    }

}
