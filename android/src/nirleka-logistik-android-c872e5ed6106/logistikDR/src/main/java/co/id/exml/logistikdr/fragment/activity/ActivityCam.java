package co.id.exml.logistikdr.fragment.activity;

import java.io.Serializable;
import java.util.List;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import co.id.exml.logistikdr.R;
import co.id.exml.logistikdr.dojo.DojoDeliveryDetail;
import co.id.exml.logistikdr.dojo.DojoDeliveryStatus;
import co.id.exml.logistikdr.dojo.DojoUserLogon;
import co.id.exml.logistikdr.sikuel.SikuelDeliveryDetail;
import co.id.exml.logistikdr.sikuel.SikuelDeliveryItem;
import co.id.exml.logistikdr.sikuel.SikuelDeliveryStatus;
import co.id.exml.logistikdr.utils.Ayeuna;
import co.id.exml.logistikdr.utils.BitBase64;
import co.id.exml.logistikdr.utils.Dimana;
import co.id.exml.logistikdr.utils.Nanyakeun;
import co.id.exml.logistikdr.utils.Temp;

import com.desmond.squarecamera.CameraActivity;
import com.desmond.squarecamera.ImageUtility;
import com.yayandroid.locationmanager.LocationConfiguration;
import com.yayandroid.locationmanager.LocationManager;
import com.yayandroid.locationmanager.constants.LogType;
import com.yayandroid.locationmanager.constants.ProviderType;

public class ActivityCam extends ActivityBaseLocation {

	private ProgressDialog progressDialog;

	public static final int resultCodeStatusDelivery = 9;

	private static final int REQUEST_CAMERA = 0;
	private static final int REQUEST_CAMERA_PERMISSION = 1;
	private Point mSize;

	private int ID_Barang;
	private int IDP_Delivery, IDP_Delivery_Detail;
	private int ID_Status_Delivery = 0;
	private ProgressDialog progress = null;
	private String base64enc = null;
	@SuppressWarnings("unused")
	private String no_resi = null;

	private ImageView camPreview;
	private EditText camDescription;
	private EditText statusDeliveryText;
	private Button camSave;
	private Button buttonStatus;
	private Bundle bungkus;

	private String latitude = null, longitude = null;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if( !DojoUserLogon.hasLogon() ){
			finish();
		};

		bungkus = getIntent().getExtras();
		if( bungkus != null ){
			ID_Barang = bungkus.getInt("ID_Barang");
			IDP_Delivery = bungkus.getInt("IDP_Delivery");
			IDP_Delivery_Detail = bungkus.getInt("IDP_Delivery_Detail");
		}else{
			finish();
		};

		setContentView(R.layout.activity_cam);
		if( bungkus.getString("title") != null ) {
			getSupportActionBar().setTitle( bungkus.getString("title") );
		};

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		camPreview = (ImageView) findViewById(R.id.cpm_activity_cam_preview);
		camDescription = (EditText) findViewById(R.id.cpm_activity_cam_keterangan);
		statusDeliveryText = (EditText) findViewById(R.id.cpm_activity_cam_status_block_status);
		camSave = (Button) findViewById(R.id.cpm_activity_cam_button_save);
		buttonStatus = (Button) findViewById(R.id.cpm_activity_cam_status_btn);

		buttonStatus.setClickable(true);
		buttonStatus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intents = new Intent( ActivityCam.this, ActivityBaka.class );
				List<Temp> tempList = getListAvailable();

				if( tempList != null ) {
					intents.putExtra("layout", R.layout.activity_pickup_row );
					intents.putExtra("response", "pilihBakaResponseDeliveryStatus");
					intents.putExtra("title", "Delivery Status");
					intents.putExtra("subtitle", "Silahkan pilih delivery status");
					intents.putExtra("temp", (Serializable) tempList);
					startActivityForResult( intents, ActivityCam.resultCodeStatusDelivery );
				};
			}
		});

		camPreview.setClickable(true);
		camPreview.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				requestForCameraPermission();
			}
		});

		camSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String desk = camDescription.getText().toString().trim();
				if ( desk.length() > 0 )
					insetIntoTable( desk );
			}
		});

		camDescription.setText("-");

		if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
			getSupportActionBar().setSubtitle(R.string.activity_cam_subtitle);
		}

		Display display = getWindowManager().getDefaultDisplay();
		mSize = new Point();
		display.getSize(mSize);

		progress = new ProgressDialog(this);
		progress.setTitle("Loading");
		progress.setMessage("Menyimpan data...");
		progress.setCancelable(false);

		//get locations
		LocationManager.setLogType(LogType.GENERAL);
		getLocation();
		this.resumeLocationsPos();
	}
	
	private List<Temp> getListAvailable(){
		List<Temp> tempList = null;
		int[] filterDelivery = {1};

		int hasScanned = SikuelDeliveryItem.getAllScanned(ID_Barang);
		int mustBeScaned = SikuelDeliveryItem.getAllMustBeScanned(ID_Barang);
		System.out.println("hasScanned : " + hasScanned + " - mustBeScaned " + mustBeScaned );
		if ( hasScanned >= mustBeScaned ) {
			tempList = SikuelDeliveryStatus.getAllAsTemp();
		}else{
			tempList = SikuelDeliveryStatus.getAllAsTempNotAny( filterDelivery );

			System.out.println("All : " + SikuelDeliveryStatus.getAllAsTemp().size() + " - Any " + SikuelDeliveryStatus.getAllAsTempNotAny( filterDelivery ).size() );
		}
		
		return tempList;
	}

	@Override
	public void onResume(){
		if (!Dimana.isActive(this)){
			Nanyakeun.taros( this, getSupportFragmentManager(), "showWarningGPS", "Peringatan", getString(R.string.location_ask));
		};
		super.onResume();
		this.resumeLocationsPos();
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
					progressDialog = new ProgressDialog(ActivityCam.this);
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

	@Override
	protected void onPause() {
		super.onPause();
		dismissProgress();
	}
	//block code each activity for locations - start

	//block cam - start
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) return;
		
		if ( requestCode == ActivityCam.resultCodeStatusDelivery ) {
			if( resultCode == Activity.RESULT_OK ){
				String result = data.getStringExtra( "pilihBakaResponseDeliveryStatus" );
				if( result != null ){
					ID_Status_Delivery = Integer.valueOf( result );
					if( ID_Status_Delivery > 0 ) {
						DojoDeliveryStatus dojoStatusDelivery = SikuelDeliveryStatus.getBy(ID_Status_Delivery);
						if( dojoStatusDelivery != null ) {
							statusDeliveryText.setText(dojoStatusDelivery.status_delivery);
						};
					};
				}
			}
			if ( resultCode == Activity.RESULT_CANCELED ) {
				
			}
		}

		if (requestCode == REQUEST_CAMERA) {
			try{
				Uri photoUri = data.getData();
				Bitmap bitmap = ImageUtility.decodeSampledBitmapFromPath(photoUri.getPath(), mSize.x, mSize.x);
				base64enc = BitBase64.encodeTobase64(bitmap);
				camPreview.setImageBitmap(bitmap);

			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void requestForCameraPermission() {
		final String permission = Manifest.permission.CAMERA;
		if (ContextCompat.checkSelfPermission(ActivityCam.this, permission) != PackageManager.PERMISSION_GRANTED) {
			if (ActivityCompat.shouldShowRequestPermissionRationale(ActivityCam.this, permission)) {
				showPermissionRationaleDialog("Test", permission);
			} else {
				requestForPermission(permission);
			}
		} else {
			launch();
		}
	}

	private void showPermissionRationaleDialog(final String message, final String permission) {
		new AlertDialog.Builder(ActivityCam.this)
		.setMessage(message)
		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ActivityCam.this.requestForPermission(permission);
			}
		})
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		})
		.create()
		.show();
	}

	private void requestForPermission(final String permission) {
		ActivityCompat.requestPermissions(ActivityCam.this, new String[]{permission}, REQUEST_CAMERA_PERMISSION);
	}

	private void launch() {
		Intent startCustomCameraIntent = new Intent(this, CameraActivity.class);
		startActivityForResult(startCustomCameraIntent, REQUEST_CAMERA);
	}

	@SuppressLint("NewApi")
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode) {
		case REQUEST_CAMERA_PERMISSION:
			final int numOfRequest = grantResults.length;
			final boolean isGranted = numOfRequest == 1 && PackageManager.PERMISSION_GRANTED == grantResults[numOfRequest - 1];
			if (isGranted) {
				launch();
			}
			break;

		default:
			super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		}
	}
	//block cam - end

	private void insetIntoTable( final String deskripsiFoto ){
		if( IDP_Delivery > 0 && ID_Status_Delivery > 0 && IDP_Delivery_Detail > 0 && ID_Barang > 0 ) {
			try {
				String delivery_remark =  deskripsiFoto;
				String tgl_terima = Ayeuna.getAyeuna();
				String path_file = base64enc;
				
				DojoDeliveryDetail dojoDetail = SikuelDeliveryDetail.getBy(IDP_Delivery, IDP_Delivery_Detail);
				if ( dojoDetail != null ) {
					dojoDetail.ID_Status_Delivery = ID_Status_Delivery;
					dojoDetail.image = path_file;
					dojoDetail.tgl_terima = tgl_terima;
					dojoDetail.delivery_remark = delivery_remark;
					dojoDetail.latitude_real = this.latitude;
					dojoDetail.longitude_real = this.longitude;
					dojoDetail.belum_ada_perasaan_ke_server = 1;
					dojoDetail.save();
				};
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				finish();
			}

		}else{
			finish();
		}
	}
}