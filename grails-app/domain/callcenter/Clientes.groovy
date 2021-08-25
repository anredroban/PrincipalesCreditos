package callcenter
import java.util.Date;
import com.pw.security.*;

class Clientes {

	//Campos de la base
	String codigoCampania
	String nombreCampania
	String identificacion
	String baseComercial
	String nombre
	String clienteBanco
	String calificacionBuro
	String perfilRiesgoEndeud
	String edad
	String credConsumEsc1
	String cuotaConsumEsc1
	String plazoConsumEsc1
	String prodConsumEsc1
	String garanteConsumEsc1
	String tarjetaEsc1
	String marcaTarjEsc1
	String fliaTarjetaEsc1
	String plast1TarjEsc1
	String credConsumExclusivo
	String cuotaConsumExclusivo
	String plazoConsumExclusivo
	String prodConsumExclusivo
	String garanteConsumExclusivo
	String credConsumRol
	String cuotaConsumRol
	String plazoConsumRol
	String prodConsumRol
	String garanteConsumRol
	String tarjetaExclusiva
	String prodTarjetaExclusiva
	String marcaTarjetaExclusiva
	String fliaTarjetaExclusiva
	String plast1TarjExclusiva
	String creditoAutomotriz
	String cuotaCredAutomotriz
	String plazoCredAutomotriz
	String entradaAutomotriz
	String maximoConsumo
	String maximaTarjeta
	String codigoCliente
	String primerNombre
	String segundoNombre
	String primerApellido
	String segundoApellido
	String codigoAgencia
	String segmento
	String subsegmento
	String codigoAsesor
	String ciudadAnclaje
	String codigoPersona
	String tipoIdentificacion
	String fechaNacimiento
	String sexo
	String numeroCuenta
	String tipoCuenta
	String estadoCivil
	String provinciaDomicilio
	String telefono1
	String telefono2
	String telefono3
	String telefono4
	String telefono5
	String telefono6
	String telefono7
	String telefono8
	String telefono9
	String telefono10
	String email
	String ciudadDomicilio
	String direccionDomicilio
	String provinciaTrabajo
	String ciudadTrabajo
	String direccionTrabajo
	String agencias
	String zona
	String region
	String cupoMaximo
	String difCupos
	String idConyuge
	String nombreConyuge
	String codigoAsignacion
	String smartPhone

	/*----Encuesta----*/
	String cedulaTitular
	String nombre1
	String nombre2
	String apellido1
	String apellido2
	String nacionalidad
	String paisNacimiento
	String provinciaNacimiento
	String ciudadNacimiento
	String fechaNacimientoGestion
	String situacionLaboral
	String callePrincipalDomicilio
	String numeroCasaDomicilio
	String calleSecundariaDomicilio
	String referenciaDomicilio
	String parroquiaDomicilio
	String telefonoDomicilio
	String celularGestion
	String ciudadDomicilioGestion
	String provinciaDomicilioGestion
	String cantonDomicilio
	String emailPersonal
	String callePrincipalTrabajo
	String numeroCasaTrabajo
	String calleSecundariaTrabajo
	String referenciaTrabajo
	String telefonoTrabajo
	String fax
	String ciudadTrabajoGestion
	String provinciaTrabajoGestion
	String cantonTrabajo
	String parroquiaTrabajo
	String emailTrabajo
	String entregaEstadoCuenta
	String telefonoContacto
	String horaContacto
	String genero
	String estadoCivilGestion
	String nombresReferencia
	String pais
	String provincia
	String ciudad
	String telefono
	String relacionConCliente
	String tipoCliente
	String nombreNegocio
	String fechaInicioNegocio
	String actividadEconomica
	String ventasHonorariosMensuales
	String costoVentas
	String gastosOperativos
	String nombreEmpresaGestion
	String contrato
	String cargo
	String fechaInicioTrabajoActual
	String sueldo
	String gastosFamilia
	String tipoVivienda
	String obligadoContabilidad
	String opcionTarjetaSeleccionada
	String estadoCuentaDigital

	String asistenciaExequial
	String asistProteccionFraudes
	String tipoCobroProtFraudes
	/*--Fin Encuesta----*/


	//Campos que SIEMPRE van en la gestión
	Date fechaGestion
	int intentos
	String estadoGestion
	Subestado subestadoGestion
	String subSubEstado
	Usuario usuario
	String nombreBase
	String nombreVendedor
	Date fechaRellamada
	String observaciones
	String motivoNoDesea
	String estadoTelefonoContactado
	String alimentacion
	String estadoBroadcast
	String respuestaBroadcast
	String fechaEnvioBroadcast
	String tipoProducto


	//nuevos campos 22/11/2018
	String cupo1
	String cupo2
	String cupo3
	String cupo4
	String sectorDomicilio
	String sectorTrabajo
	String nombreRecibeTarjeta
	String sms
	String lugarEntrega
	String horarioEntrega
	String oficinaTarjeta
	String tipoDireccionamiento


	String direccionCliente
	String ciudadDomicilioFvt



	// nuevos campos creditos 2018/12/18
	String opcionCredito //Cual oferta acepta el cliente
	String signatureDocuments //Donde se acerca a firmar los documentos
	Date fechaAgendamiento
	String numeroContactado
    String situacionLaboralCredito

	//METODO SANDLER
	String vinculoConfianza
	String contratoPreliminar

	//Telefono Contactado
	String telefonoContactado

	//NUEVO CAMPO SEGURO DESGRAVAMEN 20190121
	 String seguroDesgravamen

	String agendamientoAsesor

	//Nuevo campo No Acepta Seguro
	String motivoNoAceptaSeguro

	//Nuevos campos para credito
	String telefonoConvencionalCredito
	String telefonoCelularCredito
	Agencia agencia

	//Campo para prioridad de campañas
	String registroExitoso
	String deseaNuevoProducto
	String motivoSubSubEstado

	//Campos Plan Deuda Protegida
	String codigoMarca
	String planRecompensas

	/*Creacion de campos solicitados por el area de Reporting 2019-05-11*/
	String gama
	String regional
	String rangoEdad
	String rangoCupo
	String segmentacionAd1
	String segmentacionAd2
	String segmentacionAd3
	String segmentacionAd4
	String segmentacionAd5
	String easyCodeRegional
	String easyCodeEdad
	String easyCodeCupo
	String easyCodeEdadCupo
	String easyCodeGamaEdad
	String easyCodeAd1
	String easyCodeAd2
	String easyCodeAd3
	String easyCodeAd4
	String easyCodeAd5
	String prioridadCampania
	String fechaCaducidad
	String deudaProtegida
	String metaContactabilidad
	String metaEfectividadTelefonica
	String metaEfectividadReal
	String tipoGestion
	String plataforma
	String bloqueSegmentacion
	String creadas_nocreadas
	String imputable
	String detalle_imputable
	String fecha_envio_creacion

	//boolean para activar y desactivar bases y clientes
	boolean isActive

	static constraints = {
		//Campos de la base
		codigoCampania nullable: true
		nombreCampania nullable: true
		identificacion nullable: true
		baseComercial nullable: true
		nombre nullable: true
		clienteBanco nullable: true
		calificacionBuro nullable: true
		perfilRiesgoEndeud nullable: true
		edad nullable: true
		credConsumEsc1 nullable: true
		cuotaConsumEsc1 nullable: true
		plazoConsumEsc1 nullable: true
		prodConsumEsc1 nullable: true
		garanteConsumEsc1 nullable: true
		tarjetaEsc1 nullable: true
		marcaTarjEsc1 nullable: true
		fliaTarjetaEsc1 nullable: true
		plast1TarjEsc1 nullable: true
		credConsumExclusivo nullable: true
		cuotaConsumExclusivo nullable: true
		plazoConsumExclusivo nullable: true
		prodConsumExclusivo nullable: true
		garanteConsumExclusivo nullable: true
		credConsumRol nullable: true
		cuotaConsumRol nullable: true
		plazoConsumRol nullable: true
		prodConsumRol nullable: true
		garanteConsumRol nullable: true
		tarjetaExclusiva nullable: true
		prodTarjetaExclusiva nullable: true
		marcaTarjetaExclusiva nullable: true
		fliaTarjetaExclusiva nullable: true
		plast1TarjExclusiva nullable: true
		creditoAutomotriz nullable: true
		cuotaCredAutomotriz nullable: true
		plazoCredAutomotriz nullable: true
		entradaAutomotriz nullable: true
		maximoConsumo nullable: true
		maximaTarjeta nullable: true
		codigoCliente nullable: true
		primerNombre nullable: true
		segundoNombre nullable: true
		primerApellido nullable: true
		segundoApellido nullable: true
		codigoAgencia nullable: true
		segmento nullable: true
		subsegmento nullable: true
		codigoAsesor nullable: true
		ciudadAnclaje nullable: true
		codigoPersona nullable: true
		tipoIdentificacion nullable: true
		fechaNacimiento nullable: true
		sexo nullable: true
		numeroCuenta nullable: true
		tipoCuenta nullable: true
		estadoCivil nullable: true
		provinciaDomicilio nullable: true
		telefono1 nullable: true
		telefono2 nullable: true
		telefono3 nullable: true
		telefono4 nullable: true
		telefono5 nullable: true
		telefono6 nullable: true
		telefono7 nullable: true
		telefono8 nullable: true
		telefono9 nullable: true
		telefono10 nullable: true
		email nullable: true
		ciudadDomicilio nullable: true
		direccionDomicilio nullable: true
		provinciaTrabajo nullable: true
		ciudadTrabajo nullable: true
		direccionTrabajo nullable: true
		agencias nullable: true
		zona nullable: true
		region nullable: true
		cupoMaximo nullable: true
		difCupos nullable: true
		idConyuge nullable: true
		nombreConyuge nullable: true
		codigoAsignacion nullable: true
		smartPhone nullable: true
		alimentacion nullable: true
		estadoBroadcast nullable: true
		respuestaBroadcast nullable: true
		fechaEnvioBroadcast nullable: true
		tipoProducto nullable: true

		creadas_nocreadas nullable: true
		imputable nullable: true
		detalle_imputable nullable: true
		fecha_envio_creacion nullable: true

		/*----Encuesta----*/
		cedulaTitular nullable: true
		nombre1 nullable: true
		nombre2 nullable: true
		apellido1 nullable: true
		apellido2 nullable: true
		nacionalidad nullable: true
		paisNacimiento nullable: true
		provinciaNacimiento nullable: true
		ciudadNacimiento nullable: true
		fechaNacimientoGestion nullable: true
		situacionLaboral nullable: true
		callePrincipalDomicilio nullable: true
		numeroCasaDomicilio nullable: true
		calleSecundariaDomicilio nullable: true
		referenciaDomicilio nullable: true
		parroquiaDomicilio nullable: true
		telefonoDomicilio nullable: true
		celularGestion nullable: true
		ciudadDomicilioGestion nullable: true
		provinciaDomicilioGestion nullable: true
		cantonDomicilio nullable: true
		emailPersonal nullable: true
		callePrincipalTrabajo nullable: true
		numeroCasaTrabajo nullable: true
		calleSecundariaTrabajo nullable: true
		referenciaTrabajo nullable: true
		telefonoTrabajo nullable: true
		fax nullable: true
		ciudadTrabajoGestion nullable: true
		provinciaTrabajoGestion nullable: true
		cantonTrabajo nullable: true
		parroquiaTrabajo nullable: true
		emailTrabajo nullable: true
		entregaEstadoCuenta nullable: true
		telefonoContacto nullable: true
		horaContacto nullable: true
		genero nullable: true
		estadoCivilGestion nullable: true
		nombresReferencia nullable: true
		pais nullable: true
		provincia nullable: true
		ciudad nullable: true
		telefono nullable: true
		relacionConCliente nullable: true
		tipoCliente nullable: true
		nombreNegocio nullable: true
		fechaInicioNegocio nullable: true
		actividadEconomica nullable: true
		ventasHonorariosMensuales nullable: true
		costoVentas nullable: true
		gastosOperativos nullable: true
		nombreEmpresaGestion nullable: true
		contrato nullable: true
		cargo nullable: true
		fechaInicioTrabajoActual nullable: true
		sueldo nullable: true
		gastosFamilia nullable: true
		tipoVivienda nullable: true
		obligadoContabilidad nullable: true
		opcionTarjetaSeleccionada nullable: true
		estadoCuentaDigital nullable: true

		asistenciaExequial nullable: true
		asistProteccionFraudes nullable: true
		tipoCobroProtFraudes nullable: true
		// Seguro Desgravamen
		seguroDesgravamen nullable: true
		tipoDireccionamiento nullable:  true

		/*--Fin Encuesta----*/


		//Campos que SIEMPRE van en la gestión
		fechaGestion nullable: true
		estadoTelefonoContactado nullable: true
		intentos nullable: true
		estadoGestion nullable: true
		subestadoGestion nullable: true
		subSubEstado nullable: true
		usuario nullable: true
		nombreBase nullable: true
		nombreVendedor nullable: true
		fechaRellamada nullable: true
		observaciones nullable: true
		motivoNoDesea nullable: true
		direccionCliente nullable: true
		ciudadDomicilioFvt nullable: true

		cupo1 nullable: true
		cupo2 nullable: true
		cupo3 nullable: true
		cupo4 nullable: true
		sectorDomicilio nullable: true
		sectorTrabajo nullable: true
		nombreRecibeTarjeta nullable: true
		sms nullable: true
		lugarEntrega nullable: true
		horarioEntrega nullable: true
		oficinaTarjeta nullable: true

		opcionCredito  nullable: true
		signatureDocuments  nullable: true
		fechaAgendamiento nullable: true
		numeroContactado nullable: true
        situacionLaboralCredito nullable: true

		vinculoConfianza nullable: true
		contratoPreliminar nullable: true

		vinculoConfianza inList: ['1_SALUDO', '2_IDENTIFICAR SI ES BUEN MOMENTO PARA HABLAR', '3_ESCUCHAR Y COMPRENDER', '4_FRASES APROPIADAS QUE CONECTENY LOGREN INTREACCION, TONALIDAD DE VOZ ADECUADA']
		contratoPreliminar inList: ['5_INDENTIFICA, RESUME EL PROPÓSITO Y TIEMPO DE LA CONVERSACIÓN', '6_RATIFICA QUE ES BUEN MOMENTO PARA HABLAR']

		isActive nullable: true
		telefonoContactado nullable: true
		motivoNoAceptaSeguro nullable: true
		agendamientoAsesor nullable: true
		telefonoConvencionalCredito nullable: true
		telefonoCelularCredito nullable: true
		agencia nullable: true
		registroExitoso nullable: true
		deseaNuevoProducto nullable: true
		motivoSubSubEstado nullable: true
		codigoMarca nullable: true
		planRecompensas nullable: true

		/*Creacion de campos solicitados por el area de Reporting 2019-05-11*/
		gama nullable: true
		regional nullable: true
		rangoEdad nullable: true
		rangoCupo nullable: true
		segmentacionAd1 nullable: true
		segmentacionAd2 nullable: true
		segmentacionAd3 nullable: true
		segmentacionAd4 nullable: true
		segmentacionAd5 nullable: true
		easyCodeRegional nullable: true
		easyCodeEdad nullable: true
		easyCodeCupo nullable: true
		easyCodeEdadCupo nullable: true
		easyCodeGamaEdad nullable: true
		easyCodeAd1 nullable: true
		easyCodeAd2 nullable: true
		easyCodeAd3 nullable: true
		easyCodeAd4 nullable: true
		easyCodeAd5 nullable: true
		prioridadCampania nullable: true
		fechaCaducidad nullable: true
		deudaProtegida nullable: true
		metaContactabilidad nullable: true
		metaEfectividadTelefonica nullable: true
		metaEfectividadReal nullable: true
		tipoGestion nullable: true
		plataforma nullable: true
		bloqueSegmentacion nullable: true
	}
	
	static mapping = {
		version false
		observaciones type: 'text'
	}

	static String[] getFields(){
		String[] fields = ["codigoCampania",
						   "nombreCampania",
						   "identificacion",
						   "baseComercial",
						   "nombre",
						   "clienteBanco",
						   "calificacionBuro",
						   "perfilRiesgoEndeud",
						   "edad",
						   "credConsumEsc1",
						   "cuotaConsumEsc1",
						   "plazoConsumEsc1",
						   "prodConsumEsc1",
						   "garanteConsumEsc1",
						   "tarjetaEsc1",
						   "marcaTarjEsc1",
						   "fliaTarjetaEsc1",
						   "plast1TarjEsc1",
						   "credConsumExclusivo",
						   "cuotaConsumExclusivo",
						   "plazoConsumExclusivo",
						   "prodConsumExclusivo",
						   "garanteConsumExclusivo",
						   "credConsumRol",
						   "cuotaConsumRol",
						   "plazoConsumRol",
						   "prodConsumRol",
						   "garanteConsumRol",
						   "tarjetaExclusiva",
						   "prodTarjetaExclusiva",
						   "marcaTarjetaExclusiva",
						   "fliaTarjetaExclusiva",
						   "plast1TarjExclusiva",
						   "creditoAutomotriz",
						   "cuotaCredAutomotriz",
						   "plazoCredAutomotriz",
						   "entradaAutomotriz",
						   "maximoConsumo",
						   "maximaTarjeta",
						   "codigoCliente",
						   "primerNombre",
						   "segundoNombre",
						   "primerApellido",
						   "segundoApellido",
						   "codigoAgencia",
						   "segmento",
						   "subsegmento",
						   "codigoAsesor",
						   "ciudadAnclaje",
						   "codigoPersona",
						   "tipoIdentificacion",
						   "fechaNacimiento",
						   "sexo",
						   "numeroCuenta",
						   "tipoCuenta",
						   "estadoCivil",
						   "provinciaDomicilio",
						   "telefono1",
						   "telefono2",
						   "telefono3",
						   "telefono4",
						   "telefono5",
						   "telefono6",
						   "telefono7",
						   "telefono8",
						   "telefono9",
						   "telefono10",
						   "email",
						   "ciudadDomicilio",
						   "direccionDomicilio",
						   "provinciaTrabajo",
						   "ciudadTrabajo",
						   "direccionTrabajo",
						   "agencias",
						   "zona",
						   "region",
						   "cupoMaximo",
						   "difCupos",
						   "idConyuge",
						   "nombreConyuge",
						   "codigoAsignacion",
						   "smartPhone",
						   "codigoMarca",
		                   "planRecompensas"
						   , "gama"
						   , "regional"
						   , "rangoEdad"
						   , "rangoCupo"
						   , "segmentacionAd1"
						   , "segmentacionAd2"
						   , "segmentacionAd3"
						   , "segmentacionAd4"
						   , "segmentacionAd5"
						   , "easyCodeRegional"
						   , "easyCodeEdad"
						   , "easyCodeCupo"
						   , "easyCodeEdadCupo"
						   , "easyCodeGamaEdad"
						   , "easyCodeAd1"
						   , "easyCodeAd2"
						   , "easyCodeAd3"
						   , "easyCodeAd4"
						   , "easyCodeAd5"
						   , "prioridadCampania"
						   , "fechaCaducidad"
						   , "deudaProtegida"
						   , "metaContactabilidad"
						   , "metaEfectividadTelefonica"
						   , "metaEfectividadReal"
						   , "tipoGestion"
						   , "plataforma"
						   , "bloqueSegmentacion"
						   , "tipoProducto"
		]
		return fields
	}
	static HashMap getTiposParientes(){
		return ['': '-- Seleccione --', '00':'Ninguno', '01': 'Padre', '02': 'Madre', '03': 'Hermano(a)', '04': 'Primo(a)',
				'05': 'Tío(a)', '06': 'Sobrino(a)', '07': 'Esposa(o)', '08': 'Cuñado', '09': 'Yerno (Nuera)', '10': 'Suegro(a)', '11': 'Hijo(a)',
				'12': 'Amigo(a)', '13': 'Abuelo(a)', '14': 'Novio (a)', '15': 'Nieto (a)', '16': 'Compañero de Trabajo', '17': 'Familiar',
				'18': 'Representante Legal', '19': 'Relación Comercial', '20': 'Relación Laboral', '21': 'Presidente', '22': 'Vice-presidente',
				'23': 'Funcionario', '24': 'Ejecutivo']
	}
}
