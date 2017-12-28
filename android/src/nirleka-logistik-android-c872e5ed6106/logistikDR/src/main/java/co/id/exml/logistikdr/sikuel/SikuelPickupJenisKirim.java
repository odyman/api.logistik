package co.id.exml.logistikdr.sikuel;

import java.util.ArrayList;
import java.util.List;

import co.id.exml.logistikdr.dojo.DojoPickupJenisKirim;
import co.id.exml.logistikdr.utils.Temp;

import com.activeandroid.query.Select;

public class SikuelPickupJenisKirim {

	public static DojoPickupJenisKirim getBy( int ID_Jns_kirim ){
		return new Select().from(DojoPickupJenisKirim.class)
				.where("ID_Jns_kirim = ?", ID_Jns_kirim ).executeSingle();
	}

	public static List<DojoPickupJenisKirim> getAll(){
		return new Select().from(DojoPickupJenisKirim.class).execute();
	}
	
	public static int getCount(){
		return SikuelPickupJenisKirim.getAll().size();
	}
	
	public static List<Temp> getAllAsTemp(){
		List<Temp> tempList = new ArrayList<Temp>();
		for( DojoPickupJenisKirim jeniskirim : SikuelPickupJenisKirim.getAll() ) {
			String keterangan = jeniskirim.keterangan;
			if( keterangan.equals( null ) ){
				keterangan = "-";
			};
			keterangan = String.valueOf( keterangan ).trim();
			tempList.add(new Temp(String.valueOf(jeniskirim.jenis_kirim).trim(), keterangan, String.valueOf(jeniskirim.ID_Jns_kirim) ));
		};
		return tempList;
	}
	
}
