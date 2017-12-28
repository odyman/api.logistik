package co.id.exml.logistikdr.fragment.tab;

import io.karim.MaterialTabs;

import java.util.ArrayList;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.Toast;
import co.id.exml.logistikdr.R;
import co.id.exml.logistikdr.biskuit.IBiskuitSync;
import co.id.exml.logistikdr.dojo.DojoDeliveryDetail;
import co.id.exml.logistikdr.dojo.DojoDeliveryStatus;
import co.id.exml.logistikdr.fragment.FragmentBaka;
import co.id.exml.logistikdr.fragment.FragmentDeliveryItemList;
import co.id.exml.logistikdr.fragment.activity.ActivityCam;
import co.id.exml.logistikdr.fragment.activity.ActivityScannerDelivery;
import co.id.exml.logistikdr.sikuel.SikuelDeliveryDetail;
import co.id.exml.logistikdr.sikuel.SikuelDeliveryItem;
import co.id.exml.logistikdr.sikuel.SikuelDeliveryStatus;
import co.id.exml.logistikdr.utils.BitBase64;
import co.id.exml.logistikdr.utils.Dimana;
import co.id.exml.logistikdr.utils.Nanyakeun;
import co.id.exml.logistikdr.utils.NanyakeunCallback;
import co.id.exml.logistikdr.utils.OpenGoogleMaps;
import co.id.exml.logistikdr.utils.SuratCinta;
import co.id.exml.logistikdr.utils.Temp;
import co.id.exml.logistikdr.utils.TempInfterface;

@SuppressWarnings("deprecation")
public class TabFragmentDeliveryItem extends ActionBarActivity {

	private static final String TAG = TabFragmentDeliveryItem.class.getSimpleName();

	MaterialTabs mMaterialTabs;
	ViewPager mViewPager;

	private int IDP_Delivery, ID_Barang, IDP_Delivery_Detail;
	private String no_resi;
	private IBiskuitSync syncx = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		setContentView(R.layout.activity_tabs);

		if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
			getSupportActionBar().setSubtitle( R.string.activity_delivery_entry_item_list_subtitle );
		};

		Bundle bungkus = getIntent().getExtras();
		if( bungkus != null ){
			IDP_Delivery_Detail = bungkus.getInt("IDP_Delivery_Detail");
			ID_Barang = bungkus.getInt("ID_Barang");
			IDP_Delivery = bungkus.getInt("IDP_Delivery");
			no_resi = bungkus.getString("no_resi");
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
			ftitles.add( getString(R.string.activity_delivery_entry_item_list_fr ) );
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
				fragment = Fragment.instantiate( getApplicationContext(), FragmentDeliveryItemList.class.getName(), getBungkusan() );
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
		final DojoDeliveryDetail dojoDetail = SikuelDeliveryDetail.getBy( IDP_Delivery_Detail );
		if( dojoDetail != null ) {

			Temp buttonMap = new Temp("","", true);
			if ( !dojoDetail.latitude.equals("null") && !dojoDetail.longitude.equals("null") ) {
				if ( String.valueOf(dojoDetail.latitude) != null && String.valueOf(dojoDetail.longitude) != null ) {
					if ( dojoDetail.latitude.length() > 0 && dojoDetail.longitude.length() > 0 ) {
						buttonMap = new Temp("Maps", "Maps", new TempInfterface() {
							@Override
							public void onHandler() {
								OpenGoogleMaps.openOnGoogleMaps(TabFragmentDeliveryItem.this, Float.parseFloat(dojoDetail.latitude), Float.parseFloat(dojoDetail.longitude));
							}
						}, true );
					};
				};
			}

			Temp wasDeliveredStatus = null;
			Temp wasDeliveredImages = null;
			Temp wasDeliveredRemark = null;
			Temp wasDeliveredTerima = null;
			Temp wasDeliveredSparot = null;
			Temp wasDeliveredYomans = null;

			if ( dojoDetail.ID_Status_Delivery <= 0 ) {
				wasDeliveredYomans = new Temp("tombol stack key", "tombol stack val", new Temp[]{
						new Temp( "Tombol Status 1", "Rubah Status", new TempInfterface() {
							@Override
							public void onHandler() {
								Intent intents = new Intent( TabFragmentDeliveryItem.this, ActivityCam.class );
								intents.putExtras( getBungkusan() );
								startActivity( intents );
							}
						}, true),
						new Temp("sparot", "", true),
						new Temp("sparot", "", true),
						new Temp( "Tombol Status 2", "Pindai QRCode", new TempInfterface() {
							@Override
							public void onHandler() {
								Intent intents = new Intent( TabFragmentDeliveryItem.this, ActivityScannerDelivery.class );
								intents.putExtras( getBungkusan() );
								startActivity( intents );
							}
						}, true)
				}, true, 4 );
			}else{
				DojoDeliveryStatus statusDelivery = SikuelDeliveryStatus.getBy(dojoDetail.ID_Status_Delivery);
				String statusLabel = "Unknow";
				if( statusDelivery != null ) {
					statusLabel = statusDelivery.status_delivery;
				}
				wasDeliveredStatus = new Temp("Delivery Status", String.valueOf( statusLabel ) );

				if ( dojoDetail.delivery_remark != null ) { 
					if ( !dojoDetail.delivery_remark.equals("null") ) {
						if ( String.valueOf(dojoDetail.delivery_remark) != null ) {
							if ( dojoDetail.delivery_remark.length() > 0 ) {
								wasDeliveredRemark = new Temp("Delivery Remarks", String.valueOf( dojoDetail.delivery_remark ) ); 
							}
						}
					}
				}

				if ( dojoDetail.image != null ) {
					if ( !dojoDetail.image.equals("null") ) {
						if ( String.valueOf(dojoDetail.image) != null ) {
							if ( dojoDetail.image.length() > 0 ) {
								String base64enc = dojoDetail.image;
								Bitmap imgs = BitBase64.decodeBase64Small(base64enc);
								wasDeliveredImages = new Temp("Delivery Images", "-", imgs, true ); 
							}
						}
					}
				}

				if ( dojoDetail.tgl_terima != null ) {
					if ( !dojoDetail.tgl_terima.equals("null") ) {
						if ( String.valueOf(dojoDetail.tgl_terima) != null ) {
							if ( dojoDetail.tgl_terima.length() > 0 ) {
								wasDeliveredTerima = new Temp("Tanggal Terima", String.valueOf( dojoDetail.tgl_terima ) ); 
							}
						}
					}
				}

				wasDeliveredSparot = new Temp("", "", true );
			}

			Temp[] temp = {
					wasDeliveredYomans,
					wasDeliveredSparot,
					wasDeliveredImages,
					wasDeliveredStatus,
					wasDeliveredRemark,
					wasDeliveredTerima,
					wasDeliveredSparot,
					new Temp("No. AWB", String.valueOf( dojoDetail.kode_AWB ) ),
					new Temp("No. RESI", String.valueOf( dojoDetail.no_resi ) ),
					new Temp("Layanan", String.valueOf( dojoDetail.nama_layanan ) ),
					new Temp("Nama Penerima", String.valueOf( dojoDetail.nama_penerima ) ),
					new Temp("Lokasi Penerima", String.valueOf( dojoDetail.nama_lokasi_destination ) ),
					new Temp("Alamat", String.valueOf( dojoDetail.alamat_destination ) ),
					new Temp("Kota", String.valueOf( dojoDetail.nama_kota_destination ) ),
					new Temp("No. Telp", String.valueOf( dojoDetail.telp_destination ) ),
					new Temp("Kuantitas", String.valueOf( SikuelDeliveryItem.getAllScanned(ID_Barang) ) + " / " + String.valueOf( dojoDetail.kuantitas ) ),
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

		return null;
	}

	private Bundle getBungkusan(){
		Bundle bungkus = new Bundle();
		bungkus.putInt("IDP_Delivery_Detail", IDP_Delivery_Detail);
		bungkus.putInt("ID_Barang", ID_Barang);
		bungkus.putInt("IDP_Delivery", IDP_Delivery);
		bungkus.putString("no_resi", no_resi);
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


	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.menu_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		case R.id.action_add :
			checkingLoc();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void checkingLoc(){
		if (!Dimana.isActive(this)){
			Nanyakeun.taros( this, getSupportFragmentManager(), "showWarningGPS", "Peringatan", getString(R.string.location_ask));
		}else{
			//do something...
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(getApplicationContext(),"Batal Scan QR", Toast.LENGTH_LONG).show();
			}
		}
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
