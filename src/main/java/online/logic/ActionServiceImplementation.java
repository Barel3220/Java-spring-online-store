package online.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import online.aop.UserRoleValidation;
import online.dao.ActionNotFoundException;
import online.dao.AdvancedActionDao;
import online.dao.UserNotFoundException;
import online.dao.UserRoleException;
import online.data.ActionEntity;

@Service
public class ActionServiceImplementation implements ActionService {
	private AdvancedActionDao advancedActionDao;
	
	@Autowired
	public void setAdvancedActionDao(AdvancedActionDao advancedActionDao) {
		this.advancedActionDao = advancedActionDao;
	}

	@Override
	@Transactional
	public ActionEntity publishNewAction(ActionEntity action, String role) {
		if (!validate(action) && roles(role))
			throw new RuntimeException("Invalid Action");
		else
			return this.advancedActionDao.create(action);
	}

	@Override
	public List<ActionEntity> getActions(int size, int page, String role) {
		return this.advancedActionDao.readAll(size, page);
	}

	@Override
	@UserRoleValidation
	public String checkUserRole(String onlineStore, String userEmail, String role) {
		return role;
	}

	@Override
	public ActionEntity getSpecificAction(String actionOnlineStore, String itemId, String actionId) {
		Optional<ActionEntity> action = this.advancedActionDao.readById(actionOnlineStore + "#" + itemId + "#" + actionId);
		if (action.isPresent())
			return action.get();
		else
			throw new ActionNotFoundException("Action Doesn't Exist");
	}
	
	private boolean validate(ActionEntity action) {
		return action != null
				&& action.getActionType() != null && !action.getActionType().trim().isEmpty()
				&& action.getItemId() != null && !action.getItemId().trim().isEmpty()
				&& action.getOnlineStore() != null && !action.getOnlineStore().trim().isEmpty()
				&& action.getPaymentMethod() != null && !action.getPaymentMethod().trim().isEmpty()
				&& action.getTotalPrice() != 0.0
				&& action.getAmount() != 0;
	}
	
	private boolean roles(String role) {
		if (role == "null")
			throw new UserNotFoundException("User Doesn't Exist");
		else if (role != "customer")
			throw new UserRoleException("User MUST be Customer to complete this operation");
		else
			return true;
	}
	
}
