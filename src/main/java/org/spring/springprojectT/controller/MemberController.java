package org.spring.springprojectT.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.spring.springprojectT.dto.BoardDto;
import org.spring.springprojectT.dto.MemberDto;
import org.spring.springprojectT.entity.MemberEntity;
import org.spring.springprojectT.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
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
    //페이징 + 검색
    @GetMapping("/memberList2")
    public String memberList2(@PageableDefault(page=0,size = 5,sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                             @RequestParam(name="subject", required = false) String subject,
                             @RequestParam(name="search", required = false) String search,
                             Model model){
        Page<MemberDto> memberList=memberService.pagingSearchListAll(pageable, subject, search);
        long count = memberList.getTotalElements();
        int newPage=memberList.getNumber();
        int totalPages=memberList.getTotalPages();
        int blockNum=5;

        //int 이기 때문에 1.xxx -> 1 반환
        int startPage=(newPage/blockNum)*blockNum+1;
        int endPage=Math.min(startPage + blockNum -1,totalPages);


        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("memberList",memberList);
        model.addAttribute("newPage",newPage);
        return "pages/member/memberList2";
    }

}
