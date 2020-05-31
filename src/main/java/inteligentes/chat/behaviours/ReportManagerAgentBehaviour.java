package inteligentes.chat.behaviours;

import inteligentes.chat.auxiliar.ChatsStorage;
import inteligentes.chat.auxiliar.Utils;
import inteligentes.chat.basics.*;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class ReportManagerAgentBehaviour extends CyclicBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final MessageTemplate mt1 = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
	private ChatsStorage cs = new ChatsStorage();


	@Override
	public void action() {
        ACLMessage msg=this.myAgent.blockingReceive(mt1);
		System.out.println("Uy.. un mensajito nuevo ;)");
        
        try {
        	if (msg.getContentObject() == null) {
        		cs.listAllChats();
        	}
        	
			if(msg.getContentObject() instanceof Report) {
				Report rep = (Report)msg.getContentObject();
				EncodedMessage em = cs.getChatMessage(rep);
				
				//Vemos si el mensaje reportado estaba clasificado como ofensivo.
				if(em.isOffensive()) { 
					/* Se le da un aviso de que ha sido reportado por actitud ofensiva,
					 * ya que el mensaje estaba clasificado como ofensivo.
					 * Notese que se reporta al usuario que ha enviado el mensaje, 
					 * nunca el que ha reportado el mensaje.
					 */
					Utils.enviarMensaje(myAgent, "correoreports", rep, "reports");
				}
			}
			
			if(msg.getContentObject() instanceof EncodedMessage) {
				System.out.println("Aqui el reportir guardando un mensajito");
				cs.addChatMessage((EncodedMessage)msg.getContentObject());
			}
			
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
	}
	

}
