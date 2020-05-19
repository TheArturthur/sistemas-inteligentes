package inteligentes.chat.interfaces;

import inteligentes.chat.basics.EncodedMessage;

public interface SendMessageListener 
{
	public void enviarEncodedMensaje(String destinatario, EncodedMessage mensaje);
	public void enviarMensaje(String remitente, String mensaje);
	public void avisarEventos(MostrarMensajesListener mostrarMensajesListener);
	public void finalizar();
}

