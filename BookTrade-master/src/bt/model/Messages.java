package bt.model;

public enum Messages {
	Service("Gielda Ksiazek - Java JADE"),
	Selling_Service("sprzedaz-ksiazki"),
	Buy_Behaviour("kupowanie-ksiazki"),
	Cyclic_Behaviour("cfp"),
	Order("zamowienie"),
	Content_Not_Available("not-available");
	
	
	private String message;
	
	Messages(String message) {
		this.message = message;
	}
	
	public String getMsg() {
		return message;
	}
}