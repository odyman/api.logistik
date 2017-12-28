package co.id.exml.logistikdr.sikuel;

import java.util.List;

import co.id.exml.logistikdr.dojo.DojoHash;

import com.activeandroid.query.Select;

public class SikuelHash {

	public static DojoHash getBy( String key ){
		return new Select().from(DojoHash.class)
				.where("key = ?", key ).executeSingle();
	}

	public static List<DojoHash> getAll(){
		return new Select().from(DojoHash.class).execute();
	}
}
