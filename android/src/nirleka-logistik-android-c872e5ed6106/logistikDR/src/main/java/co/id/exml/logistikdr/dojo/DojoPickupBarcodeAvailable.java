package co.id.exml.logistikdr.dojo;

import co.id.exml.logistikdr.sikuel.SikuelPojo;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "DojoPickupBarcodeAvailable")
public class DojoPickupBarcodeAvailable extends SikuelPojo {

	@Column(name = "NIP")
    public String NIP;

	@Column(name = "kode_barcode")
    public String kode_barcode;

	public DojoPickupBarcodeAvailable(){
		super();
	}

	public DojoPickupBarcodeAvailable(String nIP, String kode_barcode) {
		super();
		NIP = nIP;
		this.kode_barcode = kode_barcode;
	}
	
	public void doSave(){
		this.save();
	}
}
