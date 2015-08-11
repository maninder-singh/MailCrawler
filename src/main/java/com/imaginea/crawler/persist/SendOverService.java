package com.imaginea.crawler.persist;

import java.io.InputStream;
import java.util.Properties;

import com.imaginea.crawler.pojo.Mail;

public class SendOverService implements IPersist{

	private Properties props;
	
	public SendOverService(Properties props) {
		this.props = props;
	}
	
	public void initialize() {
		
	}

	public void save(Mail mail) {
		
	}

	
}
