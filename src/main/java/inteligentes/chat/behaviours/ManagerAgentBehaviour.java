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
	private static final String FORWARDING = "forwarding";
	private static final String REPORTMANAGER = "reportmanager";
	public static final String MENSAJERIA = "mensajeria";
	public static final String ONTOLOGIA = "ontologia";
	public static final String EDISON = "edison";
	public static final String ARTHUR = "arthur";
	public static final String ANALYZER = "analyzer";
	public static final String EMOJIBUILDER = "emojibuilder";
	public static final String BUILDER = "builder";
	public static final String CONFIRMATION = "confirmation";
	
	
	private static final MessageTemplate mt1 = 	MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), 
			MessageTemplate.MatchPerformative(ACLMessage.INFORM));


	

	@Override
	public void action() {
		//Se espera a recibir cualquier tipo de mensaje
        ACLMessage msg=this.myAgent.blockingReceive(mt1);
        try {	
        	if(msg.getPerformative() == ACLMessage.REQUEST && msg.getOntology().equals(ONTOLOGIA)) {
        		crearEdison();
        		Thread.sleep(100);
        		EncodedMessage em1 = (EncodedMessage)msg.getContentObject();
            	Utils.enviarMensaje(myAgent, EMOJIBUILDER, em1, BUILDER);
            	// Utils.enviarMensaje(myAgent, "demoedison", em1, "builder");
            	
        	} else 
        		
        		if(msg.getPerformative() == ACLMessage.INFORM && msg.getOntology().equals(EDISON)) {
        		EncodedMessage em1 = (EncodedMessage)msg.getContentObject();
        		if(em1.isCambio()) { //Si ha cambiado, lo mandamos a su destino
                	enviarMensajeADestino(em1);
        		} else { //Si no ha cambiado, se lo enviamos a arthur a ver que dice
        			crearArthur();
        			Thread.sleep(100);
              Utils.enviarMensaje(myAgent, ANALYZER, em1, ANALYZER);
        			//Utils.enviarMensaje(myAgent, "demoarthur", em1, "analyzer");

        		}
        		
        	} else 
        		
        		if(msg.getPerformative() == ACLMessage.INFORM && msg.getOntology().equals(ARTHUR)) {
        		System.out.println("Anda!, Arthur me ha contestado");
        		EncodedMessage em1 = (EncodedMessage)msg.getContentObject();
        		if(em1.isOffensive()) { //Si es ofensivo, le mandamos al agente de correo una notificacion para ver si lo quiere mandar
    				System.out.println("Vaya, es ofensivo, pues lo mando a los reports");
        			Utils.enviarMensaje(myAgent, MENSAJERIA, em1, CONFIRMATION);
        		} else {
        			System.out.println("Anda!, no es ofensivo, pues lo mando directamente");
        			enviarMensajeADestino(em1);
        		}
        	} else 
        		
        		if(msg.getPerformative() == ACLMessage.INFORM && msg.getOntology().equals(CONFIRMATION)) {
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
		Utils.enviarMensajeInform(myAgent, MENSAJERIA, em, FORWARDING);
    	Utils.enviarMensaje(myAgent, REPORTMANAGER, em);
	}
	

}
