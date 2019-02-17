package br.com.caelum.jms;

import java.io.StringWriter;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.xml.bind.JAXB;

import br.com.caelum.modelo.Pedido;
import br.com.caelum.modelo.PedidoFactory;

public class TesteProdutorTopico {

	public static void main(String[] args) throws Exception {

		InitialContext context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection("user", "senha");
		connection.start();

		Destination topico = (Destination) context.lookup("loja");
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		MessageProducer producer = session.createProducer(topico);
		PedidoFactory pedidoFactory = new PedidoFactory();
		Pedido pedido = pedidoFactory.geraPedidoComValores();
		ObjectMessage message = session.createObjectMessage(pedido);
		
		
//		for (int i = 0; i < 1000; i++) {
//		StringWriter writer = new StringWriter();
//		JAXB.marshal(pedido, writer);
//		String xml = writer.toString();
//			TextMessage message = session.createTextMessage(xml);
//			message.setBooleanProperty("estoque", false);
			producer.send(message);
//		}

		session.close();
		connection.close();
		context.close();
	}

}
