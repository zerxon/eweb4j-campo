package org.service;

import java.util.Collection;

import org.entity.Article;
import org.entity.Reply;
import org.entity.User;
import org.eweb4j.mvc.MVC;
import org.eweb4j.orm.Db;
import org.eweb4j.orm.dao.DAOFactory;
import org.eweb4j.util.CommonUtil;

public class UserService {
	
	public Collection<User> userList(){
		Collection<User> users=DAOFactory.getDAO(User.class).selectAll().query();
		
		return users;
	}
	
	public User signup(User user){
		User regUser=null;
		
		try{
			user.setPassword(CommonUtil.md5(user.getPassword()));
			
			//status=DAOFactory.getInsertDAO().insert(user).intValue()>0;
			Boolean status = Db.ar(user).create();
			
			if(status){
				regUser=user;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return regUser;
	}
	
	public User login(User user){
		
		try{
			user.setPassword(CommonUtil.md5(user.getPassword()));
			return Db.ar(User.class).find("byNameAndPassword",user.getName(),user.getPassword()).first();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
		
		/*
		DAOFactory.getDAO()
			.selectAll()
			.where()
				.field("name").equal(user.getName())
				.and("password").equal(user.getPassword())
			.queryOne();
		 */	
	}
	
	public User getOneUserByName(String userName){
		User user=null;
		
		try{
			user=DAOFactory.getDAO(User.class).selectAll().where().field("name").equal(userName).queryOne();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return user;
	}
	
	public User getOneUserByEmail(String email){
		User user=null;
		
		try{
			user=DAOFactory.getDAO(User.class).selectAll().where().field("email").equal(email).queryOne();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return user;
	}
	
	public String genernateToken(User user){
		String token=null;
		
		try{
			token=CommonUtil.md5(user.getName()+user.getPassword());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return token;
	}
	
	public long getUserArticleCount(User user){
		long count=0;
		
		try{
			count = Db.ar(Article.class).count("byUser", user.getId());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return count;
	}
	
	public long getUserReplyCount(User user){
		long count=0;
		
		try{
			/*
			count=DAOFactory.getDAO(Reply.class).selectAll()
			.where()
			.field("user_id").equal(user.getId())
			.count();
			*/
			
			count = Db.ar(Reply.class).count("byUser", user.getId());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return count;
	}
	
	public Boolean changePassword(User user){
		Boolean status=false;
		
		try{
			status=DAOFactory.getUpdateDAO().updateByFields(user, "password").intValue()>0;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return status;
	}
}
