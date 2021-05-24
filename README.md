## jsp/servlet부터 spring 프레임워크까지
이 프로젝트는 CRUD 테스트하는 것부터 mybatis, mvc 패턴, 스프링을 사용하는 과정을 정리한 것입니다.  
이 내용들은 엄진영님의 자바 웹 개발 워크북을 공부하면서 정리하였습니다.

<br/>
<br/>

## 사용 기술

- Java 11
- 서블릿(GenericServlet, HttpServlet)
- MySQL(8.0.22) - DBeaver(GUI)

<br/>
<br/>

## 기능

- 회원목록: http://localhost:8090/javaWebWorkbook/member/list
- 회원수정(상세보기): http://localhost:8090/javaWebWorkbook/member/update?no=?
- 회원삭제: http://localhost:8090/javaWebWorkbook/member/delete?no=?
	- 삭제같은 경우는 바로 remove시키면 되기 때문에 doGet() 요청으로 삭제쿼리를 날립니다.