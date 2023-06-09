package com.koreaIT.demo.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koreaIT.demo.service.ArticleService;
import com.koreaIT.demo.service.BoardService;
import com.koreaIT.demo.util.Utility;
import com.koreaIT.demo.vo.Article;
import com.koreaIT.demo.vo.Board;
import com.koreaIT.demo.vo.ResultData;
import com.koreaIT.demo.vo.Rq;

@Controller
public class UsrArticleController {

	private ArticleService articleService;
	private BoardService boardService;

	@Autowired
	public UsrArticleController(ArticleService articleService, BoardService boardService) {
		this.articleService = articleService;
		this.boardService = boardService;
	}
	
	@RequestMapping("/usr/article/write")
	public String Write() {
		return "usr/article/write";
	}
	
	
	// 액션 메서드
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public String doAdd(HttpServletRequest req, int boardId, String title, String body) {

		Rq rq =(Rq) req.getAttribute("rq");

		if (Utility.empty(title)) {
			return Utility.jsHistoryBack("제목을 입력해주세요");
		}

		if (Utility.empty(body)) {
			return Utility.jsHistoryBack("내용을 입력해주세요");
		}

		articleService.writeArticle(rq.getLoginedMemberId(), boardId, title, body);

		int id = articleService.getLastInsertId();

		return Utility.jsReplace("게시글이 작성되었습니다.", Utility.f("detail?id=%d", id));
	}

	@RequestMapping("/usr/article/list")
	public String list(HttpServletRequest req, Model model, @RequestParam(defaultValue = "2") int boardId, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "title") String searchKeywordType,
			@RequestParam(defaultValue = "") String searchKeyword) {

		Board board = boardService.getBoardById(boardId);
		Rq rq = (Rq) req.getAttribute("rq");	
		
		if(page <= 0) {
			return rq.jsReturnOnView("페이지 번호가 옳바르지않습니다.", true);
		}
		
		if(board == null) {
			return rq.jsReturnOnView("존재하지 않는 게시판입니다.", true);
		}
		
		int articlesCnt = articleService.getBoardCount(boardId, searchKeywordType, searchKeyword);
		
		int itemsInAPage = 10;
		int pagesCount =(int) Math.ceil((double)articlesCnt / itemsInAPage);
		
		List<Article> articles = articleService.getArticles(boardId, page, itemsInAPage , searchKeywordType, searchKeyword);

		model.addAttribute("articles", articles);
		model.addAttribute("board",board);
		model.addAttribute("articlesCnt", articlesCnt);
		model.addAttribute("pagesCount", pagesCount);
		model.addAttribute("page", page);

		
		return "usr/article/list";
	}
	
	@RequestMapping("/usr/article/detail")
	public String detail(Model model, HttpServletRequest req, HttpServletResponse resp, int id) {

		Rq rq =(Rq) req.getAttribute("rq");

		Cookie oldCookie = null;
		Cookie[] cookies = req.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("viewCount")) {
					oldCookie = cookie;
				}
			}
		}

		if (oldCookie != null) {
			if (!oldCookie.getValue().contains("[" + id + "]")) {
				articleService.increaseViewCnt(id);
				oldCookie.setValue(oldCookie.getValue() + "_[" + id + "]");
				oldCookie.setPath("/");
				oldCookie.setMaxAge(30 * 60);
				resp.addCookie(oldCookie);
			}
		} else {
			articleService.increaseViewCnt(id);
			Cookie newCookie = new Cookie("viewCount", "[" + id + "]");
			newCookie.setPath("/");
			newCookie.setMaxAge(30 * 60);
			resp.addCookie(newCookie);
		}

		Article article = articleService.getForPrintArticle(id);
		
		articleService.actorCanChangeData(rq.getLoginedMemberId(),article);

		model.addAttribute("article", article);

		return "usr/article/detail";
	}
	
	@RequestMapping("/usr/article/upReactionPoint")
	@ResponseBody
	public void upReactionPoint(HttpServletRequest req, int memberId, int relTypeCode) {
		articleService.upReactionPoint(memberId, relTypeCode);
	}
	
	@RequestMapping("/usr/article/modify")
	public String modify(HttpServletRequest req, Model model ,int id) {
		Rq rq =(Rq) req.getAttribute("rq");
		
		Article article = articleService.getForPrintArticle(id);

		ResultData actorCanMD = articleService.actorCanMD(rq.getLoginedMemberId(), article);
		 
		if (actorCanMD.isFail()) {
			return rq.jsReturnOnView(actorCanMD.getMsg(), true);
		}
		
		model.addAttribute("article", article);
		
		return "usr/article/modify";
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(HttpServletRequest req, int id, String title, String body) {
		
		Rq rq =(Rq) req.getAttribute("rq");
		
		Article article = articleService.getForPrintArticle(id);

		ResultData actorCanMD = articleService.actorCanMD(rq.getLoginedMemberId(), article);
		 
		if (actorCanMD.isFail()) {
			return rq.jsReturnOnView(actorCanMD.getMsg(), true);
		}
		
		if(Utility.empty(title)) {
			return Utility.jsHistoryBack("제목을 입력해주세요");
		}
		
		articleService.modifyArticle(id, title, body);

		return Utility.jsReplace("게시물을 수정했습니다", Utility.f("detail?id=%d", id));
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, int id) {

		Rq rq =(Rq) req.getAttribute("rq");

		Article article = articleService.getArticleById(id);

		ResultData actorCanModifyRd = articleService.actorCanMD(rq.getLoginedMemberId(), article);

		if (actorCanModifyRd.isFail()) {
			return Utility.jsHistoryBack(actorCanModifyRd.getMsg());
		}

		articleService.deleteArticle(id);

		return Utility.jsReplace(Utility.f("%d번 게시물을 삭제했습니다", id), "list");
	}
}