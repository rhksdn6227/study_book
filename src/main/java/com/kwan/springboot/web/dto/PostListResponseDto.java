package com.kwan.springboot.web.dto;

import com.kwan.springboot.domain.post.Post;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class PostListResponseDto {
    private long id;
    private String title;
    private String author;
    private LocalDateTime updatedAt;

    public PostListResponseDto(Post post) {
        this.id=post.getId();
        this.title=post.getTitle();
        this.author=post.getAuthor();
        this.updatedAt=post.getUpdatedAt();
    }
}
