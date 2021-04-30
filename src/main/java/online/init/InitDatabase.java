package online.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import online.dao.UserDao;
import online.data.UserEntity;
import online.data.UserRole;

//@Component
public class InitDatabase implements CommandLineRunner {
	private UserDao<String> userDao;
	
	@Autowired
	public void setUserDao(UserDao<String> userDao) {
		this.userDao = userDao;
	}

	@Override
	public void run(String... args) throws Exception {
		UserEntity user = new UserEntity("Games", "barel@gmail.com", "Barel3220", UserRole.ADMIN);
		this.userDao.create(user);
	}

}
