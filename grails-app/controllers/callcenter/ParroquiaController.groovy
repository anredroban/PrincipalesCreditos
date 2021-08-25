package callcenter



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ParroquiaController {

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
        respond Parroquia.list(params), model:[parroquiaInstanceCount: Parroquia.count()]
    }

    def show(Parroquia parroquiaInstance) {
        respond parroquiaInstance
    }

    def create() {
        respond new Parroquia(params)
    }

    @Transactional
    def save(Parroquia parroquiaInstance) {
        if (parroquiaInstance == null) {
            notFound()
            return
        }

        if (parroquiaInstance.hasErrors()) {
            respond parroquiaInstance.errors, view:'create'
            return
        }

        parroquiaInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'parroquia.label', default: 'Parroquia'), parroquiaInstance.id])
                redirect parroquiaInstance
            }
            '*' { respond parroquiaInstance, [status: CREATED] }
        }
    }

    def edit(Parroquia parroquiaInstance) {
        respond parroquiaInstance
    }

    @Transactional
    def update(Parroquia parroquiaInstance) {
        if (parroquiaInstance == null) {
            notFound()
            return
        }

        if (parroquiaInstance.hasErrors()) {
            respond parroquiaInstance.errors, view:'edit'
            return
        }

        parroquiaInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Parroquia.label', default: 'Parroquia'), parroquiaInstance.id])
                redirect parroquiaInstance
            }
            '*'{ respond parroquiaInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Parroquia parroquiaInstance) {

        if (parroquiaInstance == null) {
            notFound()
            return
        }

        parroquiaInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Parroquia.label', default: 'Parroquia'), parroquiaInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'parroquia.label', default: 'Parroquia'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
