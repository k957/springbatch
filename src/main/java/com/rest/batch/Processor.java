package com.rest.batch;


import javax.mail.internet.MimeMessage;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.rest.model.User;
@Component
public class Processor implements ItemProcessor<User, User>{

	
	@Autowired
	private JavaMailSender sender;
	
	String msg = "Message";
	
	@Override
	public User process(User user) throws Exception {
		    MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			String gender = user.getGender();
			helper.setTo(user.getEmailId());
			if(gender.equals("M")) {
				
				helper.setText(
						"<html><body>Hi <b>"+user.getName()+"</b> ,</br>Welcome to Caltex</br></br> Thanks for using our services </br>See you around</br>Team Caltex<img src='cid:id101'/><body></html>",
						true);
				helper.setSubject("Welcome to Caltex");
				ClassPathResource file1 = new ClassPathResource("Caltexlogo.png");
				helper.addInline("id101", file1);
			}
			else{
				
				helper.setText(
						"<html><body>Hi <b>"+user.getName()+"</b> ,</br>Welcome to amazon</br></br> Thanks for using our services </br>See you around</br>Team amazon<img src='cid:id101'/><body></html>",
						true);
				helper.setSubject("Welcome to amazon");
				ClassPathResource file1 = new ClassPathResource("amazonlogo.jpg");
				helper.addInline("id101", file1);
			}
			
			sender.send(message);
		
		return user;
	}

	
}
