package com.iktpreobuka.project.controllers;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.project.entities.Roles;
import com.iktpreobuka.project.entities.UserEntity;

@RestController
@RequestMapping ("/project/users")
public class UserController {

	List<UserEntity> users = new ArrayList<UserEntity>();
	
	private List<UserEntity> getDB(){
		if(users.size()==0) {
			UserEntity u1 = new UserEntity(1, "Vladimir", "Dimitrieski","dimitrieski@uns.ac.rs","vladimir", "vladimir", Roles.ROLE_CUSTOMER);
			UserEntity u2 = new UserEntity(2, "Milan", "Celikovic", "milancel@uns.ac.rs","milan", "milan",  Roles.ROLE_CUSTOMER);
			UserEntity u3 = new UserEntity(3, "Nebojsa", "Horvat", "horva.n@uns.ac.rs","nebojsa", "nebojsa", Roles.ROLE_CUSTOMER);
			users.add(u1);
			users.add(u2);
			users.add(u3);
			return users;
		}
		return users;
	}
	
	//TODO GET - 1.3 vraća listu korisnika aplikacije - /project/users
		@RequestMapping(value = "/", method = RequestMethod.GET)
		public List<UserEntity> getAll(){
			return getDB();
		}
	
	//TODO GET - 1.4 vraća korisnika po vrednosti prosleđenog ID-a - /project/users/{id}
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public UserEntity getOne(@PathVariable Integer id){
		List<UserEntity> users = getDB();
		for(UserEntity us : users){
			if(us.getId().equals(id))
				return us;
		}
		return null;
	}
	
	//TODO GET - 1.5 dodavanje novog korisnika - /project/users
	@RequestMapping(value = "/",method = RequestMethod.POST)
	public UserEntity changeUser (@RequestBody UserEntity newUser) {
		List<UserEntity> users = getDB();	
		boolean ima = false;
		for(UserEntity user : users){
			if(user.getUserName().equals(newUser.getUserName()) &&
					user.getEmail().equals(newUser.getEmail()))
				ima=true;
			if(ima==false) {
				newUser.setId((new Random()).nextInt());
				users.add(newUser);
				return user;
			}
		}
		return null;
	}
	
	//TODO PUT - 1.6 omogućava izmen postojećeg korisnika - /project/users/{id}
	@RequestMapping(value = "/{id}",method = RequestMethod.PUT)
	public UserEntity changeClient(@RequestBody UserEntity changedUser, 
			@PathVariable Integer id) {
		List<UserEntity> users = getDB();	
		for(UserEntity user : users){
			if(user.getId().equals(id)) {
				if(changedUser.getEmail() !=null)
					user.setEmail(changedUser.getEmail());
				if(changedUser.getFirstName() !=null)
					user.setFirstName (changedUser.getFirstName());
				if(changedUser.getLastName() !=null)
					user.setLastName(changedUser.getLastName());
				if(changedUser.geteUserRole() !=null)
					user.seteUserRole(changedUser.geteUserRole());
				return user;
			}else
				return null;
		}
		return null;
	}
	
	
	//TODO PUT - 1.7 izmena atributa user_role postojećeg korisnika - /project/users/change/{id}/role/{role}
	@RequestMapping(value = "/change/{id}/role/{role}",method = RequestMethod.PUT)
	public UserEntity changeRole(@PathVariable Integer id,  
			@PathVariable Roles role) {
		List<UserEntity> users = getDB();
		for(UserEntity user : users){
			if(user.getId().equals(id)) {
				user.seteUserRole(role);
				return user;
			}
		}
		return null;
	}
	
	//TODO PUT - 1.8 izmenu vrednosti atributa password postojećeg korisnika - /project/users/changePassword/{id}
	@RequestMapping (value="/changePassword/{id}",method = RequestMethod.PUT)
	public List<UserEntity> changePassword(@PathVariable Integer id, @RequestParam String staraLoz, @RequestParam String novaLoz) {
		List<UserEntity> users = getDB();
		for(UserEntity user : users){
			if(user.getPassword().equals(staraLoz)) {
				user.setPassword(novaLoz);
			}			
		}
		return users;	
	}
	
	//TODO DELETE - 1.9 omogućava brisanje postojećeg korisnika
	@RequestMapping (value="/{id}", method = RequestMethod.DELETE)
	public UserEntity deleteUser (@PathVariable Integer id){
		List<UserEntity> users = getDB();
		Iterator <UserEntity> it = users.iterator();
		while (it.hasNext()) {
			UserEntity user =it.next();
			if(user.getId().equals(id)) {
				it.remove();
				return user;
			}
		}
		return null;
	}
	//TODO GET - 1.10 vraća korisnika po vrednosti prosleđenog username-a - /project/users/by-username/{username}
	@RequestMapping(value="/by-username/{username}", method=RequestMethod.GET)
	public UserEntity getByUsername(@PathVariable String username) {
		List<UserEntity> users = getDB();
		for(UserEntity user : users){
			if(user.getPassword().equals(username)) {
				return user;
			}
		}
		return null;
	}
	
	///
}
