package co.id.exml.logistikdr.dojo;

import co.id.exml.logistikdr.sikuel.SikuelPojo;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "DojoPickupItem")
public class DojoPickupItem extends SikuelPojo {

	@Column(name = "ID_Barang_Detail")
    public int ID_Barang_Detail;

	@Column(name = "ID_Order")
    public int ID_Order;

	@Column(name = "ID_Pickup")
    public int ID_Pickup;

	@Column(name = "no_resi")
    public String no_resi;

	@Column(name = "ID_Barang")
    public int ID_Barang;

	@Column(name = "kode_barcode")
    public String kode_barcode;

	@Column(name = "berat_kg")
    public String berat_kg;

	@Column(name = "dim_lebar_cm")
    public String dim_lebar_cm;

	@Column(name = "dim_panjang_cm")
    public String dim_panjang_cm;

	@Column(name = "dim_tinggi_cm")
    public String dim_tinggi_cm;

	@Column(name = "volume")
    public String volume;

	@Column(name = "keterangan")
    public String keterangan;

	@Column(name = "belum_ada_perasaan_ke_server")
    public int belum_ada_perasaan_ke_server = 0;

	@Column(name = "latitude")
    public String latitude = null;

	@Column(name = "longitude")
    public String longitude = null;

	public DojoPickupItem(){
		super();
	}

	public DojoPickupItem(int iD_Barang_Detail, int iD_Order, int iD_Pickup,
			String no_resi, int iD_Barang, String kode_barcode,
			String berat_kg, String dim_lebar_cm, String dim_panjang_cm,
			String dim_tinggi_cm, String volume, String keterangan, int belum_ada_perasaan_ke_server) {
		super();
		ID_Barang_Detail = iD_Barang_Detail;
		ID_Order = iD_Order;
		ID_Pickup = iD_Pickup;
		this.no_resi = no_resi;
		ID_Barang = iD_Barang;
		this.kode_barcode = kode_barcode;
		this.berat_kg = berat_kg;
		this.dim_lebar_cm = dim_lebar_cm;
		this.dim_panjang_cm = dim_panjang_cm;
		this.dim_tinggi_cm = dim_tinggi_cm;
		this.volume = volume;
		this.keterangan = keterangan;
		this.belum_ada_perasaan_ke_server = belum_ada_perasaan_ke_server;
	}

	public DojoPickupItem(int iD_Barang_Detail, int iD_Order, int iD_Pickup,
			String no_resi, int iD_Barang, String kode_barcode,
			String berat_kg, String dim_lebar_cm, String dim_panjang_cm,
			String dim_tinggi_cm, String volume, String keterangan, int belum_ada_perasaan_ke_server, String latitude, String longitude ) {
		super();
		ID_Barang_Detail = iD_Barang_Detail;
		ID_Order = iD_Order;
		ID_Pickup = iD_Pickup;
		this.no_resi = no_resi;
		ID_Barang = iD_Barang;
		this.kode_barcode = kode_barcode;
		this.berat_kg = berat_kg;
		this.dim_lebar_cm = dim_lebar_cm;
		this.dim_panjang_cm = dim_panjang_cm;
		this.dim_tinggi_cm = dim_tinggi_cm;
		this.volume = volume;
		this.keterangan = keterangan;
		this.belum_ada_perasaan_ke_server = belum_ada_perasaan_ke_server;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	
	public void doSave(){
		this.save();
	}
}
