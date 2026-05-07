package org.spring.springprojectT.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.spring.springprojectT.entity.BoardEntity;
import org.spring.springprojectT.entity.MemberEntity;
import org.springframework.web.multipart.MultipartFile;

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


    //파일(이미지)추가
    //파일이 있을 경우 1, 없을 경우 0
    private int attachFile;
    //input type = "file"
    private MultipartFile boardFile; //실제 파일(이미지)

    private String newFileName; // 새이름 -> DB,로컬저장소 저장이름

    private String oldFileName;
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
        boardDto.setMemberId(boardEntity.getMemberEntity().getId());
        boardDto.setCreateTime(boardEntity.getCreateTime());
        boardDto.setUpdateTime(boardEntity.getUpdateTime());

        if(boardEntity.getAttachFile()==0){
            //파일 X
            boardDto.setAttachFile(boardEntity.getAttachFile()); //0
        }else{
            //파일 O -> 파일 정보(newFileName,oldFileName)
            boardDto.setAttachFile(boardEntity.getAttachFile());//1
            //새 파일(이미지)이름    //파일(이미지) 하나일 경우
            boardDto.setNewFileName(boardEntity.getFileEntities().get(0).getNewFileName());
            //원본 파일(이미지)이름
            boardDto.setOldFileName(boardEntity.getFileEntities().get(0).getOldFileName());
        }

        return boardDto;
    }


}
