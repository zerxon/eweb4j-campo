package org.service;

import org.entity.Article;
import org.entity.Mark;
import org.entity.User;
import org.eweb4j.mvc.MVC;
import org.eweb4j.orm.dao.DAOFactory;
import org.eweb4j.util.CommonUtil;

public class MarkService {
	public static int MARK=1;
	public static int UN_MARK=2;
	public static int MARK_ERROR=0;
	
	public int markArticle(long articleId){
		
		try{
			User user=(User) MVC.ctx().getSession().getAttribute("user");
			
			Mark m=DAOFactory.getDAO(Mark.class).select("id").
				where().field("article").equal(articleId).
				and("user").equal(user.getId()).queryOne();
			
			if(m==null){
				Mark mark=new Mark();
				
				long addTime=(CommonUtil.getNow(10));
				mark.setAddTime(addTime);
				
				mark.setUser(user);
				
				Article article=new Article();
				article.setId(articleId);
				mark.setArticle(article);
				
				if(DAOFactory.getInsertDAO().insert(mark).intValue()>0)
					return MARK;
			}
			else{
				if(DAOFactory.getDeleteDAO().deleteById(m).intValue()>0)
					return UN_MARK;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return MARK_ERROR;
	}
}
