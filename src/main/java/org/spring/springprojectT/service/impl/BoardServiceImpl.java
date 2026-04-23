package org.spring.springprojectT.service.impl;

import lombok.RequiredArgsConstructor;
import org.spring.springprojectT.dto.BoardDto;
import org.spring.springprojectT.repository.MemberRepository;
import org.spring.springprojectT.service.BoardService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final MemberRepository memberRepository;

    @Override
    public void boardInsert(BoardDto boardDto) {

    }

    @Override
    public List<BoardDto> boardList() {
        return List.of();
    }

    @Override
    public BoardDto boardDetail(Long id) {
        return null;
    }

    @Override
    public void boardDelete(Long id) {

    }

    @Override
    public void boardUpdate(BoardDto boardDto) {

    }

    @Override
    public void updateHit(Long id) {

    }
}
