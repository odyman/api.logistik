package co.id.exml.logistikdr.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import co.id.exml.logistikdr.R;
import co.id.exml.logistikdr.adapter.AdapterBaka;

public class FragmentBakaListview extends Fragment {

	private AdapterBaka adapter;
	private FragmentBakaListviewCallback callback = null;

	public static FragmentBakaListview newInstance( AdapterBaka adapterIn, FragmentBakaListviewCallback clbk ) {
		Bundle args = new Bundle();
		FragmentBakaListview fragment = new FragmentBakaListview( adapterIn, clbk );
		fragment.setArguments(args);
		return fragment;
	}

	public FragmentBakaListview()
    {
    }

    @SuppressLint("ValidFragment")
	public FragmentBakaListview( AdapterBaka adapterIn, FragmentBakaListviewCallback clbk ){
		adapter = adapterIn;
		callback = clbk;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View mainView = inflater.inflate(R.layout.activity_pickup, container, false);

		ListView listView = (ListView) mainView.findViewById(R.id.activity_pickup_list);
		listView.setEmptyView( ( View ) mainView.findViewById(R.id.activity_pickup_list_empty) );

		listView.setAdapter( adapter );
		if( callback != null ) {
			listView.setOnItemClickListener( new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
					callback.callback(parent, view, position, id);
				}
			});
		};

		return mainView;
	}
}
