package co.id.exml.logistikdr.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentBaka extends Fragment {

	private static View usedIt;
	
    public static FragmentBaka newInstance( View usedItYah ) {
        FragmentBaka f = new FragmentBaka();
        usedIt = usedItYah;
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return usedIt;
    }
}
