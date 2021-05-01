package online.dao.rdb;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import online.dao.AdvancedActionDao;
import online.data.ActionEntity;

@Repository
public class RdbActionDao implements AdvancedActionDao {
	private ActionCrud actionCrud;
	private IdCreatorCrud idCreatorCrud;
	
	public RdbActionDao() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	public void setActionCrud(ActionCrud actionCrud) {
		this.actionCrud = actionCrud;
	}
	
	@Autowired
	public void setIdCreatorCrud(IdCreatorCrud idCreatorCrud) {
		this.idCreatorCrud = idCreatorCrud;
	}

	@Override
	@Transactional
	public ActionEntity create(ActionEntity action) {
		IdCreator idCreator = this.idCreatorCrud.save(new IdCreator());
		action.setKey(action.getOnlineStore() + "#" + action.getItemId() + "#" + idCreator.getId());
		
		if (!this.actionCrud.existsById(action.getKey())) {
			ActionEntity actionEntity = this.actionCrud.save(action);
			this.idCreatorCrud.delete(idCreator);
			return actionEntity;
		}
		else
			throw new RuntimeException("Action Already Exists, Id: " + action.getKey());
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<ActionEntity> readById(String key) {
		return this.actionCrud.findById(key);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ActionEntity> readAll() {
		List<ActionEntity> list = new ArrayList<ActionEntity>();
		this.actionCrud.findAll().forEach(list::add);
		return list;
	}

	@Override
	@Transactional
	public void update(ActionEntity action) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional
	public void deleteByKey(String key) {
		this.actionCrud.deleteById(key);
	}

	@Override
	@Transactional
	public void deleteAll() {
		this.actionCrud.deleteAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ActionEntity> readAll(int size, int page) {
		return this.actionCrud.findAll(PageRequest.of(page, size)).getContent();
	}
	
	
}
