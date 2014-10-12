package com.hascode.app;

import java.time.Instant;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.xmpp.XmppMessage;
import org.apache.camel.main.Main;
import org.jivesoftware.smack.packet.Message;

import com.tngtech.configbuilder.ConfigBuilder;

public class Bot {
	public static void main(final String[] args) throws Exception {
		new Bot().run();
	}

	private void run() throws Exception {
		Config config = new ConfigBuilder<Config>(Config.class).build();
		String chatEndpoint = String.format("xmpp://%s@%s:%s?room=%s&password=%s&nickname=%s", config.getUser(), config.getHost(), config.getPort(), config.getRoom(), config.getPassword(),
				config.getNickname());

		Main main = new Main();
		main.addRouteBuilder(new RouteBuilder() {
			@Override
			public void configure() {
				from(chatEndpoint).filter(exchange -> isValidBotCommand(exchange)).choice().when(exchange -> getSmackMessage(exchange).getBody().contains("date"))
						.setBody(constant("It's " + Instant.now())).to(chatEndpoint).otherwise().setBody(constant("Available commands: bot date, bot help")).to(chatEndpoint);
			}
		});
		main.enableHangupSupport();
		main.run();
	}

	private boolean isValidBotCommand(final Exchange exchange) {
		return getSmackMessage(exchange).getBody().matches("bot (date|help)");
	}

	private Message getSmackMessage(final Exchange exchange) {
		XmppMessage xmppMessage = (XmppMessage) exchange.getIn();
		Message smackMessage = xmppMessage.getXmppMessage();
		return smackMessage;
	}

}
