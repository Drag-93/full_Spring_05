package org.spring.springprojectT.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.spring.springprojectT.entity.BoardEntity;
import org.spring.springprojectT.entity.MemberEntity;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@ToString

public class BoardDto {
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private String boardPw;
    @NotBlank
    private String nickName;

    private int hit;

    private MemberEntity memberEntity;
    @NonNull
    private Long memberId; //로그인 한 id

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


    //Entity -> DTO

    public static BoardDto toBoardDto(BoardEntity boardEntity){
        BoardDto boardDto=new BoardDto();
        boardDto.setId(boardEntity.getId());
        boardDto.setTitle(boardEntity.getTitle());
        boardDto.setContent(boardEntity.getContent());
        boardDto.setBoardPw(boardEntity.getBoardPw());
        boardDto.setNickName(boardEntity.getNickName());
        boardDto.setHit(boardEntity.getHit());
        boardDto.setMemberEntity(boardEntity.getMemberEntity());
        boardDto.setCreateTime(boardEntity.getCreateTime());
        boardDto.setUpdateTime(boardEntity.getUpdateTime());


        return boardDto;
    }


}
