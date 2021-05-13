package com.MpaUsrHome.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.MpaUsrHome.dto.Article;
import com.MpaUsrHome.dto.Board;
import com.MpaUsrHome.dto.ResultData;
import com.MpaUsrHome.service.ArticleService;
import com.MpaUsrHome.util.Util;

@Controller
public class MpaUsrArticleController {
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping("/mpaUsr/article/list")
	public String showList(HttpServletRequest req, @RequestParam(defaultValue = "1") int boardId, String searchKeywordType, String searchKeyword,
			@RequestParam(defaultValue = "1") int page) {
		Board board = articleService.getBoardById(boardId);
		
		if ( Util.isEmpty(searchKeywordType) ) {
			searchKeywordType = "titleAndBody";
		}
		
		if (board == null) {
			return Util.msgAndBack(req, boardId + "번 게시판이 존재하지 않습니다.");
		}

		req.setAttribute("board", board);

		int totalItemsCount = articleService.getArticlesTotalCount(boardId, searchKeywordType, searchKeyword);
		
		if ( searchKeyword == null || searchKeyword.trim().length() == 0 ) {
			
		}

		req.setAttribute("totalItemsCount", totalItemsCount);

		// 한 페이지에 보여줄 수 있는 게시물 최대 개수
		int itemsCountInAPage = 20;
		// 총 페이지 수
		int totalPage = (int) Math.ceil(totalItemsCount / (double) itemsCountInAPage);

		// 현재 페이지(임시)
		req.setAttribute("page", page);
		req.setAttribute("totalPage", totalPage);

		List<Article> articles = articleService.getForPrintArticles(boardId, searchKeywordType, searchKeyword, itemsCountInAPage, page);

		req.setAttribute("articles", articles);

		return "mpaUsr/article/list";
	}
	
	@RequestMapping("mpaUsr/article/doWrite")
	@ResponseBody
	public ResultData doWrite(String title, String body) {
		return articleService.writeArticle(title, body);
	}
	
	@RequestMapping("mpaUsr/article/doDelete")
	public String doDelete(HttpServletRequest req, int id) {
		if(Util.isEmpty(id)) {
			return Util.msgAndBack(req, "삭제할 번호를 입력해주세요.");
		}
		
		ResultData rd = articleService.deleteArticleById(id);
		
		if(rd.isFail()) {
			return Util.msgAndBack(req, rd.getMsg());
		}
		
		String redirect = "../article/list?boardId="+rd.getBody().get("boardId");
		
		return Util.msgAndReplace(req, rd.getMsg(), redirect);
	}
	

	@RequestMapping("mpaUsr/article/getArticle")
	@ResponseBody
	public ResultData getArticle(int id) {
		Article article = articleService.getArticleById(id);
		
		if(article == null) {
			return new ResultData("S-1", id+"번 게시물은 존재하지 않습니다.", "id",id);
		}
		return new ResultData("S-1", id+"번 게시물 입니다.", "article",article);
	}
	
	@RequestMapping("mpaUsr/article/doModify")
	@ResponseBody
	public ResultData doModify(int id, String title, String body) {
		return articleService.modifyArticle(id, title, body);
	}
}
