package net.pd.ethraa.business;

import java.util.List;

import net.pd.ethraa.common.EthraaException;
import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.AccountStatus;
import net.pd.ethraa.common.model.Group;

public interface AccountService {

    Account findByUserName(String userName) throws EthraaException;

    Account findUserWithPermissions(String mobile, String password, AccountStatus status) throws EthraaException;

    void save(Account account) throws EthraaException;

    List<Group> getAllGroups() throws EthraaException;

    void saveGroup(Group group) throws EthraaException;

    void deleteGroup(long groupID) throws EthraaException;

    List<Account> getGroupMembers(long groupID) throws EthraaException;

}
