package co.id.exml.logistikdr.sikuel;

import java.util.List;

import co.id.exml.logistikdr.dojo.DojoDeliveryItem;

import com.activeandroid.query.Select;

public class SikuelDeliveryItem {

	public static List<DojoDeliveryItem> getAll(){
		return new Select().from(DojoDeliveryItem.class).execute();
	}

	public static DojoDeliveryItem getBy( int id ){
		return new Select().from(DojoDeliveryItem.class)
				.where("IDP_Delivery_Detail_List = ?", id ).executeSingle();
	}

	public static DojoDeliveryItem getByAny( int IDP_Delivery_Detail, int ID_Barang, String kode_barcode_awal ){
		return new Select().from(DojoDeliveryItem.class)
				.where("IDP_Delivery_Detail = ? and ID_Barang = ? and kode_barcode_awal = ?", 
						IDP_Delivery_Detail, ID_Barang, kode_barcode_awal ).executeSingle();
	}

	public static DojoDeliveryItem hasScanned( int IDP_Delivery_Detail, int ID_Barang, String kode_barcode_awal ){
		return new Select().from(DojoDeliveryItem.class)
				.where("IDP_Delivery_Detail = ? and ID_Barang = ? and kode_barcode_awal = ? and ID_Status_Detail = ?", 
						IDP_Delivery_Detail, ID_Barang, kode_barcode_awal, 2 ).executeSingle();
	}

	public static List<DojoDeliveryItem> getAllByAny( int IDP_Delivery_Detail, int ID_Barang ){
		return new Select().from(DojoDeliveryItem.class)
				.where("IDP_Delivery_Detail = ? and ID_Barang = ? and IDP_Delivery = ?", 
						IDP_Delivery_Detail, ID_Barang ).execute();
	}

	public static List<DojoDeliveryItem> getAllByIDP( int IDP_Delivery_Detail ){
		return new Select().from(DojoDeliveryItem.class)
				.where("IDP_Delivery_Detail = ? ", IDP_Delivery_Detail )
				.orderBy("belum_ada_perasaan_ke_server desc").execute();
	}

	public static List<DojoDeliveryItem> getAllBy( int ID_Barang ){
		return new Select().from(DojoDeliveryItem.class)
				.where("ID_Barang = ? ", ID_Barang )
				.orderBy("belum_ada_perasaan_ke_server desc").execute();
	}

	public static List<DojoDeliveryItem> getAllByScanned( int ID_Barang ){
		return new Select().from(DojoDeliveryItem.class)
				.where("ID_Barang = ? and ID_Status_Detail = ?", ID_Barang, 2 )
				.orderBy("belum_ada_perasaan_ke_server desc").execute();
	}

	public static List<DojoDeliveryItem> getUnsaved(){
		return new Select().from(DojoDeliveryItem.class)
				.where("belum_ada_perasaan_ke_server = ?", 1 )
				.execute();
	}
	
	public static int getAllScanned( int ID_Barang ){
		return SikuelDeliveryItem.getAllByScanned(ID_Barang).size();
	}
	
	public static int getAllMustBeScanned( int ID_Barang ){
		return SikuelDeliveryItem.getAllBy(ID_Barang).size();
	}

	public static void setData( DojoDeliveryItem obj ){
		//validasi
		obj.save();
	}

	public static void removeBy( int id ){
		DojoDeliveryItem obj = SikuelDeliveryItem.getBy(id);
		if( obj != null )
			obj.delete();
	}
}
