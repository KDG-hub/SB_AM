package com.koreaIT.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreaIT.demo.repository.MemberRepository;

@Service
public class MemberService {
	
	private MemberRepository memberRepository;
	
	@Autowired
	public MemberService (MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	public void doJoin(String loginId, String loginPw, String name, String nickName, String cellphoneNum, String email) {
		memberRepository.doJoin(loginId, loginPw, name, nickName, cellphoneNum, email);
	}

	public int idDupChk(String loginId) {
		return memberRepository.idDupChk(loginId);
	}
}
