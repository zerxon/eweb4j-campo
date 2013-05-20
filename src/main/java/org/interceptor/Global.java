package org.interceptor;

import javax.servlet.http.Cookie;

import org.entity.User;
import org.eweb4j.mvc.MVC;
import org.eweb4j.mvc.interceptor.Interceptor;
import org.eweb4j.mvc.interceptor.Uri;
import org.service.UserService;
import org.util.CookieHelper;

//@Interceptor(policy = "and", uri = { @Uri(type="actions")}, except={"test/signup"}, method="prepare")
//@Interceptor(policy = "or", uri = { @Uri(type="start", value="article/"), @Uri(type="start", value="user/")}, method="prepare")
@Interceptor(priority=0,policy = "or", uri = { @Uri(type="actions")}, method="prepare")
public class Global {
	
	public void prepare(){
		//获取URI,用于判断路径
		String uri=MVC.ctx().getUri();
		MVC.ctx().getServletContext().setAttribute("uri", uri);
		
		//判断是否存在登录cookie
		User loginUser=(User) MVC.ctx().getSession().getAttribute("user");
		
		if(loginUser==null){
			try{
				Cookie cookieUser=CookieHelper.getCookie("user");
				Cookie cookieToken=CookieHelper.getCookie("token");
				
				if(cookieUser!=null && cookieToken!=null){
					String userName=cookieUser.getValue();
					
					UserService userService=new UserService();
					User user=userService.getOneUserByName(userName);
					String token=userService.genernateToken(user);
					
					if(token.equals(cookieToken.getValue())){
						MVC.ctx().getSession().setAttribute("user", user);
					}
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
