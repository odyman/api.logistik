package co.id.exml.logistikdr.utils;

import java.util.Locale;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class OpenGoogleMaps {

	public static void openOnGoogleMaps( Context context, float latitude, float longitude ){
		String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", latitude, longitude, "Where the party is at");
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
		intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
		try{
			context.startActivity(intent);
		}catch(ActivityNotFoundException ex){
			try{
				Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
				context.startActivity(unrestrictedIntent);
			}catch(ActivityNotFoundException innerEx){
				Toast.makeText(context, "Please install a maps application", Toast.LENGTH_LONG).show();
			}
		}
	}

}
