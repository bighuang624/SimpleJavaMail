//这个没有效果

package com.huang;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class ReceiveEmail {

	private MimeMessage mimeMessage = null;
	private String savePath = "";    //附件下载后的存放目录
	private StringBuffer bodytext = new StringBuffer();    //存放邮件内容
	
	//判断是否已读
	public static boolean isSeen(Message message) throws MessagingException{
		boolean isSeen = false;
		Flags flags = message.getFlags();
		Flags.Flag[] flag = flags.getSystemFlags();
		for(int i = 0; i < flag.length; i++){
			if(flag[i] == Flags.Flag.SEEN){
				isSeen = true;
				break;
			}
		}
		return isSeen;
	}
	
	
}
