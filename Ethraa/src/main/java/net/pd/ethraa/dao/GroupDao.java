package net.pd.ethraa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.pd.ethraa.common.model.Group;

@Repository
public interface GroupDao extends JpaRepository<Group, Long> {

}
