# QueryDSL

## QuerySQL 이란?
- SQL,JPQL을 __코드로__ 작성할 수 있도록 도와주는 빌더 API

## QuerySQL을 사용해야 하는 이유
- SQL,JPQL 의 문제점 극복
    - 문제점 :
        - SQL,JPQL은 문자열이기 때문에 타입 체크가 불가능함
        - 컴파일 시점에 쿼리문의 오류를 발견 할 수 없음
        - 쿼리가 실행 되는 시점에만 오류 발견 가능 ! 

- QuerySQL 장점
    - 문자가 아닌 코드로 작성 
    - 컴파일 시점에 문법 오류 발견 가능        
    - 단순함
    - 동적 쿼리 사용시 장점 극대화! 


## QuerySQL Setting (maven)

- pom.xml - 의존성 및 플러그인 추가
```java 
    <!-- 의존성 추가 하는 부분에 추가 -->
    <dependency>
        <groupId>com.querydsl</groupId>
        <artifactId>querydsl-apt</artifactId>
        <version>${querydsl.version}</version>
        <scope>provided</scope>
    </dependency>

    <dependency>
        <groupId>com.querydsl</groupId>
        <artifactId>querydsl-jpa</artifactId>
        <version>${querydsl.version}</version>
    </dependency>


    <!-- 플러그인 추가하는 부분에 추가 -->
    <plugin>
        <groupId>com.mysema.maven</groupId>
        <artifactId>apt-maven-plugin</artifactId>
        <version>1.1.3</version>
        <executions>
            <execution>
                <goals>
                    <goal>process</goal>
                </goals>
                <configuration>
                    <outputDirectory>src/main/java/com/example/querydsl/generated-resource</outputDirectory>
                    <processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>
                </configuration>
            </execution>
        </executions>
    </plugin>
```
- `<outputDirectory>src/main/java/generated</outputDirectory>` 에는 QClass 생성 위치를 적어주면 됩니다!

- QuerydslConfig.java 추가
```java
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class QuerydslConfig {
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}

```
- 위의 설정으로 나의 프로젝트 어느곳에서든 `JPAQueryFactory`를 주입받아 Querydsl 사용 가능하게 된다(config class 말하는거임)


## 셋팅이 다 끝났으면 컴파일 합니다.
- 컴파일이 끝나면 프로젝트의 entity 만큼 QClass가 지정한 경로에 생성 되는 것을 확인 할 수 있습니다.

    <img src = "https://postfiles.pstatic.net/MjAyMTAzMTVfMjEz/MDAxNjE1NzkzOTE1Mjg0.AndkIGtP-FJuc74O_y3L30yu_DSLWLYGL47LkAHKd5wg.-OamWfbS4rOkdTIi-dDrkie9-Bk4OyRCqIFoe9xAIrsg.PNG.ahreum0412/image.png?type=w966" width="300px">

- 나 같은 경우는 컴파일 후 생성된 QClass 를 새로운 패키지를 생성하여 그 안에서 관리하였다.
- 특별한 이유는 없다. 단지 깔끔한 디렉토리 구조를 선호하기 때문이다.

    <img src = "https://postfiles.pstatic.net/MjAyMTAzMTVfNjgg/MDAxNjE1Nzk0ODYwNzg0.r0l8OdEbYRB52S0giPw7JMlOgMjKZ6AX99WcDhb2SGEg.aonNJYKJW0gM4X3iW2w7Vq1CpOny1X64kuRWbaZ4uA0g.PNG.ahreum0412/image.png?type=w966" width="300px">

---

## 기본적인 QuerySQL 사용법! 
1.  Entity 추가 
    ```java
        package com.example.querydsl.Entity;

        import lombok.Getter;
        import lombok.Setter;

        import javax.persistence.*;

        @Getter
        @Setter
        @Entity(name="member")
        public class Member {
            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private int id;

            private String name;

            @Column(name="phone_number")
            private String phoneNumber;

            private String address;
        }

    ```
2. repository 추가 
    ```java
    package com.example.querydsl.Repository;

    import com.example.querydsl.Entity.Member;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    @Repository
    public interface MemberRepo extends JpaRepository<Member,Integer> {
    }
    ```
3. 상속/구현 없이 Querydsl Repository 추가 
- __Queryds__ 만으로 Repository를 구성해보자! 

```java
    package com.example.querydsl.Repository;

    import com.example.querydsl.Entity.Member;

    import com.querydsl.jpa.impl.JPAQueryFactory;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.stereotype.Repository;
    import java.util.*;
    import static com.example.querydsl.QClass.QMember.member;

    @Repository
    @RequiredArgsConstructor
    @Slf4j
    public class MemberQueryRepo  {
        private final JPAQueryFactory queryFactory;
        
        public List<Member> findByName(String name){
            return queryFactory.selectFrom(member)
                    .where(member.name.contains(name))
                    .fetch();
        }
        
    }
```

- 이대로만 진행 했다면 사실 member 에는 에러가 나서는 안되는게 맞습니다.
- QClass를 컴파일 진행하면서 생성하였고 QMember 안의 static 으로 선언된 mebmer를 import 하여 사용하는 것이기 때문입니다. (아래의 사진 참고!)

    <img src = "https://postfiles.pstatic.net/MjAyMTAzMTVfMjAw/MDAxNjE1Nzk2MTM4MDE2.tulVTjJaSRbI9f6Gl0zZjJBhPaIKc-vIG5Lv9uf759Eg.j01x-CG13zQcl0C5sx_ztgZVACsHyIQtGv-I5wvmu00g.PNG.ahreum0412/image.png?type=w966" width="800px">

- 이런식으로 repository 를 생성 후 서비스단 혹은 테스트단에서 사용하면 된다. 


## 왜 QueryDSL이 동적 쿼리를 사용할 때 좋을까?


 ### Querydsl 을 사용하여 동적 쿼리를 해결 하는 방법

 1. BooleanBuilder
    - BooleanBuilder를 이용하여 조건들을 이어붙여 where문에 넣어주는 방식 
    - and(),or()등을 이용하여 조건 추가 가능!

    ```java
        public List<Member> searchMember1(String name,String phoneNumber){
        BooleanBuilder builder = new BooleanBuilder();
        if(name != null){
            builder.and(member.name.eq(name));
        }
        
        if(phoneNumber != null){
            builder.and(member.phoneNumber.eq(name));
        }
        return queryFactory
                .selectFrom(member)
                .where(builder)
                .fetch();
    }

    ```

 2. Where 다중 파라미터 사용 (BooleanExpression)
    ```java
    public List<Member> searchMember2(String name,String phoneNumber){
            return queryFactory
                    .selectFrom(member)
                    .where(nameEq(name),phoneNumberEq(phoneNumber))
                    .fetch();
        }
        
        private BooleanExpression nameEq(String name){
            return name !=null ? member.name.eq(name) : null;
        }

        private BooleanExpression phoneNumberEq(String phoneNumber){
            return phoneNumber !=null ? member.phoneNumber.eq(phoneNumber) : null;
        }
    ```

__참고로 where절에서 , 의 의미는 and 이다.__ 



## 마치며
 예제의 경우는 where절에 들어가는 데이터 값이 모두 String 데이터 타입이기 때문에 "엥? 이거때문에 이렇게 까지 쓴다고?" 하는 생각이 들 수 있을 수도 있다. 하지만 나의 경우 검색 api 하나로 한 테이블의 여러개의 컬럼 값을 비교해야 하는 상황이였다. 검색 키워드를 String 으로 선언 하였는데 아이디 관련 컬럼 부분의 값은 정수형이였고 날짜 관련 컬럼은 LocalDateTime 타입이였다. 헉!!!!!!!!!!!!!!!!!!!!!그럼 어떡해!!!!!!!!!!!!!!어떻게 한 쿼리로 이걸 다 끝내???????마이바티스 쓰면 안돼???????????????????하고 좌절 하다가 갓 구글을 통해 queryDSL을 알게 되었따. 그래서 이런 저런 삽질을 해본 후에 원하는 쿼리문을 콘솔창에서 확인 할 수 있었담..아래의 코드를 작성하기까지 오늘 하루의 절반을 다 쓴 느낌이다. 하지만 jpa,,,정말 알 수록 어렵고 알 수록 매력적인것 같다! 열씨미 공부해야지 오늘의 공부는 여기까지 

 ```java
@Repository
@RequiredArgsConstructor
@Slf4j
public class ReIdQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<ReId> findByKeyword(String keyword){

        return queryFactory.selectFrom(reId)
                .where((descriptionEq(keyword)
                .or(statusEq(keyword))
                .or(createdAtEq(keyword))
                .or(idEq(keyword))
                .or(siteIdEq(keyword))))
               .fetch();
    }

    private BooleanExpression descriptionEq(String keyword){
        return reId.description.eq(keyword);
    }

    private BooleanExpression statusEq(String keyword){
        return reId.status.eq(keyword);
    }

    private BooleanExpression createdAtEq(String keyword){
        try{
            LocalDateTime localDateTime = LocalDateTime.parse(keyword);
            return reId.created_at.eq(localDateTime);
        }catch (DateTimeParseException e){
            log.info("입력한 키워드는 데이트 타입이 아닙니다!");
            return null;
        }
    }

    private BooleanExpression idEq(String keyword){
        try{
            int parseInt = Integer.parseInt(keyword);
            return reId.id.eq(parseInt);
        }catch (NumberFormatException e){
            log.info("입력한 키워드는 정수형 타입이 아닙니다!");
            return null;
        }
    }

    private BooleanExpression siteIdEq(String keyword){
        try{
            int parseInt = Integer.parseInt(keyword);
            return reId.siteId.eq(parseInt);
        }catch (NumberFormatException e){
            log.info("입력한 키워드는 정수형이 아닙니다.");
            return null;
        }
    }

}

 ```


## Reference 
[조졸두님 블로그](https://jojoldu.tistory.com/372)
