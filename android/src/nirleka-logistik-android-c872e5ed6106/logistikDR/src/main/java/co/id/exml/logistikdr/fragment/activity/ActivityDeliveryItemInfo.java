package co.id.exml.logistikdr.fragment.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import co.id.exml.logistikdr.R;
import co.id.exml.logistikdr.dojo.DojoDeliveryItem;
import co.id.exml.logistikdr.sikuel.SikuelDeliveryItem;
import co.id.exml.logistikdr.utils.Dimana;
import co.id.exml.logistikdr.utils.Makerot;
import co.id.exml.logistikdr.utils.Nanyakeun;
import co.id.exml.logistikdr.utils.SuratCinta;
import co.id.exml.logistikdr.utils.Temp;

@SuppressWarnings("deprecation")
public class ActivityDeliveryItemInfo extends ActionBarActivity {

	private int IDP_Delivery_Detail_List;
	private LinearLayout emptyLayout = null;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		Bundle bungkus = getIntent().getExtras();
		IDP_Delivery_Detail_List = bungkus.getInt("IDP_Delivery_Detail_List");

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		setContentView(R.layout.activity_empty);

		if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
			getSupportActionBar().setSubtitle( R.string.activity_delivery_entry_item_list_subtitle );
		}

		emptyLayout = ( LinearLayout ) findViewById( R.id.activity_empty_layout );
		emptyLayout.removeAllViews();

		emptyLayout.addView( getDetailView() );
	}

	private View getDetailView(){
		DojoDeliveryItem dojoItem = SikuelDeliveryItem.getBy(IDP_Delivery_Detail_List);

		Temp[] temp = {
				new Temp("Visual QR", String.valueOf( dojoItem.IDP_Delivery_Detail_List ), Makerot.defaultQR( String.valueOf( dojoItem.kode_barcode ) ), true ),
				new Temp("Kode Barcode", String.valueOf( dojoItem.kode_barcode_awal ) ),
				new Temp("", String.valueOf( "" ), true ),
				new Temp("Keterangan", String.valueOf( dojoItem.keterangan_awal ) )
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