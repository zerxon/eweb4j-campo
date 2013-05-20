package org.interceptor;

import org.eweb4j.mvc.MVC;
import org.eweb4j.mvc.interceptor.Interceptor;
import org.eweb4j.mvc.interceptor.Uri;

@Interceptor(
		priority=1,
		policy = "or", 
		uri = { @Uri("user/login"),@Uri("user/doLogin")}, 
		method="isLogin")
public class CheckLogin {
	
	public String isLogin(){
		try{
			if(MVC.ctx().getSession().getAttribute("user")!=null){
				
				return "action:";
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
}
