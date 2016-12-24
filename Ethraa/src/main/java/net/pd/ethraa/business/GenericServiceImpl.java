package net.pd.ethraa.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.pd.ethraa.common.model.BaseEntity;
import net.pd.ethraa.dao.GenericDao;

@Service
public class GenericServiceImpl<T extends BaseEntity> implements GenericService<T> {

    @Autowired
    private GenericDao<T> genericDao;

    @Override
    public void add(T item) {
	genericDao.save(item);
    }

    @Override
    public void edit(T item) {
	genericDao.save(item);
    }

    @Override
    public void delete(T item) {
	genericDao.delete(item);
    }

    @Override
    public T get(long id) {
	return genericDao.findOne(id);
    }

    @Override
    public List<T> getAll() {
	return (List<T>) genericDao.findAll();
    }

}
