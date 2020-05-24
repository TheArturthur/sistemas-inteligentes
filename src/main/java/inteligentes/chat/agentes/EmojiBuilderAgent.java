package inteligentes.chat.agentes;

import java.util.Set;
import jade.lang.acl.ACLMessage;
import inteligentes.chat.behaviours.EmojiBuilderAgentBehaviour;
import inteligentes.chat.behaviours.ManagerAgentBehaviour;
import inteligentes.chat.interfaces.MostrarMensajesListener;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;



@SuppressWarnings("serial")
public class EmojiBuilderAgent extends Agent {
	
	//private static final long serialVersionUID = 1L;
	public static final String NAME = "emojibuilder";
	
	
	protected void setup()
	{
		//Crear servicios proporcionados por el agente y registrarlos en la plataforma
		 DFAgentDescription dfd = new DFAgentDescription();
	     dfd.setName(getAID());
	     ServiceDescription sd = new ServiceDescription();
         sd.setName("emojibuilder");
	     //establezco el tipo del servicio "coordinador" para poder localizarlo cuando haga una busqueda
	     sd.setType("emojibuilder");
	     sd.addOntologies("builder");
	     sd.addLanguages(new SLCodec().getName());
	     dfd.addServices(sd);
	        
	     addBehaviour(new EmojiBuilderAgentBehaviour());
	        
	     try {
	            DFService.register(this, dfd);
			} catch(FIPAException e) {
				e.printStackTrace();
			}
		}

//	DFAgentDescription[] dfd;
//	public Set<MostrarMensajesListener> setMostrarMensajesListener;
//	
//	public EmojiBuilderAgent() {
//		super();
//	}

}
