package online.plugins;

import online.data.ActionEntity;

public interface Plugin {
	public Object action(ActionEntity action) throws Exception;
}
