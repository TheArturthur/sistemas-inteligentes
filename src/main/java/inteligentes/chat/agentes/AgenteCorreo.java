package inteligentes.chat.agentes;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JOptionPane;

import inteligentes.chat.auxiliar.Utils;
import inteligentes.chat.behaviours.AgenteCorreoBehaviour;
import inteligentes.chat.behaviours.CorreoReportsBehaviour;
import inteligentes.chat.behaviours.CorreoSenderBehaviour;
import inteligentes.chat.gui.MainGuiMessenger;
import inteligentes.chat.interfaces.MostrarMensajesListener;
import inteligentes.chat.interfaces.SendMessageListener;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
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


	
	public AgenteCorreo() {
		super();
		this.avisos = 0;
	}
	
	public void setup() {
		
		//Crear servicios proporcionados por el agente y registrarlos en la plataforma
        DFAgentDescription dfd1 = new DFAgentDescription();
        dfd1.setName(getAID());
         
        
        ServiceDescription sd1 = new ServiceDescription();
        sd1.setName("Correo");
        //establezco el tipo del servicio "mensajeria" para poder localizarlo cuando haga una busqueda
        sd1.setType("mensajer�a");
        sd1.addOntologies("ontologia");
        sd1.addLanguages(new SLCodec().getName());
               
        ServiceDescription sd2 = new ServiceDescription();
        sd2.setName("Correo");
        //establezco el tipo del servicio "mensajeria" para poder localizarlo cuando haga una busqueda
        sd2.setType("correosender");
        sd2.addOntologies("forwarding");
        sd2.addLanguages(new SLCodec().getName());
        
        
        ServiceDescription sd3 = new ServiceDescription();
        sd3.setName("Correo");
        //establezco el tipo del servicio "mensajeria" para poder localizarlo cuando haga una busqueda
        sd3.setType("correoreport");
        sd3.addOntologies("reports");
        sd3.addLanguages(new SLCodec().getName());
        
        
        dfd1.addServices(sd1);
        dfd1.addServices(sd2);
        dfd1.addServices(sd3);
        
             
        
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
//		ParallelBehaviour parallelBehaviour = new ParallelBehaviour(this,ParallelBehaviour.WHEN_ALL);
//		
//		parallelBehaviour.addSubBehaviour(new AgenteCorreoBehaviour(this));
//		parallelBehaviour.addSubBehaviour(new CorreoSenderBehaviour(this));
//		parallelBehaviour.addSubBehaviour(new CorreoReportsBehaviour(this));
//		
//        addBehaviour(parallelBehaviour);
        
        addBehaviour(new AgenteCorreoBehaviour(this));
        addBehaviour(new CorreoSenderBehaviour(this));
        addBehaviour(new CorreoReportsBehaviour(this));
		
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
    		System.out.println("Destino: " + dfd[i].getName().getLocalName());
    		System.out.println("A donde lo quiero mandar: " + destinatario);
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
	
	public boolean offensiveMessagePopUp() {
	      Object[] options = {"Yes, im sure.",
	        "Not send the message"};
	      int seleccion = JOptionPane.showOptionDialog(
	    	   new JOptionPane(),
	           "Seleccione opcion", 
	           "Mensaje de alerta",
	           JOptionPane.YES_NO_OPTION,
	           JOptionPane.WARNING_MESSAGE,
	           null,    // null para icono por defecto.
	           options,   // null para YES, NO y CANCEL
	           "opcion 1");
	      
	      if (seleccion == JOptionPane.YES_OPTION) { return true; }
	      else {return false;}

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

	public void finalizar() {
		try {
			DFService.deregister(this);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		this.doDelete();
	}	


}
