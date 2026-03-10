package com.polgg.polgg.dto.community;

import com.polgg.polgg.domain.community.Post;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostSummaryDto {
    private Long id;
    private String authorName;
    private String category;
    private String title;
    private int commentCount;
    private LocalDateTime createdAt;

    public static PostSummaryDto from(Post post) {
        return PostSummaryDto.builder()
                .id(post.getId())
                .authorName(post.getAuthor().getName())
                .category(post.getCategory())
                .title(post.getTitle())
                .commentCount(post.getComments().size())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
