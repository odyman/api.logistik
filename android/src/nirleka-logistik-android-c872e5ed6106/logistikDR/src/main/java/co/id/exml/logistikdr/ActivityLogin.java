package co.id.exml.logistikdr;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.id.exml.logistikdr.biskuit.IBiskuit;
import co.id.exml.logistikdr.biskuit.IBiskuitHandler;
import co.id.exml.logistikdr.dojo.DojoUserLogon;
import co.id.exml.logistikdr.fragment.activity.ActivityConfig;
import co.id.exml.logistikdr.utils.Dimana;
import co.id.exml.logistikdr.utils.Nanyakeun;

@SuppressWarnings("deprecation")
@SuppressLint("ShowToast")
public class ActivityLogin extends ActionBarActivity {

	private EditText username = null, password = null;
	private Button btnLogin = null;
	private ProgressDialog progress = null;
	private ImageView logo = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if( DojoUserLogon.hasLogon() ){
			showMainDashboard( 0 );
		};

		setContentView(R.layout.activity_login);

		if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
			getSupportActionBar().setSubtitle(R.string.client_company);
		}

		progress = new ProgressDialog(this);
		progress.setTitle("Loading");
		progress.setMessage("Silahkan tunggu...");
		progress.setCancelable(false);

		this.username = ( EditText ) findViewById(R.id.cpm_activity_login_username);
		this.password = ( EditText ) findViewById(R.id.cpm_activity_login_password);
		this.btnLogin = ( Button ) findViewById(R.id.cpm_activity_login_button_login);
		this.logo	  = ( ImageView ) findViewById(R.id.cpm_activity_login_logo);

		username.setText("10.0007.00006");
		password.setText("123456");

		this.btnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isDoRequest = false;
				if( getUsernameInput() != null && getPasswordInput() != null ) {
					isDoRequest = true;
				}
				if( isDoRequest ){
					//Toast.makeText(getApplicationContext(), "Silahkan tunggu!", Toast.LENGTH_SHORT).show();
					showMeTheMain( getUsernameInput(), getPasswordInput() );
				}else{
					Toast.makeText(getApplicationContext(), "Silahkan isi dengan benar!", Toast.LENGTH_SHORT).show();
				}
			}
		});

		Animation blinkBlink = AnimationUtils.loadAnimation( ActivityLogin.this, R.anim.tween);
		this.logo.startAnimation( blinkBlink );
	}

	@Override
	public void onResume(){
		if (!Dimana.isActive(this)){
			Nanyakeun.taros( this, getSupportFragmentManager(), "showWarningGPS", "Peringatan", getString(R.string.location_ask));
		};
		super.onResume();
	}

	private String getUsernameInput(){
		String result = null, temp = this.username.getText().toString().trim();
		if( temp.length() > 5 ){
			result = temp;
		};
		return result;
	}

	private String getPasswordInput(){
		String result = null, temp = this.password.getText().toString().trim();
		if( temp.length() > 5 ){
			result = temp;
		};
		return result;
	}

	private void showMeTheMain( String username, String password ){
		IBiskuit biskuit = new IBiskuit( this );
		biskuit.setKey();
		biskuit.setParam("username", username);
		biskuit.setParam("password", password);
		biskuit.post("/auth", new IBiskuitHandler(){

			@Override
			public void onStart() {
				progress.show();
			}

			@Override
			public void onSuccess(JSONObject response) {
				doIfSuccess( response );
			}

			@Override
			public void onSuccess(JSONArray response) {
				JSONObject obj = null;
				try {
					obj = response.getJSONObject(0);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				doIfSuccess( obj );
			}

			@Override
			public void onFailure() {
				doIfSuccess( null );
			}

			@Override
			public void onRetry() {
				doIfSuccess( null );
			}

		});
	}

	private void showMainDashboard( int baruLogon ){
		Bundle bungkus = new Bundle();
		bungkus.putInt("baruLogon", baruLogon );
		Intent intent = new Intent( ActivityLogin.this, ActivityMain.class );
		intent.putExtras(bungkus);
		startActivity( intent );
		finish();
	};

	private void doIfSuccess( JSONObject obj ){
		boolean statusLogon = false;
		if( obj != null ){
			try {
				if ( obj.getBoolean("status") == true ) {
					String token = obj.getString("token").trim();
					String nip = obj.getString("nip").trim();
					int type = obj.getInt("type");
					if ( token.length() > 0 && nip.length() > 0 && type > 0 ){
						DojoUserLogon.login( getUsernameInput(), token, nip, type);
						statusLogon = true;
					};
				};
			} catch (JSONException e) {
				e.printStackTrace();
			};
		};
		progress.dismiss();
		if( statusLogon ){
			showMainDashboard( 1 );
		}else{
			showWarning();
		}
	}

	private void showWarning(){
		Nanyakeun.taros( this, getSupportFragmentManager(), "showWarning", "Peringatan", "Login Gagal, silahkan ulangi lagi!");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_config, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_openconfig :
			showOpenCOnfigs();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void showOpenCOnfigs(){
		Intent intent = new Intent(ActivityLogin.this, ActivityConfig.class);
		startActivity(intent);
	}
}
