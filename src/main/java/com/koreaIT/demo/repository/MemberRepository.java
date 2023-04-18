package com.koreaIT.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.koreaIT.demo.vo.Member;

@Mapper
public interface MemberRepository {
	
	public void doJoin(String loginId, String loginPw, String name, String nickName, String cellphoneNum, String email);

	public Member getMemberById(int id);

	public int getLastInsertId();
	
	@Select("""
			SELECT * FROM `member`
				WHERE loginId = #{loginId}
			""")
	public Member getMemberByLoginId(String loginId);

}
