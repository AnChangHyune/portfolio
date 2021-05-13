package com.MpaUsrHome.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.MpaUsrHome.dto.Article;
import com.MpaUsrHome.dto.Board;


@Mapper
public interface ArticleDao {

	void modifyArticle(@Param("id")int id, @Param("title")String title, @Param("body")String body);

	void deleteArticleById(@Param("id") int id);

	Article getArticleById(@Param("id") int id);

	int writeArticle(@Param("boardId") int boardId, @Param("memberId") int memberId, @Param("title") String title, @Param("body") String body);

	int getLastInsertId();

	Board getBoardById(@Param("id") int id);

	int getArticlesTotalCount(
			@Param("boardId") int boardId,
			@Param("searchKeyword")String searchKeyword,
			@Param("searchKeywordType")String searchKeywordType);

	List<Article> getForPrintArticles(
			@Param("boardId") int boardId,
			@Param("searchKeyword") String searchKeyword,
			@Param("searchKeywordType") String searchKeywordType,
			@Param("limitFrom") int limitFrom,
			@Param("limitTake")int limitTake);
	
}
