package com.imaginea.crawler.pojo;

import org.joda.time.DateTime;

public class Mail {
	
	private String from;
	private String content;
	private String subject;
	private DateTime sendDate;
	
	public DateTime getSendDate() {
		return sendDate;
	}
	public void setSendDate(DateTime sendDate) {
		this.sendDate = sendDate;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	

}
