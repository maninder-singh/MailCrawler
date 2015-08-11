package com.imaginea.crawler.constant;

public class Constant {
	public static final String FILE_LOCATION = "/home/" + System.getProperty("user.name") + "/Downloads/Mail/";
	public static final String URL = "http://mail-archives.apache.org/mod_mbox/maven-users";
	public static final int BUFFER_SIZE = 1024;
	
	public static final String PERSIST_FILE_NAME = "persist.properties";
	public static final String CONFIG_FILE_NAME = "config.properties";
	public static final String FILTER_PARAMETER_NAME = "filter-parameter";
	
	public static String SAVE_ON_LOCAL_STORAGE = "LocalStorage";
	public static String SAVE_ON_DATABASE = "DB";
	public static String SEND_OVER_SERVICE = "WebService";
}


