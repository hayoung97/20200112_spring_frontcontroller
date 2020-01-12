package article_manager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FrontController
 */
@WebServlet("*.do")
public class FrontController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 공통 코드
		System.out.println("공통 코드");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		String[] uriBits = request.getRequestURI().split("/");
		String controllerName = uriBits[2];
		String funcName = uriBits[3];

		if (controllerName.equals("article")) {
			ArticleController controller = new ArticleController();

			if (funcName.equals("list.do")) {
				controller.list(request, response);
			} else if (funcName.equals("detail.do")) {
				controller.detail(request, response);
			} else if (funcName.equals("add.do")) {
				controller.add(request, response);
			} else if (funcName.equals("update.do")) {
				controller.update(request, response);
			} else if (funcName.equals("delete.do")) {
				controller.delete(request, response);
			} else if (funcName.equals("goAddForm.do")) {
				controller.goAdd(request, response);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
