package com.imaginea.crawler.persist;

import java.util.List;
import java.util.Properties;

import com.imaginea.crawler.pojo.Data;

public class PersistOnDB implements IPersist{

	private Properties props;
	
	public PersistOnDB(Properties props) {
		this.props = props;
	}
	
	public void initialize() {
		
	}

	public void save(List<Data> dataList) {
		
	}
	
	public void cleanUp(){
		
	}
}
