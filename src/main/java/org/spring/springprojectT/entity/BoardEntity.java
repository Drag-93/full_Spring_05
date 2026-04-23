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
@ToString
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






}
