package org.spring.springprojectT.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.spring.springprojectT.common.BasicTime;
import org.spring.springprojectT.common.Role;
import org.spring.springprojectT.dto.MemberDto;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
@Entity
@Table(name = "member_tb4")
public class MemberEntity  extends BasicTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true,nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String userPw;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    //필수
//    @CreationTimestamp
//    @Column(updatable = false)
//    private LocalDateTime createTime;
//
//    @UpdateTimestamp
//    @Column(insertable = false)
//    private LocalDateTime updateTime;

    //Dto->Entity
    //회원가입
    public static MemberEntity toInsertMemberEntity(MemberDto memberDto) {
        MemberEntity memberEntity=new MemberEntity();

        memberEntity.setUserEmail(memberDto.getUserEmail());
        memberEntity.setUserName(memberDto.getUserName());
        memberEntity.setAddress(memberDto.getAddress());
        memberEntity.setUserPw(memberDto.getUserPw());
        memberEntity.setRole(Role.MEMBER);
        return memberEntity;
    }

    //회원수정
    public static MemberEntity toUpdateMemberEntity(MemberDto memberDto) {
        MemberEntity memberEntity=new MemberEntity();

        memberEntity.setId(memberDto.getId());
        memberEntity.setUserEmail(memberDto.getUserEmail());
        memberEntity.setUserPw(memberDto.getUserPw());
        memberEntity.setUserName(memberDto.getUserName());
        memberEntity.setAddress(memberDto.getAddress());
        memberEntity.setRole(memberDto.getRole());
        return memberEntity;

    }
}
