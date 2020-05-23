package inteligentes.chat.behaviours;

import inteligentes.chat.agentes.AgenteCorreo;
import inteligentes.chat.auxiliar.Utils;
import inteligentes.chat.basics.EncodedMessage;
import inteligentes.chat.basics.Report;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class CorreoReportsBehaviour extends CyclicBehaviour {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final MessageTemplate mt1 = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), 
			MessageTemplate.MatchOntology("reports"));
	private AgenteCorreo ac;

	public CorreoReportsBehaviour(AgenteCorreo agenteCorreo) {
		this.ac = agenteCorreo;
	}

	@Override
	public void action() {
		ACLMessage msg=this.myAgent.blockingReceive(mt1);
		
		try {
			Object tipo = msg.getContentObject();
			
			if(tipo instanceof Report) {
				
				Report rep = (Report)tipo;
				//Ver si el mensaje es para mi
				if(rep.getEmisor().equals(myAgent.getName())) {
					ac.reportAdvice();
				}
			}
			
			if(tipo instanceof EncodedMessage) {
				
				EncodedMessage em = (EncodedMessage)tipo;
				//Ver si el mensaje es para mi
				if(em.getFrom().equals(myAgent.getName())) {
					boolean send = ac.offensiveMessagePopUp();
					Utils.enviarMensaje(myAgent, "manager", send);
				}
			}
			
		} catch (UnreadableException e) {
			e.printStackTrace();
		}

	}

}
