package com.imaginea.crawler.core;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.imaginea.crawler.constant.Constant;
import com.imaginea.crawler.persist.IPersist;
import com.imaginea.crawler.persist.PersistFactory;
import com.imaginea.crawler.pojo.Data;
import com.imaginea.crawler.util.HtmlParseDataUtil;
import com.imaginea.crawler.util.PropertiesUtil;
import com.imaginea.crawler.util.RegexUtil;

public class Crawler{
	
	final static Logger logger = Logger.getLogger(Crawler.class);
	
	private IPersist persist;
	
	public void crawl(DateTime dateTime) throws InterruptedException{
		
		HashSet<String> visitedUrlSet = new HashSet<String>();
		BlockingQueue<String> unVisitedBlockingQueue = new LinkedBlockingQueue<String>();
		List<String> filterParameterList;
		HashSet<String> unprocessedUrlSet;
		Pattern pattern;
		List<Data> persistData;
		
		try {
			this.persist = new PersistFactory().getPersistObject();
		} catch (IOException e) {
			logger.error("Unable to create Persist Object : " + e);
			return;
		}
		unVisitedBlockingQueue.put(Constant.URL);
		filterParameterList = PropertiesUtil.getListofPropertiesValue(Constant.FILTER_PARAMETER_NAME);
		pattern = RegexUtil.createPattern(filterParameterList);
		while(unVisitedBlockingQueue.size() > 0){
			String key = unVisitedBlockingQueue.take();
			if(visitedUrlSet.contains(key)){
				continue;
			}
			try {
				unprocessedUrlSet = HtmlParseDataUtil.fetchLinksFromHtmlPage(key);
				unprocessedUrlSet = this.filterUrl(unprocessedUrlSet,dateTime);
				unVisitedBlockingQueue.addAll(unprocessedUrlSet);	
				persistData = HtmlParseDataUtil.fetchRequiredContent(key,pattern,filterParameterList); 
				persist.save(persistData);
				logger.info("valid url : " + key);
			} catch (IOException e) {
				logger.info("invalid url : " + key);
			}finally{
				visitedUrlSet.add(key);
			}
		}
		persist.cleanUp();
	}
	 
	private HashSet<String> filterUrl(HashSet<String> urlSet,DateTime dateTime){
		
		HashSet<String> filterUrl = new HashSet<String>();
		
		String year = String.valueOf(dateTime.getYear());
		
		for(String url : urlSet){
			if(url.contains(year)){
				filterUrl.add(url);
			}
		}
		return filterUrl;
	}
}
