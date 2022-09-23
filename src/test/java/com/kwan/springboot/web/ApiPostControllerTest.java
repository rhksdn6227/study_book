package com.kwan.springboot.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwan.springboot.domain.post.Post;
import com.kwan.springboot.domain.post.PostRepository;
import com.kwan.springboot.web.dto.PostSaveDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiPostControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @After
    public void cleanup() {
        postRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testSave() throws Exception {

        String title = "test title";
        String content = "bla bla";
        PostSaveDto postSaveDto = PostSaveDto.builder()
                .title(title)
                .content(content)
                .author("kwan@naver.com")
                .build();
        String url = String.format("http://localhost:%d/api/v1/posts", port);

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(postSaveDto)))
                .andExpect(status().isOk());

        Post post = postRepository.findAll().get(0);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testUpdate() throws Exception {
        String title = "test title";
        String content = "bla bla";

        Post post = postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .author("kwan@naver.com")
                .build());
        long postId = post.getId();

        String updatedTitle = "updated title";
        String updatedContent = "updated content";

        PostSaveDto postSaveDto = PostSaveDto.builder()
                .title(updatedTitle)
                .content(updatedContent)
                .build();
        String url = String.format("http://localhost:%d/api/v1/posts/%d", port, postId);

        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(postSaveDto)))
                .andExpect(status().isOk());

        Post updatedPost = postRepository.findById(postId).get();
        assertThat(updatedPost.getTitle()).isEqualTo(updatedTitle);
        assertThat(updatedPost.getContent()).isEqualTo(updatedContent);
    }
}
