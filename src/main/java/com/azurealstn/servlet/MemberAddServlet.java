package main.java.com.azurealstn.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member/add")
public class MemberAddServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.println("<html><head><title>회원 등록</title></head>");
		out.println("<body><h1>회원 등록</h1>");
		out.println("<form action='add' method='post'>");
		out.println("이름: <input type='text' name='name'><br>");
		out.println("이메일: <input type='text' name='email'><br>");
		out.println("암호: <input type='password' name='password'><br>");
		out.println("<input type='submit' value='추가'>");
		out.println("<input type='reset' value='취소'>");
		out.println("</form>");
		out.println("</body></html>");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//CharacterEncodingFilter에서 처리
		//req.setCharacterEncoding("UTF-8");
		
		//insert SQL문을 실행하기 때문에 ResultSet 참조변수는 선언X
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			ServletContext sc = this.getServletContext();
			// 클래스이름은 반드시 패키지 이름을 포함해야 하는데 이를 영어로 'fully qualified name' or 'QName'
			/*Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(sc.getInitParameter("url"), sc.getInitParameter("username"),
					sc.getInitParameter("password"));*/
			conn = (Connection) sc.getAttribute("conn");
			//PreparedStatement는 ?로 되어있는데 이것은 입력 매개변수가 많은 경우에 유용함.
			//또한 이미지와 같은 바이너리 데이터를 저장하거나 변경할 때 사용.
			stmt = conn.prepareStatement(
					"INSERT INTO MEMBERS(EMAIL,PWD,MNAME,CRE_DATE,MOD_DATE)"
					+ " VALUES (?,?,?,NOW(),NOW())");
			//setXXX() 메소드를 호출하여 ?값을 설정합니다.
			stmt.setString(1, req.getParameter("email"));
			stmt.setString(2, req.getParameter("password"));
			stmt.setString(3, req.getParameter("name"));
			//select문은 executeQuery(), 나머지는 executeUpdate()
			stmt.executeUpdate();
			
			resp.sendRedirect("list");
			
			/*Redirect는 HTML을 출력하지 않는다
			resp.setContentType("text/html; charset=UTF-8");
			PrintWriter out = resp.getWriter();
			out.println("<html><head><title>회원등록결과</title></head>");
			out.println("<body>");
			out.println("<p>등록 성공입니다!</p>");
			out.println("</body></html>");
			*/
			
			//refresh 정보를 응답 헤더에 추가
			//resp.addHeader("Refresh", "1;url=list");
			
		} catch (Exception e) {
			throw new ServletException(e);
			
		} finally {
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
			//try {if (conn != null) conn.close();} catch(Exception e) {}
		}
	}

	

}
