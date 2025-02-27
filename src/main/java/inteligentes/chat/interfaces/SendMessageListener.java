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
	public String esComandoEspecial(String texto);
	public void blockPerson(String person);
	public void unblockPerson(String person);
	public boolean blockConfirmationMessage(String amigo);

}

