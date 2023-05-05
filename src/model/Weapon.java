package model;

public class Weapon {

	private String name;
	private int attack1DamageMax, attack1DamageMin, attack1Accuracy;
	private int attack2DamageMax, attack2DamageMin, attack2Accuracy;	
	private int attack3DamageMax, attack3DamageMin, attack3Accuracy;
	private int noSpellbookattackDamageMax, noSpellbookattackDamageMin, noSpellbookattackAccuracy;
	private String attack1Description, attack2Description, attack3Description, noSpellbookAttackDescription;

	public Weapon(String name, int attack1DamageMax, int attack1DamageMin, int attack1Accuracy, int attack2DamageMax, int attack2DamageMin, int attack2Accuracy, int attack3DamageMax, 
			int attack3DamageMin, int attack3Accuracy, int noSpellbookattackDamageMax, int noSpellbookattackDamageMin, int noSpellbookattackAccuracy, String attack1Description, 
			String attack2Description, String attack3Description, String noSpellbookAttackDescription) {
		this.name = name;
		this.attack1DamageMax = attack1DamageMax; 
		this.attack1DamageMin = attack1DamageMin;
		this.attack1Accuracy = attack1Accuracy;
		this.attack2DamageMax = attack2DamageMax; 
		this.attack2DamageMin = attack2DamageMin;
		this.attack2Accuracy = attack2Accuracy;
		this.attack3DamageMax = attack3DamageMax; 
		this.attack3DamageMin = attack3DamageMin;
		this.attack3Accuracy = attack3Accuracy;
		this.noSpellbookattackDamageMax = noSpellbookattackDamageMax;
		this.noSpellbookattackDamageMin = noSpellbookattackDamageMin;
		this.noSpellbookattackAccuracy = noSpellbookattackAccuracy;
		this.attack1Description = attack1Description;
		this.attack2Description = attack2Description;
		this.attack3Description = attack3Description;
		this.noSpellbookAttackDescription = noSpellbookAttackDescription;
	}
	
	public String getName() {
        return name;
    }
	
	public int getAttack1DamageMax() {
        return attack1DamageMax;
    }
	
	public int getAttack1DamageMin() {
        return attack1DamageMin;
    }
	
	public int getAttack1Accuracy() {
        return attack1Accuracy;
    }
	
	public int getAttack2DamageMax() {
        return attack2DamageMax;
    }
	
	public int getAttack2DamageMin() {
        return attack2DamageMin;
    }
	
	public int getAttack2Accuracy() {
        return attack2Accuracy;
    }
	
	public int getAttack3DamageMax() {
        return attack3DamageMax;
    }
	
	public int getAttack3DamageMin() {
        return attack3DamageMin;
    }
	
	public int getAttack3Accuracy() {
        return attack3Accuracy;
    }
	
	public int getNoSpellbookAttackDamageMax() {
        return noSpellbookattackDamageMax;
    }
	
	public int getNoSpellbookAttackDamageMin() {
        return noSpellbookattackDamageMin;
    }
	
	public int getNoSpellbookattackAccuracy() {
        return noSpellbookattackAccuracy;
    }
	
	public String getAttack1Description() {
        return attack1Description;
    }
	
	public String getAttack2Description() {
        return attack2Description;
    }
	
	public String getAttack3Description() {
        return attack3Description;
    }
	
	public String getNoSpellbookAttackDescription() {
        return noSpellbookAttackDescription;
    }
	
}

