package net.pd.ethraa.business;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.pd.ethraa.common.CommonUtil;
import net.pd.ethraa.common.EthraaConstants;
import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.Exam;
import net.pd.ethraa.common.model.Group;
import net.pd.ethraa.common.model.Training;
import net.pd.ethraa.dao.AccountDao;
import net.pd.ethraa.dao.GroupDao;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

	// private static final String CACHE_GROUPS_WITH_PENDING_REQUESTS =
	// "groupsWithPendingRequests";
	@Autowired
	private GroupDao groupDao;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private AccountService accountService;

	@Override
	// @Cacheable(cacheNames = CACHE_GROUPS_WITH_PENDING_REQUESTS)
	public List<Group> getAllGroupsWithPendingRequestCount() throws EthraaException {
		try {

			Object[] groups = groupDao.geAllGroupsWithPendingRequestCount(EthraaConstants.INACTIVE);

			List<Group> allGroups = new ArrayList<>();
			for (Object wrapper : groups) {
				Group g = (Group) ((Object[]) wrapper)[0];
				Long count = (Long) ((Object[]) wrapper)[1];
				g.setPendingRequests(count);
				allGroups.add(g);
			}

			return allGroups;
		} catch (Exception e) {
			throw new EthraaException(e);
		}
	}

	@Override
	// @CachePut(cacheNames = CACHE_GROUPS_WITH_PENDING_REQUESTS)
	public Group saveGroup(Group group) throws EthraaException {
		try {
			return groupDao.save(group);
		} catch (Exception e) {
			throw new EthraaException(e);
		}

	}

	@Override
	// @CacheEvict(cacheNames = CACHE_GROUPS_WITH_PENDING_REQUESTS)
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
			List<Account> groupMembers = accountDao.findByGroupId(groupID);
			return groupMembers;
		} catch (Exception e) {
			throw new EthraaException(e);
		}
	}

	@Override
	public List<Group> getAllGroups() throws EthraaException {
		try {
			List<Group> groups = groupDao.findAll();
			return groups;
		} catch (Exception e) {
			throw new EthraaException(e);
		}
	}

	@Override
	public Map<String, Long> getGroupDependencies(Long id) throws EthraaException {
		Map<String, Long> result = new HashMap<String, Long>();
		Group g = new Group();
		g.setId(id);
		Long accounts = groupDao.countGroupAccounts(id);
		if (accounts > 0) {
			result.put(Account.class.getSimpleName(), accounts);
		}
		Long books = groupDao.countGroupBooks(g);
		if (books > 0) {
			result.put(Book.class.getSimpleName(), books);
		}
		Long exams = groupDao.countGroupExams(g);
		if (exams > 0) {
			result.put(Exam.class.getSimpleName(), exams);
		}
		Long messages = groupDao.countGroupMessages(id);
		if (messages > 0) {
			result.put(Message.class.getSimpleName(), messages);
		}
		Long trainings = groupDao.countGroupTrainings(g);
		if (trainings > 0) {
			result.put(Training.class.getSimpleName(), trainings);
		}
		return result;
	}

	@Override
	public void forceDeleteGroup(Long id) throws EthraaException {
		try {

			List<Account> accounts = accountDao.findByGroupId(id);
			if (!CommonUtil.isEmpty(accounts)) {
				for (Account account : accounts) {
					accountService.deleteAccount(account.getId());
				}
			}
			groupDao.delete(id);

		} catch (Exception e) {
			throw new EthraaException(e);
		}

	}

}
