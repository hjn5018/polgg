package com.polgg.polgg.controller.community;

import com.polgg.polgg.dto.community.CommentRequestDto;
import com.polgg.polgg.service.community.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> createComment(
            @PathVariable Long postId,
            Authentication authentication,
            @RequestBody CommentRequestDto dto) {
        String studentId = (String) authentication.getPrincipal();
        Long id = commentService.createComment(postId, studentId, dto);
        return ResponseEntity.ok(Map.of("id", id, "message", "댓글이 등록되었습니다."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(
            Authentication authentication,
            @PathVariable Long id) {
        String studentId = (String) authentication.getPrincipal();
        commentService.deleteComment(id, studentId);
        return ResponseEntity.ok(Map.of("message", "댓글이 삭제되었습니다."));
    }
}
