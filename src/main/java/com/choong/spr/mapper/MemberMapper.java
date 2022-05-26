package com.choong.spr.mapper;

import java.util.List;

import com.choong.spr.domain.MemberDto;

public interface MemberMapper {

	int insertMember(MemberDto member);

	int countMemberId(String id);

	int countEmail(String email);

	int countNickName(String nickName);

	List<MemberDto> selectAllMember();

	MemberDto selectMemberById(String id);

	int deleteMemberById(String id);

	int updateMember(MemberDto dto);

}
