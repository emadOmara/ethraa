package net.pd.ethraa.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.dao.AccountDao;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public Account findByUserName(String userName) {
	return accountDao.findByUserName(userName);
    }

    @Override
    public Account findUserWithPermissions(String userName) {

	Account acc = null;
	try {
	    acc = accountDao.findUserWithPermissions(userName);
	} catch (Exception e) {

	    e.printStackTrace();
	}

	return acc;
    }

    @Override
    public void add(Account account) throws EthraaException {
	accountDao.save(account);

    }

}
