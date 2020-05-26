package inteligentes.chat.behaviours;

import inteligentes.chat.agentes.AnalyzerAgent;
import inteligentes.chat.agentes.EmojiBuilderAgent;
import inteligentes.chat.auxiliar.Utils;
import inteligentes.chat.basics.EncodedMessage;
import inteligentes.chat.demo.DemoArthur;
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
        		crearEdison();
        		Thread.sleep(100);
        		EncodedMessage em1 = (EncodedMessage)msg.getContentObject();
            	Utils.enviarMensaje(myAgent, "emojibuilder", em1, "builder");
            	// Utils.enviarMensaje(myAgent, "demoedison", em1, "builder");
            	
        	} else 
        		
        		if(msg.getPerformative() == ACLMessage.INFORM && msg.getOntology().equals("edison")) {
        		EncodedMessage em1 = (EncodedMessage)msg.getContentObject();
        		if(em1.isCambio()) { //Si ha cambiado, lo mandamos a su destino
                	enviarMensajeADestino(em1);
        		} else { //Si no ha cambiado, se lo enviamos a arthur a ver que dice
        			crearArthur();
        			Thread.sleep(100);
              Utils.enviarMensaje(myAgent, "analyzer", em1, "analyzer");
        			//Utils.enviarMensaje(myAgent, "demoarthur", em1, "analyzer");

        		}
        		
        	} else 
        		
        		if(msg.getPerformative() == ACLMessage.INFORM && msg.getOntology().equals("arthur")) {
        		System.out.println("Anda!, Arthur me ha contestado");
        		EncodedMessage em1 = (EncodedMessage)msg.getContentObject();
        		if(em1.isOffensive()) { //Si es ofensivo, le mandamos al agente de correo una notificacion para ver si lo quiere mandar
        			Utils.enviarMensaje(myAgent, "correoreport", em1, "reports");
        		} else {
        			enviarMensajeADestino(em1);
        		}
        	} else 
        		
        		if(msg.getPerformative() == ACLMessage.INFORM && msg.getOntology().equals("confirmation")) {
        		EncodedMessage em1 = (EncodedMessage)msg.getContentObject();
        		if(em1.isEnviar()) {
        			enviarMensajeADestino(em1);
        		}
        	}
        	        	
		} catch (UnreadableException e) {
			e.printStackTrace();
		} catch (StaleProxyException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void crearEdison() throws StaleProxyException {
    	AgentContainer c = myAgent.getContainerController();
		AgentController ac;
		ac =c.createNewAgent(EmojiBuilderAgent.NAME, EmojiBuilderAgent.class.getName(), new Object[] { });
    	//ac =c.createNewAgent(DemoEdison.NAME, DemoEdison.class.getName(), new Object[] { });
    	ac.start();
	}
	
	private void crearArthur() throws StaleProxyException {
    	AgentContainer c = myAgent.getContainerController();
		AgentController ac;

   	ac = c.createNewAgent(AnalyzerAgent.NAME, AnalyzerAgent.class.getName(), new Object[] { });
		//ac = c.createNewAgent(DemoArthur.NAME, DemoArthur.class.getName(), new Object[] { });

    	ac.start();
	}
	
	private void enviarMensajeADestino(EncodedMessage em) {
		Utils.enviarMensaje(myAgent, correosender, em, forwarding);
    	Utils.enviarMensaje(myAgent, reportmanager, em);
	}
	
	
	
	

}
