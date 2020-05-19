package inteligentes.chat.behaviours;

import inteligentes.chat.aux.Utils;
import inteligentes.chat.basics.EncodedMessage;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class ManagerAgentBehaviour extends CyclicBehaviour {
	
	private static final long serialVersionUID = 1L;
	private static final MessageTemplate mt1 = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
	private static jade.wrapper.AgentContainer cc;




	@Override
	public void action() {
        ACLMessage msg=this.myAgent.blockingReceive(mt1);
        try {
			//TODO cc.createNewAgent(EmojiBuilderAgent.NAME, EmojiBuilderAgent.class.getName(), null).start();
        	Utils.enviarMensaje(myAgent, "emojibuilder", msg);
            ACLMessage msg2=this.myAgent.blockingReceive(mt1);
            
            EncodedMessage em1 = (EncodedMessage)msg.getContentObject();
            EncodedMessage em2 = (EncodedMessage)msg2.getContentObject();
            
            /*Ha resultado que es el mensaje es un comando*/
            if(!em1.getMessage().equals(em2.getMessage())) {
            	em2.getMessageListener().enviarMensaje(em2.getSendTo(), em2.getMessage());
            } else {
            	Utils.enviarMensaje(myAgent, "analyzer", em1);
                ACLMessage msg3=this.myAgent.blockingReceive(mt1);
                
                EncodedMessage em3 = (EncodedMessage)msg3.getContentObject();
                if(em3.isOffensive()) {
                	em1.getConver().offensiveMessagePopUp();
                	//Ver si lo quiere enviar o no, y enviarlo o no
                	
                }

            }
        	
        	//System.out.println(msg.getSender().getName()+":"+ (EncodedMessage)msg.getContentObject());
        	
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
	}

}
