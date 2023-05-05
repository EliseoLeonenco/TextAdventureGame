package model;

public class Consumable {

	private String name;
    private int healthGain;
    private int count;
    
    public Consumable(String name, int healthGain, int count) {
        this.name = name;
        this.healthGain = healthGain;
        this.count = count;
    }
	
    public String getName() {
        return name;
    }
    
    public int getHealthGain() {
        return healthGain;
    }
    
    public int getCount() {
        return count;
    }
    
    public void useConsumable() {
    	count = count -1;
    }
    
    public void addConsumableCount(int num) {
    	this.count = count + num;
    }
    
    public boolean hasConsumable() {
    	return count > 0;
    }
    
}
