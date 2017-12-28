package co.id.exml.logistikdr.fragment.activity;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import co.id.exml.logistikdr.R;
import co.id.exml.logistikdr.dojo.DojoUser;
import co.id.exml.logistikdr.dojo.DojoUserLogon;
import co.id.exml.logistikdr.sikuel.SikuelBarcodeAvailable;
import co.id.exml.logistikdr.sikuel.SikuelPickupDetail;
import co.id.exml.logistikdr.sikuel.SikuelPickupItem;
import co.id.exml.logistikdr.utils.Dimana;
import co.id.exml.logistikdr.utils.DrawToBip;
import co.id.exml.logistikdr.utils.Nanyakeun;
import co.id.exml.logistikdr.utils.Sempak;
import co.id.exml.logistikdr.utils.SuratCinta;
import co.id.exml.logistikdr.utils.Temp;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

@SuppressWarnings("deprecation")
public class ActivityProfile extends ActionBarActivity {

	private LinearLayout emptyLayout = null;

	private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
	private TextDrawable.IBuilder mDrawableBuilder;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		setContentView(R.layout.activity_empty);

		if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
			getSupportActionBar().setSubtitle( R.string.activity_profile_subtitle );
		}

		mDrawableBuilder = TextDrawable.builder().beginConfig().height(200).width(200).endConfig().roundRect(10);

		emptyLayout = ( LinearLayout ) findViewById( R.id.activity_empty_layout );
		emptyLayout.removeAllViews();

		emptyLayout.addView( getDetailView() );
	}

	private View getDetailView(){
		DojoUser dojoUser = DojoUserLogon.getUserLogon();

		TextDrawable drawable = mDrawableBuilder.build( dojoUser.username.substring(0,2), mColorGenerator.getColor(dojoUser.username));
		String typeUser = "Unknow";
		if( dojoUser.type == 1 ) {
			typeUser = "Driver";
		}else if( dojoUser.type == 2 ) {
			typeUser = "Driver Assistant";
		}else if( dojoUser.type == 3 ) {
			typeUser = "Staff";
		};

		String latitude = Sempak.getInstance(getApplicationContext()).get("latitude");
		if( latitude == null ) {
			latitude = "";
		}
		if( latitude != null && latitude.length() <= 0 ) {
			latitude = "-";
		}
		String longitude = Sempak.getInstance(getApplicationContext()).get("longitude");
		if( longitude == null ) {
			longitude = "";
		}
		if( longitude != null && longitude.length() <= 0 ) {
			longitude = "-";
		}
		
		Temp[] temp = {
				new Temp("Username", String.valueOf( dojoUser.username ), DrawToBip.convert(drawable), true ),
				new Temp("Username", String.valueOf( dojoUser.username ) ),
				new Temp("NIP", String.valueOf( dojoUser.nip ) ),
				new Temp("Latitude", latitude ),
				new Temp("Longitude", longitude ),
				new Temp("Tipe", String.valueOf( typeUser ) ),
				new Temp("Sisa Barcode", String.valueOf( SikuelBarcodeAvailable.getCount() ) ),
				new Temp("Pickup Detail Belum di Sync!", String.valueOf( SikuelPickupDetail.getUnsaved().size() ) ),
				new Temp("Pickup Item Belum di Sync!", String.valueOf( SikuelPickupItem.getUnsaved().size() ) )
		};
		return SuratCinta.manaSuratKu( this, temp );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
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
	}

} 