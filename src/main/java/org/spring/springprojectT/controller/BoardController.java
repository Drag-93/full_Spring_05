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

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;


    @GetMapping({"","/boardList"})
    public String boardList(Model model){
        List<BoardDto> boardDtoList = boardService.boardList();
        model.addAttribute("boardList",boardDtoList);
        return "/pages/board/boardList";
    }


    @GetMapping("/detail/{id}")
    public String boardDetail(@PathVariable("id") Long id,Model model){
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

    @GetMapping("/boardList2")
    public String boardList2(@PageableDefault(page=0,size = 5,sort = "id", direction = Sort.Direction.DESC)Pageable pageable, Model model){
        Page<BoardDto> boardList=boardService.pagingListAll(pageable);
        long count = boardList.getTotalElements();
        int newPage=boardList.getNumber();
        int totalPages=boardList.getTotalPages();
        int blockNum=3;

        int startPage=(newPage/blockNum)*blockNum+1;
        int endPage=Math.min(startPage + blockNum -1,totalPages);

        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("boardList",boardList);
        model.addAttribute("newPage",newPage);

        return "pages/board/boardList2";
    }






}
