package co.id.exml.logistikdr.fragment.activity;

import co.id.exml.logistikdr.R;
import co.id.exml.logistikdr.dojo.DojoPickupItem;
import co.id.exml.logistikdr.sikuel.SikuelPickupItem;
import co.id.exml.logistikdr.utils.Dimana;
import co.id.exml.logistikdr.utils.Makerot;
import co.id.exml.logistikdr.utils.Nanyakeun;
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
public class ActivityPickupItemInfo extends ActionBarActivity {

	private String kode_barcode;
	private LinearLayout emptyLayout = null;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		Bundle bungkus = getIntent().getExtras();
		kode_barcode = bungkus.getString("kode_barcode");

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		setContentView(R.layout.activity_empty);

		if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
			getSupportActionBar().setSubtitle( R.string.activity_pickup_entry_item_list_subtitle );
		}

		emptyLayout = ( LinearLayout ) findViewById( R.id.activity_empty_layout );
		emptyLayout.removeAllViews();

		emptyLayout.addView( getDetailView() );
	}

	private View getDetailView(){
		DojoPickupItem dojoItem = SikuelPickupItem.getBy(kode_barcode);

		Temp[] temp = {
				new Temp("Visual QR", String.valueOf( dojoItem.kode_barcode ), Makerot.defaultQR( String.valueOf( dojoItem.kode_barcode ) ), true ),
				new Temp("Kode Barcode", String.valueOf( dojoItem.kode_barcode ) ),
				new Temp("Berat", String.valueOf( dojoItem.berat_kg ) + " Kg" ),
				new Temp("Volume", String.valueOf( dojoItem.volume ) + " m3" ),
				new Temp("Keterangan", String.valueOf( dojoItem.keterangan ) )
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