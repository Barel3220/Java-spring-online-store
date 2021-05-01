package online.layout;

public class ActionKey {
	private String onlineStore;
	private String itemId;
	private String actionId;
	
	public ActionKey() {
		// TODO Auto-generated constructor stub
	}

	public ActionKey(String onlineStore, String itemId, String actionId) {
		this.onlineStore = onlineStore;
		this.itemId = itemId;
		this.actionId = actionId;
	}

	public String getOnlineStore() {
		return onlineStore;
	}

	public void setOnlineStore(String onlineStore) {
		this.onlineStore = onlineStore;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
}
