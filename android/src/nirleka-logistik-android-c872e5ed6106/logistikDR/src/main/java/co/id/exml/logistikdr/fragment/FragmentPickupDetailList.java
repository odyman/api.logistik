package co.id.exml.logistikdr.fragment;

import java.util.List;

import co.id.exml.logistikdr.R;
import co.id.exml.logistikdr.adapter.AdapterPickupDetail;
import co.id.exml.logistikdr.dojo.DojoPickupDetail;
import co.id.exml.logistikdr.fragment.tab.TabFragmentPickupItem;
import co.id.exml.logistikdr.sikuel.SikuelPickupDetail;
import co.id.exml.logistikdr.utils.Nanyakeun;
import co.id.exml.logistikdr.utils.NanyakeunCallback;
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
import android.widget.ListView;

public class FragmentPickupDetailList extends Fragment {
	private ListView listView;
	private AdapterPickupDetail adapter;

	private int ID_Pickup, ID_Order;
	@SuppressWarnings("unused")
	private String kode_customer;

	private List<DojoPickupDetail> values;

	private static final String ARG_POSITION = "position";
	
	private DojoPickupDetail dojodetail = null;

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

		Bundle bungkus = getActivity().getIntent().getExtras();
		if( bungkus != null ) {
			ID_Pickup = bungkus.getInt("ID_Pickup");
			ID_Order = bungkus.getInt("ID_Order");
			kode_customer = bungkus.getString("kode_customer");
		}

		values = getAllDojo();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_pickup, container, false);
		ViewCompat.setElevation(rootView, 50);

		listView = (ListView) rootView.findViewById(R.id.activity_pickup_list);
		listView.setEmptyView( ( View ) rootView.findViewById(R.id.activity_pickup_list_empty) );
		adapter = new AdapterPickupDetail( getContext(), values);
		listView.setAdapter( adapter );
		listView.setOnItemClickListener( new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

				dojodetail = values.get(position);
				
				if( dojodetail != null ) {
					Bundle bungkus = new Bundle();
					bungkus.putInt("ID_Barang", dojodetail.ID_Barang);
					bungkus.putInt("ID_Pickup", dojodetail.ID_Pickup);
					bungkus.putInt("ID_Order", dojodetail.ID_Order);
					bungkus.putString("no_resi", dojodetail.no_resi);

					Intent intents = new Intent( getActivity().getBaseContext(), TabFragmentPickupItem.class );
					intents.putExtras(bungkus);
					startActivity( intents );
				}
			}
		});
		listView.setLongClickable(true);
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				dojodetail = values.get(position);
				
				if( dojodetail != null ) {
					if ( dojodetail.belum_ada_perasaan_ke_server == 1 ) {
						Nanyakeun.taros(getContext(), getFragmentManager(), "hapusDetail", "Hapus", "Apakah anda yakin akan menghapus data?", new NanyakeunCallback() {
							@Override
							public void callback(DialogInterface dialog, int which) {
								SikuelPickupDetail.removeBy( ID_Pickup, ID_Order, dojodetail.no_resi );
								reloadData();
							}
						}, new NanyakeunCallback() {
							@Override
							public void callback(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
					};
				}
				return true;
			}
		});

		return rootView;
	}

	private List<DojoPickupDetail> getAllDojo(){
		return SikuelPickupDetail.getBy(ID_Pickup, ID_Order);
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