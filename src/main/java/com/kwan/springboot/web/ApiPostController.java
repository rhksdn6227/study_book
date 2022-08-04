package com.kwan.springboot.web;

import com.kwan.springboot.service.post.PostService;
import com.kwan.springboot.web.dto.PostResponseDto;
import com.kwan.springboot.web.dto.PostSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ApiPostController {
    private final PostService postService;

    @PostMapping("/api/v1/posts")
    public long save(@RequestBody PostSaveDto postSaveDto) {
        return postService.save(postSaveDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public long update(@PathVariable Long id, @RequestBody PostSaveDto postSaveDto) {
        return postService.update(id,postSaveDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostResponseDto findById(@PathVariable long id) {
        return postService.findById(id);
    }
}
