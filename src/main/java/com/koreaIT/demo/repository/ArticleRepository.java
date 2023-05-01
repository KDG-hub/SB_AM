package com.koreaIT.demo.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.koreaIT.demo.vo.Article;

@Mapper
public interface ArticleRepository {

	// 서비스 메서드
	public void writeArticle(int memberId, int boardId, String title, String body);

	public int getLastInsertId();
	
//	@Select("""SELECT * FROM article WHERE ID = #{ID}")
	public Article getArticleById(int id);
	
//	@Select("SELECT * FROM article ORDER BY id DESC")
	public List<Article> getArticles(int boardId, int limitStart, int itemsInAPage);
	
	/*
	//방법1
	@Update("UPDATE article SET updateDate = NOW(), title = #{title}, `body` = #{body} WHERE id = #{id}")
	//방법2
	@Update("""
			UPDATE article SET updateDate = NOW(),
			title = #{title},
			`body` = #{body}
			WHERE id = #{id}
			""")
	UPDATE article
				SET updateDate = NOW(),
				<if test="title != null and title = ''">
					title = #{title},
				</if>
				<if test="body != null and body = ''">
				`body` = #{body}
				</if>
			WHERE id = #{id}
			*/
	public void modifyArticle(int id, String title, String body);
	
//	@Delete("DELETE FROM article WHERE id = #{id}")
	public void deleteArticle(int id);

	public Article getForPrintArticle(int id);

	public int getBoardCount(int boardId);

}