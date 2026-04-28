package org.spring.springprojectT.service.impl;

import lombok.RequiredArgsConstructor;
import org.spring.springprojectT.dto.BoardDto;
import org.spring.springprojectT.entity.BoardEntity;
import org.spring.springprojectT.entity.MemberEntity;
import org.spring.springprojectT.repository.BoardRepository;
import org.spring.springprojectT.repository.MemberRepository;
import org.spring.springprojectT.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Override
    public void boardInsert(BoardDto boardDto) {
    //1. 작성자 확인
        Optional<MemberEntity>optionalMemberEntity=memberRepository.findById(boardDto.getMemberId());
        if(!optionalMemberEntity.isPresent()){
            throw new IllegalArgumentException("Fail->회원정보가 없습니다");
        }
        //파일이 없을 경우
        MemberEntity memberEntity=new MemberEntity();
        memberEntity.setId(boardDto.getMemberId());; //from memberId -> Dto ->Dto MemberEntity

        boardDto.setMemberEntity(memberEntity);

        //게시글 작성
        boardRepository.save(BoardEntity.toInsertBoardEntity(boardDto));
    }

    @Override
    public List<BoardDto> boardList() {
        return boardRepository.findAll().stream().map(BoardDto::toBoardDto).collect(Collectors.toList());
    }

    @Override
    public BoardDto boardDetail(Long id) {
        Optional<BoardEntity>optionalBoardEntity=boardRepository.findById(id);
        if(!optionalBoardEntity.isPresent()){
            throw new IllegalArgumentException("Fail->게시글정보가 없습니다");
        }
        return BoardDto.toBoardDto(optionalBoardEntity.get());
    }

    @Override
    public void boardDelete(Long id) {
        Optional<BoardEntity>optionalBoardEntity=boardRepository.findById(id);
        if(!optionalBoardEntity.isPresent()){
            throw new IllegalArgumentException("Fail->게시글정보가 없습니다");
        }
        boardRepository.deleteById(id);
    }

    @Override
    public void boardUpdate(BoardDto boardDto) {
        Optional<BoardEntity>optionalBoardEntity=boardRepository.findById(boardDto.getId());
        if(!optionalBoardEntity.isPresent()){
            throw new IllegalArgumentException("Fail->게시글정보가 없습니다");
        }
        //memberId -> Dto -> Entity 변환
        MemberEntity memberEntity =new MemberEntity();
        memberEntity.setId(boardDto.getMemberId());
        boardDto.setMemberEntity(memberEntity);

        boardRepository.save(BoardEntity.toUpdateBoardEntity(boardDto));
    }





    //조회수
    @Override
    public void updateHit(Long id) {
//        boardRepository.updateHit(id);
    }

    @Override
    public Page<BoardDto> pagingListAll(Pageable pageable) {

        Page<BoardEntity> boardEntities=boardRepository.findAll(pageable);
        return boardEntities.map(BoardDto::toBoardDto);
    }

}
