# Item 1. 생성자 대신 정적 팩터리 메서드를 고려하라. 

## JAVA에서 정적 팩터리 메서드란? 
 `private 생성자를 통해 new를 통한 객체 생성을 감추고 static 메서드를 통해 객체 생성을 캡슐화하는 디자인 패턴`을 말합니다.

 ```java
public class FanClub {

    private String name;
    private String celebrity;

    private FanClub( String name,String celebrity){

        this.name = name;
        this.celebrity = celebrity;
    }

    // 정적 팩토리 메소드
    public static FanClub newMay() {
        return new FanClub("May","박지훈");   
    }

    // 정적 팩토리 메소드
    public static FanClub newShineeWorld() { 
       return new FanClub("Shinee World","샤이니");   
    }
}

 ```


 ## 정적 팩터리 메서드의 장점 (생성자 대비)
 1. 이름을 가질 수 있다.
    - 생성자에 넘기는 매개변수와 생성자 자체만으로는 변환될 객체의 특성을 제대로 설명하지 못함!
    - 생성자를 이용하여 인스턴스를 생성해야 하는 상황이라면 
    ```java
        FanClub may = new FanClub("May","박지훈");  
        FanClub sw = new FanClub("Shinee World","샤이니");  
    ```

    - 정적 팩터리 메서드를 사용하게 된다면? 
    ```java
        FanClub may = FanClub.newMay();
        FanClub sw = FanClub.newShineeWorld();
     ```

     - 확실히 전자의 상황보다 정적 팩터리 메서드를 사용하면 해당 인스턴스 별 이름을 가질 수 있게 되어 보다 직관적인 코드가 되는것을 볼 수있다.
     전자의 상황의 경우 문자열 필드가 아닌 int 타입의 숫자가 여러개 들어가야 하는 상황이라면 변수명으로만 해당 인스턴스의 목적(?)을 파악 할 수 있지만 정적 팩터리 메서드를 사용한다면 변수명을 a,b,c로 막 짓더라도 정적 팩터리 메서드명이 인스턴스 별로 다르기 때문에 코드의 가독성을 올려준다!