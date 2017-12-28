package co.id.exml.logistikdr.dojo;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

public class DojoUserLogon {

	public static void login( String username, String token, String nip, int type ){
		ActiveAndroid.beginTransaction();
		try {
			DojoUser user = new DojoUser( username, token, nip, type );
			user.save();
			ActiveAndroid.setTransactionSuccessful();
		}
		finally {
			ActiveAndroid.endTransaction();
		}
	}
	public static boolean hasLogon(){
		return DojoUserLogon.getUserLogon() != null ? true : false;
	}

	public static DojoUser getUserLogon(){
	    return new Select().from(DojoUser.class).executeSingle();
	}

	public static void logout(){
		new Delete().from(DojoUser.class).where("Id = ?", DojoUserLogon.getUserLogon().getId()).execute();
	}

}
