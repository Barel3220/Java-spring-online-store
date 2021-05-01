package online.data;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

import org.springframework.data.annotation.Id;

@Document(collection="ACTIONS")
public class ActionEntity implements OnlineStoreEntity<String> {
	private String key;
	private String actionId;
	private String itemId;
	private String onlineStore;
	private String actionType;
	private String paymentMethod;
	private Date creationTimestamp;
	private float totalPrice;
	private int amount;
	
	public ActionEntity() {
		// TODO Auto-generated constructor stub
	}

	public ActionEntity(String itemId, String onlineStore, String actionType, String paymentMethod, float totalPrice,
			int amount) {
		this.itemId = itemId;
		this.onlineStore = onlineStore;
		this.actionType = actionType;
		this.paymentMethod = paymentMethod;
		this.totalPrice = totalPrice;
		this.amount = amount;
	}

	@Override
	@Id
	public String getKey() {
		this.key = this.onlineStore + "#" + this.itemId + "#" + this.actionId;
		return key;
	}

	@Override
	public void setKey(String key) {
		this.key = key;
		String[] temp = key.split("#");
		this.onlineStore = temp[0];
		this.itemId = temp[1];
		this.actionId = temp[2];
	}

	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
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
}
