package org.service;

import org.entity.Reply;
import org.eweb4j.orm.dao.DAOFactory;
import org.eweb4j.util.CommonUtil;

public class ReplyService {
	
	public long getReplyCount(){
		
		long count=DAOFactory.getDAO(Reply.class).select("id").count();
		
		return count;
	}
	
	public boolean postReply(Reply reply){
		boolean status=false;
		
		try{
			reply.setComment(CommonUtil.changeHTML(reply.getComment()));
			
			reply.setAddTime(String.valueOf(CommonUtil.getNow(10)));
			status=DAOFactory.getInsertDAO().insert(reply).intValue()>0;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return status;
	}
}
