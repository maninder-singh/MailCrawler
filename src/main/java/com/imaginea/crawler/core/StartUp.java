package com.imaginea.crawler.core;

import java.util.Date;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

public class StartUp {

	final static Logger logger = Logger.getLogger(StartUp.class);
	
	public static void main(String[] args) {
		
		logger.info("MailCrawler application ...."); 
		
		int year;
		DateTime dateTime = null;

		
		if(args.length > 0){			
			year = Integer.parseInt(args[0]);	
			if(year <= new DateTime(new Date()).getYear()){
				dateTime = new DateTime(year,1,1,0,0,0);
			}else{
				// no data for year greater than current year.
			}
			
		}else{
			// default is current year
			dateTime = new DateTime(new Date());
		}
		
		logger.info("Download Files for year : " + dateTime.getYear());
		UrlCrawler urlCrawler = new UrlCrawler();
		urlCrawler.crawl(dateTime);
	}
}
