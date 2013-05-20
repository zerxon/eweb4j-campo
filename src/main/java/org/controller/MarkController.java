package org.controller;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eweb4j.util.CommonUtil;
import org.ioc.ServiceIOCs;
import org.service.MarkService;


@Path("/mark")
public class MarkController {
	private MarkService markService=ServiceIOCs.create().createMarkService();
	
	@Path("/article/{id}")
	public String markArticle(@PathParam("id")long id, Map<String,Object> model){
		int status=markService.markArticle(id);
		
		Map<String,Object> json=new HashMap<String,Object>(2);
		json.put("code", status);
		
		return CommonUtil.toJson(json);
	}
}
