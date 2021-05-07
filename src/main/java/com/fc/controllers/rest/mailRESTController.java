package com.fc.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fc.domain.ContactForm;
import com.fc.services.MailService;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class mailRESTController {
	
	@Autowired
	MailService mailService;
	
	@PostMapping("/contactForm")
	public void contactMail(@RequestBody ContactForm contactForm) {
		mailService.contactMail(contactForm.getFrom(), contactForm.getSubject(), contactForm.getText());
	}

}
