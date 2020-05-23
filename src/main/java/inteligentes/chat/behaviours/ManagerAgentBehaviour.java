package inteligentes.chat.behaviours;

import inteligentes.chat.auxiliar.SetEnvironment;
import inteligentes.chat.auxiliar.Utils;
import inteligentes.chat.basics.EncodedMessage;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.wrapper.StaleProxyException;

public class ManagerAgentBehaviour extends CyclicBehaviour {
	
	private static final long serialVersionUID = 1L;
	private static final MessageTemplate mt1 = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
	private static final MessageTemplate mt2 = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
	
	


	@Override
	public void action() {
		//Se espera a recibir cualquier tipo de mensaje
        ACLMessage msg=this.myAgent.blockingReceive(mt1);
        try {
        	
        	EncodedMessage em1 = (EncodedMessage)msg.getContentObject();

        	System.out.println("He recibido el mensaje " + em1.getMessage() + "cola");
        	System.out.println("De " + em1.getFrom());
        	System.out.println("Creo a Edison");
			SetEnvironment.createEdisonAgent(myAgent);
        	//TODO Utils.enviarMensaje(myAgent, "emojibuilder", msg);
			System.out.println("Envio mensaje a Edison");
        	Utils.enviarMensaje(myAgent, "demoedison", (Object)em1);
        	
        	System.out.println("Esperando a que Edison me conteste...");
            ACLMessage msg2=this.myAgent.blockingReceive(mt2);
                  
            System.out.println("Recibo mensaje de Edison\n");
            EncodedMessage em2 = (EncodedMessage)msg2.getContentObject();
            
            //Si el mensaje es un comando, se envia directamente
            if(!em1.getMessage().equals(em2.getMessage())) {
            	System.out.println("Edison dice que el mensaje es un comando, y se lo mando a " + em2.getSendTo());
            	Utils.enviarMensaje(myAgent, "correosender", em2, "forwarding");
            	Utils.enviarMensaje(myAgent, "reportmanager", em2);
            } else {
 
            	System.out.println("Resulta que como no es un comando, hay que procesarlo para ver si es ofensivo");
            	SetEnvironment.createArthurAgent(myAgent);
                Utils.enviarMensaje(myAgent, "analyzer", em1);
                ACLMessage msg3=this.myAgent.blockingReceive(mt1);
                  
                EncodedMessage em3 = (EncodedMessage)msg3.getContentObject();
                  
              	//Se manda al report manager para que tenga el historial de mensajes de la conversacion
              	Utils.enviarMensaje(myAgent, "reportmanager", em3);

                if(em3.isOffensive()) {
                  System.out.println("Arthur dice que el mensaje es ofensivo, y se pregunta si lo quiere enviar");
                  Utils.enviarMensaje(myAgent, "correoreport", em2, "reports");
                  ACLMessage msg5=this.myAgent.blockingReceive(mt2);
                	  
                	 boolean enviar = (boolean)msg5.getContentObject();
                   
                  if(enviar) {
                	  System.out.println("Ha dicho que lo va a enviar, el vera");
                	  Utils.enviarMensaje(myAgent, "correosender", em2, "forwarding");
                  }
                    
                  }
              }
        	
        	//System.out.println(msg.getSender().getName()+":"+ (EncodedMessage)msg.getContentObject());
        	
		} catch (UnreadableException e) {
			e.printStackTrace();
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
