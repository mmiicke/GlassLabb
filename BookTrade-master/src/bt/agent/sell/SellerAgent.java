package bt.agent.sell;

import java.util.Hashtable;
import bt.agent.sell.behaviour.OfferBookBehaviour;
import bt.agent.sell.behaviour.PurchaseBookBehaviour;
import bt.agent.sell.gui.SellerGui;
import bt.model.Messages;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class SellerAgent extends Agent {
	private static final long serialVersionUID = 5633568447791272917L;
	private Hashtable<String, Integer> booksCatalog;	
	private SellerGui gui;
	
	
	public SellerAgent() {
		this.booksCatalog = new Hashtable<String, Integer>();
	}
	
	
	protected void setup() {
		gui = new SellerGui(this);
		gui.showGui();
		
		DFAgentDescription dfAgentDescription = new DFAgentDescription();
		ServiceDescription serviceDescription = new ServiceDescription();
		
		dfAgentDescription.setName(getAID());
		serviceDescription.setType(Messages.Selling_Service.getMsg());
		serviceDescription.setName(Messages.Service.getMsg());
		dfAgentDescription.addServices(serviceDescription);
		
		try {
			DFService.register(this, dfAgentDescription);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		
		addBehaviour(new OfferBookBehaviour(booksCatalog));
		addBehaviour(new PurchaseBookBehaviour(booksCatalog));
	}
	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}

		gui.dispose();
		System.out.println(" > Sprzedawca " + getAID().getName() + " usuniety...");
	}
	public void updateCatalogue(final String title, final int price) {
		addBehaviour(createSellingBookBehaviour(title, price));
	}
	private OneShotBehaviour createSellingBookBehaviour(final String title, final int price) {
		return new OneShotBehaviour() {
			private static final long serialVersionUID = 1L;

			public void action() {
				booksCatalog.put(title, new Integer(price));
				System.out.println(" + Ksiazka: " + title + " dodana do katalogu, Cena ksiazki: $" + price);
			}
		};
	}
}