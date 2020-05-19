package inteligentes.chat.interfaces;

import inteligentes.chat.basics.EncodedMessage;

public interface SendMessageListener 
{
	public void sendMsgToManager(Object objeto);
	public void sendReport(Object objeto);
	public void enviarMensaje(String remitente, String mensaje);
	public void avisarEventos(MostrarMensajesListener mostrarMensajesListener);
	public void finalizar();
}

