package online.dao.rdb;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import online.dao.AdvancedUserDao;
import online.data.UserEntity;

@Repository
public class RdbUserDao implements AdvancedUserDao {
	private UserCrud userCrud;
	
	public RdbUserDao() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	public void setUserCrud(UserCrud userCrud) {
		this.userCrud = userCrud;
	}

	@Override
	@Transactional
	public UserEntity create(UserEntity user) {		
		user.setKey(user.getUserOnlineStore() + "#" + user.getUserEmail());
		
		// SQL: INSERT
		if (!this.userCrud.existsById(user.getKey())) {
			UserEntity userEntity = this.userCrud.save(user);
			return userEntity;
		}
		else
			throw new RuntimeException("user already exists: " + user.getKey());
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<UserEntity> readById(String key) {	
		return this.userCrud.findById(key);
	}

	@Override
	@Transactional(readOnly=true)
	public List<UserEntity> readAll() {
		List<UserEntity> list = new ArrayList<>();
		// SQL: SELECT
	    this.userCrud.findAll()
				.forEach(list::add);
	    return list;
	}

	@Override
	@Transactional
	public void update(UserEntity user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// SQL: DELETE
		this.userCrud.deleteAll();
	}

	@Override
	@Transactional(readOnly=true)
	public List<UserEntity> readAll(int size, int page) {
		return this.userCrud
				.findAll(PageRequest.of(page, size))
				.getContent();
	}

}
