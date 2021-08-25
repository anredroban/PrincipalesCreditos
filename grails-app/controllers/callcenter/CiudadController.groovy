package callcenter



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class CiudadController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    //Evita que ingrese mediante url
    static beforeInterceptor = {
        String accion = actionUri;
        if (!accion.equals("/usuario/login") && !accion.equals("/usuario/logout")) {
            if (!session.user) {
                redirect(uri: "/usuario/login");
                return false;
            } else {
                boolean tienePermiso = utilitarios.Util.checkAccess(session.user.usuario, accion)
                if (!tienePermiso) {
                    render "No tienes permiso para ingresar a este sitio-> " + accion;
                }
            }
        }
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Ciudad.list(params), model:[ciudadInstanceCount: Ciudad.count()]
    }

    def show(Ciudad ciudadInstance) {
        respond ciudadInstance
    }

    def create() {
        respond new Ciudad(params)
    }

    @Transactional
    def save(Ciudad ciudadInstance) {
        if (ciudadInstance == null) {
            notFound()
            return
        }

        if (ciudadInstance.hasErrors()) {
            respond ciudadInstance.errors, view:'create'
            return
        }

        ciudadInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'ciudad.label', default: 'Ciudad'), ciudadInstance.id])
                redirect ciudadInstance
            }
            '*' { respond ciudadInstance, [status: CREATED] }
        }
    }

    def edit(Ciudad ciudadInstance) {
        respond ciudadInstance
    }

    @Transactional
    def update(Ciudad ciudadInstance) {
        if (ciudadInstance == null) {
            notFound()
            return
        }

        if (ciudadInstance.hasErrors()) {
            respond ciudadInstance.errors, view:'edit'
            return
        }

        ciudadInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Ciudad.label', default: 'Ciudad'), ciudadInstance.id])
                redirect ciudadInstance
            }
            '*'{ respond ciudadInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Ciudad ciudadInstance) {

        if (ciudadInstance == null) {
            notFound()
            return
        }

        ciudadInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Ciudad.label', default: 'Ciudad'), ciudadInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'ciudad.label', default: 'Ciudad'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
