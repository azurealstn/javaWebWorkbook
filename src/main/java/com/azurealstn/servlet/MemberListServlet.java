package main.java.com.azurealstn.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.azurealstn.vo.Member;

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
			/*Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(sc.getInitParameter("url"), sc.getInitParameter("username"),
					sc.getInitParameter("password")); AppInitServlet으로 ServletContext에 이미 저장되어있다.*/
			
			// Connection 구현체를 이용하여 SQL문을 실행할 객체
			// Statement 인터페이스는 DB에 질의하는데 필요한 메소드가 정의되어 있다.
			conn = (Connection) sc.getAttribute("conn");
			stmt = conn.createStatement();
			// ResultSet 인터페이스는 서버에서 질의 결과를 가져올 수 있다.
			rs = stmt.executeQuery("SELECT MNO,MNAME,EMAIL,CRE_DATE" + " FROM MEMBERS" + " ORDER BY MNO ASC");

			res.setContentType("text/html; charset=UTF-8");
			ArrayList<Member> members = new ArrayList<Member>();
			// select 결과 가져오기 (행(row)을 가져옴)
			//DB에서 회원정보를 가져와 Member 객체에 담는다.
			//그리고 Member 객체를 ArrayList에 add한다.
			while (rs.next()) {
				members.add(new Member()
						.setNo(rs.getInt("MNO"))
						.setName(rs.getString("MNAME"))
						.setEmail(rs.getString("EMAIL"))
						.setCreateDate(rs.getDate("CRE_DATE")));
			}
			
			//request에 회원목록 데이터를 보관한다.
			req.setAttribute("members", members);
			
			//JSP로 출력을 위임한다.
			RequestDispatcher rd = req.getRequestDispatcher("/member/MemberList.jsp");
			rd.include(req, res);
		} catch (Exception e) {
			//throw new ServletException(e);
			req.setAttribute("error", e);
			RequestDispatcher rd = req.getRequestDispatcher("/Error.jsp");
			rd.forward(req, res);
		} finally {
			try {if (rs != null) rs.close();} catch(Exception e) {}
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
			//try {if (conn != null) conn.close();} catch(Exception e) {}
		}
	}

}
