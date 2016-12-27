package net.pd.ethraa.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.AccountStatus;
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
    @Cacheable(cacheNames = "accounts", key = "#mobile")
    public Account findUserWithPermissions(String mobile, String password, AccountStatus status) {

	Account acc = null;
	try {
	    acc = accountDao.findUserWithPermissions(mobile, password, status);
	    Thread.sleep(5000);
	} catch (Exception e) {

	    e.printStackTrace();
	}

	return acc;
    }

    @Override
    @CachePut(cacheNames = "accounts", key = "#account.mobile")
    public void add(Account account) throws EthraaException {
	accountDao.save(account);

    }

}
