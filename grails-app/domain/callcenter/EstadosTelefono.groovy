package callcenter

class EstadosTelefono {
	
	String nombre
	boolean isActive
	Estado estado


    static constraints = {
		isActive nullable: true
		nombre nullable: true
    }
	
	static mapping = {
		version false
	}
	
}
