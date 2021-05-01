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

import online.dao.CatalogNotFoundException;
import online.logic.CatalogService;

@RestController
public class CatalogController {
	CatalogService catalogService;
	
	@Autowired
	public void setCatalogService(CatalogService catalogService) {
		this.catalogService = catalogService;
	}
	
	@RequestMapping(
			method=RequestMethod.POST,
			path="/onlinestore/newcatalog/{userOnlineStore}/{userEmail}",
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public CatalogBoundary publish (
			@PathVariable("userOnlineStore") String userOnlineStore, 
			@PathVariable("userEmail") String userEmail,
			@RequestBody CatalogBoundary catalog) {
		
		String role = this.catalogService.checkUserRole(userOnlineStore, userEmail, "");
		return new CatalogBoundary(
				this.catalogService
					.publishNewCatalog(
							catalog.toEntity(), role));
	}
	
	@RequestMapping(
			method=RequestMethod.GET,
			path= "/onlinestore/admin/catalogs/{userOnlineStore}/{userEmail}",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public CatalogBoundary[] getCatalogs (
			@PathVariable("userOnlineStore") String userOnlineStore, 
			@PathVariable("userEmail") String userEmail,
			@RequestParam(name="size", required=false, defaultValue="10") int size, 
			@RequestParam(name="page", required=false, defaultValue="0") int page) {
		
		String role = this.catalogService.checkUserRole(userOnlineStore, userEmail, "");
		return 
			this.catalogService
				.getCatalogs(size, page, role) // CatalogEntity List	
			.stream() // CatalogEntity Stream
			.map(CatalogBoundary::new) // CatalogBoundary Stream
			.collect(Collectors.toList()) // CatalogBoundary List
			.toArray(new CatalogBoundary[0]); // CatalogBoundary[]
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorMessage handleException(CatalogNotFoundException e){
		String message = e.getMessage();
		if (message == null || message.trim().isEmpty()) {
			message = "Couldn't Find Catalog";
		}
		return new ErrorMessage(message);
	}
}
