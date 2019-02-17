package br.com.caelum.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class TesteProdutorFilaLog {

	public static void main(String[] args) throws Exception {

		InitialContext context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection("user", "senha");
		connection.start();

		Destination fila = (Destination) context.lookup("log");
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		MessageProducer producer = session.createProducer(fila);

		TextMessage message = session.createTextMessage("666");
		producer.send(message, DeliveryMode.NON_PERSISTENT, 0, 5000);

		session.close();
		connection.close();
		context.close();
	}

}
