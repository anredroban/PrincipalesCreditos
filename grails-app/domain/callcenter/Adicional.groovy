package callcenter

import com.pw.security.Usuario

class Adicional {

    Clientes cliente
    String cedula
    String nombre1
    String nombre2
    String apellido1
    String apellido2
    String fechaNacimiento
    String sexo
    String cupo
    String parentesco
    String nombreTarjeta
    String nacionalidad
    String estadoCivil


    static constraints = {
        cliente nullable: true
        cedula nullable: true
        nombre1 nullable: true
        nombre2 nullable: true
        apellido1 nullable: true
        apellido2 nullable: true
        fechaNacimiento nullable: true
        sexo nullable: true
        cupo nullable: true
        parentesco nullable: true
        nombreTarjeta nullable: true
        nacionalidad nullable: true
        estadoCivil nullable: true
    }

    static mapping = {
        version false
    }
}
