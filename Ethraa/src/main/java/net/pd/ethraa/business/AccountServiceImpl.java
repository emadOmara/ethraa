package net.pd.ethraa.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.AccountStatus;
import net.pd.ethraa.common.model.Group;
import net.pd.ethraa.dao.AccountDao;
import net.pd.ethraa.dao.GroupDao;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private GroupDao groupDao;
    @Autowired
    private AccountDao accountDao;

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
    @CachePut(cacheNames = "accounts", key = "#account.mobile")
    public void save(Account account) throws EthraaException {
	try {
	    accountDao.save(account);
	} catch (Exception e) {
	    throw new EthraaException(e);
	}

    }

    @Override
    @CachePut(cacheNames = "groups")
    public List<Group> getAllGroups() throws EthraaException {
	try {
	    List<Group> allGroups = groupDao.findAll();
	    return allGroups;
	} catch (Exception e) {
	    throw new EthraaException(e);
	}
    }

    @Override
    public void saveGroup(Group group) throws EthraaException {
	try {
	    groupDao.save(group);
	} catch (Exception e) {
	    throw new EthraaException(e);
	}

    }

    @Override
    public void deleteGroup(long groupID) throws EthraaException {
	try {
	    groupDao.delete(groupID);
	} catch (Exception e) {
	    throw new EthraaException(e);
	}
    }

    @Override
    public List<Account> getGroupMembers(long groupID) throws EthraaException {
	try {
	    return accountDao.findByGroupId(groupID);
	} catch (Exception e) {
	    throw new EthraaException(e);
	}
    }

}
