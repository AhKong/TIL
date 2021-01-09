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


