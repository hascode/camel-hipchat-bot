package com.hascode.app;

import com.tngtech.configbuilder.annotation.propertyloaderconfiguration.PropertiesFiles;
import com.tngtech.configbuilder.annotation.valueextractor.PropertyValue;

@PropertiesFiles("config")
public class Config {
	@PropertyValue("port")
	private final int port = 5222;

	@PropertyValue("host")
	private String host;

	@PropertyValue("user")
	private String user;

	@PropertyValue("password")
	private String password;

	@PropertyValue("room")
	private String room;

	@PropertyValue("nickname")
	private String nickname;

	public String getHost() {
		return host;
	}

	public void setHost(final String host) {
		this.host = host;
	}

	public String getUser() {
		return user;
	}

	public void setUser(final String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(final String room) {
		this.room = room;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(final String nickname) {
		this.nickname = nickname;
	}

	public int getPort() {
		return port;
	}

	@Override
	public String toString() {
		return "Config [port=" + port + ", host=" + host + ", user=" + user + ", password=" + password + ", room=" + room + ", nickname=" + nickname + "]";
	}

}
