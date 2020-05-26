package inteligentes.chat.behaviours;

import java.util.ArrayList;
import java.util.HashMap;

import inteligentes.chat.agentes.AnalyzerAgent;
import inteligentes.chat.auxiliar.Utils;
import inteligentes.chat.basics.EncodedMessage;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class AnalyzerAgentBehaviour extends OneShotBehaviour {

	/**
	 * Generated serial version UID:
	 */
	private static final long serialVersionUID = 1L;
	private static final MessageTemplate messageTemplate = 
			MessageTemplate.and(
			MessageTemplate.MatchPerformative(ACLMessage.REQUEST), 
			MessageTemplate.MatchOntology("analyzer"));
	
	private AnalyzerAgent analyzerAgent;
	private ArrayList<String> esAcoso = new ArrayList<>();
	
	public AnalyzerAgentBehaviour(AnalyzerAgent analyzerAgent) {
		this.analyzerAgent = analyzerAgent;
	}

	@Override
	public void action() {
		ACLMessage aclMessage = this.analyzerAgent.blockingReceive(messageTemplate);
		
		try {
			EncodedMessage encodedMessage = (EncodedMessage) aclMessage.getContentObject();
			
			this.esAcoso = this.analyzerAgent.checkIfOffensive(encodedMessage);
			
			if (!encodedMessage.isOffensive()) {
				System.out.println("No parece haber indicios de haber acoso en el mensaje...");
			} else {
				System.out.println("Vaya, vaya, aquí hay algo...");
				System.out.println("Estos son los indicios:");
				System.out.println(esAcoso.toString());
			}
			Utils.enviarMensajeInform(analyzerAgent, "manager", encodedMessage, "arthur");
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
		
		this.analyzerAgent.doDelete();
	}

}
