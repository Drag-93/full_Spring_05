package org.spring.springprojectT.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.classfile.annotation.RuntimeInvisAnnos;
import org.spring.springprojectT.dto.BoardDto;
import org.spring.springprojectT.dto.FileDto;
import org.spring.springprojectT.entity.BoardEntity;
import org.spring.springprojectT.entity.FileEntity;
import org.spring.springprojectT.entity.MemberEntity;
import org.spring.springprojectT.repository.BoardRepository;
import org.spring.springprojectT.repository.FileRepository;
import org.spring.springprojectT.repository.MemberRepository;
import org.spring.springprojectT.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final FileRepository fileRepository;

    @Override
    public void boardInsert(BoardDto boardDto) {
        //1. 작성자 확인
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(boardDto.getMemberId());
        if (!optionalMemberEntity.isPresent()) {
            throw new IllegalArgumentException("Fail->회원정보가 없습니다");
        }
        //파일이 없을 경우
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(boardDto.getMemberId());
        ; //from memberId -> Dto ->Dto MemberEntity

        boardDto.setMemberEntity(memberEntity);

        //게시글 작성
        boardRepository.save(BoardEntity.toInsertBoardEntity(boardDto));
    }

    //게시글 작성(with 파일)
    @Override
    public void boardFileInsert(BoardDto boardDto) throws IOException {
        //1. 작성자
        MemberEntity memberEntity = MemberEntity.builder().id(boardDto.getMemberId()).build();
        boardDto.setMemberEntity(memberEntity);
        //2. 파일 없는 경우
        if (boardDto.getBoardFile().isEmpty()) {
            //file X
            BoardEntity boardEntity = BoardEntity.toInsertBoardEntity(boardDto);
            boardRepository.save(boardEntity);
            return;
        }
        //3. 파일 있는 경우

        MultipartFile boardFile = boardDto.getBoardFile(); //실제 파일
        //이름 암호화 -> DB저장, local에 저장 할 이름
        String oldFileName = boardFile.getOriginalFilename(); //원본 이미지 파일이름
        String newFileName = UUID.randomUUID() + "_" + oldFileName;///
        String fileSavePath = "E:/full/upload/" + newFileName;

        //파일 저장
        boardFile.transferTo(new File(fileSavePath)); // IoException
        //게시글 저장
        BoardEntity boardEntity = BoardEntity.toInsertFileBoardEntity(boardDto);
        BoardEntity savedBoard = boardRepository.save(boardEntity);
        //파일 DB저장
        FileDto fileDto = FileDto.builder()
                .oldFileName(oldFileName)
                .newFileName(newFileName)
                .boardEntity(savedBoard)
                .build();
        fileRepository.save(FileEntity.toInsertFile(fileDto));
    }


    @Override
    public List<BoardDto> boardList() {
        return boardRepository.findAll().stream().map(BoardDto::toBoardDto).collect(Collectors.toList());
    }

    @Override
    public BoardDto boardDetail(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (!optionalBoardEntity.isPresent()) {
            throw new IllegalArgumentException("Fail->게시글정보가 없습니다");
        }
        return BoardDto.toBoardDto(optionalBoardEntity.get());
    }

    @Override
    public void boardDelete(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (!optionalBoardEntity.isPresent()) {
            throw new IllegalArgumentException("Fail->게시글정보가 없습니다");
        }
        boardRepository.deleteById(id);
    }

    @Override
    public void boardFileDelete(Long id) {
        //1. 기존 게시글 정보 조회(조회수 및 기존 파일 상태 확인용)
        BoardEntity originBoard = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다"));
        //2. 새 파일 업로드 여부에 따른 분기
        if(originBoard.getAttachFile()==1){
            Optional<FileEntity> optionalFileEntity = fileRepository.findByBoardEntityId(id);
            if (optionalFileEntity.isPresent()) {
                File deleteFile = new File("E:/full/upload/" + optionalFileEntity.get().getNewFileName());
                if (deleteFile.exists()) deleteFile.delete();
                fileRepository.delete(optionalFileEntity.get());
            }
        }
        boardRepository.deleteById(id);
    }

    @Override
    public void boardUpdate(BoardDto boardDto) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(boardDto.getId());
        if (!optionalBoardEntity.isPresent()) {
            throw new IllegalArgumentException("Fail->게시글정보가 없습니다");
        }
        //memberId -> Dto -> Entity 변환
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(boardDto.getMemberId());
        boardDto.setMemberEntity(memberEntity);

        boardRepository.save(BoardEntity.toUpdateBoardEntity(boardDto));
    }

    @Override
    public void boardUpdate2(BoardDto boardDto) throws IOException{
        //1. 기존 게시글 정보 조회(조회수 및 기존 파일 상태 확인용)
        BoardEntity originBoard = boardRepository.findById(boardDto.getId()).orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다"));
        //2. 새 파일 업로드 여부에 따른 분기
        if (boardDto.getBoardFile().isEmpty()) {
            //기존 파일이 있었는지 여부를 그대로 전달
            boardDto.setAttachFile(originBoard.getAttachFile());
            boardDto.setHit(originBoard.getHit()); // 기존 조회수 유지
            MemberEntity memberEntity = MemberEntity.builder().id(boardDto.getMemberId()).build();
            boardDto.setMemberEntity(memberEntity);
            boardRepository.save(BoardEntity.toUpdateBoardEntity(boardDto));

        } else {
            //[새 파일로 교체]
            //2-1. 기존 파일이 있다면 삭제(로컬+DB)
            Optional<FileEntity> optionalFileEntity = fileRepository.findByBoardEntityId(boardDto.getId());
            if (optionalFileEntity.isPresent()) {
                File deleteFile = new File("E:/full/upload/" + optionalFileEntity.get().getNewFileName());
                if (deleteFile.exists()) deleteFile.delete();
                fileRepository.delete(optionalFileEntity.get());
            }
            //2-2. 새 파일 로컬 저장
            MultipartFile boardFile = boardDto.getBoardFile();
            String oldFileName = boardFile.getOriginalFilename();
            String newFileName = UUID.randomUUID()+"_"+oldFileName;
            boardFile.transferTo(new File("E:/full/upload/"+newFileName));
            //2-3. 게시글 및 파일 정보 저장
            boardDto.setAttachFile(1);
            MemberEntity memberEntity=MemberEntity.builder().id(boardDto.getMemberId()).build();
            boardDto.setMemberEntity(memberEntity);
            BoardEntity savedBoard=boardRepository.save(BoardEntity.toUpdateFileBoardEntity(boardDto));
            FileEntity fileEntity=FileEntity.builder()
                    .oldFileName(oldFileName).newFileName(newFileName).boardEntity(savedBoard).build();
            fileRepository.save(fileEntity);

        }
    }



    //조회수
    @Transactional
    @Override
    public void updateHit(Long id) {
        //조회수
        boardRepository.updateHit(id);
    }

    @Override
    public Page<BoardDto> pagingListAll(Pageable pageable) {

        Page<BoardEntity> boardEntities=boardRepository.findAll(pageable);


        return boardEntities.map(BoardDto::toBoardDto);
    }

    @Override
    public Page<BoardDto> pagingSearchListAll(Pageable pageable, String subject, String search) {

        Page<BoardEntity> boardEntities =null;

        if(subject==null||search==null){
            boardEntities =boardRepository.findAll(pageable);
        }else {
            if (subject.equals("title")) {
                boardEntities = boardRepository.findByTitleContaining(pageable,search);
            } else if (subject.equals("content")) {
                boardEntities = boardRepository.findByContentContaining(pageable,search);
            } else if (subject.equals("nickName")) {
                boardEntities = boardRepository.findByNickNameContaining(pageable,search);
            } else {
                boardEntities = boardRepository.findAll(pageable);
            }
        }
        return boardEntities.map(BoardDto::toBoardDto);
    }

}

