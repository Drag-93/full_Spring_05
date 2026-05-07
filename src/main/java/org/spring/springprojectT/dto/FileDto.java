package org.spring.springprojectT.dto;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.spring.springprojectT.entity.BoardEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FileDto {

    private Long id;

    private String newFileName; // 새이름 -> DB, 로컬저장소 저장이름

    private  String oldFileName; // 원본이름

    private Long boardId; //board_id

    private BoardEntity boardEntity; // board.getMemberEntity().id

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


    //파일(이미지)추가
    //파일이 있을 경우 1, 없을 경우 0
    private int attachFile;
    //input type = "file"
    private MultipartFile boardFile; //실제 파일(이미지)















}
