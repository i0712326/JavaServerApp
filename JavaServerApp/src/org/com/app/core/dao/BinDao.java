package org.com.app.core.dao;

import java.sql.SQLException;

import org.com.app.core.entity.Bin;
import org.hibernate.HibernateException;

public interface BinDao {
	public void save(Bin bin) throws HibernateException, SQLException;
	public Bin getBin(String bin) throws HibernateException, SQLException;
}
