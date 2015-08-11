package com.imaginea.crawler.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.imaginea.crawler.pojo.Mail;

public class HtmlParseDataUtil {
	
	private static final String AHREF = "a[href]"; 
	private static final String HREF = "href";
	private static final String FROM = ".from";
	private static final String SUBJECT = ".subject";
	private static final String DATE = ".date";
	private static final String CONTENTS = ".contents";
	
	public static HashSet<String> fetchLinkFromHtmlPage(String url) throws IOException{
		HashSet<String> urlSet = new HashSet<String>();
		Document document = Jsoup.connect(url).get();
		Elements links = document.select(AHREF);
		for (Element element : links) {
			urlSet.add(element.attr(HREF));
		}
		return urlSet;
	}
	
	public static Mail fetchHtmlPageContent(String url) throws IOException{
		
		Mail mail = new Mail();		
		Document document = Jsoup.connect(url).get();
		Element from = document.select(FROM).first();
		Element subject = document.select(SUBJECT).first();
		Element content = document.select(DATE).first();
		Element date = document.select(CONTENTS).first();
		
		if(from == null || subject == null || content == null || date == null){
			return null;
		}
		mail.setFrom(from.text());
		mail.setContent(content.text());
		mail.setSubject(subject.text());
		mail.setDate(date.text());
		
		return mail;
	}
}
