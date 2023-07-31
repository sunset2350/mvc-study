package com.pgc.myapp.post;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping(value = "/posts")
public class PostController {
    Map<Integer, Post> map = new ConcurrentHashMap<>();
    AtomicInteger num = new AtomicInteger(0);


    @GetMapping
    public List<Post> getList() {
        // 증가시키고 값 가져오기
        int no = num.incrementAndGet();
//        map.clear();
//        map.put(no, Post.builder()
//                .title("1제목")
//                .content("2")
//                .no(no)
//                .creatorName("park")
//                .createdTime(new Date().getTime())
//                .build());
//        no = num.incrementAndGet();
//        map.put(no, Post.builder()
//                .title("2제목")
//                .content("1")
//                .creatorName("chan")
//                .no(no)
//                .createdTime(new Date().getTime())
//                .build());

        var list = new ArrayList<>(map.values());
//        // 람다식(lambda expression)
//        // 식이 1개인 함수식;
//        // 매개변수 영역과 함수 본체를 화살표로 구분함
//        // 함수 본체의 수식 값이 반환값
//
        list.sort((a, b) -> (int) (b.getNo() - a.getNo()));
        return list;
    }


    @PostMapping
    public ResponseEntity<Map<String, Object>> addPost(@RequestBody Post post) {
        int no = num.incrementAndGet();

        // 채번 : 번호를 딴다 (1....2)

        // 이름 필수값 검증
        // 400 : bad request
        if(post.getTitle() == null || post.getTitle().isEmpty()){
            Map<String,Object> res = new HashMap<>();
            res.put("data",null);
            res.put("message","[title] field is required");

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(res);
        }

        if(post.getContent() == null || post.getContent().isEmpty()){
            Map<String,Object> res = new HashMap<>();
            res.put("data",null);
            res.put("message","[content] field is required");

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(res);
        }

        // 맵에 객체 추가

        //String CreatorName = "Park-Gang-chan";
        //String CreatedTime = new Date().toString();

        post.setCreatorName("Park-Gang-chan");
        post.setCreatedTime(new Date().toString());
        post.setNo(no);

//        map.put(no,new Post(CreatorName,CreatedTime,no, post.getTitle(), post.getContent()));
        map.put(no,post);


        // 응답 객체 생성
        Map<String, Object> res = new HashMap<>();
        System.out.println(map.get(no));
        res.put("data", post);
        res.put("message", "created");

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @DeleteMapping(value="/{no}")
    public ResponseEntity DeletePost(@PathVariable("no") int no){
        //Map<String, Post> map = new ConcurrentHashMap<>();
        System.out.println(map.get(no));


        if(map.get(no) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        map.remove(no);
        return ResponseEntity.status(HttpStatus.OK).build();

    }
}

