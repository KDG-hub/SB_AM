package com.koreaIT.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreaIT.demo.repository.MemberRepository;
import com.koreaIT.demo.vo.Member;

@Service
public class MemberService {
	
	private MemberRepository memberRepository;
	
	@Autowired
	public MemberService (MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	public int doJoin(String loginId, String loginPw, String name, String nickName, String cellphoneNum, String email) {

		Member existsMember = memberRepository.getMemberByLoginId(loginId);
		
		if (existsMember != null) {
			return -1;
		}
		
		existsMember = memberRepository.getMemberByNickName(nickName);
		if (existsMember != null) {
			return -2;
		}
		
		existsMember = memberRepository.getMemberByNameAndEmail(name,email);
		if (existsMember != null) {
			return -3;
		}
		
		memberRepository.doJoin(loginId, loginPw, name, nickName, cellphoneNum, email);
		
		return memberRepository.getLastInsertId();
	}

	public int getLastInsertId() {
		return memberRepository.getLastInsertId();
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}
}
