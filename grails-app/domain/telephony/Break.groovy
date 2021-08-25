package telephony

class Break {
	
	String name
	String description
	String status
	String type

    static constraints = {
		name nullable: true
		description nullable: true
		status nullable: true
		type nullable: true
    }
	
	static mapping = {
		version false
		datasource 'call'
	  }
}
