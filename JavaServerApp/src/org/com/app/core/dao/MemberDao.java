package org.com.app.core.dao;

import java.sql.SQLException;
import java.util.List;

import org.com.app.core.entity.Member;
import org.hibernate.HibernateException;

public interface MemberDao {
	public void save(Member member) throws HibernateException, SQLException;
	public List<Member> getMembers() throws HibernateException, SQLException;
	public String getIin(String port) throws HibernateException, SQLException;
}
