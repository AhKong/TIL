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
![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FQaEMk%2FbtqCZyS3WDP%2F3FOkesItcKIxYmAKAtjmtk%2Fimg.png)
출처: https://developyo.tistory.com/240

 Throwable 클래스를 상속 받는 클래스는 Error와 Exception이 있는데 모든 예외의 최고 조상 클래스는 당연히 Execption 입니다. 


## ✔️ Exception과 Error의 차이





Exception과 Error의 차이는?
RuntimeException과 RE가 아닌 것의 차이는?
커스텀한 예외 만드는 방법