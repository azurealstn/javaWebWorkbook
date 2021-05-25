## jsp/servlet부터 spring 프레임워크까지
이 프로젝트는 CRUD 테스트하는 것부터 mybatis, 미니 mvc 구조 만들기, 스프링을 사용하는 과정을 정리한 것입니다.  
이 내용들은 엄진영님의 자바 웹 개발 워크북을 공부하면서 정리하였습니다.

<br/>
<br/>

## 사용 기술

- Java 11
- 서블릿 3.1(GenericServlet, HttpServlet)
- JSP 2.3
- MySQL(8.0.22) - DBeaver(GUI)

<br/>
<br/>

## 기능

- 회원목록: http://localhost:8090/javaWebWorkbook/member/list.do
- 회원수정(상세보기): http://localhost:8090/javaWebWorkbook/member/update.do?no=?
- 회원삭제: http://localhost:8090/javaWebWorkbook/member/delete.do?no=?
- 로그인: http://localhost:8090/javaWebWorkbook/auth/login.do
- 로그아웃: http://localhost:8090/javaWebWorkbook/auth/logout.do

---

- 프로젝트목록: http://localhost:8090/javaWebWorkbook/project/list.do
- 프로젝트수정(상세보기): http://localhost:8090/javaWebWorkbook/project/update.do?no=?
- 프로젝트삭제: http://localhost:8090/javaWebWorkbook/project/delete.do?no=?

<br/>
<br/>

## DB 관계

![캡처](https://user-images.githubusercontent.com/55525868/119515727-12f77380-bdb1-11eb-86f3-761a9e0ff2e6.PNG)

- 프로젝트 멤버에는 여러 사람들의 정보가 들어있고, 여러 프로젝트들이 들어있습니다.
- 그 안에는 한 명의 정보와 한 프로젝트의 정보가 담겨있습니다.
- 한 회원 : 프로젝트 멤버 : 프로젝트 정보 -> 1 : N : 1