package co.id.exml.logistikdr.sikuel;

import java.util.List;

import co.id.exml.logistikdr.dojo.DojoDeliveryOrder;

import com.activeandroid.query.Select;

public class SikuelDelivery {

	public static DojoDeliveryOrder getBy( int id ){
		return new Select().from(DojoDeliveryOrder.class)
				.where("IDP_Delivery = ?", id ).executeSingle();
	}

	public static List<DojoDeliveryOrder> getAll(){
		return new Select().from(DojoDeliveryOrder.class).execute();
	}
}
