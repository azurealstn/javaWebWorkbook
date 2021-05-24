package main.java.com.azurealstn.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.GenericServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// 데이터베이스 관련 객체의 참조변수 선언 (패키지: java.sql)
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		// JDBC API 사용시 예외처리 (예외발생시 ServletException객체에 담아 서블릿컨테이너에 전달)
		try {
			ServletContext sc = this.getServletContext();
			// 클래스이름은 반드시 패키지 이름을 포함해야 하는데 이를 영어로 'fully qualified name' or 'QName'
			Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(sc.getInitParameter("url"), sc.getInitParameter("username"),
					sc.getInitParameter("password"));
			// Connection 구현체를 이용하여 SQL문을 실행할 객체
			// Statement 인터페이스는 DB에 질의하는데 필요한 메소드가 정의되어 있다.
			stmt = conn.createStatement();
			// ResultSet 인터페이스는 서버에서 질의 결과를 가져올 수 있다.
			rs = stmt.executeQuery("SELECT MNO,MNAME,EMAIL,CRE_DATE" + " FROM MEMBERS" + " ORDER BY MNO ASC");

			res.setContentType("text/html; charset=UTF-8");
			PrintWriter out = res.getWriter();
			out.println("<html><head><title>회원목록</title></head>");
			out.println("<body><h1>회원목록</h1>");
			out.println("<p><a href='add'>신규회원</a></p>");
			// select 결과 가져오기 (행(row)을 가져옴)
			while (rs.next()) {
				out.println(
						rs.getInt("MNO") + "," + "<a href='update?no=" + rs.getInt("MNO") + "'>" + rs.getString("MNAME")
								+ "</a>," + rs.getString("EMAIL") + "," + rs.getDate("CRE_DATE") + "<a href='delete?no=" + rs.getInt("MNO") + "'>[삭제]</a><br>");
			}
			out.println("</body></html>");
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			// 마지막 자원해제는 역순으로 처리
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
	}

}
