package inteligentes.chat.behaviours;

import inteligentes.chat.agentes.AgenteCorreo;
import inteligentes.chat.basics.EncodedMessage;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class CorreoSenderBehaviour extends CyclicBehaviour {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final MessageTemplate mt1 = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), 
					MessageTemplate.MatchOntology("forwarding"));
	private AgenteCorreo ac;

	public CorreoSenderBehaviour(AgenteCorreo agenteCorreo) {
		this.ac = agenteCorreo;
	}

	@Override
	public void action() {
		ACLMessage msg=this.myAgent.blockingReceive(mt1);
		
		try {
			EncodedMessage em = (EncodedMessage)msg.getContentObject();
			
			//Fui yo quien mando el mensaje
			if(em.getFrom().equals(myAgent.getLocalName())) {
				System.out.println("El nombre local de mi agente es " + myAgent.getLocalName());
				System.out.println("El mensaje va para " + em.getSendTo());
				System.out.println("El mensaje lo ha enviado " + em.getFrom());
				System.out.println("Soy " + em.getFrom() + "\ny lo envio a "  +em.getSendTo());
				ac.enviarMensaje(em.getSendTo(), em.getMessage());
				System.out.println("Ya le he mandado el mensaje a mi amigo");
			}
			
		} catch (UnreadableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
