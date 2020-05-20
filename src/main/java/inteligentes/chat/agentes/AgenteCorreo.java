package inteligentes.chat.agentes;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import inteligentes.chat.auxiliar.Utils;
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
	
	public AgenteCorreo() {
		super();
	}
	
	public void setup() {
		
		//Crear servicios proporcionados por el agente y registrarlos en la plataforma
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setName("Correo");
        //establezco el tipo del servicio "mensajeria" para poder localizarlo cuando haga una busqueda
        sd.setType("mensajer�a");
        sd.addOntologies("ontologia");
        sd.addLanguages(new SLCodec().getName());
        dfd.addServices(sd);
        
        try {
        	//registro los servicios en el agente DF
            DFService.register(this, dfd);
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
		Utils.enviarMensaje(this, "mensajer�a", null);
	}


	public void actualizarLista() {
		dfd=Utils.buscarAgentes(this, "mensajer�a");
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

	public void sendMsgToManager(Object objeto) {
		Utils.enviarMensaje(this, "manager", objeto);
	}
	
	public void sendReport(Object objeto) {
		Utils.enviarMensaje(this, "reportmanager", objeto);
	}
	
	@Override
	//Para env�o de mensajes que no son del tipo "mensajer�a"
	public void enviarMensaje(String destinatario, String mensaje) {
    	ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
    	
    	for(int i=0;i<dfd.length;i++)
    	{
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

	public void finalizar() {
		try {
			DFService.deregister(this);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		this.doDelete();
	}	


}
