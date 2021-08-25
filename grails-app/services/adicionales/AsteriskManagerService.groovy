package adicionales

import callcenter.Clientes
import callcenter.Historial
import com.pw.telephony.MuteActionCustom
import grails.transaction.Transactional
import org.asteriskjava.live.internal.AsteriskServerImpl
import org.asteriskjava.manager.ManagerConnection
import org.asteriskjava.manager.ManagerConnectionFactory
import org.asteriskjava.manager.ManagerEventListener
import org.asteriskjava.manager.action.GetVarAction
import org.asteriskjava.manager.action.HangupAction
import org.asteriskjava.manager.action.OriginateAction
import org.asteriskjava.manager.action.QueueAddAction
import org.asteriskjava.manager.action.QueuePauseAction
import org.asteriskjava.manager.action.QueueRemoveAction
import org.asteriskjava.manager.event.ManagerEvent
import org.asteriskjava.manager.event.VarSetEvent
import org.asteriskjava.manager.response.ManagerResponse
import telephony.LastUniqueId

@Transactional
class AsteriskManagerService implements ManagerEventListener {

    static final UNIQUEIDVAR = "UNIQUEID"

    ManagerConnection managerConnection

    def initialize(){
        print "Connecting to Asterisk!!!!"
        //connection()
    }

    /**
     * Make a call
     * @author Esteban Preciado
     * @param min
     * @param extent
     * @return
     */
    def call(String min, String extent,String manageId){
        OriginateAction originateAction

        try{
            //Instance originate action

            ManagerResponse originateResponse

            //Parameters for generate call
            originateAction = new OriginateAction()
            originateAction.setChannel("SIP/" + extent)
            originateAction.setContext("from-internal")
            originateAction.setExten(min)
            originateAction.setPriority(new Integer(1))
            originateAction.setAsync(true)
            originateAction.setVariable("manageId",manageId)


            originateResponse = managerConnection.sendAction(originateAction)

            //Get UniqueID Variable
            String channelReturned = getCompleteChannel(extent, managerConnection)
            def uniqueId = getVarAction(UNIQUEIDVAR, channelReturned)

            // print out whether the originate succeeded or not
            return uniqueId

        }catch(Exception e){
            println "*****"+e
            //TODO: Cancel action when timeout
            return "TIMEOUT"
        }
    }

    /**
     * Hangout a call
     * @author Esteban Preciado
     * @param channel
     * @return
     */
    def hangup(String extent){
        print "hanging up "+extent

        try{
            HangupAction hangoutAction
            ManagerResponse originateResponse
            String channelReturned = getCompleteChannel(extent, managerConnection)
            println "*****"+channelReturned

            if(channelReturned != null){

                hangoutAction = new HangupAction()
                hangoutAction.setChannel(channelReturned)

                managerConnection.getState()
                originateResponse = managerConnection.sendAction(hangoutAction, 30000)

                // print out whether the originate succeeded or not
                println "=="+originateResponse.getUniqueId()
            }
        }catch(Exception e){
            println e
        }
    }

    /**
     * Pause/Resume a member in a queue
     * @author Esteban Preciado
     * @param channel
     * @param paused
     * @return
     */
    def queuePause(String channel, boolean paused){

        try{
            QueuePauseAction queuePauseAction;
            ManagerResponse originateResponse;

            queuePauseAction = new QueuePauseAction()
            queuePauseAction.setInterface("SIP/" + channel)
            queuePauseAction.setPaused(paused)

            originateResponse = managerConnection.sendAction(queuePauseAction, 30000)

            // print out whether the originate succeeded or not
            println originateResponse.getMessage()

        }catch(Exception e){
            println e
        }
    }

    /**
     * Login a member of the queue
     * @author Esteban Preciado
     * @param queueNumber
     * @param channel
     * @return
     */
    def queueAdd(String queueNumber, String channel){

        try{
            QueueAddAction queueAddAction;
            ManagerResponse originateResponse;

            queueAddAction = new QueueAddAction()
            queueAddAction.setQueue(queueNumber)
            queueAddAction.setInterface("SIP/" + channel)
            queueAddAction.setPaused(false)
            queueAddAction.setPenalty(0)

            originateResponse = managerConnection.sendAction(queueAddAction, 30000)

            // print out whether the originate succeeded or not
            println originateResponse.getMessage()

        }catch(Exception e){
            println e
        }
    }

    /**
     * Logout a member of the queue
     * @author Esteban Preciado
     * @param queueNumber
     * @param channel
     * @return
     */
    def queueRemove(String queueNumber, String channel){

        try{
            QueueRemoveAction queueRemoveAction;
            ManagerResponse originateResponse;

            queueRemoveAction = new QueueRemoveAction()
            queueRemoveAction.setQueue(queueNumber)
            queueRemoveAction.setInterface("SIP/" + channel)

            originateResponse = managerConnection.sendAction(queueRemoveAction, 30000)

            // print out whether the originate succeeded or not
            println originateResponse.getMessage()

        }catch(Exception e){
            println e
        }
    }

    /**
     * Spy call in live through spychan
     * @author epreciado
     * @param channel
     * @param spyChannel
     * @return
     */
    def spyCalling(String channel, String spyChannel){

        try{
            //Instance originate action
            OriginateAction originateAction
            ManagerResponse originateResponse

            //Parameters for generate call
            originateAction = new OriginateAction()
            originateAction.setChannel(channel)
            originateAction.setApplication("ChanSpy")
            originateAction.setData(spyChannel)
            originateAction.setAsync(true)

            originateResponse = managerConnection.sendAction(originateAction, 30000)

            // print out whether the originate succeeded or not
            println originateResponse.getMessage()

        }catch(Exception e){
            println e
        }
    }

    /**
     * Mute/Unmute the channel in out direction, the destination channel not hear
     * @author epreciado
     * @param channel
     * @param state
     * @return
     */
    def muteChannel(String extent, String state){

        try{
            final direction = "in" //variable for mute destination channel

            MuteActionCustom muteAudioAction = new MuteActionCustom();
            ManagerResponse originateResponse

            //Get a complete channel
            String channelReturned = getCompleteChannel(extent, managerConnection)
            println channelReturned;

            if(channelReturned != null){
                muteAudioAction.setChannel(channelReturned)
                muteAudioAction.setDirection(direction)
                muteAudioAction.setState(state)

                originateResponse = managerConnection.sendAction(muteAudioAction, 30000)

                // print out whether the originate succeeded or not
                println managerConnection.getAttributes()
            }

        }catch(Exception e){
            println e
        }
    }

    /**
     * Get asterisk vars
     * @author epreciado & ccobo
     * @param channel
     * @return
     */
    def getVarAction(String var, String channel){

        try{
            GetVarAction getVarAction = new GetVarAction();
            ManagerResponse originateResponse
            getVarAction.setChannel(channel)
            getVarAction.setVariable(var)

            originateResponse = managerConnection.sendAction(getVarAction)

            // print out whether the originate succeeded or not
            return originateResponse.getAttributes().get("value")
        }catch(Exception e){
            println e
        }
    }

    /**
     * This  function allow an automatic dial through festival
     * @author Esteban Preciado
     * @param min
     * @param text
     * @return
     */
    static textToSpeech(String min, String text){

        final sourcePath = "/home/epreciado/Desktop/"
        final destinationPath = "/var/spool/asterisk/outgoing/"
        final nameFile = "automaticDialCall.call"
        def builder = new AntBuilder()

        //Create the call file
        File file = new File(sourcePath + nameFile)
        file << 'channel: SIP/gw/'+min+'\n'
        file << 'Application: festival\n'
        file << 'Data: ' + text

        //Move the file to /var/spool/asterisk/outgoing
        builder.sequential {
            move(file: sourcePath + nameFile, tofile: destinationPath + nameFile)
        }
    }

    /**
     * Make a connection with Asterisk Manager
     * @author epreciado
     * @return
     */
    def connection(){

        //Connection to asterisk
        //development
        ManagerConnectionFactory factory = new ManagerConnectionFactory(
                "localhost", "admin", "Administrador2017PW")
        //Production
//		ManagerConnectionFactory factory = new ManagerConnectionFactory(
//				"localhost", "admin", "sIst2m1s2020")

        managerConnection = factory.createManagerConnection()
        managerConnection.addEventListener(this)

        if(managerConnection.state.toString().contains('INITIAL') || managerConnection.state.toString().contains('DISCONNECTED')){
            managerConnection.login()
        }
    }

    /**
     * Disconnect from Asterisk Manager
     * @author epreciado
     * @return
     */
    def disconnection(){
        managerConnection.logoff()
    }

    /**
     * Get a current channel from Asterisk Manager
     * @param extent
     * @return
     */
    private static getCompleteChannel(String extent, ManagerConnection connection){
        print "CHANNELS "
        AsteriskServerImpl asteriskServer = new AsteriskServerImpl(connection)
        def channels = asteriskServer.getChannels()
        print "CHANNELS "+channels
        def channelLocated

        for (channel in channels){
            if(channel.name.contains("SIP/" + extent + "-")){
                channelLocated = channel.name
            }
        }

        return channelLocated
    }

    @Override
    void onManagerEvent(ManagerEvent event) {
//		println "==!!=="+event
//		print event
//		print event
        if(event.toString().contains("manageId")){
            VarSetEvent varEvent = (VarSetEvent) event
            print event
            if(varEvent.getVariable() == "manageId"){
                print "Set UNIQUE ID "+varEvent.getUniqueId()+" on MANAGEMENT: "+varEvent.getValue()
                if(varEvent.getValue() != null && varEvent.getValue().length() > 0){
                    def client = Clientes.findById(Long.parseLong(varEvent.getValue()))
                    def lastUniqueId = LastUniqueId.findByClient(client)

                    if(lastUniqueId == null){
                        lastUniqueId = new LastUniqueId(
                                uniqueId:  varEvent.getUniqueId(),
                                client: client,
                        )
                    }else{
                        lastUniqueId.uniqueId = varEvent.getUniqueId()
                    }
                    lastUniqueId.save(flush: true)
                }else{

                }
            }
        }
    }
}