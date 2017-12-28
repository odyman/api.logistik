package co.id.exml.logistikdr.fragment.activity;

import com.activeandroid.ActiveAndroid;

import co.id.exml.logistikdr.R;
import co.id.exml.logistikdr.dojo.DojoHash;
import co.id.exml.logistikdr.fragment.FragmentConfig;
import co.id.exml.logistikdr.utils.Dimana;
import co.id.exml.logistikdr.utils.Nanyakeun;
import co.id.exml.logistikdr.utils.Sempak;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

@SuppressWarnings("deprecation")
public class ActivityConfig extends ActionBarActivity {

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		setContentView(R.layout.activity_empty);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		FragmentManager fragmentManager = getFragmentManager();
		if (fragmentManager.findFragmentById(android.R.id.content) == null) {
			FragmentTransaction ft = fragmentManager.beginTransaction();
			ft.replace(android.R.id.content, new FragmentConfig());
			ft.commit();
		}
		//((ActionBarActivity)this).getSupportActionBar().setDisplayShowHomeEnabled(true);

		if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
			getSupportActionBar().setTitle( "Pengaturan" );
			getSupportActionBar().setSubtitle( "Pengaturan Apps" );
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_form, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		case R.id.action_save :
			doSaveForm();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void doSaveForm(){
		ActiveAndroid.beginTransaction();
		try {
			DojoHash.truncate(DojoHash.class);

			String api = Sempak.getInstance( this ).get( this.getString(R.string.configs_server_api) );
			String key = Sempak.getInstance( this ).get( this.getString(R.string.configs_server_key) );
			
			new DojoHash( this.getString(R.string.configs_server_api), api ).save();
			new DojoHash( this.getString(R.string.configs_server_key), key ).save();
			
			ActiveAndroid.setTransactionSuccessful();
		}
		finally {
			ActiveAndroid.endTransaction();
			finish();
		}
	}
	
	@Override
	public void onResume(){
		if (!Dimana.isActive(this)){
			Nanyakeun.taros( this, getSupportFragmentManager(), "showWarningGPS", "Peringatan", getString(R.string.location_ask));
		};
		super.onResume();
	}

} 