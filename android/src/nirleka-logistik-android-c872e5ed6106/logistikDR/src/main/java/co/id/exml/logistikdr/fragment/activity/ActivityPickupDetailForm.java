package co.id.exml.logistikdr.fragment.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;
import java.util.List;

import co.id.exml.logistikdr.R;
import co.id.exml.logistikdr.dojo.DojoPickupCustomer;
import co.id.exml.logistikdr.dojo.DojoPickupDetail;
import co.id.exml.logistikdr.dojo.DojoPickupJenisKirim;
import co.id.exml.logistikdr.sikuel.SikuelPickupCustomer;
import co.id.exml.logistikdr.sikuel.SikuelPickupDetail;
import co.id.exml.logistikdr.sikuel.SikuelPickupJenisKirim;
import co.id.exml.logistikdr.utils.Dimana;
import co.id.exml.logistikdr.utils.Nanyakeun;
import co.id.exml.logistikdr.utils.NanyakeunCallback;
import co.id.exml.logistikdr.utils.Nuturkeun;
import co.id.exml.logistikdr.utils.Temp;

@SuppressWarnings("deprecation")
public class ActivityPickupDetailForm extends ActionBarActivity {

	public static final int resultCodeJenisPengiriman = 9;
	public static final int resultCodeCustomer = 6;

	EditText activity_pickup_entry_detail_form_nomorresi,
			 activity_pickup_entry_detail_form_jenispengiriman,
			 activity_pickup_entry_detail_form_namabarang,
			 activity_pickup_entry_detail_form_satuan,
			 activity_pickup_entry_detail_form_qty,
			 activity_pickup_entry_detail_form_berat,
			 activity_pickup_entry_detail_form_volume;

	Nuturkeun activity_pickup_entry_detail_form_namapenerima;

	Button activity_pickup_entry_detail_form_jenispengiriman_btn,
	activity_pickup_entry_detail_form_namapenerima_btn;

	private int ID_Pickup, ID_Order, ID_Customer_Location = 0;
	private String kode_customer;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		Bundle bungkus = getIntent().getExtras();
		ID_Pickup = bungkus.getInt("ID_Pickup");
		ID_Order = bungkus.getInt("ID_Order");
		kode_customer = bungkus.getString("kode_customer");

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		setContentView(R.layout.activity_pickup_entry_detail_form);

		if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
			getSupportActionBar().setSubtitle(R.string.activity_pickup_entry_detail_form_subtitle);
		}

		activity_pickup_entry_detail_form_nomorresi = (EditText) findViewById(R.id.activity_pickup_entry_detail_form_nomorresi);
		activity_pickup_entry_detail_form_jenispengiriman = (EditText) findViewById(R.id.activity_pickup_entry_detail_form_jenispengiriman);
		activity_pickup_entry_detail_form_namabarang = (EditText) findViewById(R.id.activity_pickup_entry_detail_form_namabarang);
		activity_pickup_entry_detail_form_satuan = (EditText) findViewById(R.id.activity_pickup_entry_detail_form_satuan);
		activity_pickup_entry_detail_form_qty = (EditText) findViewById(R.id.activity_pickup_entry_detail_form_qty);
		activity_pickup_entry_detail_form_berat = (EditText) findViewById(R.id.activity_pickup_entry_detail_form_berat);
		activity_pickup_entry_detail_form_volume = (EditText) findViewById(R.id.activity_pickup_entry_detail_form_volume);

		activity_pickup_entry_detail_form_namapenerima = (Nuturkeun) findViewById(R.id.activity_pickup_entry_detail_form_namapenerima);
		//activity_pickup_entry_detail_form_namapenerima.setEnabled(true);
        //activity_pickup_entry_detail_form_namapenerima.setFocusableInTouchMode(true);
        //activity_pickup_entry_detail_form_namapenerima.setFocusable(true);
        activity_pickup_entry_detail_form_namapenerima.setEnableSizeCache(false);
        activity_pickup_entry_detail_form_namapenerima.setMovementMethod(null);
        activity_pickup_entry_detail_form_namapenerima.setMaxHeight(330);

		activity_pickup_entry_detail_form_jenispengiriman_btn = (Button) findViewById(R.id.activity_pickup_entry_detail_form_jenispengiriman_btn);
		activity_pickup_entry_detail_form_jenispengiriman_btn.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intents = new Intent( ActivityPickupDetailForm.this, ActivityBaka.class );
				List<Temp> tempList = SikuelPickupJenisKirim.getAllAsTemp();
				intents.putExtra("layout", R.layout.activity_pickup_row );
				intents.putExtra("response", "pilihBakaResponseJenisKiriman");
				intents.putExtra("title", "Jenis Pengiriman");
				intents.putExtra("subtitle", "Silahkan pilih jenis pengiriman");
				intents.putExtra("temp", (Serializable) tempList);
				startActivityForResult( intents, ActivityPickupDetailForm.resultCodeJenisPengiriman );
			}
		});
		activity_pickup_entry_detail_form_namapenerima_btn = (Button) findViewById(R.id.activity_pickup_entry_detail_form_namapenerima_btn);
		activity_pickup_entry_detail_form_namapenerima_btn.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intents = new Intent( ActivityPickupDetailForm.this, ActivityBaka.class );
				List<Temp> tempList = SikuelPickupCustomer.getAllAsTemp( kode_customer );
				intents.putExtra("layout", R.layout.activity_pickup_row_bottom );
				intents.putExtra("response", "pilihBakaResponseCustomer");
				intents.putExtra("title", "Penerima");
				intents.putExtra("search", true );
				intents.putExtra("subtitle", "Silahkan pilih penerima");
				intents.putExtra("temp", (Serializable) tempList);
				startActivityForResult( intents, ActivityPickupDetailForm.resultCodeCustomer );
			}
		});
	}

	@Override
	public void onResume(){
		if (!Dimana.isActive(this)){
			Nanyakeun.taros( this, getSupportFragmentManager(), "showWarningGPS", "Peringatan", getString(R.string.location_ask));
		};
		super.onResume();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if ( requestCode == ActivityPickupDetailForm.resultCodeJenisPengiriman ) {
			if( resultCode == Activity.RESULT_OK ){
				String result = data.getStringExtra( "pilihBakaResponseJenisKiriman" );
				if( result != null ){
					int ID_Jns_kirim = Integer.valueOf( result );
					if( ID_Jns_kirim > 0 ) {
						DojoPickupJenisKirim dojoJenisKirim = SikuelPickupJenisKirim.getBy(ID_Jns_kirim);
						if( dojoJenisKirim != null ) {
							activity_pickup_entry_detail_form_jenispengiriman.setText(dojoJenisKirim.jenis_kirim);
						};
					};
				}
			}
			if ( resultCode == Activity.RESULT_CANCELED ) {

			}
		}


		if ( requestCode == ActivityPickupDetailForm.resultCodeCustomer ) {
			if( resultCode == Activity.RESULT_OK ){
				String result = data.getStringExtra( "pilihBakaResponseCustomer" );
				if( result != null ){
					int ID_Customer_LocationChk = Integer.valueOf( result );
					if( ID_Customer_LocationChk > 0 ) {
						DojoPickupCustomer dojoCustomer = SikuelPickupCustomer.getBy(ID_Customer_LocationChk);
						if( dojoCustomer != null ) {
							ID_Customer_Location = dojoCustomer.ID_Customer_Location;
							activity_pickup_entry_detail_form_namapenerima.setText(dojoCustomer.nama_lokasi_cust_penerima);
						};
					};
				}
			}
			if ( resultCode == Activity.RESULT_CANCELED ) {

			}
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
		Nanyakeun.taros( this, getSupportFragmentManager(), "yakinDiSync", "Simpan", "Apakah anda yakin untuk melakukan menyimpan data?", new NanyakeunCallback() {
			@Override
			public void callback(DialogInterface dialog, int which) {
				yesDoSaveForm();
			}
		}, new NanyakeunCallback() {
			@Override
			public void callback(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
	}
	private void yesDoSaveForm(){
		String noresix = activity_pickup_entry_detail_form_nomorresi.getText().toString().trim();

		DojoPickupDetail detail = new DojoPickupDetail();
		detail.no_resi = activity_pickup_entry_detail_form_nomorresi.getText().toString().trim();
		detail.jenis_kirim = activity_pickup_entry_detail_form_jenispengiriman.getText().toString().trim();
		detail.nama_barang = activity_pickup_entry_detail_form_namabarang.getText().toString().trim();
		detail.nama_satuan = activity_pickup_entry_detail_form_satuan.getText().toString().trim();
		detail.kuantitas = activity_pickup_entry_detail_form_qty.getText().toString().trim();
		detail.nama_penerima = activity_pickup_entry_detail_form_namapenerima.getText().toString().trim();
		detail.belum_ada_perasaan_ke_server = 1;
		detail.ID_Pickup = ID_Pickup;
		detail.ID_Order = ID_Order;
		detail.ID_Customer_Location = ID_Customer_Location;
		detail.berat_kg = activity_pickup_entry_detail_form_berat.getText().toString();
		detail.volume = activity_pickup_entry_detail_form_volume.getText().toString();
		detail.tipe_pickup = 1;
		detail.ID_Outgoing = 0;

		if( SikuelPickupDetail.resiIsExist(ID_Pickup, ID_Order, noresix) == false ){
			if( SikuelPickupDetail.isValidInput(detail) ) {
				SikuelPickupDetail.setData(detail);
				finish();
			}else{
				Nanyakeun.taros( this, getSupportFragmentManager(), "formInpuDetailInValid", "Peringatan!", "Form input tidak valid, silahkan isi dengan benar!");
			}
		}else{
			Nanyakeun.taros( this, getSupportFragmentManager(), "resisudahdigunakan", "Peringatan!", "No. Resi sudah digunakan!");
		}
	}

}
