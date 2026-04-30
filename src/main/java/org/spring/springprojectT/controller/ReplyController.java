package org.spring.springprojectT.controller;

import lombok.RequiredArgsConstructor;
import org.spring.springprojectT.dto.ReplyDto;
import org.spring.springprojectT.service.ReplyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyController {
    private final ReplyService replyService;

    //댓글 작성 /reply/insert
    @PostMapping("/insert")
    public ResponseEntity<String> save(@RequestBody ReplyDto dto){
        replyService.replyInsert(dto);

        return ResponseEntity.ok("댓글 작성 성공");
    }

    //댓글 목록 조회 /reply/list/
    @GetMapping("/list/{boardId}")
    public ResponseEntity<List<ReplyDto>> list(@PathVariable Long boardId){
        return ResponseEntity.ok(replyService.replyList(boardId));
    }

    //댓글 상세 조회(버그 수정 포함) /reply/detail/
    @GetMapping("/detail/{id}")
    public ResponseEntity<ReplyDto> detail(@PathVariable Long id){
        return ResponseEntity.ok(replyService.replyDetail(id));
    }

    //댓글 삭제 /reply/delete/
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        replyService.replyDelete(id);
        return ResponseEntity.ok("삭제 성공");
    }

    //댓글 수정
    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody ReplyDto dto){
        replyService.replyUpdate(dto);
        return ResponseEntity.ok("수정 성공");
    }
}
