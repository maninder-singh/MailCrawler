package com.imaginea.crawler.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;

import org.joda.time.DateTime;

import com.imaginea.crawler.pojo.Mail;
import com.imaginea.crawler.util.CommonUtil;


public class MailCrawler {

	public static final String HOST_NAME = "hostname";
	public static final String USER_NAME = "username";
	public static final String PASSWORD = "password";
	public static final String FOLDER_NAME = "filter_folder";
	public static final String STORE = "store";
	public static final String FILTER_YEAR = "filter_year";
	
	public void crawl(Properties props) throws MessagingException, IOException{
		
		int filterYear = Integer.parseInt(props.getProperty(FILTER_YEAR));
		
		Session mailSession = this.getSession(props);
		Store store = this.getStore(mailSession, props);	
		Folder folder = this.getFolder(store,props.getProperty(FOLDER_NAME));
		List<Mail> filterMessages = this.getFilterMessage(folder,filterYear);
	}
	
	private Folder getFolder(Store store ,String folderName) throws MessagingException{
	
		Folder folder = store.getFolder(folderName);
		folder.open(Folder.READ_ONLY);	
		return folder;
	}
	
	private List<Mail> getFilterMessage(Folder folder,int filterYear) throws MessagingException, IOException{
		
		List<Mail> mailList = new ArrayList<Mail>();
		Mail mailObject = null;
		Date startDate = CommonUtil.getStartDateByYear(filterYear);
		Date endDate = CommonUtil.getEndDateByYear(filterYear);
		ReceivedDateTerm receivedStartDate = new ReceivedDateTerm(ComparisonTerm.GE,startDate);
		ReceivedDateTerm receivedEndDate = new ReceivedDateTerm(ComparisonTerm.LE,endDate);
		SearchTerm searchTerm = new AndTerm(receivedStartDate, receivedEndDate);
		Message[] messages = folder.search(searchTerm);
		
		for (Message message : messages) {
			
			mailObject = new Mail();
			mailObject.setFrom(message.getFrom()[0].toString());
			mailObject.setSendDate(new DateTime(message.getSentDate()));
			mailObject.setSubject(message.getSubject());
			mailObject.setContent(message.getContent().toString());
			mailList.add(mailObject);
		}
		return mailList;
	}
	
//	public static void testMethod(int filterYear){
//		
//		Calendar startDate = Calendar.getInstance();
//		startDate.set(filterYear, 0, 1,0,0,0);
//		
//		Calendar endDate = Calendar.getInstance();
//		endDate.set(filterYear,11,31,23,59,59);
//		
//		System.out.println("Start Date : " + startDate.getTime());
//		System.out.println("End Date : " + endDate.getTime());
//		
//		ReceivedDateTerm receivedStartDate = new ReceivedDateTerm(ComparisonTerm.GE,startDate.getTime());
//		ReceivedDateTerm receivedEndDate = new ReceivedDateTerm(ComparisonTerm.LE,endDate.getTime());
//		
//		SearchTerm searchTerm = new AndTerm(receivedStartDate, receivedEndDate);
//	}
	
	private Session getSession(Properties props){
		
		Session mailSession = Session.getInstance(props);
		return mailSession;
	}
	
	private Store getStore(Session session,Properties props) throws MessagingException{
		
		Store store = session.getStore(props.getProperty(STORE));
		store.connect(props.getProperty(HOST_NAME),props.getProperty(USER_NAME),props.getProperty(PASSWORD));
		return store;
	}
}
