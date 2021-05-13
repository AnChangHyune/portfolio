package com.MpaUsrHome.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
	private int id;
	private String regDate;
	private String updateDate;
	private int boardId;
	private int memberId;
	private String title;
	private String body;
	private boolean blindStatus;
    private String blindDate;
    private boolean delStatus;
    private String delDate;
    private int hitCount;
    private int repliesCount;
    private int likeCount;
    private int dislikeCount;
    
    private Map<String, Object> extra;
    
    private String extra__writerName;
    
    public String getBodyForPrint() {
    	String bodyForPrint = body.replaceAll("\r\n", "<br>");
    	bodyForPrint = bodyForPrint.replaceAll("\r", "<br>");
    	bodyForPrint = bodyForPrint.replaceAll("\n", "<br>");
    	
    	return bodyForPrint;
    }
}
