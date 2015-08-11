package com.imaginea.crawler.persist;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.imaginea.crawler.constant.Constant;
import com.imaginea.crawler.pojo.Mail;

public class PersistOnLocalStorage implements IPersist{

	private Properties props;
	final static Logger logger = Logger.getLogger(PersistOnLocalStorage.class);
	private final String UNDER_SCORE = "_";
	
	public PersistOnLocalStorage(Properties props) {
		this.props = props;
	}
	
	public void initialize() {
		this.createDirectory();
	}

	private void createDirectory(){
		
		File file = new File(Constant.FILE_LOCATION);
		if(!file.exists()){
			file.mkdirs();
			logger.info("Directory created at location.. " + Constant.FILE_LOCATION);
		}
	}
	
	public void save(Mail mail) throws FileNotFoundException {
		
		if(mail != null){
			String fileName = mail.getSubject().replace(" ",this.UNDER_SCORE);
			PrintWriter writer = new PrintWriter(new File(Constant.FILE_LOCATION + fileName));
			writer.println(mail.getFrom());
			writer.println(mail.getDate());
			writer.println(mail.getSubject());
			writer.println(mail.getContent());
			writer.close();
		}
	}
}
