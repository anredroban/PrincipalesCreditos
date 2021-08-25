package telephony

import grails.converters.JSON

class AjaxAgenteController {

    def index() { }
	
	def getMotivosPausa(){

		def resultado = [:]
		def motivosPausa = Break.findAll()

		for(int i = 0; i < motivosPausa.size(); i++){
			resultado.put(motivosPausa.get(i).id, motivosPausa.get(i).name)
		}

		render resultado as JSON
	}
	
}
