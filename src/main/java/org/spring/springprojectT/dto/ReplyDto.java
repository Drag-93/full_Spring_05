package org.spring.springprojectT.dto;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.spring.springprojectT.entity.BoardEntity;
import org.spring.springprojectT.entity.MemberEntity;
import org.spring.springprojectT.entity.ReplyEntity;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDto {

    private Long id;

    private String replyContent;

    private String replyWriter;

    private BoardEntity boardEntity;

    private Long boardId;

    private MemberEntity memberEntity;

    private Long memberId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


    public static ReplyDto toReplyDto(ReplyEntity replyEntity){
        ReplyDto replyDto=new ReplyDto();

        replyDto.setId(replyEntity.getId());
        replyDto.setReplyContent(replyEntity.getReplyContent());
        replyDto.setReplyWriter(replyEntity.getReplyWriter());

//        replyDto.setBoardEntity(replyEntity.getBoardEntity());
        replyDto.setBoardId(replyEntity.getBoardEntity().getId());

//        replyDto.setMemberEntity(replyEntity.getMemberEntity());
        replyDto.setMemberId(replyEntity.getMemberEntity().getId());

        replyDto.setCreateTime(replyEntity.getCreateTime());
        replyDto.setUpdateTime(replyEntity.getUpdateTime());

        return replyDto;
    }
}
