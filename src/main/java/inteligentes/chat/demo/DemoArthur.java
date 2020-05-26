package inteligentes.chat.demo;

import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class DemoArthur extends Agent {
	
	private static final long serialVersionUID = 1L;
	public static final String NAME = "demoarthur";

	
	@Override
	public void setup() {
		
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        
        ServiceDescription sd = new ServiceDescription();
        sd.setName(NAME);
        sd.setType(NAME);
        sd.addOntologies("analyzer");
        sd.addLanguages(new SLCodec().getName());
        
        dfd.addServices(sd);
        
		addBehaviour(new DemoArthurBehaviour());
        
        try {
            DFService.register(this, dfd);
		} catch(FIPAException e) {
			e.printStackTrace();
		}
	}
}
