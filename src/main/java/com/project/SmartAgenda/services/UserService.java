package com.project.SmartAgenda.services;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.core.serializer.Serializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.SmartAgenda.beans.Agenda;
import com.project.SmartAgenda.beans.User;
import com.project.SmartAgenda.repositories.UserRepositorie;

@CrossOrigin("*")
@RestController
public class UserService {
	@Autowired
	UserRepositorie userRepositorie;
	
	@RequestMapping(value="/smartAgenda/authentication",method=RequestMethod.GET,consumes=org.springframework.http.MediaType.APPLICATION_JSON_VALUE,produces="application/json")
	public  ResponseEntity<?> authentication(@RequestParam("login") String login,@RequestParam("password")String password)
	{
		
		User userFound=userRepositorie.authentication(login, password);
		
		if(userFound!=null)
			return new ResponseEntity<User>(userFound,HttpStatus.FOUND);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);		
	}
	
	@RequestMapping(value="/smartAgenda/deleteAccount",method=RequestMethod.DELETE,consumes=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteAcount()
	{
		
		try{
			Agenda agenda=Agenda.getInstance();
		userRepositorie.delete(agenda.getUser());
		return new ResponseEntity<>(HttpStatus.MOVED_PERMANENTLY);}
		catch(Exception e)
		{
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.METHOD_FAILURE);
			
		}
	}
	
	@RequestMapping(value="/smartAgenda/display",method=RequestMethod.GET,consumes=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> display()
	{
		User userToDisplay=Agenda.getInstance().getUser();
		if(userToDisplay!=null)
			return new ResponseEntity<User>(userToDisplay,HttpStatus.FOUND);
		return new ResponseEntity<User>(userToDisplay,HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/smartAgenda/update",method=RequestMethod.PUT,consumes=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody User userToUpdate)
	{
		userRepositorie.saveAndFlush(userToUpdate);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/smartAgenda/resetPassword",method=RequestMethod.PUT,consumes=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestParam String newPassword)
	{
		User userToUpdate=Agenda.getInstance().getUser();
		userToUpdate.setPassword(newPassword);
		userRepositorie.saveAndFlush(userToUpdate);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
 
}
