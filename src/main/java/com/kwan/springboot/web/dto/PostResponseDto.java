package com.kwan.springboot.web.dto;

import com.kwan.springboot.domain.post.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {
    public long id;
    public String title;
    public String content;
    public String author;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getAuthor();
    }
}
