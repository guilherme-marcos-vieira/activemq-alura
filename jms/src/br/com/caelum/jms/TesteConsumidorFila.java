package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class TesteConsumidorFila {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {

		InitialContext context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection("user", "senha");
		connection.start();

		Destination fila = (Destination) context.lookup("financeiro");
		Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
		MessageConsumer consumer = session.createConsumer(fila);
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				try {
					TextMessage textMessage = TextMessage.class.cast(message);
					System.out.println("Mensagem: " + textMessage.getText());
					session.commit();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					try {
						session.rollback();
					} catch (JMSException e1) {
						e1.printStackTrace();
					}
				}

			}
		});

		new Scanner(System.in).nextLine();

		session.close();
		connection.close();
		context.close();
	}

}
