package callcenter



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

//@Transactional(readOnly = true)
class AdicionalController {

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
        respond Adicional.list(params), model:[adicionalInstanceCount: Adicional.count()]
    }

    def show(Adicional adicionalInstance) {
        respond adicionalInstance
    }

    def create() {
        println("crear adicional")
        respond new Adicional(params)
    }

    @Transactional
    def save(Adicional adicionalInstance) {
        if (adicionalInstance == null) {
            notFound()
            return
        }

        if (adicionalInstance.hasErrors()) {
            respond adicionalInstance.errors, view:'create'
            return
        }

        adicionalInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'adicional.label', default: 'Adicional'), adicionalInstance.id])
                redirect adicionalInstance
            }
            '*' { respond adicionalInstance, [status: CREATED] }
        }
    }

    def edit(Adicional adicionalInstance) {
        respond adicionalInstance
    }

    @Transactional
    def update(Adicional adicionalInstance) {
        if (adicionalInstance == null) {
            notFound()
            return
        }

        if (adicionalInstance.hasErrors()) {
            respond adicionalInstance.errors, view:'edit'
            return
        }

        adicionalInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Adicional.label', default: 'Adicional'), adicionalInstance.id])
                redirect adicionalInstance
            }
            '*'{ respond adicionalInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Adicional adicionalInstance) {

        if (adicionalInstance == null) {
            notFound()
            return
        }

        adicionalInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Adicional.label', default: 'Adicional'), adicionalInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'adicional.label', default: 'Adicional'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
