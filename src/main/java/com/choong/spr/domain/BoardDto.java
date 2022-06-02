package com.choong.spr.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class BoardDto {

	private int id;
	private String title;
	private String body;
	private String memberId;
	private String nickName;
	private LocalDateTime inserted;
	private int numOfReply;
	private List<String> fileName;

	public String getPrettyInserted() {
		// 24시간 이내면 시간만
		// 이전이면 년-월-일
		LocalDateTime now = LocalDateTime.now();
		if (now.minusHours(24).isBefore(inserted)) {
			return inserted.toLocalTime().toString();
		} else {
			return inserted.toLocalDate().toString();
		}
	}
}
