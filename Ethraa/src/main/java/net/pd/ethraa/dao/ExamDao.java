package net.pd.ethraa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.pd.ethraa.common.model.Exam;
import net.pd.ethraa.common.model.Group;

@Repository
public interface ExamDao extends JpaRepository<Exam, Long> {

	@Query("select e from Exam e where :group member of  e.groups")
	List<Exam> findByGroup(@Param("group") Group group );

}
