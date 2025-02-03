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
                               @RequestParam(value="prevId", required=false) Integer prevIdParam,
                               @RequestParam(value="nextId", required=false) Integer nextIdParam,
                               HttpSession session){
        Integer userId = (Integer)session.getAttribute("userId");
        if(userId == null){
            return "redirect:/user/sign-in-view";
        }

        List<Post> postList = postBO.getPostListByUserId(userId, prevIdParam, nextIdParam);
        int prevId = 0;
        int nextId = 0;

        if(postList.isEmpty() == false){
            nextId = postList.get(postList.size()-1).getId();
            prevId = postList.get(0).getId();

            // 이전이 없나? 그렇다면 0
            // 유저가 쓴 글들 중 제일 큰 숫자가 prevId와 같으면 이전이 없음
            if(postBO.isPrevLastPageByUserId(userId, prevId)){
                prevId = 0;
            }

            // 이후가 없나? 그렇다면 0
            // 유저가 쓴 글들 중 제일 큰 숫자가 prevId와 같으면 이전이 없음
            if(postBO.isNextLastPageByUserId(userId, nextId)){
                nextId = 0;
            }
        }

        model.addAttribute("prevId", prevId);
        model.addAttribute("nextId", nextId);
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
