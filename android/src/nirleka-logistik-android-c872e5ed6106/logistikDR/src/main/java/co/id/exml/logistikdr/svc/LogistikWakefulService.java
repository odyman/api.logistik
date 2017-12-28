package co.id.exml.logistikdr.svc;

import co.id.exml.logistikdr.R;
import co.id.exml.logistikdr.utils.Dimana;
import co.id.exml.logistikdr.utils.DimanaManeh;
import co.id.exml.logistikdr.utils.Sempak;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.widget.Toast;

public class LogistikWakefulService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	@Override
	public void onCreate() {
		Dimana.start( this, new DimanaManeh() {
			@Override
			public void timeOut() {
				Toast.makeText(getApplicationContext(), getString(R.string.app_name) + " - Waktu habis ketika mengambil lokasi perangkat", Toast.LENGTH_LONG).show();
			}

			@Override
			public void next() {
				Toast.makeText(getApplicationContext(), getString(R.string.app_name) + " - Memulai pengambilan lokasi perangkat", Toast.LENGTH_LONG).show();
			}

			@Override
			public void hasDisable() {
				Toast.makeText(getApplicationContext(), getString(R.string.app_name) + " - Silahkan aktifkan GPS perangkat", Toast.LENGTH_LONG).show();
			}

			@Override
			public void found(Location location) {
				System.out.println("Latitude " + String.valueOf(location.getLatitude()) + " - Longitude " + String.valueOf(location.getLongitude()) );
				Sempak.getInstance(getApplicationContext())
				.set("latitude", String.valueOf(location.getLatitude()))
				.set("longitude", String.valueOf(location.getLongitude()));
			}
		});
		super.onCreate();
	}

}
