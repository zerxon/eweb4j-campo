package org.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.eweb4j.mvc.validator.annotation.Email;
import org.eweb4j.mvc.validator.annotation.Required;
import org.eweb4j.util.CommonUtil;
import org.util.BaseUtil;

@Entity
@Table(name="t_user")
public class User {
	
	@Id
	private Long id;
	private String name;
	@Email(mess="邮箱格式不正确")
	@Required
	private String email;
	private String password;
	
	@Transient
	private String gravatar;

	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	private List<Article> articles=new ArrayList<Article>();
	
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	private List<Reply> replies=new ArrayList<Reply>();

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="t_mark",
			joinColumns=@JoinColumn(name="user_id", referencedColumnName="id"),
			inverseJoinColumns=@JoinColumn(name="article_id",referencedColumnName="id"))
	private List<Article> markArticles=new ArrayList<Article>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		//password = CommonUtil.md5(password);
		this.password = password;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}	
	
	public List<Reply> getReplies() {
		return replies;
	}

	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}
	
	public String getGravatar() {
		if(this.email!=null){
			String gravatar=BaseUtil.generateGravatarMd5Hex(this.email);
			this.gravatar=gravatar;
		}
		
		return gravatar;
	}

	public void setGravatar(String gravatar) {
		this.gravatar = gravatar;
	}

	public List<Article> getMarkArticles() {
		return markArticles;
	}

	public void setMarkArticles(List<Article> markArticles) {
		this.markArticles = markArticles;
	}
	
}
