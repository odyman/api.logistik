package co.id.exml.logistikdr.dojo;

import co.id.exml.logistikdr.sikuel.SikuelPojo;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "DojoDeliveryOrder")
public class DojoDeliveryOrder extends SikuelPojo {

	@Column(name = "IDP_Delivery")
	public int IDP_Delivery;

	@Column(name = "tgl_delivery")
	public String tgl_delivery;

	@Column(name = "NIP_driver")
	public String NIP_driver;

	@Column(name = "NIP_asisten_driver")
	public String NIP_asisten_driver;

	@Column(name = "kode_kendaraan")
	public String kode_kendaraan;

	@Column(name = "kode_unker_delivery")
	public String kode_unker_delivery;

	@Column(name = "kode_kota_delivery")
	public String kode_kota_delivery;

	@Column(name = "kode_lokasi_delivery")
	public String kode_lokasi_delivery;

	@Column(name = "nama_lengkap_driver")
	public String nama_lengkap_driver;

	@Column(name = "nama_lengkap_asisten_driver")
	public String nama_lengkap_asisten_driver;

	@Column(name = "no_pol")
	public String no_pol;

	@Column(name = "nama_unker_delivery")
	public String nama_unker_delivery;

	@Column(name = "nama_kota_delivery")
	public String nama_kota_delivery;

	@Column(name = "nama_lokasi_delivery")
	public String nama_lokasi_delivery;

	@Column(name = "time_start")
	public String time_start;

	@Column(name = "time_end")
	public String time_end;

	@Column(name = "keterangan")
	public String keterangan;

	@Column(name = "ID_Status")
	public int ID_Status;

	@Column(name = "status")
	public String status;

	@Column(name = "periode")
	public String periode;

	@Column(name = "bulan")
	public String bulan;

	@Column(name = "nama_bulan")
	public String nama_bulan;

	@Column(name = "tahun")
	public String tahun;

	@Column(name = "status_data")
	public int status_data;

	@Column(notNull= false, name = "belum_ada_perasaan_ke_server")
    public int belum_ada_perasaan_ke_server = 0;

	public DojoDeliveryOrder(){
		super();
	}

	public DojoDeliveryOrder(int iDP_Delivery, String tgl_delivery,
			String nIP_driver, String nIP_asisten_driver,
			String kode_kendaraan, String kode_unker_delivery,
			String kode_kota_delivery, String kode_lokasi_delivery,
			String nama_lengkap_driver, String nama_lengkap_asisten_driver,
			String no_pol, String nama_unker_delivery,
			String nama_kota_delivery, String nama_lokasi_delivery,
			String time_start, String time_end, String keterangan,
			int iD_Status, String status, String periode, String bulan,
			String nama_bulan, String tahun, int status_data,
			int belum_ada_perasaan_ke_server) {
		super();
		IDP_Delivery = iDP_Delivery;
		this.tgl_delivery = tgl_delivery;
		NIP_driver = nIP_driver;
		NIP_asisten_driver = nIP_asisten_driver;
		this.kode_kendaraan = kode_kendaraan;
		this.kode_unker_delivery = kode_unker_delivery;
		this.kode_kota_delivery = kode_kota_delivery;
		this.kode_lokasi_delivery = kode_lokasi_delivery;
		this.nama_lengkap_driver = nama_lengkap_driver;
		this.nama_lengkap_asisten_driver = nama_lengkap_asisten_driver;
		this.no_pol = no_pol;
		this.nama_unker_delivery = nama_unker_delivery;
		this.nama_kota_delivery = nama_kota_delivery;
		this.nama_lokasi_delivery = nama_lokasi_delivery;
		this.time_start = time_start;
		this.time_end = time_end;
		this.keterangan = keterangan;
		ID_Status = iD_Status;
		this.status = status;
		this.periode = periode;
		this.bulan = bulan;
		this.nama_bulan = nama_bulan;
		this.tahun = tahun;
		this.status_data = status_data;
		this.belum_ada_perasaan_ke_server = belum_ada_perasaan_ke_server;
	}

	public void doSave(){
		this.save();
	}
}