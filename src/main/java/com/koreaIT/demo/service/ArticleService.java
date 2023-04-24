package com.koreaIT.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreaIT.demo.repository.ArticleRepository;
import com.koreaIT.demo.util.Utility;
import com.koreaIT.demo.vo.Article;
import com.koreaIT.demo.vo.ResultData;

@Service
public class ArticleService {

private ArticleRepository articleRepository;
	
	@Autowired
	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}
	
	public void writeArticle(String title, String body, int memberId) {
		articleRepository.writeArticle(title, body, memberId);
	}

	public int getLastInsertId() {
		return articleRepository.getLastInsertId();
	}
	
	public Article getArticleById(int id) {
		return articleRepository.getArticleById(id);
	}
	
	public List<Article> getArticles(){
		return articleRepository.getArticles();
	}
	
	public ResultData<Article> modifyArticle(int id, String title, String body) {
		articleRepository.modifyArticle(id, title, body);
		Article article = getArticleById(id);
		return ResultData.from("S-1", Utility.f("%d번 게시글이 수정되었습니다.", id), "Article", article);
	}
	
	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
	}

	public ResultData actorCanModify(int HttpSessoin, int id, String title, String body) {
		
		Article article = getArticleById(id);
		if(article == null) {
			return ResultData.from("F-B",Utility.f("%d번 글은 존재하지 않습니다.", id));
		}
		
		if(article.getMemberId() != HttpSessoin) {
			return ResultData.from("F-C",Utility.f("권한이 없습니다"));
		}
		
		return ResultData.from("S-A", "수정가능");
	}

	public Article getForPrintArticle(int loginedMemberId, int id) {

		Article article = articleRepository.getForPrintArticle(id);

		actorCanChangeData(loginedMemberId, article);

		return article;
	}

	private void actorCanChangeData(int loginedMemberId, Article article) {
		if(article == null) {
			return;
		}

		ResultData actorCanChangeDataRd = actorCanDelete(loginedMemberId, article);

		article.setActorCanChangeData(actorCanChangeDataRd.isSuccess());
	}

	private ResultData actorCanDelete(int loginedMemberId, Article article) {
		if(article == null) {
			return ResultData.from("F-1", "해당 게시물은 존재하지 않습니다");
		}

		if (loginedMemberId != article.getMemberId()) {
			return ResultData.from("F-B", "해당 게시물에 대한 권한이 없습니다");	
		}

		return ResultData.from("S-1", "삭제 가능");
	}
}
