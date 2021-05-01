package online.layout;

import java.util.Date;

import online.data.ActionEntity;

public class ActionBoundary {
	private ActionKey key;
	private String itemId;
	private String onlineStore;
	private String actionType;
	private String paymentMethod;
	private Date creationTimestamp;
	private float totalPrice;
	private int amount;
	
	public ActionBoundary() {
		// TODO Auto-generated constructor stub
	}

	public ActionBoundary(ActionEntity action) {
		key = new ActionKey(action.getOnlineStore(), action.getItemId(), action.getActionId());
		itemId = action.getItemId();
		onlineStore = action.getOnlineStore();
		actionType = action.getActionType();
		paymentMethod = action.getPaymentMethod();
		creationTimestamp = action.getCreationTimestamp();
		totalPrice = action.getTotalPrice();
		amount = action.getAmount();
	}

	public ActionKey getKey() {
		return key;
	}

	public void setKey(ActionKey key) {
		this.key = key;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getOnlineStore() {
		return onlineStore;
	}

	public void setOnlineStore(String onlineStore) {
		this.onlineStore = onlineStore;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Date getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public ActionEntity toEntity() {
		ActionEntity action = new ActionEntity();
		
		action.setActionType(this.getActionType());
		action.setAmount(this.getAmount());
		action.setCreationTimestamp(new Date());
		action.setItemId(this.getItemId());
		action.setOnlineStore(this.getOnlineStore());
		action.setTotalPrice(this.getTotalPrice());
		action.setPaymentMethod(this.getPaymentMethod());
		
		return action;
	}

}
