package model;

public class Monster {
    private String name;
    private int health, damageMax, damageMin, attackAccuracy;

    public Monster(String name, int health, int damageMax, int damageMin, int attackAccuracy) {
        this.name = name;
        this.health = health;
        this.damageMax = damageMax;
        this.damageMin = damageMin;
        this.attackAccuracy = attackAccuracy;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }
    
    public void setHealth(int h) {
        this.health = h;
    }
  
    public int getDamageMax() {
        return damageMax;
    }
    
    public int getDamageMin() {
        return damageMin;
    }
    
    public int getAttackAccuracy() {
        return attackAccuracy;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
    }

    public boolean isDead() {
        return health <= 0;
    }
}

