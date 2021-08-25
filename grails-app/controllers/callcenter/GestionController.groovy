package callcenter
import com.pw.security.*
import grails.converters.JSON
import jxl.Cell
import jxl.Sheet
import jxl.Workbook
import jxl.WorkbookSettings
import liquibase.util.file.FilenameUtils
import utilitarios.Util

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat

class GestionController {

	static ArrayList<Adicional> adicionalesArrayList

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
	 * @author Giovanny Granda
	 * Muestra en pantalla los clientes asignados
	 * @return
	 */
	def index() {
		Usuario usuario = Usuario.findById(session.user.id);
		def plataforma = 'PURE CLOUD'
//		def clientesGestionar = Clientes.executeQuery("from Clientes c where subestadoGestion.rellamar = 'SI' and usuario = :usuario order by c.intentos", [usuario: usuario])

		def clients = Clientes.withCriteria {
			eq('usuario',usuario)
			eq('isActive', true)
			notEqual('plataforma', plataforma)
			subestadoGestion {
				or{
					eq('type', Subestado.constraints.type.inList[0].toString())
					eq('type', Subestado.constraints.type.inList[1].toString())
				}
			}
			order("intentos")
		}
		def clientsNoManagement = Clientes.withCriteria {
			eq('usuario',usuario)
			eq('isActive', true)
			isNull('subestadoGestion')
		}

		clients.each {client ->
			clientsNoManagement.add(client)
		}
//		print clientsNoManagement.size()

//		def clientesGestionar = Clientes.withCriteria {
//			eq('usuario',usuario)
//			(
//					{
//						isNull('subestadoGestion')
//					}
//			)
//					{
//						or{
//							subestadoGestion{
//								eq('type',Subestado.constraints.type.inList[0].toString())
//							}
//							subestadoGestion{
//								eq('type',Subestado.constraints.type.inList[1].toString())
//							}
//						}
//					}
//			order('intentos','asc')
//		}

		[clientesGestionar: clientsNoManagement]
	}

	/**
	 * @author Giovanny Granda
	 * Muestra la pantalla de gestion donde se hara rectificación de datos
	 * @param id
	 * @return
	 */
	def gestionCliente(long id){
		long idCliente = id
		Clientes cliente = Clientes.findById(idCliente)
		session.user
		Calendar datos = Calendar.getInstance()
		int dia = datos.get(Calendar.DATE)
		int mes = datos.get(Calendar.MONTH)
		int anio = datos.get(Calendar.YEAR)
		String fechaActual = anio + '-' + (mes+1) + '-' + dia
		SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd")
		Date fecha1 = sfd.parse(fechaActual.toString())
		Date fecha2 = sfd.parse(cliente.fechaCaducidad)
		String salida = fecha1.compareTo(fecha2)
		if(salida == "1"){
			render(view: "modelFechaCaducada",  model: [idCliente: cliente.id])
		}
		[cliente: cliente,usuario: session.user]
	}

	/**
	 * @author Giovanny Granda
	 * Guarda la gestion del call center
	 * @param id
	 * @return
	 */
	def guardarCliente(){
		Usuario usuario = Usuario.findById(session.user.id)
		Date fechaActual = new Date()
		long idCliente = params.idCliente.toLong()
		long idEstadoGestion = params.estado.toLong()
		long idSubestadoGestion = params.substatus.toLong()
		def subSubEstadoNew = ""
		Estado estadoGestion = Estado.findById(idEstadoGestion)
		//String estadoGestion = Estado.findById(params.status.toString().toLong())
		Subestado objSubestadoGestion = Subestado.findById(idSubestadoGestion)


		//Busco el cliente por su id
		Clientes cliente = Clientes.findById(idCliente)
		int intentos = cliente.intentos?: 0

		if(cliente.registroExitoso != "SI"){



		//if(objSubestadoGestion.enableManagement && params.subSubStatus != "4" && params.subSubStatus != "10"){
		if(objSubestadoGestion.nombre.equalsIgnoreCase("CU1 ACEPTA PRODUCTO CAMPAÑA") || objSubestadoGestion.nombre.equalsIgnoreCase("CU3 ACEPTA PRODUCTO Y VENTA CRUZADA")){
			cliente.cedulaTitular = params.cedulaTitular
			cliente.nombre1 = formatearTexto(params.nombre1.toString()).toUpperCase()
			cliente.nombre2 = formatearTexto(params.nombre2.toString()).toUpperCase()
			cliente.apellido1 = formatearTexto(params.apellido1.toString()).toUpperCase()
			cliente.apellido2 = formatearTexto(params.apellido2.toString()).toUpperCase()
			cliente.nacionalidad = formatearTexto(params.nacionalidad.toString()).toUpperCase()
			cliente.paisNacimiento = formatearTexto(params.paisNacimiento.toString()).toUpperCase()
			if (params.provinciaNacimiento != ""){
				cliente.provinciaNacimiento = Provincia.findById(params.provinciaNacimiento.toLong()).nombre
			}else{
				cliente.provinciaNacimiento = ""
			}
			if (params.ciudadNacimiento != ""){
				cliente.ciudadNacimiento = Ciudad.findById(params.ciudadNacimiento.toLong()).nombre
			}else{
				cliente.ciudadNacimiento = ""
			}

			if(params.fechaNacimientoGestion != ""){
				cliente.fechaNacimientoGestion = params.fechaNacimientoGestion.toString().replace("/","") //Util.formatearFechaNacimiento(params.fechaNacimientoGestion)
			}else{
				cliente.fechaNacimientoGestion = null
			}

			cliente.callePrincipalDomicilio = formatearTexto(params.callePrincipalDomicilio.toString()).toUpperCase()
			cliente.numeroCasaDomicilio = formatearTexto(params.numeroCasaDomicilio.toString()).toUpperCase()
			cliente.calleSecundariaDomicilio = formatearTexto(params.calleSecundariaDomicilio.toString()).toUpperCase()
			cliente.referenciaDomicilio = formatearTexto(params.referenciaDomicilio.toString()).toUpperCase()
			if (params.parroquiaDomicilio != ""){
				cliente.parroquiaDomicilio = Parroquia.findById(params.parroquiaDomicilio.toLong()).nombre
			}else{
				cliente.parroquiaDomicilio = ""
			}

			cliente.telefonoDomicilio = params.telefonoDomicilio
			cliente.celularGestion = params.celularGestion
			if (params.ciudadDomicilioGestion != ""){
				cliente.ciudadDomicilioGestion = Ciudad.findById(params.ciudadDomicilioGestion.toLong()).nombre
			}else{
				cliente.ciudadDomicilioGestion = ""
			}

			if (params.provinciaDomicilioGestion != ""){
				cliente.provinciaDomicilioGestion = Provincia.findById(params.provinciaDomicilioGestion.toLong()).nombre
			}else{
				cliente.provinciaDomicilioGestion =""
			}
			if (params.cantonDomicilio != ""){
				cliente.cantonDomicilio = Canton.findById(params.cantonDomicilio.toLong()).nombre
			}else{
				cliente.cantonDomicilio = ""
			}

			cliente.sectorDomicilio = formatearTexto(params.sectorDomicilio).toUpperCase()
			cliente.emailPersonal = params.emailPersonal
			cliente.callePrincipalTrabajo = formatearTexto(params.callePrincipalTrabajo.toString()).toUpperCase()
			cliente.numeroCasaTrabajo = formatearTexto(params.numeroCasaTrabajo.toString()).toUpperCase()
			cliente.calleSecundariaTrabajo = formatearTexto(params.calleSecundariaTrabajo.toString()).toUpperCase()
			cliente.referenciaTrabajo = formatearTexto(params.referenciaTrabajo.toString()).toUpperCase()
			cliente.telefonoTrabajo = params.telefonoTrabajo
			cliente.fax = params.fax
			if (params.ciudadTrabajoGestion != ""){
				cliente.ciudadTrabajoGestion = Ciudad.findById(params.ciudadTrabajoGestion.toLong()).nombre
			}else{
				cliente.ciudadTrabajoGestion = ""
			}
			if (params.provinciaTrabajoGestion != ""){
				cliente.provinciaTrabajoGestion = Provincia.findById(params.provinciaTrabajoGestion.toLong()).nombre
			}else{
				cliente.provinciaTrabajoGestion = ""
			}
			if (params.cantonTrabajo != ""){
				cliente.cantonTrabajo = Canton.findById(params.cantonTrabajo.toLong()).nombre
			}else{
				cliente.cantonTrabajo = ""
			}
			if (params.parroquiaTrabajo != ""){
				cliente.parroquiaTrabajo = Parroquia.findById(params.parroquiaTrabajo.toLong()).nombre
			}else{
				cliente.parroquiaTrabajo = ""
			}
			cliente.emailTrabajo = params.emailTrabajo
			cliente.sectorTrabajo = formatearTexto(params.sectorTrabajo).toUpperCase()
			cliente.entregaEstadoCuenta = params.entregaEstadoCuenta
			cliente.lugarEntrega = params.lugarEntrega
			cliente.telefonoContacto = params.telefonoContacto
			if(params.horaContacto != ""){
				cliente.horaContacto = formatearHora( params.horaContacto.toString())
			}else{
				cliente.horaContacto = ""
			}

			cliente.nombreRecibeTarjeta = formatearTexto(params.nombreRecibeTarjeta).toUpperCase()
			cliente.horarioEntrega = params.horarioEntrega

			cliente.genero = params.genero
			cliente.estadoCivilGestion = params.estadoCivilGestion
			cliente.nombresReferencia = formatearTexto(params.nombresReferencia.toString()).toUpperCase()
			cliente.pais = formatearTexto(params.pais.toString()).toUpperCase()
			if (params.provincia != ""){
				cliente.provincia = Provincia.findById(params.provincia.toLong()).nombre
			}else{
				cliente.provincia = ""
			}
			if (params.ciudad != ""){
				cliente.ciudad = Ciudad.findById(params.ciudad.toLong()).nombre
			}else{
				cliente.ciudad = ""
			}

			cliente.telefono = params.telefono
			cliente.relacionConCliente = params.relacionConCliente
			cliente.tipoCliente = params.tipoCliente
			if(params.tipoCliente == "D"){
				cliente.situacionLaboral = params.situacionLaboral
				cliente.nombreEmpresaGestion = formatearTexto(params.nombreEmpresaGestion.toString()).toUpperCase()
				cliente.contrato = params.contrato
				cliente.cargo = formatearTexto(params.cargo.toString()).toUpperCase()
				if(params.fechaInicioTrabajoActual != "")
					cliente.fechaInicioTrabajoActual = params.fechaInicioTrabajoActual.toString().replace("/","") //Util.formatearFechaNacimiento(params.fechaInicioTrabajoActual)
				cliente.sueldo = params.sueldo
				cliente.gastosFamilia = params.gastosFamilia
			}
			else{
				cliente.situacionLaboral = "INDEPENDIENTE"
			}
			cliente.nombreNegocio = formatearTexto(params.nombreNegocio.toString()).toUpperCase()
			if(params.fechaInicioNegocio != "")
				cliente.fechaInicioNegocio = params.fechaInicioNegocio.toString().replace("/","")//Util.formatearFechaNacimiento(params.fechaInicioNegocio)
			cliente.actividadEconomica = formatearTexto(params.actividadEconomica.toString()).toUpperCase()
			cliente.ventasHonorariosMensuales = params.ventasHonorariosMensuales
			cliente.costoVentas = params.costoVentas
			cliente.gastosOperativos = params.gastosOperativos
			/*DESDE AQUI VA JUBILACIONES*/
			if(params.tipoCliente == "J"){
				cliente.situacionLaboral = params.situacionLaboralJubilado
				cliente.nombreEmpresaGestion = formatearTexto(params.nombreEmpresaGestionJubilado.toString()).toUpperCase()
				cliente.contrato = params.contratoJubilado
				cliente.cargo = formatearTexto(params.cargoJubilado.toString()).toUpperCase()
				if(params.fechaInicioTrabajoActualJubilado != "")
					cliente.fechaInicioTrabajoActual = params.fechaInicioTrabajoActualJubilado.toString().replace("/","") //Util.formatearFechaNacimiento(params.fechaInicioTrabajoActualJubilado)
				cliente.sueldo = params.sueldoJubilado
				cliente.gastosFamilia = params.gastosFamiliaJubilado
			}
			cliente.tipoVivienda = params.tipoVivienda
			cliente.obligadoContabilidad = params.obligadoContabilidad
			cliente.opcionTarjetaSeleccionada = params.opcionTarjetaSeleccionada
			//cliente.opcionTarjetaSeleccionada = ''
			cliente.estadoCuentaDigital = params.estadoCuentaDigital
			cliente.sms = params.sms
			cliente.seguroDesgravamen = params.seguroDesgravamen

			cliente.opcionCredito = params.creditoAceptado
			cliente.situacionLaboralCredito = params.situacionLaboralCredito
			cliente.signatureDocuments = params.signatureDocuments
			if (params.signatureDocuments == "DOMICILIO"){
				cliente.oficinaTarjeta = "SIN AGENCIA"
				//cliente.fechaAgendamiento =  fechaActual
			}
			if (params.signatureDocuments == "SIN AGENCIA"){
				cliente.oficinaTarjeta = params.signatureDocuments
				//cliente.fechaAgendamiento =  new Date().parse('yyyy-MM-dd HH:mm:ss', formatearRellamada(params.fechaAgendamiento2.toString()))
				cliente.tipoDireccionamiento = 'SIN AGENCIA'
			}

			if (params.signatureDocuments == "DOMICILIO"){
				cliente.ciudadDomicilioFvt = params.ciudadDomicilioFvt
				cliente.tipoDireccionamiento = 'FVT'
			}
			if (params.signatureDocuments == "DIGITAL"){
				cliente.direccionCliente = params.direccionCliente
				cliente.oficinaTarjeta = "SIN AGENCIA"
				cliente.tipoDireccionamiento = 'DIGITAL'
			}

			if (params.oficinaTarjeta != ""){
				cliente.oficinaTarjeta = Agencia.findById(params.oficinaTarjeta.toLong()).nombre
				cliente.tipoDireccionamiento = 'AGENCIA'
				long idAgencia = params.oficinaTarjeta.toLong()
				Agencia objAgencia = Agencia.findById(idAgencia)
				cliente.agencia = objAgencia
				if (params.fechaAgendamiento != ""){
					cliente.fechaAgendamiento =  new Date().parse('yyyy-MM-dd HH:mm:ss', formatearRellamada(params.fechaAgendamiento.toString()))
				}
			}else{
				//cliente.oficinaTarjeta = ""
				cliente.agencia = null
			}
			//cliente.numeroContactado = params.numeroContactado

			cliente.telefonoConvencionalCredito = params.telefonoConvencionalCredito
			cliente.telefonoCelularCredito = params.telefonoCelularCredito
			if(params.subSubStatus != ""){
				long idSubSubestadoGestion = params.subSubStatus.toLong()
				SubSubestado objSubSubestadoGestion = SubSubestado.findById(idSubSubestadoGestion)
				if(objSubSubestadoGestion.name.equalsIgnoreCase("CREDITO")){
					cliente.emailPersonal = params.correoCredito
				}
			}

			if (params.provinciaNacimientoCU2 != ""){
				cliente.ciudadNacimiento = Ciudad.findById(params.ciudadNacimientoCU2.toLong()).nombre
			}

		//	cliente.registroExitoso = 'SI'
		}

		if(objSubestadoGestion.alias.equalsIgnoreCase("NO EDITABLE")){
			cliente.registroExitoso = 'SI'
		}

		if(objSubestadoGestion.nombre.equalsIgnoreCase("CU2 ACEPTA VENTA CRUZADA")){
			cliente.ciudadNacimiento = Ciudad.findById(params.ciudadNacimientoCU2.toLong()).nombre
		}
		if(objSubestadoGestion.type.toString().equalsIgnoreCase("Rellamada")){
			//cliente.fechaRellamada = new Date().parse('yyyy-MM-dd HH:mm:ss', params.recall.toString().replace('/','-') + ':00')
			cliente.fechaRellamada =  new Date().parse('yyyy-MM-dd HH:mm:ss', formatearRellamada(params.recall.toString()))
			cliente.agendamientoAsesor = params.agendamiento
		}

		if(objSubestadoGestion.nombre.toString().equalsIgnoreCase("CU5 NO DESEA EL PRODUCTO")){
			cliente.motivoNoAceptaSeguro = params.motivoNoAceptaSeguro
		}else{
			cliente.motivoNoAceptaSeguro = ""
		}

		cliente.estadoGestion = estadoGestion.nombre
		cliente.subestadoGestion = objSubestadoGestion
		if (params.subSubStatus != ""){
			String nombreSubSubEstado = SubSubestado.findById(params.subSubStatus.toString().toLong()).name
			cliente.subSubEstado = nombreSubSubEstado
			/*if(nombreSubSubEstado.equalsIgnoreCase("CREDITO+TDC") || nombreSubSubEstado.equalsIgnoreCase("CREDITO+TDC+ASISTENCIA") ||
					nombreSubSubEstado.equalsIgnoreCase("CREDIT") || nombreSubSubEstado.equalsIgnoreCase("CREDITO+ASISTENCIA")){
				cliente.opcionTarjetaSeleccionada = 'OFERTA 1'
			}
			if(nombreSubSubEstado.equalsIgnoreCase("TDC") || nombreSubSubEstado.equalsIgnoreCase("TDC+ASISTENCIA")){
				cliente.opcionTarjetaSeleccionada = 'OFERTA 4'
			}*/
			if(nombreSubSubEstado.equalsIgnoreCase("TDC+CREDITO")){
				cliente.opcionTarjetaSeleccionada = 'OFERTA 1'
			}
			if(nombreSubSubEstado.equalsIgnoreCase("TDC")){
				cliente.opcionTarjetaSeleccionada = 'OFERTA 4'
			}
		}
		else
			cliente.subSubEstado = ""

			if (params.motivosSubSubEstados != ""){
				String nombreMotivoSubSubEstado = MotivoSubEstado.findById(params.motivosSubSubEstados.toString().toLong()).nombre
				cliente.motivoSubSubEstado = nombreMotivoSubSubEstado
			}
			else
				cliente.motivoSubSubEstado = ""

			if (params.deseaNuevoProducto != ""){
				cliente.deseaNuevoProducto = params.deseaNuevoProducto
			}
			else
				cliente.deseaNuevoProducto = ""

		if(params.exequial)
			cliente.asistenciaExequial = "SI"
		else
			cliente.asistenciaExequial = ""
		if(params.fraudes)
			cliente.asistProteccionFraudes = "SI"
		else
			cliente.asistProteccionFraudes = ""
		cliente.telefonoContactado = params.telefonoContactado
		cliente.estadoTelefonoContactado = params.estadoTelefonoContactado
		cliente.tipoCobroProtFraudes = params.cobroProteccionFraudes
		cliente.intentos = intentos+1
		cliente.fechaGestion = fechaActual
		cliente.usuario = usuario
		cliente.nombreVendedor = usuario.nombre
		cliente.vinculoConfianza = params.vinculoConfianza
		cliente.contratoPreliminar = params.contratoPreliminar
		cliente.observaciones = params.observaciones
		cliente.motivoNoDesea = params.motivoNoDesea
		cliente.save(flush: true)

		//Se guarda informacion en el historial
		Historial historial = new Historial()
		historial.cliente = Clientes.findById(cliente.id.toLong())
		historial.estadoGestion = cliente.estadoGestion
		historial.subestadoGestion = cliente.subestadoGestion
		historial.subSubEstado = cliente.subSubEstado
		historial.fechaGestion = fechaActual
		historial.intentos = cliente.intentos
		historial.nombreVendedor = cliente.nombreVendedor
		historial.observacionesGestion = cliente.observaciones
		historial.usuario = cliente.usuario
		historial.plataforma = cliente.plataforma
		historial.telefonoContactado = params.telefonoContactado
		historial.detalleTelefono1 = cliente.telefono1 + " " + params.estadoTel1
		historial.detalleTelefono2 = cliente.telefono2 + " " + params.estadoTel2
		historial.detalleTelefono3 = cliente.telefono3 + " " + params.estadoTel3
		historial.detalleTelefono4 = cliente.telefono4 + " " + params.estadoTel4
		historial.detalleTelefono5 = cliente.telefono5 + " " + params.estadoTel5
		historial.detalleTelefono6 = cliente.telefono6 + " " + params.estadoTel6
		historial.detalleTelefono7 = cliente.telefono7 + " " + params.estadoTel7
		historial.detalleTelefono8 = cliente.telefono8 + " " + params.estadoTel8
		historial.detalleTelefono9 = cliente.telefono9 + " " + params.estadoTel9
		historial.detalleTelefono10 = cliente.telefono10 + " " + params.estadoTel10
		historial.estadoTelefonoContactado = cliente.estadoTelefonoContactado
		historial.save(flush: true)
		session.setAttribute("lastManageId","")
		redirect(uri: "/gestion/index")
		}else{
			render(view: "modelValidacion",  model: [estado:cliente.estadoGestion, subestado: cliente.subestadoGestion.nombre, idCliente: cliente.id])
		}
	}

	private String formatearTexto(String entrada){
		return entrada.toUpperCase().replace('Ñ', 'N').replace('-', ' ').replace('Á', 'A').replace('É', 'E').replace('Í', 'I').replace('Ó', 'O').replace('Ú', 'U').replace('.', ' ')
	}

	/**
	 * @author Andres Redroban
	 * Convierte de formato hh:MM AM/PM a formato 24H
	 * @return resultado
	 */
	private String formatearHora(String entrada){
		DateFormat formatoInicial = new SimpleDateFormat("hh:mm a") //11:00 pm
		Date hora = null
		try {
			hora = formatoInicial.parse(entrada)
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace()
		}
		DateFormat formatoFinal = new SimpleDateFormat("HH:mm")
		String resultado = formatoFinal.format(hora) // "23:00"

		return resultado
	}

	/**
	 * @author Andres Redroban
	 * Convierte el formato de campo Fecha Rellamada
	 * @return resultado
	 */
	private String formatearRellamada(String variable){
		String[] arrayFechas = variable.trim().split(' ')
		String fecha = arrayFechas[0].replace('/', '-')
		String hora =  arrayFechas[1] + ' ' + arrayFechas[2]
		SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
		SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
		Date date = parseFormat.parse(hora);
		String  horaFinal = displayFormat.format(date)+':00'
		String resultado = fecha + ' ' + horaFinal
		return resultado
	}

	def retirarBase(){
		boolean updateRealizado = false
		int resultado = 0
		if(params.usuario != null && params.tipo != null && params.nombrebase != null){

			String desde = params.desde
			String hasta = params.hasta

			updateRealizado = true
			Usuario usuarioAdministrador = Usuario.findById(1)

			def subestados
			if(params.tipo != "RELLAMADAS"){
				subestados = Subestado.executeQuery("from Subestado where type = 'Regestionable'")
			}else {
				subestados = Subestado.executeQuery("from Subestado where type = 'Rellamada'")
			}

			String sql = "update Clientes set usuario = :usuario where (subestadoGestion in (:subestados) or subestadoGestion is null) and usuario != :usuario and plataforma != 'PURE CLOUD' "


			def condiciones = [usuario: usuarioAdministrador, subestados: subestados]
			String condicionUsuario = ""
			String condicionTipo = ""
			String condicionNombreBase = ""
			String condicionDesde = ""
			String condicionHasta = ""

			if(params.desde != ""){
				condicionDesde = " and cast(codigoAsignacion as integer) >= :desde"
				condiciones.put("desde", desde.toString().toInteger())
			}

			if(params.hasta != ""){
				condicionHasta = " and cast(codigoAsignacion as integer) <= :hasta"
				condiciones.put("hasta", hasta.toString().toInteger())
			}

			if(params.usuario != ""){
				Usuario usuarioVendedor = Usuario.findById(params.usuario)
				condicionUsuario = " and usuario = :vendedor"
				condiciones.put("vendedor", usuarioVendedor)
			}

			if(params.tipo != ""){
				if(params.tipo == "REGESTIONABLE"){
					condicionTipo = " and intentos > 0"
				}
				if(params.tipo == "RELLAMADAS"){
					condicionTipo = " and intentos > 0 and agendamientoAsesor = 'AGENDAR PARA CUALQUIERA'"
				}
				if(params.tipo == "SIN GESTION"){
					condicionTipo = " and intentos = 0"
				}
			}

			if(params.nombrebase != ""){
				condicionNombreBase = " and nombreBase = :nombreBase"
				condiciones.put("nombreBase", params.nombrebase)
			}

			resultado = Clientes.executeUpdate(sql+condicionUsuario+condicionTipo+condicionNombreBase+condicionDesde+condicionHasta, condiciones)

		}
		[updateRealizado: updateRealizado, resultado: resultado]
	}

	def cargarBase(){

	}

	def saveFile(){
		String[] formFields = Clientes.getFields()
		def file = request.getFile('file')
		Cell[] cells
		String[] headers
		if(file.empty){
			flash.message = "Por favor selecciona un archivo"
		}else{
			def webrootDir = servletContext.getRealPath(grailsApplication.config.uploadFolder) //app directory
			File fileDest = new File(webrootDir,file.getOriginalFilename())
			if(fileDest.mkdirs()){
				println "directory created"
			}else{
				println "directory not created or already exists"
			}
			file.transferTo(fileDest)

			//Reading Excel
			String ext = FilenameUtils.getExtension(fileDest.path)
			if(ext.equalsIgnoreCase("xls") || ext.equalsIgnoreCase("xlsx")){
				try {
					WorkbookSettings ws = new WorkbookSettings()
					ws.setEncoding("Cp1252")
					Workbook workbook = Workbook.getWorkbook(fileDest, ws)
					Sheet sheet = workbook.getSheet(0)
					cells = sheet.getRow(0)
					workbook.close()
				}catch (IOException ex){
					flash.error = "Problemas al cargar el archivo"
					render(view: "index")
				}
				headers = new String[cells.length]
				for(int i = 0; i < cells.length; i++){
					headers[i] = cells[i].getContents()
				}
				render(view: "sortExcel", model: [headers: headers, formFields:formFields, filePath:fileDest.path])
			}else{
				flash.error = "El archivo debe ser una hoja de cálculo de Excel"
				render(view: "index")
			}
		}
	}

	/**
	 * Status
	 * @return
	 */
	def getSubStatusByStatus(){
		if(params.id) {
			def status = Estado.findById(params.id)
			def subStatus = Subestado.findAllByEstado(status)
			def array = [subStatus.id, subStatus.nombre, subStatus.type, subStatus.enableManagement]
			render array as JSON
		}
	}

	/**
	 *
	 */
	def saveAditional(){

		if(request.xhr){
			def aditional = new Adicional()
			aditional.cedula = params.cedula
			aditional.primerNombre = params.primerNombre
			aditional.segundoNombre = params.segundoNombre
			aditional.primerApellido = params.primerApellido
			aditional.segundoApellido = params.segundoApellido
			aditional.nombreTarjeta = params.nombreTarjeta
			aditional.cupoOtorgado = params.cupoOtorgado
			aditional.fechaNacimiento = params.fechaNacimiento
			aditional.estadoCivil = params.estadoCivil
			aditional.nacionalidad = params.nacionalidad
			aditional.parentesco = params.parentesco
			aditional.sexo = params.sexo
			aditional.observaciones = params.observaciones
			aditional.usuario = Usuario.findById(params.usuario.id.toString().toLong())
			aditional.clientes = Clientes.findById(params.clientes.id.toString().toLong())

			if(aditional.save()){
				render true
			}else{
				render false
			}
		}
	}

	/**
	 * make by someone
	 * @param value
	 * @return
	 */
	private removeSpecialCharacters(value){
		if(value != null){
			def newValue = value.replace("-"," ").replace("!","").replace("@","").replace("#","").replace("\$","")
					.replace("&","").replace("/","").replace("(","").replace(")","").replace("=","")
					.replace("?","").replace("¿","").replace("ç","").replace("{","").replace("}","")
					.replace("\\","").replace("á","a").replace("é","e").replace("í","i").replace("ó","o")
					.replace("ú","u").replace("\"","").replace("Á","A").replace("É","E").replace("Í","I")
					.replace("Ó","O").replace("Ú","U").replace("\'","").replace("  "," ").replace("  "," ")
					.replace("  "," ").replace("%","").replace(".","").replace(",","").replace("º","")
					.replace("ª","").replace("|","").replace("\$","").replace("¬","").replace("%","")
					.replace("*","").replace("+","").replace("_","")
			return newValue
		}
	}

}
