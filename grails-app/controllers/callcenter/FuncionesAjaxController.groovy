package callcenter

import grails.converters.JSON
import grails.util.Holders
import groovy.json.JsonBuilder

import com.pw.security.*

import jxl.Sheet
import jxl.Workbook
import org.hibernate.SessionFactory
//import org.springframework.web.context.request.RequestContextHolder
//import org.springframework.web.context.request.ServletRequestAttributes
import utilitarios.Util
import groovy.time.TimeCategory

class FuncionesAjaxController {

    def getDisponiblesAsignacion() {
        int disponibles
        Rol rol = Rol.findByNombre("OPERADOR")

        if (params.tipoAsignacion.toString().equalsIgnoreCase("normal")) {
            if (params.tipoRegistros.toString().equalsIgnoreCase("SIN GESTION")) {
                disponibles = Clientes.executeQuery("from Clientes where intentos = 0 and nombreBase = :nombreBase and usuario.rol != :rol", [nombreBase: params.nombreBase, rol: rol]).size()
            } else {
                disponibles = Clientes.executeQuery("from Clientes where subestadoGestion.type = 'Regestionable' and nombreBase = :nombreBase and usuario.rol != :rol", [nombreBase: params.nombreBase, rol: rol]).size()
            }
        }
        render disponibles
    }

    def calcularDisponiblesAsignar(){
        String nombreBase = params.nombreBase
        String tipoRegistros = params.tipoRegistros
        String subestado = params.subestado
        String easyDesde = params.easyDesde
        String easyHasta = params.easyHasta
        String intentosDesde = params.intentosDesde
        String intentosHasta = params.intentosHasta
        String sql = ""
        String condicionEasyDesde = ""
        String condicionEasyHasta = ""
        Rol rol = Rol.findByNombre("OPERADOR")
        def condiciones = [nombreBase: nombreBase, rol: rol]


        if(easyDesde != ""){
            condicionEasyDesde = "and cast(codigoAsignacion as integer) >= :easyDesde"
            condiciones.put("easyDesde", easyDesde.toInteger())
        }

        if(easyHasta != ""){
            condicionEasyHasta = "and cast(codigoAsignacion as integer) <= :easyHasta"
            condiciones.put("easyHasta", easyHasta.toInteger())
        }


        if(tipoRegistros.equalsIgnoreCase("SIN GESTION")){
            sql = "from Clientes where usuario.rol != :rol and intentos = 0 and nombreBase = :nombreBase and plataforma != 'PURE CLOUD' ${condicionEasyDesde} ${condicionEasyHasta}"
        }else{
            String condicionIntentosDesde = ""
            String condicionIntentosHasta = ""
            String condicionSubestado = ""
            if(intentosDesde != ""){
                condicionIntentosDesde = "and intentos >= :intentosDesde"
                condiciones.put("intentosDesde", intentosDesde.toInteger())
            }
            if(intentosHasta != ""){
                condicionIntentosHasta = "and intentos <= :intentosHasta"
                condiciones.put("intentosHasta", intentosHasta.toInteger())
            }
            if(tipoRegistros.equalsIgnoreCase("RELLAMADAS")){
                sql = "from Clientes where subestadoGestion.type = 'Rellamada' and usuario.rol != :rol and nombreBase = :nombreBase and intentos != 0 and agendamientoAsesor = 'AGENDAR PARA CUALQUIERA' and plataforma != 'PURE CLOUD' ${condicionEasyDesde} ${condicionEasyHasta} ${condicionIntentosDesde} ${condicionIntentosHasta}"
            }else{
                if(subestado != ""){
                    condicionSubestado = "and subSubEstado = :subestado"
                    condiciones.put("subestado", subestado)
                }

                sql = "from Clientes where subestadoGestion.type = 'Regestionable' and usuario.rol != :rol and nombreBase = :nombreBase and intentos != 0 and plataforma != 'PURE CLOUD' ${condicionEasyDesde} ${condicionEasyHasta} ${condicionIntentosDesde} ${condicionIntentosHasta} ${condicionSubestado}"
            }


        }
        int cantidad = Clientes.executeQuery(sql, condiciones).size()
        render cantidad
    }

    def getSubestados() {

        if (params.id) {
            Estado estado = Estado.findById(params.id)
            def subestados = Subestado.executeQuery("from Subestado where estado = :estado and nombre != 'SIN GESTION'", [estado: estado]);
            def array = [subestados.id, subestados.nombre];
            render array as JSON;
        }
    }

    def getCiudades() {

        if (params.id) {
            Provincia provincia = Provincia.findById(params.id)
            def ciudades = Ciudad.executeQuery("from Ciudad where provincia = :provincia and isEnable = true or isEnable = false order by nombre", [provincia: provincia])
            def array = [ciudades.id, ciudades.nombre]
            render array as JSON
        }
    }
    def getCiudadesEntrega() {

        if (params.id) {
            Provincia provincia = Provincia.findById(params.id)
            def ciudades = Ciudad.executeQuery("from Ciudad where provincia = :provincia and isEnable = true order by nombre", [provincia: provincia])
            def array = [ciudades.id, ciudades.nombre]
            render array as JSON
        }
    }

    def getCiudadesFvt() {

        if (params.id) {
            Provincia provincia = Provincia.findById(params.id)
            def ciudades = AgenciaFvt.executeQuery("from AgenciaFvt where provincia = :provincia order by nombreCiudad", [provincia: provincia])
            def array = [ciudades.nombreCiudad, ciudades.nombreCiudad]
            render array as JSON
        }
    }

    def getEstadosTelefono(){
        if(params.id) {
            Estado status = Estado.findById(params.id)
            def subStatus = EstadosTelefono.executeQuery("from EstadosTelefono where estado = :status and isActive = true", [status: status])
            def array = [subStatus.nombre, subStatus.nombre]
            render array as JSON
        }
    }

    def getCantones() {
        if (params.id) {
            Provincia provincia = Provincia.findById(params.id)
            def cantones = Canton.executeQuery("from Canton where provincia = :provincia order by nombre", [provincia: provincia])
            def array = [cantones.id, cantones.nombre]
            render array as JSON
        }
    }

    def getParroquias() {
        if (params.id) {
            Canton canton = Canton.findById(params.id)
            def parroquias = Parroquia.executeQuery("from Parroquia where canton = :canton order by nombre", [canton: canton])
            def array = [parroquias.id, parroquias.nombre]
            render array as JSON
        }
    }
//Para las parroquias de las agencias
    def getParroquiasAgencias() {
        if (params.id) {
            Ciudad ciudad = Ciudad.findById(params.id)
            def parroquia = Agencia.executeQuery("from Agencia where ciudad = :ciudad", [ciudad: ciudad])
            def array = [parroquia.id, parroquia.nombre]
            render array as JSON
        }
    }

    //Para las parroquias de las agencias
    def getAgencias() {
        if (params.id) {
            ParroquiaAgencia parroquiaAgencia = ParroquiaAgencia.findById(params.id)
            def agencia = Agencia.executeQuery("from Agencia where parroquiaAgencia = :parroquiaAgencia", [parroquiaAgencia: parroquiaAgencia])
            def array = [agencia.id, agencia.nombre]
            render array as JSON
        }
    }

    def getDBTypes() {

        def dbType = Clientes.withCriteria {
            projections {
                distinct("ingreso")
                eq("nombreBase", params.dbName)
            }
        }
        print "TIPOS DE BASE: " + dbType
        render dbType as JSON
    }

    def getSubestadosRellamada() {
        def subestados = Subestado.withCriteria {
            projections {
                distinct("nombre")
                eq("rellamar", "SI")
                ne("nombre", "RELLAMADA")
            }
        }
        print "TIPOS DE SUBESTADOS: " + subestados
        render subestados as JSON
    }

    def getRellamadaSize() {
        print params.subestado
        def dbSizeOld = Clientes.withCriteria {
            subestadoGestion {
                eq("rellamar", "SI")
                eq("nombre", params.subestado)
            }
            usuario {
                rol {
                    ne('nombre', 'OPERADOR')
                }
            }
            order('intentos', 'desc')
            gt('intentos', 0)
            eq("nombreBase", params.dbName)
            eq("ingreso", params.dbType)
        }
        render dbSizeOld.size()
    }

    def getDBSize() {
        print params.dbName
        print params.dbType
        def dbSizeOld = Clientes.withCriteria {
            subestadoGestion {
                eq("rellamar", "SI")
                ne("nombre", "RELLAMADA")
            }
            usuario {
                rol {
                    ne('nombre', 'OPERADOR')
                }
            }
            order('intentos', 'desc')
            gt('intentos', 0)
            eq("nombreBase", params.dbName)
            eq("ingreso", params.dbType)
        }

        def dbSizeNew = Clientes.withCriteria {
            subestadoGestion {
                eq("rellamar", "SI")
            }
            usuario {
                rol {
                    ne('nombre', 'OPERADOR')
                }
            }
            eq('intentos', 0)
            eq("nombreBase", params.dbName)
            eq("ingreso", params.dbType)
        }
        print dbSizeNew.size()
        render dbSizeNew.size() + "," + dbSizeOld.size()
    }

    def getGraficoVentasHora() {

        Date fechaInicio = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 00:00:00"))
        Date fechaFin = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 23:59:59"))
        def subestados = Subestado.findByType("Exitoso")
        def result = [:]
        def ventasPorHora = Clientes.executeQuery("select cast(substring(fechaGestion, 12, 2) as int) as dia, count(*) as cantidad from Clientes where subestadoGestion in (:subestados) and fechaGestion between :fechaInicio and :fechaFin group by cast(substring(fechaGestion, 12, 2) as int) order by dia", [subestados: subestados, fechaInicio: fechaInicio, fechaFin: fechaFin])
        def contactadosPorHora = Clientes.executeQuery("select cast(substring(fechaGestion, 12, 2) as int) as dia, count(*) as cantidad from Clientes where estadoGestion = 'CONTACTADO' and fechaGestion between :fechaInicio and :fechaFin group by cast(substring(fechaGestion, 12, 2) as int) order by dia", [fechaInicio: fechaInicio, fechaFin: fechaFin])
        for (int i = 0; i < contactadosPorHora.size(); i++) {
            int cantidadVentas = 0;
            for (int j = 0; j < ventasPorHora.size(); j++) {
                if (ventasPorHora[j][0] == contactadosPorHora[i][0]) {
                    cantidadVentas = ventasPorHora[j][1];
                    break;
                }
            }
            result.put(contactadosPorHora[i][0], ['contactados': contactadosPorHora[i][1], 'ventas': cantidadVentas]);
        }
        render result as JSON;

    }

   //Prueba andres
    /*def getGrafContVsNocont() {

        Date fechaInicio = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 00:00:00"))
        Date fechaFin = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 23:59:59"))
        def result = [:]
        def mapaContactado = [:]
        def mapaNoContactado = [:]
        def consulta = Clientes.executeQuery("select estadoGestion, count(*) from Clientes where fechaGestion between :fechaInicio and :fechaFin group by estadoGestion", [fechaInicio: fechaInicio, fechaFin: fechaFin])
        def detalleContactados = Clientes.executeQuery("select subestadoGestion.nombre, count(*) from Clientes where fechaGestion between :fechaInicio and :fechaFin and estadoGestion = 'CONTACTADO' group by subestadoGestion.nombre", [fechaInicio: fechaInicio, fechaFin: fechaFin])
        def detalleNoContactados = Clientes.executeQuery("select subestadoGestion.nombre, count(*) from Clientes where fechaGestion between :fechaInicio and :fechaFin and estadoGestion = 'NO CONTACTADO' group by subestadoGestion.nombre", [fechaInicio: fechaInicio, fechaFin: fechaFin])

        for (int i = 0; i < consulta.size; i++) {
            if (consulta[i][0] == 'CONTACTADO') {
                result.put('Contactado', consulta[i][1])
            } else {
                result.put('NoContactado', consulta[i][1])
            }
        }

        for (int i = 0; i < detalleContactados.size(); i++) {
            mapaContactado.put(detalleContactados[i][0], detalleContactados[i][1])
        }

        for (int i = 0; i < detalleNoContactados.size(); i++) {
            mapaNoContactado.put(detalleNoContactados[i][0], detalleNoContactados[i][1])
        }

        result.put('DetalleContactados', mapaContactado)
        result.put('DetalleNoContactados', mapaNoContactado)


        render result as JSON
    }*/

    def getGrafContVsNocont() {

        Date fechaInicio = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 00:00:00"))
        Date fechaFin = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 23:59:59"))
        def result = [:]
        def consulta = Historial.executeQuery("select estadoGestion, count(*) from Historial where fechaGestion between :fechaInicio and :fechaFin group by estadoGestion", [fechaInicio: fechaInicio, fechaFin: fechaFin])
        //def detalleContactados = Clientes.executeQuery("select subestadoGestion.nombre, count(*) from Clientes where fechaGestion between :fechaInicio and :fechaFin and estadoGestion = 'CONTACTADO' group by subestadoGestion.nombre", [fechaInicio: fechaInicio, fechaFin: fechaFin])
        //def detalleNoContactados = Clientes.executeQuery("select subestadoGestion.nombre, count(*) from Clientes where fechaGestion between :fechaInicio and :fechaFin and estadoGestion = 'NO CONTACTADO' group by subestadoGestion.nombre", [fechaInicio: fechaInicio, fechaFin: fechaFin])

        for (int i = 0; i < consulta.size; i++) {
            result.put(consulta[i][0], consulta[i][1])
        }
        render result as JSON
    }


    def grafSubSubCu1() {

        Date fechaInicio = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 00:00:00"))
        Date fechaFin = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 23:59:59"))
        def result = [:]
        Subestado subestados = Subestado.findById(1)
        def motivos = Clientes.executeQuery("select subSubEstado, count(*) from Clientes where fechaGestion between :fechaInicio and :fechaFin and subestadoGestion in (:subestados) group by subSubEstado", [fechaInicio: fechaInicio, fechaFin: fechaFin, subestados: subestados])
        for (int i = 0; i < motivos.size(); i++) {
            result.put(motivos[i][0], motivos[i][1])
        }
        render result as JSON
    }

    def grafSubSubCu2() {

        Date fechaInicio = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 00:00:00"))
        Date fechaFin = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 23:59:59"))
        def result = [:]
        Subestado subestados = Subestado.findById(2)
        def motivos = Clientes.executeQuery("select subSubEstado, count(*) from Clientes where fechaGestion between :fechaInicio and :fechaFin and subestadoGestion in (:subestados) group by subSubEstado", [fechaInicio: fechaInicio, fechaFin: fechaFin, subestados: subestados])
        for (int i = 0; i < motivos.size(); i++) {
            result.put(motivos[i][0], motivos[i][1])
        }
        render result as JSON
    }

    def grafSubSubCu3() {

        Date fechaInicio = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 00:00:00"))
        Date fechaFin = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 23:59:59"))
        def result = [:]
        Subestado subestados = Subestado.findById(3)
        def motivos = Clientes.executeQuery("select subSubEstado, count(*) from Clientes where fechaGestion between :fechaInicio and :fechaFin and subestadoGestion in (:subestados) group by subSubEstado", [fechaInicio: fechaInicio, fechaFin: fechaFin, subestados: subestados])
        for (int i = 0; i < motivos.size(); i++) {
            result.put(motivos[i][0], motivos[i][1])
        }
        render result as JSON
    }

    def grafSubSubCu5() {

        Date fechaInicio = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 00:00:00"))
        Date fechaFin = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 23:59:59"))
        def result = [:]
        Subestado subestados = Subestado.findById(5)
        def motivos = Clientes.executeQuery("select subSubEstado, count(*) from Clientes where fechaGestion between :fechaInicio and :fechaFin and subestadoGestion in (:subestados) group by subSubEstado", [fechaInicio: fechaInicio, fechaFin: fechaFin, subestados: subestados])
        for (int i = 0; i < motivos.size(); i++) {
            result.put(motivos[i][0], motivos[i][1])
        }
        render result as JSON
    }

    def grafSubSubCu6() {

        Date fechaInicio = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 00:00:00"))
        Date fechaFin = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 23:59:59"))
        def result = [:]
        Subestado subestados = Subestado.findById(6)
        def motivos = Clientes.executeQuery("select subSubEstado, count(*) from Clientes where fechaGestion between :fechaInicio and :fechaFin and subestadoGestion in (:subestados) group by subSubEstado", [fechaInicio: fechaInicio, fechaFin: fechaFin, subestados: subestados])
        for (int i = 0; i < motivos.size(); i++) {
            result.put(motivos[i][0], motivos[i][1])
        }
        render result as JSON
    }

    def grafSubSubNu1() {

        Date fechaInicio = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 00:00:00"))
        Date fechaFin = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 23:59:59"))
        def result = [:]
        Subestado subestados = Subestado.findById(8)
        def motivos = Clientes.executeQuery("select subSubEstado, count(*) from Clientes where fechaGestion between :fechaInicio and :fechaFin and subestadoGestion in (:subestados) group by subSubEstado", [fechaInicio: fechaInicio, fechaFin: fechaFin, subestados: subestados])
        for (int i = 0; i < motivos.size(); i++) {
            result.put(motivos[i][0], motivos[i][1])
        }
        render result as JSON
    }


    def grafSubSubNu2() {

        Date fechaInicio = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 00:00:00"))
        Date fechaFin = Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 23:59:59"))
        def result = [:]
        Subestado subestados = Subestado.findById(9)
        def motivos = Clientes.executeQuery("select subSubEstado, count(*) from Clientes where fechaGestion between :fechaInicio and :fechaFin and subestadoGestion in (:subestados) group by subSubEstado", [fechaInicio: fechaInicio, fechaFin: fechaFin, subestados: subestados])
        for (int i = 0; i < motivos.size(); i++) {
            result.put(motivos[i][0], motivos[i][1])
        }
        render result as JSON
    }

    //Funciones para los reportes

    def getGraficoVentasHoraReporte() {

        Date fechaInicio = Date.parse("yyyy-MM-dd HH:mm:ss", params.fechaInicio);
        Date fechaFin = Date.parse("yyyy-MM-dd HH:mm:ss", params.fechaFin);
        def result = [:];
        Subestado subestado = Subestado.findByNombre("ACEPTA");
        def condicionesVentas = [subestado: subestado, fechaInicio: fechaInicio, fechaFin: fechaFin];
        def condicionesContactados = [fechaInicio: fechaInicio, fechaFin: fechaFin];
        String condicionUsuario = "";
        String condicionNombreBase = "";
        if (params.usuario != "") {
            Usuario usuario = Usuario.findById(params.usuario);
            condicionUsuario = "and usuario = :usuario";
            condicionesVentas.put("usuario", usuario);
            condicionesContactados.put("usuario", usuario);
        }
        if (params.nombreBase != "") {
            condicionNombreBase = "and nombreBase = :nombreBase";
            condicionesVentas.put("nombreBase", params.nombreBase);
            condicionesContactados.put("nombreBase", params.nombreBase);
        }

        String sqlVentas = "select cast(substring(fechaGestion, 12, 2) as int) as dia, count(*) as cantidad from Clientes where subestadoGestion = :subestado and fechaGestion between :fechaInicio and :fechaFin ${condicionUsuario} ${condicionNombreBase} group by cast(substring(fechaGestion, 12, 2) as int) order by dia";
        String sqlContactados = "select cast(substring(fechaGestion, 12, 2) as int) as dia, count(*) as cantidad from Clientes where estadoGestion = 'CONTACTADO' and fechaGestion between :fechaInicio and :fechaFin ${condicionUsuario} ${condicionNombreBase} group by cast(substring(fechaGestion, 12, 2) as int) order by dia";

        def ventasPorHora = Clientes.executeQuery(sqlVentas, condicionesVentas);
        def contactadosPorHora = Clientes.executeQuery(sqlContactados, condicionesContactados);
        for (int i = 0; i < contactadosPorHora.size(); i++) {
            int cantidadVentas = 0;
            for (int j = 0; j < ventasPorHora.size(); j++) {
                if (ventasPorHora[j][0] == contactadosPorHora[i][0]) {
                    cantidadVentas = ventasPorHora[j][1];
                    break;
                }
            }
            result.put(contactadosPorHora[i][0], ['contactados': contactadosPorHora[i][1], 'ventas': cantidadVentas]);
        }
        render result as JSON;

    }

    def getGraficoVentasCiudadReporte() {

        Date fechaInicio = Date.parse("yyyy-MM-dd HH:mm:ss", params.fechaInicio);
        Date fechaFin = Date.parse("yyyy-MM-dd HH:mm:ss", params.fechaFin);
        Subestado subestado = Subestado.findByNombre("ACEPTA");
        def result = [:];
        def condiciones = [subestado: subestado, fechaInicio: fechaInicio, fechaFin: fechaFin];
        String condicionUsuario = "";
        String condicionNombreBase = "";
        if (params.usuario != "") {
            Usuario usuario = Usuario.findById(params.usuario);
            condicionUsuario = "and usuario = :usuario";
            condiciones.put("usuario", usuario);
        }
        if (params.nombreBase != "") {
            condicionNombreBase = "and nombreBase = :nombreBase";
            condiciones.put("nombreBase", params.nombreBase);
        }
        String sql = "select ciudad, count(*) from Clientes where subestadoGestion = :subestado and fechaGestion between :fechaInicio and :fechaFin and (ciudad = 'QUITO' or ciudad = 'GUAYAQUIL') ${condicionUsuario} ${condicionNombreBase} group by ciudad";
        def ventasCiudad = Clientes.executeQuery(sql, condiciones);
        for (int i = 0; i < ventasCiudad.size(); i++) {
            result.put(ventasCiudad[i][0], ventasCiudad[i][1]);
        }
        render result as JSON;
    }

    def getGraficoEstadosReporte() {

        Date fechaInicio = Date.parse("yyyy-MM-dd HH:mm:ss", params.fechaInicio);
        Date fechaFin = Date.parse("yyyy-MM-dd HH:mm:ss", params.fechaFin);
        def result = [:]
        def mapaContactado = [:];
        def mapaNoContactado = [:];
        def condiciones = [fechaInicio: fechaInicio, fechaFin: fechaFin];
        String condicionUsuario = "";
        String condicionNombreBase = "";

        if (params.usuario != "") {
            Usuario usuario = Usuario.findById(params.usuario);
            condicionUsuario = "and usuario = :usuario";
            condiciones.put("usuario", usuario);
        }

        if (params.nombreBase != "") {
            condicionNombreBase = "and nombreBase = :nombreBase";
            condiciones.put("nombreBase", params.nombreBase);
        }

        String sqlConsulta = "select estadoGestion, count(*) from Clientes where fechaGestion between :fechaInicio and :fechaFin ${condicionUsuario} ${condicionNombreBase} group by estadoGestion";
        String sqlDetalleContactados = "select subestadoGestion.nombre, count(*) from Clientes where fechaGestion between :fechaInicio and :fechaFin and estadoGestion = 'CONTACTADO' ${condicionUsuario} ${condicionNombreBase} group by subestadoGestion.nombre";
        String sqlDetalleNoContactados = "select subestadoGestion.nombre, count(*) from Clientes where fechaGestion between :fechaInicio and :fechaFin and estadoGestion = 'NO CONTACTADO' ${condicionUsuario} ${condicionNombreBase} group by subestadoGestion.nombre";

        def consulta = Clientes.executeQuery(sqlConsulta, condiciones);
        def detalleContactados = Clientes.executeQuery(sqlDetalleContactados, condiciones);
        def detalleNoContactados = Clientes.executeQuery(sqlDetalleNoContactados, condiciones);

        for (int i = 0; i < consulta.size; i++) {
            if (consulta[i][0] == 'CONTACTADO') {
                result.put('Contactado', consulta[0][1]);
            } else {
                result.put('NoContactado', consulta[1][1]);
            }
        }

        for (int i = 0; i < detalleContactados.size(); i++) {
            mapaContactado.put(detalleContactados[i][0], detalleContactados[i][1])
        }

        for (int i = 0; i < detalleNoContactados.size(); i++) {
            mapaNoContactado.put(detalleNoContactados[i][0], detalleNoContactados[i][1])
        }

        result.put('DetalleContactados', mapaContactado);
        result.put('DetalleNoContactados', mapaNoContactado);

        render result as JSON;
    }

    /**
     * Esta funcion permite buscar las rellamadas que cada asesor tiene en el sistema
     * @author Esteban Preciado
     * @return
     */
    def rellamadas(){

        if(request.xhr){
            Usuario usuario
            if(!session.user){
                usuario = Usuario.findByNombre("Administrador") //Obtiene el usuario
            }else{
                usuario = Usuario.findById(session.user.id) //Obtiene el usuario
            }
            String consulta = "FROM Clientes WHERE subestadoGestion.type = 'Rellamada' AND fechaRellamada between :fechaInicioDia and :fecha AND usuario = :usuario order by fechaRellamada desc"
            def condiciones = ["usuario": usuario, fecha: new Date(), fechaInicioDia: Date.parse("yyyy-MM-dd HH:mm:ss", new Date().format("yyyy-MM-dd 00:00:00"))];
            def rellamadas = Clientes.executeQuery(consulta, condiciones);
            render rellamadas as JSON
        }
    }

    /**
     * Guarda el dato del campo editado de una tabla
     * @author Esteban Preciado
     * @return
     */
    def guardarEditado() {
        if (request.xhr) {
            def campo = params.campo
            def cliente = Clientes.findById(params.idCliente.toString().toInteger())
            cliente."${campo}" = params.valor //Guarda en la variable campo el nombre del atributo

            if (!cliente.save(flush: true)) {
                println cliente.errors

            }
            render true
        }
    }

    def uploadDB(){
        Usuario user = Usuario.findById(1)
        def path = params.path
        def data = new HashMap(JSON.parse(params.data))
        File file= new File(path)
        String[] fields = Clientes.getFields()
        try {
            Workbook workbook = Workbook.getWorkbook(file)
            Sheet sheet = workbook.getSheet(0)
            int rows = sheet.getRows()
            int cont = 0
            for (int i = 1; i < rows; i++){
                if(cont == 499){
                    SessionFactory sessionFactory = Holders.applicationContext.sessionFactory
                    sessionFactory.currentSession.clear()
                    cont = 0
                }
                Clientes cliente = new Clientes()
                for (int j = 0; j < fields.length; j++){
                    if(data[fields[j]] != 0){
                        cliente[fields[j]] = sheet.getCell(data[fields[j]]-1, i).getContents()
                    }
                }
                cliente.intentos = 0
                cliente.nombreBase = params.dbName
                cliente.nombreVendedor = "Administrador"
                cliente.usuario = user
                cliente.isActive = 1
                if(!cliente.plataforma)
                    cliente.plataforma = "PLUS WIRELESS"
                if(!cliente.save(flush: true)){
                    println cliente.errors
                }
                cont++
            }
            if(cont < 499){
                SessionFactory sessionFactory = Holders.applicationContext.sessionFactory
                sessionFactory.currentSession.clear()
            }
            render "Base cargada"
        }catch (IOException ex){
            throw new RuntimeException("Error in reading Excel file: "+ex)
            render false
        }
    }

    /*def getGrafLineas(){
        Date fechaInicio = Date.parse("yyyy-MM-dd HH:mm:ss", params.fechaInicio.toString())
        Date fechaFin = Date.parse("yyyy-MM-dd HH:mm:ss", params.fechaFin.toString())
        //Subestado subestados = Subestado.findByType("Exitoso")
        Subestado subestados = Subestado.findById(1)
        Subestado subestados2 = Subestado.findById(3)
        //def adicionales = Adicional.executeQuery("select date(cliente.fechaGestion), count(*) from Adicional where cliente.fechaGestion between :fechaInicio and :fechaFin and cliente.subestadoGestion in (:subestados) group by date(cliente.fechaGestion) order by date(cliente.fechaGestion)", [fechaInicio: fechaInicio, fechaFin: fechaFin, subestados: subestados])
        def adicionales = Clientes.executeQuery("select date(fechaGestion), count(*) from Clientes where fechaGestion between :fechaInicio and :fechaFin and subestadoGestion in (:subestados) group by date(fechaGestion) order by date(fechaGestion)", [fechaInicio: fechaInicio, fechaFin: fechaFin, subestados: subestados])
        def adicionales2 = Clientes.executeQuery("select date(fechaGestion), count(*) from Clientes where fechaGestion between :fechaInicio and :fechaFin and subestadoGestion in (:subestados2) group by date(fechaGestion) order by date(fechaGestion)", [fechaInicio: fechaInicio, fechaFin: fechaFin, subestados2:subestados2])
        def result = [:]
        def datoCu1 = [:]
        result.put(Date.parse("yyyy-MM-dd", params.fechaInicio.toString()).format("yyyy-MM-dd"), 0)
        use(TimeCategory){
            Date fecha = Date.parse("yyyy-MM-dd", params.fechaInicio.toString())
            while(fecha < Date.parse("yyyy-MM-dd", params.fechaFin.toString())){
                fecha = fecha + 1.days
                result.put(fecha.format("yyyy-MM-dd"), 0)
            }
        }
        for (int i = 0; i < adicionales.size(); i++) {
            int cantidadVentas = 0;
            for (int j = 0; j < adicionales2.size(); j++) {
                if (adicionales2[j][0] == adicionales[i][0]) {
                    cantidadVentas = adicionales2[j][1];
                    break;
                }
           }
            result.put(adicionales[i][0], ['contactados': adicionales[i][1], 'ventas': cantidadVentas]);
        }

        render result as JSON
    }*/


    def getGrafLineas(){
        Date fechaInicio = Date.parse("yyyy-MM-dd HH:mm:ss", params.fechaInicio.toString())
        Date fechaFin = Date.parse("yyyy-MM-dd HH:mm:ss", params.fechaFin.toString())
        //Subestado subestados = Subestado.findByType("Exitoso")
        Subestado subestados = Subestado.findById(1)
        Subestado subestados2 = Subestado.findById(3)
        //def adicionales = Adicional.executeQuery("select date(cliente.fechaGestion), count(*) from Adicional where cliente.fechaGestion between :fechaInicio and :fechaFin and cliente.subestadoGestion in (:subestados) group by date(cliente.fechaGestion) order by date(cliente.fechaGestion)", [fechaInicio: fechaInicio, fechaFin: fechaFin, subestados: subestados])
        def adicionales = Clientes.executeQuery("select date(fechaGestion), count(*) from Clientes where fechaGestion between :fechaInicio and :fechaFin and subestadoGestion in (:subestados, :subestados2) group by date(fechaGestion) order by date(fechaGestion)", [fechaInicio: fechaInicio, fechaFin: fechaFin, subestados: subestados, subestados2:subestados2])
        def result = [:]
        result.put(Date.parse("yyyy-MM-dd", params.fechaInicio.toString()).format("yyyy-MM-dd"), 0)
        use(TimeCategory){
            Date fecha = Date.parse("yyyy-MM-dd", params.fechaInicio.toString())
            while(fecha < Date.parse("yyyy-MM-dd", params.fechaFin.toString())){
                fecha = fecha + 1.days
                result.put(fecha.format("yyyy-MM-dd"), 0)
            }
        }
        for(dato in result){
            for(int i = 0; i < adicionales.size(); i++){
                if(adicionales[i][0].toString() == dato.key.toString()){
                    dato.value = dato.value + adicionales[i][1]
                }
            }
        }
        render result as JSON
    }

    def searchUser(){
        if(params.query){

            def seacher = params.query



            def result =  Adicional.executeQuery("select new map (ad.cedula as cedula, ad.nombre1 as name, ad.apellido1 as  surname) from Adicional  as ad where ad.cedula = :valDest", [valDest: seacher])

            if (result.isEmpty()){

                render null
            }else{

                render result
            }
        }
    }

    def addAdicionalToList(){

            Adicional adicional = new Adicional()
            String nombreTarjeta = ""
            Clientes cliente = Clientes.findById(params.idCliente.toLong())
            Usuario usuario = Usuario.findById(session.user.id)
            if(params.nombreTarjeta == "1")
                nombreTarjeta = formatearNombreTarjeta(formatearTexto(params.primerNombre.toString()), formatearTexto(params.primerApellido.toString()))
            if(params.nombreTarjeta == "2")
                nombreTarjeta = formatearNombreTarjeta(formatearTexto(params.segundoNombre.toString()), formatearTexto(params.primerApellido.toString()))

            adicional.cliente = cliente
            adicional.cedula = params.cedula
            adicional.apellido1 = formatearTexto(params.primerApellido.toString())
            adicional.apellido2 = formatearTexto(params.segundoApellido.toString())
            adicional.nombre1 = formatearTexto(params.primerNombre.toString())
            adicional.nombre2 = formatearTexto(params.segundoNombre.toString())
            adicional.nombreTarjeta = nombreTarjeta
            adicional.cupo = params.cupoOtorgado
            adicional.fechaNacimiento = Util.formatearFechaNacimiento(params.fechaNacimiento)
            adicional.sexo = params.sexo
            adicional.parentesco = params.parentesco
            adicional.estadoCivil = params.estadoCivil
            adicional.nacionalidad = formatearTexto(params.nacionalidad.toString())
            adicional.save(flush: true)
            render true
    }

    private String formatearTexto(String entrada){
        return entrada.toUpperCase().replace('Ñ', 'N').replace('-', ' ').replace('Á', 'A').replace('É', 'E').replace('Í', 'I').replace('Ó', 'O').replace('Ú', 'U')
    }

    def getCantidadAdicionales(){
        render GestionController.adicionalesArrayList.size()
//        render "Hola"
    }

    private String formatearNombreTarjeta(String nombre, String apellido){
        String salida
        int tamNombre = nombre.length()
        int tamApellido = apellido.length()
        if(tamNombre+tamApellido <= 18){
            salida = nombre + " " + apellido
        }else{
            int sobrante = 18-tamApellido
            salida = nombre.substring(0, sobrante) + " " + apellido
        }
        return salida
    }

    def getSubStatusByStatus(){
        if(params.id) {
            Estado status = Estado.findById(params.id)
            def subStatus = Subestado.executeQuery("from Subestado where estado = :status and isActive = true", [status: status])
            def array = [subStatus.id, subStatus.nombre]
            render array as JSON
        }
    }

    def getSubSubStatusBySubStatus(){
        Subestado subStatus = Subestado.findById(params.id)
        def subSubStatus = SubSubestado.executeQuery("from SubSubestado where subestado = :subStatus and isActive = true", [subStatus: subStatus])
        def array = [subSubStatus.id, subSubStatus.name, subStatus.type, subStatus.enableManagement]
        render array as JSON
    }

    def getExtraeSubSubStatus(){
        def subStatus = params.id.toLong();
        def subSubStatus = SubSubestado.executeQuery("from SubSubestado where id = :subStatus", [subStatus: subStatus])
        def array =  subSubStatus.name
        render array as JSON
    }

    def geMotivoSubStatusBySubSubStatus(){
        SubSubestado SubsubStatus = SubSubestado.findById(params.id)
        def motivoSubSubStatus = MotivoSubEstado.executeQuery("from MotivoSubEstado where subSubestado = :SubsubStatus and isActive = true", [SubsubStatus: SubsubStatus])
        def array = [motivoSubSubStatus.id, motivoSubSubStatus.nombre]
        render array as JSON
    }

    def inhabilitarBases(){
        ArrayList<String> bases = new ArrayList<>()
        String[] basesArray = params.bases.split(',')
        for(int i = 0; i < basesArray.length; i++){
            bases.add(basesArray[i])
        }
        Clientes.executeUpdate("update Clientes set isActive = false where nombreBase in (:bases)", [bases: bases])
        Util.saveLog(session.user.id.toString().toLong(), "Se ha inhabilitado base(s) ${params.bases}")
        render "OK"
    }

    def habilitarBases(){
        ArrayList<String> bases = new ArrayList<>()
        String[] basesArray = params.bases.split(',')
        for(int i = 0; i < basesArray.length; i++){
            bases.add(basesArray[i])
        }
        Clientes.executeUpdate("update Clientes set isActive = true where nombreBase in (:bases)", [bases: bases])
        Util.saveLog(session.user.id.toString().toLong(), "Se ha habilitado base(s) ${params.bases}")
        render "OK"
    }

}
