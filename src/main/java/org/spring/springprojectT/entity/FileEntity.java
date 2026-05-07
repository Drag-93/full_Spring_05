package org.spring.springprojectT.entity;

import jakarta.persistence.*;
import lombok.*;
import org.spring.springprojectT.common.BasicTime;
import org.spring.springprojectT.dto.FileDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="file_tb4")
public class FileEntity extends BasicTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @Column(nullable = false)
    private String newFileName; // 새이름 -> DB, 로컬저장소 저장이름

    @Column(nullable = false)
    private  String oldFileName; // 원본이름

    //N:1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    public static FileEntity toInsertFile(FileDto fileDto){
        FileEntity fileEntity=new FileEntity();
        fileEntity.setNewFileName(fileDto.getNewFileName());
        fileEntity.setOldFileName(fileDto.getOldFileName());
        fileEntity.setBoardEntity(fileDto.getBoardEntity());
        return fileEntity;
    }

}
