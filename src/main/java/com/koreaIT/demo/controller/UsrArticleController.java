package com.koreaIT.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koreaIT.demo.service.ArticleService;
import com.koreaIT.demo.util.Utility;
import com.koreaIT.demo.vo.Article;
import com.koreaIT.demo.vo.ResultData;

@Controller
public class UsrArticleController {

	private ArticleService articleService;
	
	@Autowired //의존성 주입
	public UsrArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}
	
	// 액션 메서드
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public ResultData<Article> doAdd(HttpSession HttpSession, String title, String body) {
		if(HttpSession.getAttribute("loginedMemberId") == null) {
			return ResultData.from("F-A", Utility.f("로그인 후 이용해주세요."));
		}
		if(Utility.empty(title)) {
			return ResultData.from("F-1",Utility.f("제목을 작성해주세요."));
		}
		if(Utility.empty(body)) {
			return ResultData.from("F-2",Utility.f("내용을 작성해주세요."));
		}
		
		int memberId = (int)HttpSession.getAttribute("loginedMemberId");
		
		articleService.writeArticle(title, body, memberId);
		
		int id = articleService.getLastInsertId();
	
		return ResultData.from("S-1",Utility.f("%d글이 작성되었습니다.", articleService.getLastInsertId()));
	}
	
	@RequestMapping("/usr/article/detail")
	public String showDetail(Model model, int id) {
 
		Article article = articleService.getForPrintArticle(id);
		model.addAttribute("article", article);
		
		return "usr/article/detail";
		}
	
	@RequestMapping("/usr/article/list")
	public String showList(Model model) {
		List<Article> articles = articleService.getArticles();
		model.addAttribute("articles",articles);
	
		return "usr/article/list";
	}
	
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData<Article> doModify(HttpSession HttpSession, int id, String title, String body) {
		if(HttpSession == null) {
			return ResultData.from("F-A", Utility.f("로그인 후 이용해주세요."));
		}

		ResultData actorCanModifyRd = articleService.actorCanModify((int)HttpSession.getAttribute("loginedMemberId"), id, title, body);
		
		if(actorCanModifyRd.isFail()) {
			return actorCanModifyRd;
		}
		
		return articleService.modifyArticle(id, title, body);
	}
	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public ResultData doDelete(HttpSession HttpSession, int id) {
		if(HttpSession.getAttribute("loginedMemberId") == null) {
			return ResultData.from("F-A", Utility.f("로그인 후 이용해주세요."));
		}
		
		Article article = articleService.getArticleById(id);
		
		if(article == null) {
			return ResultData.from("F-B",Utility.f("%d번 글은 존재하지 않습니다.", id));
		}
		if(article.getMemberId() != (int)HttpSession.getAttribute("loginedMemberId")) {
			return ResultData.from("F-C",Utility.f("권한이 없습니다"));
		}
		
		articleService.deleteArticle(id);
		
		return ResultData.from("S-1",Utility.f("%d번 글이 삭제되었습니다.", id));
	}
}
