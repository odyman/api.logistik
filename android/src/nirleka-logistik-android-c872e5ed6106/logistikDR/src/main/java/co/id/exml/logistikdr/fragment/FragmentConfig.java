package co.id.exml.logistikdr.fragment;

import co.id.exml.logistikdr.R;
import co.id.exml.logistikdr.dojo.DojoHash;
import co.id.exml.logistikdr.sikuel.SikuelHash;
import co.id.exml.logistikdr.utils.Sempak;
import android.content.Context;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

public class FragmentConfig extends PreferenceFragment {

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		getActivity();
		getPreferenceManager().setSharedPreferencesMode( Context.MODE_PRIVATE );
		getPreferenceManager().setSharedPreferencesName( Sempak.namespacesempak );

		PreferenceScreen uiConfigs = getPreferenceManager().createPreferenceScreen(getActivity());

		PreferenceCategory category = new PreferenceCategory(getActivity());
		category.setTitle("Server");
		category.setSummary("Konfigurasi Server");

		uiConfigs.addPreference(category);

		EditTextPreference apiendpoint = new EditTextPreference(getActivity());
		apiendpoint.setKey( getActivity().getString(R.string.configs_server_api) );
		apiendpoint.setTitle("API");
		apiendpoint.setSummary("URL API Logistic");

		EditTextPreference apikeys = new EditTextPreference(getActivity());
		apikeys.setKey( getActivity().getString(R.string.configs_server_key) );
		apikeys.setTitle("Key");

		String defaultURI = "http://182.253.221.107/logistik-api/index.php";
		DojoHash dojoHas1 = SikuelHash.getBy( getActivity().getString(R.string.configs_server_api ) );
		if( dojoHas1 != null ) {
			defaultURI = dojoHas1.val;
		}
        apiendpoint.setDefaultValue( defaultURI );
        apiendpoint.setSummary( "Masukan URL dari API server untuk Apps" );

		String defaultKey = "d9d0df1575dfd37ea087404c853b64ee";
		DojoHash dojoHas2 = SikuelHash.getBy( getActivity().getString(R.string.configs_server_key ) );
		if( dojoHas2 != null ) {
			defaultKey = dojoHas2.val;
		}
        apikeys.setDefaultValue( defaultKey );
        apikeys.setSummary( "Masukan Key dari API server untuk Apps" );

		Sempak.getInstance(getActivity()).set( getActivity().getString(R.string.configs_server_api ), defaultURI );
		Sempak.getInstance(getActivity()).set( getActivity().getString(R.string.configs_server_key ), defaultKey );

		category.addPreference(apiendpoint);
		category.addPreference(apikeys);

		setPreferenceScreen(uiConfigs);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

}
