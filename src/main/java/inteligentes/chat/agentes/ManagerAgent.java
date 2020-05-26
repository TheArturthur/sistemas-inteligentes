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
        sd.setName(NAME);
        sd.setType(NAME);
        sd.addOntologies("ontologia");
        sd.addLanguages(new SLCodec().getName());
        
        ServiceDescription sd1 = new ServiceDescription();
        sd1.setName(NAME);
        sd1.setType(NAME);
        sd1.addOntologies("edison");
        sd1.addLanguages(new SLCodec().getName());
        
        ServiceDescription sd2 = new ServiceDescription();
        sd2.setName(NAME);
        sd2.setType(NAME);
        sd2.addOntologies("arthur");
        sd2.addLanguages(new SLCodec().getName());
        
        
        dfd.addServices(sd);
        dfd.addServices(sd1);
        dfd.addServices(sd2);
        
        addBehaviour(new ManagerAgentBehaviour());
        
        try {
            DFService.register(this, dfd);
		} catch(FIPAException e) {
			e.printStackTrace();
		}
	}

}
