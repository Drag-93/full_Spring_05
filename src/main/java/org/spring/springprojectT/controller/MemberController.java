package org.spring.springprojectT.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.spring.springprojectT.dto.MemberDto;
import org.spring.springprojectT.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping({"", "/index"})
    private String member() {
        return "pages/member/index";
    }

    //회원가입페이지
    @GetMapping("/join")
    public String join(MemberDto memberDto) {
        return "pages/member/join";
    }

    //회원가입 실행 -> 유효성 검사
    @PostMapping("/join")
    public String joinOk(@Valid MemberDto memberDto,
                         BindingResult bindingResult) {
        //에러가 나면
        if (bindingResult.hasErrors()) {
            return "pages/member/join";
        }
        //회원가입 실행
        memberService.memberInsert(memberDto);
        return "redirect:/member/login";
    }

    //로그인 페이지
    @GetMapping("/login")
    public String login() {
        return "pages/member/login";
    }

    // 회원목록
    @GetMapping("/memberList")
    public String memberList(Model model) {
        List<MemberDto> memberDtoList = memberService.memberList();
        model.addAttribute("memberList", memberDtoList);
        return "pages/member/memberList";
    }

    // 추가: 회원 상세보기
    @GetMapping("/detail/{id}")
    public String memberDetail(@PathVariable("id") Long id, Model model) {
        MemberDto memberDto = memberService.memberDetail(id);
        model.addAttribute("member", memberDto);
        return "pages/member/memberDetail";
    }

}
