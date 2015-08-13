package com.imaginea.crawler.persist;

import java.util.List;
import java.util.Properties;

import com.imaginea.crawler.pojo.Data;
import com.imaginea.crawler.pojo.Mail;

public class SendOverService implements IPersist{

	private Properties props;
	
	public SendOverService(Properties props) {
		this.props = props;
	}
	
	public void initialize() {
		
	}

	public void save(List<Data> dataList) {
		
	}

	public void cleanUp(){
		
	}
	
}
