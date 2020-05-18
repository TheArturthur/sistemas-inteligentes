package inteligentes.chat.interfaces;

public interface SendMessageListener 
{
	public void enviarMensaje(String remitente, String mensaje);
	public void avisarEventos(MostrarMensajesListener mostrarMensajesListener);
	public void finalizar();
}

