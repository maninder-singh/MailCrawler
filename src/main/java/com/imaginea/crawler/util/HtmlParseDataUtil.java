package com.imaginea.crawler.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.imaginea.crawler.pojo.Data;
import com.imaginea.crawler.pojo.Mail;

public class HtmlParseDataUtil {
	
	private static final String AHREF = "a[href]"; 
	private static final String HREF = "href";
	private static final String FROM = ".from";
	private static final String SUBJECT = ".subject";
	private static final String DATE = ".date";
	private static final String CONTENTS = ".contents";
	
	public static HashSet<String> fetchLinksFromHtmlPage(String url) throws IOException {
		HashSet<String> urlSet = new HashSet<String>();
		Document document = Jsoup.connect(url).get();
		Elements links = document.select(AHREF);
		for (Element element : links) {
			urlSet.add(element.absUrl(HREF));
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
	
	public static List<Data> fetchRequiredContent(String url,Pattern pattern,List<String> parameterList) throws IOException{
		List<Data> dataList = new ArrayList<Data>();
		Data data;
		Document document = Jsoup.connect(url).get();
		Element element;
		boolean isPatternFound;
		
		isPatternFound = RegexUtil.isPatternFound(pattern, document.text());
		if(isPatternFound){
			for(String parameter : parameterList){
				data = new Data();
				element = document.select("." + parameter).first();
				if(element != null){
					data.setKey(parameter);
					data.setValue(element.text());
				}else{
					data.setValue("");
				}
				dataList.add(data);
			}
		}
		return dataList;
	}
}
