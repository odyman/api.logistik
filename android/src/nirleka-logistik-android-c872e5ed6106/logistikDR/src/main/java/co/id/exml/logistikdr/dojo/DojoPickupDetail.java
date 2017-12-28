package co.id.exml.logistikdr.dojo;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import co.id.exml.logistikdr.sikuel.SikuelPojo;

@Table(name = "DojoPickupDetail")
public class DojoPickupDetail extends SikuelPojo {

	@Column(notNull= false, name = "ID_Barang")
    public int ID_Barang;

	@Column(notNull= false, name = "ID_Order")
    public int ID_Order;

	@Column(notNull= false, name = "ID_Outgoing")
    public int ID_Outgoing;

	@Column(notNull= false, name = "ID_Customer_Location")
    public int ID_Customer_Location;

	@Column(notNull= false, name = "ID_Pickup")
    public int ID_Pickup;

	@Column( notNull= false, name = "tipe_pickup")
	public int tipe_pickup;

	@Column(notNull= false, name = "no_resi")
    public String no_resi;

	@Column(notNull= false, name = "tgl_request_pickup")
    public String tgl_request_pickup;

	@Column(notNull= false, name = "tgl_pickup")
    public String tgl_pickup;

	@Column(notNull= false, name = "jenis_barang")
    public String jenis_barang;

	@Column(notNull= false, name = "jenis_kirim")
    public String jenis_kirim;

	@Column(notNull= false, name = "kode_layanan")
    public String kode_layanan;

	@Column(notNull= false, name = "nama_pengirim")
    public String nama_pengirim;

	@Column(notNull= false, name = "kode_customer_location")
    public String kode_customer_location;

	@Column(notNull= false, name = "nama_customer_location")
    public String nama_customer_location;

	@Column(notNull= false, name = "kode_kota_origin")
    public String kode_kota_origin;

	@Column(notNull= false, name = "kode_lokasi_origin")
    public String kode_lokasi_origin;

	@Column(notNull= false, name = "kode_zone_origin")
    public String kode_zone_origin;

	@Column(notNull= false, name = "alamat_pickup")
    public String alamat_pickup;

	@Column(notNull= false, name = "nama_kota_origin")
    public String nama_kota_origin;

	@Column(notNull= false, name = "nama_lokasi_origin")
    public String nama_lokasi_origin;

	@Column(notNull= false, name = "nama_zone_origin")
    public String nama_zone_origin;

	@Column(notNull= false, name = "nama_penerima")
    public String nama_penerima;

	@Column(notNull= false, name = "nama_lokasi_cust_penerima")
    public String nama_lokasi_cust_penerima;

	@Column(notNull= false, name = "kode_kota_destination")
    public String kode_kota_destination;

	@Column(notNull= false, name = "kode_lokasi_destination")
    public String kode_lokasi_destination;

	@Column(notNull= false, name = "kode_zone_destination")
    public String kode_zone_destination;

	@Column(notNull= false, name = "alamat_destination")
    public String alamat_destination;

	@Column(notNull= false, name = "nama_kota_destination")
    public String nama_kota_destination;

	@Column(notNull= false, name = "nama_lokasi_destination")
    public String nama_lokasi_destination;

	@Column(notNull= false, name = "nama_zone_destination")
    public String nama_zone_destination;

	@Column(notNull= false, name = "telp_destination")
    public String telp_destination;

	@Column(notNull= false, name = "fax_destination")
    public String fax_destination;

	@Column(notNull= false, name = "nama_barang")
    public String nama_barang;

	@Column(notNull= false, name = "nama_satuan")
    public String nama_satuan;

	@Column(notNull= false, name = "kuantitas")
    public String kuantitas;

	@Column(notNull= false, name = "berat_kg")
    public String berat_kg;

	@Column(notNull= false, name = "dim_lebar_cm")
    public String dim_lebar_cm;

	@Column(notNull= false, name = "dim_panjang_cm")
    public String dim_panjang_cm;

	@Column(notNull= false, name = "dim_tinggi_cm")
    public String dim_tinggi_cm;

	@Column(notNull= false, name = "volume")
    public String volume;

	@Column(notNull= false, name = "volume_metrik")
    public String volume_metrik;

	@Column(notNull= false, name = "keterangan")
    public String keterangan;

	@Column(notNull= false, name = "ID_Status_Barang")
    public int ID_Status_Barang;

	@Column(notNull= false, name = "status_barang")
    public String status_barang;

	@Column(notNull= false, name = "status_data")
    public int status_data;

	@Column(notNull= false, name = "belum_ada_perasaan_ke_server")
    public int belum_ada_perasaan_ke_server = 0;

	public DojoPickupDetail(){
		super();
	}

	public DojoPickupDetail(int iD_Barang, int iD_Order, int iD_Pickup,
			String no_resi, String tgl_request_pickup, String tgl_pickup,
			String jenis_barang, String jenis_kirim, String kode_layanan,
			String nama_pengirim, String kode_customer_location,
			String nama_customer_location, String kode_kota_origin,
			String kode_lokasi_origin, String kode_zone_origin,
			String alamat_pickup, String nama_kota_origin,
			String nama_lokasi_origin, String nama_zone_origin,
			String nama_penerima, String nama_lokasi_cust_penerima,
			String kode_kota_destination, String kode_lokasi_destination,
			String kode_zone_destination, String alamat_destination,
			String nama_kota_destination, String nama_lokasi_destination,
			String nama_zone_destination, String telp_destination,
			String fax_destination, String nama_barang, String nama_satuan,
			String kuantitas, String berat_kg, String dim_lebar_cm,
			String dim_panjang_cm, String dim_tinggi_cm, String volume,
			String volume_metrik, String keterangan, int iD_Status_Barang,
			String status_barang, int status_data, int ID_Customer_Location, int belum_ada_perasaan_ke_server) {
		super();
		this.ID_Barang = iD_Barang;
		this.ID_Order = iD_Order;
		this.ID_Pickup = iD_Pickup;
		this.ID_Customer_Location = ID_Customer_Location;
		this.no_resi = no_resi;
		this.tgl_request_pickup = tgl_request_pickup;
		this.tgl_pickup = tgl_pickup;
		this.jenis_barang = jenis_barang;
		this.jenis_kirim = jenis_kirim;
		this.kode_layanan = kode_layanan;
		this.nama_pengirim = nama_pengirim;
		this.kode_customer_location = kode_customer_location;
		this.nama_customer_location = nama_customer_location;
		this.kode_kota_origin = kode_kota_origin;
		this.kode_lokasi_origin = kode_lokasi_origin;
		this.kode_zone_origin = kode_zone_origin;
		this.alamat_pickup = alamat_pickup;
		this.nama_kota_origin = nama_kota_origin;
		this.nama_lokasi_origin = nama_lokasi_origin;
		this.nama_zone_origin = nama_zone_origin;
		this.nama_penerima = nama_penerima;
		this.nama_lokasi_cust_penerima = nama_lokasi_cust_penerima;
		this.kode_kota_destination = kode_kota_destination;
		this.kode_lokasi_destination = kode_lokasi_destination;
		this.kode_zone_destination = kode_zone_destination;
		this.alamat_destination = alamat_destination;
		this.nama_kota_destination = nama_kota_destination;
		this.nama_lokasi_destination = nama_lokasi_destination;
		this.nama_zone_destination = nama_zone_destination;
		this.telp_destination = telp_destination;
		this.fax_destination = fax_destination;
		this.nama_barang = nama_barang;
		this.nama_satuan = nama_satuan;
		this.kuantitas = kuantitas;
		this.berat_kg = berat_kg;
		this.dim_lebar_cm = dim_lebar_cm;
		this.dim_panjang_cm = dim_panjang_cm;
		this.dim_tinggi_cm = dim_tinggi_cm;
		this.volume = volume;
		this.volume_metrik = volume_metrik;
		this.keterangan = keterangan;
		ID_Status_Barang = iD_Status_Barang;
		this.status_barang = status_barang;
		this.status_data = status_data;
		this.belum_ada_perasaan_ke_server = belum_ada_perasaan_ke_server;
	}


	public int getID_Customer_Location() {
		return ID_Customer_Location;
	}

	public void setID_Customer_Location(int iD_Customer_Location) {
		ID_Customer_Location = iD_Customer_Location;
	}

	public String getBeratKgStr()
	{
		return berat_kg != null ? berat_kg : "0";
	}

	public String getVolumeStr()
	{
		return volume != null ? volume : "0";
	}

	public void doSave(){
		this.save();
	}
}
