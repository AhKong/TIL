# Springboot에서 쿠키 생성 및 삭제 

### 쿠키 생성 

```java
RequestMapping(value="/some/path", method = RequestMethod.POST)
public void ResponseEntity<?> someMethod(HttpServletResponse response) {
   Cookie myCookie = new Cookie("cookieName", cookieValue);
   myCookie.setMaxAge(쿠키 expiration 타임 (int));
   myCookie.setPath("/"); // 모든 경로에서 접근 가능 하도록 설정
   response.addCookie(myCookie);
}

```