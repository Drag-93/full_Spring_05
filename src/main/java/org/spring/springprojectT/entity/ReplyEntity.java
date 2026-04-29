package org.spring.springprojectT.entity;


import jakarta.persistence.*;
import lombok.*;
import org.spring.springprojectT.common.BasicTime;
import org.spring.springprojectT.dto.ReplyDto;

import java.lang.reflect.Member;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="reply_tb4")
public class ReplyEntity extends BasicTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reply_id")
    private Long id;

    @Column(nullable = false)
    private String replyContent;

    @Column(nullable = false)
    private String replyWriter;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;



    public static ReplyEntity toInsertReplyEntity(ReplyDto replyDto){
        ReplyEntity replyEntity=new ReplyEntity();

        replyEntity.setReplyContent(replyDto.getReplyContent());
        replyEntity.setReplyWriter(replyDto.getReplyWriter());
        replyEntity.setBoardEntity(replyDto.getBoardEntity());
        replyEntity.setMemberEntity(replyDto.getMemberEntity());
        return replyEntity;
    }


    public static ReplyEntity toUpdateReplyEntity(ReplyDto replyDto){
        ReplyEntity replyEntity=new ReplyEntity();

        replyEntity.setId(replyDto.getId());
        replyEntity.setReplyContent(replyDto.getReplyContent());
        replyEntity.setReplyWriter(replyDto.getReplyWriter());
        replyEntity.setBoardEntity(replyDto.getBoardEntity());
        replyEntity.setMemberEntity(replyDto.getMemberEntity());
        return replyEntity;
    }
}
