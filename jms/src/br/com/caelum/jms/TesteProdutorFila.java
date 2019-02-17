package br.com.caelum.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class TesteProdutorFila {

	public static void main(String[] args) throws Exception {

		InitialContext context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection("user", "senha");
		connection.start();

		Destination fila = (Destination) context.lookup("financeiro");
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		MessageProducer producer = session.createProducer(fila);
//		for (int i = 0; i < 1000; i++) {
//			TextMessage message = session.createTextMessage("" + i);
//			producer.send(message);
//		}

		TextMessage message = session.createTextMessage("666");
		producer.send(message);

		session.close();
		connection.close();
		context.close();
	}

}
