package com.kwan.springboot.web;


import com.kwan.springboot.domain.post.Post;
import com.kwan.springboot.domain.post.PostRepository;
import com.kwan.springboot.web.dto.PostSaveDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiPostControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private PostRepository postRepository;

    @After
    public void cleanup() {
        postRepository.deleteAll();
    }

    @Test
    public void testSave() throws Exception {
        String title = "test title";
        String content = "bla bla";
        PostSaveDto postSaveDto = PostSaveDto.builder()
                .title(title)
                .content(content)
                .author("kwan@naver.com")
                .build();
        String url = String.format("http://localhost:%d/api/v1/posts", port);

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, postSaveDto, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        Post post = postRepository.findAll().get(0);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
    }

    @Test
    public void testUpdate() throws Exception {
        String title = "test title";
        String content = "bla bla";

        Post post = postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .author("kwan@naver.com")
                .build());
        long postId=post.getId();

        String updatedTitle = "updated title";
        String updatedContent = "updated content";

        PostSaveDto postSaveDto = PostSaveDto.builder()
                .title(updatedTitle)
                .content(updatedContent)
                .build();
        String url = String.format("http://localhost:%d/api/v1/posts/%d", port, postId);
        HttpEntity<PostSaveDto> requestEntity = new HttpEntity<>(postSaveDto);

        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        Post updatedPost=postRepository.findById(postId).get();
        assertThat(updatedPost.getTitle()).isEqualTo(updatedTitle);
        assertThat(updatedPost.getContent()).isEqualTo(updatedContent);
    }
}
