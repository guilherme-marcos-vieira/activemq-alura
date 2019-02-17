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
import javax.jms.Topic;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;

public class TesteConsumidorTopicoEstoque {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {

		InitialContext context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection("user", "senha");
		connection.setClientID("estoque");
		connection.start();

		Topic topico = (Topic) context.lookup("loja");
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		TopicSubscriber subscriber = session.createDurableSubscriber(topico, "assinatura", "estoque is null OR estoque=true", false);
		subscriber.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				try {
					TextMessage textMessage = TextMessage.class.cast(message);
					System.out.println("Mensagem: " + textMessage.getText());
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
