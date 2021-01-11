# Thread 


### 1. 쓰레드의 이해 및 생성 

#### thread ?  : 실행 중인 프로그램 내에서 "또 다른 실행의 흐름을 형성하는 주체" 

```java

class CurrentTreadName {
    public static void main(Stirng[] args){
        // main method를 실행하는 스레드의 정보를 담고 있는 인스턴스를 참조 
        Thread thread = Thread.currentThread();
        String threadNAme = thread.getName(); // 스레드의 이름 반환
        System.out.println("threadName : "+ threadNAme); // threadName : main 
    }
}
```
> 출력 결과를 확인해보면 스레드의 이름이 "main"인 것을 확인 가능함 -> 즉 ,메인 메서드를 실행하는 쓰레드를 "main thread" 라고 한다!

#### thread 생성하는 법 - 1 

>  Runnable을 구현한 인스턴스 생성  > Thread 인스턴스 생성 -> start 메서드 호출 

- 스레드를 추가하면 추가한 수만큼 프로그램 내에서 다른 실행의 흐름이 만들어짐!
- 스레드는 자신의 일을 마치면 자동으로 소멸됨! 
- 각각의 스레드는 독립적으로 자신의 일을 실행함 
- 스레드가 처한 상황 혹은 운영체제가 코어를 스레드에 할당하는 방식에 따라 각각의 스레드의 실행 속도에는 차이가 존재함

```java

class MakeThreadDemo {
     public static void main(Stirng[] args){
         // Runnable은 void run() 이라는 추상 메서드 하나만 존재하는 함수형 인터페이스임  
        Runnable task = () -> { // 람다식을 사용하여 메소드의 구현과 인스턴스 생성을 동시에 진행 
            int n1 = 10;
            int n2 = 20; 
            String name = Thread.currentThread().getName();
            System.out.println(name +" : " + (n1+n2));

            Thread t = new Thread(task); // 인스턴스 생성 시 run 메서드의 구현 내용 전달 
            t.start(); // 스레드 생성 및 실행 
            System.out.println("END" + Thread.currentTread().getName());
        }
    }
}

```


#### thread 생성하는 법 - 2 

>  Thread를 상속하는 클래스의 정의와 인스턴스 생성 -> start() 메서드 호출 

- Thread를 상속받는 메서드는 반드시 __public void run() 메서드를 오버라이딩 해야함__ 
- 그래야 start 메서드를 호출을 통해 쓰레드가 생성 되었을때 이 쓰레드는 오버라이딩 된 run 메서드를 실행 시킴! 

``` java

class Task extend Thread{
    @overriding
    public void run(){ 
        int n1 = 10;
        int n2 = 20; 
        String name = Thread.currentThread().getName();
        System.out.println(name +" : " + (n1+n2));
    }
}

class MakeThreadDemo {
     public static void main(Stirng[] args){
        Task t1 = new Task();
        Task t2 = new Task();

        t1.start(); 
        t2.start(); 
        System.out.println("END" + Thread.currentTread().getName());
        
    }
}

```

참고로 thread를 생성하는 방법은 1번째 방법을 주로 사용한다고 함 ~! 


### 2. 쓰레드의 동기화 

#### 쓰레드의 메모리 접근 방식과 그에 따른 문제점 

```java
import javax.print.attribute.standard.JobHoldUntil;

class Counter {
    int count = 0; // 두 쓰레드에 의해 공유 되는 변수
    
    public void increment(){
        count ++; // 첫 번째 스레드에 의해 실행되는 문장
    }

    public void decrement(){
        count--; // 두번째 스레드에 의해 실행되는 문장
    }

    public int getcount(){
        return count;
    }
}

class MutilAccess {
    public static Counter cnt = new Counter();
    
    public static void main(Stringp[] args) throws InterruptedException{
        Runnable task1 = () -> {
            for(int i =0; i<1000; i++){
                cnt.increment();
            }
        };

        Runnable task2 = () -> {
            for(int i =0; i<1000; i++){
                cnt.decrement();
            }
        };

        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);

        t1.start();
        t2.start();
        t1.join(); //t1이 참조하는 쓰레드의 종료를 기다림!
        t2.join(); // t2가 참조하는 쓰레드의 종료를 기다림!
        System.out.println("count : "+ cnt.getcount());
    }
}
```

- join() 메소드는 특정 쓰레드의 실행이 완료되기를 기다릴 때 호출하는 메소드임 ! 
- 위의 코드의 실행결과는 실행 할때마다 다르다 즉, __둘 이상의 쓰레드가 동일한 변수에 접근하는 것은 문제가 될 수 있다!__ 
- 따라서 둘 이상의 쓰레드가 동일한 메모리 공간에 접근해도 문제가 발생하지 않도록 __동기화(synchronization)을 해야함__


### 동기화는 어떻게 해야 하는데!!??!?!?

#### 1) 동기화(synchronized) 메서드 활용 
- 동기화 하고자 하는 메서드 앞에 `synchronized` 키워드를 같이 작성해주면 됨 !
- `synchronized` 키워드가 붙게 되면 해당 메서드는 한순간에 한 쓰레드의 접근만 허용하게 됨!
- 자바스크립트의 `await` 키워드와 하는 역할이 비슷! 
- 아래의 코드 처럼 한 클래스의 두 메서드에 `synchronized`이 선언이 되면 두 메서드는 둘 이상의 쓰레드에 의해 동시에 진행 실행 될 수 없도록 동기화 됨 
- 이렇게 코드가 동기화가 되면 i -> d -> i -> d 이런식으로 증가를 하든 감소를 하든 한 메서드의 실행이 끝난두 다음 메서드가 실행이 되기 때문에 결과 값은 항상 0 이 나오게 됨! (메모리에 동시 접근 불가능!)
- 사용하기는 간단하지만 메소드 전체에 동기화를 걸어야 한다는 단점이 있음 
``` java
class Counter {
   int count = 0; 
    
   synchronized public void increment(){
        count ++;
    }

    synchronized public void decrement(){
        count--;
    }

    public int getcount(){
        return count;
    }
}
```

#### 2) 동기화(Synchronized) 블록

```java
   synchronized public void increment(){
        count ++; // 첫 번째 스레드에 의해 실행되는 문장
        Systme.out.println("call increment method"); //동기화가 불필요함 
    }
```

- synchronized는 위의 코드처럼 동기화가 불필요한 부분을 실행하는 동안에도 다른 쓰레드의 접근을 막는 일이 발생함 
- 그러니까 `동기화 블록` 을 사용하장!!!!!!!!

```java
class Counter {
    int count = 0; // 두 쓰레드에 의해 공유 되는 변수
    
    public void increment(){
        synchronized(this){
            count ++;
        }
    }

     public void decrement(){
        synchronized(this){
            count --; 
        }    
    }

    public int getcount(){
        return count;
    }
}
```
- 동기화 블록의 선언에 포함된 this의 의미 = __이 인스턴스의 다른 동기화 블록과 더불어 동기화 하겠다!__ 
- 뭔말이냐면 위에서 설명한 동기화 메소드 내용과 사실 똑같음 
- 즉 이 코드도 실행시켜보면 결과값은 0 으로 항상 동일하단 뜻 


---

일단 여기까지 공부를 하면서 느낀점은 spring boot 에서 얼마나 thread pool을 쉽게 생성하고 관리 할 수 있게 해주는지 알게 되었달까,,,,,^^ 다음 내용은 이제 java 내에서 thread pool을 관리하는 내용을 정리해보자 ^.^ 
오늘으 공부는 여기까지 ㅎ,,,,,,,,,,