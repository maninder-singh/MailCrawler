package com.imaginea.crawler.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.joda.time.DateTime;

import com.imaginea.crawler.constant.Constant;

public final class CommonUtil {

	public static Properties loadPropertieFile() throws IOException{
		
		InputStream input = new FileInputStream("");
		Properties props = new Properties();
		props.load(input);
		input.close();
		return props;
	}
	
	public static Properties loadFromCommandLineArgument(Properties props,String[] parameters){
		
		for (String parameter : parameters) {
			
			String[] keyValuePair = parameter.split("=");
			
			if(props.containsKey(keyValuePair[0])){
			props.setProperty(keyValuePair[0].toString(), keyValuePair[1].toString());
			}
		}
		
		return props;
	}
	
	public static Date getStartDateByYear(int year){
		
		Calendar startDate = Calendar.getInstance();
		startDate.set(year, 0, 1,0,0,0);
		return startDate.getTime();
	}
	
	public static Date getEndDateByYear(int year){
		
		Calendar endDate = Calendar.getInstance();
		endDate.set(year,11,31,23,59,59);
		return endDate.getTime();
	}
	
	public static int getYearFromDate(Date date){
		
		DateTime dateTime = new DateTime(date);
		return dateTime.getYear();
	}
}
