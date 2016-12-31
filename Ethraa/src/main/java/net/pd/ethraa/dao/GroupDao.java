package net.pd.ethraa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.pd.ethraa.common.model.Group;

@Repository
public interface GroupDao extends JpaRepository<Group, Long> {

    @Query("SELECT a.group,count(a) FROM Account a join a.group where a.accountStatus='INACTIVE' group by a.group")
    public Object[] geAllGroupsWithPendingRequestCount();

}
