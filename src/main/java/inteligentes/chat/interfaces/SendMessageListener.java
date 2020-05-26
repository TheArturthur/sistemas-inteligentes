package inteligentes.chat.interfaces;

import inteligentes.chat.basics.EncodedMessage;
import inteligentes.chat.basics.Report;

public interface SendMessageListener 
{
	public void sendMsgToManager(EncodedMessage objeto);
	public void sendReport(Report objeto);
	public void enviarMensaje(String remitente, String mensaje);
	public void avisarEventos(MostrarMensajesListener mostrarMensajesListener);
	public void finalizar();
	public String esComando(String texto);
}

