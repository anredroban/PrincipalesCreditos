package telephony

import com.pw.security.Usuario;

class Extension {
	
	String number;
	Usuario user;

    static constraints = {
    }
	
	static mapping = {
		version false
		datasource 'crm'
	}
}
