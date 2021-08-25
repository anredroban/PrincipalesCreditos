package callcenter



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import com.pw.security.*

@Transactional(readOnly = true)
class SubestadoController {

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
        respond Subestado.list(params), model:[subestadoInstanceCount: Subestado.count()]
    }

    def show(Subestado subestadoInstance) {
        respond subestadoInstance
    }

    def create() {
        respond new Subestado(params)
    }

    @Transactional
    def save(Subestado subestadoInstance) {
        if (subestadoInstance == null) {
            notFound()
            return
        }

        if (subestadoInstance.hasErrors()) {
            respond subestadoInstance.errors, view:'create'
            return
        }

        subestadoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'subestado.label', default: 'Subestado'), subestadoInstance.id])
                redirect subestadoInstance
            }
            '*' { respond subestadoInstance, [status: CREATED] }
        }
    }

    def edit(Subestado subestadoInstance) {
        respond subestadoInstance
    }

    @Transactional
    def update(Subestado subestadoInstance) {
        if (subestadoInstance == null) {
            notFound()
            return
        }

        if (subestadoInstance.hasErrors()) {
            respond subestadoInstance.errors, view:'edit'
            return
        }

        subestadoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Subestado.label', default: 'Subestado'), subestadoInstance.id])
                redirect subestadoInstance
            }
            '*'{ respond subestadoInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Subestado subestadoInstance) {

        if (subestadoInstance == null) {
            notFound()
            return
        }

        subestadoInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Subestado.label', default: 'Subestado'), subestadoInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'subestado.label', default: 'Subestado'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
