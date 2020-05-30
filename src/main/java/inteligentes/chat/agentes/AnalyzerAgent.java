/**
 * 
 */
package inteligentes.chat.agentes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import inteligentes.chat.basics.EncodedMessage;
import inteligentes.chat.behaviours.AnalyzerAgentBehaviour;

import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;

import org.apache.commons.text.similarity.*;

public class AnalyzerAgent extends Agent {

	private static final long serialVersionUID = 1L;
	
	private LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
	
	public static final String NAME = "analyzer";
	
	/**
	 * List containing the insults to be analyzed in the message.
	 */
	private ArrayList<String> insults = new ArrayList<>(Arrays.asList(
			"puta", "zorra", "cabron", "capullo", "guarra", "gilipollas",
			"imbecil", "inutil", "gordo", "obeso", "feo", "subnormal"));
	
	@Override
	protected void setup() {
		//Crear servicios proporcionados por el agente y registrarlos en la plataforma
		DFAgentDescription agentDescription = new DFAgentDescription();
		agentDescription.setName(getAID());
		ServiceDescription serviceDescriptor = new ServiceDescription();
		serviceDescriptor.setName(NAME);
		//establezco el tipo del servicio "coordinador" para poder localizarlo cuando haga una busqueda
		serviceDescriptor.setType(NAME);
		serviceDescriptor.addOntologies(NAME);
		serviceDescriptor.addLanguages(new SLCodec().getName());
		agentDescription.addServices(serviceDescriptor);

		addBehaviour(new AnalyzerAgentBehaviour(this));

		try {
			DFService.register(this, agentDescription);
		} catch(FIPAException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Checks if the message contains an insult from the list.
	 * @param encodedMessage
	 */
	public HashMap<String, ArrayList<int[]>> checkIfOffensive(EncodedMessage encodedMessage) {
		String[] msg = encodedMessage.getMessage().toLowerCase().replace('\n',' ').split(" ");
		for (int i = 0; i < msg.length; i++) {
			msg[i] = msg[i].replaceAll("[^\\w]", "");
		}
		
		HashMap<String, ArrayList<int[]>> res = new HashMap<>();
		
		for (String pal : msg) {
			for (int i = 0; i < this.insults.size(); i++) {
				if (this.levenshteinDistance.apply(this.insults.get(i),pal) < 3 &&
						!res.containsKey(this.insults.get(i))) {
					String message = encodedMessage.getMessage();
					ArrayList<int[]> positions = new ArrayList<int[]>();
					
					List<Integer> indexes = getIndexes(message, pal);
					
					for (int index : indexes) {
						positions.add(new int[] {index, index + pal.length() - 1});
					}
					
					res.put(this.insults.get(i), positions);
				}
			}
		}
		
		if (res.size() > 0) {
			encodedMessage.setOffensive(true);					
		}
		
		return res;
	}
	
	private List<Integer> getIndexes(String str, String findStr) {
		int lastIndex = 0;
		List<Integer> result = new ArrayList<Integer>();

		while(lastIndex != -1) {

		    lastIndex = str.indexOf(findStr,lastIndex);

		    if(lastIndex != -1){
		        result.add(lastIndex);
		        lastIndex += 1;
		    }
		}
		return result;
	}
	
}
