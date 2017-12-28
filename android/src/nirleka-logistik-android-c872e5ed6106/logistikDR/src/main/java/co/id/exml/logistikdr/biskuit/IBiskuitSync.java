package co.id.exml.logistikdr.biskuit;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import co.id.exml.logistikdr.dojo.DojoDeliveryDetail;
import co.id.exml.logistikdr.dojo.DojoDeliveryItem;
import co.id.exml.logistikdr.dojo.DojoPickupDetail;
import co.id.exml.logistikdr.dojo.DojoPickupItem;
import co.id.exml.logistikdr.sikuel.SikuelDeliveryDetail;
import co.id.exml.logistikdr.sikuel.SikuelDeliveryItem;
import co.id.exml.logistikdr.sikuel.SikuelKabaret;
import co.id.exml.logistikdr.sikuel.SikuelPickupDetail;
import co.id.exml.logistikdr.sikuel.SikuelPickupItem;
import co.id.exml.logistikdr.utils.Nanyakeun;
import co.id.exml.logistikdr.utils.Pijat;

public class IBiskuitSync {

	private Context context = null;
	private ProgressDialog progress = null;

	private List<DojoDeliveryDetail> detailChangeDelivery = null;
	private List<DojoDeliveryItem> itemChangeDelivery = null;
	private List<DojoPickupDetail> detailChange = null;
	private List<DojoPickupItem> itemChange = null;

	public IBiskuitSync( Context context ){
		this.context = context;

		progress = new ProgressDialog( context );
		progress.setTitle("Sync!");
		progress.setMessage("Silahkan tunggu...");
		progress.setCancelable(false);
	}

	public void doSyncData(){
		detailChangeDelivery = SikuelDeliveryDetail.getUnsaved();
		itemChangeDelivery = SikuelDeliveryItem.getUnsaved();
		detailChange = SikuelPickupDetail.getUnsaved();
		itemChange = SikuelPickupItem.getUnsaved();

		//write json data
		boolean itsOk = false;
		JSONObject dataPickup =  new JSONObject();
		JSONObject dataDelivery =  new JSONObject();
		JSONObject dataJSON = new JSONObject();
		try {
			dataPickup.put("detail", Pijat.DOJO2JSONArray(detailChange) );
			dataPickup.put("item", Pijat.DOJO2JSONArray(itemChange) );
			
			dataDelivery.put("process", Pijat.DOJO2JSONArray(detailChangeDelivery));
			dataDelivery.put("processitems", Pijat.DOJO2JSONArray(itemChangeDelivery));

			dataJSON.put("pickup", dataPickup);
			dataJSON.put("delivery", dataDelivery);
			
			itsOk = true;
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		};
		if( itsOk ){
			IBiskuit biskuit = new IBiskuit( context );
			biskuit.setAll();
			biskuit.setParam("data", dataJSON.toString() );
			biskuit.post("/api/sync", new IBiskuitHandler(){

				@Override
				public void onStart() {
					progress.show();
				}

				@Override
				public void onSuccess(JSONObject response) {
					doIfSuccess( response );
				}

				@Override
				public void onSuccess(JSONArray response) {
					JSONObject obj = null;
					try {
						obj = response.getJSONObject(0);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					doIfSuccess( obj );
				}

				@Override
				public void onFailure() {
					doIfSuccess( null );
				}

				@Override
				public void onRetry() {
					doIfSuccess( null );
				}

			});
		}else{
			showWarning();
		};
	}
	
	private void checkIfUnChangeIsNotSavedByServer(){
		//detailChange && itemChange
		
	}

	private void doIfSuccess( JSONObject obj ){
		boolean statusSync = false;
		if( obj != null ){
			try {
				if ( obj.getBoolean("status") == true ) {
					statusSync = SikuelKabaret.fillemptyAllTables( obj );
					if( statusSync ){
						checkIfUnChangeIsNotSavedByServer();
					};
				};
			} catch (JSONException e) {
				e.printStackTrace();
			};
		};
		progress.dismiss();
		if ( statusSync ){
			Nanyakeun.taros( context, ( ( FragmentActivity ) context ).getSupportFragmentManager(), "yakinDiSync", "Sync!", "Sync berhasil dilakukan!");
		}else{
			showWarning();
		}
	}

	private void showWarning(){
		Nanyakeun.taros( context, ( ( FragmentActivity ) context ).getSupportFragmentManager(), "gagalSync", "Peringatan!", "Sync GAGAL dilakukan!");
	}

}
