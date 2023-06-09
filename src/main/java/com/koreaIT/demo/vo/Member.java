package com.koreaIT.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
 	private int id;
 	private String regDate;
 	private String updateDate;
 	private String loginId;
 	private String loginPw;
 	private String name;
 	private String nickName;
 	private String cellphoneNum;
 	private String email;
}
