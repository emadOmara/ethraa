package net.pd.ethraa.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.NullAwareBeanUtilsBean;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.AccountStatus;
import net.pd.ethraa.dao.AccountDao;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private NullAwareBeanUtilsBean beanUtilService;

    @Override
    public Account findByUserName(String userName) {
	return accountDao.findByUserName(userName);
    }

    @Override
    @Cacheable(cacheNames = "accounts", key = "#mobile", unless = "#result == null")
    public Account findUserWithPermissions(String mobile, String password, AccountStatus status)
	    throws EthraaException {

	Account acc = null;
	try {
	    acc = accountDao.findUserWithPermissions(mobile, password, status);
	} catch (Exception e) {
	    throw new EthraaException(e);
	}

	return acc;
    }

    @Override
    @CachePut(cacheNames = "accounts", key = "#account.mobile", condition = "#account.mobile !=null")
    public void saveAccount(Account account) throws EthraaException {
	try {
	    if (account.isNew()) {
		accountDao.save(account);
	    } else {// update
		Account fetchedAccount = accountDao.findOne(account.getId());
		beanUtilService.copyProperties(fetchedAccount, account);
		accountDao.save(fetchedAccount);
	    }
	} catch (Exception e) {
	    throw new EthraaException(e);
	}

    }

    @Override
    @CacheEvict(cacheNames = "accounts")
    public void deleteAccount(Long id) throws EthraaException {
	try {
	    accountDao.delete(id);
	} catch (Exception e) {
	    throw new EthraaException(e);
	}

    }

}
