package com.choong.spr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.choong.spr.domain.BoardDto;

public interface BoardMapper {

	List<BoardDto> selectBoardAll(@Param("type") String type, @Param("keyword") String keyword);

	int insertBoard(BoardDto board);

	BoardDto selectBoardById(int id);

	int updateBoard(BoardDto dto);

	int deleteBoard(int id);

	List<BoardDto> selectBoardByMemberId(String memberId);

	void insertFile(@Param("boardId") int boardId, @Param("fileName") String fileName);

	List<String> selectFileByBoardId(int id);

	void deleteFileByBoardId(int boardId);

	void deleteFileByBoardIdAndFileName(@Param("boardId")int boardId, @Param("fileName") String fileName);

	

}
