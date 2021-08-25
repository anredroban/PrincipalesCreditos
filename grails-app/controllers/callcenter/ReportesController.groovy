package callcenter

//import javafx.scene.control.Cell
import jxl.Workbook
import jxl.WorkbookSettings
import jxl.format.Alignment
import jxl.format.Border
import jxl.format.BorderLineStyle
import jxl.format.Colour
import jxl.format.VerticalAlignment
import jxl.write.Label
import jxl.write.WritableFont
import jxl.write.WritableSheet
import jxl.write.WritableWorkbook
import telephony.Break
import telephony.BreakTime
import utilitarios.ExcelUtils


import com.pw.security.*
import groovy.sql.Sql
import org.hibernate.criterion.CriteriaSpecification
import utilitarios.Util

import java.text.DecimalFormat
import java.text.SimpleDateFormat

//import pl.touk.excel.export.WebXlsxExporter

class ReportesController {

	static beforeInterceptor = {
		String accion = actionUri;
		if(!accion.equals("/usuario/login") && !accion.equals("/usuario/logout")){
			if(!session.user){
				redirect(uri: "/usuario/login");
				return false;
			}else{
				boolean tienePermiso = utilitarios.Util.checkAccess(session.user.usuario, accion)
				if(!tienePermiso){
					render "No tienes permiso para ingresar a este sitio-> "+accion;
				}
			}
		}
	}


	/**
	 * @author Andres Redroban
	 * Genera trama de clientes de Tarjetas Principales BP
	 * @param
	 * @return
	 */

	def bitacoraClientes(){
		if(params.fechas){
			//Obtenemos los datos
			Date[] fechas = Util.formatearFechasReporte(params.fechas.toString())
			ArrayList<SubSubestado> subestados = Subestado.executeQuery("from Subestado where id in (1, 3)")
			def nombresBase = params.list("nombreBase")
			def condiciones = [fechaInicio: fechas[0], fechaFin: fechas[1], subestados: subestados/*, nombresBase: nombresBase*/]
			String sqlPrincipales = "from Clientes where subestadoGestion in (:subestados) and fechaGestion between :fechaInicio and :fechaFin and (subSubEstado = 'TDC' or subSubEstado = 'TDC+CREDITO')"
			def principalesList = Clientes.executeQuery(sqlPrincipales, condiciones)
			String direccionEntregaDomicilio = ""
			String direccionEntregaTrabajo = ""

			//Empezamos a crear y llenar el Excel
			def webrootDir = servletContext.getRealPath(grailsApplication.config.uploadFolder)
			File file = new File(webrootDir, "temporal.xls")
			WorkbookSettings workbookSettings = new WorkbookSettings()
			workbookSettings.setLocale(new Locale("es", "ES"))
			workbookSettings.setEncoding("UTF-8")
			WritableWorkbook workbook = Workbook.createWorkbook(file)
			workbook.createSheet("Clientes Latam", 0)
			WritableFont cellFont = new WritableFont(WritableFont.TAHOMA, 10)
			WritableFont cellFont2 = new WritableFont(WritableFont.createFont("Calibri"), 11)
			WritableSheet sheetPrincipales = workbook.getSheet(0)
			String[] headersPrincipales = ["CEDULA", "NOMBRE1", "NOMBRE2", "APELLIDO1", "APELLIDO2", "NACIONALIDAD", "PAIS DE NACIMIENTO",
										   "PROVINCIA NACIMIENTO", "CIUDAD NACIMIENTO", "FECHA NACIMIENTO", "TIPO TARJETA", "CUPO1",
										   "CUPO2", "CUPO3", "SITUACION LABORAL", "CALLE PRINCIPAL DOMICILIO", "NUMERO CASA DOMICILIO",
										   "CALLE SECUNDARIA DOMICILIO", "BARRIO/SECTOR DOMICILIO", "REFERENCIA DOMICILIO", "PARROQUIA DOMICILIO", "TELEFONO DOMICILIO",
										   "CELULAR", "CIUDAD DOMICILIO", "PROVINCIA DOMICILIO", "CANTON DOMICILIO", "EMAIL PERSONAL",
										   "CALLE PRINCIPAL TRABAJO", "NUMERO CASA TRABAJO", "CALLE SECUNDARIA TRABAJO", "BARRIO/SECTOR TRABAJO", "REFERENCIA TRABAJO",
										   "TELEFONO TRABAJO", "CIUDAD TRABAJO", "PROVINCIA TRABAJO", "CANTON TRABAJO", "PARROQUIA TRABAJO",
										   "EMAIL TRABAJO", "LUGAR ENTREGA TARJETA", "DIRECCION ENTREGA", "CIUDAD ENTREGA", "TELEFONO CONTACTO",
										   "HORA CONTACTO", "CAMPANA", "CALLCENTER", "EQUIPO FUTBOL PREFERENCIA", "NOMBRE DE LA PERSONA QUE PUEDE RECIBIR LA TARJETA",
										   "HORARIO DE ENTREGA TARJETA", "GENERO", "ESTADO CIVIL", "SEGURO DESGRAVAMEN", "SMS",
										   "NOMBRES DE REFERENCIA", "PAIS", "PROVINCIA", "CIUDAD", "TELEFONO",
										   "RELACION CON CLIENTE", "TIPO_CLIENTE", "NOMBRE NEGOCIO", "FEC. INI. NEGOCIO", "ACT. ECON.",
										   "VENTAS/HONORARIOS MEN.", "COSTO VENTAS", "GASTOS GEN. OPERATIVOS", "NOMBRE EMPRESA", "CONTRATO", "CARGO", "FEC. INI. TRAB. ACT.",
										   "SUELDO", "GASTOS FAMILIA", "TIP. VIVIENDA", "OBLIGADO CONTABILIDAD", "NOMBRE DE BASE ASIGNADA",
										   "OPCION TARJETA SELECCIONADA POR EL CLIENTE", "ESTADO DE CUENTA DIGITAL","CODIGOCAMPANIA", "FECHAGESTION","ASESOR VENTA", "TIPO ARCHIVO", "HORARIO VENTA",
										   "CREADAS NO CREADAS", "IMPUTABLE", "DETALLE IMPUTABLE", "FECHA ENVIO CREACION", "ID SISTEMA"]
			/*String[] headersAdicionales = ["CEDULA TITULAR", "CEDULA ADICIONAL", "NACIONALIDAD", "NOMBRE1 ADICIONAL", "NOMBRE2 ADICIONAL",
										 "APELLIDO1 ADICIONAL", "APELLIDO 2 ADICIONAL", "FECHA NACIMIENTO", "SEXO", "ESTADO CIVIL",
										 "CUPO", "PARENTESCO", "NOMBRE TARJETA"]*/
			ExcelUtils.addCells(headersPrincipales, sheetPrincipales, 0, Colour.GRAY_25, Alignment.LEFT, VerticalAlignment.CENTRE, cellFont, Border.ALL, BorderLineStyle.HAIR)

			for (int i = 0; i < principalesList.size(); i++){
				String[] campos = new String[headersPrincipales.length]
				Clientes princ = principalesList.get(i)
				campos[0] = princ.cedulaTitular
				campos[1] = princ.nombre1
				campos[2] = princ.nombre2
				campos[3] = princ.apellido1
				campos[4] = princ.apellido2
				campos[5] = princ.nacionalidad
				campos[6] = princ.paisNacimiento
				campos[7] = princ.provinciaNacimiento
				campos[8] = princ.ciudadNacimiento
				campos[9] = princ.fechaNacimientoGestion

				/*TARJETAS DE CREDITO LATAM*/
			/*	campos[10] = princ.prodConsumEsc1 //TIPO TARJETA
				campos[11] = princ.tarjetaEsc1    //CUPO 1
				campos[12] = princ.tarjetaEsc1   //CUPO 2
				campos[13] = princ.tarjetaEsc1  //CUPO 3*/

				if (princ.opcionTarjetaSeleccionada.toString().equalsIgnoreCase("OFERTA 1")){
					campos[10] = princ.prodConsumEsc1 //TIPO TARJETA
					campos[11] = princ.tarjetaEsc1    //CUPO 1
					campos[12] = princ.tarjetaEsc1   //CUPO 2
					campos[13] = princ.tarjetaEsc1  //CUPO 3
				}
				if (princ.opcionTarjetaSeleccionada.toString().equalsIgnoreCase("OFERTA 4")){
					campos[10] = princ.prodTarjetaExclusiva //TIPO TARJETA
					campos[11] = princ.tarjetaExclusiva    //CUPO 1
					campos[12] = princ.tarjetaExclusiva   //CUPO 2
					campos[13] = princ.tarjetaExclusiva  //CUPO 3
				}

				campos[14] = princ.situacionLaboral   //SITUACION LABORAL
				campos[15] = princ.callePrincipalDomicilio
				campos[16] = princ.numeroCasaDomicilio
				campos[17] = princ.calleSecundariaDomicilio
				campos[18] = "PRRQ " + princ.sectorDomicilio
				campos[19] = princ.referenciaDomicilio
				campos[20] = princ.parroquiaDomicilio
				campos[21] = princ.telefonoDomicilio
				campos[22] = princ.celularGestion
				campos[23] = princ.ciudadDomicilioGestion
				campos[24] = princ.provinciaDomicilioGestion
				campos[25] = princ.cantonDomicilio
				campos[26] = princ.emailPersonal
				campos[27] = princ.callePrincipalTrabajo
				campos[28] = princ.numeroCasaTrabajo
				campos[29] = princ.calleSecundariaTrabajo
				campos[30] = "PRRQ " + princ.sectorTrabajo
				campos[31] = princ.referenciaTrabajo
				campos[32] = princ.telefonoTrabajo
				campos[33] = princ.ciudadTrabajoGestion
				campos[34] = princ.provinciaTrabajoGestion
				campos[35] = princ.cantonTrabajo
				campos[36] = princ.parroquiaTrabajo
				campos[37] = ""
				campos[38] = princ.lugarEntrega
				if (princ.lugarEntrega.toString().equalsIgnoreCase("1")){
					direccionEntregaDomicilio = princ.callePrincipalDomicilio + ' ' + princ.numeroCasaDomicilio + ' ' + princ.calleSecundariaDomicilio
					if(direccionEntregaDomicilio.length() > 50){
						campos[39] = direccionEntregaDomicilio.substring(0,50)
					}else{
						campos[39] = direccionEntregaDomicilio
					}
					campos[40] = princ.ciudadDomicilioGestion
				}else {
					direccionEntregaTrabajo = princ.callePrincipalTrabajo + ' ' + princ.numeroCasaTrabajo + ' ' + princ.calleSecundariaTrabajo
					if(direccionEntregaTrabajo.length() > 50){
						campos[39] = direccionEntregaTrabajo.substring(0,50)
					}else{
						campos[39] = direccionEntregaTrabajo
					}

					campos[40] = princ.ciudadTrabajoGestion
				}
				campos[41] = princ.telefonoContacto
				campos[42] = princ.horaContacto
				campos[43] = princ.nombreCampania
				campos[44] = "PW"
				campos[45] = ""
				campos[46] = princ.nombre
				campos[47] = princ.horarioEntrega
				campos[48] = princ.genero
				campos[49] = princ.estadoCivilGestion
				campos[50] = princ.seguroDesgravamen
				campos[51] = ""
				campos[52] = princ.nombresReferencia
				campos[53] = princ.pais
				campos[54] = princ.provincia
				campos[55] = princ.ciudad
				campos[56] = princ.telefono
				campos[57] = princ.relacionConCliente
				campos[58] = princ.tipoCliente
				campos[59] = princ.nombreNegocio
				campos[60] = princ.fechaInicioNegocio
				campos[61] = princ.actividadEconomica
				campos[62] = princ.ventasHonorariosMensuales
				campos[63] = princ.costoVentas
				campos[64] = princ.gastosOperativos
				campos[65] = princ.nombreEmpresaGestion
				campos[66] = princ.contrato
				campos[67] = princ.cargo
				campos[68] = princ.fechaInicioTrabajoActual
				campos[69] = princ.sueldo
				campos[70] = princ.gastosFamilia
				campos[71] = princ.tipoVivienda
				campos[72] = princ.obligadoContabilidad
				campos[73] = princ.nombreCampania
				campos[74] = princ.opcionTarjetaSeleccionada
				campos[75] = princ.estadoCuentaDigital
				campos[76] = princ.codigoCampania
				campos[77] = princ.fechaGestion.toString().substring(0,10)
				campos[78] = princ.nombreVendedor.toUpperCase()
				campos[79] = "REGISTROS NUEVO"
				campos[80] = separarFechas(princ.fechaGestion.toString())
				campos[81] = princ.creadas_nocreadas
				campos[82] = princ.imputable
				campos[83] = princ.detalle_imputable
				campos[84] = princ.fecha_envio_creacion
				campos[85] = princ.id
				ExcelUtils.addCells(campos, sheetPrincipales, i+1, Colour.WHITE, Alignment.LEFT, VerticalAlignment.CENTRE, cellFont2, null, null)
			}
			workbook.write()
			workbook.close()
			response.setHeader("Content-disposition", "filename=TCP_NUEVO_CLIENTES_PW_YYYY_MM_DD.xls")
			response.setContentType("application/octet-stream")
			response.outputStream << file.getBytes()
			return
		}
	}
	def separarFechas(fecha){
		String[] arrayFecha = fecha.trim().split(' ')
		String separadorHora = arrayFecha[1].substring(0,8)
		return separadorHora
	}

	/**
	 * @author Andres Redroban
	 * Genera trama de No clientes de Tarjetas Principales Latam BP
	 * @param
	 * @return
	 */

	def bitacoraLatamNoClientes(){
		if(params.fechas){

			Date fechaActual = new Date()
			//Obtenemos los datos
			Date[] fechas = Util.formatearFechasReporte(params.fechas.toString())
			ArrayList<SubSubestado> subestados = Subestado.executeQuery("from Subestado where id in (1, 3)")
			def nombresBase = params.list("nombreBase")
			def condiciones = [fechaInicio: fechas[0], fechaFin: fechas[1], subestados: subestados/*, nombresBase: nombresBase*/]
			String sqlPrincipales = "from Clientes where subestadoGestion in (:subestados) and fechaGestion between :fechaInicio and :fechaFin and (subSubEstado = 'TDC' or subSubEstado = 'TDC+CREDITO')"
			def principalesList = Clientes.executeQuery(sqlPrincipales, condiciones)

			//Empezamos a crear y llenar el Excel
			def webrootDir = servletContext.getRealPath(grailsApplication.config.uploadFolder)
			File file = new File(webrootDir, "temporal.xls")
			WorkbookSettings workbookSettings = new WorkbookSettings()
			workbookSettings.setLocale(new Locale("es", "ES"))
			workbookSettings.setEncoding("UTF-8")
			WritableWorkbook workbook = Workbook.createWorkbook(file)
			workbook.createSheet("No Clientes Latam", 0)
			WritableFont cellFont = new WritableFont(WritableFont.TAHOMA, 10)
			WritableFont cellFont2 = new WritableFont(WritableFont.createFont("Calibri"), 11)
			WritableSheet sheetPrincipales = workbook.getSheet(0)
			String[] headersPrincipales = ["CEDULA", "NOMBRE1", "NOMBRE2", "APELLIDO1", "APELLIDO2", "NACIONALIDAD", "PAIS NACIMIENTO", "PROVINCIA NACIMIENTO",
										   "CIUDAD NACIMIENTO", "SITUACION LABORAL", "GENERO", "ESTADO CIVIL", "FECHA NACIMIENTO", "CALLE PRINCIPAL DOMICILIO", "NUMERO CASA DOMICILIO",
										   "CALLE SECUNDARIA DOMICILIO", "BARRIO/SECTOR DOMICILIO", "AMPLIACION DOMICILIO", "PROVINCIA DOMICILIO", "CIUDAD DOMICILIO", "CANTON DOMICILIO", "PARROQUIA DOMICILIO",
										   "DIRECCION EMAIL", "TELEFONO DOMICILIO", "CODIGO OFICINA VENDEDORA", "OFICINA TARJETA", "MARCA TARJETA", "BIN TARJETA", "CUPO NORMAL", "CUPO DIFERIDO",
										   "FORMA PAGO", "AFINIDAD", "DESTINO ESTADO DE CUENTA", "DIRECCION ESTADO DE CUENTA", "LUGAR DE ENTREGA", "NUMERO CELULAR",
										   "CALLE PRINCIPAL TRABAJO", "NUMERO CASA TRABAJO", "CALLE SECUNDARIA TRABAJO", "AMPLIACION TRABAJO", "BARRIO/SECTOR TRABAJO", "PROVINCIA TRABAJO",
										   "CIUDAD TRABAJO", "CANTON TRABAJO", "PARROQUIA TRABAJO",  "EMAIL TRABAJO", "TELEFONO TRABAJO", "CAMPAÑA", "CALL CENTER", "SEGURO DESGRAVAMEN", "SMS",
										   "NOMBRES DE REFERENCIA", "PAIS", "PROVINCIA", "CIUDAD", "TELEFONO", "RELACION CON CLIENTE", "TIPO CLIENTE", "NOMBRE NEGOCIO", "FEC. INI. NEGOCIO", "ACT. ECON.", "VENTAS/HONORARIOS MEN.",
										   "COSTO VENTAS", "GASTOS GEN. OPERATIVOS", "NOMBRE EMPRESA", "CONTRATO", "CARGO", "FEC.INI.TRAB.ACT.", "SUELDO", "GASTOS FAMILIA", "TIP. VIVIENDA",
										   "OBLIGADO CONTABILIDAD", "NOMBRE DE BASE ASIGNADA", "OPCION TARJETA SELECCIONADA POR EL CLIENTE", "EMISION DE ESTADO DE CUENTA ELECTRONICO",
			                               "NOMBRE DE LA PERSONA QUE PUEDE RECIBIR LA TARJETA", "HORARIO DE ENTREGA TARJETA","CODIGOCAMPANA","FECHAGESTION", "ASESOR VENTA", "TIPO ARCHIVO"]
			ExcelUtils.addCells(headersPrincipales, sheetPrincipales, 0, Colour.YELLOW2, Alignment.LEFT, VerticalAlignment.CENTRE, cellFont, Border.ALL, BorderLineStyle.HAIR)

			for (int i = 0; i < principalesList.size(); i++){
				String[] campos = new String[headersPrincipales.length]
				Clientes princ = principalesList.get(i)
				campos[0] = princ.cedulaTitular
				campos[1] = princ.nombre1
				campos[2] = princ.nombre2
				campos[3] = princ.apellido1
				campos[4] = princ.apellido2
				campos[5] = ""
				campos[6] = princ.paisNacimiento
				campos[7] = ""
				campos[8] = princ.ciudadNacimiento
				campos[9] = princ.situacionLaboral
				if (princ.genero.toString().equalsIgnoreCase("MASCULINO")) {
					campos[10] = "M"
				}
				if (princ.genero.toString().equalsIgnoreCase("FEMENINO")) {
					campos[10] = "F"
				}
				if (princ.estadoCivilGestion.toString().equalsIgnoreCase("DIVORCIADO")) {
					campos[11] = "D"
				}
				if (princ.estadoCivilGestion.toString().equalsIgnoreCase("SOLTERO")) {
					campos[11] = "S"
				}
				if (princ.estadoCivilGestion.toString().equalsIgnoreCase("CASADO")) {
					campos[11] = "C"
				}
				if (princ.estadoCivilGestion.toString().equalsIgnoreCase("UNION LIBRE")) {
					campos[11] = "UL"
				}
				if (princ.estadoCivilGestion.toString().equalsIgnoreCase("VIUDO")) {
					campos[11] = "V"
				}
				//CUPO 1
				campos[12] = princ.fechaNacimientoGestion   //CUPO 2
				campos[13] = princ.callePrincipalDomicilio  //CUPO 3
				campos[14] = princ.numeroCasaDomicilio
				campos[15] = princ.calleSecundariaDomicilio
				campos[16] = princ.sectorDomicilio
				campos[17] = princ.referenciaDomicilio
				campos[18] = princ.provinciaDomicilioGestion
				campos[19] = princ.ciudadDomicilioGestion
				campos[20] = ""
				campos[21] = ""
				campos[22] = princ.emailPersonal
				campos[23] = princ.telefonoDomicilio
				campos[24] = "796"
				campos[25] = princ.oficinaTarjeta
				//bin tarjetas
				if (princ.plast1TarjEsc1.toString().equalsIgnoreCase("VISA INTERNACIONAL")){
					campos[26] = "V"
					campos[27] = "451432"
				}
				if(princ.plast1TarjEsc1.toString().equalsIgnoreCase("VISA PRESTIGE LANPASS")){
					campos[26] = "V"
					campos[27] = "446004"
				}
				if(princ.plast1TarjEsc1.toString().equalsIgnoreCase("VISA PLATINUM LANPASS")){
					campos[26] = "V"
					campos[27] = "446005"
				}
				if(princ.plast1TarjEsc1.toString().equalsIgnoreCase("VISA SIGNATURE LANPASS")){
					campos[26] = "V"
					campos[27] = "400925"
				}
				if(princ.plast1TarjEsc1.toString().equalsIgnoreCase("VISA INFINITE LANPASS")){
					campos[26] = "V"
					campos[27] = "469716"
				}
				if(princ.plast1TarjEsc1.toString().equalsIgnoreCase("MASTERCARD INTERNACIONAL")){
					campos[26] = "M"
					campos[27] = "888811"
				}
				if(princ.plast1TarjEsc1.toString().equalsIgnoreCase("MASTERCARD PRESTIGE LANPASS")){
					campos[26] = "M"
					campos[27] = "518968"
				}
				if(princ.plast1TarjEsc1.toString().equalsIgnoreCase("MASTERCARD PLATINUM LANPASS")){
					campos[26] = "M"
					campos[27] = "521329"
				}
				if(princ.plast1TarjEsc1.toString().equalsIgnoreCase("MASTERCARD BLACK LANPASS")){
					campos[26] = "M"
					campos[27] = "546605"
				}
				if(princ.plast1TarjEsc1.toString().equalsIgnoreCase("VISA INTERNACIONAL SUPERMAXI")){
					campos[26] = "V"
					campos[27] = "445444"
				}
				if(princ.plast1TarjEsc1.toString().equalsIgnoreCase("VISA PRESTIGE LANPASS SUPERMAXI")){
					campos[26] = "V"
					campos[27] = "445445"
				}
				if(princ.plast1TarjEsc1.toString().equalsIgnoreCase("VISA PLATINUM LANPASS SUPERMAXI")){
					campos[26] = "V"
					campos[27] = "445446"
				}
				if(princ.plast1TarjEsc1.toString().equalsIgnoreCase("VISA SIGNATURE LANPASS SUPERMAXI")){
					campos[26] = "V"
					campos[27] = "445447"
				}
				if(princ.plast1TarjEsc1.toString().equalsIgnoreCase("VISA INTERNACIONAL MILES SUPERMAXI")){
					campos[26] = "V"
					campos[27] = "145444"
				}
				if(princ.plast1TarjEsc1.toString().equalsIgnoreCase("VISA PRESTIGE  MILES SUPERMAXI")){
					campos[26] = "V"
					campos[27] = "145445"
				}
				if(princ.plast1TarjEsc1.toString().equalsIgnoreCase("VISA PLATINUM  MILES SUPERMAXI")){
					campos[26] = "V"
					campos[27] = "145446"
				}
				if(princ.plast1TarjEsc1.toString().equalsIgnoreCase("VISA SIGNATURE  MILES SUPERMAXI")){
					campos[26] = "V"
					campos[27] = "145447"
				}
				if(princ.plast1TarjEsc1.toString().equalsIgnoreCase("VISA INFINITE  MILES SUPERMAXI")){
					campos[26] = "V"
					campos[27] = "169716"
				}
				if(princ.plast1TarjEsc1.toString().equalsIgnoreCase("MASTERCARD INTERNACIONAL MILES")){
					campos[26] = "M"
					campos[27] = "218114"
				}
				if(princ.plast1TarjEsc1.toString().equalsIgnoreCase("MASTERCARD PRESTIGE  MILES")){
					campos[26] = "M"
					campos[27] = "218968"
				}
				if(princ.plast1TarjEsc1.toString().equalsIgnoreCase("MASTERCARD PLATINUM  MILES")){
					campos[26] = "M"
					campos[27] = "221329"
				}
				if(princ.plast1TarjEsc1.toString().equalsIgnoreCase("MASTERCARD BLACK MILES")){
					campos[26] = "M"
					campos[27] = "246605"
				}
				campos[28] = princ.tarjetaEsc1 +".00"
				campos[29] = princ.tarjetaEsc1 + ".00"
				campos[30] = "1"
				campos[31] = "B"
				campos[32] = princ.lugarEntrega

				if (princ.lugarEntrega.toString().equalsIgnoreCase("1")){
					campos[33] = princ.callePrincipalDomicilio + ' ' + princ.numeroCasaDomicilio + ' ' + princ.calleSecundariaDomicilio
				}else {
					campos[33] = princ.callePrincipalTrabajo + ' ' + princ.numeroCasaTrabajo + ' ' + princ.calleSecundariaTrabajo
				}
				campos[34] = "3"
				campos[35] = princ.telefonoContactado
				campos[36] = princ.callePrincipalTrabajo
				campos[37] = princ.numeroCasaTrabajo
				campos[38] = princ.calleSecundariaTrabajo
				campos[39] = princ.referenciaTrabajo
				campos[40] = princ.sectorTrabajo
				campos[41] = princ.provinciaTrabajoGestion
				campos[42] = princ.ciudadTrabajoGestion
				campos[43] = ""
				campos[44] = ""
				campos[45] = ""
				campos[46] = princ.telefonoTrabajo
				//campos[47] = princ.fax
				campos[47] = ""
				campos[48] = "PW"
				campos[49] = "" //SEGURO DESGRAVAMEN
				campos[50] = ""
				campos[51] = princ.nombresReferencia
				campos[52] = ""
				campos[53] = ""
				campos[54] = ""
				campos[55] = princ.telefono
				campos[56] = princ.relacionConCliente
				//TIPO CLIENTE
				if (princ.tipoCliente.toString().equalsIgnoreCase("INDEPENDIENTE")){
					campos[57] = "I"
				} else{
					campos[57] = "D"
				}

				campos[58] = princ.nombreNegocio
				campos[59] = princ.fechaInicioNegocio
				campos[60] = princ.actividadEconomica
				campos[61] = princ.ventasHonorariosMensuales
				campos[62] = princ.costoVentas
				campos[63] = princ.gastosOperativos
				campos[64] = princ.nombreEmpresaGestion
				campos[65] = princ.contrato
				campos[66] = princ.cargo
				campos[67] = princ.fechaInicioTrabajoActual
				campos[68] = princ.sueldo
				campos[69] = princ.gastosFamilia
				campos[70] = princ.tipoVivienda
				campos[71] = princ.obligadoContabilidad
				campos[72] = ""
				campos[73] = princ.opcionTarjetaSeleccionada
				campos[74] = princ.estadoCuentaDigital
				campos[75] = ""
				campos[76] = ""
				campos[77] = princ.codigoCampania
				campos[78] =  princ.fechaGestion.toString().substring(0,10)
				campos[79] = princ.nombreVendedor.toUpperCase()
				campos[80] = "REGISTROS NUEVO"
				ExcelUtils.addCells(campos, sheetPrincipales, i+1, Colour.WHITE, Alignment.LEFT, VerticalAlignment.CENTRE, cellFont2, null, null)
			}
			println fecha2
			workbook.write()
			workbook.close()
			response.setHeader("Content-disposition", "filename=TCP_NUEVO_NO_CLIENTES_PW_YYYY_MM_DD.xls")
			response.setContentType("application/octet-stream")
			response.outputStream << file.getBytes()
			return
		}
	}

/**
 * @author Andres Redroban
 * Genera trama de creditos aceptados por los clientes de BP
 * @param
 * @return
 */

	def bitacoraCredito(){
		Date fechaActual = new Date()
		SimpleDateFormat objSDF = new SimpleDateFormat('yyyy-MM-dd')
		String fecha = objSDF.format(fechaActual)
		//Date fecha3 = new Date()
		if(params.fechas){

			//Obtenemos los datos
			Date[] fechas = Util.formatearFechasReporte(params.fechas.toString())
			ArrayList<SubSubestado> subestados = Subestado.executeQuery("from Subestado where id in (1)")
			def nombresBase = params.list("nombreBase")
			def condiciones = [fechaInicio: fechas[0], fechaFin: fechas[1]/*, nombresBase: nombresBase*/]
			String sqlAgencia = "from Clientes where fechaGestion between :fechaInicio and :fechaFin and subSubEstado = 'CREDITO' and ( tipoDireccionamiento = 'AGENCIA' or tipoDireccionamiento = 'SIN AGENCIA')"
			String sqlFVT = "from Clientes where fechaGestion between :fechaInicio and :fechaFin and subSubEstado = 'CREDITO' and tipoDireccionamiento = 'FVT'"
			String sqlDigital = "from Clientes where fechaGestion between :fechaInicio and :fechaFin and subSubEstado = 'CREDITO' and tipoDireccionamiento = 'DIGITAL'"
			def AgenciaList = Clientes.executeQuery(sqlAgencia, condiciones)
			def FvtList = Clientes.executeQuery(sqlFVT, condiciones)
			def digitalList = Clientes.executeQuery(sqlDigital, condiciones)

			//Empezamos a crear y llenar el Excel
			def webrootDir = servletContext.getRealPath(grailsApplication.config.uploadFolder)
			File file = new File(webrootDir, "temporal.xls")
			WorkbookSettings workbookSettings = new WorkbookSettings()
			workbookSettings.setLocale(new Locale("es", "ES"))
			workbookSettings.setEncoding("UTF-8")
			WritableWorkbook workbook = Workbook.createWorkbook(file)
			workbook.createSheet("Agencia", 0)
			workbook.createSheet("Fvt", 1)
			workbook.createSheet("Digital", 2)
			WritableFont cellFont = new WritableFont(WritableFont.TAHOMA, 10, WritableFont.BOLD)
			WritableFont cellFont2 = new WritableFont(WritableFont.createFont("Calibri"), 11)
			WritableSheet sheetAgencia = workbook.getSheet(0)
			WritableSheet sheetFvt = workbook.getSheet(1)
			WritableSheet sheetDigital = workbook.getSheet(2)
			String[] headersAgencia = ["CEDULA", "NOMBRES COMPLETOS", "MONTO ACEPTADO", "GARANTE", "AGENCIA", "ASESOR", "TIPO ASESOR PRODUCTIVIDAD",
										   "MAIL", "TELEFONO CONVENCIONAL", "TELEFONO CELULAR","CORREO ELECTRONICO CLIENTE", "FECHA", "HORA", "NOMBRE BASE", "NOMBRE ASESOR", "DIRECCIONAMIENTO"]
			String[] headersFvt = ['FECHA DE ENVIO POR WASAP ',	'NOMBRE DE SUPÉRVISOR  FVT',	'CEDULA CLIENTE',	'NOMBRE DEL CLIENTE',	'ZONA',	'CIUDAD',	'NUMERO DE CONTACTO ',	'MONTO DE CRÉDITO',	'SECTOR',	'DIRECCION ',
								   'CELULAR',	'CORREO',	'CIUDAD',	'PROVINCIA',	'SUPERVISOR',	'ZONA', 'DIRECCIONAMIENTO']
			String[] headersDigital = ['CEDULA',	'NOMBRES COMPLETOS',	'MONTO ACEPTADO',	'GARANTE',	'TELEFONO CONTACTADO',	'DIRECCIONAMIENTO CLIENTE',	'NOMBRE BASE',	'NOMBRE ASESOR',	'DIRECCIONAMIENTO']
			ExcelUtils.addCells(headersAgencia, sheetAgencia, 0, Colour.VERY_LIGHT_YELLOW, Alignment.LEFT, VerticalAlignment.CENTRE, cellFont, Border.ALL, BorderLineStyle.HAIR)
			ExcelUtils.addCells(headersFvt, sheetFvt, 0, Colour.VERY_LIGHT_YELLOW, Alignment.LEFT, VerticalAlignment.CENTRE, cellFont, Border.ALL, BorderLineStyle.HAIR)
			ExcelUtils.addCells(headersDigital, sheetDigital, 0, Colour.VERY_LIGHT_YELLOW, Alignment.LEFT, VerticalAlignment.CENTRE, cellFont, Border.ALL, BorderLineStyle.HAIR)

			for (int i = 0; i < AgenciaList.size(); i++){
				String[] campos = new String[headersAgencia.length]
				Clientes princ = AgenciaList.get(i)
				campos[0] = princ.identificacion
				campos[1] =  Util.formatearTexto(princ.nombre)
				campos[2] = princ.credConsumEsc1
				campos[3] = princ.garanteConsumEsc1
				/*if (princ.opcionCredito.toString().equalsIgnoreCase("OPT1")){
					campos[2] = princ.credConsumEsc1
					campos[3] = princ.garanteConsumEsc1
				}else{
					campos[2] = princ.credConsumExclusivo
					campos[3] = princ.garanteConsumExclusivo
				}*/

				//println (princ.oficinaTarjeta)
				if(princ.oficinaTarjeta.equalsIgnoreCase('SIN AGENCIA')){
					campos[4] = "SIN AGENCIA"
					campos[5] = ""
					campos[6] = ""
					campos[7] = ""
					campos[8] = ""
					campos[9] = princ.telefonoContactado
					campos[10] = princ.emailPersonal
					campos[11] = ""
					campos[12] = ""
				}else {
					campos[4] = princ.agencia.nombre
					campos[5] = princ.agencia.asesorAgencia
					campos[6] = princ.agencia.tipoPersonaAgencia
					campos[7] = princ.agencia.correoPersonaAgencia
					campos[8] = princ.telefonoConvencionalCredito
					campos[9] = princ.telefonoCelularCredito
					campos[10] = princ.emailPersonal
					campos[11] = convertirFecha(princ.fechaAgendamiento).toUpperCase()//princ.fechaAgendamiento.toString().substring(0,10)
					campos[12] = princ.fechaAgendamiento.toString().substring(11,19)
				}

				campos[13] = princ.nombreBase
				campos[14] = princ.nombreVendedor
				campos[15] = princ.tipoDireccionamiento
				ExcelUtils.addCells(campos, sheetAgencia, i+1, Colour.WHITE, Alignment.LEFT, VerticalAlignment.CENTRE, cellFont2, null, null)
			}

			for (int i = 0; i < FvtList.size(); i++){
				String[] campos = new String[headersFvt.length]
				Clientes princ = FvtList.get(i)
				campos[0] = princ.fechaGestion.toString()
				if (princ.ciudadDomicilioFvt != null){
					campos[1] =  AgenciaFvt.findByNombreCiudad(princ.ciudadDomicilioFvt).supervisor
				}else{
					campos[1] =  ""
				}
				campos[2] = princ.identificacion
				campos[3] = princ.nombre
				if (princ.ciudadDomicilioFvt != null){
					campos[4] =  AgenciaFvt.findByNombreCiudad(princ.ciudadDomicilioFvt).zona
				}else{
					campos[4] =  ""
				}
				campos[5] = princ.ciudadDomicilioFvt
				campos[6] = princ.telefonoContactado
				campos[7] = princ.credConsumEsc1
				campos[8] = ""
				campos[9] = ""
				if (princ.ciudadDomicilioFvt != null){
					campos[10] =  AgenciaFvt.findByNombreCiudad(princ.ciudadDomicilioFvt).celular
				}else{
					campos[10] =  ""
				}
				if (princ.ciudadDomicilioFvt != null){
					campos[11] =  AgenciaFvt.findByNombreCiudad(princ.ciudadDomicilioFvt).correo
				}else{
					campos[11] =  ""
				}
				if (princ.ciudadDomicilioFvt != null){
					campos[12] =  AgenciaFvt.findByNombreCiudad(princ.ciudadDomicilioFvt).nombreCiudad
				}else{
					campos[12] =  ""
				}
				if (princ.ciudadDomicilioFvt != null){
					campos[13] =  AgenciaFvt.findByNombreCiudad(princ.ciudadDomicilioFvt).provincia.nombre
				}else{
					campos[13] =  ""
				}
				if (princ.ciudadDomicilioFvt != null){
					campos[14] =  AgenciaFvt.findByNombreCiudad(princ.ciudadDomicilioFvt).supervisor
				}else{
					campos[14] =  ""
				}
				if (princ.ciudadDomicilioFvt != null){
					campos[15] =  AgenciaFvt.findByNombreCiudad(princ.ciudadDomicilioFvt).zona
				}else{
					campos[15] =  ""
				}
				campos[16] = princ.tipoDireccionamiento
				ExcelUtils.addCells(campos, sheetFvt, i+1, Colour.WHITE, Alignment.LEFT, VerticalAlignment.CENTRE, cellFont2, null, null)
			}

			for (int i = 0; i < digitalList.size(); i++){
				String[] campos = new String[headersDigital.length]
				Clientes princ = digitalList.get(i)
				campos[0] = princ.identificacion
				campos[1] = princ.nombre
				campos[2] = princ.credConsumEsc1
				campos[3] = princ.garanteConsumEsc1
				campos[4] = princ.telefonoContactado
				campos[5] = princ.direccionCliente
				campos[6] = princ.nombreBase
				campos[7] = princ.nombreVendedor
				campos[8] = princ.tipoDireccionamiento
				ExcelUtils.addCells(campos, sheetDigital, i+1, Colour.WHITE, Alignment.LEFT, VerticalAlignment.CENTRE, cellFont2, null, null)
			}

			workbook.write()
			workbook.close()
			response.setHeader("Content-disposition", "filename=TRAMA_CREDITOS_"+fecha+".xls")
			response.setContentType("application/octet-stream")
			response.outputStream << file.getBytes()
			return
		}
	}

	def bitacoraCredito1(){
		Date fechaActual = new Date()
		SimpleDateFormat objSDF = new SimpleDateFormat('yyyy-MM-dd')
		String fecha = objSDF.format(fechaActual)
		//Date fecha3 = new Date()
		if(params.fechas){

			//Obtenemos los datos
			Date[] fechas = Util.formatearFechasReporte(params.fechas.toString())
			ArrayList<SubSubestado> subestados = Subestado.executeQuery("from Subestado where id in (1)")
			def nombresBase = params.list("nombreBase")
			def condiciones = [fechaInicio: fechas[0], fechaFin: fechas[1]/*, nombresBase: nombresBase*/]
			String sqlPrincipales = "from Clientes where fechaGestion between :fechaInicio and :fechaFin and (subSubEstado = 'CREDITO' or subSubEstado = 'TDC+CREDITO' or subSubEstado = 'CREDITO+PDP')"
			//String sqlPrincipalesfvt = "from Clientes where fechaGestion between :fechaInicio and :fechaFin and (subSubEstado = 'CREDITO' or subSubEstado = 'TDC+CREDITO' or subSubEstado = 'CREDITO+PDP')"
			def principalesList = Clientes.executeQuery(sqlPrincipales, condiciones)

			//Empezamos a crear y llenar el Excel
			def webrootDir = servletContext.getRealPath(grailsApplication.config.uploadFolder)
			File file = new File(webrootDir, "temporal.xls")
			WorkbookSettings workbookSettings = new WorkbookSettings()
			workbookSettings.setLocale(new Locale("es", "ES"))
			workbookSettings.setEncoding("UTF-8")
			WritableWorkbook workbook = Workbook.createWorkbook(file)
			workbook.createSheet("Clientes Crédito", 0)
			workbook.createSheet("Trama Nueva", 1)
			WritableFont cellFont = new WritableFont(WritableFont.TAHOMA, 10, WritableFont.BOLD)
			WritableFont cellFont2 = new WritableFont(WritableFont.createFont("Calibri"), 11)
			WritableSheet sheetPrincipales = workbook.getSheet(0)
			WritableSheet sheetFvt = workbook.getSheet(1)
			String[] headersPrincipales = ["CEDULA", "NOMBRES COMPLETOS", "MONTO ACEPTADO", "GARANTE", "AGENCIA", "ASESOR", "TIPO ASESOR PRODUCTIVIDAD",
										   "MAIL", "TELEFONO CONVENCIONAL", "TELEFONO CELULAR","CORREO ELECTRONICO CLIENTE", "FECHA", "HORA", "NOMBRE BASE", "NOMBRE ASESOR"]
			String[] headersFvt = ['FECHA DE ENVIO POR WASAP ',	'NOMBRE DE SUPÉRVISOR  FVT',	'CEDULA CLIENTE',	'NOMBRE DEL CLIENTE',	'ZONA',	'CIUDAD',	'NUMERO DE CONTACTO ',	'MONTO DE CRÉDITO',	'SECTOR',	'DIRECCION ']
			ExcelUtils.addCells(headersPrincipales, sheetPrincipales, 0, Colour.VERY_LIGHT_YELLOW, Alignment.LEFT, VerticalAlignment.CENTRE, cellFont, Border.ALL, BorderLineStyle.HAIR)
			ExcelUtils.addCells(headersFvt, sheetFvt, 0, Colour.VERY_LIGHT_YELLOW, Alignment.LEFT, VerticalAlignment.CENTRE, cellFont, Border.ALL, BorderLineStyle.HAIR)

			for (int i = 0; i < principalesList.size(); i++){
				String[] campos = new String[headersPrincipales.length]
				Clientes princ = principalesList.get(i)
				campos[0] = princ.identificacion
				campos[1] =  Util.formatearTexto(princ.nombre)  
				if (princ.opcionCredito.toString().equalsIgnoreCase("OPT1")){
					campos[2] = princ.credConsumEsc1
					campos[3] = princ.garanteConsumEsc1
				}else{
					campos[2] = princ.credConsumExclusivo
					campos[3] = princ.garanteConsumExclusivo
				}

				//println (princ.oficinaTarjeta)
				if(princ.oficinaTarjeta.equalsIgnoreCase('SIN AGENCIA')){
					campos[4] = "SIN AGENCIA"
					campos[5] = ""
					campos[6] = ""
					campos[7] = ""
					campos[8] = ""
					campos[9] = princ.telefonoContactado
					campos[10] = princ.emailPersonal
					campos[11] = ""
					campos[12] = ""
				}else {
					campos[4] = princ.agencia.nombre
					campos[5] = princ.agencia.asesorAgencia
					campos[6] = princ.agencia.tipoPersonaAgencia
					campos[7] = princ.agencia.correoPersonaAgencia
					campos[8] = princ.telefonoConvencionalCredito
					campos[9] = princ.telefonoCelularCredito
					campos[10] = princ.emailPersonal
					campos[11] = convertirFecha(princ.fechaAgendamiento).toUpperCase()//princ.fechaAgendamiento.toString().substring(0,10)
					campos[12] = princ.fechaAgendamiento.toString().substring(11,19)
				}

				campos[13] = princ.nombreBase
				campos[14] = princ.nombreVendedor
				ExcelUtils.addCells(campos, sheetPrincipales, i+1, Colour.WHITE, Alignment.LEFT, VerticalAlignment.CENTRE, cellFont2, null, null)
			}

			for (int i = 0; i < principalesList.size(); i++){
				String[] campos = new String[headersFvt.length]
				Clientes princ = principalesList.get(i)
				campos[0] = princ.fechaGestion.toString()
				if (princ.ciudadDomicilioFvt != null){
					campos[1] =  AgenciaFvt.findByNombreCiudad(princ.ciudadDomicilioFvt).supervisor
				}else{
					campos[1] =  ""
				}
				campos[2] = princ.identificacion
				campos[3] = princ.nombre
				if (princ.ciudadDomicilioFvt != null){
					campos[4] =  AgenciaFvt.findByNombreCiudad(princ.ciudadDomicilioFvt).zona
				}else{
					campos[4] =  ""
				}
				campos[5] = princ.ciudadDomicilioFvt
				campos[6] = princ.telefonoContactado
				campos[7] = princ.credConsumEsc1
				campos[8] = ""
				campos[9] = ""
				ExcelUtils.addCells(campos, sheetFvt, i+1, Colour.WHITE, Alignment.LEFT, VerticalAlignment.CENTRE, cellFont2, null, null)
			}

			workbook.write()
			workbook.close()
			response.setHeader("Content-disposition", "filename=TRAMA_CREDITOS_"+fecha+".xls")
			response.setContentType("application/octet-stream")
			response.outputStream << file.getBytes()
			return
		}
	}

	def bitacoraMotivosNoDesea(){
		Date fechaActual = new Date()
		SimpleDateFormat objSDF = new SimpleDateFormat('yyyy-MM-dd')
		String fecha = objSDF.format(fechaActual)
		//Date fecha3 = new Date()
		if(params.fechas){

			//Obtenemos los datos
			Date[] fechas = Util.formatearFechasReporte(params.fechas.toString())
			ArrayList<SubSubestado> subestados = Subestado.executeQuery("from Subestado where id in (1)")
			def nombresBase = params.list("nombreBase")
			def condiciones = [fechaInicio: fechas[0], fechaFin: fechas[1]/*, nombresBase: nombresBase*/]
			String sqlPrincipales = "from Clientes where fechaGestion between :fechaInicio and :fechaFin and (subSubEstado = 'DESEA OTRO PRODUCTO')"
			def principalesList = Clientes.executeQuery(sqlPrincipales, condiciones)

			int secuencial = 1

			//Empezamos a crear y llenar el Excel
			def webrootDir = servletContext.getRealPath(grailsApplication.config.uploadFolder)
			File file = new File(webrootDir, "temporal.xls")
			WorkbookSettings workbookSettings = new WorkbookSettings()
			workbookSettings.setLocale(new Locale("es", "ES"))
			workbookSettings.setEncoding("UTF-8")
			WritableWorkbook workbook = Workbook.createWorkbook(file)
			workbook.createSheet("Clientes Efectivos", 0)
			WritableFont cellFont = new WritableFont(WritableFont.createFont("Calibri"), 11, WritableFont.BOLD)
			cellFont.setColour(Colour.WHITE);
			WritableFont cellFont2 = new WritableFont(WritableFont.createFont("Calibri"), 11)
			WritableSheet sheetPrincipales = workbook.getSheet(0)
			String[] headersPrincipales = ["Nº", "COD CAMPANIA", "CEDULA TITULAR", "PRODUCTO SOLICITADO POR EL CLIENTE", "EN CASO DE OTROS PRODUCTOS, ESPECIFICAR CUAL", "PROVEEDOR"]
			ExcelUtils.addCells(headersPrincipales, sheetPrincipales, 0, Colour.GREEN, Alignment.LEFT, VerticalAlignment.CENTRE, cellFont, Border.ALL, BorderLineStyle.THIN)
			for (int i = 0; i < principalesList.size(); i++) {
				String[] campos = new String[headersPrincipales.length]
				Clientes cli = principalesList.get(i)
				campos[0] = secuencial
				campos[1] = cli.codigoCampania
				campos[2] = cli.identificacion
				campos[3] = cli.motivoSubSubEstado
				campos[4] = cli.deseaNuevoProducto
				campos[5] = "PW"
				secuencial++
				ExcelUtils.addCells(campos, sheetPrincipales, i + 1, Colour.WHITE, Alignment.LEFT, VerticalAlignment.CENTRE, cellFont2, null, null)
			}
			workbook.write()
			workbook.close()
			response.setHeader("Content-disposition", "filename=ventasTarjetasAustro.xls")
			response.setContentType("application/octet-stream")
			response.outputStream << file.getBytes()
			return
		}
	}

	def bitacoraPDP(){
		Date fechaActual = new Date()
		SimpleDateFormat objSDF = new SimpleDateFormat('yyyy-MM-dd')
		String fecha = objSDF.format(fechaActual)
		//Date fecha3 = new Date()
		if(params.fechas){

			//Obtenemos los datos
			Date[] fechas = Util.formatearFechasReporte(params.fechas.toString())
			ArrayList<SubSubestado> subestados = Subestado.executeQuery("from Subestado where id in (1)")
			def nombresBase = params.list("nombreBase")
			def condiciones = [fechaInicio: fechas[0], fechaFin: fechas[1]/*, nombresBase: nombresBase*/]
			String sqlPrincipales = "from Clientes where fechaGestion between :fechaInicio and :fechaFin and (subSubEstado = 'PDP' or subSubEstado = 'CREDITO+PDP')"
			def principalesList = Clientes.executeQuery(sqlPrincipales, condiciones)

			//Empezamos a crear y llenar el Excel
			def webrootDir = servletContext.getRealPath(grailsApplication.config.uploadFolder)
			File file = new File(webrootDir, "temporal.xls")
			WorkbookSettings workbookSettings = new WorkbookSettings()
			workbookSettings.setLocale(new Locale("es", "ES"))
			workbookSettings.setEncoding("UTF-8")
			WritableWorkbook workbook = Workbook.createWorkbook(file)
			workbook.createSheet("Clientes Crédito", 0)
			WritableFont cellFont = new WritableFont(WritableFont.TAHOMA, 10, WritableFont.BOLD)
			WritableFont cellFont2 = new WritableFont(WritableFont.createFont("Calibri"), 11)
			WritableSheet sheetPrincipales = workbook.getSheet(0)
			String[] headersPrincipales = ["FECHA REFERIMIENTO", "PROVEEDOR", "NOMBRE CLIENTE", "CEDULA CLIENTE", "TIPO PLAN", "CIUDAD UBICACION CLIENTE", "VALOR A DEBITAR",
										   "PERIODICIDAD", "CODIGO CAMPANIA", "NOMBRE CAMPAÑA", "NOMBRE VENDEDOR"]
			/*String[] headersAdicionales = ["CEDULA TITULAR", "CEDULA ADICIONAL", "NACIONALIDAD", "NOMBRE1 ADICIONAL", "NOMBRE2 ADICIONAL",
										 "APELLIDO1 ADICIONAL", "APELLIDO 2 ADICIONAL", "FECHA NACIMIENTO", "SEXO", "ESTADO CIVIL",
										 "CUPO", "PARENTESCO", "NOMBRE TARJETA"]*/
			ExcelUtils.addCells(headersPrincipales, sheetPrincipales, 0, Colour.VERY_LIGHT_YELLOW, Alignment.LEFT, VerticalAlignment.CENTRE, cellFont, Border.ALL, BorderLineStyle.HAIR)
			int contadorCruzadas = 0
			for (int j = 0; j < principalesList.size(); j++){
				String[] campos3 = new String[headersPrincipales.length]
				Clientes princ2 = principalesList.get(j)
				contadorCruzadas++
				campos3[0] = princ2.fechaGestion.toString().substring(8,10) + "/" + princ2.fechaGestion.toString().substring(5,7) + "/" + princ2.fechaGestion.toString().substring(0,4)
				campos3[1] = "PW"
				campos3[2] = princ2.nombre
				campos3[3] = princ2.identificacion
				campos3[4] = "PLAN DEUDA PROTEGIDA"
				if (princ2.ciudadNacimiento == null || princ2.ciudadNacimiento == ""){
					campos3[5] = princ2.ciudadDomicilio
				}else{
					campos3[5] = princ2.ciudadNacimiento
				}
				campos3[6] = '2.98'
				campos3[7] = "MENSUAL"
				campos3[8] = princ2.codigoCampania
				campos3[9] = princ2.nombreBase
				campos3[10] = princ2.nombreVendedor
				ExcelUtils.addCells(campos3, sheetPrincipales, j+1, Colour.WHITE, Alignment.LEFT, VerticalAlignment.CENTRE, cellFont2, null, null)
			}
			workbook.write()
			workbook.close()
			response.setHeader("Content-disposition", "filename=TRAMA_CREDITOS_"+fecha+".xls")
			response.setContentType("application/octet-stream")
			response.outputStream << file.getBytes()
			return
		}
	}
/**
 * @author Andres Redroban
 * @description Funcion que transforma una fecha en formato EEEE dd MMM YYYY. Ejemplo Miercoles 17 de 04 del 2019
 * @param fecha
 * @return formato
 */
	def convertirFecha(Date fecha){
		String formato = new SimpleDateFormat("EEEE dd 'de' MMMM 'del' YYYY", new Locale("ES")).format(fecha)
		return formato
	}

	def indicadoresGestion(){
		boolean visibilizar = false
		if(params.fechas){
			visibilizar = true
			DecimalFormat df = new DecimalFormat("#.00")
			Date[] fechas = Util.formatearFechasReporte(params.fechas.toString())
			def nombresBase = params.list("nombreBase")
			Date fechaInicio = fechas[0]
			Date fechaFin = fechas[1]

			int Exitosas = 0
			int Contactados = 0
			int NoDesea = 0
			int exitosasPdp = 0
			int exitosasCredito = 0
			double Efectividad = 0
			double EfectividadPdp = 0
			double EfectividadCredito = 0
			double NoDeseaPorcentaje = 0

			int totalGestionados = 0
			int totalContactados = 0
			int totalNoContactados = 0
			int totalExitosos = 0
			int totalCruzadasCU2 = 0
			int totalCruzadasCU3 = 0
			int totalNoDeseaCU5 = 0
			int totalTarjetasCreadas = 0
			int totalDiferenciaTarjetas = 0
			String totalPorcentajeContactabilidad = ""
			String totalPorcentajeEfectividad = ""
			String totalPorcentajeEfectividadPdp = ""
			String totalPorcentajeEfectividadCredito = ""
			String totalPorcentajeReales = ""
			def subestadosEfectivos = Subestado.executeQuery("from Subestado where id in (1)")
			def subestadosCruzadas = Subestado.executeQuery("from Subestado where id in (2)")
			def subestadosCU3 = Subestado.executeQuery("from Subestado where id in (3)")
			def subestadosCU5 = Subestado.executeQuery("from Subestado where id in (5)")
			def ventasPorUsuario = Clientes.executeQuery("select nombreVendedor, count(*), substring(fechaGestion,1,10) from Historial where (subSubEstado = 'TDC' or subSubEstado = 'TDC+CREDITO') and fechaGestion between :fechaInicio and :fechaFin group by nombreVendedor, substring(fechaGestion,1,10) order by nombreVendedor", [fechaInicio: fechaInicio, fechaFin: fechaFin])
			def cruzadasPorUsuario = Clientes.executeQuery("select nombreVendedor, count(*), substring(fechaGestion,1,10) from Historial where (subSubEstado = 'PDP' or subSubEstado = 'CREDITO+PDP') and fechaGestion between :fechaInicio and :fechaFin group by nombreVendedor , substring(fechaGestion,1,10) order by nombreVendedor", [fechaInicio: fechaInicio, fechaFin: fechaFin])
			def CU3PorUsuario = Clientes.executeQuery("select nombreVendedor, count(*), substring(fechaGestion,1,10) from Historial where (subSubEstado = 'CREDITO' or subSubEstado = 'TDC+CREDITO' or subSubEstado = 'CREDITO+PDP') and fechaGestion between :fechaInicio and :fechaFin group by nombreVendedor, substring(fechaGestion,1,10) order by nombreVendedor", [fechaInicio: fechaInicio, fechaFin: fechaFin])
			def noDeseaPorUsuario = Clientes.executeQuery("select nombreVendedor, count(*), substring(fechaGestion,1,10) from Historial where subestadoGestion in (:subestadosCU5) and fechaGestion between :fechaInicio and :fechaFin group by nombreVendedor , substring(fechaGestion,1,10) order by nombreVendedor", [subestadosCU5: subestadosCU5, fechaInicio: fechaInicio, fechaFin: fechaFin])
			def gestionadosAgente = Clientes.executeQuery("select nombreVendedor, count(*), substring(fechaGestion,1,10) from Historial where intentos != 0 and fechaGestion between :fechaInicio and :fechaFin group by nombreVendedor, substring(fechaGestion,1,10) order by nombreVendedor", [fechaInicio: fechaInicio, fechaFin: fechaFin])
			def contactadosAgente = Clientes.executeQuery("select nombreVendedor, count(*), substring(fechaGestion,1,10) from Historial where intentos != 0 and estadoGestion = 'CONTACTADO' and fechaGestion between :fechaInicio and :fechaFin group by nombreVendedor, substring(fechaGestion,1,10) order by nombreVendedor", [fechaInicio: fechaInicio, fechaFin: fechaFin])
			def noContactadosAgente = Clientes.executeQuery("select nombreVendedor, count(*), substring(fechaGestion,1,10) from Historial where intentos != 0 and estadoGestion = 'NO CONTACTADO' and fechaGestion between :fechaInicio and :fechaFin group by nombreVendedor, substring(fechaGestion,1,10) order by nombreVendedor", [fechaInicio: fechaInicio, fechaFin: fechaFin])

			String[][] tablaResult = new String[gestionadosAgente.size()][12]
			//Lleno la matriz de resultados con los resultados de las onsultas anteriores
			for(int i = 0; i < tablaResult.size(); i++){
				tablaResult[i][0] = gestionadosAgente[i][0]
				tablaResult[i][1] = gestionadosAgente[i][1]
				tablaResult[i][10] = gestionadosAgente[i][2]

				//Lleno información de contactados y % de contactabilidad
				for(int j = 0; j < contactadosAgente.size(); j++){
					if(contactadosAgente[j][0] == gestionadosAgente[i][0] && contactadosAgente[j][2] == gestionadosAgente[i][2]){
						tablaResult[i][2] = contactadosAgente[j][1]
						Contactados = tablaResult[i][2].toInteger()
						break
					}
				}
				if(tablaResult[i][2] == null){
					Contactados = 0
				}
				//Lleno información de no contactados
				/*for(int j = 0; j < noContactadosAgente.size(); j++){
					if(noContactadosAgente[j][0] == gestionadosAgente[i][0] && noContactadosAgente[j][2] == gestionadosAgente[i][2]){
						tablaResult[i][3] = noContactadosAgente[j][1]
						break
					}
				}*/
				//LLeno la información de las ventas
				for(int j = 0; j < ventasPorUsuario.size(); j++){
					if(ventasPorUsuario[j][0] == gestionadosAgente[i][0] && ventasPorUsuario[j][2] == gestionadosAgente[i][2]){
						tablaResult[i][4] = ventasPorUsuario[j][1]
						Exitosas = tablaResult[i][4].toInteger()
						break
					}
				}
				if(tablaResult[i][4] == null){
					Exitosas = 0
				}
				//LLeno la información de las ventas cruzadas
				for(int j = 0; j < cruzadasPorUsuario.size(); j++){
					if(cruzadasPorUsuario[j][0] == gestionadosAgente[i][0] && cruzadasPorUsuario[j][2] == gestionadosAgente[i][2]){
						tablaResult[i][5] = cruzadasPorUsuario[j][1]
						exitosasPdp = tablaResult[i][5].toInteger()
						break
					}
				}
				if(tablaResult[i][5] == null){
					exitosasPdp = 0
				}

				//LLeno la información de las ventas CU3
				for(int j = 0; j < CU3PorUsuario.size(); j++){
					if(CU3PorUsuario[j][0] == gestionadosAgente[i][0] && CU3PorUsuario[j][2] == gestionadosAgente[i][2]){
						tablaResult[i][6] = CU3PorUsuario[j][1]
						exitosasCredito = tablaResult[i][6].toInteger()
						break
					}
				}
				if(tablaResult[i][6] == null){
					exitosasCredito = 0
				}

				//LLeno la información de los No Desea
				for(int j = 0; j < noDeseaPorUsuario.size(); j++){
					if(noDeseaPorUsuario[j][0] == gestionadosAgente[i][0] && noDeseaPorUsuario[j][2] == gestionadosAgente[i][2]){
						tablaResult[i][7] = noDeseaPorUsuario[j][1]
						NoDesea = tablaResult[i][7].toInteger()
						break
					}
				}
				if(tablaResult[i][7] == null){
					NoDesea = 0
				}

				//Pra calcular los procentajes de efectividad y no desea
				if(Exitosas != 0 && Exitosas != null){
					Efectividad = (Exitosas / Contactados) * 100
					tablaResult[i][8] = df.format(Double.parseDouble(Efectividad.toString()))
					//tablaResult[i][8] = 0
				}else{
					tablaResult[i][8] = 0
				}

				if(exitosasPdp != 0 && exitosasPdp != null){
					EfectividadPdp = (exitosasPdp / Contactados) * 100
					tablaResult[i][3] = df.format(Double.parseDouble(EfectividadPdp.toString()))
				}else{
					tablaResult[i][3] = 0
				}

				if(NoDesea != 0 && NoDesea != null){
					NoDeseaPorcentaje = (NoDesea / Contactados) * 100
					tablaResult[i][9] = df.format(Double.parseDouble(NoDeseaPorcentaje.toString()))
					//tablaResult[i][9] = 0
				}else{
					tablaResult[i][9] = 0
				}

				if(exitosasCredito != 0 && exitosasCredito != null){
					EfectividadCredito = (exitosasCredito / Contactados) * 100
					tablaResult[i][11] = df.format(Double.parseDouble(EfectividadCredito.toString()))
					//tablaResult[i][9] = 0
				}else{
					tablaResult[i][11] = 0
				}
				//println(Contactados + " " + Exitosas + " " + NoDesea)
			}

			//Recorro la matriz de resultados para obtener los totales
			for(int i = 0; i < tablaResult.size(); i++){
				totalGestionados = totalGestionados + tablaResult[i][1].toInteger()
				if(tablaResult[i][2] != null)
					totalContactados = totalContactados + tablaResult[i][2].toInteger()
				/*if(tablaResult[i][3] != null)
					totalNoContactados = totalNoContactados + tablaResult[i][3].toInteger()*/
				if(tablaResult[i][4] != null)
					totalExitosos = totalExitosos + tablaResult[i][4].toInteger()
				if(tablaResult[i][5] != null)
					totalCruzadasCU2 = totalCruzadasCU2 + tablaResult[i][5].toInteger()
				if(tablaResult[i][6] != null)
					totalCruzadasCU3 = totalCruzadasCU3 + tablaResult[i][6].toInteger()
				if(tablaResult[i][7] != null)
					totalNoDeseaCU5 = totalNoDeseaCU5 + tablaResult[i][7].toInteger()
			}

			if(totalGestionados == 0){
				totalPorcentajeContactabilidad = "0.00"
				totalPorcentajeEfectividad = "0.00"
				totalPorcentajeEfectividadPdp = "0.00"
				totalPorcentajeEfectividadCredito = "0.00"
			}else{
				totalPorcentajeContactabilidad = df.format((totalExitosos/totalContactados)*100)
				totalPorcentajeEfectividad = df.format((totalNoDeseaCU5/totalContactados)*100)
				totalPorcentajeEfectividadPdp = df.format((totalCruzadasCU2/totalContactados)*100)
				totalPorcentajeEfectividadCredito = df.format((totalCruzadasCU3/totalContactados)*100)
			}
			[visibilizar: visibilizar, tablaResult: tablaResult, totalGestionados: totalGestionados, totalContactados: totalContactados,
			 totalNoContactados: totalNoContactados, totalExitosos:totalExitosos, totalCruzadasCU2: totalCruzadasCU2, totalCruzadasCU3: totalCruzadasCU3,
			 totalNoDeseaCU5: totalNoDeseaCU5, totalPorcentajeContactabilidad: totalPorcentajeContactabilidad, totalPorcentajeEfectividad: totalPorcentajeEfectividad,
			 totalPorcentajeEfectividadPdp: totalPorcentajeEfectividadPdp, totalPorcentajeEfectividadCredito: totalPorcentajeEfectividadCredito]
		}
	}

	def tiemposBreak() {
		if (params.fechas) {
			Date[] fechas = Util.formatearFechasReporte(params.fechas.toString())
			ArrayList<SubSubestado> subestados = Subestado.executeQuery("from Subestado where id in (1)")
			def nombresBase = params.list("nombreBase")
			def condiciones = [fechaInicio: fechas[0], fechaFin: fechas[1]]
			String sqlPrincipales = "from BreakTime where dateBreak between :fechaInicio and :fechaFin"
			def principalesList = BreakTime.executeQuery(sqlPrincipales, condiciones)
			//Empezamos a crear y llenar el Excel
			def webrootDir = servletContext.getRealPath(grailsApplication.config.uploadFolder)
			File file = new File(webrootDir, "temporal.xls")
			WorkbookSettings workbookSettings = new WorkbookSettings()
			workbookSettings.setLocale(new Locale("es", "ES"))
			workbookSettings.setEncoding("UTF-8")
			WritableWorkbook workbook = Workbook.createWorkbook(file)
			workbook.createSheet("Clientes Efectivos", 0)
			WritableFont cellFont = new WritableFont(WritableFont.createFont("Calibri"), 11, WritableFont.BOLD)
			cellFont.setColour(Colour.WHITE);
			WritableFont cellFont2 = new WritableFont(WritableFont.createFont("Calibri"), 11)
			WritableSheet sheetPrincipales = workbook.getSheet(0)
			String[] headersPrincipales = ["FECHA/HORA", "TIEMPO", "OPCION", "NOMBRE USUARIO"]
			ExcelUtils.addCells(headersPrincipales, sheetPrincipales, 0, Colour.GREEN, Alignment.LEFT, VerticalAlignment.CENTRE, cellFont, Border.ALL, BorderLineStyle.THIN)
			for (int i = 0; i < principalesList.size(); i++) {
				String[] campos = new String[headersPrincipales.length]
				BreakTime cli = principalesList.get(i)
				campos[0] = cli.dateBreak.toString()
				campos[1] = cli.timeBreak.toString()
				campos[2] = cli.typeBreak
				campos[3] = cli.user.nombre
				ExcelUtils.addCells(campos, sheetPrincipales, i + 1, Colour.WHITE, Alignment.LEFT, VerticalAlignment.CENTRE, cellFont2, null, null)
			}
			workbook.write()
			workbook.close()
			response.setHeader("Content-disposition", "filename=tiemposBreakAsesores.xls")
			response.setContentType("application/octet-stream")
			response.outputStream << file.getBytes()
			return
		}
	}

	def baseGestionada(){
		if(params.fechas){
			Date[] fechas = Util.formatearFechasReporte(params.fechas.toString())
			def nombresBase = params.list("nombreBase")
			def subestados = params.list("subestados")
			def condiciones = [fechaInicio: fechas[0], fechaFin: fechas[1], nombresBase: nombresBase]
			String sql = "from Clientes where fechaGestion between :fechaInicio and :fechaFin and nombreBase in (:nombresBase)"
			def base = Clientes.executeQuery(sql, condiciones)

			//Empezamos a crear y llenar el Excel
			def webrootDir = servletContext.getRealPath(grailsApplication.config.uploadFolder)
			File file = new File(webrootDir, "temporal.xls")
			WorkbookSettings workbookSettings = new WorkbookSettings()
			workbookSettings.setLocale(new Locale("es", "ES"))
			workbookSettings.setEncoding("Cp1252")
			WritableWorkbook workbook = Workbook.createWorkbook(file, workbookSettings)
			workbook.createSheet("baseGestionada", 0)
			WritableSheet sheet = workbook.getSheet(0)
			String[] headers = [
					"CEDULA",
					"NOMBRES",
					"ESTADO",
					"SUBESTADO",
					"SUBSUBESTADO",
					"FECHA GESTION",
					"NOMBRE BASE",
					"NOMBRE VENDEDOR",
					"INTENTOS",
					"OBSERVACIONES",
					"TELEFONO CONTACTADO"
			]
			ExcelUtils.addCells(headers, sheet, 0, Colour.GRAY_25, Alignment.LEFT, VerticalAlignment.CENTRE, null, Border.ALL, BorderLineStyle.HAIR)

			for(int i = 0; i < base.size(); i++){
				String[] campos = new String[headers.length]
				Clientes c = base.get(i)

				campos[0] = c.identificacion
				campos[1] = c.nombre
				campos[2] = c.estadoGestion
				campos[3] = c.subestadoGestion.nombre
				campos[4] = c.subSubEstado
				campos[5] = c.fechaGestion.toString()
				campos[6] = c.nombreBase
				campos[7] = c.nombreVendedor
				campos[8] = c.intentos
				campos[9] = c.observaciones
				campos[10] = c.telefonoContactado
				ExcelUtils.addCells(campos, sheet, i+1, Colour.WHITE, Alignment.LEFT, VerticalAlignment.CENTRE, null, null, null)
			}
			workbook.write()
			workbook.close()
			response.setHeader("Content-disposition", "filename=baseGestionadaDiferido.xls")
			response.setContentType("application/octet-stream")
			response.outputStream << file.getBytes()
			return

		}
	}


	def loginAgentes(){
		boolean visibilizar = false
		if(params.fechas) {
			visibilizar = true
			Date[] fechas = Util.formatearFechasReporte(params.fechas.toString())
			def nombresBase = params.list("nombreBase")
			Date fechaInicio = fechas[0]
			Date fechaFin = fechas[1]
			def consulta = Clientes.executeQuery("select substring(fechaGestion,1,10), nombreVendedor, substring(min(fechaGestion),11,12), substring(max(fechaGestion),11,12) from Clientes where fechaGestion between :fechaInicio and :fechaFin group by substring(fechaGestion,1,10), nombreVendedor", [fechaInicio: fechaInicio, fechaFin: fechaFin])
			String[][] tablaResult = new String[consulta.size()][5]
			//Lleno la matriz de resultados con los resultados de las onsultas anteriores
			for(int i = 0; i < tablaResult.size(); i++) {
				tablaResult[i][0] = consulta[i][0]
				tablaResult[i][1] = consulta[i][1]
				tablaResult[i][2] = consulta[i][2]
				tablaResult[i][3] = consulta[i][3]
			}
			[visibilizar: visibilizar, tablaResult: tablaResult]
		}
	}


	def bitacoraGestion(){
		if(params.nombreBase){
			//Obtenemos los datos
			ArrayList<SubSubestado> subestados = Subestado.executeQuery("from Subestado where id in (1,2,3)")
			def nombresBase = params.list("nombreBase")
			def condiciones = [nombresBase: nombresBase]
			String sqlPrincipales = "from Clientes where codigoCampania in (:nombresBase)"
			String sqlBroadcast = "from Clientes where codigoCampania in (:nombresBase) and estadoBroadcast is not null"
			def principalesList = Clientes.executeQuery(sqlPrincipales, condiciones)
			def broadcastList = Clientes.executeQuery(sqlBroadcast, condiciones)

			//Empezamos a crear y llenar el Excel
			def webrootDir = servletContext.getRealPath(grailsApplication.config.uploadFolder)
			File file = new File(webrootDir, "temporal.xls")
			WorkbookSettings workbookSettings = new WorkbookSettings()
			workbookSettings.setLocale(new Locale("es", "ES"))
			workbookSettings.setEncoding("Cp1252")
			workbookSettings.setUseTemporaryFileDuringWrite(true)
			WritableWorkbook workbook = Workbook.createWorkbook(file, workbookSettings)
			workbook.createSheet("GESTION", 0)
			workbook.createSheet("BROADCAST", 1)
			WritableFont cellFont = new WritableFont(WritableFont.TAHOMA, 10)
			WritableFont cellFont2 = new WritableFont(WritableFont.createFont("Calibri"), 11)
			WritableSheet sheetPrincipales = workbook.getSheet(0)
			WritableSheet sheetBroadcast = workbook.getSheet(1)
			String[] headersPrincipales = ['CODIGOCAMPANA',	'AGENTE',	'USUARIO ID',	'TIPO_GESTION',	'IDENTIFICACION',	'NOMBRECLIENTE',
										   'FECHAGESTION',	'ESTATUS',	'ULTIMOTELEFONOCONTACTO',	'OBSERVACIONMOTIVONODESEA',	'NUMEROINTENTOS',
										   'ESTADO_GESTION',	'NOMBRE BASE',	'TELEFONO1',	'TELEFONO2',	'TELEFONO3',	'TELEFONO4',
										   'TELEFONO5',	'ADICIONALES',	'GAMA',	'REGIONAL',	'RANGO EDAD',	'RANGO CUPO',	'CADUCIDAD',	'ID SISTEMA',
										   'ALIMENTACION', 'BROADCAST', 'RESPONDE SI / NO', 'FECHA ENVIO BROADCAST' , "",
										   "CREADAS NO CREADAS", "IMPUTABLE", "DETALLE IMPUTABLE", "FECHA ENVIO CREACION"]
			String[] headersBroadcast = ['CODIGO CAMPAÑA',	'NUMERO DE CEDULA (14 DIGITOS)',	'NOMBRE DEL CLIENTE',	'OPERACIÓN',	'CALL',
										 'FECHA PRIMER ENVIO',	'OBSERVACION PRIMER ENVIO',	'FECHA SEGUNDO ENVIO',	'OBSERVACION SEGUNDO ENVIO']

			ExcelUtils.addCells(headersPrincipales, sheetPrincipales, 0, Colour.GOLD, Alignment.LEFT, VerticalAlignment.CENTRE, cellFont, Border.ALL, BorderLineStyle.HAIR)
			ExcelUtils.addCells(headersBroadcast, sheetBroadcast, 0, Colour.GRAY_25, Alignment.LEFT, VerticalAlignment.CENTRE, cellFont, Border.ALL, BorderLineStyle.HAIR)
			for (int i = 0; i < principalesList.size(); i++){
				String[] campos3 = new String[headersPrincipales.length]
				Clientes princ3 = principalesList.get(i)
				campos3[0] = princ3.codigoCampania
				campos3[1] = princ3.nombreVendedor
				if (princ3.usuario == null){
					campos3[2] = princ3.usuario
				}else{
					campos3[2] = princ3.usuario.id
				}
				campos3[3] = princ3.tipoGestion
				campos3[4] = princ3.identificacion
				campos3[5] = princ3.nombre
				if (princ3.fechaGestion == null){
					campos3[6] = princ3.fechaGestion
				}else{
					campos3[6] = princ3.fechaGestion.toString().substring(0,10).replace("/","-")
				}
				if (princ3.subestadoGestion == null){
					campos3[7] = princ3.subestadoGestion
				}else{
					campos3[7] = princ3.subestadoGestion.nombre
				}
				campos3[8] = princ3.telefonoContactado
				campos3[9] = princ3.subSubEstado
				campos3[10] = princ3.intentos
				campos3[11] = princ3.estadoGestion
				campos3[12] = princ3.nombreBase
				campos3[13] = princ3.telefono1
				campos3[14] = princ3.telefono2
				campos3[15] = princ3.telefono3
				campos3[16] = princ3.telefono4
				campos3[17] = princ3.telefono5
				campos3[18] = ''
				campos3[19] = princ3.gama
				campos3[20] = princ3.regional
				campos3[21] = princ3.rangoEdad
				campos3[22] = princ3.rangoCupo
				campos3[23] = princ3.fechaCaducidad
				campos3[24] = princ3.id
				campos3[25] = princ3.alimentacion
				campos3[26] = princ3.estadoBroadcast
				campos3[27] = princ3.respuestaBroadcast
				campos3[28] = princ3.fechaEnvioBroadcast
				campos3[29] = ""
				campos3[30] = princ3.creadas_nocreadas
				campos3[31] = princ3.imputable
				campos3[32] = princ3.detalle_imputable
				campos3[33] = princ3.fecha_envio_creacion
				ExcelUtils.addCells(campos3, sheetPrincipales, i+1, Colour.WHITE, Alignment.LEFT, VerticalAlignment.CENTRE, cellFont2, null, null)
			}
			for (int j = 0; j < broadcastList.size(); j++){
				String[] campos3 = new String[headersBroadcast.length]
				Clientes princ3 = broadcastList.get(j)
				campos3[0] = princ3.codigoCampania
				campos3[1] = princ3.identificacion
				campos3[2] = princ3.nombre
				campos3[3] = ""
				campos3[4] = "PW"
				campos3[5] = princ3.fechaEnvioBroadcast
				campos3[6] = princ3.estadoBroadcast
				campos3[7] = ""
				campos3[8] = ""
				ExcelUtils.addCells(campos3, sheetBroadcast, j+1, Colour.WHITE, Alignment.LEFT, VerticalAlignment.CENTRE, cellFont2, null, null)
			}
			workbook.write()
			workbook.close()
			response.setHeader("Content-disposition", "filename=bitacoraGestion.xls")
			response.setContentType("application/octet-stream")
			response.outputStream << file.getBytes()
			return
		}
	}


}
