package inteligentes.chat.agentes;

import inteligentes.chat.auxiliar.Utils;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.wrapper.ControllerException;
import jade.wrapper.PlatformController;
import jade.wrapper.PlatformEvent;


public class CoordinatorAgent extends Agent implements PlatformController.Listener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static jade.wrapper.AgentContainer cc;

	@Override
	public void setup() {
		
        try {
			cc.createNewAgent(ManagerAgent.NAME, ManagerAgent.class.getName(), null).start();
			//TODO
			//cc.createNewAgent(ClasificatorAgent.NAME, ClasificatorAgent.class.getName(), null).start();
			//cc.createNewAgent(ReportManagerAgent.NAME, ManagerAgent.class.getName(), null).start();

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

