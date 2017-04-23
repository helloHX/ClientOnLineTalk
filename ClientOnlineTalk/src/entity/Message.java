package entity;


public class Message {
	private String messageId;
	private String message;
	private String messageTime;
	private String formId;
	private String ToId;
	
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessageTime() {
		return messageTime;
	}
	public void setMessageTime(String messageTime) {
		this.messageTime = messageTime;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public String getToId() {
		return ToId;
	}
	public void setToId(String toId) {
		ToId = toId;
	}
	
}
