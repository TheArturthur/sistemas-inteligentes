package inteligentes.chat.auxiliar;

import java.io.IOException;

import inteligentes.chat.agentes.AgenteCorreo;
import inteligentes.chat.agentes.AnalyzerAgent;
import inteligentes.chat.agentes.CoordinatorAgent;
import inteligentes.chat.agentes.ManagerAgent;
import inteligentes.chat.agentes.ReportManagerAgent;
import inteligentes.chat.demo.DemoEdison;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class SetEnvironment {
	
	private static jade.wrapper.AgentContainer cc;
	
	
    public static void loadBoot(){

    	jade.core.Runtime rt = jade.core.Runtime.instance();

        rt.setCloseVM(true);
        System.out.println("Runtime created");

        Profile profile = new ProfileImpl(null, 1099, null);
        System.out.println("Profile created");

        System.out.println("Launching a whole in-process platform..."+profile);
        cc = rt.createMainContainer(profile);

        try {

            ProfileImpl pContainer = new ProfileImpl(null, 1200, null);

            rt.createAgentContainer(pContainer);

            System.out.println("Containers created");
            System.out.println("Launching the rma agent on the main container ...");
            cc.createNewAgent("rma","jade.tools.rma.rma", new Object[0]).start();
            //CREATE AGENTS
            createInitialAgents();
        } catch (StaleProxyException e) {
            System.err.println("Error during boot!!!");
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    private static void createInitialAgents() throws StaleProxyException {
        cc.createNewAgent(CoordinatorAgent.NAME, CoordinatorAgent.class.getName(), new Object[] { }).start();
        cc.createNewAgent(ManagerAgent.NAME, ManagerAgent.class.getName(), new Object[] { }).start();
		//TODO
		//cc.createNewAgent(ClasificatorAgent.NAME, ClasificatorAgent.class.getName() , new Object[] { }).start();
		cc.createNewAgent(ReportManagerAgent.NAME, ReportManagerAgent.class.getName(), new Object[] { }).start();
		
		cc.createNewAgent(AgenteCorreo.NAME + "1", AgenteCorreo.class.getName(), new Object[] { }).start();
		cc.createNewAgent(AgenteCorreo.NAME + "2", AgenteCorreo.class.getName(), new Object[] { }).start();
		
    }
    
}
