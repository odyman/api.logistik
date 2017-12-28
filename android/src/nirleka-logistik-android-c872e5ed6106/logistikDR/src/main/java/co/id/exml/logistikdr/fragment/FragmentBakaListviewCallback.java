package co.id.exml.logistikdr.fragment;

import android.view.View;
import android.widget.AdapterView;

public interface FragmentBakaListviewCallback {
	
	public void callback( AdapterView<?> parent, View view,int position, long id );
	
}
