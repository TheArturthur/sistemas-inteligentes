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
	private static final long serialVersionUID = -9114446805370424559L;
	private static final MessageTemplate messageTemplate = 
			MessageTemplate.and(
			MessageTemplate.MatchPerformative(ACLMessage.REQUEST), 
			MessageTemplate.MatchOntology("analyzer"));
	
	private AnalyzerAgent analyzerAgent;
	private HashMap<String,HashMap<Integer,Integer>> esAcoso;
	
	public AnalyzerAgentBehaviour(AnalyzerAgent analyzerAgent) {
		this.analyzerAgent = analyzerAgent;
		this.esAcoso = null;
	}

	@Override
	public void action() {
		ACLMessage aclMessage = this.analyzerAgent.blockingReceive(messageTemplate);
		
		try {
			EncodedMessage encodedMessage = (EncodedMessage) aclMessage.getContentObject();
			
			if (!encodedMessage.isOffensive()) {
				System.out.println("No parece haber indicios de haber acoso en el mensaje...");
				Utils.enviarMensajeInform(analyzerAgent, "manager", encodedMessage, "arturo");
			} else {
				System.out.println("Vaya, vaya, aquí hay algo...");
				System.out.println("Estos son los indicios:");
				System.out.println(esAcoso.toString());
			}
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
	}

}
