package org.spring.springprojectT.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.spring.springprojectT.common.Role;
import org.spring.springprojectT.entity.MemberEntity;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class MemberDto {

    private Long id;

    @NotBlank(message = "이메일을 입력해주세요")
    @Size(min = 3)
    private String userEmail;

    @NotBlank(message = "비빌번호를 입력해주세요")
    @Size(min = 3)
    private String userPw;

    @NotBlank(message = "이름를 입력해주세요")
    @Size(min = 3,max = 20)
    private String userName;

    @NotBlank(message = "주소를 입력해주세요")
    @Size(min = 3)
    private String address;

    private Role role;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    //Entity-> Dto

    public static MemberDto toMemberDto(MemberEntity memberEntity) {
        MemberDto memberDto=new MemberDto();
        memberDto.setId(memberEntity.getId());
        memberDto.setUserEmail(memberEntity.getUserEmail());
        memberDto.setUserPw(memberEntity.getUserPw());
        memberDto.setUserName(memberEntity.getUserName());
        memberDto.setAddress(memberEntity.getAddress());
        memberDto.setRole(memberEntity.getRole());
        memberDto.setCreateTime(memberEntity.getCreateTime());
        memberDto.setUpdateTime(memberEntity.getUpdateTime());
        return memberDto;
    }



}
