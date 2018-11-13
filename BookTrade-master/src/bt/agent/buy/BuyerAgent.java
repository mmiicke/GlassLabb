package bt.agent.buy;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import java.util.ArrayList;
import java.util.List;

import bt.agent.buy.behaviour.BuyBookBehaviour;
import bt.model.Messages;
import jade.core.AID;

public class BuyerAgent extends Agent {
	private static final long serialVersionUID = 8488379474730664834L;
	private static int timeSeconds = 15;
	private String titleOfBookToBuy;
	private List<AID> sellersAgents;
	

	public BuyerAgent() {
		this.titleOfBookToBuy = null;
		this.sellersAgents = new ArrayList<AID>();
	}
	
	
	protected void setup() {
		System.out.println("Witaj! Agent Kupujacy ==> " + getAID().getLocalName() + " <== zostal utworzony.");
		
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			titleOfBookToBuy = args[0].toString();
			System.out.println(" > Poszukiwana ksiazka ==> " + titleOfBookToBuy);
			
			addBehaviourToBuyerAgent();
		}
	}
	private void addBehaviourToBuyerAgent() {
		addBehaviour(createTickerBehaviour());
	}
	private TickerBehaviour createTickerBehaviour() {	
		return new TickerBehaviour(this, timeSeconds * 1000) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onTick() {
				DFAgentDescription dfAgentDescription = new DFAgentDescription();
				ServiceDescription serviceDescription = new ServiceDescription();
				
				System.out.println(" >> Proba odnalezienia oraz zakupu ksiazki: " + titleOfBookToBuy);
				
				searchServiceForBuyerAgent(dfAgentDescription, serviceDescription, myAgent);
				myAgent.addBehaviour(new BuyBookBehaviour(sellersAgents, titleOfBookToBuy));
			}
		};
	}
	private void searchServiceForBuyerAgent(DFAgentDescription dfAgentDescription, ServiceDescription serviceDescription, Agent myAgent) {
		serviceDescription.setType(Messages.Selling_Service.getMsg());
		dfAgentDescription.addServices(serviceDescription);
		
		try {
			DFAgentDescription[] result = DFService.search(myAgent, dfAgentDescription);
			System.out.println(" >>> Agenci posiadajacy ksiazke ==> " + titleOfBookToBuy + " <== na sprzedaz: ");
			
			for(DFAgentDescription dfAgent : result) {
				sellersAgents.add(dfAgent.getName());
				System.out.println(" - " + dfAgent.getName().getLocalName());
			}
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}
	protected void takeDown() {
		System.out.println(" >> Kupujacy " + getAID().getLocalName() + " usuniety...");
	}
	


	public String getTitleOfBookToBuy() {
		return titleOfBookToBuy;
	}
	public void setTitleOfBookToBuy(String titleOfBookToBuy) {
		this.titleOfBookToBuy = titleOfBookToBuy;
	}
	public List<AID> getSellersAgents() {
		return sellersAgents;
	}
	public void setSellersAgents(List<AID> sellersAgents) {
		this.sellersAgents = sellersAgents;
	}
}