package telephony

class RecordingsController {

	def recordings() {

	}

	/**
	 * Download a wav file.
	 * @author Esteban Preciado
	 * @return
	 */
	def downloadRecorder(){
		
			InputStream contentStream
			
			try {
				def file = new File("/home/epreciado/Desktop/" + params.nameFile)
				response.setHeader "Content-disposition", "attachment; filename=" + params.nameFile
				response.setHeader("Content-Length", "file-size")
				response.setContentType("audio/mpeg")
				contentStream = file.newInputStream()
				response.outputStream << contentStream
				webRequest.renderView = false
			}catch(Exception e){
				println e
			}	
	}
}

