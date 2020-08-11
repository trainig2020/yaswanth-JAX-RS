package org.yaswanth.messenger.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement 
public class Comment {
    private long id;
    private String mesage;
    private Date created;
    private String author;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMesage() {
		return mesage;
	}
	public void setMesage(String mesage) {
		this.mesage = mesage;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Comment(long id, String mesage, String author) {
		super();
		this.id = id;
		this.mesage = mesage;
		this.created = new Date();
		this.author = author;
	}
	public Comment() {
		super();
	}
	
    
}
