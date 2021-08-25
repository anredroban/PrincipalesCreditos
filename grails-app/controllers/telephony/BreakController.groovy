package telephony

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class BreakController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
	
//	static beforeInterceptor = {
//		if(!session.user){
//			redirect(uri: "/usuario/login");
//			return false;
//		}else{
//			String accion = actionUri;
//			boolean tienePermiso = false;
//			def rol = Rol.findById(session.user.rolId);
//			def permiso = Permiso.findByDescripcion(accion);
//			//Si el permiso no está creado se lo ingresa a la BDD
//			if(permiso == null){
//				Permiso p = new Permiso();
//				p.descripcion = accion;
//				p.save(flush: true);
//			}
//			for(perm in rol.permisos){
//				if(perm == permiso){
//					tienePermiso = true;
//					break;
//				}
//			}
//
//			if(!tienePermiso){
//				render "No tienes permiso para ingresar a este sitio";
//			}
//		}
//	}

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Break.list(params), model:[breakInstanceCount: Break.count()]
    }

    def show(Break breakInstance) {
        respond breakInstance
    }

    def create() {
        respond new Break(params)
    }

    @Transactional
    def save(Break breakInstance) {
        if (breakInstance == null) {
            notFound()
            return
        }

        if (breakInstance.hasErrors()) {
            respond breakInstance.errors, view:'create'
            return
        }

        breakInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'break.label', default: 'Break'), breakInstance.id])
                redirect breakInstance
            }
            '*' { respond breakInstance, [status: CREATED] }
        }
    }

    def edit(Break breakInstance) {
        respond breakInstance
    }

    @Transactional
    def update(Break breakInstance) {
        if (breakInstance == null) {
            notFound()
            return
        }

        if (breakInstance.hasErrors()) {
            respond breakInstance.errors, view:'edit'
            return
        }

        breakInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Break.label', default: 'Break'), breakInstance.id])
                redirect breakInstance
            }
            '*'{ respond breakInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Break breakInstance) {

        if (breakInstance == null) {
            notFound()
            return
        }

        breakInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Break.label', default: 'Break'), breakInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'break.label', default: 'Break'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
