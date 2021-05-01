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

import online.dao.AdvancedCatalogDao;
import online.dao.AdvancedUserDao;
import online.data.CatalogEntity;
import online.data.UserEntity;
import online.data.UserRole;
import online.data.util.EntityFactory;
import online.layout.CatalogBoundary;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties="spring.profiles.active=default")
public class CatalogControllerTests {
	private int port;
	private String baseUrl;
	private RestTemplate rest;
	private EntityFactory factory;
	private AdvancedUserDao advancedUserDao;
	private AdvancedCatalogDao advancedCatalogDao;
	
	@Autowired
	public void setFactory(EntityFactory factory) {
		this.factory = factory;
	}
	
	@Autowired
	public void AdvancedUserDao(AdvancedUserDao advancedUserDao) {
		this.advancedUserDao = advancedUserDao;
	}
	
	@Autowired
	public void setAdvancedCatalogDao(AdvancedCatalogDao advancedCatalogDao) {
		this.advancedCatalogDao = advancedCatalogDao;
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
		this.advancedCatalogDao.deleteAll();
	}

	@After
	public void teardown() {
		this.advancedUserDao.deleteAll();
		this.advancedCatalogDao.deleteAll();
	}
	
	@Test
	public void testGet() throws Exception {
		// GIVEN the database is empty
		
		// WHEN I post a new Catalog as ADMIN
		String onlineStore = "Games", userEmail = "barel@gmail.com";
		
		UserEntity user = factory.createNewUser(onlineStore, userEmail, "Barel3220", UserRole.ADMIN);
		this.advancedUserDao.create(user);
		
		CatalogEntity catalog = factory.createNewCatalog(onlineStore, "Games-Action", false, userEmail);
		this.advancedCatalogDao.create(catalog);
		
		this.rest
		.getForObject(this.baseUrl + "/admin/catalogs/{onlineStore}/{userEmail}",
				CatalogBoundary[].class, onlineStore, userEmail);
		
		// THEN the database contains a single new Catalog
		assertThat(this.advancedCatalogDao.readAll()).hasSize(1);
	}
	
	@Test(expected=Exception.class)
	public void testGetWitoutAdmin() throws Exception {
		// GIVEN the database is occupied with a CUSTOMER
		String onlineStore = "All", userEmail = "barel@gmail.com";
		
		UserEntity user = factory.createNewUser(onlineStore, userEmail, "Barel3220", UserRole.CUSTOMER);
		this.advancedUserDao.create(user);
		
		CatalogEntity catalog = factory.createNewCatalog("Games", "Games-Action", false, userEmail);
		this.advancedCatalogDao.create(catalog);
		
		// WHEN I try to GET all catalogs
		this.rest
		.getForObject(this.baseUrl + "/admin/catalogs/{onlineStore}/{userEmail}",
				CatalogBoundary[].class, onlineStore, userEmail);
		
		// THEN i get an error because I'm not an ADMIN/MANAGER
	}
	
	@Test
	public void testGetWithCustomer() throws Exception {
		// GIVEN the database is occupied with a CUSTOMER
		String onlineStore = "All", userEmail = "barel@gmail.com";
		String catalogOnlineStore = "Games";
		UserEntity user = factory.createNewUser(onlineStore, userEmail, "Barel3220", UserRole.CUSTOMER);
		this.advancedUserDao.create(user);
		
		CatalogEntity catalog1 = factory.createNewCatalog("Games", "Games-Action", false, userEmail);
		this.advancedCatalogDao.create(catalog1);
		
		CatalogEntity catalog2 = factory.createNewCatalog("Games", "Games-Shooting", false, userEmail);
		this.advancedCatalogDao.create(catalog2);
		
		CatalogEntity catalog3 = factory.createNewCatalog("Electrical", "Electrical-Kitchen", false, userEmail);
		this.advancedCatalogDao.create(catalog3);
		
		// WHEN I try to GET catalogs from "Games"
		CatalogBoundary[] actual = this.rest
		.getForObject(this.baseUrl + "/catalogs/{catalogOnlineStore}",
				CatalogBoundary[].class, catalogOnlineStore);
		
		// THEN I should get 2
		int expected = 2;
		assertThat(actual).hasSize(expected);
	}
	
	@Test
	public void testGettingWithPagination() throws Exception {
		// GIVEN the database is occupied with an ADMIN
		String onlineStore = "Games", userEmail = "barel@gmail.com";
		
		UserEntity user = factory.createNewUser(onlineStore, userEmail, "Barel3220", UserRole.ADMIN);
		this.advancedUserDao.create(user);
		
		// WHEN the ADMIN inserts 5 new catalogs, 
		// AND requesting a GET with pagination of page=0, size=3
		int input = 6;
		IntStream.range(1, input)
			.mapToObj(i->factory.createNewCatalog(onlineStore + i, "Games-Action", false, userEmail))
			.forEach(this.advancedCatalogDao::create);

		// THEN 3 results will be back 
		int page = 0;
		int size = 3;
		CatalogBoundary[] actual =
				this.rest
				.getForObject(
						this.baseUrl + "/admin/catalogs/{onlineStore}/{userEmail}?page={page}&size={size}", 
						CatalogBoundary[].class, onlineStore, userEmail, page, size);
		
		int expected = 3;
		assertThat(actual).hasSize(expected);
	}
}
