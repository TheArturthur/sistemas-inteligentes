package inteligentes.chat.agentes;

import inteligentes.chat.auxiliar.TokensEmoji;
import inteligentes.chat.behaviours.EmojiBuilderAgentBehaviour;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;



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

		addBehaviour(new EmojiBuilderAgentBehaviour(this));

		try {
			DFService.register(this, dfd);
		} catch(FIPAException e) {
			e.printStackTrace();
		}
	}

	public String esComando(String str) {		

		String vuelta = "";
		String toUse = str.toLowerCase();

		switch (toUse) {
		case TokensEmoji.comando1:
			vuelta = TokensEmoji.comando1value;
			break;

		case TokensEmoji.comando2:
			vuelta = TokensEmoji.comando2value;
			break;	

		case TokensEmoji.comando3:
			vuelta = TokensEmoji.comando3value;
			break;

		case TokensEmoji.comando4:
			vuelta = TokensEmoji.comando4value;
			break;	

		case TokensEmoji.comando5:
			vuelta = TokensEmoji.comando5value;
			break;

		case TokensEmoji.comando6:
			vuelta = TokensEmoji.comando6value;
			break;

		case TokensEmoji.comando7:
			vuelta = TokensEmoji.comando7value;
			break;

		case TokensEmoji.comando8:
			vuelta = TokensEmoji.comando8value;
			break;

		case TokensEmoji.comando9:
			vuelta = TokensEmoji.comando9value;
			break;

		case TokensEmoji.comando10:
			vuelta = TokensEmoji.comando10value;
			break;

		case TokensEmoji.comando11:
			vuelta = TokensEmoji.comando11value;
			break;
			
		case TokensEmoji.comando12:
			vuelta = TokensEmoji.comando12value;
			break;	
			
		case TokensEmoji.comando13:
			vuelta = TokensEmoji.comando13value;
			break;
			
		case TokensEmoji.comando14:
			vuelta = TokensEmoji.comando14value;
			break;	
			
		case TokensEmoji.comando15:
			vuelta = TokensEmoji.comando15value;
			break;
			
		case TokensEmoji.comando16:
			vuelta = TokensEmoji.comando16value;
			break;
			
		case TokensEmoji.comando17:
			vuelta = TokensEmoji.comando17value;
			break;
			
		default:
			vuelta = str;
			break;
		}
		return vuelta;
	}

	//	DFAgentDescription[] dfd;
	//	public Set<MostrarMensajesListener> setMostrarMensajesListener;
	//	
	//	public EmojiBuilderAgent() {
	//		super();
	//	}

}
