package co.id.exml.logistikdr.fragment.tab;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import co.id.exml.logistikdr.R;
import co.id.exml.logistikdr.biskuit.IBiskuitSync;
import co.id.exml.logistikdr.dojo.DojoPickupOrder;
import co.id.exml.logistikdr.fragment.FragmentBaka;
import co.id.exml.logistikdr.fragment.FragmentPickupDetailList;
import co.id.exml.logistikdr.fragment.activity.ActivityPickupDetailForm;
import co.id.exml.logistikdr.sikuel.SikuelPickup;
import co.id.exml.logistikdr.utils.Dimana;
import co.id.exml.logistikdr.utils.Nanyakeun;
import co.id.exml.logistikdr.utils.NanyakeunCallback;
import co.id.exml.logistikdr.utils.OpenGoogleMaps;
import co.id.exml.logistikdr.utils.SuratCinta;
import co.id.exml.logistikdr.utils.Temp;
import co.id.exml.logistikdr.utils.TempInfterface;
import io.karim.MaterialTabs;

@SuppressWarnings("deprecation")
public class TabFragmentPickupDetail extends ActionBarActivity {

	private static final String TAG = TabFragmentPickupDetail.class.getSimpleName();

	MaterialTabs mMaterialTabs;
	ViewPager mViewPager;

	private int ID_Pickup, ID_Order;
	private String kode_customer;
	private IBiskuitSync syncx = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		setContentView(R.layout.activity_tabs);

		if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
			getSupportActionBar().setSubtitle(R.string.activity_pickup_entry_detail_list_subtitle);
		};

		Bundle bungkus = getIntent().getExtras();
		if( bungkus != null ){
			ID_Pickup = bungkus.getInt("ID_Pickup");
			ID_Order = bungkus.getInt("ID_Order");
			kode_customer = bungkus.getString("kode_customer");
		};

		mMaterialTabs = (MaterialTabs) findViewById(R.id.material_tabs);
		mViewPager = (ViewPager) findViewById(R.id.view_pager);

		SectionsPagerAdapter adapter = new SectionsPagerAdapter( getSupportFragmentManager() );
		if( adapter != null )
			mViewPager.setAdapter(adapter);

		mMaterialTabs.setViewPager(mViewPager);

		mMaterialTabs.setOnTabSelectedListener(new MaterialTabs.OnTabSelectedListener() {
			@Override
			public void onTabSelected(int position) {
				Log.i(TAG, "onTabSelected called with position " + position);
			}
		});

		mMaterialTabs.setOnTabReselectedListener(new MaterialTabs.OnTabReselectedListener() {
			@Override
			public void onTabReselected(int position) {
				Log.i(TAG, "onTabReselected called with position " + position);
			}
		});

		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
		mViewPager.setPageMargin(pageMargin);
		this.syncx =  new IBiskuitSync( this );
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		private final ArrayList<String> ftitles;

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
			ftitles = new ArrayList<>();
			ftitles.add("Info");
			ftitles.add( getString(R.string.activity_pickup_entry_detail_list_fr ) );
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return ftitles.get(position);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment =null;
			switch (position) {
			case 0:
				fragment = FragmentBaka.newInstance( getDetailView() );
				break;
			case 1:
				fragment = Fragment.instantiate( getApplicationContext(), FragmentPickupDetailList.class.getName(), getBungkusan() );
				break;
			}
			return fragment;
		}

		@Override
		public int getCount() {
			return 2;
		}
	}

	private View getDetailView(){
		final DojoPickupOrder dojoOrder = SikuelPickup.getBy(ID_Order);

		Temp buttonMap = new Temp("","", true);
		if ( !dojoOrder.latitude.equals("null") && !dojoOrder.longitude.equals("null") ) {
			if ( String.valueOf(dojoOrder.latitude) != null && String.valueOf(dojoOrder.longitude) != null ) {
				if ( dojoOrder.latitude.length() > 0 && dojoOrder.longitude.length() > 0 ) {
					buttonMap = new Temp("Maps", "Maps", new TempInfterface() {
						@Override
						public void onHandler() {
							OpenGoogleMaps.openOnGoogleMaps(TabFragmentPickupDetail.this, Float.parseFloat(dojoOrder.latitude), Float.parseFloat(dojoOrder.longitude));
						}
					}, true );
				};
			};
		};

		Temp[] temp = {
				new Temp("Customer", String.valueOf( dojoOrder.nama_customer ) ),
				new Temp("Customer Lokasi", String.valueOf( dojoOrder.nama_customer_location ) ),
				new Temp("Lokasi", String.valueOf( dojoOrder.nama_lokasi_origin ) ),
				new Temp("Alamat", String.valueOf( dojoOrder.alamat_pickup ) ),
				new Temp("Area / Kota", String.valueOf( dojoOrder.nama_kota_origin ) ),
				new Temp("Keterangan", String.valueOf( dojoOrder.keterangan ) ),
				new Temp("", "", new Temp[]{
						new Temp("Sync", "Sync", new TempInfterface() {
							@Override
							public void onHandler() {
								askFoSync();
							}
						}, true ),
						new Temp("","", true),
						buttonMap
				}, true, 3)
		};
		return SuratCinta.manaSuratKu( this, temp );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		case R.id.action_add :
			Intent intents = new Intent( TabFragmentPickupDetail.this, ActivityPickupDetailForm.class );
			intents.putExtras( getBungkusan() );
			startActivity( intents );
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private Bundle getBungkusan(){
		Bundle bungkus = new Bundle();
		bungkus.putInt("ID_Pickup", ID_Pickup);
		bungkus.putInt("ID_Order", ID_Order);
		bungkus.putString("kode_customer", kode_customer);
		return bungkus;
	}

	@Override
	protected void onResume(){
		if (!Dimana.isActive(this)){
			Nanyakeun.taros( this, getSupportFragmentManager(), "showWarningGPS", "Peringatan", getString(R.string.location_ask));
		};
		super.onResume();
	}

	@Override
	protected void onPause(){
		super.onPause();
	}

	private void askFoSync(){
		Nanyakeun.taros( this, getSupportFragmentManager(), "yakinDiSync", "Sync!", "Apakah anda yakin untuk melakukan Singkronisasi?", new NanyakeunCallback() {
			@Override
			public void callback(DialogInterface dialog, int which) {
				syncx.doSyncData();
			}
		}, new NanyakeunCallback() {
			@Override
			public void callback(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
	}
}
