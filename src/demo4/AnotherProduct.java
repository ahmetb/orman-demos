package demo4;

import org.orman.mapper.Model;
import org.orman.mapper.annotation.*;

@Entity
public class AnotherProduct extends Model<AnotherProduct> {
	
	private volatile float TAX = 18.0f;
	
	@PrimaryKey(autoIncrement=true)
	public int id;
	
	@Column(type = "CHAR(32)")
	private String name;
	
	private int pieces;
	
	private float price;
	
	public String getName() {
		return name;
	}
	
	public void setName(String val) {
		name = val;
	}
	
	public int getPieces() {
		return pieces;
	}
	
	public void setPieces(int val) {
		pieces = val;
	}
	
	public float getPrice() {
		return price;
	}
	
	public void setPrice(float val) {
		price = val;
	}
	
	public float buy(int howMuch) {
		float totalCost;
		
		if (pieces < howMuch)
			return 0.0f;
		
		totalCost = price * howMuch;
		totalCost += (totalCost * TAX) / 100.0f;
		pieces -= howMuch;
		
		this.update();
		
		return totalCost;
	}
	
	@Override
	public String toString() {
		return String.format("Product: %s, Available: %d, Price: %f", name, pieces, price);
	}
}
