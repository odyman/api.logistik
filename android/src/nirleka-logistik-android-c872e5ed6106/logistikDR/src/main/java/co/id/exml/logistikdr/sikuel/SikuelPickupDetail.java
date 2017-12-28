package co.id.exml.logistikdr.sikuel;

import com.activeandroid.query.Select;

import java.util.List;

import co.id.exml.logistikdr.dojo.DojoPickupDetail;

public class SikuelPickupDetail {

	public static List<DojoPickupDetail> getAll(){
		return new Select().from(DojoPickupDetail.class).execute();
	}

	public static DojoPickupDetail getBy( int id ){
		return new Select().from(DojoPickupDetail.class)
				.where("ID_Barang = ?", id ).executeSingle();
	}

	public static DojoPickupDetail getBy( int iD_Pickup, int iD_Order, String no_resi ){
		return new Select().from(DojoPickupDetail.class)
				.where("ID_Pickup = ? and ID_Order = ? and no_resi = ?", iD_Pickup, iD_Order, no_resi ).executeSingle();
	}

	public static List<DojoPickupDetail> getBy( int ID_Pickup, int ID_Order ){
		return new Select().from(DojoPickupDetail.class)
				.where("ID_Pickup = ? AND ID_Order = ? ", ID_Pickup, ID_Order )
				.orderBy("belum_ada_perasaan_ke_server desc").execute();
	}

	public static List<DojoPickupDetail> getUnsaved(){
		return new Select().from(DojoPickupDetail.class)
				.where("belum_ada_perasaan_ke_server = ?", 1 )
				.execute();
	}

	public static void setData( DojoPickupDetail obj ){
		//validasi
		obj.save();
	}

	public static void removeBy( int id ){
		DojoPickupDetail obj = SikuelPickupDetail.getBy(id);
		if( obj != null )
			obj.delete();
	}

	public static void removeBy( int iD_Pickup, int iD_Order, String no_resi ){
		DojoPickupDetail obj = SikuelPickupDetail.getBy( iD_Pickup, iD_Order, no_resi );
		if( obj != null )
			obj.delete();
	}

	public static boolean resiIsExist( int iD_Pickup, int iD_Order, String no_resi ){
		no_resi = no_resi.trim();
		return ( SikuelPickupDetail.getBy( iD_Pickup, iD_Order, no_resi ) != null ? true : false );
	}

	public static boolean isValidInput( DojoPickupDetail detail ){
		if( detail.no_resi == null ) {
			return false;
		}
		if( detail.jenis_kirim == null ) {
			return false;
		}
		if( detail.nama_barang == null ) {
			return false;
		}
		if( detail.nama_satuan == null ) {
			return false;
		}
		if( detail.kuantitas == null ) {
			return false;
		}
		if( detail.nama_penerima == null ) {
			return false;
		}
		if( detail.no_resi == null ) {
			return false;
		}
		if( detail.jenis_kirim.length() <= 0 ) {
			return false;
		}
		if( detail.nama_barang.length() <= 0 ) {
			return false;
		}
		if( detail.nama_satuan.length() <= 0 ) {
			return false;
		}
		if( detail.kuantitas.length() <= 0 ) {
			return false;
		}
		if( Float.valueOf(detail.kuantitas) < 0 ) {
			return false;
		}
		if( detail.nama_penerima.length() <= 0 ) {
			return false;
		}
		if( detail.ID_Pickup <= 0 ) {
			return false;
		}
		if( detail.ID_Order <= 0 ) {
			return false;
		}
		if( detail.ID_Customer_Location <= 0 ) {
			return false;
		}
		if ( detail.volume == null ) {
			return false;
		}
		if ( detail.berat_kg == null ) {
			return false;
		}

		return true;
	}

}
