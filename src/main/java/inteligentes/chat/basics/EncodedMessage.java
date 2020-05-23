package inteligentes.chat.basics;

import java.io.Serializable;

public class EncodedMessage implements Serializable {
	
	private String sendTo;
	private String from;
	private String message;
	private boolean isOffensive = false; //Asumimos que el mensaje no es ofensivo
	
	

	public boolean isOffensive() {
		return isOffensive;
	}

	public void setOffensive(boolean isOffensive) {
		this.isOffensive = isOffensive;
	}

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
