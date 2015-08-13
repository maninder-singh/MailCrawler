package com.imaginea.crawler.persist;

import java.io.IOException;
import java.util.Properties;

import com.imaginea.crawler.constant.Constant;
import com.imaginea.crawler.util.PropertiesUtil;

public class PersistFactory {
	
	private IPersist persist;
	private Properties props;
	private final String PERSIST_TYPE = "persist-type";

	public IPersist getPersist(){
		return persist;
	}
	
	public IPersist getPersistObject() throws IOException{
		
		String persistType;
		if(props == null){
			PropertiesUtil.readPropertyFile(Constant.PERSIST_FILE_NAME);
			props = PropertiesUtil.getProperties();
		}
		
		if(props.containsKey(PERSIST_TYPE))	{
			persistType = props.getProperty(PERSIST_TYPE);
		}else{
			persistType = "";
		}
		
		if(persistType.equals(Constant.SAVE_ON_LOCAL_STORAGE)){
			persist = new PersistOnLocalStorage(props);
		}else if(persistType.equals(Constant.SAVE_ON_DATABASE)){
			persist = new PersistOnDB(props);
		}else if(persistType.equals(Constant.SEND_OVER_SERVICE)){
			persist = new SendOverService(props);
		}else{
			return null;
		}
		
		persist.initialize();
		return persist;
	}

}
