package callcenter



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import com.pw.security.*

@Transactional(readOnly = true)
class MotivosnoaceptaController {

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
        respond Motivosnoacepta.list(params), model:[motivosnoaceptaInstanceCount: Motivosnoacepta.count()]
    }

    def show(Motivosnoacepta motivosnoaceptaInstance) {
        respond motivosnoaceptaInstance
    }

    def create() {
        respond new Motivosnoacepta(params)
    }

    @Transactional
    def save(Motivosnoacepta motivosnoaceptaInstance) {
        if (motivosnoaceptaInstance == null) {
            notFound()
            return
        }

        if (motivosnoaceptaInstance.hasErrors()) {
            respond motivosnoaceptaInstance.errors, view:'create'
            return
        }

        motivosnoaceptaInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'motivosnoacepta.label', default: 'Motivosnoacepta'), motivosnoaceptaInstance.id])
                redirect motivosnoaceptaInstance
            }
            '*' { respond motivosnoaceptaInstance, [status: CREATED] }
        }
    }

    def edit(Motivosnoacepta motivosnoaceptaInstance) {
        respond motivosnoaceptaInstance
    }

    @Transactional
    def update(Motivosnoacepta motivosnoaceptaInstance) {
        if (motivosnoaceptaInstance == null) {
            notFound()
            return
        }

        if (motivosnoaceptaInstance.hasErrors()) {
            respond motivosnoaceptaInstance.errors, view:'edit'
            return
        }

        motivosnoaceptaInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Motivosnoacepta.label', default: 'Motivosnoacepta'), motivosnoaceptaInstance.id])
                redirect motivosnoaceptaInstance
            }
            '*'{ respond motivosnoaceptaInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Motivosnoacepta motivosnoaceptaInstance) {

        if (motivosnoaceptaInstance == null) {
            notFound()
            return
        }

        motivosnoaceptaInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Motivosnoacepta.label', default: 'Motivosnoacepta'), motivosnoaceptaInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'motivosnoacepta.label', default: 'Motivosnoacepta'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
