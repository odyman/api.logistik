package co.id.exml.logistikdr.fragment;

import java.util.List;

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
import co.id.exml.logistikdr.adapter.AdapterDeliveryItem;
import co.id.exml.logistikdr.dojo.DojoDeliveryItem;
import co.id.exml.logistikdr.fragment.activity.ActivityDeliveryItemInfo;
import co.id.exml.logistikdr.sikuel.SikuelDeliveryItem;

public class FragmentDeliveryItemList extends Fragment {
	private ListView listView;
	private AdapterDeliveryItem adapter;
	@SuppressWarnings("unused")
	private EditText edit1, edit2, edit3;
	private List<DojoDeliveryItem> values;

	private int ID_Barang;
	@SuppressWarnings("unused")
	private int IDP_Delivery_Detail;
	@SuppressWarnings("unused")
	private int IDP_Delivery;
	@SuppressWarnings("unused")
	private String no_resi;

	private static final String ARG_POSITION = "position";
	@SuppressWarnings("unused")
	private int position;

	private DojoDeliveryItem dojoItem = null;

	public static FragmentDeliveryItemList newInstance(int position) {
		FragmentDeliveryItemList f = new FragmentDeliveryItemList();
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
			IDP_Delivery_Detail = bungkus.getInt("IDP_Delivery_Detail");
			IDP_Delivery = bungkus.getInt("IDP_Delivery");
			ID_Barang = bungkus.getInt("ID_Barang");
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
		adapter = new AdapterDeliveryItem( getContext(), values );
		listView.setAdapter( adapter );
		listView.setOnItemClickListener( new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				dojoItem = values.get(position);
				if( dojoItem != null ) {
					Bundle bungkus = new Bundle();
					bungkus.putInt("IDP_Delivery_Detail_List", dojoItem.IDP_Delivery_Detail_List);
 
					Intent intents = new Intent( getActivity().getBaseContext(), ActivityDeliveryItemInfo.class );
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

					/*Nanyakeun.taros( getContext(), getFragmentManager(), "hapusItem", "Hapus", "Apakah anda yakin akan menghapus data?", new NanyakeunCallback() {
						@Override
						public void callback(DialogInterface dialog, int which) {
							SikuelDeliveryItem.removeBy( dojoItem.ID_Barang_Detail );
							reloadData();
						}
					}, new NanyakeunCallback() {
						@Override
						public void callback(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});*/
					
				};

				return true;
			}
		});

		return rootView;
	}

	private List<DojoDeliveryItem> getAllDojo(){
		return SikuelDeliveryItem.getAllBy(ID_Barang);
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