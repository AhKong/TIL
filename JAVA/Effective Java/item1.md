## 아이템1. 생성자 대신 정적 팩터리 메서드를 고려하라.

### 1. 정적 팩터리 메서드(static factory method)

- 정적 팩토리 메서드란 객체 생성의 역할을 하는 클래스 메서드
- 클래스는 클라이언트에 public 생성자 대신 혹은 생성자와 함께 정적 팩터리 메서드 제공 할 수 있음.
-  ex) java.time 패키지에 포함된 LocalTime 클래스의 정적 팩토리 메서드
``` java
   
...
public static LocalTime of(int hour, int minute) {
  ChronoField.HOUR_OF_DAY.checkValidValue((long)hour);
  if (minute == 0) {
    return HOURS[hour];
  } else {
    ChronoField.MINUTE_OF_HOUR.checkValidValue((long)minute);
    return new LocalTime(hour, minute, 0, 0);
  }
}
...

// hour, minutes을 인자로 받아서 9시 30분을 의미하는 LocalTime 객체를 반환한다.
LocalTime openTime = LocalTime.of(9, 30); 
```
- LocalTime 클래스의 of 메서드처럼 __직접적으로 생성자를 통해 객체를 생성하는 것이 아닌 메서드를 통해서 객체를 생성하는 것!__ 

### 2. 생성자가 있는데 왜 굳이 정적 팩터리 메서드를 사용해야 할까?🤷🏼‍♀️

#### 1. 이름을 가질 수 있다.
- 생성자에 넘기는 매개변수와 생성자 자체만으로는 반환될 객체의 특성을 제대로 설명하지 못함 
- 반면에 정적 팩터리 메서드는 이름만 잘 지으면 반환될 객체의 특성을 쉽게 묘사 할 수 있음
- 한 클래스에 시그니처가 같은 생성자가 여러 개 필요할 것 같으면, 생성자를 정적 팩터리 메서드로 바꾸고 각각의 차이를 잘 드러내는 이름을 지어주자.   
> 객체는 생성 목적과 과정에 따라 생성자를 구별해서 사용할 필요가 있다. new라는 키워드를 통해 객체를 생성하는 생성자는 내부 구조를 잘 알고 있어야 목적에 맞게 객체를 생성할 수 있다. 하지만 정적 팩토리 메서드를 사용하면 메서드 이름에 객체의 생성 목적을 담아 낼 수 있다 
```java

@Builder
public class Developer {
    private Integer age;
    private String name;
    private List<String> skills;
    private String career;
    private boolean hasMoved;
    private List<String> previousCompanies; 

    public static Developer createDeveloperWhoHasNeverChangedJobs(String name, List<String> skill,Integer age,String career){
        return Developer.build()
                .name("김아름")
                .age(24)
                .skills(skill)
                .career("11개월")
                .hasMoved(false)
                .build();
    }

    public static Devoloer createNewDeveloperWhoHasChangedJobs(String name, List<String> skill,Integer age,String career,List<String> previousCompanies ){
           return Developer.build()
                .name("김아름")
                .age(24)
                .skills(skill)
                .career("11개월")
                .hasMoved(true)
                .previousCompanies(previousCompanies)
                .build();
    }


```

<br>

#### 2. 호출될 때마다 인스턴스를 새로 생성하지 않아도 된다.
- 불변 클래스는 인스턴스를 미리 만들어 놓거나 새로 생성한 인스턴스를 캐싱하여 재활용 하는 식으로 불필요한 객체 생성을 피할 수 있다.
- 따라서 같은 객체가 자주 요청되는 상황이라면 성능을 향상 시킬 수 있다.
- 반복되는 요청에 같은 객체를 반환하는 식으로 정적 팩터리 방식의 클래스는 언제 어느 인스턴스를 살아 있게 할지를 철저하게 통제 할 수 있다.
```java
Class Boolean{
  private static final Boolean FLASE = new Boolean(false);
  private static final Boolean.TRUE = new Boolean(true);

  public static valueOf(boolean b) {
    return b ? TRUE : FALSE;
  }
} // true, false 의 객체를 미리 생성 후 반복 호출  할 때 인스턴스를 새로 만들지 않음 
```
- 이런 클래스를 __인스턴스 통제 클래스__ 라고 한다

> 인스턴스를 통제해야 하는 이유?
> - 인스턴스를 통제하면 클래스를 싱글턴으로 만들 수도, 인스턴스화 불가로 만들 수 도 있다.
> - 불변 값 클래스에서 동치인 인스턴스가 단 하나 뿐임을 보장할 수 있다.
>   - a == b 일때만 a.equals(b)가 성립!


#### 3. 반환 타입의 하위 타입 객체를 반환 할 수 있는 능력이 있다.
- 이 능력은 반환할 객체의 클래스를 자유롭게 선택 할 수 있는 엄청난 유연성을 선물한다.
- 하위 자료형 객체를 반환하는 정적 팩토리 메서드의 특징은 상속을 사용할 때 확인할 수 있다. 
- 이는 생성자의 역할을 하는 정적 팩토리 메서드가 반환값을 가지고 있기 때문에 가능한 특징이다.

- Basic, Intermediate, Advanced 클래스가 Level라는 상위 타입을 상속받고 있는 구조를 생각해보자. 시험 점수에 따라 결정되는하위 등급 타입을 반환하는 정적 팩토리 메서드를 만들면, 다음과 같이 분기처리를 통해 하위 타입의 객체를 반환할 수 있다.
```java
public class Level {
  ... 
  public static Level of(int score) {
    if (score < 50) {
      return new Basic();
    } else if (score < 80) {
      return new Intermediate();
    } else {
      return new Advanced();
    }
  }
  ...
} 
```


#### 4.  입력 매개변수에 따라 매번 다른 클래스의 객체를 반환 할 수 있다.
- 반환타입의 하위 타입이기만 하면 어떤 클래스의 객체를 반환하든 상관 없다
```java
public class Ticket {

    public static Ticket getTicketByType(String type) {
        if (type.equals("vip")) {
            return VipTicket.INSTANCE;
        }
        if(type.equals("general")){
            return GeneralTicket.INSTANCE;
        }

        new IllegalArgumentException("잘못된 타입입니다!");
    }

}
```

#### 5. 정적 팩터리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.
????? 여기는 책에서 설명하는 내용이 1도 이해가 안간다. 다시 보자 



### 2. 그래서 단점은 없는건가? 그럴리가,,,

1. 상속을 하려면 public 이나 protected 생성자가 필요하니 정적 팩터리 메서드만 제공하면 하위 클래스를 만들 수 없다. 근데 이 부분은 어쩌면 장점이 될 수 도 있다고함.
2. 정적 팩터리 메서드는 프로그래머가 찾기 어렵다.



### 결론
- 정적 팩터리 메서드와 public 생성자는 각각의 쓰임새가 있으니 상대적인 장단점을 이해하고 사용하는 것이 좋다. 그렇다 하더라도 정적 팩터리를 사용하는 게 유리한 경유가 더 많으므로 무작정 public 생성자를 제공하던 습관이 있으면 고치자.




