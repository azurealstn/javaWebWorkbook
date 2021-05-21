package main.java.com.azurealstn.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

/**
 * @author azure
 * setContentType(): 출력할 데이터의 인코딩 형식과 문자 집합을 지정합니다.
 * setCharacterEncoding(): 출력할 데이터의 문자 집합을 지정합니다.
 */
@WebServlet("/calc")
public class CalculatorServlet extends GenericServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		int a =  Integer.parseInt(req.getParameter("a"));
		int b =  Integer.parseInt(req.getParameter("b"));
		
		res.setContentType("text/plain");
		res.setCharacterEncoding("UTF-8");
		PrintWriter writer = res.getWriter();
		writer.println("a=" + a + "," + "b=" + b + "의 계산결과 입니다.");
		writer.println("a + b = " + (a + b));
		writer.println("a - b = " + (a - b));
		writer.println("a * b = " + (a * b));
		writer.println("a / b = " + ((float)a / (float)b));
		writer.println("a % b = " + (a % b));
	}

}
