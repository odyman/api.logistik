package co.id.exml.logistikdr.sikuel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.id.exml.logistikdr.dojo.DojoDeliveryDetail;
import co.id.exml.logistikdr.dojo.DojoDeliveryItem;
import co.id.exml.logistikdr.dojo.DojoDeliveryOrder;
import co.id.exml.logistikdr.dojo.DojoDeliveryStatus;
import co.id.exml.logistikdr.dojo.DojoPickupBarcodeAvailable;
import co.id.exml.logistikdr.dojo.DojoPickupCustomer;
import co.id.exml.logistikdr.dojo.DojoPickupDetail;
import co.id.exml.logistikdr.dojo.DojoPickupItem;
import co.id.exml.logistikdr.dojo.DojoPickupJenisKirim;
import co.id.exml.logistikdr.dojo.DojoPickupOrder;
import co.id.exml.logistikdr.utils.Pijat;

import com.activeandroid.ActiveAndroid;

public class SikuelKabaret {

	public static void emptyAllTables(){
		ActiveAndroid.beginTransaction();
		try {
			DojoPickupDetail.truncate( DojoPickupDetail.class );
			DojoPickupItem.truncate( DojoPickupItem.class );
			DojoPickupBarcodeAvailable.truncate( DojoPickupBarcodeAvailable.class );
			DojoPickupCustomer.truncate( DojoPickupCustomer.class );
			DojoPickupOrder.truncate( DojoPickupOrder.class );
			DojoPickupJenisKirim.truncate( DojoPickupJenisKirim.class );

			DojoDeliveryStatus.truncate(DojoDeliveryStatus.class);
			DojoDeliveryItem.truncate(DojoDeliveryItem.class);
			DojoDeliveryDetail.truncate(DojoDeliveryDetail.class);
			DojoDeliveryOrder.truncate(DojoDeliveryOrder.class);

			ActiveAndroid.setTransactionSuccessful();
		}
		finally {
			ActiveAndroid.endTransaction();
		}
	}

	public static boolean fillemptyAllTables( JSONObject obj ){
		SikuelKabaret.emptyAllTables();

		boolean status = false;
		ActiveAndroid.beginTransaction();
		try {
			JSONObject pickupData = obj.getJSONObject("pickup");

			JSONArray orderData = pickupData.getJSONArray("order");
			JSONArray detailData = pickupData.getJSONArray("detail");
			JSONArray itemData = pickupData.getJSONArray("item");
			JSONArray customerData = pickupData.getJSONArray("customer");	
			JSONObject barcodeavailable = pickupData.getJSONObject("barcode");
			JSONArray barcodeData = barcodeavailable.getJSONArray("available");
			JSONArray jeniskirimData = pickupData.getJSONArray("jeniskirim");	

			JSONObject deliveryData = obj.getJSONObject("delivery");

			JSONArray dl_orderData = deliveryData.getJSONArray("order");
			JSONArray dl_detailData = deliveryData.getJSONArray("detail");
			JSONArray dl_itemData = deliveryData.getJSONArray("item");
			JSONArray dl_statusData = deliveryData.getJSONArray("status");	

			Pijat.JSON2DOJO(orderData, DojoPickupOrder.class);
			Pijat.JSON2DOJO(detailData, DojoPickupDetail.class);
			Pijat.JSON2DOJO(itemData, DojoPickupItem.class);
			Pijat.JSON2DOJO(customerData, DojoPickupCustomer.class);
			Pijat.JSON2DOJO(barcodeData, DojoPickupBarcodeAvailable.class);
			Pijat.JSON2DOJO(jeniskirimData, DojoPickupJenisKirim.class);

			Pijat.JSON2DOJO(dl_orderData, DojoDeliveryOrder.class);
			Pijat.JSON2DOJO(dl_detailData, DojoDeliveryDetail.class);
			Pijat.JSON2DOJO(dl_itemData, DojoDeliveryItem.class);
			Pijat.JSON2DOJO(dl_statusData, DojoDeliveryStatus.class);

			ActiveAndroid.setTransactionSuccessful();

			status = true;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		finally {
			ActiveAndroid.endTransaction();
		}
		return status;
	}

}
