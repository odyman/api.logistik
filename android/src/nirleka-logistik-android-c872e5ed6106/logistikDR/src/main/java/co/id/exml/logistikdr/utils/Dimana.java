package co.id.exml.logistikdr.utils;

import fr.quentinklein.slt.LocationTracker;
import fr.quentinklein.slt.TrackerSettings;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

public class Dimana {

	public static TrackerSettings getSetting(){
		return new TrackerSettings()
		.setUseGPS(true)
        .setUseNetwork(true)
        .setUsePassive(true)
        .setTimeBetweenUpdates( 5 * 60 * 1000 )
        .setMetersBetweenUpdates( 10 );
	}
	
	public static boolean isActive( Context ctx ){
		if ( !SerpisKampak.isRunningLogistikService(ctx)) {
			SerpisKampak.doRunningLogistikService(ctx);
		}
		return ( ( ( LocationManager )ctx.getSystemService( Context.LOCATION_SERVICE ) ).isProviderEnabled( LocationManager.GPS_PROVIDER ) );
	}

	public static LocationTracker start( Context ctx, final DimanaManeh dimanaManeh ){
		LocationTracker tracker = null;
		if ( !Dimana.isActive(ctx) ) {
			dimanaManeh.hasDisable();
		} else {
			tracker = new LocationTracker(ctx, Dimana.getSetting()) {
				@Override
				public void onLocationFound(Location location) {
					dimanaManeh.found(location);
				}

				@Override
				public void onTimeout() {
					dimanaManeh.timeOut();
				}
			};
			tracker.startListening();
			dimanaManeh.next();
		};
		return tracker;
	}

}
