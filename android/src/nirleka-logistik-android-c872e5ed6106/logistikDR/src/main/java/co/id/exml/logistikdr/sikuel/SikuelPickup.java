package co.id.exml.logistikdr.sikuel;

import java.util.List;

import co.id.exml.logistikdr.dojo.DojoPickupOrder;

import com.activeandroid.query.Select;

public class SikuelPickup {

	public static DojoPickupOrder getBy( int id ){
		return new Select().from(DojoPickupOrder.class)
				.where("ID_Order = ?", id ).executeSingle();
	}

	public static List<DojoPickupOrder> getAll(){
		return new Select().from(DojoPickupOrder.class).execute();
	}
}
