package co.id.exml.logistikdr.biskuit;

import org.json.JSONArray;
import org.json.JSONObject;

public interface IBiskuitHandler {

	public void onStart();

	public void onSuccess(JSONObject response);

	public void onSuccess(JSONArray response );
	
	public void onFailure();

	public void onRetry();

}
