package com.koreaIT.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public String doAdd(HttpServletRequest req, String title, String body) {

		Rq rq =(Rq) req.getAttribute("rq");

		if (Utility.empty(title)) {
			return Utility.jsHistoryBack("제목을 입력해주세요");
		}

		if (Utility.empty(body)) {
			return Utility.jsHistoryBack("내용을 입력해주세요");
		}

		articleService.writeArticle(rq.getLoginedMemberId(), title, body);

		int id = articleService.getLastInsertId();

		return Utility.jsReplace("게시글이 작성되었습니다.", Utility.f("detail?id=%d", id));
	}

	@RequestMapping("/usr/article/detail")
	public String showDetail(Model model, HttpServletRequest req, int id) {

		Rq rq =(Rq) req.getAttribute("rq");

		Article article = articleService.getForPrintArticle(id);
		
		articleService.actorCanChangeData(rq.getLoginedMemberId(),article);

		model.addAttribute("article", article);

		return "usr/article/detail";
	}

	@RequestMapping("/usr/article/list")
	public String showList(Model model, int boardId) {
		
		Board board = boardService.getBoardById(boardId);
		List<Article> articles = articleService.getArticles(boardId);

		model.addAttribute("articles", articles);
		model.addAttribute("board",board);

		return "usr/article/list";
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