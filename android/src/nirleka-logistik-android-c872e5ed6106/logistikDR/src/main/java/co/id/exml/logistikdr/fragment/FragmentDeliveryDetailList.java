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
import android.widget.ListView;
import co.id.exml.logistikdr.R;
import co.id.exml.logistikdr.adapter.AdapterDeliveryDetail;
import co.id.exml.logistikdr.dojo.DojoDeliveryDetail;
import co.id.exml.logistikdr.fragment.tab.TabFragmentDeliveryItem;
import co.id.exml.logistikdr.sikuel.SikuelDeliveryDetail;

public class FragmentDeliveryDetailList extends Fragment {
	private ListView listView;
	private AdapterDeliveryDetail adapter;

	private int IDP_Delivery;

	private List<DojoDeliveryDetail> values;

	private static final String ARG_POSITION = "position";
	
	private DojoDeliveryDetail dojodetail = null;

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

		Bundle bungkus = getActivity().getIntent().getExtras();
		if( bungkus != null ) {
			IDP_Delivery = bungkus.getInt("IDP_Delivery");
		}

		values = getAllDojo();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_pickup, container, false);
		ViewCompat.setElevation(rootView, 50);

		listView = (ListView) rootView.findViewById(R.id.activity_pickup_list);
		listView.setEmptyView( ( View ) rootView.findViewById(R.id.activity_pickup_list_empty) );
		adapter = new AdapterDeliveryDetail( getContext(), values);
		listView.setAdapter( adapter );
		listView.setOnItemClickListener( new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

				dojodetail = values.get(position);
				
				if( dojodetail != null ) {
					Bundle bungkus = new Bundle();
					bungkus.putInt("IDP_Delivery_Detail", dojodetail.IDP_Delivery_Detail);
					bungkus.putInt("ID_Barang", dojodetail.ID_Barang);
					bungkus.putInt("IDP_Delivery", IDP_Delivery);
					bungkus.putString("no_resi", dojodetail.no_resi);

					Intent intents = new Intent( getActivity().getBaseContext(), TabFragmentDeliveryItem.class );
					intents.putExtras(bungkus);
					startActivity( intents );
				}
			}
		});

		return rootView;
	}

	private List<DojoDeliveryDetail> getAllDojo(){
		return SikuelDeliveryDetail.getAllBy(IDP_Delivery);
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