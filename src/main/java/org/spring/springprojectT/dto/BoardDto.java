package org.spring.springprojectT.dto;

import jakarta.persistence.Column;
import lombok.*;
import org.spring.springprojectT.entity.MemberEntity;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class BoardDto {
    private Long id;

    private String title;

    private String content;

    private String boardPw;

    private String nickName;

    private int hit;

    private MemberEntity memberEntity;

    private Long memberId; //로그인 한 id

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
