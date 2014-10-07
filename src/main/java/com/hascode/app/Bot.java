package com.hascode.app;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import com.tngtech.configbuilder.ConfigBuilder;

public class Bot {
	public static void main(final String[] args) throws Exception {
		Config config = new ConfigBuilder<Config>(Config.class).build();
		String route = String.format("xmpp://%s@%s:%s/?room=%s&password=%s&nickname=%s", config.getUser(), config.getHost(), config.getPort(), config.getRoom(), config.getPassword(),
				config.getNickname());
		CamelContext context = new DefaultCamelContext();
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
		context.addComponent("activemq", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() {
				from(route).to("activemq:chat.room");
				from("timer://kickoff?period=20000").setBody(constant("Domo arigato mr roboto...")).to(route);
			}
		});
		context.start();
		while (true) {

		}
	}
}
