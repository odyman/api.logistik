package co.id.exml.logistikdr.dojo;

import co.id.exml.logistikdr.sikuel.SikuelPojo;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "DojoDeliveryStatus")
public class DojoDeliveryStatus extends SikuelPojo {

	@Column(name = "ID_Status_Delivery")
    public int ID_Status_Delivery;

	@Column(name = "status_delivery")
    public String status_delivery;

	@Column(name = "ket_status_delivery")
    public String ket_status_delivery;

	@Column(name = "status_data")
    public int status_data;

	public DojoDeliveryStatus(){
		super();
	}

	public DojoDeliveryStatus(int iD_Status_Delivery, String status_delivery,
			String ket_status_delivery, int status_data) {
		super();
		ID_Status_Delivery = iD_Status_Delivery;
		this.status_delivery = status_delivery;
		this.ket_status_delivery = ket_status_delivery;
		this.status_data = status_data;
	}

	public void doSave(){
		this.save();
	}
}