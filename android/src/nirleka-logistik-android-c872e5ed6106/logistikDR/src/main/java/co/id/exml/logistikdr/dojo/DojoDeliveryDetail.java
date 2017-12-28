package co.id.exml.logistikdr.dojo;

import co.id.exml.logistikdr.sikuel.SikuelPojo;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "DojoDeliveryDetail")
public class DojoDeliveryDetail extends SikuelPojo {

	@Column(name = "IDP_Delivery_Detail")
	public int IDP_Delivery_Detail;

	@Column(name = "IDP_Delivery")
	public int IDP_Delivery;

	@Column(name = "ID_Barang")
	public int ID_Barang;

	@Column(name = "kode_AWB")
	public String kode_AWB;

	@Column(name = "no_resi")
	public String no_resi;

	@Column(name = "jenis_barang")
	public String jenis_barang;

	@Column(name = "jenis_kirim")
	public String jenis_kirim;

	@Column(name = "nama_layanan")
	public String nama_layanan;

	@Column(name = "nama_pengirim")
	public String nama_pengirim;

	@Column(name = "nama_penerima")
	public String nama_penerima;

	@Column(name = "nama_lokasi_cust_penerima")
	public String nama_lokasi_cust_penerima;

	@Column(name = "nama_kota_destination")
	public String nama_kota_destination;

	@Column(name = "nama_lokasi_destination")
	public String nama_lokasi_destination;

	@Column(name = "kode_zone_destination")
	public String kode_zone_destination;

	@Column(name = "alamat_destination")
	public String alamat_destination;

	@Column(name = "telp_destination")
	public String telp_destination;

	@Column(name = "fax_destination")
	public String fax_destination;

	@Column(name = "nama_barang")
	public String nama_barang;

	@Column(name = "kuantitas")
	public String kuantitas;

	@Column(name = "nama_satuan")
	public String nama_satuan;

	@Column(name = "berat_kg")
	public String berat_kg;

	@Column(name = "volume")
	public String volume;

	@Column(name = "payment_method")
	public String payment_method;

	@Column(name = "nama_lengkap_assign")
	public String nama_lengkap_assign;

	@Column(name = "status_barang")
	public String status_barang;

	@Column(name = "status_data")
	public int status_data;

	@Column(notNull= false, name = "belum_ada_perasaan_ke_server")
	public int belum_ada_perasaan_ke_server = 0;

	@Column(name = "latitude_real")
	public String latitude_real;

	@Column(name = "longitude_real")
	public String longitude_real;

	@Column(name = "latitude")
	public String latitude;

	@Column(name = "longitude")
	public String longitude;

	@Column(name = "image")
	public String image;

	@Column(name = "tgl_terima")
	public String tgl_terima;

	@Column(name = "ID_Status_Delivery")
	public int ID_Status_Delivery;

	@Column(name = "delivery_remark")
	public String delivery_remark;

	public DojoDeliveryDetail(int iDP_Delivery_Detail, int iDP_Delivery,
			int iD_Barang, String kode_AWB, String no_resi,
			String jenis_barang, String jenis_kirim, String nama_layanan,
			String nama_pengirim, String nama_penerima,
			String nama_lokasi_cust_penerima, String nama_kota_destination,
			String nama_lokasi_destination, String kode_zone_destination,
			String alamat_destination, String telp_destination,
			String fax_destination, String nama_barang, String kuantitas,
			String nama_satuan, String berat_kg, String volume,
			String payment_method, String nama_lengkap_assign,
			String status_barang, int status_data,
			int belum_ada_perasaan_ke_server) {
		super();
		IDP_Delivery_Detail = iDP_Delivery_Detail;
		IDP_Delivery = iDP_Delivery;
		ID_Barang = iD_Barang;
		this.kode_AWB = kode_AWB;
		this.no_resi = no_resi;
		this.jenis_barang = jenis_barang;
		this.jenis_kirim = jenis_kirim;
		this.nama_layanan = nama_layanan;
		this.nama_pengirim = nama_pengirim;
		this.nama_penerima = nama_penerima;
		this.nama_lokasi_cust_penerima = nama_lokasi_cust_penerima;
		this.nama_kota_destination = nama_kota_destination;
		this.nama_lokasi_destination = nama_lokasi_destination;
		this.kode_zone_destination = kode_zone_destination;
		this.alamat_destination = alamat_destination;
		this.telp_destination = telp_destination;
		this.fax_destination = fax_destination;
		this.nama_barang = nama_barang;
		this.kuantitas = kuantitas;
		this.nama_satuan = nama_satuan;
		this.berat_kg = berat_kg;
		this.volume = volume;
		this.payment_method = payment_method;
		this.nama_lengkap_assign = nama_lengkap_assign;
		this.status_barang = status_barang;
		this.status_data = status_data;
		this.belum_ada_perasaan_ke_server = belum_ada_perasaan_ke_server;
	}

	public DojoDeliveryDetail(int iDP_Delivery_Detail, int iDP_Delivery,
			int iD_Barang, String kode_AWB, String no_resi,
			String jenis_barang, String jenis_kirim, String nama_layanan,
			String nama_pengirim, String nama_penerima,
			String nama_lokasi_cust_penerima, String nama_kota_destination,
			String nama_lokasi_destination, String kode_zone_destination,
			String alamat_destination, String telp_destination,
			String fax_destination, String nama_barang, String kuantitas,
			String nama_satuan, String berat_kg, String volume,
			String payment_method, String nama_lengkap_assign,
			String status_barang, int status_data,
			int belum_ada_perasaan_ke_server, String latitude_real,
			String longitude_real, String latitude, String longitude) {
		super();
		IDP_Delivery_Detail = iDP_Delivery_Detail;
		IDP_Delivery = iDP_Delivery;
		ID_Barang = iD_Barang;
		this.kode_AWB = kode_AWB;
		this.no_resi = no_resi;
		this.jenis_barang = jenis_barang;
		this.jenis_kirim = jenis_kirim;
		this.nama_layanan = nama_layanan;
		this.nama_pengirim = nama_pengirim;
		this.nama_penerima = nama_penerima;
		this.nama_lokasi_cust_penerima = nama_lokasi_cust_penerima;
		this.nama_kota_destination = nama_kota_destination;
		this.nama_lokasi_destination = nama_lokasi_destination;
		this.kode_zone_destination = kode_zone_destination;
		this.alamat_destination = alamat_destination;
		this.telp_destination = telp_destination;
		this.fax_destination = fax_destination;
		this.nama_barang = nama_barang;
		this.kuantitas = kuantitas;
		this.nama_satuan = nama_satuan;
		this.berat_kg = berat_kg;
		this.volume = volume;
		this.payment_method = payment_method;
		this.nama_lengkap_assign = nama_lengkap_assign;
		this.status_barang = status_barang;
		this.status_data = status_data;
		this.belum_ada_perasaan_ke_server = belum_ada_perasaan_ke_server;
		this.latitude_real = latitude_real;
		this.longitude_real = longitude_real;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public DojoDeliveryDetail(int iDP_Delivery_Detail, int iDP_Delivery,
			int iD_Barang, String kode_AWB, String no_resi,
			String jenis_barang, String jenis_kirim, String nama_layanan,
			String nama_pengirim, String nama_penerima,
			String nama_lokasi_cust_penerima, String nama_kota_destination,
			String nama_lokasi_destination, String kode_zone_destination,
			String alamat_destination, String telp_destination,
			String fax_destination, String nama_barang, String kuantitas,
			String nama_satuan, String berat_kg, String volume,
			String payment_method, String nama_lengkap_assign,
			String status_barang, int status_data,
			int belum_ada_perasaan_ke_server, String latitude_real,
			String longitude_real, String latitude, String longitude,
			String image, String tgl_terima, int iD_Status_Delivery,
			String delivery_remark) {
		super();
		IDP_Delivery_Detail = iDP_Delivery_Detail;
		IDP_Delivery = iDP_Delivery;
		ID_Barang = iD_Barang;
		this.kode_AWB = kode_AWB;
		this.no_resi = no_resi;
		this.jenis_barang = jenis_barang;
		this.jenis_kirim = jenis_kirim;
		this.nama_layanan = nama_layanan;
		this.nama_pengirim = nama_pengirim;
		this.nama_penerima = nama_penerima;
		this.nama_lokasi_cust_penerima = nama_lokasi_cust_penerima;
		this.nama_kota_destination = nama_kota_destination;
		this.nama_lokasi_destination = nama_lokasi_destination;
		this.kode_zone_destination = kode_zone_destination;
		this.alamat_destination = alamat_destination;
		this.telp_destination = telp_destination;
		this.fax_destination = fax_destination;
		this.nama_barang = nama_barang;
		this.kuantitas = kuantitas;
		this.nama_satuan = nama_satuan;
		this.berat_kg = berat_kg;
		this.volume = volume;
		this.payment_method = payment_method;
		this.nama_lengkap_assign = nama_lengkap_assign;
		this.status_barang = status_barang;
		this.status_data = status_data;
		this.belum_ada_perasaan_ke_server = belum_ada_perasaan_ke_server;
		this.latitude_real = latitude_real;
		this.longitude_real = longitude_real;
		this.latitude = latitude;
		this.longitude = longitude;
		this.image = image;
		this.tgl_terima = tgl_terima;
		ID_Status_Delivery = iD_Status_Delivery;
		this.delivery_remark = delivery_remark;
	}

	public DojoDeliveryDetail(){
		super();
	}

	public void doSave(){
		this.save();
	}
}