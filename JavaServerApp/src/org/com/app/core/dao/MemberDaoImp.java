package org.com.app.core.dao;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.com.app.core.entity.Member;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("memberDao")
public class MemberDaoImp implements MemberDao {
	private HibernateTemplate hibernateTemplate;
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory){
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	@Transactional
	@Override
	public void save(Member member) throws HibernateException, SQLException {
		hibernateTemplate.save(member);
	}
	@Transactional
	@Override
	public List<Member> getMembers() throws HibernateException, SQLException {
		String hql = "from Member m order by m.iin";
		return toList(hibernateTemplate.find(hql));
	}
	@Override
	public String getIin(final String port) throws HibernateException, SQLException {
		return hibernateTemplate.execute(new HibernateCallback<String>(){

			@Override
			public String doInHibernate(Session session)
					throws HibernateException, SQLException {
				String hql = "from Member m where m.port = :port";
				Query query = session.createQuery(hql);
				query.setString("port", port);
				Member member = (Member) query.uniqueResult();
				return member.getIin();
			}
			
		});
	}
	public List<Member> toList(final List<?> beans){
		if(beans == null) return null;
		if(beans.isEmpty()) return null;
		int size = beans.size();
		Member[] list = new Member[size];
		list = beans.toArray(list);
		return Arrays.asList(list);
	}
	

}
