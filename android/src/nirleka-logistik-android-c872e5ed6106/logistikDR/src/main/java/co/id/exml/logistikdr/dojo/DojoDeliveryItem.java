package co.id.exml.logistikdr.dojo;

import co.id.exml.logistikdr.sikuel.SikuelPojo;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "DojoDeliveryItem")
public class DojoDeliveryItem extends SikuelPojo {

	@Column(notNull= false, name = "belum_ada_perasaan_ke_server")
	public int belum_ada_perasaan_ke_server = 0;

	@Column(name = "IDP_Delivery_Detail_List")
	public int IDP_Delivery_Detail_List;

	@Column(name = "IDP_Delivery_Detail")
	public int IDP_Delivery_Detail;

	@Column(name = "ID_Barang_Detail")
	public int ID_Barang_Detail;

	@Column(name = "ID_Barang")
	public int ID_Barang;

	//2 == sudah di scan dari server OK
	@Column(name = "ID_Status_Detail")
	public int ID_Status_Detail;

	@Column(name = "kode_barcode")
	public String kode_barcode;

	@Column(name = "kode_barcode_awal")
	public String kode_barcode_awal;

	@Column(name = "keterangan")
	public String keterangan;

	@Column(name = "keterangan_awal")
	public String keterangan_awal;

	@Column(name = "status_data")
	public int status_data;

	@Column(name = "latitude")
	public String latitude;

	@Column(name = "longitude")
	public String longitude;

	public DojoDeliveryItem(){
		super();
	}
	
	public DojoDeliveryItem(int belum_ada_perasaan_ke_server,
			int iDP_Delivery_Detail_List, int iDP_Delivery_Detail,
			int iD_Barang_Detail, int iD_Status_Detail, String kode_barcode,
			String kode_barcode_awal, String keterangan,
			String keterangan_awal, int status_data) {
		super();
		this.belum_ada_perasaan_ke_server = belum_ada_perasaan_ke_server;
		IDP_Delivery_Detail_List = iDP_Delivery_Detail_List;
		IDP_Delivery_Detail = iDP_Delivery_Detail;
		ID_Barang_Detail = iD_Barang_Detail;
		ID_Status_Detail = iD_Status_Detail;
		this.kode_barcode = kode_barcode;
		this.kode_barcode_awal = kode_barcode_awal;
		this.keterangan = keterangan;
		this.keterangan_awal = keterangan_awal;
		this.status_data = status_data;
	}

	public DojoDeliveryItem(int belum_ada_perasaan_ke_server,
			int iDP_Delivery_Detail_List, int iDP_Delivery_Detail,
			int iD_Barang_Detail, int iD_Barang, int iD_Status_Detail,
			String kode_barcode, String kode_barcode_awal, String keterangan,
			String keterangan_awal, int status_data, String latitude,
			String longitude) {
		super();
		this.belum_ada_perasaan_ke_server = belum_ada_perasaan_ke_server;
		IDP_Delivery_Detail_List = iDP_Delivery_Detail_List;
		IDP_Delivery_Detail = iDP_Delivery_Detail;
		ID_Barang_Detail = iD_Barang_Detail;
		ID_Barang = iD_Barang;
		ID_Status_Detail = iD_Status_Detail;
		this.kode_barcode = kode_barcode;
		this.kode_barcode_awal = kode_barcode_awal;
		this.keterangan = keterangan;
		this.keterangan_awal = keterangan_awal;
		this.status_data = status_data;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public void doSave(){
		this.save();
	}
}