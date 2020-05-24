package inteligentes.chat.behaviours;

import inteligentes.chat.auxiliar.TokensEmoji;
import inteligentes.chat.auxiliar.Utils;
import inteligentes.chat.basics.EncodedMessage;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class EmojiBuilderAgentBehaviour extends OneShotBehaviour{
	
	private static final long serialVersionUID = 1L;
	private static final MessageTemplate mt1 = MessageTemplate.and(MessageTemplate.
			MatchPerformative(ACLMessage.REQUEST), MessageTemplate.MatchOntology("builder"));
		
	@Override
	
	public void action() {
		System.out.println("Aqui Edison esperando un mensaje...");
		ACLMessage msg=this.myAgent.blockingReceive(mt1);
		try {

			System.out.println("A Edison le acaba de llegar el mensaje");
			EncodedMessage em1 = (EncodedMessage)msg.getContentObject();           

			System.out.println(em1.getMessage());
			String resultado = esComando(em1.getMessage());
			System.out.println("Resultado es " + resultado);
			if(resultado.equals(em1.getMessage())) {
				System.out.println("Resulta que no era un comando. Vaya...");
				Utils.enviarMensajeInform(myAgent, "manager", em1, "edison");
			} else {
				System.out.println("Era un comando, bien!");
				EncodedMessage nuevo = new EncodedMessage();
				nuevo.setOffensive(em1.isOffensive());
				nuevo.setSendTo(em1.getSendTo());
				nuevo.setFrom(em1.getFrom());

				nuevo.setMessage(resultado);
				nuevo.setCambio(true);

				System.out.println("Edison va a enviar el mensaje");
				Utils.enviarMensajeInform(myAgent, "manager", nuevo, "edison");
			}
			
			myAgent.doDelete();
			
		} catch (UnreadableException e) {
			e.printStackTrace();
		}


	}
	
	private String esComando(String str) {		
		
		String vuelta = new String();
		
		
		switch (str) {
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
			
		default:
			vuelta = str;
			break;
		}
		return vuelta;
	}
		
}
