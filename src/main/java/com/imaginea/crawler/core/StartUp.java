package com.imaginea.crawler.core;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.imaginea.crawler.constant.Constant;
import com.imaginea.crawler.util.PropertiesUtil;

public class StartUp {

	final static Logger logger = Logger.getLogger(StartUp.class);
	
	public static void main(String[] args) {	
		int year;
		DateTime dateTime = null;
		Crawler crawler = new Crawler();
		
		logger.info("Crawler application ...."); 
		if(args.length > 0){			
			year = Integer.parseInt(args[0]);	
			if(year <= new DateTime(new Date()).getYear()){
				dateTime = new DateTime(year,1,1,0,0,0);
			}
		}else{
			// default is current year
			dateTime = new DateTime(new Date());
		}
		try {
			PropertiesUtil.readPropertyFile(Constant.CONFIG_FILE_NAME);
			crawler.crawl(dateTime);
		}
		catch (InterruptedException e) {
			logger.error("InterruptedException : " + e);
		} catch (IOException e) {
			logger.error("IOException : " + e);
		}
	}
}
