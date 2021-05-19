# 연관관계 매핑 기초 

- 엔티티들은 대부분 다른 엔티티와 연관관계를 가지고 있다.
- ex) 주문 엔티티는 어떤 상품을 주문했는지를 알기 위해 상품 엔티티와 연관관계가 있고 상품엔티티는 카테고리,제고 등 또 다른 엔티티와 관계가 있다.
- 객체는 참조를 사용해서 관계를 맺고 테이블은 외래 키를 사용해서 관계를 맺는다. 
- 둘은 완전히 다른 특징을 가지고 있기 때문에 ORM(객체 관계 매핑)에서 가장 어려운 부분이 바로 객체 연관관계와 테이블 연관관계를 매핑하는 일이다.
- __객체의 참조와 테이블의 외래 키를 매핑하는 것이 오늘 내용의 목표__
<br>
<br>

### 연관관계 핵심 키워드 
- 방향 :
    - 단방향, 양방향이 있음
    - 단방향 관계 : 회원->팀 or 팀 -> 회원  ==> 둘 중 한쪽만 참조하는 것 
    - 양방향 관계 : 회원 ->팀 and 팀 -> 회원  ==> 양쪽 모두 서로를 참조하는 것 
    - 방향은 객체 관계에만 존재하고 테이블 관계는 항상 양방향

- 다중성 :
    - 다대일(N:1), 일대다(1:N) , 일대일 (1:1) 다대다(M:N)
    - 회원과 팀이 관계가 있을 때 여려 회원은 한 팀에 속하기 때문에 회원과 팀의 관계는 다대일 관계
    - 반대로 한 팀에 여러 회원이 소속될 수 있으므로 팀과 회원의 관계는 일대다 관계

 - 연관관계의 주인 : 
    - 객체를 양방향 연관관계로 만들면 연관관계 주인을 정해야 함



## 1. 단방향 연관관계 

- 연관관계 중에선 다대일(N:1) 연관관계를 가장 먼저 이해야함 
- EX) 회원과 팀의 관계를 통한 다대일 단방향 관계 
    - 회원과 팀이 있다.
    - 회원은 하나의 팀에만 소속될 수 있다.
    - 회원과 팀은 다대일 관계다.

    ![](https://images.velog.io/images/ljinsk3/post/f020e35d-4b20-420b-9013-d677956ce257/image.png)

    - 객체 연관관계 
        - 회원 객체는 Member.team 필드로 팀 객체와 연관관계를 맺게 됨
        - 회원 객체와 팀 객체는 __단방향 관계__
        - 회원은 Member.team 필드를 통해서 팀을 알 수 있지만 반대로 팀은 회원은 알 수 없음. 

    - 테이블 연관관계
        - 회원 테이블은 TEAM_ID 외래 키로 팀 테이블과 연관관계를 맺음
        - 회원 테이블과 팀 테이블은 __양뱡향 관계__
        - 회원 테이블의 TEAM_ID 외래키를 통해서 회원과 팀을 조인 할 수 있고 반대로 팀과 회원도 조인할 수 있기 때문이다
        
        ```sql
        SELECT
            * 
        FROM 
            MEMBER
        INNER JOIN 
            TEAM 
        ON 
            TEAM.ID = MEMBER.TEAM_ID; 

              
        SELECT
            * 
        FROM 
            TEAM
        INNER JOIN 
            MEMBER
        ON 
            TEAM.ID = MEMBER.TEAM_ID;            
        ```    

        - 위의 쿼리문을 실행하면 동일한 내용이 조회가 됨. 

     - 객체 연관관계와 테이블 연관관계의 가장 큰 차이 
        - 참조를 통한 연관관계는 언제나 단방향
        - 객체간의 연관관계를 양방향으로 만들고 싶다면 반대쪽에도 필드를 추가해서 참조를 보관해야함
        - 이렇게 양쪽에서 참조하는것을 양방향 연관관계라고 함 
        - 하지만 정확하게 이야기하면 이것은 양방향 관계가 아니라 서로 다른 단방향 관계가 2개인것뿐
        ``` java
        class A {
            B b;
        }

        class B {
            A a;
        }

        ```
        - 반면에 테이블은 외래키 하나로 양방향으로 조인 할 수 있음 . 


     - 객체 관계 vs 테이블 연관관계 정리 
        - 객체는 참조(주소)로 연관관계를 맺는다.
        - 테이블은 외래키로 연관관계를 맺는다. 


### 1.1  순수한 객체 연관관계
```java
public class Member{
    private Stirng id;
    private String username;

    private Team team;

    public void setTeam(Team team){
        this.team = team;
    }

    //Getter, Setter ... 
}

public class Team{
    private String id;
    private String name;

    //Getter, Setter ...
}

Member member1 = new Member("member1","김아름");
Member member2 = new Member("member2","김나무");

Team team = new Team("team1","행복한팀");

member1.setTeam(team);
member2.setTeam(team);

```

- 위의 코드를 그림으로 표현하면 다음과 같음 

![](https://postfiles.pstatic.net/MjAyMTA1MTlfMjY5/MDAxNjIxNDA0MTU1NTAz.82qwvVOeLAAKNTsjnoUdUclP2EgUp6eWhr_uG1X5ltIg.gFy2r6B7N2v1TSzgQ7-Nphus7JkwrXLJDGdUgFb7VOUg.PNG.ahreum0412/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2021-05-19_%EC%98%A4%ED%9B%84_3.02.13.png?type=w966)       

-  아래의 코드로 회원1이 속한 팀1을 조회 할 수 있음
```java
Team findTeam = member1.getTeam();
```
- 이처럼 객체는 참조를 사용해서 연관관계를 탐색 할 수 있고 이것을 __객체 그래프 탐색__ 이라고 함!

<br>

### 1.2 테이블 연관관계

- 데이터베이스는 외래키를 사용해셔 연관관계를 탐색할 수 있고 이것을 __조인__ 이라고 함
- 이에 해당하는 내용은 이하 생략.

<br>

### 1.3 객체 관계 매핑 

 ![](https://images.velog.io/images/ljinsk3/post/f020e35d-4b20-420b-9013-d677956ce257/image.png)

- 매핑한 회원 엔티티
    ```java
    @Entiy
    public class Member{
        @Id
        @Column(name ="MEMBER_ID")

        private String username;

        //연관관계 매핑
        @ManyToOne
        @JoinColumn(name="TEAM_ID")
        private Team team;

        //연관관계 설정
        public void setTeam(Team team){
            this.team = team; 
        }
    }
    ```
- 매핑한 팀 엔티티
    ```java
    @Entiy
    public class Team{
        @Id
        @Column(name="TEAM_ID")
        private String id;

        private String name;
        //Getter, Setter ...
    }
    ```
    - @ManyToOne 
        - 다대일 관계라는 매핑 정보
        - 연관관계를 매핑할 때 이렇게 다중성을 나타내는 어노테이션을 필수로 사용해야 함 
        - @OneToMany,@ManyToMany,@OneToOne도 있음.

    - @JoinColumn(name="TEAM_ID")
        - 외래 키를 매핑할 때 사용함 
        - name 속성에는 매핑할 외래 키 이름을 지정
        - 회원과 팀은 TEAM_ID 외래키로 연관관계를 맺기 때문에 name에 이 값을 지정하면 됨 
        - 생략 가능     
    <br>

 
 ## 2. 양방향 연관관계
- 팀에서 회원으로 접근하는 관계 추가
- 회원에서 팀으로 접근하고 반대 방향인 팀에서도 회원으로 접근 할 수 있음 
![](https://media.vlpt.us/images/syleemk/post/51d4ed0c-24a4-4735-9ad0-e34b4370587b/image.png)

- 객체 연관관계
    - 회원과 팀은 다대일 관계
    - 반대로 팀에서 회원은 일대다 관계
    - 일대다 관계는 여러 건과 연관관계를 맺을 수 있기 때문에 컬렉션을 사용해야함
    - Team.members 를 List 컬렉션으로 추가
    - 회원 -> 팀 (Member.team)
    - 팀 -> 회원 (Team.members)
    - 참고 : JPA는 List를 포함해서 Collection,Set,Map 같은 다양한 컬렉션을 지원함 

- 테이블 연관관계 
    - 테이블은 외래 키 하나로 양방향 조회 가능 
    - 원래부터 양방향으로 조회가 가능하기 때문에 달라진점 없음!



### 2.1 양방향 연관관계 매핑

- 매핑한 회원 엔티티 (변경사항 없음)
    ```java
        @Entiy
    public class Member{
        @Id
        @Column(name ="MEMBER_ID")

        private String username;

        //연관관계 매핑
        @ManyToOne
        @JoinColumn(name="TEAM_ID")
        private Team team;

        //연관관계 설정
        public void setTeam(Team team){
            this.team = team; 
        }
    }
    ```

- 매핑한 팀 엔티티
    ```java
    @Entiy
    public class Team{
        @Id
        @Column(name="TEAM_ID")
        private String id;

        private String name;
        // 추가된 부분 
        @OneToMany(mappedBy ="team")
        private List<Member> members = new ArrayList<Member>();
        //Getter, Setter ...
    }
    ```
    - 팀과 회원은 일대다 관계이기 때문에 팀 엔티티에 컬렉션인 List<Member> members를 추가하였음
    - 일대다 관계를 매핑하기 위해 @OneToMany 매핑 정보를 사용
    - mappedBy 속성은 양방향 매핑일 때 사용하는데 반대쪽 매핑의 필드 이름을 값으로 주면 됨

 ### 2.2 일대다 컬렉션 조회
 - 팀에서 회원 컬렉션으로 객체 그래프 탐색을 사용하여 조회한 회원들을 출력 

```java
Team team = en.find(Team.class,"team1");
List<Member> members = team.getMembers();

for(Member member : members){
    System.out.println("Member.username : "+member.getUsername());
}
```
   

## 3. 연관관계의 주인 
- @OneToMany만 있으면 되지 mappedBy는 왜 필요할까?
- 객체에는 양방향 연관관계라는것이 없음 
    - 서로 다른 단방향 연관관계 2개를 애플리케이션 로직으로 잘 묶어서 양방향인것 처럼 보이게 할 뿐 
    - 회원 -> 팀 (단방향)
    - 팀 -> 회원 (단방향)
- 하지만 테이블 연관관계는 회원 <-> 팀 (양방향) 
- 그렇기 때문에 테이블은 외래 키 하나로 두 테이블의 연관관계를 관리
- 엔티티를 단방향으로 매핑하면 참조를 하나만 사용하므로 이 참조로 외래키를 관리하면 됨
- 그런데 엔티티를 양방향으로 매핑하면 회원->팀, 팀->회원 두 곳에서 서로를 참조
- 따라서 객체의 연관관계를 관리하는 포인트가 2곳으로 늘어남
- `엔티티를 양방향 연관관계로 설정하면 객체의 참조는 둘인데 외래키는 하나`
- `따라서 둘 사이에 차이가 발생하게 됨`
- 그럼 둘 중 어떤 관계를 사용해서 외래키를 관리해야 할까?
- 이런 차이로 인해  `JPA는 두 객체 연관관계 중 하나를 정해서 테이블의 외래키를 관리해야하는데 이것을 연관관계의 주인 이라함`


### 3.1 양방향 매핑의 규칙 : 연관관계의 주인
- 양방향 연관관계 매핑시 두 연관관계 중 하나를 연관관계의 주인으로 정해야함
- 연관관계의 주인만이 데이터베이스 연관관계와 매핑되고 외래키를 관리(등록,수정,삭제) 할 수 있음
- 반면에 주인이 아닌쪽은 읽기만을 할 수 있음
- 어떤 연관관계를 주인을 정할지는 `mappedBy` 속성을 사용하면 됨!
- 주인은 mappedBy 속성을 사용하지 않음 
- 주인이 아니면 mappedBy 속성을 사용해서 속성의 값으로 연관관계 주인을 지정해야함 

### 3.2 연관관계의 주인은 외래키가 있는 곳 !
-  `연관관계의 주인은 테이블에 외래 키가 있는 곳으로 정해야 함.`
- 여기서는 회원 테이블에 외래 키를 가지고 있으므로 Member.team이 주인이 됨 
- 주인이 아닌 Team.members 에는 mappedBy="team" 속성을 사용해서 주인이 아님을 설정함.
![](https://media.vlpt.us/images/syleemk/post/68e80192-dfa1-4b78-aed1-c542e6608991/image.png)

- 정리하면 연관관계의 주인만 데이터베이스 연관관계와 매핑되고 외래키를 관리 할 수 있음 
- 주인이 아닌 반대편은 읽기만 가능하고 외래키를 변경하지는 못함 

### 참고
- 데이터베이스의 테이블의 다대일,일대다 관계에서는 항상 다 쪽이 외래키를 가짐
- 다 쪽인 @ManyToOne은 항상 연관관계의 주인이 되므로 mappedBy를 설정할 수 없음 
- 따라서 @ManyToOne 에는 mappedBy 속성이 존재하지 않음!

<br>

## 4. 양방향 연관관계의 주의점
- 양뱡향 연관관계를 설정하고 가장 흔히 하는 실수는 연관관계의 주인에는 값을 입력하지 않고 주인이 아닌 곳에만 값을 입력하는 것!
```java
Member member1 = new Member("member1","회원1");
em.persist(member1);

Member member2 = new Member("member2","회원2");
em.persist(member2);

Team team = new Team("team1","팀1");
//주인이 아닌 곳에만 연관관계 생성!
team.getMembers().add(member1);
team.getMembers().add(member2);

em.persist(team1);
```
- 위의 코드를 실행하고 데이터베이스에서 회원 테이블을 조회 한다면 아래와 같은 결과가 조회 됨

    MEMBER_ID|USERNAME|TEAM_ID|
    |------|---|---|
    |member1|회원1|null|
    |member2|회원2|null|    

- 연관관계의 주인이 아닌 Team.members에만 값을 저장했기 때문!
- 다시 한번 강조 하지만 연관관계의 주인만이 외래 키의 갑사을 변경 할 수 있음
- 예제 코드에서는 연관관계의 주인인 Member.team에 아무 값도 입력 하지 않았기 때문에 TEAM_ID 외래 키의 값도 null이 저장이 되는것임 

<br>

### 4.1 순수한 객체까지 고려한 양방향 연관관계
- 정말 연관관계의 주인에만 값을 저장하고 주인이 아닌 곳에는 값을 저장하지 않아도 될까?
- 사실은 `객체 관점에서 양쪽 방향에 모두 값을 입력해주는 것이 가장 안전!`
- 양쪽 방향 모두 값을 입력하지 않으면 JPA를 상요하지 않는 순수한 객체 상태에서 심각한 문제가 발생 할 수 있음.
- 예를 들어 JPA를 사용하지 않고 엔티티에 대한 테스트 코드를 작성한다고 가정 해보자

```java
Team team = new Team("team1", "팀1");
Member member1 = new Member("member1","회원1");
Member member2 = new Member("member2","회원2");

//연관관계 설정
member1.setTeam(team1);
member2.setTeam(team1);

List<Member> members = team.getMembers();
System.out.println("member.size : " +member.size()); // 0
```
- 양방향은 양쪽다 관계를 설정해야함! 이처럼 회원->팀을 설정하면 다음 코드처럼 반대 방향인 팀->회원도 설정해줘야함
```java
team1.getMembers().add(member1); // 팀->회원
```
- 양방향 모두 관계 설정 
```java
Team team = new Team("team1", "팀1");
Member member1 = new Member("member1","회원1");
Member member2 = new Member("member2","회원2");

//연관관계 설정
member1.setTeam(team1); //member1 -> team
team.getMembers().add(member1); // team -> member1

member2.setTeam(team1); //member2 -> team
team.getMembers().add(member2); //team -> member2

List<Member> members = team.getMembers();
System.out.println("member.size : " +member.size()); //2 
```
- 위의 코드는 양쪽 모두 관계를 설정했기 때문에 원하던 결과인 2가 출력됨
- 객체까지 고려하면 이렇게 양쪽 다 관계를 맺어야 함
- JPA로 코드 완성
```java

//팀1 저장
Team team = new Team("team1", "팀1");
em.persist(team);

Member member1 = new Member("member1","회원1");
Member member2 = new Member("member2","회원2");

//연관관계 설정
member1.setTeam(team1); //member1 -> team
team.getMembers().add(member1); // team -> member1
em.persist(member1);

member2.setTeam(team1); //member2 -> team
team.getMembers().add(member2); //team -> member2
em.persist(member2);
```
- 양쪽에 연관관계를 설정했기 때문에 순수한 객체 상태에서도 동작하고, 테이블의 외래키도 정상적으로 입력이 됨
- 물론 외래 키의 값은 연관관계의 주인인 Member.team의 값을 사용하게 됨!

<br>

### 4.2 연관관계 편의 메소드
- 양방향 연관관계는 결국 양쪽 다 신경써야 함
- member.setTeam(team) 과 team.getMembers().add(member1)를 각각 호출하다보면 실수로 하나만 호출해서 양방향이 깨질 수 있음 
- 양방향 관계에서는 두 코드는 하나인 것처럼 사용하는것이 안전함 
- Member 클래스의 setTeam() 메소드를 수정해서 코드를 리팩토링 해보자!
```java
public class Member{
    private Team team;

    public void setTeam(Team team){
        this.team = team;
        team.getMembers().add(this); 
    }
}
```
- setTeam() 메소드 하나로 양방향 관계를 모두 설정하도록 변경!
```java
Team team = new Team("team1", "팀1");
em.persist(team);

Member member1 = new Member("member1","회원1");
Member member2 = new Member("member2","회원2");

//연관관계 설정
member1.setTeam(team1); //member1 -> team, team->member1
em.persist(member1);

member2.setTeam(team1); //member2 -> team, team->member2
em.persist(member2);
```

- 이렇게 한 번에 `양방향 관계를 설정하는 메소드를 연관관계 편의 메소드`라고 함

<br>

### 4.3 연관관계 편의 메소드 작성 시 주의 사항 
- 사실 setTeam() 메소드에는 버그가 있음 
```java
    member1.setTeam(teamA);
    member1.setTeam(teamB);
    Member findMember = teamA.getMember(); //member1이 여전히 조회가 됨
```

- 무엇이 문제일까?
    - member1이 temaB로 변경 할 때 teamA->member1의 관계를 제거하지 않았음 
    - 연관관계를 변경할 때는 기존 팀이 있으면 기존 팀과 회원의 연관 관계를 삭제하는 코드를 추가해야함 
    ```java
    public void setTeam(Team team){
        //기존 팀과의 관계를 제거
        if(this.team != null){
            this.team.getMembers().remove(this);
        }

        this.team = team;
        team.getMembers().add(this);
    }
    ``` 
    - 이 코드는 객체에서 서로 다른 단방향 연관관계 2개를 양방향인 것 처럼 보이게 하기 위해 얼마나 많은 고민과 수고가 필요한지 보여줌
    - 반면에 관계형 데이터베이스는 외래키 하나로 문제를 단순하게 해결함 
    - 정리하자면 객체에서 양방향 연관관계를 사용하려면 로직을 견곻게 작성해야 한다는것~!

<br>

### 참고
- teamA->member1의 관계가 제거되지 않아도 데이터베이스의 외래키를 변경하는데는 문제가 없음 
- 왜냐하면 teamA->member1 관계를 설정한 Team.members는 연관관계의 주인이 아니기 때문!
- 연관관계의 주인인 Member.team은 참조를 member -> teamB로 변경했기 때문에 데이터베이스에 외래 키는 teamB를 참조하도록 정상 반영 됨
- 그리고 이후에 새로운 영속성 컨텍스트에서 teamA을 조회히새 teamA.getMembers()를 호출해도 데이터베이스 외래키에는 관계가 끊어져있기 때문에 아무것도 조회되지 않음 
- 여기까지만 보면 그럼 굳이 복잡하게 객체에 양방향을 신경써줘야 하는 이유가 있을까 싶지만 
- 문제는 관계를 변경하고 영속성 컨텍스트가 아직 살아있는 상태에서 teamA의 getMembers()를 호출하면 여전히 member1이 반환된다는 점!
- 따라서 변경된 연관관계는 앞서 설명한 것 처럼 관계를 제거하는 것이 안전~!

