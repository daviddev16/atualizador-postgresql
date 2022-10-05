package br.com.alterdata.core.postgres.data;

public class ConnectionInfo {

	private String host;
	private String port;
	private String username;
	private String password;

	public ConnectionInfo(String host, String port, String username, String password) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = (password != null) ? password : "#abc123#";
	}

	public ConnectionInfo(String host, String port, String username) {
		this(host, port, username, null);
	}

	public String getHost() {
		return host;
	}

	public String getPort() {
		return port;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
