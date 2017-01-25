package net.pd.ethraa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.Group;
import net.pd.ethraa.common.model.Training;
import net.pd.ethraa.common.model.TrainingDay;

@Repository
public interface TrainingDao extends JpaRepository<Training, Long> {

    @Query("select t from Training t where :group member of t.groups")
    List<Training> findByGroup(@Param("group") Group group);

    @Query("select acc from Account acc  where acc.group.id in (select g.id from Training t inner join t.groups g where t.id=:trainingId) ")
    List<Account> getMeetingMembers(@Param("trainingId") Long trainingId);

    @Query("select count (t) from TrainingDay t inner join t.accounts acc  where acc.id=:userId) ")
    Long countUserTrainingAttendence(@Param("userId") Long userId);

    @Query("select t from TrainingDay t where day.id=:dayId and training.id=:trainingId ")
    TrainingDay getTrainingDay(@Param("dayId") Long dayId, @Param("trainingId") Long trainingId);

}
