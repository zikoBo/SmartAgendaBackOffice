package com.project.SmartAgenda.services;


import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.SmartAgenda.beans.Agenda;
import com.project.SmartAgenda.beans.User;
import com.project.SmartAgenda.repositories.AgendaRepositorie;
import com.project.SmartAgenda.repositories.UserRepositorie;

@CrossOrigin("*")
@RestController
public class UserService {
	@Autowired
	UserRepositorie userRepositorie;
	@Autowired
	AgendaRepositorie agendaRepositorie;
	
	
	@RequestMapping(value="/smartAgenda/authentication",method=RequestMethod.GET,consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces="application/json")
	public ResponseEntity<?> authentication(@RequestParam("login") String login,@RequestParam("password")String password)
	{
		
		 User userFound=userRepositorie.authentication(login, password);
		
		if(userFound!=null)
			return new ResponseEntity<User>(userFound,HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value="/smartAgenda/addUser",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces="application/json")
	public ResponseEntity<?> addUser(@RequestBody User userToAdd)
	{
		try{
		User userAdded=userRepositorie.save(userToAdd);
		return new ResponseEntity<User>(userAdded,HttpStatus.CREATED);
		}catch(Exception e)
		{
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.METHOD_FAILURE);

		}
	}
	
	@RequestMapping(value="/smartAgenda/deleteAccount",method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteAcount(@RequestParam(name="idUser") int idUser)
	{
		User userToDelete=userRepositorie.getOne(idUser);
		Agenda agendaToDelete=agendaRepositorie.findByUser(userToDelete);
		agendaRepositorie.delete(agendaToDelete);
		userRepositorie.delete(userToDelete);
//		userRepositorie.deleteAgendasForUser(idUser);
//		userRepositorie.deleteById(idUser);
		
//		Agenda agenda=Agenda.getInstance();
//		System.out.println("le user "+agenda.getUser().getFirstName());
//		userRepositorie.delete(agenda.getUser());
		return new ResponseEntity<Boolean>(true,HttpStatus.MOVED_PERMANENTLY);
	
	}
	
	@RequestMapping(value="/smartAgenda/display",method=RequestMethod.GET,consumes=org.springframework.http.MediaType.APPLICATION_JSON_VALUE,produces="application/json")
	public ResponseEntity<?> display(@RequestParam("idUser") int idUser)
	{
		
		User userToDisplay=userRepositorie.getOne(idUser);
		if(userToDisplay!=null)
			return new ResponseEntity<User>(userToDisplay,HttpStatus.FOUND);
		return new ResponseEntity<User>(userToDisplay,HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/smartAgenda/update",method=RequestMethod.PUT,consumes=org.springframework.http.MediaType.APPLICATION_JSON_VALUE,produces="application/json")
	public ResponseEntity<?> update(@RequestBody User userToUpdate)
	{
		userRepositorie.saveAndFlush(userToUpdate);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/smartAgenda/resetPassword",method=RequestMethod.PUT,consumes=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestParam("idUser") int idUser,@RequestParam("newPassword") String newPassword)
	{
		
		User userToUpdate=userRepositorie.findByIdUser(idUser);
		userToUpdate.setPassword(newPassword);
		userRepositorie.saveAndFlush(userToUpdate);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
 
}
