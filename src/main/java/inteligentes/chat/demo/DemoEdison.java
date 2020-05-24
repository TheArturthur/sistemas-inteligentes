package inteligentes.chat.demo;

import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class DemoEdison extends Agent {
	private static final long serialVersionUID = 1L;
	public static final String NAME = "demoedison";

	
	@Override
	public void setup() {
		
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        
        ServiceDescription sd = new ServiceDescription();
        sd.setName(NAME);
        //establezco el tipo del servicio "coordinador" para poder localizarlo cuando haga una busqueda
        sd.setType(NAME);
        sd.addOntologies("builder");
        sd.addLanguages(new SLCodec().getName());
        
        dfd.addServices(sd);
        
		addBehaviour(new DemoEdisonBehaviour(this));
        
        try {
            DFService.register(this, dfd);
		} catch(FIPAException e) {
			e.printStackTrace();
		}
	}
	
	public String esComando(String str) {		
		
		String vuelta = "";
		
		switch (str) {
		case TokensDemoEdison.comando1:
			vuelta = TokensDemoEdison.comando1value;
			break;
			
		case TokensDemoEdison.comando2:
			vuelta = TokensDemoEdison.comando2value;
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
