package online.logic;

import java.util.List;

import online.data.ActionEntity;

public interface ActionService {
	public Object publishNewAction(ActionEntity action, String role);
	public List<ActionEntity> getActions(int size, int page, String role);
	public String checkUserRole(String onlineStore, String userEmail, String role);
	public ActionEntity getSpecificAction(String actionOnlineStore, String itemId, String actionId);
}
