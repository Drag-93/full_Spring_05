package org.spring.springprojectT.service.impl;

import lombok.RequiredArgsConstructor;
import org.spring.springprojectT.dto.MemberDto;
import org.spring.springprojectT.entity.MemberEntity;
import org.spring.springprojectT.repository.MemberRepository;
import org.spring.springprojectT.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public void memberInsert(MemberDto memberDto) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByUserEmail(memberDto.getUserEmail());
        if (optionalMemberEntity.isPresent()) {
            throw new IllegalStateException("이메일이 이미존재합니다.");
        }
//        memberRepository.findByUserEmail(userEmail).isPresent(el->{
//            throw  new IllegalStateException("이메일이 이미존재합니다."+el);
//        });

//        if (memberRepository.existsByUserEmail(memberDto.getUserEmail())) {
//            throw new IllegalStateException("이미 존재하는 이메일입니다.");
//        }
        memberRepository.save(MemberEntity.toInsertMemberEntity(memberDto));

    }

    @Override
    public boolean emailCheck(String userEmail) {
        boolean emailChecked = memberRepository.existsByUserEmail(userEmail);
        if (emailChecked != false) {
            throw new IllegalStateException("이메일이 이미존재합니다.");
        }
//        if(memberRepository.existsByUserEmail(userEmail)){
//            throw  new IllegalStateException("이메일이 이미존재합니다.");
//        }
        return emailChecked;
    }

    @Override
    public List<MemberDto> memberList() {
        List<MemberDto> memberDtos = memberRepository.findAll().stream()
                .map(MemberDto::toMemberDto)
                .collect(Collectors.toList());

        return memberDtos;
    }

    @Override
    public MemberDto memberDetail(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (!optionalMemberEntity.isPresent()) {
//            throw new NoSuchElementException("조회할 아이디가 없습니다");
            throw  new NoSuchElementException("회원이 존재하지 않습니다.");
        }
        MemberEntity memberEntity = optionalMemberEntity.get();
        return MemberDto.toMemberDto(memberEntity);

//        return MemberDto.toMemberDto(memberRepository.findById(id).orElseThrow(() -> {
//            throw new NoSuchElementException("조회할 아이디가 없습니다");
//        }));
    }

    @Override
    public void memberUpdate(MemberDto memberDto) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberDto.getId());
        if (!optionalMemberEntity.isPresent()) {
//            throw new NoSuchElementException("조회할 아이디가 없습니다");
            throw  new NoSuchElementException("회원이 존재하지 않습니다.");
        }

//        memberRepository.findById(memberDto.getId())
//                .orElseThrow(() -> new NoSuchElementException("조회할 아이디가 없습니다"));

        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDto));

    }

    @Override
    public void memberDelete(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (!optionalMemberEntity.isPresent()) {
//            throw new NoSuchElementException("조회할 아이디가 없습니다");
            throw  new NoSuchElementException("회원이 존재하지 않습니다.");
        }

        //        memberRepository.findById(memberDto.getId())
//                .orElseThrow(() -> new NoSuchElementException("조회할 아이디가 없습니다"));
        //회원삭제 deleteById
        memberRepository.deleteById(id);
    }
}
