package com.pgc.myapp.contact;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor // 전체 필드 초기 생성자
@NoArgsConstructor  // 빈 생성자
@Entity
// Entity는 기본적으로 클래스명(파스칼케이스) -> 테이블명(스네이크케이스) 맵핑
// class : ContactActivity -> table : contact_activity

// ORM(Object Relational  Mapping
public class Contact {
    // @Id : 엔터티의 PK(primary key)를 지정
    @Id
    // key
    private String email; // 계정 Id

    // 제약조건 not null
    @Column(nullable = false)
    private String name;
    @Column(nullable = true)
    private String phone;

    private String image; // 파일을 base64 data-url 문자열로 저장
}
