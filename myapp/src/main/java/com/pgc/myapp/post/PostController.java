package com.pgc.myapp.post;


import com.pgc.myapp.contact.Contact;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping(value = "/posts")
public class PostController {
    List<Post> list = new ArrayList<>();
    AtomicInteger num = new AtomicInteger(0);

    @Autowired
    PostRepository repo;


    @GetMapping
    public List<Post> getList() {
        List<Post> list = repo.findAll(Sort.by("no").ascending());
        return list;
    }




    @PostMapping
    public ResponseEntity<Map<String, Object>> addPost(@RequestBody Post post) {
//        long no = num.incrementAndGet();

        if (post.getTitle() == null || post.getTitle().isEmpty()) {
            Map<String, Object> res = new HashMap<>();
            res.put("data", null);
            res.put("message", "[title] field is required");

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(res);
        }

        if (post.getContent() == null || post.getContent().isEmpty()) {
            Map<String, Object> res = new HashMap<>();
            res.put("data", null);
            res.put("message", "[content] field is required");

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(res);
        }

        post.setCreatorName("chan");
        post.setCreatedTime(new Date().toString());
//        post.setNo(no);

//        map.put(no, post);

        repo.save(post);
        Post savedpost = repo.save(post);

        if (savedpost != null) {
            Map<String, Object> res = new HashMap<>();
            System.out.println(post.getNo());
            System.out.println(post.getCreatedTime());
            res.put("data", post);
            res.put("message", "created");
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{no}")
    public ResponseEntity DeletePost(@PathVariable("no") long no) {
        if(!repo.findById(no).isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        repo.deleteById(no);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}

