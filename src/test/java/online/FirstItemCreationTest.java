package online;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import online.dao.rdb.RdbItemDao;
import online.data.ItemEntity;
import online.data.util.EntityFactory;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FirstItemCreationTest {
	private EntityFactory factory;
	private RdbItemDao rdbItemDao;
	
	@Autowired
	public void setFactory(EntityFactory factory) {
		this.factory = factory;
	}
	
	@Autowired
	public void setRdbCatalogDao(RdbItemDao rdbItemDao) {
		this.rdbItemDao = rdbItemDao;
	}
	
	@Before
	public void setup() {
		this.rdbItemDao.deleteAll();
	}
	
	@After
	public void tearDown() {
		this.rdbItemDao.deleteAll();
	}
	
	@Test
	public void testCreateItem() throws Exception {
		// GIVEN the database is empty
		
		// WHEN I create a catalog
		ItemEntity item = factory.createNewItem("1", "Games", "barel@gmail.com", "Call of Duty", "39.9", false, 100, null);
		
		this.rdbItemDao.create(item);		
		
		// THEN I expect to find it in the database
		assertThat(this.rdbItemDao.readAll()).hasSize(1);
	}
}
