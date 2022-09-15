package com.kwan.springboot.web;

import com.kwan.springboot.config.auth.LoginUser;
import com.kwan.springboot.config.auth.dto.SessionUser;
import com.kwan.springboot.service.post.PostService;
import com.kwan.springboot.web.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final PostService postService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts",postService.findAllDesc());

        if(user!=null) {
            model.addAttribute("userName",user.getName());
        }

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("posts/update/{id}")
    public String postsUpdate(@PathVariable long id, Model model) {
        PostResponseDto responseDto=postService.findById(id);
        model.addAttribute("post",responseDto);

        return "posts-update";
    }
}
