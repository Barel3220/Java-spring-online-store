package online.dao;

import java.util.List;
import java.util.Optional;

import online.data.ActionEntity;

public interface ActionDao<ActionKey> {
	public ActionEntity create(ActionEntity action);
	public Optional<ActionEntity> readById(ActionKey key);
	public List<ActionEntity> readAll();
	public void update(ActionEntity action);
	public void deleteByKey(ActionKey key);
	public void deleteAll();
}
