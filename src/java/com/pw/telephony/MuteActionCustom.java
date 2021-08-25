package com.pw.telephony;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.asteriskjava.manager.action.AbstractManagerAction;
import org.asteriskjava.manager.action.EventGeneratingAction;
import org.asteriskjava.manager.event.ResponseEvent;


/**
 * Class MuteAction allow mute/unmute the destination channel 
 * This class was created because the original class not worked correctly
 * @author Cesar Cobo 
 */
public class MuteActionCustom extends AbstractManagerAction implements EventGeneratingAction
{

    private final Log logger = LogFactory.getLog(getClass());

    /**
     * Serializable version identifier
     */
    static final long serialVersionUID = 8194597741743334704L;

    private String channel;
    private String direction;
    private String state;
    private Long timeout;

    /**
     * Returns the name of this action, i.e. "Originate".
     *
     * @return the name of this action.
     */
    @Override
    public String getAction()
    {
        return "MuteAudio";
    }

    /**
     * Returns the name of the channel to connect to the outgoing call.
     */
    public String getChannel()
    {
        return channel;
    }
    
    public void setChannel(String channel)
    {
        this.channel = channel;
    }
    
    public void setDirection(String direction)
    {
    	this.direction = direction;
    }
    
    public String getDirection()
    {
    	return direction;
    }
    
    public void setState(String state)
    {
    	this.state = state;
    }
    
    public String getState()
    {
    	return state;
    }

    /**
     * Sets the timeout (in milliseconds) for the origination.
     * <p>
     * The channel must be answered within this time, otherwise the origination
     * is considered to have failed and an OriginateFailureEvent is generated.
     * <p>
     * If not set, Asterisk assumes a default value of 30000 meaning 30 seconds.
     *
     * @param timeout the timeout in milliseconds
     * @deprecated use {@link #setTimeout(Long)} instead.
     */
    @Deprecated
    public void setTimeout(Integer timeout)
    {
        if (timeout != null)
        {
            if (timeout < 1000)
            {
                logger.error("A timeout of 1000 will cause the originate to almost cretainly fail!");
            }
            if (timeout < 10000)
            {
                logger.warn(
                        "A timeout of less than 10000 will cause the originate to fail if not answered within 10 seconds!");
            }
            this.timeout = timeout.longValue();
        }
        else
        {
            this.timeout = null;
        }
    }

    /**
     * Sets the timeout (in milliseconds) for the origination.
     * <p>
     * The channel must be answered within this time, otherwise the origination
     * is considered to have failed and an OriginateFailureEvent is generated.
     * <p>
     * If not set, Asterisk assumes a default value of 30000 meaning 30 seconds.
     *
     * @param timeout the timeout in milliseconds
     */
    public void setTimeout(Long timeout)
    {
        if (timeout != null)
        {
            if (timeout < 1000)
            {
                logger.error("A timeout of 1000 will cause the originate to almost cretainly fail!");
            }
            if (timeout < 10000)
            {
                logger.warn(
                        "A timeout of less than 100000 will cause the originate to fail if not answered within 10 seconds!");
            }
        }
        this.timeout = timeout;
    }

	@Override
	public Class<? extends ResponseEvent> getActionCompleteEventClass() {
		// TODO Auto-generated method stub
		return null;
	}
}