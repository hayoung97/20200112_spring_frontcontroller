package article_manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ArticleDao {
	
	// JDBC의 명령을 받아 실제로 DBMS와 통신할 실무자를 선택한다.
	String dbDriver = "com.mysql.jdbc.Driver";

	// 내(JSP)가 통신할 DB서버의 위치와 DB의 이름, 그리고 연결 옵션들 세팅
	String dbUrl = "jdbc:mysql://localhost:3306/board?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
	// DB서버의 사용자명
	String dbUser = "root";
	// DB서버의 사용자 비번
	String dbPass = "";

	// DB연결객체 변수
	Connection con = null;
	// SQL 작성양식 변수
	Statement stmt = null;
	// SQL 쿼리 응답 변수
	ResultSet rs = null;
	
	public List<Article> getAllArticle() {

		// 실제로 DB서버에 이야기할 내용
		String sql = "SELECT * FROM article ORDER BY id DESC";

		// DB서버에 쿼리를 날리면 결과가 리턴되는데, 그 결과는 압축되어 있어서 바로 쓰기가 곤란하다.
		// 그래서 사용하기 편한 용기(?)에 담을 계획이다.
		// 여러가지 면에서 봤을 때 게시물 데이터들을 담을 용기로 리스트가 선정되었다.
		// 그리고 각각의 게시물은 DTO (Article)fh 저장하기로 결정했다. 
		ArrayList<Article> articles = new ArrayList<Article>();

		try {
			// DB연결을 하기 전에 실무자를 선택한다.
			// 즉 DB 드라이버 활성화
			Class.forName(dbDriver);

			// DB와 연결
			con = DriverManager.getConnection(dbUrl, dbUser, dbPass);
			// DB연결객체에게 빈 양식 하나를 만들어서 달라고 한다.
			stmt = con.createStatement();
			// 양식을 실행(?)하면 결과가 온다. 단 압축되어 있다.
			rs = stmt.executeQuery(sql);

			// rs.next() rs 라는 책을 다음 장으로 넘긴다.
			// 만약에 쿼리 결과 행이 4개(줄이 4개)가 나왔다면 rs 안의 페이지는 총 (0, 1, 2, 3) 4페이지로 구성된다.
			// 다음장이 있으면 true, 없으면 false를 리턴한다.
			// 이 작업은 rs에 압축되어 있는 데이터를 압축해제해서 articles에 담기위한 것이다.
			while (rs.next()) {
				int id = rs.getInt("id"); // rs가 현재 선택한 페이지에서 id를 읽어간다.
				String regDate = rs.getString("regDate");
				String title = rs.getString("title");
				String body = rs.getString("body");
				String writer = rs.getString("writer");

				// 각각의 게시물 정보를 담을 해시맵 객체를 만든다.
				Article article = new Article();
				article.setId(id);
				article.setRegDate(regDate);
				article.setTitle(title);
				article.setBody(body);
				article.setNick(writer);

				// 게시물들(list)에 게시물(article)을 추가한다.
				articles.add(article);
			}

		} catch (SQLException e) {
			// 위 try 구문에서 SQLException이 발생했다면 여기가 실행한다.
			System.out.println("SQLException : " + e.getMessage());
		} catch (ClassNotFoundException e) {
			// 위 try 구문에서 ClassNotFoundException 발생했다면 여기가 실행한다.
			System.out.println("ClassNotFoundException : " + e.getMessage());
		} finally {
			free(rs, stmt, con);
		}
		
		return articles;
	}
	
	public Article getArticleById(int id) {
		
		String sql = "SELECT * FROM article WHERE id=" + id;
		Article a = null;
		
		try {
			Class.forName(dbDriver);

			con = DriverManager.getConnection(dbUrl, dbUser, dbPass);

			stmt = con.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			
			if(rs.next()) {
				a = new Article();
				a.setId(rs.getInt("id"));
				a.setTitle(rs.getString("title"));
				a.setBody(rs.getString("body"));
				a.setNick(rs.getString("writer"));
				a.setRegDate(rs.getString("regDate"));
			}

		} catch (SQLException e) {
			// 위 try 구문에서 SQLException이 발생했다면 여기가 실행한다.
			System.out.println("SQLException : " + e.getMessage());
		} catch (ClassNotFoundException e) {
			// 위 try 구문에서 ClassNotFoundException 발생했다면 여기가 실행한다.
			System.out.println("ClassNotFoundException : " + e.getMessage());
		} finally {
			free(rs, stmt, con);
		}
		
		return a;
	}
	
	public void insertArticle(Article a) {
		
		String sql = "INSERT INTO article SET title = '" +a.getTitle() +"'"
				 + ", body = '" + a.getBody() + "'"
				 + ", writer = '" + a.getNick() + "'"
				 + ", regDate = now()"
				 + ", boardId = 1"
				 + ", memberId = 1"
				 + ", hit = 0"
				 + ", passwd = ''";
		
		try {
			Class.forName(dbDriver);

			con = DriverManager.getConnection(dbUrl, dbUser, dbPass);

			stmt = con.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			// 위 try 구문에서 SQLException이 발생했다면 여기가 실행한다.
			System.out.println("SQLException : " + e.getMessage());
		} catch (ClassNotFoundException e) {
			// 위 try 구문에서 ClassNotFoundException 발생했다면 여기가 실행한다.
			System.out.println("ClassNotFoundException : " + e.getMessage());
		} finally {
			free(rs, stmt, con);
		}
	}

	public void updateArticle(int id, String title, String body) {

		// 실제로 DB서버에 이야기할 내용
		String sql = "UPDATE article SET title = '" + title + "'"
				+ ", body = '" + body + "'"
				+ " where id = " + id;

		try {
			Class.forName(dbDriver);

			con = DriverManager.getConnection(dbUrl, dbUser, dbPass);

			stmt = con.createStatement();
			
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			// 위 try 구문에서 SQLException이 발생했다면 여기가 실행한다.
			System.out.println("SQLException : " + e.getMessage());
		} catch (ClassNotFoundException e) {
			// 위 try 구문에서 ClassNotFoundException 발생했다면 여기가 실행한다.
			System.out.println("ClassNotFoundException : " + e.getMessage());
		} finally {
			free(rs, stmt, con);
		}
	}
	
	public void deleteArticle(int id) {

		// 실제로 DB서버에 이야기할 내용
		String sql = "DELETE FROM article WHERE id = " + id;

		try {
			Class.forName(dbDriver);

			con = DriverManager.getConnection(dbUrl, dbUser, dbPass);

			stmt = con.createStatement();
			
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			// 위 try 구문에서 SQLException이 발생했다면 여기가 실행한다.
			System.out.println("SQLException : " + e.getMessage());
		} catch (ClassNotFoundException e) {
			// 위 try 구문에서 ClassNotFoundException 발생했다면 여기가 실행한다.
			System.out.println("ClassNotFoundException : " + e.getMessage());
		} finally {
			free(rs, stmt, con);
		}
	}
	
	void free(ResultSet rs, Statement pstmt, Connection con) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
