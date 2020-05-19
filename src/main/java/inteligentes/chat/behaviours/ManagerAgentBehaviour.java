package inteligentes.chat.behaviours;

import inteligentes.chat.auxiliar.Utils;
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
            
            //Si el mensaje es un comando, se envia directamente
            if(!em1.getMessage().equals(em2.getMessage())) {
            	em2.getMessageListener().enviarMensaje(em2.getSendTo(), em2.getMessage());
            	Utils.enviarMensaje(myAgent, "reportmanager", em2);
            } else {
                //TODO cc.createNewAgent(AnalyzerAgent.NAME, AnalyzerAgent.class.getName(), null).start();
                Utils.enviarMensaje(myAgent, "analyzer", em1);
                  ACLMessage msg3=this.myAgent.blockingReceive(mt1);
                  
                  EncodedMessage em3 = (EncodedMessage)msg3.getContentObject();
                  
              	  //Se manda al report manager para que tenga el historial de mensajes de la conversacion
              	  Utils.enviarMensaje(myAgent, "reportmanager", em3);

                  if(em3.isOffensive()) {
                    boolean enviar = em1.getConver().offensiveMessagePopUp();
                    //Ver si lo quiere enviar o no, y enviarlo o no
                    if(enviar) em1.getMessageListener().enviarMensaje(em1.getSendTo(), em1.getMessage());
                    
                  }
              }
        	
        	//System.out.println(msg.getSender().getName()+":"+ (EncodedMessage)msg.getContentObject());
        	
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
	}

}
