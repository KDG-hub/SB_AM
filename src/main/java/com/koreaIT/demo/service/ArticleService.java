package com.koreaIT.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreaIT.demo.repository.ArticleRepository;
import com.koreaIT.demo.vo.Article;
import com.koreaIT.demo.vo.ResultData;

@Service
public class ArticleService {

private ArticleRepository articleRepository;
	
	@Autowired
	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}
	
	public void writeArticle(int memberId, int boardId, String title, String body) {
		articleRepository.writeArticle(memberId, boardId, title, body);
	}

	public int getLastInsertId() {
		return articleRepository.getLastInsertId();
	}
	
	public Article getArticleById(int id) {
		return articleRepository.getArticleById(id);
	}
	
	public List<Article> getArticles(int boardId, int page, int itemsInAPage, String searchKeywordType, String searchKeyword){
		int limitStart = (page-1) *itemsInAPage;
		
		return articleRepository.getArticles(boardId, limitStart, itemsInAPage , searchKeywordType, searchKeyword);
	}
	
	public void modifyArticle(int id, String title, String body) {
		articleRepository.modifyArticle(id, title, body);
	
	}
	
	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
	}

	public ResultData actorCanMD(int loginedMemberId, Article article) {
		if(article == null) {
			return ResultData.from("F-1", "해당 게시물은 존재하지 않습니다");
		}
		
		if (loginedMemberId != article.getMemberId()) {
			return ResultData.from("F-B", "해당 게시물에 대한 권한이 없습니다");	
		}
		
		return ResultData.from("S-1", "가능");
	}

	public Article getForPrintArticle(int id) {
		
		Article article = articleRepository.getForPrintArticle(id);
		
		
		return article;
	}

	public void actorCanChangeData(int loginedMemberId, Article article) {
		
		ResultData actorCanChangeDataRd = actorCanMD(loginedMemberId, article);
		
		article.setActorCanChangeData(actorCanChangeDataRd.isSuccess());
	}

	public int getBoardCount(int boardId, String searchKeywordType, String searchKeyword) {
		
		return articleRepository.getBoardCount(boardId, searchKeywordType, searchKeyword);
	}

	public void increaseViewCnt(int id) {
		articleRepository.increaseViewCnt(id);
	}

	public void upReactionPoint(int memberId, int relTypeCode) {
		articleRepository.upReactionPoint(memberId, relTypeCode);
		
	}

}
