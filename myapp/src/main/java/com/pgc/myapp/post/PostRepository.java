package com.pgc.myapp.post;

import com.pgc.myapp.contact.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {



    // Spring Data Query Creation
    // 메서드 시그니처(이름, 배개변수개수/타입)에 맞게 SQL문을 생성해줌

}
