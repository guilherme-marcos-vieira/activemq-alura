package br.com.caelum.jms;

import java.io.Serializable;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;

import br.com.caelum.modelo.Pedido;

public class TesteConsumidorTopicoComercial {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","*");

		InitialContext context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection("user", "senha");
		connection.setClientID("comercial");
		connection.start();

		Topic topico = (Topic) context.lookup("loja");
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		TopicSubscriber subscriber = session.createDurableSubscriber(topico, "assinatura");
		subscriber.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				try {
					ObjectMessage textMessage = ObjectMessage.class.cast(message);
					Pedido pedido = Pedido.class.cast(textMessage.getObject());
					System.out.println("Codigo: " + pedido.getCodigo());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		new Scanner(System.in).nextLine();

		session.close();
		connection.close();
		context.close();
	}

}
