package org.spring.springprojectT.service;

import org.spring.springprojectT.dto.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {
    //게시글 작성
    void boardInsert(BoardDto boardDto);

    //게시글 목록 조회
    List<BoardDto> boardList();

    //게시글 상세 조회
    BoardDto boardDetail(Long id);

    //게시글삭제
    void boardDelete(Long id);

    //게시글 수정
    void boardUpdate(BoardDto boardDto);

    //조회수 증가
    void updateHit(Long id);

    //페이징
    Page<BoardDto> pagingListAll(Pageable pageable);

    }
