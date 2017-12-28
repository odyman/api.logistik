package co.id.exml.logistikdr.dojo;

import co.id.exml.logistikdr.sikuel.SikuelPojo;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "DojoPickupJenisKirim")
public class DojoPickupJenisKirim extends SikuelPojo {

	@Column(name = "ID_Jns_kirim")
    public int ID_Jns_kirim;

	@Column(name = "jenis_kirim")
    public String jenis_kirim;

	@Column(name = "keterangan")
    public String keterangan;

	@Column(name = "status_data")
    public int status_data;

	public DojoPickupJenisKirim(){
		super();
	}
	
	public DojoPickupJenisKirim(int iD_Jns_kirim, String jenis_kirim, String keterangan, int status_data) {
		super();
		ID_Jns_kirim = iD_Jns_kirim;
		this.jenis_kirim = jenis_kirim;
		this.keterangan = keterangan;
		this.status_data = status_data;
	}

	public void doSave(){
		this.save();
	}
}