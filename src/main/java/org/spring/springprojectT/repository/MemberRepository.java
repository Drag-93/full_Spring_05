package org.spring.springprojectT.repository;

import org.spring.springprojectT.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Long> {

    Optional<MemberEntity> findByUserEmail(String userEmail);
    boolean existsByUserEmail(String userEmail);
}
