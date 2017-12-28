package co.id.exml.logistikdr;

import co.id.exml.logistikdr.biskuit.IBiskuitSync;
import co.id.exml.logistikdr.dojo.DojoUser;
import co.id.exml.logistikdr.dojo.DojoUserLogon;
import co.id.exml.logistikdr.fragment.activity.ActivityDelivery;
import co.id.exml.logistikdr.fragment.activity.ActivityPickup;
import co.id.exml.logistikdr.fragment.activity.ActivityProfile;
import co.id.exml.logistikdr.utils.BakaViewHolderSimple;
import co.id.exml.logistikdr.utils.Dimana;
import co.id.exml.logistikdr.utils.Nanyakeun;
import co.id.exml.logistikdr.utils.NanyakeunCallback;

import android.support.v7.app.ActionBarActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressWarnings("deprecation")
@SuppressLint({ "ClickableViewAccessibility", "InflateParams" })
public class ActivityMain extends ActionBarActivity implements OnItemClickListener {

	private static final int TIME_INTERVAL = 2000;
	private long mBackPressed;

	private LauncherIcon[] ICONS;

	private TextView dashboardInfo = null;
	private IBiskuitSync syncx = null;
	private DojoUser currentUser = null;
	private int baruLogon = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if( !DojoUserLogon.hasLogon() ){
			doLogout();
		};

		Bundle bungkus = getIntent().getExtras();
		if( bungkus != null ){
			this.baruLogon = bungkus.getInt("baruLogon", 0);
		};

		this.currentUser = DojoUserLogon.getUserLogon();

		if( this.currentUser.type == 1 || this.currentUser.type == 2 ){
			ICONS = new LauncherIcon[]{
					new LauncherIcon(R.drawable.ic_launcher_logistik_pickup, "Pickup", "pickup"),
					new LauncherIcon(R.drawable.ic_launcher_logistik_order, "Delivery", "delivery"),
					new LauncherIcon(R.drawable.ic_launcher_pickup_sync, "Sync!", "sync"),
					new LauncherIcon(R.drawable.ic_launcher_logistik_user, "Log Out", "logout"),
			};
		}else if( this.currentUser.type == 3 ){
			ICONS = new LauncherIcon[]{
					new LauncherIcon(R.drawable.ic_launcher_logistik_user, "Log Out", "logout"),
			};
		}

		setContentView(R.layout.activity_main);

		if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
			getSupportActionBar().setSubtitle(R.string.client_company);
		}

		this.syncx =  new IBiskuitSync( this );
		this.dashboardInfo = ( TextView ) findViewById(R.id.dashboard_info);

		this.dashboardInfo.setText( getString(R.string.tagline) );
		this.dashboardInfo.setTextColor(Color.parseColor("#444444"));

		GridView gridview = (GridView) findViewById(R.id.dashboard_grid);
		gridview.setAdapter(new ImageAdapter(this));
		gridview.setOnItemClickListener(this);

		gridview.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return event.getAction() == MotionEvent.ACTION_MOVE;
			}
		});

		if( baruLogon == 1 ){
			this.syncx.doSyncData();
		};
	}
	
	@Override
	public void onResume(){
		if (!Dimana.isActive(this)){
			Nanyakeun.taros( this, getSupportFragmentManager(), "showWarningGPS", "Peringatan", getString(R.string.location_ask));
		};
		super.onResume();
	}

	static class LauncherIcon {
		final String text;
		final int imgId;
		final String map;

		public LauncherIcon(int imgId, String text, String map) {
			super();
			this.imgId = imgId;
			this.text = text;
			this.map = map;
		}

	}

	class ImageAdapter extends BaseAdapter {
		private Context mContext;

		public ImageAdapter(Context c) {
			mContext = c;
		}

		@Override
		public int getCount() {
			return ICONS.length;
		}

		@Override
		public LauncherIcon getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			BakaViewHolderSimple holder;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

				v = vi.inflate(R.layout.dashboard_icon, null);
				holder = new BakaViewHolderSimple();
				holder.text = (TextView) v.findViewById(R.id.dashboard_icon_text);
				holder.icon = (ImageView) v.findViewById(R.id.dashboard_icon_img);
				v.setTag(holder);
			} else {
				holder = (BakaViewHolderSimple) v.getTag();
			}

			holder.icon.setImageResource(ICONS[position].imgId);
			animeImg( holder.icon, mContext );
			holder.text.setText(ICONS[position].text);

			return v;
		}
	}

	private static void animeImg ( final ImageView iv, final Context mContext ){
		iv.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Animation animFadein = AnimationUtils.loadAnimation( mContext, R.anim.fade_in);
				iv.startAnimation(animFadein); 
				return false;
			}
		});
	}

	@Override
	public void onBackPressed() {
		if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) { 
			finish();
		}else {
			Toast.makeText(getBaseContext(), "Tap back button in order to exit.", Toast.LENGTH_SHORT).show(); 
		}

		mBackPressed = System.currentTimeMillis();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		switch( ICONS[position].map ){
		case "pickup" :
			Intent showModulePickup = new Intent( ActivityMain.this, ActivityPickup.class );
			startActivity( showModulePickup );
			break;
		case "delivery" :
			Intent showModuleDelivery = new Intent( ActivityMain.this, ActivityDelivery.class );
			startActivity( showModuleDelivery );
			//moduleNotFound();
			break;
		case "sync" :
			askFoSync();
			break;
		case "logout" :
			doLogout();
			break;
		}
	}

	private void doLogout(){
		Nanyakeun.taros( this, getSupportFragmentManager(), "yakinLogout", "Log Out", "Apakah anda yakin untuk keluar?", new NanyakeunCallback() {
			@Override
			public void callback(DialogInterface dialog, int which) {
				DojoUserLogon.logout();
				Intent backToLogin = new Intent( ActivityMain.this, ActivityLogin.class );
				startActivity( backToLogin );
				finish();
			}
		},new NanyakeunCallback() {
			@Override
			public void callback(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
	}

	private void askFoSync(){
		Nanyakeun.taros( this, getSupportFragmentManager(), "yakinDiSync", "Sync!", "Apakah anda yakin untuk melakukan Singkronisasi?", new NanyakeunCallback() {
			@Override
			public void callback(DialogInterface dialog, int which) {
				syncx.doSyncData();
			}
		}, new NanyakeunCallback() {
			@Override
			public void callback(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
	}

	@SuppressWarnings("unused")
	private void moduleNotFound(){
		Nanyakeun.taros( this, getSupportFragmentManager(), "moduleNotFound", "Info", "Belum tersedia");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_profile :
			showDetailUserLogon();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void showDetailUserLogon(){
		startActivity(new Intent( this, ActivityProfile.class));
	}
}
