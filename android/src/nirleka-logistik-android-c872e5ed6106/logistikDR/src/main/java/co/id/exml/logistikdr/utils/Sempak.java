package co.id.exml.logistikdr.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Sempak {

	public final static String namespacesempak = "sempak_bolong_hitam";
	private static Sempak sempakBolong = null;
	private Context ctx;
	
	public static Sempak getInstance( Context ictx ) {
		if( sempakBolong == null ){
			sempakBolong = new Sempak(ictx);
		}
		return sempakBolong;
	}
	
	public Sempak( Context ictx ){
		this.ctx = ictx;
	}
	
	public Sempak set( String key, String val ) {
		SharedPreferences sharedPreferences = ctx.getSharedPreferences( Sempak.namespacesempak, Context.MODE_PRIVATE );
		SharedPreferences.Editor editor = sharedPreferences.edit();

		editor.putString( key, val );
		editor.commit();
		
		return sempakBolong;
	}

	public String get( String key ) {
		SharedPreferences sharedPreferences = ctx.getSharedPreferences( Sempak.namespacesempak, Context.MODE_PRIVATE );
		return sharedPreferences.getString( key, null );
	}

	public String get( String key, String valdef ) {
		SharedPreferences sharedPreferences = ctx.getSharedPreferences( Sempak.namespacesempak, Context.MODE_PRIVATE );
		return sharedPreferences.getString( key, valdef );
	}
}
