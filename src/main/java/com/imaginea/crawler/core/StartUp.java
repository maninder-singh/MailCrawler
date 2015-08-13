package com.imaginea.crawler.core;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.imaginea.crawler.constant.Constant;
import com.imaginea.crawler.util.PropertiesUtil;

public class StartUp {

	final static Logger logger = Logger.getLogger(StartUp.class);
	private final static String URL_PATTERN = "http://";
	
	public static void main(String[] args) {	
		int year;
		DateTime dateTime = null;
		Crawler crawler = new Crawler();
		String url = "";
		logger.info("Crawler application ...."); 
		
		if(args.length == 2){
			if(args[0].contains(URL_PATTERN)){
				url = args[0];
				year = Integer.parseInt(args[1]);
				dateTime = new DateTime(year,1,1,0,0,0);
			}else if(args[1].contains(URL_PATTERN)){
				url = args[1];
				year = Integer.parseInt(args[0]);
				dateTime = new DateTime(year,1,1,0,0,0);
			}
		}else if(args.length == 1){
			if(args[0].contains(URL_PATTERN)){
				url = args[0];
				dateTime = new DateTime(new Date());
			}else{
				logger.info("please provide url for crawl");
				return;
			}
		}else{
			logger.info("Please provide url and year");
			return;
		}
		
//		if(args.length > 0){			
//			year = Integer.parseInt(args[0]);	
//			if(year <= new DateTime(new Date()).getYear()){
//				dateTime = new DateTime(year,1,1,0,0,0);
//			}
//		}else{
//			// default is current year
//			dateTime = new DateTime(new Date());
//		}
		
		try {
			PropertiesUtil.readPropertyFile(Constant.CONFIG_FILE_NAME);
			logger.info("crawl for year : " + dateTime.getYear());
			crawler.crawl(dateTime,url);
		}
		catch (InterruptedException e) {
			logger.error("InterruptedException : " + e);
		} catch (IOException e) {
			logger.error("IOException : " + e);
		}
	}
}
