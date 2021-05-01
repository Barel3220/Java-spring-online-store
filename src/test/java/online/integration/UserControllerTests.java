package online.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import online.dao.AdvancedUserDao;
import online.data.UserEntity;
import online.data.UserRole;
import online.data.util.EntityFactory;
import online.layout.UserBoundary;
import online.layout.UserKey;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties="spring.profiles.active=default")
public class UserControllerTests {
	private int port;
	private String baseUrl;
	private RestTemplate rest;
	private EntityFactory factory;
	private AdvancedUserDao advancedUserDao;
	
	@Autowired
	public void setFactory(EntityFactory factory) {
		this.factory = factory;
	}
	
	@Autowired
	public void setAdvancedUserDao(AdvancedUserDao advancedUserDao) {
		this.advancedUserDao = advancedUserDao;
	}
	
	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}
	
	@PostConstruct
	public void init() {
		this.baseUrl = "http://localhost:" + port + "/onlinestore";
		this.rest = new RestTemplate();
	}
	
	@Before
	public void setup() {
		this.advancedUserDao.deleteAll();
	}

	@After
	public void teardown() {
		this.advancedUserDao
			.deleteAll();
	}
	
	@Test
	public void testGet() throws Exception {
		// GIVEN the database is empty
		
		// WHEN I post a new User as ADMIN
		String onlineStore = "Games", userEmail = "barel@gmail.com";
		
		UserEntity user = factory.createNewUser(onlineStore, userEmail, "Barel3220", UserRole.ADMIN);
		this.advancedUserDao.create(user);
		
		this.rest
		.getForObject(this.baseUrl + "/admin/users/{onlineStore}/{userEmail}",
				UserBoundary[].class, onlineStore, userEmail);
		
		// THEN the database contains a single new ADMIN
		assertThat(this.advancedUserDao.readAll()).hasSize(1);
	}
	
	@Test(expected=Exception.class)
	public void testGetWitoutAdmin() throws Exception {
		// GIVEN the database is occupied with a MANAGER
		String onlineStore = "Games", userEmail = "barel@gmail.com";
		
		UserEntity user = factory.createNewUser(onlineStore, userEmail, "Barel3220", UserRole.MANAGER);
		this.advancedUserDao.create(user);
		
		// WHEN I try to GET all users
		this.rest
		.getForObject(this.baseUrl + "/admin/users/{onlineStore}/{userEmail}",
				UserBoundary[].class, onlineStore, userEmail);
		
		// THEN i get an error because I'm not an ADMIN
	}
	
	@Test
	public void testPostNewUserWithAdmin() throws Exception {
		// GIVEN the database is occupied with an ADMIN
		String onlineStore = "Games", userEmail = "barel@gmail.com";
		
		UserEntity user = factory.createNewUser(onlineStore, userEmail, "Barel3220", UserRole.ADMIN);
		this.advancedUserDao.create(user);
		
		// WHEN I try to POST a new user
		UserEntity newuser = factory.createNewUser("Electrical", "ligal@gmail.com", "ligali67", UserRole.MANAGER);
		
		UserBoundary userBoundary = new UserBoundary();
		userBoundary.setKey(new UserKey(newuser.getUserOnlineStore(), newuser.getUserEmail()));
		userBoundary.setRole(newuser.getRole().name());
		userBoundary.setUserName(newuser.getUserName());
		
		this.rest
			.postForObject(
					this.baseUrl + "/newuser", 
					userBoundary, 
					UserBoundary.class);
		
		// THEN the database should contain a new user
		assertThat(this.advancedUserDao.readAll()).hasSize(2);
	}
	
	@Test
	public void testGettingWithPagination() throws Exception {
		// GIVEN the database is occupied with an ADMIN
		String onlineStore = "Games", userEmail = "barel@gmail.com";
		
		UserEntity user = factory.createNewUser(onlineStore, userEmail, "Barel3220", UserRole.ADMIN);
		this.advancedUserDao.create(user);
		
		// WHEN the ADMIN inserts 5 new users, 
		// AND requesting a GET with pagination of page=0, size=3
		int input = 6;
		IntStream.range(1, input)
			.mapToObj(i->factory.createNewUser(onlineStore + i, userEmail, 
					user.getUserName() + i, UserRole.MANAGER))
			.forEach(this.advancedUserDao::create);

		// THEN 3 results will be back 
		int page = 0;
		int size = 3;
		UserBoundary[] actual =
				this.rest
				.getForObject(
						this.baseUrl + "/admin/users/{onlineStore}/{userEmail}?page={page}&size={size}", 
						UserBoundary[].class, onlineStore, userEmail, page, size);
		
		int expected = 3;
		assertThat(actual).hasSize(expected);
	}
	
}
