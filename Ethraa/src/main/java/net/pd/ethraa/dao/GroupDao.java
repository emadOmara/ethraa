package net.pd.ethraa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.pd.ethraa.common.model.Group;

@Repository
public interface GroupDao extends JpaRepository<Group, Long> {

    @Query("SELECT g,count(a) FROM Account a right outer join a.group g on a.accountStatus=:status group by g")
    public Object[] geAllGroupsWithPendingRequestCount(@Param("status") int status);

}
