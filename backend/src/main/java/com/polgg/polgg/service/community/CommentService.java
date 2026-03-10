package com.polgg.polgg.service.community;

import com.polgg.polgg.domain.community.Comment;
import com.polgg.polgg.domain.community.Post;
import com.polgg.polgg.domain.user.Member;
import com.polgg.polgg.dto.community.CommentRequestDto;
import com.polgg.polgg.repository.community.CommentRepository;
import com.polgg.polgg.repository.community.PostRepository;
import com.polgg.polgg.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createComment(Long postId, String studentId, CommentRequestDto dto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        Member author = memberRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Comment parent = null;
        if (dto.getParentId() != null) {
            parent = commentRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("상위 댓글을 찾을 수 없습니다."));
        }

        Comment comment = Comment.builder()
                .post(post)
                .author(author)
                .content(dto.getContent())
                .parent(parent)
                .build();

        return commentRepository.save(comment).getId();
    }

    @Transactional
    public void deleteComment(Long id, String studentId) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        if (!comment.getAuthor().getStudentId().equals(studentId)) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);
    }
}
