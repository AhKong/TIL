# NGINX  

![](https://media.vlpt.us/images/wijihoon123/post/74a515e9-e59d-4534-be9c-32ba441d66f9/nginx123.png)


## NGINX 란?

- 경량 웹 서버
- 아파치보다 동작이 단순하고 전달자 역할만 하기 때문에 동시 접속 처리에 특화됨.
    - 아파치의 경우는 요청 하나당 프로세스를 하나씩 사용 
    - Nginx는 __Event-Driven 구조__
- 동시접속이 700명 이상이면 서버를 증설하거나 Nginx 환경을 권장한다고함.
- 클라이언트로부터 요청을 받았을 때 요청에 맞는 정적 파일을 응답해주는 HTTP Web Server로 활용
- Reverse Proxy Server로 활용하여 WAS 서버의 부하를 줄일 수 있는 로드 밸런서로 활용

<br>

## NGINX 역할 
### 1.1 정적 파일을 처리하는 HTTP 서버로서의 역할
- 웹서버의 역할은 HTML, CSS, Javascript, 이미지와 같은 정보를 웹 브라우저(Chrome, Iexplore, Opera, Firefox 등)에 전송하는 역할을 한다. (HTTP 프로토콜을 준수)

<br>

### 1.2 응용프로그램 서버에 요청을 보내는 리버스 프록시로서의 역할
![](https://i.imgur.com/yReDKjj.png)
- 한마디로 말하면 클라이언트는 가짜 서버에 요청(request)하면, 프록시 서버가 배후 서버(reverse server)로부터 데이터를 가져오는 역할을 한다. 
- 여기서 프록시 서버가 Nginx, 리버스 서버가 응용프로그램 서버를 의미한다.
- 웹 응용프로그램 서버에 리버스 프록시(Nginx)를 두는 이유는 요청(request)에 대한 버퍼링이 있기 때문이다. 
- `클라이언트가 직접 App 서버에 직접 요청하는 경우, 프로세스 1개가 응답 대기 상태가 되어야만 한다.`
- 따라서 프록시 서버를 둠으로써 요청을 배분하는 역할을 한다.
- 요청을 배분하는 방법은  `location` 지시어를 사용하여 분배한다.

## NGINX 흐름
![](https://i.imgur.com/W6JATVH.png)

- Nginx는 Event-Driven 구조로 동작하기 때문에 한 개 또는 고정된 프로세스만 생성하여 사용하고,비동기 방식으로 요청들을 동시에 처리할 수 있습니다.
- 위의 그림에서 보이듯이 Nginx는 새로운 요청이 들어오더라도 새로운 프로세스와 쓰레드를 생성하지 않기 때문에 프로세스와 쓰레드 생성 비용이 존재하지 않고,적은 자원으로도 효율적인 운용이 가능합니다.
- 이러한 Nginx의 장점 덕분에 단일 서버에서도 동시에 많은 연결을 처리할 수 있습니다.

> Event-Driven 구조?
> <br> 분산된 시스템에서 이벤트를 생성(발행)하고 발행된 이벤트를 수신자에게 전송하는 구조로 수신자는 그 이벤트를 처리하는 방식의 아키텍처
> <Br> 이벤트에 반응하는 분리된 시스템들

## NGINX 구조

- Nginx는 하나의 Master Process와 다수의 Worker Process로 구성되어 실행됩니다. Master Process는 설정 파일을 읽고,유효성을 검사 및 Worker Process를 관리합니다.
- 모든 요청은 Worker Process에서 처리합니다. 
- Nginx는 이벤트 기반 모델을 사용하고, Worker Process 사이에 요청을 효율적으로 분배하기 위해 OS에 의존적인 메커니즘을 사용합니다.
- Worker Process의 개수는 설정 파일에서 정의되며, 정의된 프로세스 개수와 사용 가능한 CPU 코어 숫자에 맞게 자동으로 조정됩니다.
- 워커 프로세스가 실질적인 웹서버 역할을 함.

![](https://media.vlpt.us/images/wijihoon123/post/3467a69b-25c0-49e1-ad76-51ee4143c49c/nginx111.png)


## nginx.conf - Nginx가 동작해야 할 방식을 지정하는 설정파일!

### 1. 최상단 (Core 모듈) 
```

#user  nobody;  
worker_processes  1;

#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;

#pid        logs/nginx.pid;


```

- `user`     :
    -  워커 프로세스의 권한을 지정한다.
    > 참고 
    > <br>user의 값이 root로 되어 있다면 일반 계정으로 변경하자. nginx는 마스터 프로세스와 워커 프로세스로 동작하고, 워커 프로스세가 실질적인 웹서버의 역할을 수행하는데 user 지시어는 워커 프로세스의 권한을 지정한다. 만약 user의 값이 root로 되어 있다면 워커 프로세스를 root의 권한으로 동작하게되고, 워커 프로세스를 악의적 사용자가 제어하게 된다면 해당 머신을 루트 사용자의 권한으로 원격제어하게 되는 셈이기 때문에 보안상 위험하다. 
- `worker_processes ` :
    - NGINX 프로세스 실행 가능 수
    - auto도 무방하지만, 명시적으로 서버에 장착되어 있는 코어 수 만큼 할당하는 것이 보통이며, 더 높게도 설정 가능하다.

- `pid`:
    - NGINX 마스터 프로세스 ID 정보가 저장된다.

 <br> 
 
 ### 2. events 블락

 ```
events {
    worker_connections  1024;
}
 ```
 - `worker_connections`:
    - 하나의 프로세스가 처리할 수 있는 커넥션의 수를 의미한다.
    - 최대 접속자수 = worker_processes X worket_connections가 된다.
