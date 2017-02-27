package net.pd.ethraa.business;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.pd.ethraa.common.CommonUtil;
import net.pd.ethraa.common.EthraaConstants;
import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.NullAwareBeanUtilsBean;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.AccountType;
import net.pd.ethraa.common.model.Email;
import net.pd.ethraa.common.model.Permission;
import net.pd.ethraa.dao.AccountDao;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDao accountDao;
	@Autowired
	private NullAwareBeanUtilsBean beanUtilService;

	@Autowired
	private MailService mailService;

	@Override
	public List<Account> findByUserName(AccountType type, String userName) {

		if (StringUtils.isEmpty(userName)) {
			return accountDao.findByAccountType(type);
		}

		return accountDao.findByAccountTypeAndUserNameContainingIgnoreCase(type, userName);
	}

	@Override
	// @Cacheable(cacheNames = "accounts", key = "#mobile", unless = "#result ==
	// null")
	public Account findUserWithPermissions(Long id) throws EthraaException {

		Account acc = null;
		try {
			acc = accountDao.findUserWithPermissions(id);
		} catch (Exception e) {
			throw new EthraaException(e);
		}

		return acc;
	}

	@Override
	@Cacheable(cacheNames = "accounts", key = "#mobile", unless = "#result == null")
	public Account findUserWithPermissions(String mobile, String password, int status) throws EthraaException {

		Account acc = null;
		try {
			acc = accountDao.findUserWithPermissions(mobile, password, status);
		} catch (Exception e) {
			throw new EthraaException(e);
		}

		return acc;
	}

	@Override
	// @CachePut(cacheNames = "accounts", key = "#account.mobile", condition =
	// "#account.mobile !=null")
	public Account saveAccount(Account account) throws EthraaException {
		try {
			if (account.isNew()) {
				return accountDao.save(account);
			} else {// update
				Account fetchedAccount = accountDao.findOne(account.getId());
				if (fetchedAccount == null) {
					throw new EthraaException("can't find account with specified id " + account.getId());
				}
				beanUtilService.copyProperties(fetchedAccount, account);
				return accountDao.save(fetchedAccount);
			}
		} catch (Exception e) {
			throw new EthraaException(e);
		}

	}

	@Override

	public void activateAccount(Account account) throws EthraaException {
		try {

			Account fetchedAccount = accountDao.findOne(account.getId());
			fetchedAccount.setAccountStatus(1);
			accountDao.save(fetchedAccount);

			Email activationEmail = CommonUtil.createEmail(fetchedAccount.getEmail(),
					EthraaConstants.EMAIL_SUBJECT_ACTIVATE_ACCOUNT, EthraaConstants.EMAIL_BODY_ACTIVATE_ACCOUNT);
			mailService.send(activationEmail);

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

	@Override
	public List<Account> getAllAccounts(AccountType type) throws EthraaException {
		try {
			return accountDao.findByAccountType(type);
		} catch (Exception e) {
			throw new EthraaException(e);
		}
	}

	@Override
	public List<Permission> getAllPermissions() throws EthraaException {
		try {
			return accountDao.getAllPermissions();
		} catch (Exception e) {
			throw new EthraaException(e);
		}
	}

	@Override
	public Account find(Long id) {
		return accountDao.findOne(id);

	}

	@Override
	public void forgetPassword(Account account) throws EthraaException {
		try {

			Account fetchedAccount = accountDao.findByMobile(account.getMobile());
			if (fetchedAccount == null) {
				throw new EthraaException("No account defined for this mobile number");
			}

			String generatePassword = CommonUtil.generatePassword(EthraaConstants.RANDOM_PASSWORD_LENGTH);

			fetchedAccount.setPassword(generatePassword);
			accountDao.save(fetchedAccount);

			Email forgetPasswordEmail = CommonUtil.createEmail(fetchedAccount.getEmail(),
					EthraaConstants.EMAIL_SUBJECT_FORGET_PASSWORD,
					EthraaConstants.EMAIL_BODY_FORGET_PASSWORD + generatePassword);
			mailService.send(forgetPasswordEmail);

		} catch (Exception e) {
			throw new EthraaException(e);
		}

	}

	@Override
	public Long countPendingUsers() throws EthraaException {
		try {

			Long count = accountDao.countPending();
			return count;

		} catch (Exception e) {
			throw new EthraaException(e);
		}
	}
}
