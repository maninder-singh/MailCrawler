package com.imaginea.crawler.persist;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.imaginea.crawler.constant.Constant;
import com.imaginea.crawler.pojo.Data;
import com.imaginea.crawler.pojo.Mail;

public class PersistOnLocalStorage implements IPersist{

	private Properties props;
	final static Logger logger = Logger.getLogger(PersistOnLocalStorage.class);
	private final String UNDER_SCORE = "_";
	private ExecutorService executorService;
	
	
	public PersistOnLocalStorage(Properties props) {
		this.props = props;
	}
	
	public void initialize() {
		this.createDirectory();
		executorService = Executors.newFixedThreadPool(2000);
		
	}

	private void createDirectory(){
		
		File file = new File(Constant.FILE_LOCATION);
		if(!file.exists()){
			file.mkdirs();
			logger.info("Directory created at location.. " + Constant.FILE_LOCATION);
		}
	}
	
	public void save(final List<Data> dataList) {
		
		if(dataList != null && dataList.size() > 0){
			
			executorService.execute(new Runnable() {
				public void run() {	
					String fileName = dataList.get(0).getValue().replace(" ",UNDER_SCORE);
					PrintWriter writer = null;
					try {
						writer = new PrintWriter(new File(Constant.FILE_LOCATION + fileName));
						for(Data data : dataList){
							writer.println(data.getValue());
						}
					} catch (FileNotFoundException e) {
						logger.error("Unable to save mail of subject : " + dataList.get(0).getValue());
					}finally{
						if(writer != null){
							writer.close();
						}
					}			
				}
			});
		}
	}
	
	public void cleanUp(){
		executorService.shutdown();
	}
}
