package online.layout;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import online.dao.UserNotFoundException;
import online.dao.UserRoleException;
import online.logic.UserService;

@RestController
public class UserController {
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(
			method=RequestMethod.POST,
			path="/onlinestore/newuser",
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary publish (
			@RequestBody UserBoundary user) {
		return new UserBoundary(
				this.userService
					.publishNewUser(
							user.toEntity()));
	}
	
	@RequestMapping(
			method=RequestMethod.GET,
			path= "/onlinestore/admin/users/{adminOnlineStore}/{adminEmail}",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary[] getUsers (
			@PathVariable("adminOnlineStore") String smartspace, 
			@PathVariable("adminEmail") String email,
			@RequestParam(name="size", required=false, defaultValue="10") int size, 
			@RequestParam(name="page", required=false, defaultValue="0") int page) {
		
		return 
			this.userService
				.getUsers(size, page, smartspace, email) // UserEntity List	
			.stream() // UserEntity Stream
			.map(UserBoundary::new) // UserBoundary Stream
			.collect(Collectors.toList()) // UserBoundary List
			.toArray(new UserBoundary[0]); // UserBoundary[]
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorMessage handleException(UserNotFoundException e){
		String message = e.getMessage();
		if (message == null || message.trim().isEmpty()) {
			message = "could not find user";
		}
		return new ErrorMessage(message);
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage handleException(UserRoleException e){
		String message = e.getMessage();
		if (message == null || message.trim().isEmpty()) {
			message = "Admin does not have authority";
		}
		return new ErrorMessage(message);
	}
}
