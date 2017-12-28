package co.id.exml.logistikdr.fragment.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import co.id.exml.logistikdr.R;
import co.id.exml.logistikdr.utils.Dimana;
import co.id.exml.logistikdr.utils.DrawToBip;
import co.id.exml.logistikdr.utils.Nanyakeun;
import co.id.exml.logistikdr.utils.SuratCinta;
import co.id.exml.logistikdr.utils.Temp;

@SuppressWarnings("deprecation")
public class ActivityWarning extends ActionBarActivity {

	private String title, subtitle, pesan;
	private LinearLayout emptyLayout = null;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		Bundle bungkus = getIntent().getExtras();
		title = bungkus.getString("title");
		subtitle = bungkus.getString("subtitle");
		pesan = bungkus.getString("pesan");

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		setContentView(R.layout.activity_empty);

		if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
			getSupportActionBar().setTitle( title );
			getSupportActionBar().setSubtitle( subtitle );
		}

		emptyLayout = ( LinearLayout ) findViewById( R.id.activity_empty_layout );
		emptyLayout.removeAllViews();

		emptyLayout.addView( getDetailView() );
	}

	private View getDetailView(){
		Temp[] temp = {
				new Temp("", "", true ),
				new Temp("", "", DrawToBip.convert(getResources().getDrawable(R.drawable.ic_launcher)), true ),
				new Temp("Pesan", String.valueOf( pesan ), true ),
				new Temp("", "", true )
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