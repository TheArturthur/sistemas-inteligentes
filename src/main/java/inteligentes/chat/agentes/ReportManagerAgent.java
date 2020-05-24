package inteligentes.chat.agentes;

import inteligentes.chat.behaviours.ReportManagerAgentBehaviour;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

@SuppressWarnings("serial")
public class ReportManagerAgent extends Agent {

	public static final String NAME = "reportmanager";
	
	@Override
	public void setup() {
		
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setName(NAME);
        sd.setType(NAME);
        sd.addOntologies("ontologia");
        sd.addLanguages(new SLCodec().getName());
        dfd.addServices(sd);
        
        addBehaviour(new ReportManagerAgentBehaviour());
        
        try {
            DFService.register(this, dfd);
		} catch(FIPAException e) {
			e.printStackTrace();
		}
	}

}
