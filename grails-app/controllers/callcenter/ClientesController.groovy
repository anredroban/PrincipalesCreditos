package callcenter

import static org.springframework.http.HttpStatus.*
import grails.converters.JSON
import grails.transaction.Transactional
import java.awt.image.BufferedImage
import java.text.DateFormat
import java.text.SimpleDateFormat
import com.pw.security.*

@Transactional
class ClientesController {

	def BuscarRellamadaService

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	//Evita que ingrese mediante url
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

	def index(Integer max) {
		params.max = Math.min(max ?: 10, 100)

		//Obtiene los clientes que no se han gestionado
		def clientes = Clientes.withCriteria {
			eq("gestionado", false)
			eq("nombreVendedor", session.user.nombre)
		} as List

		respond clientes, model:[clientesInstanceCount: Clientes.count()]
	}

	def show(Clientes clientesInstance) {
		respond clientesInstance
	}

	def create() {
		respond new Clientes(params)
	}

	@Transactional
	def save(Clientes clientesInstance) {
		if (clientesInstance == null) {
			notFound()
			return
		}

		if (clientesInstance.hasErrors()) {
			respond clientesInstance.errors, view:'create'
			return
		}

		clientesInstance.save flush:true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.created.message', args: [
					message(code: 'clientes.label', default: 'Clientes'),
					clientesInstance.id
				])
				redirect clientesInstance
			}
			'*' { respond clientesInstance, [status: CREATED] }
		}
	}

	def edit(Clientes clientesInstance) {
		respond clientesInstance
	}

	@Transactional
	def update(Clientes clientesInstance) {

		
		if (clientesInstance == null) {
			notFound()
			return
		}

		if (clientesInstance.hasErrors()) {
			respond clientesInstance.errors, view:'edit'
			return
		}

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.updated.message', args: [
					message(code: 'Clientes.label', default: 'Clientes'),
					clientesInstance.id
				])
				redirect action:"index"
			}
			'*'{ respond clientesInstance, [status: OK] }
		}
	}

	@Transactional
	def delete(Clientes clientesInstance) {

		if (clientesInstance == null) {
			notFound()
			return
		}

		clientesInstance.delete flush:true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.deleted.message', args: [
					message(code: 'Clientes.label', default: 'Clientes'),
					clientesInstance.id
				])
				redirect action:"index", method:"GET"
			}
			'*'{ render status: NO_CONTENT }
		}
	}
	
	/**
	 * Genera el contrato que se imprimira con los datos del cliente
	 * @author Cesar Cobo
	 * @return
	 */
	def contract(){
		def CELL_DEFAULT_HEIGHT = 17;
		def CELL_DEFAULT_WIDTH = 64;

		def client = Clientes.findById(params.idClient.toString().toInteger())
		def plan = Planes.findByPlan(client.velocidadInternet)		
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");		
		Date date = formatter.parse(client.horario);		
		render(view: "contract", model: [clients: client, date:date, plan:plan])
	}

	/**
	 * Obtiene los datos del cliente 
	 * @return
	 */
	def getDataClient(){
		if(request.xhr) {
			def idCliente = HojaRuta.findByBarcode(params.codigo)
			
			if(idCliente){
				def client = Clientes.findById(idCliente.idCliente)
				
				if(client){
					render client as JSON
				}else{
					render false
				}
			}else{
				render false
			}
		}
	}

	/**
	 * Obtiene los datos del cliente buscado por un id
	 * @return
	 */
	def getDataClientId(){
		if(request.xhr) {
			def client = Clientes.findById(params.id)

			render client as JSON
		}
	}

	protected void notFound() {
		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.not.found.message', args: [
					message(code: 'clientes.label', default: 'Clientes'),
					params.id
				])
				redirect action: "index", method: "GET"
			}
			'*'{ render status: NOT_FOUND }
		}
	}
}
