package inteligentes.chat.behaviours;

import java.util.Iterator;

import inteligentes.chat.agentes.AgenteCorreo;
import inteligentes.chat.interfaces.MostrarMensajesListener;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class AgenteCorreoBehaviour extends CyclicBehaviour {

	private static final long serialVersionUID = 1L;
	private static final MessageTemplate mt1 = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
			MessageTemplate.MatchOntology("ontologia"));
	private AgenteCorreo ac;
	
	public AgenteCorreoBehaviour(AgenteCorreo agenteCorreo) {
		this.ac = agenteCorreo;
	}

	@Override
	public void action() {
		System.out.println("Puede que me haya llegado un mensaje...");
        ACLMessage msg=this.myAgent.blockingReceive(mt1);
        
        try {
        	//Si recibimos un mensaje en el que el contenido es null, querr� decir que hay nuevos agentes en el chat o agentes que han abandonado el chat
        	//Tendremos que actualizar la lista de agentes que hay en chat y mostrarla en el interfaz
        	if(msg.getContentObject()==null) {
        		System.out.println("Resulta que era un mensaje de tipo actualizacion de usuario");
        		ac.actualizarLista();
        	}
        	//Si recibimos un mensaje con un contenido distinto de null, mostraremos el mensaje en el chat
        	//Tendremos que recorrer todos aquellos chats que est�n activos para ir mostrando el mensaje
        	//A�adiremos el contenido del mensaje a continuaci�n del contenido publicado previamente en el chat.
        	else {
        		System.out.println(msg.getSender().getName()+":"+ (String)msg.getContentObject());
        		System.out.println("Resulta que era un mensaje para mi. ¡Que bien!");
        		Iterator<MostrarMensajesListener> iter=ac.setMostrarMensajesListener.iterator();
        		while(iter.hasNext()) {
        			iter.next().nuevoMensaje(msg.getSender().getLocalName(), (String)msg.getContentObject());
        		}
        	}
		} catch (UnreadableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
