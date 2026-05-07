package org.spring.springprojectT.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.spring.springprojectT.common.BasicTime;
import org.spring.springprojectT.dto.BoardDto;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
//@ToString
@Table(name="board_tb4")
public class BoardEntity extends BasicTime {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name="board_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String boardPw;

    @Column(nullable = false)
    private String nickName;

    private int hit;

    //연관관계
    //외래키
    // BoardEntity    N:1     MemberEntity
    // 'member_id' -> 칼럼명(필드)이 자동으로 생김 -> 'board_tb4' 테이블에 자동으로 생김
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private MemberEntity memberEntity;


    @JsonIgnore
    @OneToMany(mappedBy = "boardEntity",cascade = CascadeType.REMOVE)
    private List<ReplyEntity> replyEntityList;

    //FileEntity 연관관계 추가
    //파일 유무 체크
    @Column(nullable = false)
    private int attachFile; // 게시글 작성시 파일이 존재 하면 1, 없으면 0

    //1:N
    @OneToMany(mappedBy = "boardEntity", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<FileEntity> fileEntities;

//DTO -> Entity
    //파일(이미지)이 없을 경우
    //게시글작성
    public static BoardEntity toInsertBoardEntity(BoardDto boardDto){
        BoardEntity boardEntity=new BoardEntity();
        boardEntity.setTitle(boardDto.getTitle());
        boardEntity.setContent(boardDto.getContent());
        boardEntity.setBoardPw(boardDto.getBoardPw());
        boardEntity.setNickName(boardDto.getNickName());
        boardEntity.setAttachFile(0);//파일이 없을 경우
        boardEntity.setHit(0);
        boardEntity.setMemberEntity(boardDto.getMemberEntity());
        return boardEntity;
    }

    //게시글수정
    public static BoardEntity toUpdateBoardEntity(BoardDto boardDto){
        BoardEntity boardEntity=new BoardEntity();
        boardEntity.setId(boardDto.getId());
        boardEntity.setTitle(boardDto.getTitle());
        boardEntity.setContent(boardDto.getContent());
        boardEntity.setBoardPw(boardDto.getBoardPw());
        boardEntity.setNickName(boardDto.getNickName());
        boardEntity.setAttachFile(boardDto.getAttachFile());
        boardEntity.setHit(boardDto.getHit());
        boardEntity.setMemberEntity(boardDto.getMemberEntity());

        return boardEntity;
    }

    //파일(이미지)이 있을 경우
    //게시글작성
    public static BoardEntity toInsertFileBoardEntity(BoardDto boardDto){
        BoardEntity boardEntity=new BoardEntity();
        boardEntity.setTitle(boardDto.getTitle());
        boardEntity.setContent(boardDto.getContent());
        boardEntity.setBoardPw(boardDto.getBoardPw());
        boardEntity.setNickName(boardDto.getNickName());
        boardEntity.setAttachFile(1);//파일이 있을 경우
        boardEntity.setHit(0);
        boardEntity.setMemberEntity(boardDto.getMemberEntity());
        return boardEntity;
    }

    //게시글수정
    public static BoardEntity toUpdateFileBoardEntity(BoardDto boardDto){
        BoardEntity boardEntity=new BoardEntity();
        boardEntity.setId(boardDto.getId());
        boardEntity.setTitle(boardDto.getTitle());
        boardEntity.setContent(boardDto.getContent());
        boardEntity.setBoardPw(boardDto.getBoardPw());
        boardEntity.setNickName(boardDto.getNickName());
//        boardEntity.setAttachFile(1);//기존 파일이 있는걸 수정하므로
        boardEntity.setAttachFile(boardDto.getAttachFile());
        boardEntity.setHit(boardDto.getHit());
        boardEntity.setMemberEntity(boardDto.getMemberEntity());

        return boardEntity;
    }
}
