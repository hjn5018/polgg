package com.polgg.polgg.dto.community;

import com.polgg.polgg.domain.community.Comment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDto {
    private Long id;
    private Long parentId;
    private String authorName;
    private String authorId;
    private String content;
    private LocalDateTime createdAt;
    private List<CommentResponseDto> children;

    public static CommentResponseDto from(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .parentId(comment.getParent() != null ? comment.getParent().getId() : null)
                .authorName(comment.getAuthor().getName())
                .authorId(comment.getAuthor().getStudentId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .children(comment.getChildren().stream()
                        .map(CommentResponseDto::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
