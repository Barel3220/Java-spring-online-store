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

import online.dao.ActionNotFoundException;
import online.logic.ActionService;

@RestController
public class ActionController {
	private ActionService actionService;
	
	@Autowired
	public void setActionService(ActionService actionService) {
		this.actionService = actionService;
	}
	
	@RequestMapping(
			method=RequestMethod.POST,
			path="/onlinestore/newaction/{userOnlineStore}/{userEmail}",
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public Object publish (
			@PathVariable("userOnlineStore") String userOnlineStore, 
			@PathVariable("userEmail") String userEmail,
			@RequestBody ActionBoundary action) {
		
		String role = this.actionService.checkUserRole(userOnlineStore, userEmail, "");
		return this.actionService.publishNewAction(action.toEntity(), role);
	}
	
	@RequestMapping(
			method=RequestMethod.GET,
			path= "/onlinestore/admin/actions/{userOnlineStore}/{userEmail}",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ActionBoundary[] getCatalogs (
			@PathVariable("userOnlineStore") String userOnlineStore, 
			@PathVariable("userEmail") String userEmail,
			@RequestParam(name="size", required=false, defaultValue="10") int size, 
			@RequestParam(name="page", required=false, defaultValue="0") int page) {
		
		String role = this.actionService.checkUserRole(userOnlineStore, userEmail, "");
		return 
			this.actionService
				.getActions(size, page, role) // ActionEntity List	
			.stream() // ActionEntity Stream
			.map(ActionBoundary::new) // ActionBoundary Stream
			.collect(Collectors.toList()) // ActionBoundary List
			.toArray(new ActionBoundary[0]); // ActionBoundary[]
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorMessage handleException(ActionNotFoundException e) {
		String message = e.getMessage();
		
		if (message == null || message.trim().isEmpty())
			message = "could not find error message";
		
		return new ErrorMessage(message);
	}
}
