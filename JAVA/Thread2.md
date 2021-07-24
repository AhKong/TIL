# Thread 

## 1. Thread & Process 

### 프로세스
- 실행중인 프로그램 
- 사용자가 작성한 프로그램이 운영체제에 의해 메모리 공간을 할당받아 실행중인 상태 
- 프로그램에 사용되는 데이터와 메모리 자원 그리고 스레들 구성 
- 프로세스 간에는 각 프로세스의 데이터 접근이 불가

### 스레드
- 프로세스내에서 실제로 작업을 수행하는 주체
- 모든 프로세스에는 한 개 이상의 스레드가 존재하여 작업을 수행함.
- 두 개 이상의 스레드를 가지는 프로세스를 멀티스레드 프로세스라고 함 
- 프로세스 안에 있기 때문에 프로세스의 데이터를 모두 접근 가능

### 스레드 장점 
- CPU 활용도 높임 -> 성능 개선 ,응답성 향상

### 스레드 단점 
- 하나의 스레드 문제가 프로세스 전반에 영향을 미침
- 여러 스레드 생성 시 성능 저하 기능


## 2. 스레드의 생성과 실행 

### 2.1 스레드 생성

- Runnable 인터페이스를 구현하는 방법 
    - 해당 클래스는 run() 메소드를 구현해야 함
- Thread 클래스를 상속하는 방법 
    - 자식 클래스는 run 메소드를 재정의 해야 함.
- 어떤 방법을 선택해도 성능상의 차이는 별로 없음 하지만 Runnable 인터페이스를 구현하는 방법이 일반적임 
    - Thread 클래스를 상속받으면 다른 클래스를 상속 받을 수 없기 때문 
    - 자바는 다중상속이 불가능 하잖니 ^>^
    - Runnable 인터페이스를 구현하는 방법은 재사용성을 높이고 코드의 일관성을 유지 할 수 있기 때문에 객체지향적인 방법이라고 할 수 있음.


    ```java

    @Slf4j
    public class MyRunnable implements Runnable{
        @Override
        public void run() {
            log.info("Runnable 인터페이스를 구현한 방법");
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("Runnable 인터페이스를 구현하지 않은 방법");
            }
        });
        thread.start();

        Thread thread = new Thread(()-> )
        
        Thread thread2 = new Thread(new MyRunnable());
        thread2.start();

         Thread thread3 = new Thread(()->log.info("Runnable 인터페이스를 구현하지 않은 방법2 - 람다식 이용"));
        thread3.start();
    }
    ```

### 스레드 실행  : start() vs run() 

- 메인 메서드에서 run()를 호출 하는 것은 생성된 쓰레드를 실행 시키는 것이 아니라 단순히 클래스에서 선언된 메서드를 호출하는것 
- start() 는 새로운 쓰레드가 작업을 실행하는데 필요한 호출스택을 생성한 다음 run()을 호출해서, 생성된 호출 스택에 run()이 첫번째로 올라가게 함 
- 따라서 run()은 싱글스레드에서만 실행 되고 멀티 스레드를 사용하는 경우에는 start() 메서드를 호출해야 함 

![](https://postfiles.pstatic.net/MjAyMTA3MjVfOTYg/MDAxNjI3MTM5ODg0MjE5.9jlJTC3o2IW0aGUfazuKULBJecOd2QQItnp3rO-sqhYg.M82bndbC4o0iqa0IXUweM-Yu3ruWrZFTfH9rmGlmlmcg.PNG.ahreum0412/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2021-07-25_%EC%98%A4%EC%A0%84_12.16.59.png?type=w966)

> 참고 <br> 
> 호출 스택 ?<Br>
> - 메서드의 작업에 필요한 메모리 공간을 제공<br>
> - 메서드 호출시 호출 스택에 호출된 메서드를 위한 메모리가 할당되며, 메서드가 작업을 수행하는 동안 지역변수(매개변수 포함) 들과 연산의 중간결과등을 저장하는데 사용됨 <br>
> - 메소드 작업이 끝나면 할당되었던 메모리공간은 반환되어 비워짐 <br>
> - 특징 <br>
메서드가 호출되면 수행에 필요한 만큼의 메모리를 스택에 할당 <br>
메서드가 수행을 마치고나면 사용했던 메모리를 반환하고 스택에서 제거 <br>
호출스택의 제일 위에 있는 메서드가 현재 실행중인 메서드 <br>
아래에 있는 메서드가 바로 위의 메서드를 호출한 메서드 <br>


## 3. 쓰레드의 우선순위 

- 2개 이상의 스레드에서 각 스레드에 대한 우선순위를 부여하여 우선순위가 높은 스레드가 먼저 실행 되도록 하는 것 
- 작업의 중요도에 따라 우선순위를 다르게 지정하여 특정 스레드가 더 많은 작업을 갖도록 할 수 있음 
- 범위는 1~10 이고 숫자가 높을수록 우선순위가 높다.

```java

 Thread thread2 = new Thread(new MyRunnable());
 thread2.setPriority(2);

Thread thread3 = new Thread(()->log.info("Runnable 인터페이스를 구현하지 않은 방법2 - 람다식 이용"));
thread3.setPriority(3);
```

- 하지만 스레드가 우선순위가 높다고 해서 무조건 먼저 실행되는건 절대 아님
- 우선순위를 준다는건 먼저 실행 시키라는것이 아니라 더 많은 실행 시간과 실행 기회를 갖게 될 것이라고 예상만 하는 것 뿐 
- 어떤 core 의 CPU냐, 어떤 OS 이냐에 따라서 스케줄링 방식이 다르기 때문에 그에 따른 다양한 결과가 나올 수 있음 


## 3. 메인 스레드 
- main메서드의 작업을수행하는 것도 스레드가 하는 일이고 이를 main 스레드 라고 함 
- 프로그램이 실행되기 위해서는 작업을 수행하는 스레드가 최소한 하나는 필요함 그렇기 때문에 기본작으로 하나의 스레드를 생성하고 그 스레드가 메인 메서드를 호출해서 작업이 수행되도록 하는 것 

## 4. 데몬 스레드
- 일반 쓰레드의 작업을 보조하는 역할을 수행하는 쓰레드
- 일반 쓰레드가 모두 종료되면 데몬 쓰레드는 강제로 자동 종료됨 
- 애초에 역할이 일반 쓰레드의 보조 역할이기 때문 
- ex 자동 저장 기능 
``` java
 thread.setDaemon(true);
 thread.isDaemon(); // return type boolean
```

## 5. 동기화

- 한 쓰레드가 진행 중인 작업을 다른 쓰레드가 간섭하지 못하도록 막는 것 
- 임계 영역에 여러 스레드가 접근하는 경우 한 스레드가 수행하는 동안 공유 자원을 lock 해서 다른 thread의 접근을 막음 -> lock 획득한 스레드만 실행 
- 동기화를 잘못 구현하면 deadlock에 빠질 수 있음 

* 임계영역 : 두 개 이상의 스레드가 동시에 접근하게 되는 데이터

- `synchronized` 키워드를 이용한 동기화
    
``` java
public synchronized void run( {
    //  메서드 전체를 임계영역으로 지정
})

public void test(String myString){
     log.info("이곳은 동기화가 필요 없음")
     String originalString = null;
     synchronized(originalString){
        originalString = myString;
     } // 특정 영역을 임계영역으로 지정 
    
}
```
- 스레드는 synchronized 메서드가 호출된 시점부터 해당 메서드가 포함된 객체의 Lock을 얻어 작업을 수행하다가 메서드가 종료되면 lock을 반환 
- 두가지 방법 모두 lock의 획득 및 반납이 자동적으로 이루어지기 때문에 개발자는 임계영역을 잘 설정해주기~!

## 6. 데드락 (교착상태)
- 두개 이상의 스레드가 하나의 리소스를 사용 할 때 발생 할 수 있음 
- ex : 스레드1 이 리소스 a 를 얻고 리소스 b를 기다리는 상황 & 스레드 2가 리소스 b를 얻고 리소스 a를 기다리는 상황 
- 원하는 리소스가 상대방에게 할당 되어있기 때문에 스레드1,2, 는 무한정 대기상태에 빠지게 됨 이게 바로 데드락 상태~!

### wait() & notify(),notifyAll()

- synchronized로 동기화를 해서 공유 데이터를 보호하는것도 중요하지만 특정 쓰레드가 객체의 락을 가진 상태로 오랜 시간을 보내지 않도록 하는것도 중요함 
- 다른 스레드들이 어떤 객체의 락을 기다리느라 다른 작업들도 원할히 진행되지 않을 수 잇기 때문 -> 멀티스레드를 적용한게 아주 무의미하게 되버리겠즁>??

- 이런 상황을 개선하기 위해서 wait() & notify().notifyAll() 메서드를 적절하게 잘 사용해야함 
- 한 스레드가 lock을 오래 걸고 기다리는 대신에 wait() 을 호출하여 다른 스레드에게 제어권을 넘겨주고 대기상태로 기다리다가 다른 쓰레드에 의해서 notify()가 호출되면 다시 실행 상태가 되도록 하는 것 

- wait() & notify().notifyAll() 는 Thread 클래스가 아닌 Object 클래스에서 정의된 메서드 이므로 모든 객체에서 호출이 가능함 
- 스레드가 wait()을 호출하면 그때 까지 자신이 객체에 놓았던 lock을 풀고, wait()가 호출된 객체의 대기실 (waiting-pool)에서 기다림 
- notify() 는 waiting pool에 있는 쓰레드 중에 하나만 깨우고 notifyAll()은 모든 객체를 깨움 
- notify() 를 호출하는 경우에는 어떤 객체가 깨어날 지 모르기 때문에 notifyAll()로 모든 객체를 깨운뒤에  JVM의 스레드 스케줄링에 의해 처리되는것이 안전함 
- `동기화 블럭 내에서만 사용가능` 

```java
class Account{
    int balance = 1000;
 
    public synchronized void withdraw(int money){
        /* 잔고가 부족할 경우 wait()를 호출하여 lock을 풀고 waiting pool에
           들어가면서 제어권을다른 쓰레드에게 양보하게 됩니다. */
        while(balance < money){ 
            try{
                wait();
            }catch(InterruptedException e){ }
        }
 
        balance -= money;
    }
    
    /* 다른 쓰레드에 의해서 deposit()메서드가 호출되어 잔고가 증가하면서 notify()를 
       호출하면 객체의 waiting pool에서 기다리고 있던 쓰레드를 깨우게 됩니다. */
    public synchronized void deposit(int money){
        balance += money;
        notify();
    }
}

```