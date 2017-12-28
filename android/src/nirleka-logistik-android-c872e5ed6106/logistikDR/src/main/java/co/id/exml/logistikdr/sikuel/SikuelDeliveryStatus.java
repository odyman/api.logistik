package co.id.exml.logistikdr.sikuel;

import java.util.ArrayList;
import java.util.List;

import co.id.exml.logistikdr.dojo.DojoDeliveryStatus;
import co.id.exml.logistikdr.utils.Temp;

import com.activeandroid.query.From;
import com.activeandroid.query.Select;

public class SikuelDeliveryStatus {

	public static DojoDeliveryStatus getBy( int ID_Status_Delivery ){
		return new Select().from(DojoDeliveryStatus.class)
				.where("ID_Status_Delivery = ?", ID_Status_Delivery ).executeSingle();
	}

	public static List<DojoDeliveryStatus> getAllNot( int[] ID_Status_Delivery ){
		From query = new Select().from(DojoDeliveryStatus.class);
		for( int i = 0; i < ID_Status_Delivery.length; i++ ) {
			query.where("ID_Status_Delivery != ?", ID_Status_Delivery[i] );
		};
		return query.execute();
	}

	public static List<DojoDeliveryStatus> getAll(){
		return new Select().from(DojoDeliveryStatus.class).execute();
	}

	public static int getCount(){
		return SikuelDeliveryStatus.getAll().size();
	}

	public static List<Temp> getAllAsTempNotAny( int[] ID_Status_Delivery ){
		return SikuelDeliveryStatus.getAllAsTempDummy( SikuelDeliveryStatus.getAllNot( ID_Status_Delivery ) );
	}

	public static List<Temp> getAllAsTemp(){
		return SikuelDeliveryStatus.getAllAsTempDummy( SikuelDeliveryStatus.getAll() );
	}

	public static List<Temp> getAllAsTempDummy( List<DojoDeliveryStatus> listOfStatus ){
		List<Temp> tempList = new ArrayList<Temp>();
		for( DojoDeliveryStatus statusdelivery : listOfStatus ) {
			String keterangan = statusdelivery.ket_status_delivery;
			if( keterangan.equals( null ) ){
				keterangan = "-";
			};
			keterangan = String.valueOf( keterangan ).trim();
			tempList.add(new Temp(String.valueOf(statusdelivery.status_delivery).trim(), keterangan, String.valueOf(statusdelivery.ID_Status_Delivery) ));
		};
		return tempList;
	}

}
