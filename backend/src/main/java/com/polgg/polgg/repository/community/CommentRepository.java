package com.polgg.polgg.repository.community;

import com.polgg.polgg.domain.community.Comment;
import com.polgg.polgg.domain.community.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostOrderByCreatedAtAsc(Post post);
    List<Comment> findAllByPostAndParentIsNullOrderByCreatedAtAsc(Post post);
}
