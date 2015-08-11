package com.imaginea.crawler.core;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.imaginea.crawler.constant.Constant;
import com.imaginea.crawler.persist.IPersist;
import com.imaginea.crawler.persist.PersistFactory;
import com.imaginea.crawler.util.HtmlParseDataUtil;
import com.imaginea.crawler.util.PropertiesUtil;

public class Crawler {
	final static Logger logger = Logger.getLogger(Crawler.class);
	private IPersist persist;
	private final String FORWARD_SLASH = "/";
	private final String DOUBLE_FORWARD_SLASH = "//";
	
	public void crawl(DateTime dateTime) throws InterruptedException{	
		HashSet<String> visitedUrlSet = new HashSet<String>();
		BlockingQueue<String> unVisitedBlockingQueue = new LinkedBlockingQueue<String>();
		List<String> filterParameterList;
		HashSet<String> unprocessedUrlSet;
		
		try {
			this.getPersistObject();
		} catch (IOException e) {
			logger.error("Unable to create Persist Object : " + e);
			return;
		}
		unVisitedBlockingQueue.put(Constant.URL);
		filterParameterList = PropertiesUtil.getListofPropertiesValue(Constant.FILTER_PARAMETER_NAME);
		while(unVisitedBlockingQueue.size() > 0){
			String key = unVisitedBlockingQueue.take();
			if(visitedUrlSet.contains(key)){
				continue;
			}
			try {
				unprocessedUrlSet = HtmlParseDataUtil.fetchLinkFromHtmlPage(key);
				unprocessedUrlSet = this.filterUrl(key,unprocessedUrlSet,filterParameterList,dateTime,visitedUrlSet);
				unVisitedBlockingQueue.addAll(unprocessedUrlSet);	
				persist.save(HtmlParseDataUtil.fetchHtmlPageContent(key));
				logger.info("valid url : " + key);
			} catch (IOException e) {
				logger.info("invalid url : " + key);
			}finally{
				visitedUrlSet.add(key);
			}
		}
	}
	 
	private HashSet<String> filterUrl(String url ,HashSet<String> urlSet ,List<String> filterParameterList, DateTime dateTime,HashSet<String> visitedUrlSet){
		HashSet<String> filterUrlSet = new HashSet<String>();
		StringBuilder keyStringBuilder;
		
		Iterator<String> iter = urlSet.iterator();
		while(iter.hasNext()){
			keyStringBuilder = new StringBuilder();
			keyStringBuilder.append(iter.next());
			if(keyStringBuilder.toString().contains(String.valueOf(dateTime.getYear()))){
				for(String filterParameter: filterParameterList){
					int index = keyStringBuilder.indexOf(filterParameter);
					if(index != -1){
						keyStringBuilder.replace(index, index + filterParameter.length(),"");
					}
					
					if(keyStringBuilder.substring(keyStringBuilder.length() - 1).equals(FORWARD_SLASH)){
						keyStringBuilder.replace(keyStringBuilder.length() - 1, keyStringBuilder.length() - 1,"");
					}
				}	
				if(keyStringBuilder.substring(keyStringBuilder.length() - 2).equals(DOUBLE_FORWARD_SLASH)){
					keyStringBuilder.replace(keyStringBuilder.length() -1, keyStringBuilder.length(),"");
				}
				boolean isFound = this.isUrlFoundinSet(keyStringBuilder.toString(),visitedUrlSet);
				if(!isFound){
					filterUrlSet.add(url + FORWARD_SLASH +keyStringBuilder.toString());
				}	
			}
		}
		return filterUrlSet;
	}
	
	private boolean isUrlFoundinSet(String url,HashSet<String> visitedUrlSet){
		boolean isFound = false;
		
		if(url.charAt(0) == FORWARD_SLASH.charAt(0) && url.charAt(1) == FORWARD_SLASH.charAt(0)){
			url = url.substring(1);	
		}
		Iterator<String> iterator = visitedUrlSet.iterator();
		while(iterator.hasNext()){
			if(iterator.next().contains(url)){
				isFound = true;
				break;
			}	
		}
		return isFound;
	}
	
	private void getPersistObject() throws IOException{
		PersistFactory factory = new PersistFactory();
		
		this.persist = factory.getPersistObject();
		persist.initialize();
	}
}
