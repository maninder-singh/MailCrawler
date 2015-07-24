package com.imaginea.crawler.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.imaginea.crawler.constant.Constant;

public class UrlCrawler {
	
	final static Logger logger = Logger.getLogger(UrlCrawler.class);
	
	public void crawl(DateTime dateTime){
		
		
		List<String> urlStringList = this.getUrlsForYear(Constant.URL,dateTime);
		this.createDirectory();
		for (String urlString : urlStringList) {
			this.saveFile(urlString);
		}
		
		logger.info("Download file location : " + Constant.FILE_LOCATION);
		logger.info(urlStringList.size() +" files are downloaded");
	}
	
	private List<String> getUrlsForYear(String baseUrl,DateTime dateTime){
		
		List<String> urlList = new ArrayList<String>();
		int currentYear = new DateTime(new Date()).getYear();
		int month ; 
		if(currentYear == dateTime.getYear()){
			// when current year is selected then get the list of month till today date	
			month = dateTime.getMonthOfYear();
		}else{
			// when current year is not selected then fetch for all month in that year
			month = 12;			
		}
		
		for (int index = 1 ; index <= month; index++) {
			String monthUrl;
			if(index < 10){
				monthUrl =  dateTime.getYear() +"0" + index + ".mbox";
			}
			else{
				monthUrl = dateTime.getYear() + "" + index + ".mbox";
			}
			urlList.add(monthUrl);
		}		
		
		return urlList;
	}
	
	private void saveFile(String fileName){
		
		
		HttpURLConnection connection;
		InputStream inputStream = null;
		FileOutputStream outputStream = null;
		int bytesRead = -1;
		try {
			String url = Constant.URL + "/" + fileName;
			logger.info("File url location : " + url);
			connection = this.getUrlConnenction(url);
			inputStream = connection.getInputStream();
			outputStream = new FileOutputStream(Constant.FILE_LOCATION + fileName);
			byte[] buffer = new byte[Constant.BUFFER_SIZE];
			 while ((bytesRead = inputStream.read(buffer)) != -1) {
	                outputStream.write(buffer, 0, bytesRead);
	            }
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Exception occur : " + e.getMessage());
		}finally{
			if(inputStream != null){
				try {
					inputStream.close();
					logger.info("InputStream handler closed");
				} catch (IOException e) {
				}
			}
			
			if(outputStream != null){
				try {
					outputStream.close();
					logger.info("FileOutputStream handler closed");
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}
		}
		
	}
	
	private void createDirectory(){
		
		File file = new File(Constant.FILE_LOCATION);
		if(!file.exists()){
			file.mkdirs();
			logger.info("Directory created at location.. " + Constant.FILE_LOCATION);
		}
	}
	
	private HttpURLConnection getUrlConnenction(String urlString) throws IOException{
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		return connection;
	}
}
