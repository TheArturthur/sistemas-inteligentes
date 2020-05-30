package inteligentes.chat.agentes;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JOptionPane;

import inteligentes.chat.auxiliar.TokensEmoji;
import inteligentes.chat.auxiliar.Utils;
import inteligentes.chat.basics.EncodedMessage;
import inteligentes.chat.basics.Report;
import inteligentes.chat.behaviours.AgenteCorreoBehaviour;
import inteligentes.chat.gui.MainGuiMessenger;
import inteligentes.chat.interfaces.MostrarMensajesListener;
import inteligentes.chat.interfaces.SendMessageListener;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.Envelope;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;




public class AgenteCorreo extends Agent implements SendMessageListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DFAgentDescription[] dfd;
	public Set<MostrarMensajesListener> setMostrarMensajesListener;
	private int avisos;
	public static final String NAME = "correo";
	public static final String MENSAJERIA = "mensajeria";


	
	public AgenteCorreo() {
		super();
		this.avisos = 0;
	}
	
	public void setup() {
		
		//Crear servicios proporcionados por el agente y registrarlos en la plataforma
        DFAgentDescription dfd1 = new DFAgentDescription();
        dfd1.setName(getAID());
         
        
        ServiceDescription sd1 = new ServiceDescription();
        sd1.setName(NAME);
        //establezco el tipo del servicio "mensajeria" para poder localizarlo cuando haga una busqueda
        sd1.setType(MENSAJERIA);
        sd1.addOntologies("ontologia");
        sd1.addLanguages(new SLCodec().getName());
               
        ServiceDescription sd2 = new ServiceDescription();
        sd2.setName(NAME);
        //establezco el tipo del servicio "mensajeria" para poder localizarlo cuando haga una busqueda
        sd2.setType(MENSAJERIA);
        sd2.addOntologies("forwarding");
        sd2.addLanguages(new SLCodec().getName());
        
        
        ServiceDescription sd3 = new ServiceDescription();
        sd3.setName(NAME);
        //establezco el tipo del servicio "mensajeria" para poder localizarlo cuando haga una busqueda
        sd3.setType(MENSAJERIA);
        sd3.addOntologies("reports");
        sd3.addLanguages(new SLCodec().getName());
        
        ServiceDescription sd4 = new ServiceDescription();
        sd4.setName(NAME);
        //establezco el tipo del servicio "mensajeria" para poder localizarlo cuando haga una busqueda
        sd4.setType(MENSAJERIA);
        sd4.addOntologies("confirmation");
        sd4.addLanguages(new SLCodec().getName());
        
        
        dfd1.addServices(sd1);
        dfd1.addServices(sd2);
        dfd1.addServices(sd3);
        dfd1.addServices(sd4);
     
        
        try {
        	//registro los servicios en el agente DF
            DFService.register(this, dfd1);
        }
        catch(FIPAException e) {
            System.err.println("Agente "+getLocalName()+": "+e.getMessage());
        }
        //Inicializamos el contenido del chat
		setMostrarMensajesListener=new HashSet<MostrarMensajesListener>();
		
        //Aniadimos un comportamiento ciclico para capturar y procesar los mensajes que recibe el agente
        addBehaviour(new AgenteCorreoBehaviour(this));
		
		//Inicializamos el interfaz del agente. No es necesario lanzarlo como un hilo independiente.
		MainGuiMessenger gui=new MainGuiMessenger(this.getLocalName(), this);
		gui.run();

		//Doy servicio de mensajeria
		Utils.enviarMensaje(this, MENSAJERIA, null);
	}


	public void actualizarLista() {
		dfd=Utils.buscarAgentes(this, MENSAJERIA);
		String usuarios[]=new String[dfd.length];
		for(int i=0;i<dfd.length;i++) {
			System.out.println(dfd[i].getName().getLocalName());
			usuarios[i]=dfd[i].getName().getLocalName();
		}

		Iterator<MostrarMensajesListener> iter=setMostrarMensajesListener.iterator();
		while(iter.hasNext()) {
			iter.next().usuariosListener(usuarios);
		}
	}

	public void sendMsgToManager(EncodedMessage mensaje) {
		Utils.enviarMensaje(this, "manager", mensaje);
	}
	
	public void sendReport(Report report) {
		Utils.enviarMensaje(this, "reportmanager", report);
	}
	
	@Override
	//Para env�o de mensajes que no son del tipo "mensajer�a"
	public void enviarMensaje(String destinatario, String mensaje) {
    	ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
    	
    	for(int i=0;i<dfd.length;i++)
    	{
//    		System.out.println("Destino: " + dfd[i].getName().getLocalName());
//    		System.out.println("A donde lo quiero mandar: " + destinatario);
    		if(dfd[i].getName().getLocalName().equals(destinatario))
    			aclMessage.addReceiver(dfd[i].getName());
    	}
    	
    	
        aclMessage.setOntology("ontologia");
        //el lenguaje que se define para el servicio
        aclMessage.setLanguage(new SLCodec().getName());
        //el mensaje se transmita en XML
        aclMessage.setEnvelope(new Envelope());
		//cambio la codificacion de la carta
		aclMessage.getEnvelope().setPayloadEncoding("ISO8859_1");
        //aclMessage.getEnvelope().setAclRepresentation(FIPANames.ACLCodec.XML); 
		try {
			aclMessage.setContentObject((Serializable)mensaje);
		} catch (IOException e) {
			e.printStackTrace();
		}
		send(aclMessage);  
	}

	@Override
	// En este m�todo controlamos los chats de tipo JPanelPrincipalMessenger en los que hay que mostrar mensajes
	public void avisarEventos(MostrarMensajesListener mostrarMensajesListener) {
		this.setMostrarMensajesListener.add(mostrarMensajesListener);
		
	}
	
	public boolean offensiveMessagePopUp(EncodedMessage em) {
	      Object[] options = {"¡Por supuesto que si!",
	        "No, mejor no lo envio..."};
	      int seleccion = JOptionPane.showOptionDialog(
	    	   new JOptionPane(),
	           "Has mandado un mensaje ofensivo a una persona.\n ¿De verdad quieres enviarlo?", 
	           "Mensaje de alerta",
	           JOptionPane.YES_NO_OPTION,
	           JOptionPane.WARNING_MESSAGE,
	           null,    // null para icono por defecto.
	           options,   // null para YES, NO y CANCEL
	           "opcion 1");
	      
	      if (seleccion == JOptionPane.YES_OPTION) { 
	    	  em.setEnviar(true); 
	    }
	      else {
	    	  em.setEnviar(false); 
	    }    
	      return true;
	}
	
	public void reportAdvice() {
		this.avisos++;
		switch (this.avisos) {
		case 1:
			JOptionPane.showMessageDialog(new JOptionPane(), "Has sido reportado por un usuario\n por actitud ofensiva.\n\n"
					+ "A la siguiente se te expulsa del chat.", "AVISO",
			        JOptionPane.WARNING_MESSAGE);
			break;	
		default:
			JOptionPane.showMessageDialog(new JOptionPane(), "Has sido reportado por segunda vez. Quedas expulsado\n", "AVISO",
			        JOptionPane.WARNING_MESSAGE);
			this.finalizar();
			break;
		}
	}
	
	public String esComando(String str) {		

		String vuelta = "";


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

		case TokensEmoji.comando11:
			vuelta = TokensEmoji.comando11value;
			break;

		default:
			vuelta = str;
			break;
		}
		return vuelta;
	}

	public void finalizar() {
		try {
			DFService.deregister(this);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		this.doDelete();
	}	


}
