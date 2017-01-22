package net.pd.ethraa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.Group;
import net.pd.ethraa.common.model.Training;

@Repository
public interface TrainingDao extends JpaRepository<Training, Long> {

    @Query("select t from Training t where :group member of t.groups")
    List<Training> findByGroup(@Param("group") Group group);

    @Query("select acc from Account acc  where acc.group.id in (select g.id from Training t inner join t.groups g where t.id=:trainingId) ")
    List<Account> getMeetingMembers(@Param("trainingId") Long trainingId);

}