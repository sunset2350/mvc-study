-- 해당 스키마(데이터베이스)를 사용
-- 데이터베이스 
-- : 데이터베이스 객체를 사용할 수 있는 공간
-- : 객체 - 테이블, 뷰, 인덱스, 저장프로시저 ...


/*
데이터베이스 엔진 : MySQL
데이터베이스(스키마) : myapp
CREATE SCHEMA myapp;
CREATE DATABASE myapp;
MySQL에서는 스키마와 데이터베이스가 동일한 개념이다.
*/

-- create table 테이블 컬럼 데이터조건 
use myapp;
create table `contact`(
	`email` varchar(255) not null,
    `image` varchar(255),
    `name` varchar(255),
    `phone` varchar(255),
    primary key(email)
) engine=InnoDB;
/*
primary Key 제약 조건
1. 테이블내의 데이터 중에서 같은 값이 중복이 있으면 안 됨
2. null 값이 될 수 없음
*/
-- insert into 테이블 values(값 목록...);  -> 순서가 중요
insert into contact (name,phone,email,image)
value("johno doe", "010-1234-5678", "john11@naver.com", null);
select * from contact;

select length('홍');
select length('a');

-- 데이터베이스의 PK(index, clustered)
-- clustered index에 맞게 데이터가 정렬 되어있음
-- index(binaray tree)고지옥, 데이터(linked list) 구조
-- index(목차) 트리를 탐색하여 데이터까지 찾아감
select * from contact;
delete from contact where email='john@naver.com';
select * from contact;
-- truncate는 transaction 로그를 쌓지 않음(복구 불가)
truncate table contact;


    -- batch insert
INSERT INTO contact (email, name, phone)
VALUES
  ('email1@example.com', 'Name1', '010-1111-1111'),
  ('email2@example.com', 'Name2', '010-2222-2222'),
  ('email3@example.com', 'Name3', '010-3333-3333'),
  ('email4@example.com', 'Name4', '010-4444-4444'),
  ('email5@example.com', 'Name5', '010-5555-5555'),
  ('email6@example.com', 'Name6', '010-6666-6666'),
  ('email7@example.com', 'Name7', '010-7777-7777'),
  ('email8@example.com', 'Name8', '010-8888-8888'),
  ('email9@example.com', 'Name9', '010-9999-9999'),
  ('email10@example.com', 'Name10', '010-1010-1010'),
  ('email11@example.com', 'Name11', '010-1111-1111'),
  ('email12@example.com', 'Name12', '010-1212-1212'),
  ('email13@example.com', 'Name13', '010-1313-1313'),
  ('email14@example.com', 'Name14', '010-1414-1414'),
  ('email15@example.com', 'Name15', '010-1515-1515'),
  ('email16@example.com', 'Name16', '010-1616-1616'),
  ('email17@example.com', 'Name17', '010-1717-1717'),
  ('email18@example.com', 'Name18', '010-1818-1818'),
  ('email19@example.com', 'Name19', '010-1919-1919'),
  ('email20@example.com', 'Name20', '010-2020-2020'),
  ('email21@example.com', 'Name21', '010-2121-2121'),
  ('email22@example.com', 'Name22', '010-2222-2222'),
  ('email23@example.com', 'Name23', '010-2323-2323'),
  ('email24@example.com', 'Name24', '010-2424-2424'),
  ('email25@example.com', 'Name25', '010-2525-2525'),
  ('email26@example.com', 'Name26', '010-2626-2626'),
  ('email27@example.com', 'Name27', '010-2727-2727'),
  ('email28@example.com', 'Name28', '010-2828-2828'),
  ('email29@example.com', 'Name29', '010-2929-2929'),
  ('email30@example.com', 'Name30', '010-3030-3030');
    
    
select * from contact;

-- 조건 검색(equal)
select * from contact where name = 'Name9';
select count(*) from contact where name = 'Name9';

-- 조건 검색(contain)
select * from contact where name like 'Name1%';

select c1_0.email,c1_0.image,c1_0.name,c1_0.phone from contact c1_0 where 
c1_0.name like ? escape '\\' order by c1_0.email desc limit ?,?
