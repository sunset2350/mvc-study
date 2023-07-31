package com.pgc.myapp.post;


// Get /posts
// 게시글 목록이 JSON으로 나오게

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private String creatorName;
    private String createdTime;
    private int no;
    private String title;
    private String content;

}
