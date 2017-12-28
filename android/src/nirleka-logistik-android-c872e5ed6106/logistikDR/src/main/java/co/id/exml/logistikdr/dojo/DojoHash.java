package co.id.exml.logistikdr.dojo;

import co.id.exml.logistikdr.sikuel.SikuelPojo;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "DojoHash")
public class DojoHash extends SikuelPojo {

	@Column(name = "key")
    public String key;

	@Column(name = "val")
    public String val;

	public DojoHash(){
		super();
	}
	
	public DojoHash( String key, String val ) {
		super();
		this.key = key;
		this.val = val;
	}

	public void doSave(){
		this.save();
	}
}