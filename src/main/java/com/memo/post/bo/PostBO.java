package com.memo.post.bo;

import com.memo.common.FileManagerService;
import com.memo.post.domain.Post;
import com.memo.post.mapper.PostMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PostBO {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private FileManagerService fileManager;

//    private Logger log = LoggerFactory.getLogger(PostBO.class);
//    private Logger log = LoggerFactory.getLogger(this.getClass());

    public List<Post> getPostListByUserId(int userId){
        return postMapper.selectPostList(userId);
    }

    public Post getPostByUserIdAndPostId(int postId, int userId){
        return postMapper.selectPostByUserIdAndPostId(postId, userId);
    }

    public int addPost(String subject, String content, MultipartFile file, int userId){

        String imagePath = null;

        if(file != null){
            imagePath = fileManager.uploadFile(file, userId);
        }

        return postMapper.insertPost(subject, content, imagePath, userId);
    }
    public void updatePostByUserIdAndPostId(int userId,
                                            int postId, String subject,
                                            String content, MultipartFile file){
        // 기존 글 가져오기 - 이미지 교체시 기존 이미지 제거하려면 imagePath가 필요함, 대상 존재 확인 유무 (생략 가능)
        Post post = postMapper.selectPostByUserIdAndPostId(postId, userId);
        if(post == null){
            log.warn("[### 글 수정] post is null. postId:{}, userId:{}", postId, userId);
            return;
        }

        String imagePath = null;
        if(file != null){
            // 새 이미지 업로드
            imagePath = fileManager.uploadFile(file, userId);

            // imagePath가 있으면 성공, 기존 이미지가 있다면 기존 이미지 삭제
            if(imagePath != null && post.getImagePath() != null){
                fileManager.deleteFile(post.getImagePath());
            }
        }

        // imagePath = null이거나 있거나.
        postMapper.updatePostById(postId, subject, content, imagePath);
    }
    public void deletePostByUserIdAndPostId(int postId, int userId){
        Post post = postMapper.selectPostByUserIdAndPostId(postId, userId);
        String imagePath = post.getImagePath();
        if(imagePath != null){
            fileManager.deleteFile(imagePath);
        }
        postMapper.deletePostByUserIdAndPostId(postId, userId);
    }
}
