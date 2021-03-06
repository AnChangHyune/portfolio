package com.MpaUsrHome.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.MpaUsrHome.dao.ArticleDao;
import com.MpaUsrHome.dto.Article;
import com.MpaUsrHome.dto.Board;
import com.MpaUsrHome.dto.ResultData;

@Service
public class ArticleService {
	@Autowired
	private ArticleDao articleDao;
	
	public ResultData modifyArticle(int id, String title, String body) {
		Article article = getArticleById(id);
		
		if(article == null) {
			return new ResultData("S-1", id+"번 게시물은 존재하지 않습니다.", "id",id);
		}
		
		articleDao.modifyArticle(id, title, body);
		
		return new ResultData("S-1", id+"번 게시물을 수정하였습니다.", "article", getArticleById(id));
	}
	
	public ResultData deleteArticleById(int id) {
		Article article = getArticleById(id);
		if(article == null) {
			return new ResultData("S-1", id+"번 게시물은 존재하지 않습니다.", "id",id);
		}
		articleDao.deleteArticleById(id);
		return new ResultData("S-1", id+"번 게시물을 삭제하였습니다.", "id",id);
	}
	
	public Article getArticleById(int id) {
		return articleDao.getArticleById(id);
	}
	
	public ResultData writeArticle(String title, String body) {
		int boardId = 3;
		int memberId = 3;
		articleDao.writeArticle(boardId, memberId, title, body);
		int id = articleDao.getLastInsertId();
		
		return new ResultData("S-1", "게시물이 등록되었습니다.", "id",id);
	}

	public Board getBoardById(int id) {
		return articleDao.getBoardById(id);
	}

	public int getArticlesTotalCount(int boardId, String searchKeyword, String searchKeywordType) {
		if(searchKeyword != null && searchKeyword.length() == 0) {
			searchKeyword = null;
		}
		return articleDao.getArticlesTotalCount(boardId, searchKeyword, searchKeywordType);
	}

	public List<Article> getForPrintArticles(int boardId, String searchKeywordType, String searchKeyword,
			int itemsCountInAPage, int page) {
		if(searchKeyword != null && searchKeyword.length() == 0) {
			searchKeyword = null;
		}
		
		int limitFrom = (page - 1) * itemsCountInAPage;
		int limitTake = itemsCountInAPage;
		return articleDao.getForPrintArticles(boardId, searchKeyword, searchKeywordType, limitFrom, limitTake);
	}
}
