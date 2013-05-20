package org.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.eweb4j.util.CommonUtil;
import org.pegdown.PegDownProcessor;

@Entity
@Table(name="t_article")
public class Article {
	
	@Id
	private Long id;
	private String title;
	private String content;
	
	@Transient
	private String htmlContent;
	
	@Column(name="pub_date")
	private String pubDate;

	@Transient
	private String displayDate;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToMany(mappedBy="article", fetch=FetchType.LAZY)
	private List<Reply> replies=new ArrayList<Reply>();
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="t_mark",
			joinColumns=@JoinColumn(name="article_id", referencedColumnName="id"),
			inverseJoinColumns=@JoinColumn(name="user_id", referencedColumnName="id"))
	private List<User> markUsers=new ArrayList<User>();
	
	@OneToMany(mappedBy = "article", fetch=FetchType.LAZY)
	private List<Mark> marks = new ArrayList<Mark>();
	
	public void setDisplayDate(String displayDate) {
		this.displayDate = displayDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getHtmlContent() {
		if(this.content!=null){
			PegDownProcessor parser=new PegDownProcessor();
			this.htmlContent=parser.markdownToHtml(this.content);
		}
		
		return this.htmlContent;
	}

	public String getPubDate() {
		
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		
		this.pubDate = pubDate;
	}

	public String getDisplayDate() {
		if(this.pubDate!=null){
			Date d = new Date(CommonUtil.toLong(this.pubDate)*1000L);
			this.displayDate = CommonUtil.formatTime("yyyy年MM月dd日 HH:mm:ss", d);
		}
	
		return this.displayDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public List<Reply> getReplies() {
		return replies;
	}

	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}
	
	public List<User> getMarkUsers() {
		return markUsers;
	}

	public void setMarkUsers(List<User> markUser) {
		this.markUsers = markUser;
	}

	public void setMarks(List<Mark> marks) {
		this.marks = marks;
	}

	public List<Mark> getMarks() {
		return marks;
	}

}
