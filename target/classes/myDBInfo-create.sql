SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS t_user;
CREATE TABLE t_user(
	id bigint (20)  AUTO_INCREMENT , 
	name varchar (255) , 
	email varchar (255) , 
	password varchar (255) , 
	PRIMARY KEY (id) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS t_article;
CREATE TABLE t_article(
	id bigint (20)  AUTO_INCREMENT , 
	title varchar (255) , 
	content varchar (255) , 
	pub_date varchar (255) , 
	user_id bigint (20) , 
	PRIMARY KEY (id), 
	KEY user_id (user_id), 
	CONSTRAINT t_article_user_id FOREIGN KEY (user_id) REFERENCES t_user (id) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
