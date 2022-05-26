package com.choong.spr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.choong.spr.domain.MemberDto;
import com.choong.spr.mapper.MemberMapper;

@Service
public class MemberService {

	@Autowired
	private MemberMapper mapper;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public boolean addMember(MemberDto member) {
		
		// 평문 password를 암호화(encoding)
		String encodedPassword = passwordEncoder.encode(member.getPassword());
		
		// 암호화된 password를 다시 세팅
		member.setPassword(encodedPassword);
		
		return mapper.insertMember(member) == 1;
	}

	public boolean hasMemberId(String id) {
		
		return mapper.countMemberId(id) > 0;
	}

	public boolean hasMemberEmail(String email) {
		
		return mapper.countEmail(email) > 0;
	}

	public boolean hasMemberNickName(String nickName) {
		
		return mapper.countNickName(nickName) > 0;
	}

	public List<MemberDto> listMember() {
		
		return mapper.selectAllMember();
	}

	public MemberDto getMemberById(String id) {

		return mapper.selectMemberById(id);
	}

	public boolean removeMember(MemberDto dto) {
		MemberDto member = mapper.selectMemberById(dto.getId());
		
		String rawPW = dto.getPassword();
		String encodedPW = member.getPassword();
		
		if(passwordEncoder.matches(rawPW, encodedPW)) {
			return mapper.deleteMemberById(dto.getId()) == 1;
		}
		
		return false;
	}

	public boolean modifyMember(MemberDto dto, String oldPassword) {
		// db에서 member 읽어서 
		MemberDto oldMember = mapper.selectMemberById(dto.getId());
		
		String encodedPW = oldMember.getPassword();
		// 기존 password가 일치할 때만 계속 진행
		if(passwordEncoder.matches(oldPassword, encodedPW)) {
			dto.setPassword(passwordEncoder.encode(dto.getPassword()));
			return mapper.updateMember(dto) == 1;
		}
				
		return false;
	}

}
