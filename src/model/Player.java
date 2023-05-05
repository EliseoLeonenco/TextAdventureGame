package model;

public class Player {
	
    private String name, position;
    private int health, maxHealth, damage, gold;
    private Weapon weapon;

    public Player(String name, int health, int maxHealth) {
        this.name = name;
        this.health = health;
        this.maxHealth = maxHealth;
    }

    public String getName() {
        return name;
    }

    public Weapon getWeapon() {
        return weapon;
    }
     
    public int getHealth() {
        return health;
    }
    
    public int getMaxHealth() {
        return maxHealth;
    }
    
    public int getDamage() {
        return damage;
    }
    
    public int getGold() {
        return gold;
    }
    
    public String getPosition() {
        return position;
    }
    
    public void setHealth(int hp) {
        this.health = hp;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    
    public void setDamage(int dmg) {
        this.damage = dmg;
    }

    public void addGold(int amount) {
        this.gold += amount;
    }

    public void addExtraMaxHealth(int maxHp) {
        this.maxHealth = this.maxHealth + maxHp;
        this.health = this.health+ maxHp;
    }
    
    public void takeDamage(int damage) {
        this.health -= damage;
    }
    
    public boolean isDead() {
        return health <= 0;
    }
    
    public void reset() {
    	maxHealth = 100;
        health = maxHealth;
    }
    
    public void heal(int hp) {
        this.health = this.health + hp;
    }

    public boolean isMaxHealth() {
        return this.health == this.maxHealth;
    }
    
    public boolean hasEnoughGold(int g) {
    	return this.gold >= g;
    }
}

