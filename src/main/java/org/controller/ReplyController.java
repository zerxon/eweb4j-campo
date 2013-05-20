package org.controller;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.entity.Article;
import org.entity.Reply;
import org.entity.User;
import org.eweb4j.mvc.MVC;
import org.eweb4j.util.CommonUtil;
import org.ioc.ServiceIOCs;
import org.service.ReplyService;

@Path("/reply")
public class ReplyController {
	private ReplyService replyService=ServiceIOCs.create().createReplyService();
	
	@Path("/doReply")
	@POST
	public String doReply(@QueryParam("reply")Reply reply, @QueryParam("article")Article article){
		boolean status=false;
		Map<String,Object> json=new HashMap<String,Object>();
		
		User user=(User) MVC.ctx().getSession().getAttribute("user");
		reply.setUser(user);
		
		reply.setArticle(article);
		
		status=replyService.postReply(reply);
		
		if(status){
			json.put("code", 1);
			json.put("data", reply);
		}
		else{
			json.put("code", -1);
		}
		
		return CommonUtil.toJson(json);
	}
}
