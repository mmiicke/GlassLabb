package bt.agent.sell.behaviour;

import java.util.Hashtable;

import bt.model.Messages;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class OfferBookBehaviour extends CyclicBehaviour {
	private static final long serialVersionUID = 1L;
	private Hashtable<String, Integer> booksCatalog;
	
	
	public OfferBookBehaviour(Hashtable<String, Integer> booksCatalog) {
		this.booksCatalog = booksCatalog;
	}
	
	
	public void action() {
		MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.CFP);
		ACLMessage aclMessage = myAgent.receive(messageTemplate);
		
		if (aclMessage != null) {
			String title = aclMessage.getContent();
			ACLMessage reply = aclMessage.createReply();

			Integer price = (Integer) booksCatalog.get(title);
			if (price != null) {
				reply.setPerformative(ACLMessage.PROPOSE);
				reply.setContent(String.valueOf(price.intValue()));
			} else {
				reply.setPerformative(ACLMessage.REFUSE);
				reply.setContent(Messages.Content_Not_Available.getMsg());
			}
			myAgent.send(reply);
		} else {
			block();
		}
	}
}