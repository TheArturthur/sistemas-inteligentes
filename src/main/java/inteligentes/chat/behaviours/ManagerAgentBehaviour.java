package inteligentes.chat.behaviours;

import inteligentes.chat.auxiliar.SetEnvironment;
import inteligentes.chat.auxiliar.Utils;
import inteligentes.chat.basics.EncodedMessage;
import inteligentes.chat.demo.DemoEdison;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class ManagerAgentBehaviour extends CyclicBehaviour {
	
	private static final long serialVersionUID = 1L;
	private static final MessageTemplate mt1 = 	MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), 
			MessageTemplate.MatchPerformative(ACLMessage.INFORM));
	private static final String correosender = "correosender";
	private static final String forwarding = "forwarding";
	private static final String reportmanager = "reportmanager";
	
	


	@Override
	public void action() {
		//Se espera a recibir cualquier tipo de mensaje
        ACLMessage msg=this.myAgent.blockingReceive(mt1);
        try {	
        	
        	if(msg.getPerformative() == ACLMessage.REQUEST && msg.getOntology().equals("ontologia")) {
            	AgentContainer c = myAgent.getContainerController();
        		AgentController ac;
        		ac =c.createNewAgent(EmojiBuilderAgent.NAME, EmojiBuilderAgent.class.getName(), new Object[] { });
            	//ac =c.createNewAgent(DemoEdison.NAME, DemoEdison.class.getName(), new Object[] { });
            	ac.start();
            	
            	
        		EncodedMessage em1 = (EncodedMessage)msg.getContentObject();
            	//TODO Utils.enviarMensaje(myAgent, "emojibuilder", msg);
    			System.out.println("Envio mensaje a Edison");	
    			System.out.println("El mensaje que envio es: " + em1.getMessage());
            	Utils.enviarMensaje(myAgent, "demoedison", em1, "builder");
            	
        	} else 
        		
        		if(msg.getPerformative() == ACLMessage.INFORM && msg.getOntology().equals("edison")) {
        		EncodedMessage em1 = (EncodedMessage)msg.getContentObject();
        		if(em1.isCambio()) { //Si ha cambiado, lo mandamos a su destino
                	Utils.enviarMensaje(myAgent, correosender, em1, forwarding);
                	Utils.enviarMensaje(myAgent, reportmanager, em1);
        		} else { //Si no ha cambiado, se lo enviamos a arthur a ver que dice
        	    	//AgentContainer c = myAgent.getContainerController();
        			//AgentController ac;
        			//TODO cc.createNewAgent(EmojiBuilderAgent.NAME, EmojiBuilderAgent.class.getName(), new Object[] { });
        	    	//ac = c.createNewAgent(AnalyzerAgent.NAME, AnalyzerAgent.class.getName(), new Object[] { });
        	    	//ac.start();
                    Utils.enviarMensaje(myAgent, "analyzer", em1);
        		}
        		
        	} else 
        		
        		if(msg.getPerformative() == ACLMessage.INFORM && msg.getOntology().equals("arthur")) {
        		EncodedMessage em1 = (EncodedMessage)msg.getContentObject();
        		if(em1.isOffensive()) { //Si es ofensivo, le mandamos al agente de correo una notificacion para ver si lo quiere mandar
        			Utils.enviarMensaje(myAgent, "correoreport", em1, "reports");
        		} else {
                   	Utils.enviarMensaje(myAgent, correosender, em1, forwarding);
                	Utils.enviarMensaje(myAgent, reportmanager, em1);
        		}
        	} else 
        		
        		if(msg.getPerformative() == ACLMessage.INFORM && msg.getOntology().equals("confirmation")) {
        		boolean enviar = (boolean)msg.getContentObject();
        		if(enviar) {
        			Utils.enviarMensaje(myAgent, correosender, msg, forwarding);
        			Utils.enviarMensaje(myAgent, reportmanager, msg);
        		}
        	}
        	        	
		} catch (UnreadableException e) {
			e.printStackTrace();
		} 
        catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
