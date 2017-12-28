package co.id.exml.logistikdr.sikuel;

import java.util.List;

import co.id.exml.logistikdr.dojo.DojoPickupItem;

import com.activeandroid.query.Select;

public class SikuelPickupItem {

	public static List<DojoPickupItem> getAll(){
		return new Select().from(DojoPickupItem.class).execute();
	}

	public static DojoPickupItem getBy( int id ){
		return new Select().from(DojoPickupItem.class)
				.where("ID_Barang_Detail = ?", id ).executeSingle();
	}

	public static DojoPickupItem getBy( String kode_barcode ){
		return new Select().from(DojoPickupItem.class)
				.where("kode_barcode = ?", kode_barcode ).executeSingle();
	}

	public static DojoPickupItem getBy( int iD_Pickup, int iD_Order, String no_resi, String kode_barcode ){
		return new Select().from(DojoPickupItem.class)
				.where("ID_Pickup = ? AND ID_Order = ? AND no_resi = ? AND kode_barcode = ?", iD_Pickup, iD_Order, no_resi, kode_barcode ).executeSingle();
	}
	
	public static List<DojoPickupItem> getBy( int ID_Pickup, String no_resi ){
		return new Select().from(DojoPickupItem.class)
				.where("ID_Pickup = ? AND no_resi = ? ", ID_Pickup, no_resi )
				.orderBy("belum_ada_perasaan_ke_server desc").execute();
	}

	public static List<DojoPickupItem> getUnsaved(){
		return new Select().from(DojoPickupItem.class)
				.where("belum_ada_perasaan_ke_server = ?", 1 )
				.execute();
	}
	
	public static void setData( DojoPickupItem obj ){
		//validasi
		obj.save();
	}
	
	public static void removeBy( int id ){
		DojoPickupItem obj = SikuelPickupItem.getBy(id);
		if( obj != null )
			obj.delete();
	}
	
	public static void removeBy( int iD_Pickup, int iD_Order, String no_resi, String kode_barcode ){
		DojoPickupItem obj = SikuelPickupItem.getBy( iD_Pickup, iD_Order, no_resi, kode_barcode );
		if( obj != null )
			obj.delete();
	}
}
