package inteligentes.chat.agentes;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.wrapper.ControllerException;
import jade.wrapper.PlatformController;
import jade.wrapper.PlatformEvent;

import inteligentes.chat.aux.*;


public class CoordinatorAgent extends Agent implements PlatformController.Listener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void setup() {
        try {
			this.getContainerController().addPlatformListener(this);
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void bornAgent(PlatformEvent arg0) {
		// TODO Auto-generated method stub
		DFAgentDescription dFAgentDescription=new DFAgentDescription();
		dFAgentDescription.setName(new AID(arg0.getAgentGUID(), true));
		System.out.println("Agente nuevo: "+arg0.getAgentGUID());
		
		Utils.enviarMensaje(this, "mensajer�a", null);
	}

	@Override
	public void deadAgent(PlatformEvent arg0) {
		// TODO Auto-generated method stub
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

