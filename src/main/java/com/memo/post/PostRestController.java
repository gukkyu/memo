package com.memo.post;

import com.memo.post.bo.PostBO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/post")
@RestController
public class PostRestController {

    private final PostBO postBO;

    @PostMapping("/create")
    public Map<String,Object> create(
            @RequestParam("subject") String subject,
            @RequestParam("content") String content,
            @RequestParam(value = "file", required = false) MultipartFile file,
            HttpSession session
    ) {
        int userId = (int) session.getAttribute("userId");
        int rowCount = postBO.addPost(subject, content, file, userId);
        Map<String, Object> result = new HashMap<>();
        if (rowCount > 0) {
            result.put("code", 200);
            result.put("result", "성공");
        } else {
            result.put("code", 404);
            result.put("error_message", "관리자에게 연락!");
        }
        return result;
    }

    @PutMapping("/update")
    public Map<String,Object> update(
            @RequestParam("postId") int postId,
            @RequestParam("subject") String subject,
            @RequestParam("content") String content,
            @RequestParam(value = "file", required = false) MultipartFile file,
            HttpSession session
    ){
        int userId = (int) session.getAttribute("userId");

        postBO.updatePostByUserIdAndPostId(userId, postId, subject, content, file);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("result", "성공");
        return result;
    }

    @DeleteMapping("/delete")
    public Map<String,Object> delete(@RequestParam("postId") int postId, HttpSession session){
        int userId = (int) session.getAttribute("userId");

        postBO.deletePostByUserIdAndPostId(postId, userId);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("result", "성공");
        return result;
    }
}
