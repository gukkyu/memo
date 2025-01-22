package com.memo.post.mapper;

import com.memo.post.domain.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Mapper
public interface PostMapper {
    public List<Map<String, Object>> selectPostListTest();
    public List<Post> selectPostList(int userId);
    public Post selectPostByUserIdAndPostId(
            @Param("postId") int postId,
            @Param("userId") int userId);

    public int insertPost(
            @Param("subject") String subject,
            @Param("content") String content,
            @Param("imagePath") String imagePath,
            @Param("userId") int userId);

    public void updatePostById(
            @Param("postId") int postId,
            @Param("subject") String subject,
            @Param("content") String content,
            @Param("imagePath") String imagePath);
    public void deletePostByUserIdAndPostId(
            @Param("postId") int postId,
            @Param("userId") int userId);
}
