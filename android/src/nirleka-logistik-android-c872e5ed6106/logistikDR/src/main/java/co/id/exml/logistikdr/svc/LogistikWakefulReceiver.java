package co.id.exml.logistikdr.svc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LogistikWakefulReceiver extends BroadcastReceiver	 {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent service = new Intent(context, LogistikWakefulService.class);

		context.startService(service);

		//Intent App = new Intent( context, ActivityLogin.class );
		//App.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//context.startActivity(App);
	}
	
}
