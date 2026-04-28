package org.spring.springprojectT.entity;

import jakarta.persistence.*;
import lombok.*;
import org.spring.springprojectT.common.BasicTime;
import org.spring.springprojectT.dto.BoardDto;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private MemberEntity memberEntity;


//DTO -> Entity
    //게시글작성
    public static BoardEntity toInsertBoardEntity(BoardDto boardDto){
        BoardEntity boardEntity=new BoardEntity();
        boardEntity.setTitle(boardDto.getTitle());
        boardEntity.setContent(boardDto.getContent());
        boardEntity.setBoardPw(boardDto.getBoardPw());
        boardEntity.setNickName(boardDto.getNickName());
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
        boardEntity.setHit(boardDto.getHit());
        boardEntity.setMemberEntity(boardDto.getMemberEntity());

        return boardEntity;
    }
}
