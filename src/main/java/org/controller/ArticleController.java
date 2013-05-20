package org.controller;

import java.util.Map;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.entity.Article;
import org.entity.User;
import org.eweb4j.mvc.MVC;
import org.eweb4j.orm.Page;
import org.ioc.ServiceIOCs;
import org.service.ArticleService;
import org.service.ReplyService;

@Path("/article")
public class ArticleController {
	
	private ArticleService articleService=ServiceIOCs.create().createArticleService();
	private ReplyService replyService=ServiceIOCs.create().createReplyService();
	
	public String index(Map<String,Object> model){
		return pageIndex(1,model);
	}
	
	@Path("/page/{p}")
	public String pageIndex(@PathParam("p")int pageIndex, Map<String,Object> model){
		Page<Article> artilcePage=articleService.getArticlePage(pageIndex,5);
		model.put("artilcePage",artilcePage);
		
		long replyCount=replyService.getReplyCount();
		model.put("replyCount", replyCount);
		
		return "vm:article/index.html";
	}
	
	@Path("/post")
	public String post(){
		
		return "vm:article/post.html";
	}
	
	@Path("/doPost")
	public String doPost(@QueryParam("article")Article article){
		long articleId=0;
		
		User user=(User) MVC.ctx().getSession().getAttribute("user");
		article.setUser(user);

		articleId=articleService.postArticle(article);
		if(articleId>0){
			return "action:article/" + String.valueOf(articleId);
		}
		else{
			return "vm:article/post.html";
		}
	}
	
	@Path("/{id}")
	public String topic(@PathParam("id")long articleId,Map<String,Object> model){
		Article article=articleService.getOneArticle(articleId);
		model.put("article", article);
		
		//return CommonUtil.toJson(article);
		
		//判断当前登录用户是否收藏了该文章
		User user=(User)MVC.ctx().getSession().getAttribute("user");
		if(user!=null && article.getMarkUsers()!=null){
			for(User markUser : article.getMarkUsers()){
				if(markUser.getId()==user.getId()){
					model.put("isMark", true);
					break;
				}
			}
		}
		
		return "vm:article/topic.html";
	}
	
	@Path("/edit/{id}")
	public String edit(@PathParam("id")long articleId, Map<String,Object> model){
		User user=(User) MVC.ctx().getSession().getAttribute("user");
		
		Article article=articleService.getOneArticle(articleId);
		
		if(article.getUser().getId()==user.getId()){
			model.put("article", article);
			
			return "vm:article/post.html";
		}
		
		return "action:";
	}
	
	@Path("/doEdit")
	@POST
	public String doEdit(@QueryParam("article")Article article){
		boolean status=false;
		
		User user=(User) MVC.ctx().getSession().getAttribute("user");
		Article oldArticle=articleService.getOneArticle(article.getId());
		
		if(user.getId()==oldArticle.getUser().getId()){
			status=articleService.editArticle(article);
		}
		
		if(status){
			return "action:article/"+article.getId().toString();
		}
		else{
			return "action:";
		}
	}
}
