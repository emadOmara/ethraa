package net.pd.ethraa.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.Group;
import net.pd.ethraa.common.model.Training;
import net.pd.ethraa.common.model.TrainingDay;

@Repository
public interface TrainingDao extends JpaRepository<Training, Long> {

	@Query("select t from Training t where :group member of t.groups and t.type=:type")
	List<Training> findByGroup(@Param("group") Group group, @Param("type") Long type);

	@Query("select acc from Account acc  where acc.accountStatus=1 and acc.group.id in (select g.id from Training t inner join t.groups g where t.id=:trainingId) ")
	List<Account> getMeetingMembers(@Param("trainingId") Long trainingId);

	@Query("select count (att) from TrainingDay t inner join t.attendence att  where att.account.id=:userId ")
	Long countUserTrainingAttendence(@Param("userId") Long userId);

	@Query("select count (att) from TrainingDay t inner join t.attendence att  where att.account.id=:userId and att.attendenceDate=:date ")
	Long isAttendedOnDay(@Param("userId") Long userId, @Param("date") Date date);

	@Query("select t from TrainingDay t where day.id=:dayId and training.id=:trainingId ")
	TrainingDay getTrainingDay(@Param("dayId") Long dayId, @Param("trainingId") Long trainingId);

	List<Training> findByType(Long type);

	@Query("select t.training.type ,count(att),t.training.points from TrainingDay t inner join t.attendence att where att.account.id=:userId group by t.training.type,t.training.points")
	Object[] countTrainingPoints(@Param("userId") Long userId);
	// @Query("select t.training.type ,count(t.id),t.training.points from
	// TrainingDay t inner join t.accounts acc where acc.id=:userId group by
	// t.training.type")
	// Object[] sumTrainingPoints(@Param("userId") Long userId);

	@Query("select t.training.title ,count(att),t.training.points from TrainingDay t inner join t.attendence att where att.account.id=:userId and t.training.type=:type group by t.training.title,t.training.points")
	Object[] getAllTrainingsWithAttendence(@Param("userId") Long userId, @Param("type") Long type);

	@Modifying
	@Query("delete  from TrainingDay t where t.day.id=:id")
	void removeTrainingDays(@Param("id") Long id);

	@Query("select count(t) from Training t inner join t.groups g ,Account acc where g.id=acc.group.id and acc.id=:userId and t.type=:type and t.startDate > :date")
	Long countLastTrainings(@Param("userId") Long userId, @Param("type") Long type, @Param("date") Date date);
}
