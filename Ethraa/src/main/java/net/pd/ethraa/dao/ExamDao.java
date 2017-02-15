package net.pd.ethraa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.pd.ethraa.common.model.Account;
import net.pd.ethraa.common.model.Exam;
import net.pd.ethraa.common.model.Group;
import net.pd.ethraa.common.model.UserExamKey;

@Repository
public interface ExamDao extends JpaRepository<Exam, Long> {

	@Query("select e from Exam e where :group member of  e.groups")
	List<Exam> findByGroup(@Param("group") Group group);

	@Query("select acc from Account acc  where acc.accountStatus=1 and acc.group.id in (select g.id from Exam e inner join e.groups g where e.id=:examId) ")
	List<Account> getExamMembers(@Param("examId") Long examId);

	// @Query("select sum(s.) from UserExam ue inner join ue.solutions s where
	// ue.id=:key")
	@Query("select sum(s.score) from UserExam ue inner join ue.solutions s where ue.id=:key")
	Long getUserScore(@Param("key") UserExamKey key);

	@Modifying
	@Query("delete from Solution s where s.userExam.id.exam.id=:examId")
	void deleteSolutions(@Param("examId") Long examId);

	@Modifying
	@Query("delete from UserExam e where id.exam.id=:examId")
	void deleteUserExams(@Param("examId") Long examId);

	@Modifying
	@Query("delete from Answer a where a.question.id in (select q.id from Question q where q.exam.id=:examId)")
	void deleteAnswers(@Param("examId") Long examId);

	@Modifying
	@Query("delete from Question e where e.exam.id=:examId")
	void deleteQuestions(@Param("examId") Long examId);

}
