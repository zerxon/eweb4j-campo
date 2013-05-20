package org.util;

import javax.servlet.http.Cookie;

import org.eweb4j.mvc.MVC;

public class CookieHelper {
	
	public static Cookie getCookie(String cookieName){
		Cookie cookie=null;
		Cookie[] cookies=MVC.ctx().getRequest().getCookies();
		
		if(cookies!=null){
			for(Cookie c:cookies){
				if(c.getName().equals(cookieName)){
					cookie=c;
					break;
				}
			}
		}
		
		return cookie;
	}
	
	public static void delCookie(String cookieName){
		Cookie[] cookies=MVC.ctx().getRequest().getCookies();
		
		if(cookies!=null){
			for(Cookie c:cookies){
				if(c.getName().equals(cookieName)){
					c.setMaxAge(0);
					MVC.ctx().getResponse().addCookie(c);
					break;
				}
			}
		}
	}
	
	
	public static void delAllCookie(){
		Cookie[] cookies=MVC.ctx().getRequest().getCookies();
		
		if(cookies!=null){
			for(Cookie c:cookies){
				c.setMaxAge(0);
				c.setPath("/");
				MVC.ctx().getResponse().addCookie(c);
			}
		}
	}
	
}
