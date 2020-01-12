package article_manager;

import java.util.ArrayList;

/**
 * 게시물 관리를 위한 객체.
 * 게시물을 조회 등의 기능을 제공할 것.
 */
public class ArticleManager {
	
	//1. 게시물을 저장할 저장소 만들기.(ArrayList) 게시물의 저장을 담당.  
	public static ArrayList<Article> aList = new ArrayList<Article>(); //모든 Article_manager 객체가 공유할 수 있도록 static으로 선언.
	static int lastId; // 가장 마지막 게시물의 아이디를 기억함으로써 새로운 게시물을 추가할 때 다음 아이디 번호를 부여할 수 있다.
	
	//2. 테스트 게시물 5개 만들기 위한 초기화 메서드
	public void init() {
		
		// aList가 비어있을 때만 초기화 데이터를 만들고 그 외에는 만들지 않는다.
		if(aList.size() == 0) {
			
			// Article 객체를 이용해 각각 게시물을 5개 만든다.
			Article a1 = new Article();
			
			a1.setId(1);
			a1.setTitle("제목1");
			a1.setBody("내용1");
			a1.setNick("작성자1");
			a1.setRegDate("20190817");
			
			Article a2 = new Article();
			
			a2.setId(2);
			a2.setTitle("제목2");
			a2.setBody("내용2");
			a2.setNick("작성자2");
			a2.setRegDate("20190817");

			Article a3 = new Article();
			
			a3.setId(3);
			a3.setTitle("제목3");
			a3.setBody("내용3");
			a3.setNick("작성자3");
			a3.setRegDate("20190817");

			Article a4 = new Article();
			
			a4.setId(4);
			a4.setTitle("제목4");
			a4.setBody("내용4");
			a4.setNick("작성자4");
			a4.setRegDate("20190817");

			Article a5 = new Article();
			
			a5.setId(5);
			a5.setTitle("제목5");
			a5.setBody("내용5");
			a5.setNick("작성자5");
			a5.setRegDate("20190817");
			
			// 만들어진 5개의 게시물을 aList 저장소에 담는다.
			aList.add(a1);
			aList.add(a2);
			aList.add(a3);
			aList.add(a4);
			aList.add(a5);
			
			lastId = 5; // 5개를  저장했으므로 마지막 아이디는 5이다.

		}
	}
	
	//3. 조회 메서드를 만들어 저장소에 있는 게시물 가져오기
	public ArrayList<Article> getAllArticles() {
		return this.aList;
	}
	
	//4. 번호로 특정 게시물 index 찾기
	public int getIndexById(int id) {
		int idx = -1;
		for(int i = 0; i < aList.size(); i++) {
			idx++;
			if(aList.get(i).getId() == id ) {
				return idx;
			}
		}
		
		return idx;
	}
	
	// 5. 아이디로 특정 게시물 찾기
	public Article getArticleById(int id) {
		
		Article a = null;
		
		int i = getIndexById(id);
		
		if(i > -1) {
			a = aList.get(i);			
		}
		
		return a;
		
		
	}
	//6. 게시물 삽입하기
	public void insertArticle(Article a) {
		a.setId(lastId+1);
		a.setRegDate("20200111");
		aList.add(a);
		lastId++;
	}
	
	//7. 특정 게시물 수정하기
	public void updateArticle(int id, String title, String body) {
		int i = getIndexById(id);
		
		if(i > -1) {
			aList.get(i).setBody(body);
			aList.get(i).setTitle(title);
		}
	}
	
	//8. 특정 게시물 삭제하기
	public void deleteArticleById(int id) {
		int i = getIndexById(id);
		
		if(i > -1) {
			aList.remove(i);
		}
	}
	
}
