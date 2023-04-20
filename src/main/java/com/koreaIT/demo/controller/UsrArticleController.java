package com.koreaIT.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	public ResultData<Article> doAdd(String title, String body) {
		if(Utility.empty(title)) {
			return ResultData.from("F-1",Utility.f("제목을 작성해주세요."));
		}
		if(Utility.empty(body)) {
			return ResultData.from("F-2",Utility.f("내용을 작성해주세요."));
		}
		articleService.writeArticle(title, body);
		
		int id = articleService.getLastInsertId();
	
		return ResultData.from("S-1",Utility.f("%d글이 작성되었습니다.", articleService.getLastInsertId()));
	}
	
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData<Article> getArticle(int id) {

		Article article = articleService.getArticleById(id);

		if(article == null) {
			return ResultData.from("F-1", Utility.f("%d번 게시물은 존재하지 않습니다.", id));
		}
		return ResultData.from("S-1", Utility.f("%d번 게시물 입니다", id), article);
		}
	
	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public ResultData<List<Article>> getArticles() {
		return ResultData.from("S-1", Utility.f("게시물 리스트"), articleService.getArticles());
	}
	
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData doModify(int id, String title, String body) {
		
		Article article = articleService.getArticleById(id);
		
		if(article == null) {
			return ResultData.from("F-1",Utility.f("%d번 글은 존재하지 않습니다.", id));
		}
		
		articleService.modifyArticle(id, title, body);
		
		return ResultData.from("S-1",Utility.f("%d번 글이 수정되었습니다.", id));
	}
	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public ResultData doDelete(int id) {
		
		Article article = articleService.getArticleById(id);
		
		if(article == null) {
			return ResultData.from("F-1",Utility.f("%d번 글은 존재하지 않습니다.", id));
		}
		
		articleService.deleteArticle(id);
		
		return ResultData.from("S-1",Utility.f("%d번 글이 삭제되었습니다.", id));
	}
}
