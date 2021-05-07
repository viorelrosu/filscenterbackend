package com.fc.domain;

public class ContactForm {
	String from;
	String subject;
	String text;

	public ContactForm() {
	}

	public ContactForm(String from, String subject, String text) {
		super();
		this.from = from;
		this.subject = subject;
		this.text = text;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
