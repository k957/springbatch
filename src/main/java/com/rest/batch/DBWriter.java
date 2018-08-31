package com.rest.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.rest.model.User;
@Component
public class DBWriter implements ItemWriter<User>{

	@Override
	public void write(List<? extends User> user) throws Exception {
		
		String mail=user.iterator().next().getEmailId();
		System.out.println("Mail sent to : "+mail);
	}

}
