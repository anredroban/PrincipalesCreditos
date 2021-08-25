package telephony

import adicionales.AsteriskManagerService
import com.pw.telephony.Asterisk

class AccionesAsteriskController {

	AsteriskManagerService asteriskManagerService

	/**
	 * This functions make a call to Asterisk functions to interact with Asterisk Manager
	 * @author epreciado
	 * @return
	 */
	//make a call from
	def makeACall() {
		asteriskManagerService.call(params.numero, params.exten, session.getAttribute("lastManageId").toString())

	}

	//spy a channel from crm
	def makeASpyChannel(){
		//exten = spy channel or extent
		//min = channel spied
		asteriskManagerService.spyCalling(params.exten, params.min)
		render "Spying channel"
	}

	//hangup a call
	def makeAHangup(){
		asteriskManagerService.hangup(params.extension)
		render "Hangup"
	}

	//mute/unmute channel from crm
	def muteAChannel(){
		asteriskManagerService.muteChannel(params.extension, params.muted)

		if(params.muted.toString().equals("on")){
			render "Muted Channel"
		}else if(params.muted.toString().equals("off")){
			render "Unmuted Channel"
		}
	}

	//put a member in pause/unpause from crm
	//only extent number
	def memberPause(){
		asteriskManagerService.queuePause(params.extension, params.paused.toString().toBoolean())

		if(params.paused.toString().equals("true")){
			render "Member paused"
		}else if(params.paused.toString().equals("false")){
			render "Member unpaused"
		}
	}

	//Add a member/extension to the queue
	def memberAddQueue(){
		asteriskManagerService.queueAdd(params.queue, params.extension)
		render "Logged in queue"
	}

	//Logoff a member from crm
	//only extent number
	def memberRemoveQueue(){
		asteriskManagerService.queueRemove(params.queue, params.extension)
		render "Logoff in queue"
	}

	//Make an automatic call through call file, sending to outgoing folder
	def automaticDialer(String min, String text){
		asteriskManagerService.textToSpeech(min, text)
	}
}
