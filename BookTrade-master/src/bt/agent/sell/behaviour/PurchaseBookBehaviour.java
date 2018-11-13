package bt.agent.sell.behaviour;

import java.util.Hashtable;

import bt.model.Messages;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class PurchaseBookBehaviour extends CyclicBehaviour {
	private static final long serialVersionUID = 1L;
	private Hashtable<String, Integer> booksCatalog;
	
	
	public PurchaseBookBehaviour(Hashtable<String, Integer> booksCatalog) {
		this.booksCatalog = booksCatalog;
	}
	
	
	public void action() {
		MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
		ACLMessage aclMessage = myAgent.receive(messageTemplate);
		
		if (aclMessage != null) {
			String title = aclMessage.getContent();
			ACLMessage reply = aclMessage.createReply();

			Integer price = (Integer) booksCatalog.remove(title);
			if (price != null) {
				reply.setPerformative(ACLMessage.INFORM);
				System.out.print(" > Ksiazka: " + title + ", sprzedana dla Agenta: " + aclMessage.getSender().getLocalName());
			} else {
				reply.setPerformative(ACLMessage.FAILURE);
				reply.setContent(Messages.Content_Not_Available.getMsg());
			}
			myAgent.send(reply);
		} else {
			block();
		}
	}
}