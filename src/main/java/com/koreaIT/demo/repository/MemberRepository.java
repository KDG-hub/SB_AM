package com.koreaIT.demo.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberRepository {
	
	public void doJoin(String loginId, String loginPw, String name, String nickName, String cellphoneNum, String email);

	public int idDupChk(String loginId);
}
