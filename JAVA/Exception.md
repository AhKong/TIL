# 예외처리 

## ✔️ 예외처리를 해야하는 이유
- 프로그램을 구현하다보면 예상치 못한 상황으로 에러가 발생 하여 프로그램이 정상적으로 실행 되지 않는 경우가 생길 수 있습니다. 에러가 발생하여 프로그램에 문제가 생긴다는것은 코드의 문제점이 있다는 사실을 알게 되는 아주 ~~귀한 순간~~이지만 그렇다고 넋놓고 지켜만 봐도 안되는 상황입니다. `해당 에러를 무시 하고 싶거나 혹은 그에 대응하는 적절한 처리`를 해야만 서비스를 정상적으로 제공 할 수 있습니다. 그렇기 때문에 예외처리는 필히 해야합니다.


## ✔️ 자바에서 예외 처리 방법 (try, catch, throw, throws, finally)

1. try - catch -finally
```java
    // 단일 캐치문
    try{
        // 예외가 발생 할 것 같은 코드 작성
    } catch (발생 할 것 같은 예외 ) {
       // 해당 예외가 발생 했을 시 어떻게 처리 할 것인지에 대해!
    } finally {
        // 무조건 실행 되어야 하는 코드 
    }

   // 다중 캐치문1 

    try{
      
    } catch (발생 할 것 같은 예외1 ) {
        
    } catch (발생 할 것 같은 예외2 ) {
      
    }  finally {
     
    }
    // 다중 캐치문2 (멀티 캐치문)
     try{
      
    } catch (발생 할 것 같은 예외1 | 예외 클래스 2 ) {
    
    }  finally {
     
    }
```
>   💡 다중 catch문 작성 시 주의 사항 💡 <br> 
    상위 예외클래스가 하위 예외 클래스보다 아래에 위치해야 합니다.
    하위 예외 클래스가 이미 상위 예외 클래스 에 속해있기 때문에, 상위 예외 클래스가 앞에있으면 하위 예외 클래스가 발생하면 상위 예외 클래스에 속하게 되기 때문에 정확하게 예외처리를 할 수 없기 때문입니다.
   

2. throw vs throws 
    - `throw`는 Exception을 강제로 발생시킬 때 사용하는 키워드입니다.
    ```java
        try{
            throw new Exception();  
        } catch(Exception e){
            System.out.println("예외가 강제로 발생 되었습니다!!");
        }
     ```
    - `throws`는 예외를 상위쪽으로 미루어 처리합니다.
   ```java
    public void func() throws Exception{
    // 해당 메서드는 어디서인진 정확 하게 알 순 없지만 Exception 에러가 난다는 사실을 알 수 있음
    }

    public void func2(){
        try{
            func(); //Exception 에러가 발생 할 수 도 있는 func메서드 호출
        }catch(Exception e ) {
            // fucn()를 호출한 상위 메서드인 func2에서  해당 예외처리 진행!
        }
    }
   ```


## ✔️ 자바가 제공하는 예외 계층 구조
![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FRoZlV%2FbtqS3I0AXjN%2FadMoY7CeVX8YCffIlNCoH0%2Fimg.png)
출처: https://madplay.github.io/post/java-checked-unchecked-exceptions

 Throwable 클래스를 상속 받는 클래스는 Error와 Exception이 있는데 모든 예외의 최고 조상 클래스는 당연히 Execption 입니다. 
## ✔️ Checked Exceptions VS Unchecked Exceptions

위 그림을 살펴보면 Exception을 상속받는 클래스들 중에서 RuntimeException을 제외하고는 모두 Checked Exception이라고 표시되어 있다.

`Checked Exception은 컴파일 시점에서 확인될 수 있는 예외입니다.` 만약 코드 내에서 Checked Exception을 발생시킨다면, 해당 예외는 반드시 처리되거나, 해당 코드가 속한 메서드의 선언부에 예외를 선언해줘야 합니다.

```java
public static void main(Stringp[] args) {
    throw new IOException();
}
```

위의 코드는 애초에 컴파일이 되지 않습니다. 
```java
 public static void main(Stringp[] args) throws IOException {
        throw new IOException();
 }   

 or 
  public static void main(Stringp[] args) {
      try{
          throw new IOException();
      }catch (IOException e){
        // 예외가 발생 했을때 처리 할 코드!
      }      
 }   
 
```
이 처럼 해당 예외에 대한 핸들링을 해주면 컴파일이 정상적으로 이루어집니다!
<br><br>
`Unchecked Exception은 컴파일 단계에서 확인되지 않는 예외입니다.` Java에서는 RuntimeException과 그 하위 클래스, 그리고 Error와 그 하위 클래스가 이에 속합니다.
즉 컴파일 시점에 코드의 문제가 있는지 알지 못하고 해당 코드가 직접 실행이 되어야만 발생하는 Exception입니다.그렇기 때문에 이 예외들은 컴파일러가 예외를 처리하거나 선언하도록 강제하지 않습니다. Unchecked Exception에 대한 모든 책임은 개발자의 몫입니다. 
~~그러니까 예외처리를 잘 하는 개발자가 되어야징~~

## ✔️ Exception과 Error의 차이
- Error : 프로그램이 실행 중 어떤 원인에 의해서 오작동을 하거나 비정상적으로 종료되는 경우

- Exception : 프로그램이 동작 도중 `예기치 않았던 이상 상태가 발생하여 수행 중인 프로그램이 영향`을 받는 경우 , 하지만 개발자가 적절하게 코드를 작성해주면 비정상적인 종료를 막을 수 있습니다.

- 즉, Error가 발생하여 프로그램이 종료가 될 상황을 미리 방지하기 위해 Exception 상황을 만들어 핸들링 하면 됩니다!



## 마치며 

예외처리는 항상 어느 수준까지 예외를 처리해야 하는지 고민하게 되는 부분인것 같습니다.
현재는 스프링부트 어노테이션을 활용해서 좀 더 쉽게 커스텀한 예외들을 가지고 예외처리를 진행 하고 있습니다. 추후에 스프링부트에서 예외처리 다루는 방법에 대해 정리하여 포스팅 해야겠습니다!

