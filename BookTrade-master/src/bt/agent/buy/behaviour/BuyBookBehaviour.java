package bt.agent.buy.behaviour;

import java.util.List;

import bt.model.Messages;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class BuyBookBehaviour extends Behaviour {
	private static final long serialVersionUID = 1L;
	private AID bestSeller; 
	private int bestPrice, repliesCount, step; 
	private MessageTemplate messageTemplate; 
	private List<AID> sellersAgents;
	private String titleOfBookToBuy;
	
	
	public BuyBookBehaviour(List<AID> sellersAgents, String titleOfBookToBuy) {
		this.sellersAgents = sellersAgents;
		this.titleOfBookToBuy = titleOfBookToBuy;
		this.bestPrice = 0;
		this.repliesCount = 0;
		this.step = 1;
	}
	

	public void action() {
		switch (step) {
			case 1:
				stepOne();
				break;
			case 2:
				ACLMessage reply = myAgent.receive(messageTemplate);
				stepTwo(reply);
				break;
			case 3:
				stepThree();
				break;
			case 4:
				reply = myAgent.receive(messageTemplate);
				stepFour(reply);
				break;
		}
	}
	private void stepOne() {
		ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
		
		for(AID agent : sellersAgents) {
			cfp.addReceiver(agent);
		}
		cfp.setContent(titleOfBookToBuy);
		cfp.setConversationId(Messages.Buy_Behaviour.getMsg());
		cfp.setReplyWith(Messages.Cyclic_Behaviour.getMsg() + System.currentTimeMillis()); 
		
		myAgent.send(cfp);
		messageTemplate = MessageTemplate.and(MessageTemplate.MatchConversationId(Messages.Buy_Behaviour.getMsg()), MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
		step = 2;
	}
	private void stepTwo(ACLMessage reply) {		
		if (reply != null) {
			if (reply.getPerformative() == ACLMessage.PROPOSE) {
				int price = Integer.parseInt(reply.getContent());
				
				if (bestSeller == null || price < bestPrice) {
					bestPrice = price;
					bestSeller = reply.getSender();
				}
			}
			
			repliesCount++;
			if (repliesCount >= sellersAgents.size()) {
				step = 3;
			}
		} else {
			block();
		}
	}
	private void stepThree() {
		ACLMessage order = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
		
		order.addReceiver(bestSeller);
		order.setContent(titleOfBookToBuy);
		order.setConversationId(Messages.Buy_Behaviour.getMsg());
		order.setReplyWith(Messages.Order.getMsg() + System.currentTimeMillis());
		myAgent.send(order);

		messageTemplate = MessageTemplate.and(MessageTemplate.MatchConversationId(Messages.Buy_Behaviour.getMsg()), MessageTemplate.MatchInReplyTo(order.getReplyWith()));
		step = 4;
	}
	private void stepFour(ACLMessage reply) {
		if (reply != null) {
			
			if (reply.getPerformative() == ACLMessage.INFORM) {
				System.out.print(". Pomyslnie zakupiona od sprzedawcy: " + reply.getSender().getLocalName() + ", za cene: $" + bestPrice);
				
				myAgent.doDelete();
			} else {
				System.out.println("Poszukiwana ksiazka zostala sprzedana.");
			}
			step = 5;
		} else {
			block();
		}
	}
	public boolean done() {
		if (step == 3 && bestSeller == null) {
			System.out.println(titleOfBookToBuy + " nie dostepna na sprzedaz.");
		}
		return ((step == 3 && bestSeller == null) || step == 5);
	}
}