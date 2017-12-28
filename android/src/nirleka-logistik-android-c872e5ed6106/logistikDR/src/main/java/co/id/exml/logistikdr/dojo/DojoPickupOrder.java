package co.id.exml.logistikdr.dojo;

import co.id.exml.logistikdr.sikuel.SikuelPojo;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "DojoPickupOrder")
public class DojoPickupOrder extends SikuelPojo {

	@Column(notNull= false, name = "ID_Pickup_Detail")
    public int ID_Pickup_Detail;

	@Column(notNull= false, name = "ID_Pickup")
    public int ID_Pickup;

	@Column(notNull= false, name = "ID_Order")
    public int ID_Order;

	@Column(notNull= false, name = "NIP_driver")
    public String NIP_driver;

	@Column(notNull= false, name = "NIP_asisten_driver")
    public String NIP_asisten_driver;

	@Column(notNull= false, name = "kode_customer")
    public String kode_customer;

	@Column(notNull= false, name = "kode_customer_location")
    public String kode_customer_location;

	@Column(notNull= false, name = "nama_customer")
    public String nama_customer;

	public String getNama_customer() {
		return nama_customer;
	}

	public void setNama_customer(String nama_customer) {
		this.nama_customer = nama_customer;
	}


	@Column(notNull= false, name = "nama_customer_location")
    public String nama_customer_location;

	@Column(notNull= false, name = "alamat_pickup")
    public String alamat_pickup;

	@Column(notNull= false, name = "kode_kota_origin")
    public String kode_kota_origin;

	@Column(notNull= false, name = "kode_lokasi_origin")
    public String kode_lokasi_origin;

	@Column(notNull= false, name = "nama_kota_origin")
    public String nama_kota_origin;

	@Column(notNull= false, name = "nama_lokasi_origin")
    public String nama_lokasi_origin;

	@Column(notNull= false, name = "keterangan")
    public String keterangan;

	@Column(notNull= false, name = "ID_Status")
    public int ID_Status;

	@Column(notNull= false, name = "ID_Status_Detail")
    public int ID_Status_Detail;

	@Column(notNull= false, name = "status_data")
    public int status_data;

	@Column(name = "latitude")
    public String latitude = null;

	@Column(name = "longitude")
    public String longitude = null;

	public DojoPickupOrder(){
		super();
	}

	public DojoPickupOrder(int iD_Pickup_Detail, int iD_Pickup, int iD_Order,
			String nIP_driver, String nIP_asisten_driver, String kode_customer,
			String kode_customer_location, String nama_customer,
			String nama_customer_location, String alamat_pickup,
			String kode_kota_origin, String kode_lokasi_origin,
			String nama_kota_origin, String nama_lokasi_origin,
			String keterangan, int iD_Status, int iD_Status_Detail,
			int status_data) {
		super();
		ID_Pickup_Detail = iD_Pickup_Detail;
		ID_Pickup = iD_Pickup;
		ID_Order = iD_Order;
		NIP_driver = nIP_driver;
		NIP_asisten_driver = nIP_asisten_driver;
		this.kode_customer = kode_customer;
		this.kode_customer_location = kode_customer_location;
		this.nama_customer = nama_customer;
		this.nama_customer_location = nama_customer_location;
		this.alamat_pickup = alamat_pickup;
		this.kode_kota_origin = kode_kota_origin;
		this.kode_lokasi_origin = kode_lokasi_origin;
		this.nama_kota_origin = nama_kota_origin;
		this.nama_lokasi_origin = nama_lokasi_origin;
		this.keterangan = keterangan;
		ID_Status = iD_Status;
		ID_Status_Detail = iD_Status_Detail;
		this.status_data = status_data;
	}
	
	public DojoPickupOrder(int iD_Pickup_Detail, int iD_Pickup, int iD_Order,
			String nIP_driver, String nIP_asisten_driver, String kode_customer,
			String kode_customer_location, String nama_customer,
			String nama_customer_location, String alamat_pickup,
			String kode_kota_origin, String kode_lokasi_origin,
			String nama_kota_origin, String nama_lokasi_origin,
			String keterangan, int iD_Status, int iD_Status_Detail,
			int status_data, String latitude, String longitude) {
		super();
		ID_Pickup_Detail = iD_Pickup_Detail;
		ID_Pickup = iD_Pickup;
		ID_Order = iD_Order;
		NIP_driver = nIP_driver;
		NIP_asisten_driver = nIP_asisten_driver;
		this.kode_customer = kode_customer;
		this.kode_customer_location = kode_customer_location;
		this.nama_customer = nama_customer;
		this.nama_customer_location = nama_customer_location;
		this.alamat_pickup = alamat_pickup;
		this.kode_kota_origin = kode_kota_origin;
		this.kode_lokasi_origin = kode_lokasi_origin;
		this.nama_kota_origin = nama_kota_origin;
		this.nama_lokasi_origin = nama_lokasi_origin;
		this.keterangan = keterangan;
		ID_Status = iD_Status;
		ID_Status_Detail = iD_Status_Detail;
		this.status_data = status_data;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public void doSave(){
		this.save();
	}
}
