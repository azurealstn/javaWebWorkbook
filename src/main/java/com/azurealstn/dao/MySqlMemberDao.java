package main.java.com.azurealstn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import main.java.com.azurealstn.util.DBConnectionPool;
import main.java.com.azurealstn.vo.Member;


public class MySqlMemberDao implements MemberDao {
	
	DataSource ds;
	
	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}
	
	public List<Member> selectList() throws Exception {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			connection = ds.getConnection();
			// Statement 인터페이스는 DB에 질의하는데 필요한 메소드가 정의되어 있다.
			stmt = connection.createStatement();
			// ResultSet 인터페이스는 서버에서 질의 결과를 가져올 수 있다.
			rs = stmt.executeQuery(
					"select mno,mname,email,cre_date" +
					" from members" +
					" order by mno asc");
			ArrayList<Member> members = new ArrayList<Member>();
			// select 결과 가져오기 (행(row)을 가져옴)
			//DB에서 회원정보를 가져와 Member 객체에 담는다.
			//그리고 Member 객체를 ArrayList에 add한다.
			while (rs.next()) {
				members.add(new Member()
						.setNo(rs.getInt("mno"))
						.setName(rs.getString("mname"))
						.setEmail(rs.getString("email"))
						.setCreatedDate(rs.getDate("cre_date")));
			}
			return members;
		} catch(Exception e) {
			throw e;
		} finally {
			try {if (rs != null) rs.close(); } catch(Exception e) {}
			try {if (stmt != null) stmt.close(); } catch(Exception e) {}
			try {if (connection != null) connection.close(); } catch(Exception e) {}
		}
	}

	public int insert(Member member) throws Exception {
		Connection connection = null;
		PreparedStatement stmt = null;

		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement(
					"INSERT INTO MEMBERS(EMAIL,PWD,MNAME,CRE_DATE,MOD_DATE)" + " VALUES (?,?,?,NOW(),NOW())");
			// setXXX() 메소드를 호출하여 ?값을 설정합니다.
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getPassword());
			stmt.setString(3, member.getName());
			// select문은 executeQuery(), 나머지는 executeUpdate()
			return stmt.executeUpdate();

		} catch (Exception e) {
			throw e;

		} finally {
		      try {if (stmt != null) stmt.close();} catch(Exception e) {}
		      try {if (connection != null) connection.close(); } catch(Exception e) {}
	    }
	}
	
	public int delete(int no) throws Exception {
		/* 회원 삭제 */
		Connection connection = null;
		Statement stmt = null;

		try {
			connection = ds.getConnection();
			stmt = connection.createStatement();
			return stmt.executeUpdate(
					"DELETE FROM MEMBERS WHERE MNO=" + no);
			
		} catch (Exception e) {
			throw e;
			
		} finally {
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
			try {if (connection != null) connection.close(); } catch(Exception e) {}
		}
	}
	
	public Member selectOne(int no) throws Exception {
		/* 회원 상세 정보 조회 */
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			connection = ds.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(
					"SELECT MNO,EMAIL,MNAME,CRE_DATE FROM MEMBERS" + " WHERE MNO=" + no);
			// 단 한명의 회원 정보를 가져오기 때문에 next() 한 번만 호출
			if (rs.next()) {
					return new Member()
						.setNo(rs.getInt("MNO"))
						.setEmail(rs.getString("EMAIL"))
						.setName(rs.getString("MNAME"))
						.setCreatedDate(rs.getDate("CRE_DATE"));
				
			} else {
				throw new Exception("해당 번호의 회원을 찾을 수 없습니다.");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {if (rs != null) rs.close();} catch(Exception e) {}
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
			try {if (connection != null) connection.close(); } catch(Exception e) {}
		}
	}
	
	public int update(Member member) throws Exception {
		/* 회원 정보 변경 */
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement("UPDATE MEMBERS SET EMAIL=?,MNAME=?,MOD_DATE=now()" + " WHERE MNO=?");
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getName());
			stmt.setInt(3, member.getNo());
			return stmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
			try {if (connection != null) connection.close(); } catch(Exception e) {}
		}
	}
	
	public Member exist(String email, String password) throws Exception {
		/* 있으면 Member객체 리턴, 없으면 null 리턴 */
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement(
					"select mname,email from members where email=? and pwd=?");
			stmt.setString(1, email);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return new Member()
						.setName(rs.getString("mname"))
						.setEmail(rs.getString("email"));
			} else {
				return null;
			}
		} catch(Exception e) {
			throw e;
		} finally {
			try {if (rs != null) rs.close(); } catch (Exception e) {}
			try {if (stmt != null) rs.close(); } catch (Exception e) {}
			try {if (connection != null) connection.close(); } catch(Exception e) {}
		}
	}
}
