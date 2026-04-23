package org.spring.springprojectT.boardTest;

import org.junit.jupiter.api.Test;
import org.spring.springprojectT.dto.BoardDto;
import org.spring.springprojectT.entity.BoardEntity;
import org.spring.springprojectT.entity.MemberEntity;
import org.spring.springprojectT.repository.BoardRepository;
import org.spring.springprojectT.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;
import java.util.Optional;

@SpringBootTest
public class BoardTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;
    @Test
    void insert(){
        //회원 유무 체크
        Long memberId=1L;
//        Optional<MemberEntity> optionalMemberEntity=memberRepository.findById(memberId);
//        if(!optionalMemberEntity.isPresent()){
//            throw new NoSuchElementException("회원아이디가 없습니다.");
//        }
        memberRepository.findById(memberId).orElseThrow(()->{
            throw new NoSuchElementException("회원아이디가 없습니다.");
        });
        //게시글 작성
        boardRepository.save(BoardEntity.toInsertBoardEntity(boardDto));




    }
}
