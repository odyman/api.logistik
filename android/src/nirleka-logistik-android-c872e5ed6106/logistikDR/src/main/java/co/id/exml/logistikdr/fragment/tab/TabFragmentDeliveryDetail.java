package co.id.exml.logistikdr.fragment.tab;

import io.karim.MaterialTabs;

import java.util.ArrayList;

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
import co.id.exml.logistikdr.R;
import co.id.exml.logistikdr.dojo.DojoDeliveryOrder;
import co.id.exml.logistikdr.fragment.FragmentBaka;
import co.id.exml.logistikdr.fragment.FragmentDeliveryDetailList;
import co.id.exml.logistikdr.sikuel.SikuelDelivery;
import co.id.exml.logistikdr.utils.Dimana;
import co.id.exml.logistikdr.utils.Nanyakeun;
import co.id.exml.logistikdr.utils.SuratCinta;
import co.id.exml.logistikdr.utils.Temp;

@SuppressWarnings("deprecation")
public class TabFragmentDeliveryDetail extends ActionBarActivity {

	private static final String TAG = TabFragmentDeliveryDetail.class.getSimpleName();

	MaterialTabs mMaterialTabs;
	ViewPager mViewPager;

	private int IDP_Delivery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		setContentView(R.layout.activity_tabs);

		if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
			getSupportActionBar().setSubtitle(R.string.activity_delivery_entry_detail_list_subtitle);
		};

		Bundle bungkus = getIntent().getExtras();
		if( bungkus != null ){
			IDP_Delivery = bungkus.getInt("IDP_Delivery");
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
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		private final ArrayList<String> ftitles;

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
			ftitles = new ArrayList<>();
			ftitles.add("Info");
			ftitles.add( getString(R.string.activity_delivery_entry_detail_list_fr ) );
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
				fragment = Fragment.instantiate( getApplicationContext(), FragmentDeliveryDetailList.class.getName(), getBungkusan() );
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
		DojoDeliveryOrder dojoOrder = SikuelDelivery.getBy(IDP_Delivery);

		Temp[] temp = {
				//new Temp("Status", String.valueOf( dojoOrder.status ) ) ,
				new Temp("Tanggal Delivery", String.valueOf( dojoOrder.tgl_delivery ) ),
				//new Temp("Kode Kendaraan", String.valueOf( dojoOrder.kode_kendaraan ) ),
				new Temp("No. Polisi", String.valueOf( dojoOrder.no_pol ) ),
				new Temp("Unit Kerja Pengiriman", String.valueOf( dojoOrder.nama_unker_delivery ) ),
				new Temp("Kota Pengiriman", String.valueOf( dojoOrder.nama_kota_delivery ) ),
				new Temp("Lokasi Pengiriman", String.valueOf( dojoOrder.nama_lokasi_delivery ) ) ,
				new Temp("Keterangan", String.valueOf( dojoOrder.keterangan ) ) ,
				//new Temp("Nama Bulan", String.valueOf( dojoOrder.nama_bulan ) ) 
		};
		return SuratCinta.manaSuratKu( this, temp );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
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

	private Bundle getBungkusan(){
		Bundle bungkus = new Bundle();
		bungkus.putString("title", "Rubah Status");
		bungkus.putInt("IDP_Delivery", IDP_Delivery);
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
}
