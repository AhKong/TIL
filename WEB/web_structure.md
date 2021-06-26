# Web ?
- 웹이란 World Wide Web의 줄임말로 WWW라고 부르기도 하는데, 인터넷에 연결된 사용자들이 서로의 정보를 공유할 수 있는 공간을 의미한다.

<br>

# Web 구조 
![](https://media.vlpt.us/post-images/taylorkwon92/0fc3b7c0-4af1-11ea-8587-91447c55302c/image.png)
- 웹 브라우저는 Web Server에 IP주소로 접속 요청을 보내고, 그 요청은 WAS를 거쳐 DB에서 요청된 파일을 가져온다. 
- 그리고 그 파일은 다시 WAS를 거쳐 웹서버를 통해 웹브라우저에게 전달된다. 이로써 우리는 웹 브라우저 상에 네이버 화면을 볼 수 있다.

<br>

# Web Server 와 WAS(Web Application Server)

- Web Server
    - 정적 페이지(html, jpeg, css 등)를 제공하는 서버이다. 대표적인 웹 서버에는 `NGINX`,Apache, IIS, Netscape가 있다.

- WAS
    - html만으로 할 수 없는 데이터베이스 조회나 다양한 로직처리 같은 동적인 컨텐츠를 제공하기 위해 만들어진 서버이다.
    -  대표적으로 Tomcat, Jeus, JBoss가 있다.
    -  때문에 웹 서버를 앞에 두고 필요한 WAS를 웹서버에 플러그인 형태로 설정하여 효율적으로 처리되게 한다.
    - 참고로 스프링부트의 경우는 내장 WAS로 톰캣을 사용한다.
    - 웹 컨테이너,컨테이너,서블릿 컨테이너 라고도 칭한다. 
    - WAS는 웹서버와 웹 컨테이너의 결합으로 다양한 기능을 컨테이너에 구현하여 다양한 역할을 수행할 수 있다.
    - 클라이언트의 요청이 있을 때 내부의 프로그램을 통해 결과를 만들어 내고 이것을 다시 클라이언트에 전달해주는 역할을 하는 것이 바로 웹 컨테이너이다.
    ![](https://kookyungmin.github.io/image/S_Note_image/s_note_image_02.png)



출처: <https://kookyungmin.github.io/server/2018/08/05/s_note_01/>

