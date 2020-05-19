package inteligentes.chat.basics;

import inteligentes.chat.gui.JPanelConversacion;
import inteligentes.chat.interfaces.SendMessageListener;

public class EncodedMessage {
	
	private String sendTo;
	private String from;
	private String message;
	private SendMessageListener msgl;
	private boolean isOffensive = false; //Asumimos que el mensaje no es ofensivo
	private JPanelConversacion conver;
	
	public JPanelConversacion getConver() {
		return conver;
	}

	public void setConver(JPanelConversacion conver) {
		this.conver = conver;
	}

	public boolean isOffensive() {
		return isOffensive;
	}

	public void setOffensive(boolean isOffensive) {
		this.isOffensive = isOffensive;
	}
	
	public SendMessageListener getMessageListener() {
		return msgl;
	}

	public void setMessageListener(SendMessageListener msgl) {
		this.msgl = msgl;
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
