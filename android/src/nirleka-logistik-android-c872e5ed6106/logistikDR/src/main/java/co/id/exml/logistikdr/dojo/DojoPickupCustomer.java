package co.id.exml.logistikdr.dojo;

import co.id.exml.logistikdr.sikuel.SikuelPojo;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "DojoPickupCustomer")
public class DojoPickupCustomer extends SikuelPojo {

	@Column(name = "ID_Customer_Location")
    public int ID_Customer_Location;

	@Column(name = "kode_customer_location")
    public String kode_customer_location;

	@Column(name = "kode_customer")
    public String kode_customer;

	@Column(name = "nama_penerima")
    public String nama_penerima;

	@Column(name = "nama_lokasi_cust_penerima")
    public String nama_lokasi_cust_penerima;

	@Column(name = "alamat_destination")
    public String alamat_destination;

	@Column(name = "nama_kota_destination")
    public String nama_kota_destination;

	@Column(name = "nama_lokasi_destination")
    public String nama_lokasi_destination;

	@Column(name = "nama_zone_destination")
    public String nama_zone_destination;

	@Column(name = "kode_kota_destination")
    public String kode_kota_destination;

	@Column(name = "kode_lokasi_destination")
    public String kode_lokasi_destination;

	@Column(name = "kode_zone_destination")
    public String kode_zone_destination;

	@Column(name = "telp_destination")
    public String telp_destination;

	@Column(name = "fax_destination")
    public String fax_destination;

	@Column(name = "jenis_lokasi_customer")
    public String jenis_lokasi_customer;

	public DojoPickupCustomer(){
		super();
	}

	public DojoPickupCustomer(int iD_Customer_Location,
			String kode_customer_location, String kode_customer,
			String nama_penerima, String nama_lokasi_cust_penerima,
			String alamat_destination, String nama_kota_destination,
			String nama_lokasi_destination, String nama_zone_destination,
			String kode_kota_destination, String kode_lokasi_destination,
			String kode_zone_destination, String telp_destination,
			String fax_destination, String jenis_lokasi_customer) {
		super();
		ID_Customer_Location = iD_Customer_Location;
		this.kode_customer_location = kode_customer_location;
		this.kode_customer = kode_customer;
		this.nama_penerima = nama_penerima;
		this.nama_lokasi_cust_penerima = nama_lokasi_cust_penerima;
		this.alamat_destination = alamat_destination;
		this.nama_kota_destination = nama_kota_destination;
		this.nama_lokasi_destination = nama_lokasi_destination;
		this.nama_zone_destination = nama_zone_destination;
		this.kode_kota_destination = kode_kota_destination;
		this.kode_lokasi_destination = kode_lokasi_destination;
		this.kode_zone_destination = kode_zone_destination;
		this.telp_destination = telp_destination;
		this.fax_destination = fax_destination;
		this.jenis_lokasi_customer = jenis_lokasi_customer;
	}

	
	public void doSave(){
		this.save();
	}
}
