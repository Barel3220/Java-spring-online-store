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

import online.dao.ItemNotFoundException;
import online.logic.ItemService;

@RestController
public class ItemController {
	private ItemService itemService;
	
	@Autowired
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}
	
	@RequestMapping(
			method=RequestMethod.POST,
			path="/onlinestore/newitem/{userOnlineStore}/{userEmail}",
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary publish (
			@PathVariable("userOnlineStore") String userOnlineStore, 
			@PathVariable("userEmail") String userEmail,
			@RequestBody ItemBoundary item) {
		
		String role = this.itemService.checkUserRole(userOnlineStore, userEmail, "");
		return new ItemBoundary(
				this.itemService
					.publishNewItem(
							item.toEntity(), role));
	}
	
	@RequestMapping(
			method=RequestMethod.GET,
			path= "/onlinestore/admin/items/{userOnlineStore}/{userEmail}",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ItemBoundary[] getCatalogs (
			@PathVariable("userOnlineStore") String userOnlineStore, 
			@PathVariable("userEmail") String userEmail,
			@RequestParam(name="size", required=false, defaultValue="10") int size, 
			@RequestParam(name="page", required=false, defaultValue="0") int page) {
		
		String role = this.itemService.checkUserRole(userOnlineStore, userEmail, "");
		return 
			this.itemService
				.getItems(size, page, role) // ItemEntity List	
			.stream() // ItemEntity Stream
			.map(ItemBoundary::new) // ItemBoundary Stream
			.collect(Collectors.toList()) // ItemBoundary List
			.toArray(new ItemBoundary[0]); // ItemBoundary[]
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorMessage handleException(ItemNotFoundException e){
		String message = e.getMessage();
		if (message == null || message.trim().isEmpty()) {
			message = "Couldn't Find Item";
		}
		return new ErrorMessage(message);
	}
}
