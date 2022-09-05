package com.kwan.springboot.service.post;

import com.kwan.springboot.domain.post.Post;
import com.kwan.springboot.domain.post.PostRepository;
import com.kwan.springboot.web.dto.PostListResponseDto;
import com.kwan.springboot.web.dto.PostResponseDto;
import com.kwan.springboot.web.dto.PostSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


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

    @Transactional(readOnly = true)
    public List<PostListResponseDto> findAllDesc() {
        return postRepository.findAllDesc().stream().map(PostListResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void delete(long id) {
        Post post=postRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        postRepository.delete(post);
    }
}
