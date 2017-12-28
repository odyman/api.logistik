package co.id.exml.logistikdr.sikuel;

import java.util.ArrayList;
import java.util.List;

import co.id.exml.logistikdr.dojo.DojoPickupCustomer;
import co.id.exml.logistikdr.utils.Temp;
import com.activeandroid.query.Select;

public class SikuelPickupCustomer {

	public static DojoPickupCustomer getBy( int ID_Customer_Location ){
		return new Select().from(DojoPickupCustomer.class)
				.where("ID_Customer_Location = ?", ID_Customer_Location ).executeSingle();
	}

	public static List<DojoPickupCustomer> getAll(){
		return new Select().from(DojoPickupCustomer.class).execute();
	}

	public static List<DojoPickupCustomer> getAll( String kode_customer ){
		return new Select().from(DojoPickupCustomer.class)
				.where("kode_customer = ?", kode_customer ).execute();
	}

	public static int getCount(){
		return SikuelPickupCustomer.getAll().size();
	}

	public static List<Temp> getAllAsTemp( String kode_customer ){
		List<Temp> tempList = new ArrayList<Temp>();
		for( DojoPickupCustomer customer : SikuelPickupCustomer.getAll( kode_customer ) ) {
			String keterangan = String.valueOf(customer.nama_lokasi_cust_penerima).trim();
			tempList.add(new Temp(String.valueOf(customer.nama_penerima).trim(), keterangan, String.valueOf(customer.ID_Customer_Location) ));
		};
		return tempList;
	}

}
