package inteligentes.chat.agentes;

import inteligentes.chat.auxiliar.Utils;
import jade.core.AID;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.wrapper.ControllerException;
import jade.wrapper.PlatformController;
import jade.wrapper.PlatformEvent;
import jade.wrapper.StaleProxyException;


public class CoordinatorAgent extends Agent implements PlatformController.Listener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NAME = "coordinator";
	

	@Override
	public void setup() {
		
        try {
			this.getContainerController().addPlatformListener(this);
		} catch (ControllerException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void bornAgent(PlatformEvent arg0) {
		DFAgentDescription dFAgentDescription=new DFAgentDescription();
		dFAgentDescription.setName(new AID(arg0.getAgentGUID(), true));
		System.out.println("Agente nuevo: "+arg0.getAgentGUID());
		
		Utils.enviarMensaje(this, "mensajer�a", null);
	}

	@Override
	public void deadAgent(PlatformEvent arg0) {
		DFAgentDescription dFAgentDescription=new DFAgentDescription();
		dFAgentDescription.setName(new AID(arg0.getAgentGUID(), true));
		
		System.out.println("Agente muerto: "+arg0.getAgentGUID());
		
		Utils.enviarMensaje(this, "mensajer�a", null);
	}

	@Override
	public void killedPlatform(PlatformEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resumedPlatform(PlatformEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startedPlatform(PlatformEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void suspendedPlatform(PlatformEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

