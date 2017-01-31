package net.pd.ethraa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.pd.ethraa.common.model.Exam;

@Repository
public interface ExamDao extends JpaRepository<Exam, Long> {

}
