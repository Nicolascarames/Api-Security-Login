package com.universe.posts.domain.ports.out;

import com.universe.usersSecurity.persistence.entity.UserEntity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime dateUpdate;
    private Long user_id;
    private byte[] data;
    private String extName;
    private String urlData;
    private String fileName;
    private  UserEntity user;

}
