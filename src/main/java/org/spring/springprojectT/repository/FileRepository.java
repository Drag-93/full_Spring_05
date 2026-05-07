package org.spring.springprojectT.repository;

import org.spring.springprojectT.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long>{

    Optional<FileEntity> findByBoardEntityId(Long id);
}
