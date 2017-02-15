package net.pd.ethraa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.pd.ethraa.common.model.UserExam;
import net.pd.ethraa.common.model.UserExamKey;

@Repository
public interface UserExamDao extends JpaRepository<UserExam, UserExamKey> {

}
