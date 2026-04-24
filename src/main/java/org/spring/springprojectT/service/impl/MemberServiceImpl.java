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

        //이메일 변경시 중복 검사
        String oldEmail = optionalMemberEntity.get().getUserEmail();
        String newEmail = memberDto.getUserEmail();

        if(!oldEmail.equals(newEmail)){
            if(memberRepository.existsByUserEmail(oldEmail)){
                throw new IllegalStateException("중복된 이메일");
            }
        }

        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDto));

    }

    @Override
    public void memberDelete(MemberDto memberDto) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberDto.getId());
        if (!optionalMemberEntity.isPresent()) {
//            throw new NoSuchElementException("조회할 아이디가 없습니다");
            throw  new NoSuchElementException("회원이 존재하지 않습니다.");
        }
        if(!memberRepository.existsByUserEmail(memberDto.getUserEmail())){
            throw new NoSuchElementException("없는 아이디 입니다");
        }
        if(!optionalMemberEntity.get().getUserEmail().equals(memberDto.getUserEmail())){
            throw new IllegalArgumentException("본인만 삭제 가능합니다");
        }

        if(!optionalMemberEntity.get().getUserPw().equals(memberDto.getUserPw())){
            throw new IllegalArgumentException("비밀번호를 확인해주세요");
        }
        //회원삭제 deleteById
        memberRepository.deleteById(memberDto.getId());
    }

    @Override
    public MemberDto memberLogin(MemberDto memberDto) {
        Optional<MemberEntity> optionalMemberEntity=memberRepository.findByUserEmail(memberDto.getUserEmail());
        if(!optionalMemberEntity.isPresent()){
            throw new NoSuchElementException("없는 이메일");
        }
        if(!optionalMemberEntity.get().getUserPw().equals(memberDto.getUserPw())){
            throw  new IllegalArgumentException("아이디 또는 비밀번호를 확인해주세요");
        }

        return MemberDto.toMemberDto(optionalMemberEntity.get());
    }
}
