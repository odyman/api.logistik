package co.id.exml.logistikdr.sikuel;

import java.util.List;

import co.id.exml.logistikdr.dojo.DojoDeliveryDetail;

import com.activeandroid.query.Select;

public class SikuelDeliveryDetail {

	public static List<DojoDeliveryDetail> getAll(){
		return new Select().from(DojoDeliveryDetail.class).execute();
	}

	public static DojoDeliveryDetail getBy( int id ){
		return new Select().from(DojoDeliveryDetail.class)
				.where("IDP_Delivery_Detail = ?", id ).executeSingle();
	}

	public static DojoDeliveryDetail getBy( int IDP_Delivery, int IDP_Delivery_Detail ){
		return new Select().from(DojoDeliveryDetail.class)
				.where("IDP_Delivery = ? AND IDP_Delivery_Detail = ?", IDP_Delivery, IDP_Delivery_Detail ).executeSingle();
	}

	public static List<DojoDeliveryDetail> getAllBy( int IDP_Delivery ){
		return new Select().from(DojoDeliveryDetail.class)
				.where("IDP_Delivery = ? ", IDP_Delivery )
				.orderBy("belum_ada_perasaan_ke_server desc").execute();
	}

	public static List<DojoDeliveryDetail> getUnsaved(){
		return new Select().from(DojoDeliveryDetail.class)
				.where("belum_ada_perasaan_ke_server = ?", 1 )
				.execute();
	}

	public static void setData( DojoDeliveryDetail obj ){
		//validasi
		obj.save();
	}

	public static void removeBy( int id ){
		DojoDeliveryDetail obj = SikuelDeliveryDetail.getBy(id);
		if( obj != null )
			obj.delete();
	}

}
