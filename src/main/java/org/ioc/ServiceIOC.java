package org.ioc;

import org.service.ArticleService;
import org.service.MarkService;
import org.service.ReplyService;
import org.service.UserService;

public interface ServiceIOC {
	public UserService createUserService();
	
	public ArticleService createArticleService();
	
	public MarkService createMarkService();
	
	public ReplyService createReplyService();
}
