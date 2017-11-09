package com.huang;

import java.sql.Connection;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.AndTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

public class Email {
    
    // 你需要根据自己的实际情况修改以下信息
	private static final String smtp_host = "smtp.sina.cn";
	private static final String pop3_host = "pop3.sina.cn";
	private static final String home_address = "xx@sina.cn";
	private static final String password = "password";
	
	
	
	private static final String jpg_path = "doc/test.jpg";
	
	private static final String regex_email = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
//	private static final String regex_subject = "[\\s\\S]*(Kyon)[\\s\\S]*";
	private static final String regex_no1 = "[\\s\\S]*[Nn][Oo][\\s\\S]*";
	private static final String regex_yes1 = "[\\s\\S]*[Yy][Ee][Ss][\\s\\S]*";
	private static final String regex_no2 = "[\\s\\S]?[Nn][Oo][\\s\\S]?";
	private static final String regex_yes2 = "[\\s\\S]?[Yy][Ee][Ss][\\s\\S]?";
	
	//发单纯的文字邮件
	public static void sendMail(String target_address){
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", smtp_host);
		properties.put("mail.smtp.ssl.enable", true);
		properties.put("mail.smtp.auth", true);
		Session session = Session.getDefaultInstance(properties, new Authenticator(){
			@Override
			public PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(home_address, password);
			}
			
		});
		try{
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(home_address));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(target_address));
			message.setSubject("Java test mail from Kyon Huang");
			message.setText("This is a test mail for Java homework from Kyon Huang.\nDo you like me?\nQAQ");
			Transport.send(message);
			System.out.println("Sending to " + target_address + " succeed!");
			
		}catch(MessagingException e){
			e.printStackTrace();
		}
	}
	
	//发带附件的邮件
	public static void sendMultipartMail(String target_address){
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", smtp_host);
		properties.put("mail.smtp.ssl.enable", true);
		properties.put("mail.smtp.auth", true);
		Session session = Session.getDefaultInstance(properties, new Authenticator(){
			@Override
			public PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(home_address, password);
			}
			
		});
		try{
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(home_address));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(target_address));
			message.setSubject("Java test multipart mail from Kyon Huang");
			
			MimeBodyPart bodyPartOne = new MimeBodyPart();
			bodyPartOne.setText("This is a test multipart mail for Java homework from Kyon Huang.\nDo you like me?\nQAQ");
			
			MimeMultipart multipart = new MimeMultipart();
			multipart.addBodyPart(bodyPartOne);
			
			MimeBodyPart bodyPartTwo = new MimeBodyPart();
			DataSource source = new FileDataSource(jpg_path);
			bodyPartTwo.setDataHandler(new DataHandler(source));
			bodyPartTwo.setFileName("test.jpg");
			multipart.addBodyPart(bodyPartTwo);
			
			message.setContent(multipart);
			Transport.send(message);
			System.out.println("Sending to " + target_address + " succeed!");
			
		}catch(MessagingException e){
			e.printStackTrace();
		}
	}
	
	//收邮件
	public static Message[] receiveMail(){
		Properties properties = System.getProperties();
		properties.put("mail.pop3.ssl.enable", true);
		properties.put("mail.pop3.host", pop3_host);
		properties.put("mail.pop3.port", 995);
		
		Session session = Session.getInstance(properties, null);
		Message messages[] = null;
		Store store = null;
		Folder folder = null;
		
		try{
		    store = session.getStore("pop3");    //Store 代表存储邮件的邮件服务器
		    store.connect(pop3_host, home_address, password);
		    folder = store.getFolder("INBOX");
		    folder.open(Folder.READ_ONLY);
//		    messages = folder.getMessages();
		    SearchTerm st = new AndTerm(new SubjectTerm("Java"), new SubjectTerm("Kyon Huang"));
		    messages = folder.search(st);
		   
		} catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("Receving finished!");
		return messages;
	}
	
	//判断回复
	public static void getEmotion(Message[] messages){
		try{
		    for(int j = 0; j < messages.length; j++){
		    		
		    		Pattern split = Pattern.compile("[<>]");
		    		String[] strs = split.split(messages[j].getFrom()[0].toString());
		    		
		    		for(int k = 0; k < strs.length;k++){
		    			if(Pattern.matches(regex_email, strs[k])){	    				;
		    				
				    		if(Pattern.matches(regex_yes1, messages[j].getContent().toString())||
				    				Pattern.matches(regex_yes2, messages[j].getContent().toString()))
				    			System.out.println(strs[k] + " likes me! (｀・ω・´)");
				    		else if(Pattern.matches(regex_no1, messages[j].getContent().toString())||
				    				Pattern.matches(regex_no2, messages[j].getContent().toString()))
				    			System.out.println(strs[k] + " dislikes me! Σ(ﾟдﾟ;)");
		    			}
		    		}
		    	}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		
		Connection conn = null;
		try{
			conn = JDBC.getConnection();
			String[] addresses = JDBC.searchEmailAddress(conn);
			for(int i = 0; i < addresses.length; i++){
				Email.sendMultipartMail(addresses[i]);
				try{
				    Thread.currentThread().sleep(1000);    //暂停 1 秒后程序继续执行
				}catch (InterruptedException e) {
				    e.printStackTrace();
				} 
			}
			
			try{
			    Thread.currentThread().sleep(90000);    //暂停 90 秒后程序继续执行
			}catch (InterruptedException e) {
			    e.printStackTrace();
			} 
			
			Message[] mails = Email.receiveMail();
			Email.getEmotion(mails);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
}
