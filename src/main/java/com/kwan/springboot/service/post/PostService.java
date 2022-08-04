package com.kwan.springboot.service.post;

import com.kwan.springboot.domain.post.Post;
import com.kwan.springboot.domain.post.PostRepository;
import com.kwan.springboot.web.dto.PostResponseDto;
import com.kwan.springboot.web.dto.PostSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public long save(PostSaveDto postSaveDto) {
        return postRepository.save(postSaveDto.toEntity()).getId();
    }

    @Transactional
    public long update(long id, PostSaveDto postSaveDto) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(String.format("해당 게시글이 없습니다.id:%d", id))
        );
        post.update(postSaveDto.getTitle(), postSaveDto.getContent());
        return post.getId();
    }

    public PostResponseDto findById(long id) {
        return new PostResponseDto(postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(String.format("해당 게시글이 없습니다.id:%d", id))
        ));
    }
}
