package article_manager;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ArticleController {

	ArticleDao dao = new ArticleDao();
	
	public void list(HttpServletRequest request, HttpServletResponse response) {
		
		List<Article> articles = dao.getAllArticle();
		request.setAttribute("articles", articles);
		
		forward(request, response, "/list.jsp");
	}
	
	public void detail(HttpServletRequest request, HttpServletResponse response) {
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		Article a = dao.getArticleById(id);
		request.setAttribute("article", a);
		
		forward(request, response, "/detail.jsp");
	}
	
	public void add(HttpServletRequest request, HttpServletResponse response) {
		// addForm.jsp 에서 넘긴 파라미터 데이터를 저장한다.
		String title = request.getParameter("title");
		String body = request.getParameter("body");
	 	String nick = request.getParameter("nick");
		
	 	// 새로운 게시물을 만들기 위해 Article 객체를 만든다.
		Article a = new Article();
		
	 	// 위에서 파라미터로 받은 게시물 정보를 셋팅해준다.
		a.setTitle(title);
		a.setBody(body);
		a.setNick(nick);
		
		ArticleDao dao = new ArticleDao();
		dao.insertArticle(a); // 게시물 관리자의 게시물 추가 기능을 이용해 만든 게시물을 저장한다.
		
		redirect(request, response, "list.do");
		//forward(request, response, "/list.jsp");
	}
	
	public void update(HttpServletRequest request, HttpServletResponse response) {
		
		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String body = request.getParameter("body");
		
		
		ArticleDao dao = new ArticleDao();
		dao.updateArticle(id, title, body);
		
		redirect(request, response, "list.do");
	}
	
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		ArticleDao dao = new ArticleDao();
		dao.deleteArticle(id);
		
		redirect(request, response, "list.do");
	}
	
	public void goAdd(HttpServletRequest request, HttpServletResponse response) {
		forward(request, response, "/addForm.jsp");
	}
	
	void forward(HttpServletRequest request, HttpServletResponse response, String url) {
		ServletContext context = request.getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(url); // 넘길 페이지 주소
		try {
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void redirect(HttpServletRequest request, HttpServletResponse response, String url) {
		try {
			response.sendRedirect(url);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
