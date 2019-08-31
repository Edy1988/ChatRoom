package edu.udacity.java.nano.chat;

/**
 * WebSocket message model
 */
public class Message {

	public static final String ENTER = "ENTER";
	public static final String CHAT = "CHAT";
	public static final String LEAVE = "LEAVE";
	private String type;
	private String username;
	private String message;
	private int onlineCount;

	public Message(String type, String username, String message, int onlineCount) {

		this.type = type;
		this.username = username;
		this.message = message;
		this.onlineCount = onlineCount;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setOnlineCount(int onlineCount) {
		this.onlineCount = onlineCount;
	}

	public int getOnlineCount() {
		return onlineCount;
	}
	public String getENTER(){
		return "ENTER";
	}

	public String getChat(){
		return "CHAT";
	}

	public String getLeave(){
		return "LEAVE";
	}
}
