package de.soulworks.dam.webservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author Christian Schmitz <csc@soulworks.de>
 */
public class ExampleMessageListener implements SessionAwareMessageListener {
	private static final Logger log = LoggerFactory.getLogger(ExampleMessageListener.class);
	
	@Override
	public void onMessage(Message message, Session session) throws JMSException {
		log.info("Message received: "+message.toString());
		if (message instanceof TextMessage) {
			try {
				System.out.println(((TextMessage) message).getText());
			}
			catch (JMSException ex) {
				throw new RuntimeException(ex);
			}
		} else {
			throw new IllegalArgumentException("Message must be of type TextMessage");
		}
	}
}
