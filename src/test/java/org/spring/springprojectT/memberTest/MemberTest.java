package org.spring.springprojectT.memberTest;

import org.junit.jupiter.api.Test;
import org.spring.springprojectT.common.Role;
import org.spring.springprojectT.dto.MemberDto;
import org.spring.springprojectT.entity.MemberEntity;
import org.spring.springprojectT.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
public class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    //1회원가입 -> 5
    @Test
    void insert(){
        for(int i=0;i<5;i++){
            MemberDto memberDto=new MemberDto();
            memberDto.setUserEmail("m"+i+"@m.com");
            memberDto.setUserPw("111");
            memberDto.setUserName("m1"+i);
            memberDto.setAddress("서울시");
            memberDto.setRole(Role.MEMBER);

            memberRepository.save(MemberEntity.toInsertMemberEntity(memberDto));

        }
    }
    //2.목록조회
    @Test
    void memberList(){
      List<MemberDto> memberDtos= memberRepository.findAll().stream()
                 .map(MemberDto::toMemberDto)
                 .collect(Collectors.toList());

      memberDtos.forEach(System.out::println);

    }
    //3.상세조회
    @Test
    void detail(){
        Long id=1L;
        //있으면 Entity, 없으며 예외발생 IllegalArgumentException
        Optional<MemberEntity> optionalMemberEntity= memberRepository.findById(id);
        if(!optionalMemberEntity.isPresent()){
            throw  new IllegalArgumentException("조회할 아이디가 없습니다");
        }
        MemberEntity memberEntity= optionalMemberEntity.get();
//        MemberEntity memberEntity= memberRepository.findById(id).orElseThrow(()->{
//           throw new IllegalArgumentException("조회할 아이디가 없습니다");
//        });
        System.out.println(MemberDto.toMemberDto(memberEntity));
    }

//    findBy~   있으면 Entity get
//    existsBy ~ 있으면 True,없으면 false
    //이메일 체크
    @Test
    void email(){
        String userEmail="m1222@m.com";
        //있으면 Entity, 없으며 예외발생 IllegalArgumentException
        Optional<MemberEntity> optionalMemberEntity= memberRepository.findByUserEmail(userEmail);
        if(optionalMemberEntity.isPresent()){
            throw  new IllegalArgumentException("이메일이 이미존재합니다.");
        }
//        memberRepository.findByUserEmail(userEmail).isPresent(el->{
//            throw  new IllegalArgumentException("이메일이 이미존재합니다."+el);
//        });
        //이메일 있는지 체크
       boolean emailChecked= memberRepository.existsByUserEmail(userEmail);
        if(emailChecked!=false){
            throw  new IllegalArgumentException("이메일이 이미존재합니다.");
        }
        if(memberRepository.existsByUserEmail(userEmail)){
            throw  new IllegalArgumentException("이메일이 이미존재합니다.");
        }
        System.out.println("회원가입 실행");
    }
    //4.회원수정
    @Test
    void update(){
        //아이디 체크
        Long id=1L;
        //있으면 Entity, 없으며 예외발생 IllegalArgumentException
        Optional<MemberEntity> optionalMemberEntity= memberRepository.findById(id);
        if(!optionalMemberEntity.isPresent()){
            throw  new IllegalArgumentException("조회할 아이디가 없습니다");
        }
        //수정 전
        System.out.println(MemberDto.toMemberDto(optionalMemberEntity.get()));
        //회원수정 save
        MemberDto memberDto=new MemberDto();
        memberDto.setId(id);
        memberDto.setUserEmail("m412@m.com");
        memberDto.setUserPw("111");
        memberDto.setUserName("m144");
        memberDto.setAddress("서울시44");
        memberDto.setRole(Role.MANAGER);
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDto));

        //수정후
       MemberEntity memberEntity= memberRepository.findById(id).orElseThrow(()->{
           throw new IllegalArgumentException("조회할 아이디가 없습니다");
        });
        System.out.println(MemberDto.toMemberDto(memberEntity));


    }
    //5.회원삭제
    @Test
    void delete(){
        //아이디 체크
        Long id=1L;
        //있으면 Entity, 없으며 예외발생 IllegalArgumentException
        Optional<MemberEntity> optionalMemberEntity= memberRepository.findById(id);
        if(!optionalMemberEntity.isPresent()){
            throw  new IllegalArgumentException("조회할 아이디가 없습니다");
        }
        //회원삭제 deleteById
        memberRepository.deleteById(id);


    }

}
