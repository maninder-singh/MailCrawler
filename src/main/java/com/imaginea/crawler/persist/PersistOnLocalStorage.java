package com.imaginea.crawler.persist;

import java.io.InputStream;
import java.util.Properties;

public class PersistOnLocalStorage implements IPersist{

	private Properties props;
	
	
	public PersistOnLocalStorage(Properties props) {
		this.props = props;
	}
	
	public void configuration() {
		
		
	}

	public void save(InputStream inputStream) {
		
	}
	
	

}
