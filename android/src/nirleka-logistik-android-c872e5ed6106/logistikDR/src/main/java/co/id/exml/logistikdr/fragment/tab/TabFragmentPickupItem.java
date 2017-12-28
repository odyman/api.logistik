package co.id.exml.logistikdr.fragment.tab;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import co.id.exml.logistikdr.R;
import co.id.exml.logistikdr.dojo.DojoPickupDetail;
import co.id.exml.logistikdr.fragment.FragmentBaka;
import co.id.exml.logistikdr.fragment.FragmentPickupItemList;
import co.id.exml.logistikdr.fragment.activity.ActivityScanner;
import co.id.exml.logistikdr.sikuel.SikuelPickupDetail;
import co.id.exml.logistikdr.sikuel.SikuelPickupItem;
import co.id.exml.logistikdr.utils.Dimana;
import co.id.exml.logistikdr.utils.Nanyakeun;
import co.id.exml.logistikdr.utils.Sempak;
import co.id.exml.logistikdr.utils.SuratCinta;
import co.id.exml.logistikdr.utils.Temp;
import io.karim.MaterialTabs;

@SuppressWarnings("deprecation")
public class TabFragmentPickupItem extends ActionBarActivity {

	private static final String TAG = TabFragmentPickupItem.class.getSimpleName();

	MaterialTabs mMaterialTabs;
	ViewPager mViewPager;

	private int ID_Pickup, ID_Order, ID_Barang;
	private String no_resi;

	private EditText edit1, edit2, edit3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		setContentView(R.layout.activity_tabs);

		if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
			getSupportActionBar().setSubtitle( R.string.activity_pickup_entry_item_list_subtitle );
		};

		Bundle bungkus = getIntent().getExtras();
		if( bungkus != null ){
			ID_Barang = bungkus.getInt("ID_Barang");
			ID_Pickup = bungkus.getInt("ID_Pickup");
			ID_Order = bungkus.getInt("ID_Order");
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
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		private final ArrayList<String> ftitles;

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
			ftitles = new ArrayList<>();
			ftitles.add("Info");
			ftitles.add( getString(R.string.activity_pickup_entry_item_list_fr ) );
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
				fragment = Fragment.instantiate( getApplicationContext(), FragmentPickupItemList.class.getName(), getBungkusan() );
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
		//DojoPickupDetail dojoDetail = SikuelPickupDetail.getBy( ID_Barang );
		DojoPickupDetail dojoDetail = SikuelPickupDetail.getBy( ID_Pickup, ID_Order, no_resi );

		int kuantitasRealisasi = SikuelPickupItem.getBy(ID_Pickup, no_resi).size();

		Temp[] temp = {
				new Temp("No. RESI", String.valueOf( dojoDetail.no_resi ) ),
				new Temp("Jenis Pengiriman", String.valueOf( dojoDetail.jenis_kirim ) ),
				new Temp("Satuan", String.valueOf( dojoDetail.nama_satuan ) ),
				new Temp("Kuantitas", String.valueOf( kuantitasRealisasi ) + " / " + String.valueOf( dojoDetail.kuantitas ) ),
				new Temp("Nama Penerima", String.valueOf( dojoDetail.nama_penerima ) ),
				new Temp("Berat Kg", String.valueOf( dojoDetail.getBeratKgStr() ) ),
				new Temp("Volume", String.valueOf( dojoDetail.getVolumeStr() ) )
		};
		return SuratCinta.manaSuratKu( this, temp );
	}

	private Bundle getBungkusan(){
		Bundle bungkus = new Bundle();
		bungkus.putInt("ID_Barang", ID_Barang);
		bungkus.putInt("ID_Pickup", ID_Pickup);
		bungkus.putInt("ID_Order", ID_Order);
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
			checkingLoc();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void checkingLoc(){
		if (!Dimana.isActive(this)){
			Nanyakeun.taros( this, getSupportFragmentManager(), "showWarningGPS", "Peringatan", getString(R.string.location_ask));
		}else{
			showConfirmInput();
		}
	}

	@SuppressLint("InflateParams")
	private void showConfirmInput(){
		LayoutInflater li = LayoutInflater.from( this );
		View promptsView = li.inflate(R.layout.confirm_dialog_input_pickup, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		alertDialogBuilder.setView(promptsView);

		edit1 = (EditText) promptsView.findViewById(R.id.confirm_diload_input_pickup_edit1);
		edit2 = (EditText) promptsView.findViewById(R.id.confirm_diload_input_pickup_edit2);
		edit3 = (EditText) promptsView.findViewById(R.id.confirm_diload_input_pickup_edit3);

		String tempBerat = Sempak.getInstance( getApplicationContext() ).get("iBerat");
		if( tempBerat != null ) {
			float iBerat_tmp = Float.valueOf( tempBerat );
			if( iBerat_tmp > 0 ){
				edit1.setText( String.valueOf( iBerat_tmp ) );
			}
		}
		String tempVol = Sempak.getInstance( getApplicationContext() ).get("iVolume");
		if( tempVol != null ) {
			float iVolume_tmp = Float.valueOf(  Sempak.getInstance( getApplicationContext() ).get("iVolume") );
			if( iVolume_tmp > 0 ){
				edit2.setText( String.valueOf( iVolume_tmp ) );
			}
		}
		String iKeterangan_tmp = Sempak.getInstance( getApplicationContext() ).get("iKeterangan");

		if( iKeterangan_tmp != null ){
			edit3.setText( iKeterangan_tmp );
		};

		alertDialogBuilder
		.setCancelable(false)
		.setIcon(R.drawable.ic_launcher)
		.setPositiveButton("Save", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				boolean statusOk 	= true;
				String iBerat		= edit1.getText().toString().trim();
				String iVolume		= edit2.getText().toString().trim();
				String iKeterangan 	= edit3.getText().toString().trim();

				if( statusOk == true && iKeterangan.length() > 0 && Float.valueOf(iBerat) >= 0  && Float.valueOf(iVolume) >= 0 ){
					Sempak.getInstance( getApplicationContext() )
					.set("iBerat", String.valueOf(iBerat))
					.set("iVolume", String.valueOf(iVolume))
					.set("iKeterangan", String.valueOf(iKeterangan));
					showQRScan();
				}else{
					Toast.makeText(getApplicationContext(),"Silahkan isi dengan benar!", Toast.LENGTH_LONG).show();;
				}

			}
		})
		.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			}
		});

		AlertDialog alertDialog = alertDialogBuilder.create();

		alertDialog.show();
	}

	private void showQRScan(){
		try {
			//Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			//intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			//startActivityForResult(intent, 0);

			Intent intent = new Intent(TabFragmentPickupItem.this, ActivityScanner.class);
			intent.putExtras(getBungkusan());
			startActivity( intent );
		} catch (ActivityNotFoundException anfe) {
			anfe.printStackTrace();
		}
	}

	/*@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String hasilScan = intent.getStringExtra("SCAN_RESULT").trim();
				if( hasilScan.length() > 0 ){
					DojoPickupBarcodeAvailable dojoBarcode = SikuelBarcodeAvailable.getBy( hasilScan );
					DojoPickupItem dojoCheckItemBarcode = SikuelPickupItem.getBy( hasilScan );
					if( dojoBarcode != null ) {
						if( dojoCheckItemBarcode != null  ){
							Toast.makeText(getApplicationContext(),"QR Sudah Pernah Digunakan!", Toast.LENGTH_LONG).show();
						}else{
							DojoPickupItem dojoItemSave = new DojoPickupItem();

							dojoItemSave.ID_Barang = ID_Barang;
							dojoItemSave.ID_Pickup = ID_Pickup;
							dojoItemSave.ID_Order = ID_Order;
							dojoItemSave.no_resi = no_resi;
							dojoItemSave.kode_barcode = hasilScan;
							dojoItemSave.berat_kg = Sempak.getInstance( getApplicationContext() ).get("iBerat");
							dojoItemSave.volume = Sempak.getInstance( getApplicationContext() ).get("iVolume");
							dojoItemSave.keterangan = Sempak.getInstance( getApplicationContext() ).get("iKeterangan");
							dojoItemSave.belum_ada_perasaan_ke_server = 1;

							SikuelPickupItem.setData(dojoItemSave);
						};
					}else{
						Toast.makeText(getApplicationContext(),"QR Tidak Ditemukan!", Toast.LENGTH_LONG).show();
					};
				};
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(getApplicationContext(),"Batal Scan QR", Toast.LENGTH_LONG).show();
			}
		}
	}*/
}
