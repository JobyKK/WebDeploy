package demo.jms_service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import demo.service.ServerReceiveException;


@Component
public class MessageSender {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	private String url = "tcp://46.101.138.142:61616";
	
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public Map<String, Object> send(Map<String, Object> request) throws JMSException, ServerReceiveException {
    	
    	ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        
    	Connection connection = connectionFactory.createConnection();
        connection.setClientID("ProducerSynchronous");
        connection.start();
       
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue sendQueue = session.createQueue("sendQueue");
        Queue replyQueue = session.createQueue("replyQueue");
        	  
        MessageProducer producer = session.createProducer(sendQueue);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        
        //generate unique number for each request
        String correlationId = String.valueOf(new Random().nextInt(32));
        MessageConsumer replyConsumer = session.createConsumer(replyQueue,
        		"JMSCorrelationID = '" + correlationId + "'");
        
        //map to json
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
			 json = mapper.writeValueAsString(request);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	    TextMessage textMessage = session.createTextMessage(json);
	    textMessage.setJMSReplyTo(replyQueue);
	    textMessage.setJMSCorrelationID(correlationId);
        			 
	    System.out.println("Sending message...");
        			 
	    producer.send(textMessage);
        			 
	    System.out.println("Waiting for reply...");
        			 
	    Message reply = replyConsumer.receive(0);
	    TextMessage msg = (TextMessage)reply;
	    System.out.println("Message Received :" + msg.getText());

	    replyConsumer.close();
	    connection.close();
	    
	    //json to map
	    Map<String, Object> model = new HashMap<String, Object>();
	    try {
			model = mapper.readValue(msg.getText(), 
					new TypeReference<HashMap<String, Object>>(){});
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    if(false == (Boolean)model.get("status")){
	    	throw new ServerReceiveException((String) model.get("message"));
	    }
	    
	    return model;
    }
    
	
}
