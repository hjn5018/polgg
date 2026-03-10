package com.polgg.polgg.dto.community;

import com.polgg.polgg.domain.community.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDto {
    private Long id;
    private String authorName;
    private String authorId;
    private String category;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentResponseDto> comments;
    private int commentCount;

    public static PostResponseDto from(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .authorName(post.getAuthor().getName())
                .authorId(post.getAuthor().getStudentId())
                .category(post.getCategory())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .comments(post.getComments().stream()
                        .filter(c -> c.getParent() == null) // Root comments only; nested handled in CommentResponseDto
                        .map(CommentResponseDto::from)
                        .collect(Collectors.toList()))
                .commentCount(post.getComments().size())
                .build();
    }
}
