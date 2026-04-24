package org.spring.springprojectT.service;

import org.spring.springprojectT.dto.MemberDto;

import java.util.List;

public interface MemberService {

    void memberInsert(MemberDto memberDto);

    boolean emailCheck(String userEmail);

    List<MemberDto> memberList();

    MemberDto memberDetail(Long id);

    void memberUpdate(MemberDto memberDto);

    void memberDelete(MemberDto memberDto);

    MemberDto memberLogin(MemberDto memberDto);
}
