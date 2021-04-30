package online;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import online.dao.rdb.RdbUserDao;
import online.data.util.EntityFactory;
import online.data.UserEntity;
import online.data.UserRole;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FirstUserCreationTest {
	private RdbUserDao rdbUserDao;
	private EntityFactory factory;

	@Autowired
	public void setFactory(EntityFactory factory) {
		this.factory = factory;
	}
	
	@Autowired
	public void SetUserDao(RdbUserDao RdbUserDao) {
		this.rdbUserDao = RdbUserDao;
	}
	
	
	@Before
	public void setup() {
		this.rdbUserDao.deleteAll();
	}
	
	@After
	public void tearDown() {
		this.rdbUserDao.deleteAll();
	}
	
	@Test
	public void testCreateUser() throws Exception {
		// GIVEN the database is empty
		
		// WHEN I create a user
		UserEntity user = factory.createNewUser("Games", "barel@gmail.com", "Barel3220", UserRole.ADMIN);
		
		String userKey = this.rdbUserDao.create(user).getKey();
		
		// THEN the database contains the inserted user
		assertThat(this.rdbUserDao.readById(userKey))
		.isPresent()
		.get()
		.isEqualToIgnoringGivenFields(user, "key");
	}
}
