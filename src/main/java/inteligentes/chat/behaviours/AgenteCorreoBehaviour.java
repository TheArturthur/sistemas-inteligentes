package inteligentes.chat.behaviours;

import java.util.Iterator;

import inteligentes.chat.agentes.AgenteCorreo;
import inteligentes.chat.auxiliar.Utils;
import inteligentes.chat.basics.EncodedMessage;
import inteligentes.chat.basics.Report;
import inteligentes.chat.interfaces.MostrarMensajesListener;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class AgenteCorreoBehaviour extends CyclicBehaviour {

	private static final long serialVersionUID = 1L;
	private static final MessageTemplate mt1 = 	MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), 
			MessageTemplate.MatchPerformative(ACLMessage.INFORM));
	private AgenteCorreo ac;

	public AgenteCorreoBehaviour(AgenteCorreo agenteCorreo) {
		this.ac = agenteCorreo;
	}

	@Override
	public void action() {
		System.out.println("Puede que me haya llegado un mensaje...");
		ACLMessage msg=this.myAgent.blockingReceive(mt1);

		try {

			if (msg.getPerformative() == ACLMessage.INFORM) {
				System.out.println("\n\n\n\n\n ERA DE TIPO INFORMM");
				System.out.println("Y de ontologia: " + msg.getOntology());

				if(msg.getOntology().equals("reports")) {
					Object tipo = msg.getContentObject();    				
					Report rep = (Report)tipo;
					//Ver si el mensaje es para mi
					if(rep.getEmisor().equals(myAgent.getName())) {
						System.out.println("Vaya, me han reportado un mensaje... Soy " + rep.getEmisor());
						ac.reportAdvice();
					}
				} else	

					if(msg.getOntology().equals("forwarding")) {
						EncodedMessage em = (EncodedMessage)msg.getContentObject();

						//Fui yo quien mando el mensaje
						if(em.getFrom().equals(myAgent.getLocalName())) {
							System.out.println("El mensaje era de tipo reenvio! Alla va!");
							//    				System.out.println("El nombre local de mi agente es " + myAgent.getLocalName());
							//    				System.out.println("El mensaje va para " + em.getSendTo());
							//    				System.out.println("El mensaje lo ha enviado " + em.getFrom());
							//    				System.out.println("Soy " + em.getFrom() + "\ny lo envio a "  +em.getSendTo() + "\ny el mensaje es: " + em.getMessage());
							ac.enviarMensaje(em.getSendTo(), em.getMessage());
							//    				System.out.println("Ya le he mandado el mensaje a mi amigo");
						}
					}	


			} else

				if (msg.getPerformative() == ACLMessage.REQUEST) {
					System.out.println("\n\n\n\n\n ERA DE TIPO REQUEST");
					System.out.println("Y de ontologia: " + msg.getOntology());
					
					
					if(msg.getOntology().equals("confirmation")) {
						EncodedMessage em = (EncodedMessage)msg.getContentObject();
						System.out.println("Estoy dentro de confirmation...");
						System.out.println("Soy " + myAgent.getName());
						System.out.println("Y lo ha mandado " + em.getFrom());


						//Ver si el mensaje es para mi
						if(em.getFrom().equals(myAgent.getLocalName())) {
							System.out.println("Uy!, tengo que mandar un pop up a mi usuario!");
							boolean done = false;
							done = ac.offensiveMessagePopUp(em);
							while(!done) {
								
							}
							Utils.enviarMensajeInform(myAgent, "manager", em, "confirmation");
						}
					} else 

						if(msg.getOntology().equals("ontologia")) {
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
								//            		System.out.println(msg.getSender().getName()+":"+ (String)msg.getContentObject());
								System.out.println("Resulta que era un mensaje para mi. ¡Que bien!");
								if (!ac.isBlocked(msg.getSender().getName())) {
									Iterator<MostrarMensajesListener> iter=ac.setMostrarMensajesListener.iterator();
									while(iter.hasNext()) {
										iter.next().nuevoMensaje(msg.getSender().getLocalName(), (String)msg.getContentObject());
									}
								}
							}
						}

				}



		} catch (UnreadableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
