package callcenter

class MotivosnoAceptaSeguro {
    String nombre
    boolean isActive

    static constraints = {
        nombre nullable: true
    }

    static mapping = {
        version false
    }
}
