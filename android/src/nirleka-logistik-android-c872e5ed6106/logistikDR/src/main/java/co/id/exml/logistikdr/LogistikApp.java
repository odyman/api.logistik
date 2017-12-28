package co.id.exml.logistikdr;
import com.activeandroid.ActiveAndroid;

import android.app.Application;
import android.content.Context;

public class LogistikApp extends com.activeandroid.app.Application {

	private static Application sApplication;

	public static Application getApplication() {
		return sApplication;
	}

	public static Context getContext() {
		return getApplication().getApplicationContext();
	}


	@Override
	public void onCreate() {
		super.onCreate();
		sApplication = this;

		ActiveAndroid.initialize(this);

		/*BlurredGridMenuConfig
		.build(new BlurredGridMenuConfig.Builder()
		.radius(1)
		.downsample(1)
		.overlayColor(Color.parseColor("#AA000000")));*/

	}

}