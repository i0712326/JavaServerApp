package org.com.app.core.service;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.com.app.core.dao.BinDao;
import org.com.app.core.entity.Bin;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("binService")
public class BinServiceImp implements BinService {
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private BinDao binDao;
	public void setBinDao(BinDao binDao){
		this.binDao = binDao;
	}
	@Override
	public void save(Bin bin) {
		try {
			binDao.save(bin);
		} catch (HibernateException he){
			logger.debug("Hibernate Exception occured while tried to save bin", he);
		}
		catch(SQLException se) {
			logger.debug("SQL Exception occured while try to save bin", se);
		}
	}

	@Override
	public Bin getBin(String bin) {
		try {
			return binDao.getBin(bin);
		} catch (HibernateException he){
			logger.debug("Hibernate Exception occured while try to get bin data", he);
			return null;
		}
		catch( SQLException se){
			logger.debug("SQL Exception occured while try to get bin data", se);
			return null;
		}
	}

}
