package org.spring.springprojectT.service.impl;

import lombok.RequiredArgsConstructor;
import org.spring.springprojectT.dto.BoardDto;
import org.spring.springprojectT.dto.ReplyDto;
import org.spring.springprojectT.entity.BoardEntity;
import org.spring.springprojectT.entity.MemberEntity;
import org.spring.springprojectT.entity.ReplyEntity;
import org.spring.springprojectT.repository.BoardRepository;
import org.spring.springprojectT.repository.MemberRepository;
import org.spring.springprojectT.repository.ReplyRepository;
import org.spring.springprojectT.service.ReplyService;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Override
    public void replyInsert(ReplyDto dto) {

        Optional<MemberEntity>optionalMemberEntity=memberRepository.findById(dto.getMemberId());
        if(!optionalMemberEntity.isPresent()){
            throw new IllegalArgumentException("Fail->회원정보가 없습니다");
        }
        Optional<BoardEntity>optionalBoardEntity=boardRepository.findById(dto.getBoardId());
        if(!optionalBoardEntity.isPresent()){
            throw new IllegalArgumentException("Fail->게시글 정보가 없습니다");
        }

//        BoardEntity boardEntity=new BoardEntity();
//        boardEntity.setId(dto.getBoardId());
//        dto.setBoardEntity(boardEntity);
        dto.setBoardEntity(optionalBoardEntity.get());

//        MemberEntity memberEntity=new MemberEntity();
//        memberEntity.setId(dto.getMemberId());
//        dto.setMemberEntity(memberEntity);
        dto.setMemberEntity(optionalMemberEntity.get());

        //댓글 작성
        replyRepository.save(ReplyEntity.toInsertReplyEntity(dto));

    }

    @Override
    public List<ReplyDto> replyList(Long boardId) {


       return     replyRepository.findAllByBoardEntity_Id(boardId)
                .stream()
                .map(ReplyDto :: toReplyDto)
                .collect(Collectors.toList());
    }

    @Override
    public void replyDelete(Long id) {

    }

    @Override
    public void replyUpdate(ReplyDto dto) {
        Optional<ReplyEntity> optionalReplyEntity = replyRepository.findById(dto.getId());
        if(!optionalReplyEntity.isPresent()){
            throw new IllegalArgumentException("Fail -> 댓글 정보가 없습니다");
        }
        Optional<MemberEntity>optionalMemberEntity=memberRepository.findById(dto.getMemberId());
        if(!optionalMemberEntity.isPresent()){
            throw new IllegalArgumentException("Fail->회원정보가 없습니다");
        }
        Optional<BoardEntity>optionalBoardEntity=boardRepository.findById(dto.getBoardId());
        if(!optionalBoardEntity.isPresent()){
            throw new IllegalArgumentException("Fail->게시글 정보가 없습니다");
        }
        dto.setBoardEntity(optionalBoardEntity.get());
        dto.setMemberEntity(optionalMemberEntity.get());

        replyRepository.save(ReplyEntity.toUpdateReplyEntity(dto));
        System.out.println(dto);
    }

    @Override
    public ReplyDto replyDetail(Long id) {
        return ReplyDto.toReplyDto(replyRepository.findById(id).orElseThrow(()->new IllegalStateException("댓글 없음")));
    }
}
