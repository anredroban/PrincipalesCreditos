package telephony

import com.pw.security.Usuario

class BreakTime {

	Date dateBreak
	int timeBreak
	String typeBreak
	Usuario user

    static constraints = {
    }
	
	static mapping = {
		version false
	  }
}
