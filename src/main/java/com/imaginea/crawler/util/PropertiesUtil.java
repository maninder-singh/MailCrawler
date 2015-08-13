package com.imaginea.crawler.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertiesUtil {

	private static Properties props = null;
	
	public static void readPropertyFile(String fileName) throws IOException{
			
		InputStream inputStream = new FileInputStream(fileName);
		if(props == null){
			props = new Properties();
		}
		props.load(inputStream);
			
	}
	
	public static Properties getProperties(){
		return props;
	}
	
	public static void setProperties(String key , String value){
		props.setProperty(key, value);
	}
	
	public static List<String> getListofPropertiesValue(String key){
		List<String> propertiesValueList = new ArrayList<String>();
		
		if(props.containsKey(key)){
			for(String value : props.getProperty(key).split(" ")){
				propertiesValueList.add(value);
			}
		}
		return propertiesValueList;
	}
}
