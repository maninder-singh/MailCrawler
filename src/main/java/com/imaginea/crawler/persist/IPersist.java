package com.imaginea.crawler.persist;

import java.io.InputStream;

public interface IPersist {

	public void configuration();
	
	public void save(InputStream inputStream);
}
