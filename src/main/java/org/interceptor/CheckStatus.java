package org.interceptor;

import org.eweb4j.mvc.MVC;
import org.eweb4j.mvc.interceptor.Interceptor;
import org.eweb4j.mvc.interceptor.Uri;

@Interceptor(
		priority=1,
		policy = "or", 
		uri = { 
				@Uri("article/post"),
				@Uri("article/doPost"),
				@Uri("article/doEdit"),
				@Uri("user/favorite"),
				@Uri(type="start", value="article/edit"),
				@Uri(type="start", value="reply/"),
				@Uri(type="start", value="mark/"),
				@Uri(type="start", value="user/setting/")},
		method="check")

public class CheckStatus {
	
	public String check(){
		try{
			if(MVC.ctx().getSession().getAttribute("user")==null){
				
				return "action:user/login";
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
}
