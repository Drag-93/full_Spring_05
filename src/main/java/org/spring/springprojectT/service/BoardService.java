package org.spring.springprojectT.service;

import org.spring.springprojectT.dto.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface BoardService {
    //게시글 작성
    //파일X
    void boardInsert(BoardDto boardDto);
    //파일O
    void boardFileInsert(BoardDto boardDto) throws IOException;

    //게시글 목록 조회
    List<BoardDto> boardList();

    //게시글 상세 조회
    BoardDto boardDetail(Long id);

    //게시글삭제
    //파일X
    void boardDelete(Long id);
    //파일O
    void boardFileDelete(Long id);

    //게시글 수정
    //파일X
    void boardUpdate(BoardDto boardDto);
    //파일O
    void boardUpdate2(BoardDto boardDto)  throws IOException;


    //조회수 증가
    void updateHit(Long id);

    //페이징
    Page<BoardDto> pagingListAll(Pageable pageable);

    //페이징 + 검색
    Page<BoardDto> pagingSearchListAll(Pageable pageable, String subject, String search);

}
