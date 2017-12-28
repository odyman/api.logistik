package co.id.exml.logistikdr.biskuit;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import cz.msebera.android.httpclient.Header;
import co.id.exml.logistikdr.R;
import co.id.exml.logistikdr.dojo.DojoHash;
import co.id.exml.logistikdr.dojo.DojoUser;
import co.id.exml.logistikdr.dojo.DojoUserLogon;
import co.id.exml.logistikdr.sikuel.SikuelHash;
import co.id.exml.logistikdr.utils.Sempak;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class IBiskuit {
	private String biskuitUri = null;
	private String biskuitKey = null;
	private AsyncHttpClient client = null;
	private RequestParams params = null;
	private DojoUser userlogon = null;

	private static IBiskuit biskuit;

	public static synchronized IBiskuit getInstance( Context ctx ) {
		if ( biskuit == null ) {
			biskuit = new IBiskuit( ctx );
		};
		return biskuit;
	}

	public IBiskuit( Context ctx ){
		
		biskuitUri = Sempak.getInstance( ctx ).get( ctx.getString(R.string.configs_server_api));
		biskuitKey = Sempak.getInstance( ctx ).get( ctx.getString(R.string.configs_server_key));

		DojoHash hashURI = SikuelHash.getBy(ctx.getString(R.string.configs_server_api));
		DojoHash hashKey = SikuelHash.getBy(ctx.getString(R.string.configs_server_key));

		if( hashURI != null ) {
			biskuitUri = hashURI.val;
		}
		if( hashKey != null ) {
			biskuitKey = hashKey.val;
		}
		
		client = new AsyncHttpClient();
		params = new RequestParams();
		this.userlogon = DojoUserLogon.getUserLogon();
	}

	public IBiskuit setParams( RequestParams paramss ){
		params = paramss;
		return biskuit;
	}

	public IBiskuit setParam( String key, String value ){
		params.add(key, value);
		return biskuit;
	}


	public IBiskuit setAll(){
		this.setKey();
		this.setToken();
		this.setNIP();
		this.setType();
		return biskuit;
	}

	public IBiskuit setKey(){
		client.addHeader( "SMAPIKEY", biskuitKey );
		return biskuit;
	}

	public IBiskuit setToken(){
		client.addHeader( "SMAPITOKEN", userlogon.token );
		return biskuit;
	}

	public IBiskuit setNIP(){
		client.addHeader( "SMAPINIP", userlogon.nip );
		return biskuit;
	}

	public IBiskuit setType(){
		client.addHeader( "SMAPITYPE", String.valueOf(userlogon.type) );
		return biskuit;
	}

	public void get( String url, IBiskuitHandler responseHandler ) {
		client.get( getbiskuitUri( url ), params, getHandleResponse( responseHandler ) );
	}

	public void post(String url, IBiskuitHandler responseHandler ) {
		client.post( getbiskuitUri( url ), params, getHandleResponse( responseHandler ) );
	}

	private String getbiskuitUri( String relativeUrl ) {
		return biskuitUri + relativeUrl;
	}

	private JsonHttpResponseHandler getHandleResponse( final IBiskuitHandler handler ){
		return new JsonHttpResponseHandler() {

			@Override
			public void onStart() {
				handler.onStart();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				handler.onFailure();
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
				handler.onFailure();
			}
			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				handler.onFailure();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				handler.onSuccess(response);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONArray response ) {
				handler.onSuccess(response);
			}

			@Override
			public void onRetry(int retryNo) {
				handler.onRetry();
			}

		};
	}
}
