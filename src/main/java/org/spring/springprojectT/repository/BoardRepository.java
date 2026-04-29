package org.spring.springprojectT.repository;

import org.spring.springprojectT.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    //조회수
    @Modifying  // JPQL -> Entity
    @Query("update BoardEntity b set b.hit = b.hit + 1 where b.id = :id")
    void updateHit(@Param("id") Long id);

    Page<BoardEntity> findByTitleContaining(Pageable pageable, String subject);

    Page<BoardEntity> findByContentContaining(Pageable pageable, String subject);

    Page<BoardEntity> findByNickNameContaining(Pageable pageable, String subject);
}
