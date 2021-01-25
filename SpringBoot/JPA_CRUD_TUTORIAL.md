# JPA를 사용하여 CRUD 구현해보기!

## 1. Dependency 추가 (pom.xml)

```java
<dependency>
 <groupId>org.springframework.boot</groupId> 
 <artifactId>spring-boot-starter-data-jpa</artifactId> 
</dependency>
<dependency>
 <groupId>com.h2database</groupId>
<artifactId>h2</artifactId>
</dependency>
```
<br>


## 2. Entity 클래스 생성 
- 테이블 생성 쿼리

```sql
CREATE TABLE IF NOT EXISTS TEST.MEMBER (
     MBR_NO BIGINT NOT NULL AUTO_INCREMENT,
      ID VARCHAR(200),
       NAME VARCHAR(200),
       PRIMARY KEY(MBR_NO) /*AUTO_INCREMENT 컬럼 단일 PK */ 
    );
```

```java
package jpastudy.demo.VO;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="member")
public class MemberVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mbrNo;
    private String id;
    private String name;

    @Builder
    public MemberVO(String id, String name)
    {
        this.id = id;
        this.name = name;
    }
}

```

1. `@Entity`가 붙은 클래스는 JPA가 관리하는 클래스
2. mbrNo 필드는 `@Id` 어노테이션을 사용하여 기본키(PK)로 지정함 
    - table 생성 시 해당 필드를 pk, AUTO_INCREMENT로 설정하였기 때문에 직접 할당 방식이 아닌 자동으로 생성 되도록 하기 위해 `@GeneratedValue`어노테이션을 사용
    - `GenerationType.IDENTITY`는 기본 키 생성을 데이터베이스에 위임하는 방식 
    - `@GeneratedValue`는 여러 strategy 가 있음( IDENTITY, SEQUENCE, TABLE, AUTO ) -> 해당 역할에 대해서는 추후 조사 해보기

<br>

## 3. Repository 클래스 생성
- JPA에서는 단순히 Repository 인터페이스를 생성 후 JpaRepository<Entity,기본키 타입>을 상속 받으면 (extends) 기본적인 CRUD가 자동으로 생성됨 
- 즉, 인터페이스 만들고 상속만 잘 해주면 기본적인 동작 테스트 까지 ~~쌉가능~~
- JPA 처리를 담당하는 4가지 Repository (T : Entity의 타입 클래스 , ID : PK값의 Type)   
    1. Repository<T,ID>
    2. CrudRepository<T,ID>
    3. PaginAndSortingRepository<T,ID>
    4. JpaRepository<T,ID> -> 가장 기본적인 Repository


```java
package jpastudy.demo.Repository;

import jpastudy.demo.VO.MemberVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<MemberVO,Long> {
    //findBy 뒤에 컬럼명을 붙여주면 이를 이용하여 검색이 가능함!
    public List<MemberVO> findById(String id);
    public List<MemberVO> findByName(String name);
    public List<MemberVO> findByNameLike(String keyword);

}
```

- `메소드 이름을 잘 조합하여 쿼리를 처리 할 수 있음`
    - AND : findByIdAndName 
    - OR : findByIdOrName
    - Is,Equals : findByNameIs,findByNameEquals
    - Between : findBySalBetween 
    - 등등등 자세한건 추후에 플젝 하면서 더 알아보자!!!!!!!!!!


 ## 4. Service 생성
```java

package jpastudy.demo.Service;

import jpastudy.demo.Repository.MemberRepository;
import jpastudy.demo.VO.MemberVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<MemberVO> findAll(){
        return memberRepository.findAll();
    }

    public Optional<MemberVO> findById(Long mbrNo){
        return memberRepository.findById(mbrNo);
    }

    public void deleteById(Long mbrNo){
        memberRepository.deleteById(mbrNo);
    }

    public MemberVO save(MemberVO memberVO){
        memberRepository.save(memberVO);
        return memberVO;
    }

    public void updateById(MemberVO memberVO){
        Optional<MemberVO> e = memberRepository.findById(memberVO.getMbrNo());
        if(e.isPresent()){
            e.get().setMbrNo(memberVO.getMbrNo());
            e.get().setId(memberVO.getId());
            e.get().setName(memberVO.getName());
            memberRepository.save(memberVO);
        }
    }
}

```

## 5. Controller 생성

```java
package jpastudy.demo.Controller;


import jpastudy.demo.Service.MemberService;
import jpastudy.demo.VO.MemberVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@AllArgsConstructor
public class JpaController {

    MemberService memberService;

    // 모든 회원 리스트 조회
    @GetMapping(value = "/member-list",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MemberVO>> getAllMembers(){
        List<MemberVO> member = memberService.findAll();
        return new ResponseEntity<List<MemberVO>>(member, HttpStatus.OK);
    }

    // 회원 번호로 한명의 회원 조회
    @GetMapping(value ="/member/{mbrNo}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMember(@PathVariable("mbrNo") Long mbrNo){
        Optional<MemberVO> member = memberService.findById(mbrNo);

        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    // 회원번호로 회원 삭제
    @DeleteMapping(value = "/member/{mbrNo}",
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Void> deleteMember(@PathVariable("mbrNo") Long mbrNo) {
        memberService.deleteById(mbrNo);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    // 회원번호로 회원 수정
    @PutMapping(value="/member/{mbrNo}"
            ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateMember(@RequestBody MemberVO memberVO){
        memberService.updateById(memberVO);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    //회원 입력
    @PostMapping(value = "/member")
    public ResponseEntity<MemberVO> save(@RequestBody MemberVO memberVO){
        return new ResponseEntity<MemberVO>(memberService.save(memberVO),HttpStatus.OK);
    }

}
```

## 6. propertices 설정 

```java 
server.port=8080

#H2 Database 설정
spring.datasource.url=jdbc:mariadb://localhost:3306/test?serverTimezone=UTC&characterEncoding=UTF8
spring.datasource.driverClassName=org.mariadb.jdbc.Driver
spring.datasource.username=
spring.datasource.password=

#Hibernate가 실행하는 모든 SQL문을  콘솔로 출력
spring.jpa.properties.hibernate.show_sql=true
#SQL문을 가독성 있게 출력
spring.jpa.properties.hibernate.format_sql=true
#SQL문 이외에 추가적인 정보를 출력
spring.jpa.properties.hibernate.use_sql_comments=true
#MariaDB InnoDB Dialect 설정
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect

#SQL문 중 물음표로 표기되는 BindParameter의 값를 출력
logging.level.org.hibernate.type.descriptor.sql=trace

```

## 7. TEST 
`Talend API Tester - Free Edition`을 활용하여 테스트 진행 

###  회원 입력 

 request body에 json 타입으로 등록할 회원의 아이디와 이름 입력하여 api 호출 
![](https://postfiles.pstatic.net/MjAyMTAxMjVfMjQw/MDAxNjExNTU4NzM4NjYw.nQWczK9Vsw8_z0hgN93sPbbbpAqjLQhhMNtOXUGDeCUg.XXngwbBPUlh4N8IIRoqHyCqS_In-Z1sbsmGO_API-mog.PNG.ahreum0412/image.png?type=w966)

콘솔창에 찍히는 쿼리 

![](https://postfiles.pstatic.net/MjAyMTAxMjVfMzAg/MDAxNjExNTU4OTk5MTEz.pxA69PeYJfWQfOiHw07XN_hXMfp6KDtK1ffw3e5h4UEg.J7c56wh0H-3_N-RgDpXXRyxI02inkyt0vYgi9ASHHGMg.PNG.ahreum0412/image.png?type=w966)

### 전체 회원 조회

- api 호출

![](https://postfiles.pstatic.net/MjAyMTAxMjVfMjQ2/MDAxNjExNTU5MDU4NzM5.3oowEL8C4uDIxoROG7mPrbVMPN4_C0vy8uHUhAGzy6og.4cM72GZQWErkl0sOT05NhyMJ0bFn8PRuBG6UzIFIDCgg.PNG.ahreum0412/image.png?type=w966)

- 콘솔창에 찍히는 쿼리

 ![](https://postfiles.pstatic.net/MjAyMTAxMjVfMTY3/MDAxNjExNTU5MDc0MjUx.YXbAfMIt5LMeTZR1OL4Wx6-pAr4tJ0En5zAlfWmastsg.vs0AM1pQLKgvI-f-l4AecX0xMKz2_KxAvzjst46Su88g.PNG.ahreum0412/image.png?type=w966)


### 1명의 회원 조회 (mbrNo가 1인 회원 조회 결과)
- 조회할 회원의 mbrNo를 추가하여 api 호출

![](https://postfiles.pstatic.net/MjAyMTAxMjVfODcg/MDAxNjExNTU5MTYwOTIx.EiRpCUQgcZsxidB8hgyBLkvxD46udLcjsmwFSVDP1bwg.lZTr1ENaermPq5E9YO0htuqn7qbYO4mWg2q-emq4SrEg.PNG.ahreum0412/image.png?type=w966)

- 콘솔창에 찍히는 쿼리

![](https://postfiles.pstatic.net/MjAyMTAxMjVfMTkg/MDAxNjExNTU5MTk2MTEz.rgYnVRGtG2Uh0aQHMqkq0eQVfCL2IuoOeDGwoyzo3oIg.h1xVa7J3k7b5_TvIL_q0g9GgiGYVM2s-Z-JdVpu0MAwg.PNG.ahreum0412/image.png?type=w966)

### 회원 정보 수정 

- 수정할 회원의 정보 request body에 담아 api 호출

![](https://postfiles.pstatic.net/MjAyMTAxMjVfMTY2/MDAxNjExNTU5MzIzNTM2.lNi7SuLrE0rqpfXiwtndmK7VBc0KbDsAs5o-GrZo7usg.218n-lcxdtfGdzCor7avRCfMB5jrmZmo9xnV71XENcgg.PNG.ahreum0412/image.png?type=w966)

- 콘솔창에 찍히는 쿼리

![](https://postfiles.pstatic.net/MjAyMTAxMjVfMTIx/MDAxNjExNTU5MzYzNDk4.fa7ZLATGKlM2MuJpGbC3G6FzLzB1_lkc23TUe_6E9LYg.znWx6ajKHNJ_mrLAevjGmQrXPEQb-Nhcm-NioilQM4og.PNG.ahreum0412/image.png?type=w966)

- db 데이터 확인 

![](https://postfiles.pstatic.net/MjAyMTAxMjVfMjIw/MDAxNjExNTU5MzQ4MDYw.bZJxKwir3lpl_7Hsk5pBJyYY12m5f7y5_ZDkILURgoIg.hn2T2WWF1jhPg03_7Nwi7iF1EzNxNvWRMBh6pE28XI0g.PNG.ahreum0412/image.png?type=w966)

### 회원 정보 삭제

- 삭제할 회원 mbrNo 추가하여 api 호출 

![](https://postfiles.pstatic.net/MjAyMTAxMjVfODUg/MDAxNjExNTU5NDEwNDQz.ThPaNMVA7wbe4zg29j30hAGx0zLa1_8fBwnThhGM0GMg.OEQqd4yPlxrC4_dHLJFgnhmGOsbEBzw1k9yY-SYalYwg.PNG.ahreum0412/image.png?type=w966)

- 콘솔창에 찍히는 쿼리

![](https://postfiles.pstatic.net/MjAyMTAxMjVfODQg/MDAxNjExNTU5NDI2NTM2.6uuLO_mmRM2WCNGMMiXRGBuPDkhTrJmbsze1QP-n58cg.6MO6cNV-s7RCXTfBOm1Sn5F_-zbZQbdYUiFRDWo0_wsg.PNG.ahreum0412/image.png?type=w966)

- db 데이터 확인

![](https://postfiles.pstatic.net/MjAyMTAxMjVfNTYg/MDAxNjExNTU5NDUxNzg5.LR81l_WZY1W_ATC52CCO4kkrBP4-ezzgPdGrNSEWndAg.IpTGfzaSbtmQbEVu-W-4880XAjVwzepyTG4LWAF3etcg.PNG.ahreum0412/image.png?type=w966)






## Reference 

- [갓대희님 블로그](https://goddaehee.tistory.com/209)를 참고하여 실습한 내용입니다.


## 마치며 

 해당 글은 jpa를 사용하여 아주 기본적인 CRUD 를 사용하는 방법에 대한 내용입니다. 추후에 jpa 를 이용하여 프로젝트를 진행 하면서 부족한 부분을 채워나갈 예정입니다.