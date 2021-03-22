## ORM(Object-Relational Mapping) : 객체 관계 매핑 

> 객체는 객체대로 , RDB는 RDB대로 설계 하자!  

- 대중적인 언어에는 대부분 ORM 기술이 존재함
- ORM은 객체와 RDB 두 기둥 위에 있는 기술 (?)


## JPA(Java Persistence API) 
- 현재 자바 진영의 ORM 기술 표준으로, __인터페이스의 모음__
    - 인터페이스이기에 실제로 동작하는 것은 아님 
    - JPA 인터페이스를 구현한 대표적인 오픈 소스가 Hibernate 
 - EJB의 여러 문제를 해결하고, 엔터프라이즈 애플리케이션 개발을 좀 더 쉽게 하기 위한 목적으로 만들어짐 

 #### EJB (Entity Bean)

 - 과거의 자바 표준이자 과거의 ORM 
 - 문제점 : 
    - 코드가 지저분해짐
    - API 의 복잡성이 높음 (interface를 많이 구현해야함)
    - 속도가 느림 


 ## JPA의 동작 과정 

- JPA는 애플리케이션과 JDBC 사이에서 동작
  - 개발자가 직접 JDBC API를 쓰는것이 아닌 JPA를 사용
  - JPA 를 사용하면 JPA 내부에서 JDBC API를 사용하여  DB와 통신


#### 저장 과정 예시 
1. __개발자__ 는 JPA에 Member 객체를 넘긴다
2. __JPA__ 는 Member Entity를 분석한다
3. __JPA__ 는 INSERT SQL을 생성한다
4. __JPA__ 는 JDBC API를 사용하여 SQL을 DB에 날린다

 
#### 조회 과정 예시 
1. __개발자__ 는 member의 pk 값을 JPA에 넘긴다 
2. __JPA__ 는 엔티티의 매핑 정보를 바탕으로 적절한 SELECT SQL을 생성한다 
3. __JPA__ 는 JDBC API를 사용하여 SQL을 DB에 날린다
4. __JPA__  는 DB로 부터 결과를 받아온다
5.  __JPA__ 결과(resultSet)를 객체에 모두 매핑한다 

> 쿼리를 JPA가 만들어주기 때문에 Object와 RDB간의 패러다임 불일치 해결 가능 


### jpa 책 보고 내용정리하기
 


