package org.spring.springprojectT.service;

import org.spring.springprojectT.dto.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberService {

    void memberInsert(MemberDto memberDto);

    boolean emailCheck(String userEmail);

    List<MemberDto> memberList();

    MemberDto memberDetail(Long id);

    void memberUpdate(MemberDto memberDto);

    void memberDelete(MemberDto memberDto);

    MemberDto memberLogin(MemberDto memberDto);

    Page<MemberDto> pagingSearchListAll(Pageable pageable, String subject, String search);
}
