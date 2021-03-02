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

     - 상황별로 생성자가 여러개인 경우 개발자는 어떤 역할을 하는지 정확하게 기억하기 어려워 엉뚱한 것을 호출하는 실수를 할 수있다. 또한 코드를 읽는 사람도 클래스의 설명 문서를 찾아보지 않고는 정확한 의미를 알지 못할것이다.
     > 즉, 한 클래스에 시그니처 같은 생정자가 여러 개 필요할 것 같으면, 생성자를 정적 팩터리 메서드로 바꾸고 각각의 차이를 잘 드러내는 이름으로 네이밍하자! 

2. 호출될 때마다 인스턴스를 새로 생성하지 않아도 된다.
    - 불변 클래스는 인스턴스를 미리 만들어 놓거나 새로생성한 인스턴스를 캐싱하여 재활용하는 식으로 불필요한 객체 생성을 피할 수 있다.
    - (특히 생성 비용이 큰) 같은 객체가 자주 요청 되는 상황이라면 성능 향상에 아주 큰 영향을 끼친다.
    - 플라이웨이트 패턴도 이와 비슷한 기법이라고함 (찾아볼것)
    - 

    넘 어렵다^^..................오늘은 여기까지
     