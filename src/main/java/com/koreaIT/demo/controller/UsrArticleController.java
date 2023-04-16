package com.koreaIT.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koreaIT.demo.vo.Article;

@Controller
public class UsrArticleController {
	
	private int lastArticleId;
	private List<Article> articles;
	
	//서비스 메서드
	public UsrArticleController() {
		this.lastArticleId = 0;
		this.articles = new ArrayList<>();
		makeTestData();
	}
	
	private void makeTestData() {
		for(int i = 1; i<=10; i++) {
			
			String title = "제목"+1;
			String body = "내용"+1;
			
			wrtieArticle(title, body); 
		}
	}
	
	private Article wrtieArticle(String title, String body) {
		int id = this.lastArticleId +1;
		this.lastArticleId = id;
		Article article = new Article(id, title, body); 
		
		articles.add(article);
		return article;
	}
	
	private Article getArticleById(int id) {
		for(Article article : articles) {
			if(article.getId() == id) {
				return article;
			}	
		}
		return null;
	}
	
	private void modifyArticle(int id, String title, String body) {
		Article article = getArticleById(id);
		
		article.setTitle(title);
		article.setBody(body);
	}

	//액션 메서드
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {
		Article article = wrtieArticle(title, body);
		return article;
	}
	
	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {

		return this.articles;
	}
	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		Article foundArticle = getArticleById(id);
		
		if(foundArticle == null) {
			return id+"번 게시글은 존재하지않습니다.";
		}
		
		articles.remove(id);
		return id+"번 게시글이 삭제되었습니다.";
	}
	
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(int id, String title, String body) {
		Article foundArticle = getArticleById(id);
		
		if(foundArticle == null) {
			return id+"번 게시글은 존재하지않습니다.";
		}
		
		modifyArticle(id, title, body);
		return id+"번 게시글이 수정되었습니다.";
	}
}
