package com.polgg.polgg.controller.community;

import com.polgg.polgg.dto.community.PostRequestDto;
import com.polgg.polgg.dto.community.PostResponseDto;
import com.polgg.polgg.dto.community.PostSummaryDto;
import com.polgg.polgg.service.community.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(Authentication authentication, @RequestBody PostRequestDto dto) {
        String studentId = (String) authentication.getPrincipal();
        Long id = postService.createPost(studentId, dto);
        return ResponseEntity.ok(Map.of("id", id, "message", "게시글이 등록되었습니다."));
    }

    @GetMapping
    public ResponseEntity<Page<PostSummaryDto>> getPosts(
            @RequestParam(required = false) String category,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(postService.getPosts(category, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(Authentication authentication, @PathVariable Long id, @RequestBody PostRequestDto dto) {
        String studentId = (String) authentication.getPrincipal();
        postService.updatePost(id, studentId, dto);
        return ResponseEntity.ok(Map.of("message", "게시글이 수정되었습니다."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(Authentication authentication, @PathVariable Long id) {
        String studentId = (String) authentication.getPrincipal();
        postService.deletePost(id, studentId);
        return ResponseEntity.ok(Map.of("message", "게시글이 삭제되었습니다."));
    }
}
