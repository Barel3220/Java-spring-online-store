package online;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import online.dao.rdb.RdbCatalogDao;
import online.data.CatalogEntity;
import online.data.util.EntityFactory;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FirstCatalogCreationTest {
	private RdbCatalogDao rdbCatalogDao;
	private EntityFactory factory;
	
	@Autowired
	public void setFactory(EntityFactory factory) {
		this.factory = factory;
	}
	
	@Autowired
	public void setRdbCatalogDao(RdbCatalogDao rdbCatalogDao) {
		this.rdbCatalogDao = rdbCatalogDao;
	}
	
	@Before
	public void setup() {
		this.rdbCatalogDao.deleteAll();
	}
	
	@After
	public void tearDown() {
		this.rdbCatalogDao.deleteAll();
	}
	
	@Test
	public void testCreateCatalog() throws Exception {
		// GIVEN the database is empty
		
		// WHEN I create a catalog
		CatalogEntity catalog = factory.createNewCatalog("Games", "Games-Action", false, "barel@gmail.com");
		
		this.rdbCatalogDao.create(catalog);		
		
		// THEN I expect to find it in the database
		assertThat(this.rdbCatalogDao.readAll()).hasSize(1);
	}
}
