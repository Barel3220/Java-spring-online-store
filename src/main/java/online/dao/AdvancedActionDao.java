package online.dao;

import java.util.List;

import online.data.ActionEntity;

public interface AdvancedActionDao extends ActionDao<String> {
	public List<ActionEntity> readAll(int size, int page);
}
