package inteligentes.chat.demo;

import inteligentes.chat.auxiliar.Utils;
import inteligentes.chat.basics.EncodedMessage;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class DemoArthurBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final MessageTemplate mt1 = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);	
	
	
	@Override
	public void action() {
		ACLMessage msg=this.myAgent.blockingReceive(mt1);
		
			try {
				if (msg.getOntology().equals("analyzer")) {
					System.out.println("Hola soy Arthur y me ha llegado un mensaje");
					EncodedMessage em = (EncodedMessage)msg.getContentObject();
					System.out.println("Le doy una patada y lo mando de vuelta!!");
					Utils.enviarMensajeInform(myAgent, "manager", em, "arthur");
				}
				
			} catch (UnreadableException e) {
				e.printStackTrace();
			}
			myAgent.doDelete();			
	}


}
