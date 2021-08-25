package callcenter

class SubSubestado {

    String name
    boolean isActive
    Subestado subestado

    static constraints = {
        String name
        Subestado subestado
    }

    static mapping = {
        version false
    }
}
