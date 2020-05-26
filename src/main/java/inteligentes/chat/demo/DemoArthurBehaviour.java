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
	private static final MessageTemplate mt1 = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), 
			MessageTemplate.MatchOntology("analyzer"));	
	@Override
	public void action() {
		ACLMessage msg=this.myAgent.blockingReceive(mt1);
		
			try {
				
				//if () {
					EncodedMessage em = (EncodedMessage)msg.getContentObject();
					Utils.enviarMensajeInform(myAgent, "manager", em, "arthur");
				//}
				
				myAgent.doDelete();
			} catch (UnreadableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		
	}


}
