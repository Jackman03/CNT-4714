
public class items {
	String id;
	String itemName;
	boolean inStock;
	int amount;
	int customerAmount;
	Double price;
	String discount;
	Double totalPrice;

	
	
	
	public items(String id, String itemName, boolean inStock, int amount, Double price, int customerAmount ,String discount,Double totalPrice) {
		super();
		this.id = id;
		this.itemName = itemName;
		this.inStock = inStock;
		this.amount = amount;
		this.price = price;
		this.customerAmount = customerAmount;
		this.discount = discount;
		this.totalPrice = totalPrice;
	}
	
	public items() {
		super();
	
	}

	@Override
	public String toString() {
		
		totalPrice = Math.round(totalPrice * 100) /100.0;	//used to round to nearest deicmal
		
		return id +" " + itemName + "$"+price + " " + customerAmount+ " " +" " + discount+ " $" + totalPrice;
	}
	
	public String makeCSV() {
		
		
		
		return id +", " + itemName + ", $"+price + ", " + customerAmount+ ", " + discount+ ", $" + totalPrice + ", ";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public boolean isInStock() {
		return inStock;
	}

	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getCustomerAmount() {
		return customerAmount;
	}

	public void setCustomerAmount(int customerAmount) {
		this.customerAmount = customerAmount;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
	
	




	

	
}
