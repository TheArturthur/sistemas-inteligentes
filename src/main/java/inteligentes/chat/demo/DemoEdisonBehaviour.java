package inteligentes.chat.demo;

import inteligentes.chat.auxiliar.Utils;
import inteligentes.chat.basics.EncodedMessage;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class DemoEdisonBehaviour extends OneShotBehaviour {
	
	private static final long serialVersionUID = 1L;
	private static final MessageTemplate mt1 = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
	private DemoEdison de;

	public DemoEdisonBehaviour(DemoEdison demoEdison) {
			de = demoEdison;
	}

	@Override
	public void action() {
		System.out.println("Aqui Edison esperando un mensaje...");
        ACLMessage msg=this.myAgent.blockingReceive();
        try {
			
        	System.out.println("A Edison le acaba de llegar el mensaje");
            EncodedMessage em1 = (EncodedMessage)msg.getContentObject();
            
            System.out.println(em1.getMessage());
            String resultado = de.esComando(em1.getMessage());
            System.out.println("Resultado es " + resultado);
            if(resultado.equals(em1.getMessage())) {
            	System.out.println("Resulta que no era un comando. Vaya...");
            	Utils.enviarMensaje(myAgent, "manager", (Object)em1);
            } else {
            	System.out.println("Era un comando, bien!");
            	EncodedMessage nuevo = new EncodedMessage();
            	nuevo.setOffensive(em1.isOffensive());
            	nuevo.setSendTo(em1.getSendTo());
            	nuevo.setFrom(em1.getFrom());
            	
            	nuevo.setMessage(resultado);
            	
            	//nuevo.setConver(em1.getConver());
            	//nuevo.setMessageListener(em1.getMessageListener());
            	System.out.println("Edison va a enviar el mensaje");
            	Utils.enviarMensajeInform(myAgent, "manager", (Object)nuevo);
            }
             	
           
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
        
        de.finalizar();
	}
	
	


}
