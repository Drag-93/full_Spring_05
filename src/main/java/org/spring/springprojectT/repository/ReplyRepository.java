package org.spring.springprojectT.repository;

import org.spring.springprojectT.entity.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReplyRepository extends JpaRepository<ReplyEntity,Long> {


    List<ReplyEntity> findAllByBoardEntity_Id(Long boardId);
}
