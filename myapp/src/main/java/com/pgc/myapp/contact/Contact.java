package com.pgc.myapp.contact;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor // 전체 필드 초기 생성자
@NoArgsConstructor  // 빈 생성자

public class Contact {
    private String email;
    private String name;
    private String phone;
    private String image; // 파일을 base64 data-url 문자열로 저장
}
