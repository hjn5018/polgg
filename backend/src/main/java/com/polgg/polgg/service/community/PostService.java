package com.polgg.polgg.service.community;

import com.polgg.polgg.domain.community.Post;
import com.polgg.polgg.domain.user.Member;
import com.polgg.polgg.dto.community.PostRequestDto;
import com.polgg.polgg.dto.community.PostResponseDto;
import com.polgg.polgg.dto.community.PostSummaryDto;
import com.polgg.polgg.enums.UserRole;
import com.polgg.polgg.repository.community.PostRepository;
import com.polgg.polgg.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createPost(String studentId, PostRequestDto dto) {
        Member author = memberRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if ("공지".equals(dto.getCategory()) && author.getRole() != UserRole.ADMIN) {
            throw new IllegalArgumentException("공지사항은 관리자만 작성할 수 있습니다.");
        }

        Post post = Post.builder()
                .author(author)
                .title(dto.getTitle())
                .content(dto.getContent())
                .category(dto.getCategory())
                .build();

        return postRepository.save(post).getId();
    }

    @Transactional(readOnly = true)
    public Page<PostSummaryDto> getPosts(String category, Pageable pageable) {
        if (category == null || category.isEmpty() || "전체".equals(category)) {
            return postRepository.findAllByOrderByCreatedAtDesc(pageable)
                    .map(PostSummaryDto::from);
        }
        return postRepository.findAllByCategoryOrderByCreatedAtDesc(category, pageable)
                .map(PostSummaryDto::from);
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        return PostResponseDto.from(post);
    }

    @Transactional
    public void updatePost(Long id, String studentId, PostRequestDto dto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        if (!post.getAuthor().getStudentId().equals(studentId)) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }

        post.update(dto.getTitle(), dto.getContent(), dto.getCategory());
    }

    @Transactional
    public void deletePost(Long id, String studentId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        Member user = memberRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!post.getAuthor().getStudentId().equals(studentId) && user.getRole() != UserRole.ADMIN) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        postRepository.delete(post);
    }
}
