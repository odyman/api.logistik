package co.id.exml.logistikdr.dojo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "DojoUser")
public class DojoUser extends Model {

	@Column(name = "username")
    public String username;

	@Column(name = "token")
    public String token;

	@Column(name = "nip")
    public String nip;

	@Column(name = "type")
    public int type;

	public DojoUser(){
		super();
	}

	public DojoUser(String username, String token, String nip, int type) {
		super();
		this.username = username;
		this.token = token;
		this.nip = nip;
		this.type = type;
	}
	
}
