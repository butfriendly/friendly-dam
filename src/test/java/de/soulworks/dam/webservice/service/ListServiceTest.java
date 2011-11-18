package de.soulworks.dam.webservice.service;

import de.soulworks.dam.webservice.ServiceTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @author Christian Schmitz <csc@soulworks.de
 */
public class ListServiceTest extends ServiceTest {
	private static final Logger log = LoggerFactory.getLogger(ListServiceTest.class);
	
	@Autowired
	private JmsTemplate myJmsTemplate;

	@Test
	public void testSomethin() {
		myJmsTemplate.send("mms.dfdf", new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage("Hallo Welt");
			}
		});

	}

	@Test
	public void read_from_queue() {
		try {
			Message msg = myJmsTemplate.receive("mms.dfdf");
			log.info(msg.toString());
			msg.acknowledge();
		} catch (JMSException e) {
		}
	}
}
