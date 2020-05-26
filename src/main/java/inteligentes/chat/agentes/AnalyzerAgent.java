/**
 * 
 */
package inteligentes.chat.agentes;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import inteligentes.chat.basics.EncodedMessage;
import inteligentes.chat.behaviours.AnalyzerAgentBehaviour;
import inteligentes.chat.interfaces.MostrarMensajesListener;

import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.core.behaviours.Behaviour;


public class AnalyzerAgent extends Agent {

	private static final long serialVersionUID = 1L;
	
	public static final String NAME = "analyzer";
	
	private ArrayList<String> insults = new ArrayList<>(Arrays.asList(
			"puta", "zorra", "cabron", "capullo", "guarra", "gilipollas",
			"imbecil", "inutil", "gordo", "obeso"));
	
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
	 * @param encodedMessage
	 */
	public ArrayList<String> checkIfOffensive(EncodedMessage encodedMessage) {
		String[] msg = encodedMessage.getMessage().split("\n");
		ArrayList<String> res = new ArrayList<>();
		for (String pal : msg) {
			if (this.insults.contains(pal)) {
				res.add(pal);
				encodedMessage.setOffensive(true);
			}
		}
		return res;
	}
	
}
