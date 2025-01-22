package com.memo.post;

import com.memo.post.bo.PostBO;
import com.memo.post.domain.Post;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/post")
@Controller
public class PostController {

    @Autowired
    private PostBO postBO;

    @GetMapping("/post-list-view")
    public String postListView(Model model,
                               HttpSession session){
        Integer userId = (Integer)session.getAttribute("userId");
        if(userId == null){
            return "redirect:/user/sign-in-view";
        }

        List<Post> postList = postBO.getPostListByUserId(userId);
        model.addAttribute("post", postList);
        return "post/postList";
    }

    @GetMapping("/post-create-view")
    public String postCreate(){
        return "post/postCreate";
    }

    @GetMapping("/post-detail-view")
    public String postDetailView(
            @RequestParam("postId") int postId,
            Model model, HttpSession session
    ){
        int userId = (int)session.getAttribute("userId");
        Post post = postBO.getPostByUserIdAndPostId(postId, userId);
        model.addAttribute("post", post);

        return "post/postDetail";
    }
}
