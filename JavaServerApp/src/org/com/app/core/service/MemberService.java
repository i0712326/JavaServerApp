package org.com.app.core.service;

import java.util.List;

import org.com.app.core.entity.Member;

public interface MemberService {
	public void save(Member member);
	public List<Member> getMember();
	public String getMemberPort(String port);
}
