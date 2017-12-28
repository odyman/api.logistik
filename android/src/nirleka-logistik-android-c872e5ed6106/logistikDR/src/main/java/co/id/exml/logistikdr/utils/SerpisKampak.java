package co.id.exml.logistikdr.utils;

import java.util.List;

import co.id.exml.logistikdr.LogistikApp;
import co.id.exml.logistikdr.svc.LogistikWakefulService;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;

public class SerpisKampak {
	
	public static boolean isRunningLogistikService( Context ctx ){
		return ( SerpisKampak.isServiceRunning(LogistikWakefulService.class.getName()) );
	}
	
	public static void doRunningLogistikService( Context ctx ){
		Intent service = new Intent(ctx, LogistikWakefulService.class);
		ctx.startService(service);
	}

	public static boolean isServiceRunning(String serviceClassName){
		final ActivityManager activityManager = (ActivityManager)LogistikApp.getContext().getSystemService(Context.ACTIVITY_SERVICE);
		final List<RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);

		for (RunningServiceInfo runningServiceInfo : services) {
			if (runningServiceInfo.service.getClassName().equals(serviceClassName)){
				return true;
			}
		}
		return false;
	}

}
