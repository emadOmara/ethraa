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

	@Query("SELECT  count(a.id) FROM Account a  where a.group.id=:id ")
	public Long countGroupAccounts(@Param("id") Long id);

	@Query("select count(b.id) from Book b where :group member of  b.groups")
	public Long countGroupBooks(@Param("group") Group group);

	@Query("select count(e.id) from Exam e where :group member of  e.groups ")
	Long countGroupExams(@Param("group") Group group);

	@Query("select count(m.id) from Message m where m.group.id=:id")
	Long countGroupMessages(@Param("id") Long id);

	@Query("select count(t.id) from Training t where :group member of t.groups ")
	Long countGroupTrainings(@Param("group") Group group);

}
