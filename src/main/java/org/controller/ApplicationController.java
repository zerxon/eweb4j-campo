package org.controller;

import java.util.Map;

import javax.ws.rs.Path;

import org.eweb4j.mvc.MVC;
import org.eweb4j.util.CommonUtil;

@Path("/")
public class ApplicationController {
	
	public String index(Map<String,Object> model){
		
		return new ArticleController().index(model);
		//return "action:blog/";
		
		//return CommonUtil.toJson(MVC.ctx().getRequest().getCookies());
	}
}