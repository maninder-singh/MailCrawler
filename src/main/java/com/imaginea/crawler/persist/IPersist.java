package com.imaginea.crawler.persist;

import java.io.FileNotFoundException;
import java.io.InputStream;

import com.imaginea.crawler.pojo.Mail;

public interface IPersist {

	public void initialize();
	
	public void save(Mail mail) throws FileNotFoundException;
}
