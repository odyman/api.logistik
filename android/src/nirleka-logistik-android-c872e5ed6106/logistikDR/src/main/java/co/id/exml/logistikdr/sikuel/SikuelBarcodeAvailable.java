package co.id.exml.logistikdr.sikuel;

import java.util.List;

import co.id.exml.logistikdr.dojo.DojoPickupBarcodeAvailable;

import com.activeandroid.query.Select;

public class SikuelBarcodeAvailable {

	public static DojoPickupBarcodeAvailable getBy( String kode_barcode ){
		return new Select().from(DojoPickupBarcodeAvailable.class)
				.where("kode_barcode = ?", kode_barcode ).executeSingle();
	}

	public static List<DojoPickupBarcodeAvailable> getAll(){
		return new Select().from(DojoPickupBarcodeAvailable.class).execute();
	}
	
	public static int getCount(){
		return SikuelBarcodeAvailable.getAll().size();
	}
	
}
