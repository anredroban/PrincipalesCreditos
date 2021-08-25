package callcenter

class Agencia {
    String nombre
    Ciudad ciudad
    String direccionAgencia
    String asesorAgencia
    String tipoPersonaAgencia
    String correoPersonaAgencia
    static constraints = {
        nombre nullable: true
        direccionAgencia nullable: true
        asesorAgencia nullable: true
        tipoPersonaAgencia nullable: true
        correoPersonaAgencia nullable: true
    }
    static mapping = {
        version false
    }
}
