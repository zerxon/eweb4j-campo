package org.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.eweb4j.util.CommonUtil;
import org.pegdown.PegDownProcessor;

@Entity
@Table(name="t_reply")
public class Reply {
	
	@Id
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="article_id", referencedColumnName="id")
	private Article article;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id", referencedColumnName="id")
	private User user;

	@Column(name="comment")
	private String comment;
	
	@Transient
	private String htmlComment;

	@Column(name="add_time")
	private String addTime;
	
	@Transient
	private String displayAddTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getHtmlComment() {
		if(this.comment!=null){
			PegDownProcessor parser=new PegDownProcessor();
			this.htmlComment=parser.markdownToHtml(this.comment);
		}
		
		return this.htmlComment;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	
	public String getDisplayAddTime() {
		if(this.addTime!=null){
			Date date=new Date(CommonUtil.toLong(this.addTime)*1000L);
			this.displayAddTime=CommonUtil.formatTime("yyyy年MM月dd日 HH:mm:ss", date);
		}
		
		return displayAddTime;
	}
}
