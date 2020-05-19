package inteligentes.chat.agentes;

import inteligentes.chat.behaviours.ManagerAgentBehaviour;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class ManagerAgent extends Agent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NAME = "manager";

	
	@Override
	public void setup() {
		
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setName("manager");
        //establezco el tipo del servicio "coordinador" para poder localizarlo cuando haga una busqueda
        sd.setType("manager");
        sd.addOntologies("ontologia");
        sd.addLanguages(new SLCodec().getName());
        dfd.addServices(sd);
        
        addBehaviour(new ManagerAgentBehaviour());
        
        try {
            DFService.register(this, dfd);
		} catch(FIPAException e) {
			e.printStackTrace();
		}
	}

}
