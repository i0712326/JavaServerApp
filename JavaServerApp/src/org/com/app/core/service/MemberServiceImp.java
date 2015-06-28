package org.com.app.core.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.com.app.core.dao.MemberDao;
import org.com.app.core.entity.Member;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("memberService")
public class MemberServiceImp implements MemberService {
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private MemberDao memberDao;
	public void setMemberDao(MemberDao memberDao){
		this.memberDao = memberDao;
	}
	@Override
	public void save(Member member) {
		try {
			memberDao.save(member);
		} catch (HibernateException he) {
		 	logger.debug("Hibernate Exception occured while try to save member data", he);
			return;
		} catch(SQLException se){
			logger.debug("SQL Exception occured while try to save member data", se);
			return;
		}
	}

	@Override
	public List<Member> getMember() {
		try {
			return memberDao.getMembers();
		} catch (HibernateException he) {
			logger.debug("Hibernate Exception occured while try to get members data", he);
			return null;
		} catch(SQLException se){
			logger.debug("SQL Exception occured while try to get members data", se);
			return null;
		}
	}
	@Override
	public String getMemberPort(String port) {
		try {
			return memberDao.getIin(port);
		} catch (HibernateException e) {
			logger.debug("Hibernate Exception occured while try to get member iin", e);
			return null;
		} catch (SQLException e) {
			logger.debug("SQL Exception occured while try to get member iin", e);
			return null;
		}
	}
	
}
