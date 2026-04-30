package org.spring.springprojectT.service;

import org.spring.springprojectT.dto.ReplyDto;

import java.util.List;

public interface ReplyService {
    public void replyInsert(ReplyDto dto);

    public List<ReplyDto> replyList(Long boardId);

    public void replyDelete(Long id);

    public void replyUpdate(ReplyDto dto);

    public ReplyDto replyDetail(Long id);
}
