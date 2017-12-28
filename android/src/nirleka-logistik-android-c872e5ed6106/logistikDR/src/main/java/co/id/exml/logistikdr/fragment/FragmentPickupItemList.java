package co.id.exml.logistikdr.fragment;

import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import co.id.exml.logistikdr.R;
import co.id.exml.logistikdr.adapter.AdapterPickupItem;
import co.id.exml.logistikdr.dojo.DojoPickupItem;
import co.id.exml.logistikdr.fragment.activity.ActivityPickupItemInfo;
import co.id.exml.logistikdr.sikuel.SikuelPickupItem;
import co.id.exml.logistikdr.utils.Nanyakeun;
import co.id.exml.logistikdr.utils.NanyakeunCallback;

public class FragmentPickupItemList extends Fragment {
	private ListView listView;
	private AdapterPickupItem adapter;
	@SuppressWarnings("unused")
	private EditText edit1, edit2, edit3;
	private List<DojoPickupItem> values;

	private int ID_Pickup, ID_Order;
	private String no_resi;

	private static final String ARG_POSITION = "position";
	@SuppressWarnings("unused")
	private int position;

	private DojoPickupItem dojoItem = null;

	public static FragmentPickupItemList newInstance(int position) {
		FragmentPickupItemList f = new FragmentPickupItemList();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		position = getArguments().getInt(ARG_POSITION);

		Bundle bungkus = getActivity().getIntent().getExtras();
		if( bungkus != null ) {
			ID_Pickup = bungkus.getInt("ID_Pickup");
			ID_Order = bungkus.getInt("ID_Order");
			no_resi = bungkus.getString("no_resi");
		};

		values = getAllDojo();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_pickup, container, false);
		ViewCompat.setElevation(rootView, 50);

		listView = (ListView) rootView.findViewById(R.id.activity_pickup_list);
		listView.setEmptyView( ( View ) rootView.findViewById(R.id.activity_pickup_list_empty) );
		adapter = new AdapterPickupItem( getContext(), values );
		listView.setAdapter( adapter );
		listView.setOnItemClickListener( new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				dojoItem = values.get(position);
				if( dojoItem != null ) {
					Bundle bungkus = new Bundle();
					bungkus.putString("kode_barcode", dojoItem.kode_barcode);
 
					Intent intents = new Intent( getActivity().getBaseContext(), ActivityPickupItemInfo.class );
					intents.putExtras(bungkus);
					startActivity( intents );
				};
			}
		});
		listView.setLongClickable(true);
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				dojoItem = values.get(position);
				if( dojoItem != null ) {
					if ( dojoItem.belum_ada_perasaan_ke_server == 1  ){
						Nanyakeun.taros( getContext(), getFragmentManager(), "hapusItem", "Hapus", "Apakah anda yakin akan menghapus data?", new NanyakeunCallback() {
							@Override
							public void callback(DialogInterface dialog, int which) {
								SikuelPickupItem.removeBy( ID_Pickup, ID_Order, no_resi, dojoItem.kode_barcode );
								reloadData();
							}
						}, new NanyakeunCallback() {
							@Override
							public void callback(DialogInterface dialog, int which) {
								dialog.cancel();
							}
						});
					};
				};

				return true;
			}
		});

		return rootView;
	}

	private List<DojoPickupItem> getAllDojo(){
		return SikuelPickupItem.getBy(ID_Pickup, no_resi);
	}

	@Override
	public void onResume(){
		super.onResume();
		this.reloadData();
	}

	private void reloadData(){
		values = getAllDojo();
		adapter.clear();
		adapter.addAll( values );
		adapter.notifyDataSetChanged();
	}

} 