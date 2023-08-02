package com.pgc.myapp.contact;

import com.pgc.myapp.post.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping(value = "/contacts")
public class ContactController {
    // 동시처리에 대한 지원을 해주는 자료구조
    // 여러명의 유저들이 같은 데이터를 접근할 수 있음.
    // 데이터베이스는 기본적으로 동시성에 대한 구현이 있음.
    Map<String, Contact> map = new ConcurrentHashMap<>();

    // @Autowired : Bean 객체를 의존성 주입
    // Bean 객체 : @Configuration 클래스에 등록된 싱글턴 클래스로 생성된 객체

    // static : JVM 실행 시점에 생성됨
    // 싱글턴 : 첫 실행시점 객체가 1번 생성됨. 이후 부터는 생성된 객체를 재 사용
    // 메서드의 반환 타입이 싱글턴 클래스

    // 스프링 프레임워크의 DATA JPA 라이브러리
    // @Repository 인터페이스에 맞는 동적 클래스를 실행 시점에 생성하여 @Autowired 키워드가 있는 선언 변수에 주입한다.

    // 의존성 주입(Dependency Injection)
    // 객체를 사용하는 곳 외부에서 객체를 생성한 후에
    // 객체를 사용하는 곳에 필드, 메서드 매개변수로 넣어줌
    // @Configuration 클래스의 @Bean 클래스

    // 스프링프레임워크에서는
    // 1. @Configuration 클래스의 @Bean클래스를 생성
    // 2. @Autowired 어노테이션 변수에 객체를 의존성 주입
    @Autowired
    ContactRepository repo;

    // GET /contacts
    @GetMapping
    public List<Contact> getContactList() {
        // repo.finAll(); 전체 테이블 목록 조회
        // SELECT * FROM 테이블
        // repo.findAll(Sort sort); 정렬하여 전체 테이블 목록 조회
        // SELECT * FROM 테이블 ORDER BY 정렬컬럼, 정렬컬럼...
        List<Contact> list = repo.findAll(Sort.by("name").ascending());

        return list;
    }

    // GET /contacts/paging?page=0$size=10
    // query-string으로 받을 것임
    // ? 키 = 값 & 키 = 값..
    // @RequestParam
    // query=string 값을 매개변수 받는 어노테이션
    @GetMapping(value ="/paging")
    public Page<Contact> getContactPaging(@RequestParam int page, @RequestParam int size){
        // 기본적으로 key 정렬
        // ORDER BY email DESC
        // 정렬 매개변수 객체
        Sort sort = Sort.by("email").descending();

        // 페이지 매개변수 객체
        // SQL : OFFSET page * size LIMIT size
        // OFFSET : 어떤 기준점에 대한 거리
        // OFFSET 10 : 10번째 까지 이후
        // LIMIT 10 : 10건의 레코드
        // OFFSET 10  LIMIT 10 : 앞쪽 10건은 패스하고 다음에 10건을 조회
        PageRequest pageRequest = PageRequest.of(page,size,sort);
        return repo.findAll(pageRequest);
    }

    // GET /contacts/paging/searchByName?page=0&size=10&name=hong
    @GetMapping(value= "/paging/searchByName")
    public Page<Contact> getContactSearch(@RequestParam int page, @RequestParam int size, @RequestParam String name){
//        System.out.println(page);
//        System.out.println(size);
//        System.out.println(name);

        Sort sort = Sort.by("email").descending();
        PageRequest pageRequest = PageRequest.of(page,size,sort);
        return repo.findByNameContaining(name,pageRequest);
    }
    @GetMapping(value= "/paging/search")
    public Page<Contact> getCtSearch(@RequestParam int page, @RequestParam int size,  @RequestParam String query){

        Sort sort = Sort.by("email").descending();
        PageRequest pageRequest = PageRequest.of(page,size,sort);
        return repo.findByNameContainsOrPhoneContains(query,query,pageRequest);
    }

    // HTTP 1.1 POST /contacts
    // HTTP버전 메서드 리소스URL
    // : Request Line

    // Content-Type: application/json - 요청보낼 데이터 형식
    // Accept: */* - 서버로 부터 어떤 형식의 데이터를 받을지
    //         ex) image/jpeg, application/json, text/html, plain/text
    // : Request Header
    // : 서버에 부가적인 요청정보

    // {"name":"홍길동", "phone":"010-1234-5678", "email":"hong@gmail.com"}
    // :Request Body(요청 본문)

    // JSON: 문자열, 자바스크립트 객체 표기법
    // Client(브라우저) Request Header에 Content-Type: application/json
    // Request Body가 JSON 문자열이면

    // Server(스프링) @RequestBody를 메서드 매개변수에 넣어주면
    // JSON(문자열) -> Java객체로 변환

    // 백엔드 API 메서드 구조
//    1. 요청에 데이터에 대해서 검증
//       -> 잘못된 데이터, 충돌되는 데이터면 400, 409와 같은 응답코드 내보냄
//    2. 요청 데이터에서 대해서 조회를 하거나 생성/수정/삭제
//    3. 요청 데이터를 처리후 응답을 줌
    @PostMapping
    public ResponseEntity<Map<String, Object>> addContact(@RequestBody Contact contact) {
        // 클라이언트에서 넘어온 JSON이 객체로 잘 변환됐는지 확인
//        System.out.println(contact.getName());
//        System.out.println(contact.getPhone());
//        System.out.println(contact.getEmail());

        // 이메일 필수값 검증
        // 400: bad request
        if (contact.getEmail() == null || contact.getEmail().isEmpty()) {
            // 응답 객체 생성
            Map<String, Object> res = new HashMap<>();
            res.put("data", null);
            res.put("message", "[email] field is required");

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(res);
        }


        if (contact.getEmail() != null && map.get(contact.getEmail()) != null) {

            Map<String, Object> res = new HashMap<>();
            res.put("data", null);
            res.put("message", "동일한 정보가 이미 존재합니다.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
        }

        // 맵에 객체 추가
        // map.put(contact.getEmail(), contact);

        repo.save(contact);


        Optional<Contact> savedContact
                = repo.findById(contact.getEmail());

        //레코드가 존재 하는지 영부
        if (savedContact.isPresent()) {
            Map<String, Object> res = new HashMap<>();
            res.put("data", contact);
            res.put("message", "created");
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }
        return ResponseEntity.ok().build();
    }

//    DELETE /contacts/{email}
//            : Path(경로)Variable(변수)
//    DELETE /contacts/abc@naver.com

    @DeleteMapping(value = "/{email}")
//    @PathVariable("email") : 경로 문자열{email}과 변수명 String email이 동일하면 안 써도 된다.
    public ResponseEntity removeContact(@PathVariable("email") String email) {
        if (!repo.findById(email).isPresent()) { // 해당 키(key)의 데이터가 없으면 , pk값으로 레코드로 1건 조회해서 없으면
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
//      객체(리소스) 삭제
//      map.remove(email);
//      레코드(리소스-데이터베이스의 파일 일부분) 삭제
        System.out.println(email);
        repo.deleteById(email);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // PUT(전체 수정), PATCH(일부 수정)
    // PUT /hong@gmail.com
    // {"name" : "길동", "phone":"010...."}
    @PutMapping(value = "/{email}")
    public ResponseEntity modifyContact (@PathVariable String email, @RequestBody Contact contact) {
        // 1. 키 값으로 조회해옴
        Optional<Contact> findedContact = repo.findById(email);
        // 2. 해당 레코드가 있는지 확인
        if (!findedContact.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Contact toModifyContact = findedContact.get();
        // 3. 조회해온 레코드에 필드값을 수정
        // 매개변수에 name값이 있으면 수정
        if(contact.getName() != null && contact.getName().isEmpty()){
            toModifyContact.setName(contact.getName());
        }
        if(contact.getPhone() != null && contact.getPhone().isEmpty()){
            toModifyContact.setPhone(contact.getPhone());
        }

        repo.save(toModifyContact);

        //200 OK 처리
        return ResponseEntity.ok().build();
    }
}