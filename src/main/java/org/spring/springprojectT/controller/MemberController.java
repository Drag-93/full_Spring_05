package org.spring.springprojectT.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.spring.springprojectT.dto.MemberDto;
import org.spring.springprojectT.entity.MemberEntity;
import org.spring.springprojectT.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping("/login")
    public String loginOk(@ModelAttribute MemberDto memberDto){
        MemberDto member =memberService.memberLogin(memberDto);

        return "redirect:/member/detail/"+member.getId();
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
    @GetMapping("/update/{id}")
    public String memberUpdate(@PathVariable("id") Long id, Model model) {
        MemberDto memberDto = memberService.memberDetail(id);
        model.addAttribute("member", memberDto);
        return "pages/member/update";
    }

    @PostMapping("/update")
    public String updateOk(@Valid @ModelAttribute("member") MemberDto memberDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "pages/member/update";
        }

        memberService.memberUpdate(memberDto);;
        return "redirect:/member/detail/"+memberDto.getId();
    }

    @GetMapping("/delete/{id}")
    public String memberDelete(@PathVariable("id") Long id, Model model){
        MemberDto memberDto=memberService.memberDetail(id);
        model.addAttribute("member",memberDto);
        return "pages/member/delete";
    }
    @PostMapping("/delete")
    public String deleteOk(@ModelAttribute MemberDto memberDto){
        memberService.memberDelete(memberDto);
        return "redirect:/member/memberList";
    }


}
