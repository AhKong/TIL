# Entity Mapping 
- __JPA를 사용하는 데 가장 중요한 일은 엔티티와 테이블을 정확하게 매핑하는 것!__
- JPA 대표 어노테이션 
    - 객체와 테이블 매핑 : @Entity, @Table
    - 기본 키 매핑 : @Id
    - 필드와 컬럼 매핑 : @Column
    - 연관관계 매핑 : @ManyToOne, @JoinColumn


## @Entity

- JPA를 사용해서 테이블과 매핑할 클레스는 `@Entity` 어노테이션을 필수로 붙여야함 
- `@Entity`가 붙은 클래스는 JPA가 관리하는 것으로 해당 클래스를 엔티티라고 칭함

|속성|기능|기본값|
|------|-----------------|---|
|name|JPA에서 사용할 엔티티 이름을 지정함, 보통 기본 값인 클래스 이름을 사용함|설정하지 않으면 클래스 이름을 그대로 사용 ex)Member|


- 주의 사항
    - 기본 생성자는 필수
        - JPA가 엔티티 객체를 생성 할 때 기본 생성ㅈ다를 사용하기 때문에
    - final 클래스, enum, interface, inner 클래스에는 사용할 수 없음. 
    - 저장할 필드에 final을 사용하면 안됨.


## @Table
- 엔티티와 매핑할 테이블 지정
- 생략하면 매핑한 엔티티 이름을 테이블 이름으로 사용함

|속성|기능|기본값|
|------|-----------------|---|
|name | 매핑할 에티블 이름 | 엔티티 이름을 사용함 |
|catalog | catalog 기능이 있는 데이터베이스에서 catalog를 매핑함 ||
|schema|schema 기능이 있는 데이터베이스에서 schema를 매핑함||
|uniqueConstraints (DDL) | - DDL 생성 시에 유니크 제약조건을 만듬 <br> - 2개 이상의 복합 유니크 제약 조건도 만들 수 있음 <br> - 스키마 자동 생성 기능을 사용해서 DDL을 만들때만 사용하면 됨||


## 다양한 매핑 사용

- 요구사항
    1. 회원은 일반 회원과 관리자로 구분해야 한다
    2. 회원은 가입일과 수정일이 있어야 한다.
    3. 회원을 설명할 수 있는 필드가 있어야 한다. 이 필드는 길이 제한이 없다.

    ```java
    @Getter
    @Setter
    @Entity
    @Table(name="Member")
    public class Member {

        @Id
        @Column(name="ID")
        private String id;

        @Column(name="NAME")
        private String userName;

        private Integer age;

        @Enumerated(EnumType.STRING)
        private RoleType roleType;

        @Temporal(Temporal.TIMESTAMP)
        private Date createdDate;

        @Temporal(Temporal.TIMESTAMP)
        private Date lastModiriedDate;

        @Lob
        private String description;

    }

    public enum RoleType {
        ADMIN, USER
    }
    ```

    - 자바의 enum을 사용하기 위해선 `@Enumerated` 어노테이션으로 매핑해야함 
    - 자바의 날짜 타입은 `@Temporal` 어노테이션으로 매핑해야함.
    - `@Lob`을 사용하면 CLOB,BLOB 타입을 매핑 할 수 있음.



## 데이터베이스 스키마 자동 생성

- JPA는 데이터베이스 스키마를 자동으로 생성하는 기능을 지원함 
- 클래스의 매핑 정보를 확인하면 어떤 테이블에서 어떤 컬럼을 사용 하는 지 알 수있기 때문
- JPA는 매핑 정보와 데이터베이스 방언을 사용해서 데이터베이스 스키마를 생성

<br>

## DDL 생성 기능 생성

- 회원 이름은 필수로 입력되어야 하고, 10자를 초과하면 안 된다는 제약조건 추가
- 스키마 자동 생성하기를 통해 만들어진 DDL에 위의 제약 조건 추가 
```java
    @Entity
    @Table(name="Member")
    public class Member {

        @Id
        @Column(name="ID")
        private String id;

        @Column(name="NAME", nullable = false, length = 10)
        private String userName;

        ...
    }
```
- @Column 매핑 정보의 nullable 속성을 false 로 지정하면 자동으로 생성되는 DDL에 not null  제약조건 추가 가능 
- length 속성 값을 사용하면 자동으로 생성되는 DDL에 문자의 크기를 지정 할 수 있음.

- 유니크 제약 조건 만들어보기! 
```java
    @Entity
    @Table(name="Member", uniqueConstraints = {@UniqueConstraint(
        name = "NAME_AGE_UNIQUE",
        columnNames = {"NAME,AGE"} )})
    public class Member {

        @Id
        @Column(name="ID")
        private String id;

        @Column(name="NAME", nullable = false, length = 10)
        private String userName;

        ...
    }
```
- 위의 모든 기능들은 DDL을 자동 생 할 때만 사용되거 JPA의 실행 로직에는 영향을 주지 않음. 
- 따라서 스키마 자동기능을  사용하지 않을거라면 위의 과정은 생략해도 무방


## 기본키 매핑 사용
- `@Id` 어노테이션을 사용하여 기본키 매핑
- 기본키를 직접 할당 하는 것이 아닌 데이터베이스가 생성해 주는 값 (ex 오라클 - 시퀀스, MySQL - AUTO_INCREMENT)을 사용하려면?
    - 데이터베이스마다 기본 키를 생성하는 방식이 서로 다르기 때문에 이 문제를 해결하기 쉽지 않음
    - JPA는 어떻게 이걸 해결 했을까 ?
    - JPA가 제공하는 데이터베이스 기본키 생성 전략
    ```
    직접 할당 : 기본 키를 애플리케이션에서 직접 할당 
    
    자동 생성 : 대리키 사용 방식
        - IDENTITY : 기본 키 생성을 데이터베이스에 위임
        - SEQUENCE : 데이터베이스의 시퀀스를 사용하여 기본 키를 할당
        - TABLE : 키 생성 테이블을 사용 
    ```

### 기본키 직접 할당 전략 

- 기본 키를 직접 할당하려면 @Id 어노테이션을 사용해서 매핑 하면 됨
- em.persist()로 엔티티를 저장하기 전에 애플리케이션에서 기본키를 직접 할당하는 방법 
- @Id 적용 가능한 자바 타입
    - 자바 기본형 
    - 자바 래퍼(Wrapper)형 


### IDENTITY 전략
- 기본 키 생성을 데이터베이스에 위임 하는 전략 
- `@GeneratedValues` 어노테이션을 사용하여  IDENTITY 전략 적용 
- 데이터베이스에 값을 저장하고 나서야 기본 키 값을 구할 수 있을 때 사용 

```java
@Entity 
public class Board{
    @Id
    @GeneratedValues(strategy = GenerationType.IDENTITY)
    private Long id;
    ...
}

Board board = new Board();
em.persist(board);

Long addId = board.getId(); // 1 
    
```


### SEQUENCE 전략
- 데이터베이스 시퀀스는 유일한 값을 순서대로 생성하는 특별한 데이터베이스 오브젝트 
- SEQUENCE 전략은 이런 시퀀스 기능을 사용해서 기본키를 생성 
- 시퀀스를 지원하는 데이터베이스에서만 사용 할 수 있음


### TABLE 전략
- 키 생성 전용 테이블을 하나 만들고 여기에 이름과 값으로 사용할 컬럼을 만들어 데이터베이스의 시퀀스를 흉내내는 전략
- 해당 전략은 테이블을 사용하기 때문에 모든 데이터베이스에 적용 할 수 있음   

### AUTO 전략
- 선택한 데이터베이스 방언에 따라 IDENTITY,SEQUENCE,TABLE 전략 중 하나를 자동으로 선택 
- 오라클을 선택하면 SEQUENCE , MySQL을 선택하면 IDENTITY를 사용함 
- 그렇기 때문에 데이터베이스를 변경해도 코드를 수정 할 필요가 없음 
- 키 생성 전략이 아직 확정되지 않은 개발 초기 단계나 프로토타입 개발 시 편리하게 사용 가능 
- 단,SEQUENCE나 TABLE 전략이 선택 되면 시퀀스나 키 생성용 테이블을 미리 만들어 두어야 함 
- 만약 스키마 자동 생성 기능을 사용한다면 하이버네이트가 기본 값을 사용하여 적절한 시퀀스나 키 생성용 테이블을 만들어 줌


## 필드와 컬럼 매핑 : 레퍼런스 


매핑 어노테이션|설명|
|-----------------|---|
|@Column| 컬럼을 매핑함|
|@Enumerated| 자바의 enum 타입을 매핑|
|@Temporal|날짜 타입을 매핑|
|@Lob|BLOB,CLOB 타입을 매핑|
|@Transient|특정 필드를 데이터베이스에 매핑하지 않음|
|@Access|JPA가 엔티티에 접근하는 방식을 지정|
