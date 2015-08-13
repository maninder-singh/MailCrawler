package com.imaginea.crawler.persist;

import java.util.List;

import com.imaginea.crawler.pojo.Data;

public interface IPersist {

	public void initialize();
	
	public void save(List<Data> dataList);
	
	public void cleanUp();
}
