# Persistence Context (영속성 컨텍스트) In JPA 


## 엔티티 매니저 팩토리와 엔티티 매니저 
- JPA는 스레드가 하나생될 때마다(매 요청마다) EntityManagerFactory에서 EntityManager를 생성
- EntityManager는 내부적으로 DB 커넥션 풀을 사용해서 DB에 붙음


## 영속성 컨텍스트

- __엔티티를 영구 저장하는 환경__ 
- `EntityManager.persist(entity);`
    - 실제 DB에 저장하는것이 아니라 영속성 컨텍스트를 통해 엔티티를 영속화 한다는 의미
    - 정확하게 persist() 시점에는 영속성 컨텍스트에 저장하고 DB 저장은 그 이후임
- 엔티티 매니저? 영속성 컨텍스트 ?
    - 영속성 컨텍스트는 논리적인 개념이기 때문에 눈에 보이지 않는다.
    - 그렇기 때문에 엔티티 매니저를 통해 영속성 컨텍스트에 접근해야 한다. 
    - 스프링에서 EntityManager를 주입 받아서 쓰면, 같은 트랜잭션의 범위에 있는 EntitiyManager는 동일 영속성 컨텍스트에 접근한다.


## 엔티티의 생명주기


![](https://media.vlpt.us/images/neptunes032/post/ecd3b113-862f-4158-a208-e1eeec92d61d/image.png)

1. 비영속(new/transient)
    - 엔티티 객체를 생성해지만 아직 영속성 컨텍스트에 저장하지 않은 상태
    ```java
     Member member = new Member();
    ```

2. 영속(managed)
    - 엔티티 매니저를 통해서 엔티티를 영속성 컨텍스트에 저장한 상태
    - 영속성 컨텍스트에 의해 관리 됨 
    - __이때 DB에 저장 되지 않고 영속성 컨텍스트에 저장이 된거야 DB에 insert쿼리 안날라감!!!__
    - 트랜잭션의 커밋 시점에서 영속성 컨텍스트에 있는 정보들이 DB 쿼리로 날라감!
    ```java
    em.persist();
    ```
    - Data JPA 를 사용하는 경우 JPARepository의 save()를 호출하여 영속성 컨텍스트에 저장 ~~(여기서 하고싶은 말은 jpa의 proxy를 정리하면서 추가적으로 다루겠음)~~


3. 준영속(detached)
    - 영속성 컨텍스트가 관리하던 영속 상태의 엔티티를 더이상 관리하지 않으면 준영속 상태가 됨 
    - 특정 엔티티를 준영속 상태로 만들려면 `em.datach()`를 호출하자! 
    ```java
    em.datach(member); // 엔티티를 영속성 컨텍스트에서 분리해 준영속 상태로 만듬
    em.clear(); // 영속성 컨텍스트를 비워도 관리되던 엔티티는 준영속 상태가 됨
    em.close(); // 영속성 컨텍스트를 종료해도 엔티티는 준영속 상태가 됨.
    ```
    - 준영속 상태의 특징
        - 1차 캐시,쓰기 지연, 변경 감지, 지연 로딩을 포함한 영속성 컨텍스트가 제공하는 어떠한 기능도 동작하지 않음
        - 식별자 값을 가지고 있다.

4. 삭제 (Removed)
    - 엔티티를 영속성 컨텍스트와 데이터베이스에서 삭제
    ```java
    em.remove(member);

    ```

## 영속성 컨텍스트 그래서 뭐가 좋은걸까?????

### 1. 1차 캐시

- 영속성 컨텍스트(엔티티 매니저)에는 내부에 1차 캐시가 존재
- 엔티티를 영속성 컨텍스트에 저장하는 순간 1차 캐시에 key : @Id로 선언한 필드 값 ,value : 해당 엔티티 자체로 캐시에 저장이 됨
- 1차 캐시의 장점
    - 조회 할 때 이점이 생김
    - find()가 호출 되는 순간, 엔티티 매니저 내부의 1차 캐시를 먼저 찾음 
    - 1차 캐시에 엔티티가 존재하면 바로 반환 즉, DB에서 조회하지 않음! 
- __1차 캐시는 글로벌 하지 않다. 해당 스레드 하나가 시작 할 때 부터 끝날 때 까지 잠깐 사용하는 것일 뿐 공유하지 않는 캐시__
    - 100명에게 100개의 요청이 온다면, 엔티티 매니저&1차 캐시 전부 100개 생기고 스레드가 종료되면 그때 다 사라짐
    - 트랜잭션의 범위 안에서만 사용하는 굉장히 짧은 캐시 레이어
    - 전체에서 쓰는 클로벌 캐시는 2차 캐시라고함 

```java
Member member = new Member();
member.setId("mId");
member.setUserName("mName");

em.persist(); // 1차 캐시에 저장

Member findMember = em.find(Member.class,"mId"); // 1차 캐시에서 조회 
```    

- 1차 캐시에 데이터가 없다면? 그땐 당연히 데이터베이스에서 조회 !
    - member2를 조회하는데 1차 캐시에 해당 엔티티가 없음
    - db에서 데이터 조회 후 1차 캐시에 저장 
    - member2 반환
    - 다시 member2를 조회하면, 1차 캐시에 있는 member2가 반환
        - 즉, select 쿼리를 db에 날리지 않음 


### 2. 동일성(identity) 보장
- 영속 엔티티의 동일성을 보장함
- 1차 캐시 덕분에 mId를 몇번을 조회 해도 다른 객체가 아님
- 1차 캐시로 반복 가능한 읽기 (REPEATABLE READ) 등급의 트랜잭션 격리 수준을 데이터베이스가 아닌 애플리케이션 차원에서 제공 
```java

Member a = em.find(Member.class, "mId");
Member b = em.find(Member.class, "mId");
​
Systen.out.println(a == b); // 동일성 비교 true

```


### 3. 트랜잭션을 지원하는 쓰기 지연(transactional wirte-behind) - 엔티티 등록
![](https://github.com/namjunemy/TIL/blob/master/Jpa/inflearn/img/04_transactional_write_behind.PNG?raw=true)
- 위에서도 언급 하였지만, em.()메서드를 사용하여 엔티티를 저장하여도 db에 insert 쿼리문을 날리지 않음
- 엔티티 매니저는 트랜잭션을 커밋하기 직전까지 내부 쿼리 저장소에 insert sql문을 모아둠
- 그리고 트랜잭션이 커밋 할 때 모다운 쿼리를 db에 보냄
- 이러한 과정을 `트랜잭션을 지원하는 쓰기 지연`이라고 함.
```java
EntityManager em = emf.createEntityManager();
EntityTransaction transaction = em.getTransaction();
// 엔티티 매니저는 데이터 변경시 트랜잭션을 시작해야 한다.
transaction.begin(); // 트랜잭션 시작
​
em.persist(memberA);
em.persist(memberB);
// 이때까지 INSERT SQL을 데이터베이스에 보내지 않는다.
​
// 커밋하는 순간 데이터베이스에 INSERT SQL을 보낸다.
transaction.commit(); // 트랜잭션 커밋

```
- 메서드,클래스,인터페이스 위에 `@Transactional` 어노테이션을 사용하면 위와 같은 트랜잭션 처리를 알아서 해준다.

### 4. 변경 감지(Dirty Checking) - 엔티티 수정
- 엔티티 필드 값의 수정이 일어나면 update()나 persist()로 영속성 컨텍스트에게 알려주지 않아도 알아서 update가 된다.
```java 
EntityManager em = emf.createEntityManager();
EntityTransaction transaction = em.getTransaction();
transaction.begin(); // 트랜잭션 시작
​
// 영속 엔티티 조회
Member memberA = em.find(Member.class, "memberA");
​
// 영속 엔티티 수정
memberA.setUsername("nj");
memberA.setAge(27);
​
//em.update(member) 
//em.persist(member)로 다시 저장하지 않아도 됨!!!
​
transaction.commit(); // 트랜잭션 커밋

```
- 어떻게 이게 가능하지????
    - 변경 감지를 __Dirty Checking__ 라고 한다
    - 1차 캐시에 저장할 때 동시에 스냅샷 필드도 저장을 한다
    - 그러고 난 후 commit() or flush()가 일어날 때 __엔티티와 스냅샷을 비교__ 변경 사항이 있으면 update sql을 알아서 만들어 db에 저장한다 
- 그냥 update() 를 만들면 되지 왜 이런 방법으로 구현하였을까?
    - 우리는 자바 컬렉션 혹은 리스트의 값을 변경하고 다시 그 값을 담지 않는다.
    - 값을 변경하면 변경된 리스트가 유지되는 것과 같은 컨셉이다...~~오...대박~~
- 따라서, 영속상태의 엔티티를 가져와서 값만 바꾸면 수정은 아주 스무스하게 끝나버린다.
- 엔티티 수정시 기본적으로 전체 필드 다 업데이트 혹은 변경된 필드만 반영 되도록 할 수 도 있다. 
- 엔티티 클래스 위에 @DynamicUpdate을 추가해주면 가능 !     


## flush 
- 플러시는 영속성 컨텍스트의 변경 내용을 데이터베이스에 반영
- 트랜잭션 커밋이 일어날 때 플러시가 동작하는데, 쓰기 지연 저장소에 쌓아 놨던 insert,update,delete sql 들이 데이터베이스에 날아가게 됨
- 즉, 영속성 컨텍스트의 변경 사항들과 데이터베이스의 데이터 간의 싱크를 맞추는 작업!

## flush 발생하게 되면 어떤 일이 생길까?
- 변경을 감지함 -> Dirty Checking
- 수정된 엔티티를 쓰기 지연 SQL 저장소에 등록
- 쓰기 지연 SQL 저장소의 쿼리를 데이터베이스에 전송 (등록,수정,삭제 SQL)
- 플러시가 발생한다고 해서 커밋을 무조건 하는게 아니라 그냥 순서가 플러시 다음이 커밋일 뿐!

## 영속성 컨텍스트를 flush 하는 방법

- em.flush()로 직접 호출 
- 트랜잭션 커밋시 플러시 자동 호출
- JPQL 쿼리 실행하면 플러시 자동 호출
    - 왜 JPQL 쿼리를 실행하면 플러시가 자동으로 호출 되어야 할까
        - em.persist()로 영속화 한 후 쿼리가 안날라감
        - JPQL로 select 쿼리를 날릴려고 하면 저장되어있는 값이 없어서 문제가 생길 수 있음
        - JPA는 이런 상황을 방지하고자 JPQL 실행 전에 무조건 flush로 DB와의 싱크를 맞춘 다음에 JPQL 쿼리를 날리도록 설정 되어있음 
        ```java
            em.persist(memberA);
            em.persist(memberA);
            em.persist(memberA);
            ​
            // 중간에 JPQL 실행
            query = em.createQuery("select m from Member m", Member.class);
            List<Member> members = query.getResultList();
        ```
        - 그래서 위와 같은 상황에서 데이터 조회가 가능하다! 
 - __플러시가 일어난다고 해도 1차 캐시는 삭제되지 않음__  쓰기 지연 SQL 저장소에 있는 쿼리들만 DB에 전송 될뿐 1차 캐시는 남아있음 


 ## 플러시 총 정리
 - 플러시는 영속성 컨텍스트를 비우는것이 아니다.
 - 플러시는 영속성 컨텍스트의 변경 내용을 데이터베이스에 동기화 한다.
 - 플러시가 동작할 수 있는 이유는 데이터베이스의 트랜잭션이라는 작업단위가 있기 때문이다.
    - 트랜잭션이 시작되고 커밋되는 시점에만 동기화 해주면 되기 때문에, 그 사이에서 플러시 매커니즘의 동작이 가능한 것!! 
 - JPA는 기본적으로 데이터를 맞추거나 동시성에 관련된 것들을 데이터베이스 트랜젹선에 위임한다. 



## 준영속 상택 
- 영속상태의 엔티티가 영속성 컨텍스트에서 분리된 상태 (detached)
    - em.detached(member); 로 영속성 컨텍스트에서 분리
    - 트랜잭션 커밋하면 아무일도 일어나지 않음 즉 ,JPA가 관리하지 않은 객체가 됨!
    - 실제로 아래으 ㅣ코드에서는 update가 발생하지 않음! 
    ``` java
    Member member = em.find(Member.class, 150L);
    member.setName("AAAAA");
    ​
    em.detach(member);
    ​
    transaction.commit();
    ```          
    - 즉, 준영속 상태에서는 컨텍스트가 제공하는 모든 기능을 사용할 수 없다.


## Reference 

https://ict-nroo.tistory.com/130
