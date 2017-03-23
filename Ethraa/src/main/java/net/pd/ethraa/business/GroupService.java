package net.pd.ethraa.business;

import java.util.List;
import java.util.Map;

import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.Group;

public interface GroupService {

	List<Group> getAllGroupsWithPendingRequestCount() throws EthraaException;

	List<Group> getAllGroups() throws EthraaException;

	Group saveGroup(Group group) throws EthraaException;

	void deleteGroup(long groupID) throws EthraaException;

	List<Account> getGroupMembers(long groupID) throws EthraaException;

	Map<String, Long> getGroupDependencies(Long Id) throws EthraaException;

	void forceDeleteGroup(Long id) throws EthraaException;

}
