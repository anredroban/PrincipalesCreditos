package telephony

import callcenter.Clientes
import callcenter.Historial

class LastUniqueId {

    String uniqueId
    Clientes client

    static constraints = {
        uniqueId nullable: true
        client nullable: true
    }

    static mapping = {
        version false
    }
}

