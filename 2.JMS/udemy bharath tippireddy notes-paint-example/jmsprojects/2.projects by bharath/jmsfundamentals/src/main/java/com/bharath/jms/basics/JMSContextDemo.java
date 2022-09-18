package com.bharath.jms.basics;

import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

/**
This is written with jms 2.x framework
*/
public class JMSContextDemo {

	public static void main(String[] args) throws NamingException {
		
		//This will make use of details present in jndi.properties 
		InitialContext context = new InitialContext();
		//this will get the queue object fromjndi registry
		Queue queue = (Queue) context.lookup("queue/myQueue");
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext()){
			jmsContext.createProducer().send(queue,"Arise Awake and stop not till the goal is reached");
			String messageReceived = jmsContext.createConsumer(queue).receiveBody(String.class);
			System.out.println(messageReceived);
		}

	}

}
