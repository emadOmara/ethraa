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
import net.pd.ethraa.common.model.Question;
import net.pd.ethraa.common.model.UserExamKey;

@Repository
public interface ExamDao extends JpaRepository<Exam, Long> {

	@Query("select e from Exam e where :group member of  e.groups")
	List<Exam> findByGroup(@Param("group") Group group);

	@Query("select e from Exam e where :group member of  e.groups and e.type=:type")
	List<Exam> findByGroupAndType(@Param("group") Group group, @Param("type") Long type);

	@Query("select acc from Account acc  where acc.accountStatus=1 and acc.group.id in (select g.id from Exam e inner join e.groups g where e.id=:examId) ")
	List<Account> getExamMembers(@Param("examId") Long examId);

	// @Query("select sum(s.) from UserExam ue inner join ue.solutions s where
	// ue.id=:key")
	@Query("select sum(s.score),ue.id ,ue.status from UserExam ue inner join ue.solutions s where ue.id=:key")
	Object[] getUserScore(@Param("key") UserExamKey key);

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

	@Query("select q from Question q where q.id=:id")
	Question getQuestion(@Param("id") Long id);

	List<Exam> findByType(Long type);

	@Query("select count(acc.id) from Account acc  where acc.accountStatus=1 and acc.group.id in (select g.id from Exam e inner join e.groups g where e.id=:examId) ")
	Long countExamMembers(@Param("examId") Long examId);

	@Query("select count(ue.id.exam.id) ,ue.status from UserExam ue where ue.id.exam.id=:examId group by ue.status,ue.id.exam.id")
	Object[] countExamSolutions(@Param("examId") Long examId);

	@Query("select count(ue.id.exam.id) from UserExam ue where ue.id.exam.type=:examType and ue.status=:status")
	Long countExams(@Param("examType") Long type, @Param("status") Long status);

	// @Query("select count(ue.id) from UserExam ue where
	// ue.id.exam.type=:examType and ue.status=:status")
	@Query("select count(e) from Exam e where :group member of  e.groups and e.type=:examType and e.userExams is empty")
	Long countUserExams(@Param("examType") Long examType, @Param("group") Group group);

}
