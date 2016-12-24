package net.pd.ethraa.business;

import java.util.List;

import net.pd.ethraa.common.EthraaException;

public interface GenericService<T> {

    public abstract void add(T item) throws EthraaException;

    public void edit(T item) throws EthraaException;

    public void delete(T item) throws EthraaException;

    public T get(long id) throws EthraaException;

    public List<T> getAll() throws EthraaException;

}
