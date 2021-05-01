package online.integration;

import static org.assertj.core.api.Assertions.assertThat;

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

import online.dao.AdvancedItemDao;
import online.dao.AdvancedCatalogDao;
import online.dao.AdvancedUserDao;
import online.data.CatalogEntity;
import online.data.ItemEntity;
import online.data.UserEntity;
import online.data.UserRole;
import online.data.util.EntityFactory;
import online.layout.ItemBoundary;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties="spring.profiles.active=default")
public class ItemControllerTests {
	private int port;
	private String baseUrl;
	private RestTemplate rest;
	private EntityFactory factory;
	private AdvancedUserDao advancedUserDao;
	private AdvancedCatalogDao advancedCatalogDao;
	private AdvancedItemDao advancedItemDao;
	
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
	
	@Autowired
	public void setAdvancedItemDao(AdvancedItemDao advancedItemDao) {
		this.advancedItemDao = advancedItemDao;
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
		this.advancedItemDao.deleteAll();
	}

	@After
	public void teardown() {
		this.advancedUserDao.deleteAll();
		this.advancedCatalogDao.deleteAll();
		this.advancedItemDao.deleteAll();
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
		
		ItemEntity item = factory.createNewItem(catalog.getCatalogId(), onlineStore, userEmail, "Blender", "39.9", false, 100, null);
		this.advancedItemDao.create(item);
		
		this.rest
		.getForObject(this.baseUrl + "/admin/items/{onlineStore}/{userEmail}",
				ItemBoundary[].class, onlineStore, userEmail);
		
		// THEN the database contains a single new Catalog
		assertThat(this.advancedItemDao.readAll()).hasSize(1);
	}
}
