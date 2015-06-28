package org.com.app.core.dao;

import java.sql.SQLException;

import org.com.app.core.entity.Bin;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("binDao")
public class BinDaoImp implements BinDao {
	private HibernateTemplate hibernateTemplate;
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory){
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	@Transactional
	@Override
	public void save(Bin bin) throws HibernateException, SQLException {
		hibernateTemplate.save(bin);
	}
	@Transactional
	@Override
	public Bin getBin(final String bin) throws HibernateException, SQLException {
		return hibernateTemplate.execute(new HibernateCallback<Bin>(){
			@Override
			public Bin doInHibernate(Session session) throws HibernateException,
					SQLException {
				String hql = "from Bin b where b.bin = :bin";
				Query query = session.createQuery(hql);
				query.setString("bin", bin);
				return (Bin) query.uniqueResult();
			}
			
		});
	}

}
