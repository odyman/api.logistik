package co.id.exml.logistikdr.fragment.activity;

import java.util.List;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import co.id.exml.logistikdr.R;
import co.id.exml.logistikdr.adapter.AdapterPickup;
import co.id.exml.logistikdr.dojo.DojoPickupOrder;
import co.id.exml.logistikdr.fragment.tab.TabFragmentPickupDetail;
import co.id.exml.logistikdr.sikuel.SikuelPickup;
import co.id.exml.logistikdr.utils.Dimana;
import co.id.exml.logistikdr.utils.Nanyakeun;

@SuppressWarnings("deprecation")
public class ActivityPickup extends ActionBarActivity {
	private ListView listView;
	private AdapterPickup adapter;
	private List<DojoPickupOrder> values;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		values = SikuelPickup.getAll();

		setContentView(R.layout.activity_pickup);

		if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
			getSupportActionBar().setSubtitle(R.string.activity_pickup_sibtitle);
		}

		listView = (ListView) findViewById(R.id.activity_pickup_list);
		listView.setEmptyView( ( View ) findViewById(R.id.activity_pickup_list_empty) );
		adapter = new AdapterPickup(this, values);
		listView.setAdapter( adapter );
		listView.setOnItemClickListener( new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				DojoPickupOrder dojoorder = values.get(position);

				Bundle bungkus = new Bundle();
				bungkus.putInt("ID_Pickup", dojoorder.ID_Pickup);
				bungkus.putInt("ID_Order", dojoorder.ID_Order);
				bungkus.putString("kode_customer", dojoorder.kode_customer);

				Intent intents = new Intent( ActivityPickup.this, TabFragmentPickupDetail.class );
				intents.putExtras(bungkus);
				startActivity( intents );
			}
		});
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