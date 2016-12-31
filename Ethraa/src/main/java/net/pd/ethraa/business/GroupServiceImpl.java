package net.pd.ethraa.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.Group;
import net.pd.ethraa.dao.AccountDao;
import net.pd.ethraa.dao.GroupDao;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDao groupDao;
    @Autowired
    private AccountDao accountDao;

    @Override
    @Cacheable(cacheNames = "groups")
    public List<Group> getAllGroups() throws EthraaException {
	try {

	    Object[] groups = groupDao.geAllGroupsWithPendingRequestCount();

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
    @CachePut(cacheNames = "groups")
    public void saveGroup(Group group) throws EthraaException {
	try {
	    groupDao.save(group);
	} catch (Exception e) {
	    throw new EthraaException(e);
	}

    }

    @Override
    @CacheEvict(cacheNames = "groups")
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

}
