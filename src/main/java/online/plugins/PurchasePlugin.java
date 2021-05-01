package online.plugins;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import online.dao.AdvancedItemDao;
import online.data.ActionEntity;
import online.data.ItemEntity;

@Component
public class PurchasePlugin implements Plugin {
	private AdvancedItemDao advancedItemDao;
	
	@Autowired
	public void setAdvancedItemDao(AdvancedItemDao advancedItemDao) {
		this.advancedItemDao = advancedItemDao;
	}
	
	@Override
	public Object action(ActionEntity action) throws Exception {
		ItemEntity item = this.advancedItemDao.readById(action.getOnlineStore() + "#" + action.getItemId()).get();
		int amount = item.getAmountInStock();
		
		item.setAmountInStock(amount - action.getAmount());
		this.advancedItemDao.update(item);
		
		return new StringResponse("You've successfully purchased " + action.getAmount() + " " + item.getName() + "(s) for " + action.getTotalPrice());
	}
}
