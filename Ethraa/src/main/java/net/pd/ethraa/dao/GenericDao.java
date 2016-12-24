package net.pd.ethraa.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.pd.ethraa.common.model.BaseEntity;

@Repository
public interface GenericDao<T extends BaseEntity> extends CrudRepository<T, Long> {

}
