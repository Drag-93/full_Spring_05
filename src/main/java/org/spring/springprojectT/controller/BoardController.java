package org.spring.springprojectT.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.spring.springprojectT.dto.BoardDto;
import org.spring.springprojectT.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    //게시글 작성
    @GetMapping("/write")
    public String write(BoardDto boardDto){
        return "pages/board/boardWrite";
    }

    @PostMapping("/write")
    public String writeOk(@Valid BoardDto boardDto,
                          BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "pages/board/boardWrite";
        }
        // 파일이 있고,없고
        boardService.boardInsert(boardDto);;

        return "redirect:/board/boardList";
    }



    @GetMapping({"","/boardList"})
    public String boardList(Model model){
        List<BoardDto> boardDtoList = boardService.boardList();
        model.addAttribute("boardList",boardDtoList);
        return "/pages/board/boardList";
    }


    @GetMapping("/detail/{id}")
    public String boardDetail(@PathVariable("id") Long id,Model model){
        //조회수
        boardService.updateHit(id);

        BoardDto boardDto=boardService.boardDetail(id);
        model.addAttribute("board",boardDto);
        return "pages/board/detail";
    }


    @PostMapping("/update")
    public String updateOk(@ModelAttribute("board") BoardDto boardDto,Model model){

        boardService.boardUpdate(boardDto);
        return "redirect:/board/boardList";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id")Long id){
        boardService.boardDelete(id);
        return "redirect:/board/boardList";
    }

    //페이징
    @GetMapping("/boardList2")
    public String boardList2(@PageableDefault(page=0,size = 5,sort = "id", direction = Sort.Direction.DESC)Pageable pageable, Model model){
        Page<BoardDto> boardList=boardService.pagingListAll(pageable);
        long count = boardList.getTotalElements();
        int newPage=boardList.getNumber();
        int totalPages=boardList.getTotalPages();
        int blockNum=5;

        //int 이기 때문에 1.xxx -> 1 반환
        int startPage=(newPage/blockNum)*blockNum+1;
        int endPage=Math.min(startPage + blockNum -1,totalPages);

        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("boardList",boardList);
        model.addAttribute("newPage",newPage);
        return "pages/board/boardList2";
    }
    //페이징 + 검색
    @GetMapping("/boardList3")
    public String boardList3(@PageableDefault(page=0,size = 5,sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                             @RequestParam(name="subject", required = false) String subject,
                             @RequestParam(name="search", required = false) String search,
                             Model model){
        Page<BoardDto> boardList=boardService.pagingSearchListAll(pageable, subject, search);
        long count = boardList.getTotalElements();
        int newPage=boardList.getNumber();
        int totalPages=boardList.getTotalPages();
        int blockNum=5;

        //int 이기 때문에 1.xxx -> 1 반환
        int startPage=(newPage/blockNum)*blockNum+1;
        int endPage=Math.min(startPage + blockNum -1,totalPages);

                
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("boardList",boardList);
        model.addAttribute("newPage",newPage);
        return "pages/board/boardList3";
    }

    @GetMapping("/boardList4")
    public String boardList4(@PageableDefault(page=0,size = 5,sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                             @RequestParam(name="subject", required = false) String subject,
                             @RequestParam(name="search", required = false) String search,
                             Model model){
        Page<BoardDto> boardList=boardService.pagingSearchListAll(pageable, subject, search);
        long count = boardList.getTotalElements();
        int newPage=boardList.getNumber();
        int totalPages=boardList.getTotalPages();
        int blockNum=5;

        //int 이기 때문에 1.xxx -> 1 반환
        int startPage=(newPage/blockNum)*blockNum+1;
        int endPage=Math.min(startPage + blockNum -1,totalPages);


        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("boardList",boardList);
        model.addAttribute("newPage",newPage);
        return "pages/board/boardList4";
    }

    @GetMapping("/detail2/{id}")
    public String detail2(@PathVariable("id") Long id,Model model){
        boardService.updateHit(id);
        BoardDto boardDto=boardService.boardDetail(id);
        model.addAttribute("board",boardDto);
        return "pages/board/detail2";
    }

    @GetMapping("/write2")
    public String write2(BoardDto boardDto){
        return "/pages/board/boardWrite2";
    }
    @PostMapping("/write2")
    public String writeOk2(@Valid BoardDto boardDto,
                           BindingResult bindingResult)throws IOException{
        if(bindingResult.hasErrors()){
            return "/pages/board/boardWrite2";
        }
        boardService.boardFileInsert(boardDto);
        return "redirect:/board/boardList4";

    }
    @PostMapping("/update2")
    public String updateOk2(@Valid BoardDto boardDto,
                            BindingResult bindingResult)throws IOException {
        if (bindingResult.hasErrors()) {
            return "/pages/board/boardList4";
        }
        boardService.boardUpdate2(boardDto);
        return "redirect:/board/detail2/" + boardDto.getId();
    }

    @GetMapping("/delete2/{id}")
    public String deleteFile(@PathVariable("id")Long id){
        boardService.boardFileDelete(id);
        return "redirect:/board/boardList4";
    }
}
