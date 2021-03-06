package co.id.exml.logistikdr.fragment.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import co.id.exml.logistikdr.R;
import co.id.exml.logistikdr.dojo.DojoDeliveryItem;
import co.id.exml.logistikdr.sikuel.SikuelDeliveryItem;
import co.id.exml.logistikdr.utils.CircleButton;
import co.id.exml.logistikdr.utils.Dimana;
import co.id.exml.logistikdr.utils.Nanyakeun;

import com.yayandroid.locationmanager.LocationConfiguration;
import com.yayandroid.locationmanager.LocationManager;
import com.yayandroid.locationmanager.constants.LogType;
import com.yayandroid.locationmanager.constants.ProviderType;

import eu.livotov.labs.android.camview.ScannerLiveView;
import eu.livotov.labs.android.camview.camera.CameraController;
import eu.livotov.labs.android.camview.scanner.decoder.zxing.ZXDecoder;

@SuppressLint("ResourceAsColor")
public class ActivityScannerDelivery extends ActivityBaseLocation {

	private ProgressDialog progressDialog;

	private ScannerLiveView camera;
	@SuppressWarnings("unused")
	private CameraController controller;
	private boolean flashStatus;
	private CircleButton controll;

	private int IDP_Delivery_Detail, ID_Barang, IDP_Delivery;
	private String no_resi;

	private List<String> stackScanner = new ArrayList<String>();

	private String latitude = null, longitude = null;

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		setContentView(R.layout.activity_scanner);

		if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
			getSupportActionBar().setSubtitle( R.string.activity_scanner_sibtitle );
		};

		Bundle bungkus = getIntent().getExtras();
		if( bungkus != null ){
			ID_Barang = bungkus.getInt("ID_Barang");
			IDP_Delivery_Detail = bungkus.getInt("IDP_Delivery_Detail");
			IDP_Delivery = bungkus.getInt("IDP_Delivery");
			no_resi = bungkus.getString("no_resi");
		};

		stackScanner.clear();

		camera = (ScannerLiveView) findViewById(R.id.camview);
		camera.setScannerViewEventListener(new ScannerLiveView.ScannerViewEventListener()
		{
			@Override
			public void onScannerStarted(ScannerLiveView scanner)
			{
				Toast.makeText(ActivityScannerDelivery.this,"Scanner Started",Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onScannerStopped(ScannerLiveView scanner)
			{
				//Toast.makeText(ActivityScanner.this,"Scanner Stopped",Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onScannerError(Throwable err)
			{
				//Toast.makeText(ActivityScanner.this,"Scanner Error: " + err.getMessage(),Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onCodeScanned(String data)
			{
				String dataScan = data.trim();
				if ( stackScanner.contains(dataScan) ) {
					Toast.makeText(ActivityScannerDelivery.this, "QR Sudah Di Pindai", Toast.LENGTH_SHORT).show();
				}else{
					stackScanner.add(dataScan);
					Toast.makeText(ActivityScannerDelivery.this, data, Toast.LENGTH_SHORT).show();
				};
				saveHasilScan();
			}
		});

		/*findViewById(R.id.btnFlash).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				toggleFlash();
			}
		});*/

		this.controll = ( CircleButton ) findViewById(R.id.btnControll);
		this.controll.setVisibility(View.GONE);
		this.controll.setColor(R.color.exml_color_secondry);
		this.controll.setOnTouchListener(new View.OnTouchListener() {
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
					controll.setColor(Color.parseColor("#FF4444"));
					camera.startScanner();
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					controll.setColor(R.color.exml_color_secondry);
					camera.stopScanner();
				}
				return true;
			}
		});

		//get locations
		LocationManager.setLogType(LogType.GENERAL);
		getLocation();
		this.resumeLocationsPos();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.menu_form, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		case R.id.action_save:
			saveHasilScan();
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
		ZXDecoder decoder = new ZXDecoder();
		decoder.setScanAreaPercent(1);
		camera.setDecoder(decoder);
		camera.startScanner();
	}

	@Override
	protected void onPause()
	{
		controll.setColor(R.color.exml_color_secondry);
		camera.stopScanner();
		super.onPause();
		dismissProgress();
	}

	public void toggleFlash()
	{
		flashStatus = !flashStatus;
		camera.getCamera().getController().switchFlashlight(flashStatus);
	}

	private void saveHasilScan(){
		for( String hasilScan : stackScanner ) {
			this.saveHasilScan( hasilScan );
		}
		finish();
	}

	private void saveHasilScan( String hasilScan ){
		hasilScan = hasilScan.trim();
		if( hasilScan.length() > 0 ){
			DojoDeliveryItem dojoItemSave = SikuelDeliveryItem.getByAny( IDP_Delivery_Detail, ID_Barang, hasilScan);
			if( dojoItemSave != null  ){
				if ( dojoItemSave.ID_Status_Detail >= 2 ) {
					Toast.makeText(getApplicationContext(),"QR Sudah Pernah Di Scan!", Toast.LENGTH_LONG).show();
				}else{
					dojoItemSave.ID_Status_Detail = 2;
					dojoItemSave.latitude = this.latitude;
					dojoItemSave.longitude = this.longitude;
					dojoItemSave.kode_barcode = hasilScan;
					dojoItemSave.belum_ada_perasaan_ke_server = 1;

					SikuelDeliveryItem.setData(dojoItemSave);
				};
			}else{
				Intent intents = new Intent( ActivityScannerDelivery.this, ActivityWarning.class );
				intents.putExtras( getBungkusan( hasilScan, "Peringatan!", "QR Code yang ada pindai tidak sesuai.", "" +
						"\n\nQR Code yang anda pindai tidak sesuai\n\n\n"+
						hasilScan + 
						"\n\n\nData hasil pemindaian tidak akan dirubah.\n\n") );
				startActivity( intents );
				finish();
			};
		};
	}

	private Bundle getBungkusan(){
		Bundle bungkus = new Bundle();
		bungkus.putInt("IDP_Delivery_Detail", IDP_Delivery_Detail);
		bungkus.putInt("ID_Barang", ID_Barang);
		bungkus.putInt("IDP_Delivery", IDP_Delivery);
		bungkus.putString("no_resi", no_resi);
		return bungkus;
	}

	private Bundle getBungkusan( String kode_barcode, String title, String subtitle, String pesan ){
		Bundle bungkus = getBungkusan();
		bungkus.putString("kode_barcode", kode_barcode );
		bungkus.putString("title", title );
		bungkus.putString("subtitle", subtitle );
		bungkus.putString("pesan", pesan );
		return bungkus;
	}


	//block code each activity for locations - start
	private void resumeLocationsPos(){
		if (getLocationManager().isWaitingForLocation() && !getLocationManager().isAnyDialogShowing()) {
			displayProgress();
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		dismissProgress();
		this.latitude = String.valueOf(location.getLatitude());
		this.longitude = String.valueOf(location.getLongitude());
	}

	@Override
	public void onLocationFailed() {
		dismissProgress();
		Nanyakeun.taros( this, getSupportFragmentManager(), "showWarningGPS", "Peringatan", "Gagal Melakukan Pengambilan Lokasi");
	}

	@Override
	public LocationConfiguration getLocationConfiguration() {
		return new LocationConfiguration()
		.keepTracking(true)
		.askForGooglePlayServices(true)
		.setMinAccuracy(200.0f)
		.setWaitPeriod(ProviderType.GOOGLE_PLAY_SERVICES, 5 * 1000)
		.setWaitPeriod(ProviderType.GPS, 10 * 1000)
		.setWaitPeriod(ProviderType.NETWORK, 5 * 1000)
		.setGPSMessage("Would you mind to turn GPS on?")
		.setRationalMessage("Gimme the permission!");
	}

	private void displayProgress() {
		runOnUiThread(new Runnable() {
			public void run() {
				if (progressDialog == null) {
					progressDialog = new ProgressDialog(ActivityScannerDelivery.this);
					progressDialog.getWindow().addFlags(Window.FEATURE_NO_TITLE);
					progressDialog.setMessage("Getting location...");
				}

				if (!progressDialog.isShowing()) {
					progressDialog.show();
				}
			}
		});
	}

	private void dismissProgress() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}
	//block code each activity for locations - start

} 