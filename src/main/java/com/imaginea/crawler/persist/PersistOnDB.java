package com.imaginea.crawler.persist;

import java.io.InputStream;
import java.util.Properties;

import com.imaginea.crawler.pojo.Mail;

public class PersistOnDB implements IPersist{

	private Properties props;
	
	public PersistOnDB(Properties props) {
		this.props = props;
	}
	
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	public void save(Mail mail) {
		// TODO Auto-generated method stub
		
	}

}
