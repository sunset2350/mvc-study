package com.pgc.myapp.post;


// Get /posts
// 게시글 목록이 JSON으로 나오게

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // db의 auto-increment를 사용함 // 레코드가 추가될때 자동으로 증가되는 값을 사용
    private long no;
    private String title;
    private String content;
    private String creatorName;
    private String createdTime;

}
