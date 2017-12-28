package co.id.exml.logistikdr.fragment.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import co.id.exml.logistikdr.utils.AsyncJob;

import com.google.android.gms.location.LocationListener;
import com.yayandroid.locationmanager.LocationConfiguration;
import com.yayandroid.locationmanager.LocationManager;
import com.yayandroid.locationmanager.LocationReceiver;

@SuppressWarnings("deprecation")
public abstract class ActivityBaseLocation extends ActionBarActivity implements LocationListener {

	private LocationManager locationManager;

	public abstract LocationConfiguration getLocationConfiguration();

	public abstract void onLocationFailed();

	public abstract void onLocationChanged(Location location);

	public LocationManager getLocationManager() {
		return locationManager;
	}

	public void getLocation() {
		AsyncJob.doInBackground(new AsyncJob.OnBackgroundJob() {
			@Override
			public void doOnBackground() {
				AsyncJob.doOnMainThread(new AsyncJob.OnMainThreadJob() {
					@Override
					public void doInUIThread() {
						if (locationManager != null) {
							locationManager.get();
						}
					}
				});
			}
		});
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	public void onProviderEnabled(String provider) {
	}

	public void onProviderDisabled(String provider) {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		locationManager = new LocationManager(getLocationConfiguration()).on(this).notify(locationReceiver);
	}

	@Override
	protected void onDestroy() {
		locationManager.onDestroy();
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
		locationManager.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		locationManager.onResume();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		locationManager.onActivityResult(requestCode, requestCode, data);
	}

	@TargetApi(23)
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		locationManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}

	private final LocationReceiver locationReceiver = new LocationReceiver() {

		@Override
		public void onLocationChanged(Location location) {
			ActivityBaseLocation.this.onLocationChanged(location);
		}

		@Override
		public void onLocationFailed() {
			ActivityBaseLocation.this.onLocationFailed();
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			ActivityBaseLocation.this.onStatusChanged(provider, status, extras);
		}

		@Override
		public void onProviderEnabled(String provider) {
			ActivityBaseLocation.this.onProviderEnabled(provider);
		}

		@Override
		public void onProviderDisabled(String provider) {
			ActivityBaseLocation.this.onProviderDisabled(provider);
		}
	};

}