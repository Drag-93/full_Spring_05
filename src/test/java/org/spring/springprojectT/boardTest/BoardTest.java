package org.spring.springprojectT.boardTest;

import org.junit.jupiter.api.Test;
import org.spring.springprojectT.dto.BoardDto;
import org.spring.springprojectT.dto.MemberDto;
import org.spring.springprojectT.entity.BoardEntity;
import org.spring.springprojectT.entity.MemberEntity;
import org.spring.springprojectT.repository.BoardRepository;
import org.spring.springprojectT.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
public class BoardTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

//    @Test
//    void insert() {
//        //회원 유무 체크
//        Long memberId = 1L;
////        Optional<MemberEntity> optionalMemberEntity=memberRepository.findById(memberId);
////        if(!optionalMemberEntity.isPresent()){
////            throw new NoSuchElementException("회원아이디가 없습니다.");
////        }
//        memberRepository.findById(memberId).orElseThrow(() -> {
//            throw new NoSuchElementException("회원아이디가 없습니다.");
//        });
//        //게시글 작성
//        boardRepository.save(BoardEntity.toInsertBoardEntity(boardDto));
//
//
//    }

    @Test
    void insertBoard() {
        for (int i = 61; i <= 100; i++) {
            MemberEntity memberEntity = new MemberEntity();
            memberEntity.setId(101L);

            BoardDto boardDto = new BoardDto();
            boardDto.setBoardPw("12");
            boardDto.setContent("내용" + i);
            boardDto.setTitle("제목" + i);
            boardDto.setNickName("tester");
            boardDto.setMemberEntity(memberEntity);

            BoardEntity boardEntity1 = BoardEntity.toInsertBoardEntity(boardDto);

//            BoardEntity boardEntity=BoardEntity.toInsertBoardEntity(BoardDto.builder()
//                    .title("제목"+i)
//                    .content("내용"+i)
//                    .boardPw("12")
//                    .nickName("m1"+i)
//                    .memberEntity(MemberEntityty.builder().id(1L).build())
//                    .build()
//            );
//            boardRepository.save(boardEntity);


        boardRepository.save(boardEntity1);
        }
        System.out.println();
    }
    //목록조회
    @Test
    void boardList(){

        List<BoardDto> boardDtos = boardRepository.findAll().stream()
                .map(BoardDto::toBoardDto)
                .collect(Collectors.toList());

        System.out.println(boardDtos.toString());
    }

    //상세조회
    @Test
    void boardDetail(){
        Long id = 1L;
        Optional<BoardEntity> optionalBoardEntity=boardRepository.findById(id);
        if(!optionalBoardEntity.isPresent()){
            throw new IllegalArgumentException("Fail->board id");
        }
        System.out.println(optionalBoardEntity.get());
    }

    //게시글 수정
    @Test
    void updateBoard(){
        Long id = 1L;
        Optional<BoardEntity> optionalBoardEntity=boardRepository.findById(id);
        if(!optionalBoardEntity.isPresent()){
            throw new IllegalArgumentException("Fail->board id");
        }
        MemberEntity memberEntity=new MemberEntity();
        memberEntity.setId(1L);

        BoardDto boardDto=new BoardDto();
        boardDto.setId(id);
        boardDto.setBoardPw("12");
        boardDto.setContent("내용수정"+1);
        boardDto.setTitle("제목수정");
        boardDto.setNickName("m1");
        boardDto.setHit(0); //테스트용 ->0
        boardDto.setMemberEntity(memberEntity);

        BoardEntity boardEntity2=BoardEntity.toUpdateBoardEntity(boardDto);
        boardRepository.save(boardEntity2);
    }

    @Test
    void boardUp(){

    }

}