package co.id.exml.logistikdr.fragment.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import co.id.exml.logistikdr.R;
import co.id.exml.logistikdr.adapter.AdapterBaka;
import co.id.exml.logistikdr.fragment.FragmentBakaListview;
import co.id.exml.logistikdr.fragment.FragmentBakaListviewCallback;
import co.id.exml.logistikdr.utils.Dimana;
import co.id.exml.logistikdr.utils.Nanyakeun;
import co.id.exml.logistikdr.utils.Temp;

@SuppressLint("DefaultLocale")
@SuppressWarnings("deprecation")
public class ActivityBaka extends ActionBarActivity {

	private List<Temp> values;
	private String responseCode;
	private Bundle bungkus;
	private AdapterBaka adapter;

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		bungkus = getIntent().getExtras();

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		this.values = (List<Temp>) bungkus.getSerializable("temp");
		responseCode = bungkus.getString("response");

		adapter = new AdapterBaka(this, this.values, bungkus.getInt("layout") );

		FragmentManager fragmentManager = getSupportFragmentManager();
		if (fragmentManager.findFragmentById(android.R.id.content) == null) {
			FragmentBakaListview fragment = FragmentBakaListview.newInstance(adapter, new FragmentBakaListviewCallback() {
				@Override
				public void callback(AdapterView<?> parent, View view, int position, long id) {
					Temp tpm = values.get(position);

					if( responseCode != null ) {
						Intent returnIntent = new Intent();
						returnIntent.putExtra( responseCode, tpm.getData() );
						setResult( Activity.RESULT_OK, returnIntent );
						finish();
					};
				}
			});
			FragmentTransaction ft = fragmentManager.beginTransaction();
			ft.replace(android.R.id.content, fragment);
			ft.commit();
		}
		((ActionBarActivity)this).getSupportActionBar().setDisplayShowHomeEnabled(true);

		if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
			getSupportActionBar().setTitle( bungkus.getString("title") );
			getSupportActionBar().setSubtitle( bungkus.getString("subtitle") );
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onResume(){
		if (!Dimana.isActive(this)){
			Nanyakeun.taros( this, getSupportFragmentManager(), "showWarningGPS", "Peringatan", getString(R.string.location_ask));
		};
		super.onResume();
	}

	private SearchView searchView;
	@Override
	public boolean onCreateOptionsMenu( Menu menu) {
		if( bungkus.getBoolean( "search", false ) == true ) {

			getMenuInflater().inflate( R.menu.menu_search, menu);

			final MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
			searchView = (SearchView) myActionMenuItem.getActionView();
			searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
				@Override
				public boolean onQueryTextSubmit(String query) {
					if( !searchView.isIconified()) {
						searchView.setIconified(true);
					}
					myActionMenuItem.collapseActionView();
					return false;
				}
				@Override
				public boolean onQueryTextChange(String s) {
					adapter.getFilter().filter( s.toLowerCase() );
					return false;
				}
			});
		}
		return true;
	}

} 