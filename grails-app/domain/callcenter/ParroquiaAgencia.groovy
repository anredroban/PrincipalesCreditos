package callcenter

class ParroquiaAgencia {
    String nombre
    Ciudad ciudad

    static constraints = {
        nombre nullable: true
    }
    static mapping = {
        version false
    }
}
