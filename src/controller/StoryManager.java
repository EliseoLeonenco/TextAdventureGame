 package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JTextArea;
import javax.swing.Timer;
import model.Consumable;
import model.Monster;
import model.Player;
import model.Weapon;
import view.GameFrame;
import view.InGamePanel;

public class StoryManager {

	Game game;
	GameFrame gameFrame;
	InGamePanel inGamePanel;
	Player player = new Player("Player", 100, 100);
	Monster werewolf_Storage = new Monster("Werewolf", 160, 35, 30, 75), ghoul1_Lounge = new Monster("Ghoul1", 65, 14, 10, 67), ghoul2_Lounge = new Monster("Ghoul2", 70, 14, 10, 67), 
			skeleton_Dining = new Monster("Skeleton", 110, 28, 24, 85), undead1_Kitchen = new Monster("Undead1", 80, 16, 12, 60), undead2_Kitchen = new Monster("Undead2", 75, 16, 12, 60), 
			undead3_Kitchen = new Monster("Undead3", 85, 16, 12, 60), goblin_Bathroom = new Monster("Goblin", 350, 1, 0, 0), werewolf_Ballroom = new Monster("Werewolf", 180, 38, 33, 70), 
			hunter_Church = new Monster("Corrupted Hunter", 240, 17, 10, 70);
	Weapon fists = new Weapon("Fists", 11, 8, 85, 7, 4, 95, 14, 12, 75, 4, 2, 80, "Charge punch: A charged strong attack punch", "Rapid punch: A flurry of punches that quickly hits enemies", "Haymaker: A heavy, slow attack that deals massive damage", "Power Hit: Punch the enemy with your fists"), 
			ceremonialDagger = new Weapon("Ceremonial Dagger", 18, 15, 80, 15, 12, 90, 22, 19, 70, 7, 4, 80, "Backstab: Shadow step towards an enemy and stab it in the back", "Vicious Riposte: A sturdy and accurate attack, deliver a vicious powerful attack", "Deathblow: An intense finishing powerful downwards slash", "Ceremonial Slash: Slash the enemy with your dagger"),	
			soulBlade = new Weapon("Soul Blade", 28, 25, 80, 24, 21, 90, 33, 27, 68, 12, 9, 80, "Soul Slash: A powerful attack that deals heavy damage to the enemy and steals their life force", "Blade Storm: Spin your blade around, creating an undodgeable whirlwind", "Arcane Fury: Fury of arcane sword slashes enhanced by the runes of the blade", "Blade Slash: Slash the enemy with your sword"), 
			dualDaggers = new Weapon("Dual Daggers", 30, 27, 69, 25, 23, 85, 27, 22, 75, 14, 12, 80, "Cross Slash: Quick slash both daggers in a cross pattern, dealing high damage to enemies directly in front", "Whirlwind: Spin around with both daggers, creating an undodgeable whirlwind", "Double Strike: Deliver two quick strikes in rapid succession, dealing moderate damage", "Dagger Slashes: Slash the enemy with both daggers"),
			sorcerersWand = new Weapon("Sorcerers Wand", 25, 22, 95, 29, 25, 78, 36, 34, 60, 10, 8, 80, "Magic Missile: Shoots a small bolt of magical energy that deals moderate damage", "Arcana Blast: Unleash a powerful burst of arcane energy", "Holy Smite: Take a moment to call down a burst of divine energy on an enemy, dealing heavy damage", "Fireball: Shoot a small low damaging fireball towards the enemy"), 
			scythe = new Weapon("Shadow Scythe", 38, 33, 75, 36, 32, 86, 40, 37, 62,	17, 14, 80, "Dark Whirlwind: Spin the scythe around, creating a whirlwind of darkness that damages in front of you", "Phantom Strike: A silent attack that causes the player to disappear and reappear behind an enemy, slicing from behind", "Bone Breaker: Swing the scythe low, sweeping enemies off their feet and dealing moderate damage", "Scythe Slash: Slash the enemy with your scythe"),
			greatSword = new Weapon("Moonblade Great Sword", 38, 34, 85, 46, 42, 67, 42, 37, 75, 20, 18, 80, "Crescent Moon Slash: A quick slash that sends a crescent-shaped shockwave at enemies", "Lunar Beam: An energy beam attack that shoots out from the sword, piercing through enemies, dealing massive damage", "Moonlight Slash: Send a moonlight slash projectile towards an enemy", "Greatsword Slash: Slash the enemy with your greatsword");
	Boolean secretRoomDaggerTaken=false, secretRoomPotionsTaken=false, scytheTaken=false, librarySpellbookTaken=false, libraryPotionsTaken=false, 
			amuletTaken=false, rustyKeyTaken=false, pocketWatchTaken=false, artifactBladeTaken=false, artifactDaggersTaken=false, artifactWandTaken=false, 
			ringTaken=false, gauntletTaken=false, churchKeyTaken=false, purificationCubeTaken=false, potionsTakenTrader=false, bootsTaken=false, 
			chestplateTaken=false, helmetTaken=false, greatSwordTaken=false, reEnteringSecretRoom=false, storageRoomWerewolfDefeated=false, ghoulsDefeated=false, 
			skeletonDefeated=false, undeadDefeated=false, ballroomWerewolfDefeated=false, goblinDefeated=false, hunterDefeated=false, reEnterChurch=false;
	Clip inGameMusicClip, combatClip, finalBossClip;
	Consumable largeHealthPotion= new Consumable("Large Health Potion", 60, 3), smallHealthPotion = new Consumable("Small Health Potion", 35, 2);
	int monsterDamage, monsterDamage2, monsterDamage3, playerDamage, attackAccuracy;
	private Timer animationTimer;
	
	StoryManager(Game g, GameFrame gFrame) {
		game = g;
		gameFrame = gFrame;
	}

	public void defaultSetup() {
		player.reset();
		player.setWeapon(fists);
		Game.getGameFrame().getInGamePanel().changeHealthNum(player.getHealth());
		Game.getGameFrame().getInGamePanel().changeWeaponName(player.getWeapon().getName());
		enterManorHall();
	}
	
	public void selectPosition(String command) {
		switch(player.getPosition()) {
		case "instructions": 
			switch(command) {
			case "start": wakeUp(); break;
			}
			break;
		case "wakeUp": 
			switch(command) {
			case "next": wakeUp2(); break;
			}
			break;
		case "wakeUp2":
			switch(command) {
			case "inspect table", "inspect the table": 
				if(secretRoomDaggerTaken==false&&secretRoomPotionsTaken==false) {
					secretRoomInspectTable1(); break;
				} else if(secretRoomDaggerTaken==true&&secretRoomPotionsTaken==true) {
					secretRoomInspectTableEmpty(); break;
				} else if(secretRoomDaggerTaken==false&&secretRoomPotionsTaken==true) {
					secretRoomInspectTable2(); break;
				} else if(secretRoomDaggerTaken==true&&secretRoomPotionsTaken==false) {
					secretRoomInspectTable3(); break;
				}
				break;
			case "leave", "leave room", "leave the room", "leave secret room", "leave secretroom", "leave the secret room", "leave the secretroom": 
				secretRoomLeave(); break;
			case "back": wakeUp(); break;
			}
			break;
		case "secretRoomInspectTable1":
			switch(command) {
			case "take dagger", "take ceremonial dagger", "pick up dagger", "pick up ceremonial dagger", "pickup dagger", "pickup ceremonial dagger": 
				secretRoomTakeDagger(); break;
			case "take potion", "pick up potion", "pickup potion", "take potions", "pick up potions", "pickup potions": 
				secretRoomTakePotions(); break;
			case "back": 
				if(reEnteringSecretRoom==true) {
					reEnterSecretRoom(); break;
				} else if(reEnteringSecretRoom==false) {
					wakeUp2(); break;
				}
				break;
			case "leave", "leave room", "leave the room", "leave secret room", "leave secretroom", "leave the secret room", "leave the secretroom": 
				secretRoomLeave(); break;
			}
			break;
		case "secretRoomInspectTable2":
			switch(command) {
			case "take dagger", "take ceremonial dagger", "pick up dagger", "pick up ceremonial dagger", "pickup dagger", "pickup ceremonial dagger": 
				secretRoomTakeDagger(); break;
			case "back": 
				if(reEnteringSecretRoom==true) {
					reEnterSecretRoom(); break;
				} else if(reEnteringSecretRoom==false) {
					wakeUp2(); break;
				}
				break;
			case "leave", "leave room", "leave the room", "leave secret room", "leave secretroom", "leave the secret room", "leave the secretroom": 
				secretRoomLeave(); break;
			}
			break;
		case "secretRoomInspectTable3":
			switch(command) {
			case "take potion", "pick up potion", "pickup potion", "take potions", "pick up potions", "pickup potions": 
				secretRoomTakePotions(); break;
			case "back": 
				if(reEnteringSecretRoom==true) {
					reEnterSecretRoom(); break;
				} else if(reEnteringSecretRoom==false) {
					wakeUp2(); break;
				}
				break;
			case "leave", "leave room", "leave the room", "leave secret room", "leave secretroom", "leave the secret room", "leave the secretroom": 
				secretRoomLeave(); break;
			}
			break;
		case "secretRoomInspectTableEmpty":
			switch(command) {
			case "back":
				if(reEnteringSecretRoom==true) {
				reEnterSecretRoom(); break;
			} else if(reEnteringSecretRoom==false) {
				wakeUp2(); break;
			}
			break;
			case "leave", "leave room", "leave the room", "leave secret room", "leave secretroom", "leave the secret room", "leave the secretroom": 
				if(reEnteringSecretRoom==true) {
				reEnterSecretRoom(); break;
			} else if(reEnteringSecretRoom==false) {
				secretRoomLeave(); break;
				}
			}
			break;
		case "secretRoomLeave":
			switch(command) {
			case "sneak", "sneak by", "sneak by werewolf", "sneak around", "sneak around werewolf", "sneak behind crates", "sneak behind crate", "sneak around crates", "sneak around crate": 
				sneakByStorageRoomWerewolf(); break;
			case "attack werewolf", "attack", "attack the werewolf", "fight", "fight the werewolf", "fight werewolf": 
				engageCombat_StorageWerewolf(); break;
			}
			break;
		case "fight_StorageWerewolf":
			switch(command) {
			case "1": player.setDamage(playerDamage = new java.util.Random().nextInt(player.getWeapon().getAttack1DamageMax()-player.getWeapon().getAttack1DamageMin()+1)+player.getWeapon().getAttack1DamageMin()); 
			attackAccuracy = player.getWeapon().getAttack1Accuracy();
			monsterTakesDamage_StorageWerewolf(); break;
			case "2": player.setDamage(playerDamage = new java.util.Random().nextInt(player.getWeapon().getAttack2DamageMax()-player.getWeapon().getAttack2DamageMin()+1)+player.getWeapon().getAttack2DamageMin()); 
			attackAccuracy = player.getWeapon().getAttack2Accuracy();
			monsterTakesDamage_StorageWerewolf(); break;
			case "3": player.setDamage(playerDamage = new java.util.Random().nextInt(player.getWeapon().getAttack3DamageMax()-player.getWeapon().getAttack3DamageMin()+1)+player.getWeapon().getAttack3DamageMin());
			attackAccuracy = player.getWeapon().getAttack3Accuracy();
			monsterTakesDamage_StorageWerewolf(); break;
			case "4": 
				if (player.isMaxHealth()) {
				} else if(smallHealthPotion.hasConsumable() == true) {
					useSmallHealthPotion_StorageWerewolf(); break;
				}
				break;
			case "5": 
				if (player.isMaxHealth()) {
				} else if(largeHealthPotion.hasConsumable() == true) {
					useLargeHealthPotion_StorageWerewolf(); break;
					}
				}
				break;
			case "fight_StorageWerewolf_noSpellbook":
				switch(command) {
				case "1": player.setDamage(playerDamage = new java.util.Random().nextInt(player.getWeapon().getNoSpellbookAttackDamageMax()-player.getWeapon().getNoSpellbookAttackDamageMin()+1)+player.getWeapon().getNoSpellbookAttackDamageMin()); 
				attackAccuracy = player.getWeapon().getNoSpellbookattackAccuracy();
				monsterTakesDamage_StorageWerewolf(); break;	
				case "2": 
					if (player.isMaxHealth()) {
					} else if(smallHealthPotion.hasConsumable() == true) {
						useSmallHealthPotion_StorageWerewolf(); break;
					}
					break;
				case "3": 
					if (player.isMaxHealth()) {
					} else if(largeHealthPotion.hasConsumable() == true) {
						useLargeHealthPotion_StorageWerewolf(); break;
					}
					break;
				}
		case "defeatedStorageWerewolf":
			switch(command) {
			case "next": storageRoomPostBattle(); break;
			}
			break;
		case "storageRoomPostBattle":
			switch(command) {
			case "enter library", "enter the library": enterLibrary(); break;
			case "leave", "leave library", "leave the library": enterLibrary(); break;
			case "enter secret room": reEnterSecretRoom(); break;
			case "inspect corpse", "inspect werewolf", "inspect wolf", "inspect the corpse", "inspect the werewolf", "inspect the wolf": 
				if(scytheTaken==false) {
					inspectStorageRoomCorpse(); break;
				} else if(scytheTaken==true) {
					inspectStorageRoomCorpseEmpty(); break;
				}
			}
			break;
		case "sneakByStorageRoomWerewolf":
			switch(command) {
			case "pull leaver", "pull the leaver", "pull", "interact with leaver", "interact leaver", "interact with the leaver": 
				pullLeaver(); break;
			}
			break;
		case "leaveStorageRoom":
			switch(command) {
			case "pull leaver", "pull the leaver", "pull", "interact with leaver", "interact leaver", "interact with the leaver": 
				pullLeaver(); break;
			}
			break;
		case "reEnterStorageRoom1":
			switch(command) {
			case "sneak", "sneak by", "sneak by werewolf", "sneak around", "sneak around werewolf", "sneak behind crates", "sneak behind crate", "sneak around crates", "sneak around crate": 
				sneakByStorageRoomWerewolf2(); break;
			case "attack werewolf", "attack", "attack the werewolf", "fight", "fight the werewolf", "fight werewolf": 
				fight_StorageWerewolf(); break;
			}
			break;
		case "reEnterStorageRoom2":
			switch(command) {
			case "sneak", "sneak by", "sneak by werewolf", "sneak around", "sneak around werewolf", "sneak behind crates", "sneak behind crate", "sneak around crates", "sneak around crate": 
				sneakByStorageRoomWerewolf3(); break;
			case "attack werewolf", "attack", "attack the werewolf", "fight", "fight the werewolf", "fight werewolf": 
				fight_StorageWerewolf(); break;
			}
			break;
		case "reEnterStorageRoomPostBattle":
			switch(command) {
			case "enter library", "enter the library", "leave", "leave storage room", "leave storageroom", "leave the storage room", "leave the storageroom": 
				enterLibrary(); break;
			case "enter secret room", "enter secretroom", "enter the secret room", "enter the secretroom": 
				reEnterSecretRoom(); break;
			case "inspect corpse", "inspect the corpse", "inspect hunter", "inspect the hunter", "inspect body", "inspect the body", "inspect dead body", "inspect the dead body": 
				if(scytheTaken==false) {
					inspectStorageRoomCorpse(); break;
				} else if(scytheTaken==true) {
					inspectStorageRoomCorpseEmpty(); break;
				}
			}
			break;	
		case "inspectStorageRoomCorpse":
			switch(command) {
			case "back": reEnterStorageRoomPostBattle(); break;
			case "take scythe", "take", "take the scythe", "pick up scythe", "pick up", "pick up the scythe", "pickup scythe", "pickup", "pickup the scythe": 
				takeStorageRoomWeapon(); break;
			}
			break;
		case "inspectStorageRoomCorpseEmpty":
			switch(command) {
			case "back": reEnterStorageRoomPostBattle(); break;
			}
			break;
		case "sneakByStorageRoomWerewolf2":
			switch(command) {
			case "next": reEnterSecretRoom(); break;
			}
			break;
		case "sneakByStorageRoomWerewolf3":
			switch(command) {
			case "next": enterLibrary(); break;
			}
			break;
		case "pullLeaver":
			switch(command) {
			case "enter library", "enter the library", "next": enterLibrary(); break;
			}
			break;
		case "reEnterStorageRoom":
			switch(command) {
			case "enter secret room", "enter the secret room", "enter secretroom", "enter the secretroom": 
				reEnterSecretRoom(); break;
			case "enter library", "enter the library", "leave", "leave storage room", "leave storageroom", "leave the storage room", "leave the storageroom": 
				enterLibrary(); break;
			} 
			break;
		case "reEnterSecretRoom":
			switch(command) {
			case "enter storage room", "enter the storage room", "enter storageroom", "enter the storageroom", "leave", "leave secret room", "leave secretroom", "leave the secretroom", "leave the secret room": 
				if(storageRoomWerewolfDefeated==false) {
					reEnterStorageRoom2(); break;
				} else if(storageRoomWerewolfDefeated==true) {
					reEnterStorageRoomPostBattle(); break;
				} 
			break;	
			case "inspect table", "inspect the table": 
				if(secretRoomDaggerTaken==false&&secretRoomPotionsTaken==false) {
					secretRoomInspectTable1(); break;
				} else if(secretRoomDaggerTaken==true&&secretRoomPotionsTaken==true) {
					secretRoomInspectTableEmpty(); break; 
				} else if(secretRoomDaggerTaken==false&&secretRoomPotionsTaken==true) {
					secretRoomInspectTable2(); break; 
				} else if(secretRoomDaggerTaken==true&&secretRoomPotionsTaken==false) {
					secretRoomInspectTable3(); break; 
				}
			break;
			}
			break; 
		case "enterLibrary":
			switch(command) {
			case "inspect bookshelf", "inspect the bookshelf", "inspect shelf", "inspect the shelf": inspectLibraryBookshelf(); break; 
			case "inspect table", "inspect the table": 
				if(librarySpellbookTaken==false&&libraryPotionsTaken==false) {
					inspectLibraryTable1(); break;
				} else if(librarySpellbookTaken==true&&libraryPotionsTaken==true) {
					inspectLibraryTableEmpty(); break;
				} else if(librarySpellbookTaken==true&&libraryPotionsTaken==false) {
					inspectLibraryTable2(); break;
				} else if(librarySpellbookTaken==false&&libraryPotionsTaken==true) {
					inspectLibraryTable3(); break;
				}
				break;
			case "leave", "leave library", "leave the library", "enter manor hall", "enter manorhall", "enter hall", "enter the manor hall", "enter the manorhall", "enter the hall": 
				enterManorHall(); break;
			case "enter storage room", "enter storageroom", "enter the storage room", "enter the storageroom": 
				if(storageRoomWerewolfDefeated==false) {
					reEnterStorageRoom1(); break;
				} else if(storageRoomWerewolfDefeated==true) {
					reEnterStorageRoomPostBattle(); break;
				}
			break;
			}
			break;	
		case "inspectLibraryBookshelf":
			switch(command) {
			case "back": enterLibrary(); break;
			}
			break;
		case "inspectLibraryTable1":
			switch(command) {
			case "take potions", "take the potions", "take potion", "take the potion", "pick up potions", "pick up the potions", "pick up potion", "pick up the potion", "pickup potions", "pickup the potions", "pickup potion", "pickup the potion": 
				libraryTakePotions(); break;
			case "take book", "take the book", "pick up book", "pick up the book", "pickup book", "pickup the book", "take spellbook", "take the spellbook", "pick up spellbook", "pick up the spellbook", "pickup spellbook", "pickup the spellbook": 
				libraryTakeSpellbook(); break;
			case "back": enterLibrary(); break;
			}
			break;
		case "inspectLibraryTable2":
			switch(command) {
			case "take potions", "take the potions", "take potion", "take the potion", "pick up potions", "pick up the potions", "pick up potion", "pick up the potion", "pickup potions", "pickup the potions", "pickup potion", "pickup the potion": 
				libraryTakePotions(); break;
			case "back": enterLibrary(); break;
			}
			break;
		case "inspectLibraryTable3":
			switch(command) {
			case "take book", "take the book", "pick up book", "pick up the book", "pickup book", "pickup the book": 
				libraryTakeSpellbook(); break;
			case "back": enterLibrary(); break;
			}
			break;
		case "inspectLibraryTableEmpty":
			switch(command) {
			case "back": enterLibrary(); break;
			}
			break;
		case "mainHall":
			switch(command) {
			case "enter library", "enter the library": enterLibrary(); break;
			case "enter lounge", "enter the lounge": 
				if(ghoulsDefeated==false) {
					enterLounge(); break;
				} else if(ghoulsDefeated==true) {
					enterLoungePostBattle(); break;
				}
				break;
			case "enter gallery", "enter the gallery": enterGallery(); break;
			case "enter dining room", "enter the dining room", "enter diningroom", "enter the diningroom": 
				if(skeletonDefeated==false) {
					enterDiningRoom(); break;
				} else if(skeletonDefeated==true) {
					enterDiningRoomPostBattle(); break;
				}
				break;
			case "enter crossing corridor", "enter the crossing corridor", "enter crossingcorridor", "enter the crossingcorridor": 
				enterCrossingCorridor(); break;
			case "go upstairs", "go up stairs", "take stairs", "take the stairs": 
				goUpstairs(); break;
			case "leave", "leave hall", "leave the hall", "leave manor hall", "leave the manor hall", "leave manorhall", "leave the manorhall": 
				enterEntranceHall(); break;
			}
			break;		
		case "goUpstairs":
			switch(command) {
			case "back", "leave": enterManorHall(); break;
			}
			break;	
		case "enterLounge":
			switch(command) {
			case "attack", "attack ghouls", "attack the ghouls",  "attack ghoul",  "attack the ghoul": 
				engageCombat_Ghouls(); break;
			case "leave", "leave lounge", "leave the lounge", "enter manor hall", "enter the manor hall", "enter manorhall", "enter the manorhall", "enter hall", "enter the hall": 
				enterManorHall(); break;
			}
			break;
		case "enterLoungePostBattle":
			switch(command) {
			case "rest", "rest at fireplace", "rest fireplace", "rest at fire", "rest fire": 
				restAtFireplace(); break;
			case "inspect ghouls", "inspect ghoul", "inspect the ghouls", "inspect the ghoul", "inspect corpse", "inspect the corpse", "inspect corpses", "inspect the corpses": 
				if(pocketWatchTaken==true) {
					inspectGhoulsEmpty(); break;
				} else if(pocketWatchTaken==false) {
					inspectGhouls(); break;
				}
				break;
			case "leave", "leave lounge", "leave the lounge", "enter manor hall", "enter the manor hall", "enter manorhall", "enter the manorhall", "enter hall", "enter the hall": 
				enterManorHall(); break;
			}
			break;
		case "restAtFireplace":
			switch(command) {
			case "next": restAtFireplace2(); break;
			}
			break;	
		case "restAtFireplace2":
			switch(command) {
			case "1": changeWeapon_Fireplace(); break;
			case "2": enterManorHall(); break;
			}
			break;
		case "changeWeapon_Fireplace":
			switch(command) {
			case "1": 
				if (secretRoomDaggerTaken==true) {
					takeWeapon1_Fireplace(); break;
				}
				break;
			case "2": 
				if(artifactDaggersTaken==true) {
					takeWeapon2_Fireplace(); break;
				}
				break;
			case "3": 
				if(artifactBladeTaken==true) {
					takeWeapon3_Fireplace(); break;
				}
				break;
			case "4": 
				if(artifactWandTaken==true) {
					takeWeapon4_Fireplace(); break;
				}
				break;
			case "5":
				if(scytheTaken==true) {
					takeWeapon5_Fireplace(); break;
				}
				break;
			case "6":
				if(greatSwordTaken==true) {
					takeWeapon6_Fireplace(); break;
				}
				break;
			case "7": enterManorHall(); break;
			}
			break;
		case "loungePostBattle":
			switch(command) {
			case "rest", "rest at fireplace", "rest fireplace", "rest at fire", "rest fire": 
				restAtFireplace(); break;
			case "inspect ghouls", "inspect corpse", "inspect corpses": 
				if(pocketWatchTaken==true) {
					inspectGhoulsEmpty(); break;
				} else if(pocketWatchTaken==false) {
					inspectGhouls(); break;
				}
				break;
			case "leave", "leave the lounge", "leave lounge": 
				enterManorHall(); break;
			}
			break;
		case "inspectGhouls":
			switch(command) {
			case "take watch", "take the watch", "take pocket watch", "take the pocket watch", "take pocketwatch", "take the pocketwatch": 
				takePocketWatch(); break;
			case "back": enterLoungePostBattle(); break;
			case "leave", "leave lounge", "leave the lounge", "enter manor hall", "enter the manor hall", "enter manorhall", "enter the manorhall", "enter hall", "enter the hall": 
				enterManorHall(); break;
			}
			break;
		case "inspectGhoulsEmpty":
			switch(command) {
			case "back": 
				enterLoungePostBattle(); break;
			case "leave", "leave lounge", "leave the lounge", "enter manor hall", "enter the manor hall", "enter manorhall", "enter the manorhall", "enter hall", "enter the hall": 
				enterManorHall(); break;
			}
			break;	
		case "fight_Ghouls":
			switch(command) {
			case "1": player.setDamage(playerDamage = new java.util.Random().nextInt(player.getWeapon().getAttack1DamageMax()-player.getWeapon().getAttack1DamageMin()+1)+player.getWeapon().getAttack1DamageMin());  
			attackAccuracy = player.getWeapon().getAttack1Accuracy();
			if(ghoul1_Lounge.isDead()==false && ghoul2_Lounge.isDead()==false) {
				chooseTarget_Ghouls(); break;
			} else if(ghoul1_Lounge.isDead()==true && ghoul2_Lounge.isDead()==false) {
				monsterTakesDamage_Ghoul2(); break;
			} else if(ghoul1_Lounge.isDead()==false && ghoul2_Lounge.isDead()==true) {
				monsterTakesDamage_Ghoul1(); break;
			}
			break;
			case "2": player.setDamage(playerDamage = new java.util.Random().nextInt(player.getWeapon().getAttack2DamageMax()-player.getWeapon().getAttack2DamageMin()+1)+player.getWeapon().getAttack2DamageMin()); 
			attackAccuracy = player.getWeapon().getAttack2Accuracy();
			if(ghoul1_Lounge.isDead()==false && ghoul2_Lounge.isDead()==false) {
				chooseTarget_Ghouls(); break;
			} else if(ghoul1_Lounge.isDead()==true && ghoul2_Lounge.isDead()==false) {
				monsterTakesDamage_Ghoul2(); break;
			} else if(ghoul1_Lounge.isDead()==false && ghoul2_Lounge.isDead()==true) {
				monsterTakesDamage_Ghoul1(); break;
			}
			break;
			case "3": player.setDamage(playerDamage = new java.util.Random().nextInt(player.getWeapon().getAttack3DamageMax()-player.getWeapon().getAttack3DamageMin()+1)+player.getWeapon().getAttack3DamageMin());
			attackAccuracy = player.getWeapon().getAttack3Accuracy();
			if(ghoul1_Lounge.isDead()==false && ghoul2_Lounge.isDead()==false) {
				chooseTarget_Ghouls(); break;
			} else if(ghoul1_Lounge.isDead()==true && ghoul2_Lounge.isDead()==false) {
				monsterTakesDamage_Ghoul2(); break;
			} else if(ghoul1_Lounge.isDead()==false && ghoul2_Lounge.isDead()==true) {
				monsterTakesDamage_Ghoul1(); break;
			}
			break;
			case "4": 
				if (player.isMaxHealth()) {
				} else if(smallHealthPotion.hasConsumable() == true) {
					useSmallHealthPotion_Ghouls(); break;
				}
				break;
			case "5": 
				if (player.isMaxHealth()) {
				} else if(largeHealthPotion.hasConsumable() == true) {
					useLargeHealthPotion_Ghouls(); break;
				} 
				break;
			}
			break;
		case "fight_Ghouls_noSpellbook":
			switch(command) {
			case "1": player.setDamage(playerDamage = new java.util.Random().nextInt(player.getWeapon().getNoSpellbookAttackDamageMax()-player.getWeapon().getNoSpellbookAttackDamageMin()+1)+player.getWeapon().getNoSpellbookAttackDamageMin()); 
			attackAccuracy = player.getWeapon().getNoSpellbookattackAccuracy();
			if(ghoul1_Lounge.isDead()==false && ghoul2_Lounge.isDead()==false) {
				chooseTarget_Ghouls(); break;
			} else if(ghoul1_Lounge.isDead()==true && ghoul2_Lounge.isDead()==false) {
				monsterTakesDamage_Ghoul2(); break;
			} else if(ghoul1_Lounge.isDead()==false && ghoul2_Lounge.isDead()==true) {
				monsterTakesDamage_Ghoul1(); break;
			}
			break;
			case "4": 
				if (player.isMaxHealth()) {
				} else if(smallHealthPotion.hasConsumable() == true) {
					useSmallHealthPotion_Ghouls(); break;
				}
				break;
			case "5": 
				if (player.isMaxHealth()) {
				} else if(largeHealthPotion.hasConsumable() == true) {
					useLargeHealthPotion_Ghouls(); break;
				} 
				break;
			}
			break;
		case "chooseTarget_Ghouls":
			switch(command) {
			case "1": monsterTakesDamage_Ghoul1(); break;
			case "2": monsterTakesDamage_Ghoul2(); break;
			}
			break;
		case "defeatedGhouls":
			switch(command) {
			case "next": loungePostBattle(); break;
			}
			break;
		case "enterGallery":
			switch(command) {
			case "inspect", "inspect artifacts", "inspect the artifacts", "inspect artifact", "inspect the artifact", "inspect glass cases", "inspect the glass cases", "inspect glass case", "inspect the glass case": 
				if(artifactBladeTaken==true||artifactDaggersTaken==true||artifactWandTaken==true) {
					inspectArtifactsEmpty(); break;
				} else if(artifactBladeTaken==false&&artifactDaggersTaken==false&&artifactWandTaken==false) {
					inspectArtifacts();	break;
				}
				break;
			case "leave", "leave gallery", "leave the gallery", "enter manor hall", "enter the manor hall", "enter manorhall", "enter the manorhall", "enter hall", "enter the hall": 
				enterManorHall(); break;
			}
			break;	
		case "inspectArtifacts":
			switch(command) {
			case "1": takeArtifact1(); break;
			case "2": takeArtifact2(); break;
			case "3": takeArtifact3(); break;
			case "leave": enterManorHall(); break;
			}
			break;
		case "takeArtifact1":
			switch(command) {
			case "next": enterGallery(); break;
			}
			break;
		case "takeArtifact2":
			switch(command) {
			case "next": enterGallery(); break;
			}
			break;
		case "takeArtifact3":
			switch(command) {
			case "next": enterGallery(); break;
			}
			break;
		case "enterDiningRoom":
			switch(command) {
			case "enter kitchen", "enter the kitchen": 
				if(undeadDefeated==true) {
					enterKitchenPostBattle(); break;
				} else if(undeadDefeated==false) {
					enterKitchen(); break;
				}
				break;
			case "inspect skeleton", "inspect the skeleton", "inspect corpse", "inspect the corpse": 
				if(ringTaken==true) {
					inspectSkeletonEmpty(); break;
				} else if(ringTaken==false) {
					inspectSkeleton(); break;
				}
				break;
			case "attack skeleton", "attack the skeleton", "attack": 
				engageCombat_Skeleton(); break;
			case "leave", "leave dining room", "leave the dining room", "leave diningroom", "leave the diningroom", "enter manor hall", "enter the manor hall", "enter manorhall", "enter the manorhall", "enter hall", "enter the hall": 
				enterManorHall(); break;
			}
			break;		
		case "enterDiningRoomPostBattle":
			switch(command) {
			case "enter kitchen", "enter the kitchen": 
				if(undeadDefeated==true) {
					enterKitchenPostBattle(); break;
				} else if(undeadDefeated==false) {
					enterKitchen(); break;
				}
				break;
			case "inspect skeleton", "inspect the skeleton", "inspect corpse", "inspect the corpse":
				if(ringTaken==true) {
					inspectDeadSkeletonEmpty(); break;
				} else if(ringTaken==false) {
					inspectDeadSkeleton(); break;
				}
				break;
			case "leave", "leave dining room", "leave the dining room", "leave diningroom", "leave the diningroom", "enter manor hall", "enter the manor hall", "enter manorhall", "enter the manorhall", "enter hall", "enter the hall": 
				enterManorHall(); break;
			}
			break;
		case "diningRoomPostBattle":
			switch(command) {
			case "enter kitchen", "enter the kitchen", "go kitchen", "go to the kitchen", "go to kitchen": 
				if(undeadDefeated==true) {
					enterKitchenPostBattle(); break;
				} else if(undeadDefeated==false) {
					enterKitchen(); break;
				}
				break;
			case "inspect skeleton", "inspect", "inspect corpse": 
				if(ringTaken==true) {
					inspectDeadSkeletonEmpty(); break;
				} else if(ringTaken==false) {
					inspectDeadSkeleton(); break;
				}
				break;
			case "enter hall", "enter manor hall", "enter manorhall": 
				enterManorHall(); break;
			case "leave", "leave dining room", "leave diningroom", "leave the dining room", "leave the diningroom": 
				enterManorHall(); break;
			}
			break;	
		case "inspectSkeleton":
			switch(command) {
			case "take ring", "take the ring", "take precious ring", "take the precious ring", "pick up ring", "pick up the ring", "pickup ring", "pickup the ring", "pickup precious ring", "pickup the precious ring" : 
				takeRing(); break;
			case "attack skeleton", "attack the skeleton", "attack": 
				engageCombat_Skeleton(); break;
			case "leave", "leave dining room", "leave the dining room", "leave diningroom", "leave the diningroom", "enter manor hall", "enter the manor hall", "enter manorhall", "enter the manorhall", "enter hall", "enter the hall":
				enterManorHall(); break;
			case "back": enterDiningRoom(); break;
			}
			break;
		case "inspectDeadSkeleton":
			switch(command) {
			case "take ring", "take the ring", "take precious ring", "take the precious ring", "pick up ring", "pick up the ring", "pickup ring", "pickup the ring", "pickup precious ring", "pickup the precious ring": 
				takeRing(); break;
			case "leave", "leave dining room", "leave the dining room", "leave diningroom", "leave the diningroom", "enter manor hall", "enter the manor hall", "enter manorhall", "enter the manorhall", "enter hall", "enter the hall":
				enterManorHall(); break;
			case "back": enterDiningRoomPostBattle(); break;
			}
			break;
		case "inspectSkeletonEmpty":
			switch(command) {
			case "back": enterDiningRoom(); break;
			case "leave", "leave dining room", "leave the dining room", "leave diningroom", "leave the diningroom", "enter manor hall", "enter the manor hall", "enter manorhall", "enter the manorhall", "enter hall", "enter the hall":
				enterManorHall(); break;
			}
			break;
		case "inspectDeadSkeletonEmpty":
			switch(command) {
			case "back": enterDiningRoomPostBattle(); break;
			case "leave", "leave dining room", "leave the dining room", "leave diningroom", "leave the diningroom", "enter manor hall", "enter the manor hall", "enter manorhall", "enter the manorhall", "enter hall", "enter the hall": 
				enterManorHall(); break;
			}
			break;			
		case "fight_Skeleton":
			switch(command) {
			case "1": player.setDamage(playerDamage = new java.util.Random().nextInt(player.getWeapon().getAttack1DamageMax()-player.getWeapon().getAttack1DamageMin()+1)+player.getWeapon().getAttack1DamageMin()); 
			attackAccuracy = player.getWeapon().getAttack1Accuracy();
			monsterTakesDamage_Skeleton(); break;
			case "2": player.setDamage(playerDamage = new java.util.Random().nextInt(player.getWeapon().getAttack2DamageMax()-player.getWeapon().getAttack2DamageMin()+1)+player.getWeapon().getAttack2DamageMin()); 
			attackAccuracy = player.getWeapon().getAttack2Accuracy();
			monsterTakesDamage_Skeleton(); break;
			case "3": player.setDamage(playerDamage = new java.util.Random().nextInt(player.getWeapon().getAttack3DamageMax()-player.getWeapon().getAttack3DamageMin()+1)+player.getWeapon().getAttack3DamageMin());
			attackAccuracy = player.getWeapon().getAttack3Accuracy();
			monsterTakesDamage_Skeleton(); break;
			case "4": 
				if (player.isMaxHealth()) {
				} else if(smallHealthPotion.hasConsumable() == true) {
					useSmallHealthPotion_Skeleton(); break;
				}
				break;
			case "5": 
				if (player.isMaxHealth()) {
				} else if(largeHealthPotion.hasConsumable() == true) {
					useLargeHealthPotion_Skeleton(); break;
				}
				break;
			}
			break;
		case "fight_Skeleton_noSpellbook":
			switch(command) {
			case "1": player.setDamage(playerDamage = new java.util.Random().nextInt(player.getWeapon().getNoSpellbookAttackDamageMax()-player.getWeapon().getNoSpellbookAttackDamageMin()+1)+player.getWeapon().getNoSpellbookAttackDamageMin()); 
			attackAccuracy = player.getWeapon().getNoSpellbookattackAccuracy();
			monsterTakesDamage_Skeleton(); break;	
			case "2": 
				if (player.isMaxHealth()) {
				} else if(smallHealthPotion.hasConsumable() == true) {
					useSmallHealthPotion_Skeleton(); break;
				}
				break;
			case "3": 
				if (player.isMaxHealth()) {
				} else if(largeHealthPotion.hasConsumable() == true) {
					useLargeHealthPotion_Skeleton(); break;
				}
				break;
			}
			break;
		case "defeatedSkeleton":
			switch(command) {
			case "next": diningRoomPostBattle(); break;
			}
			break;
		case "churchPostBattle":
			switch(command) {
			case "leave", "leave church", "leave the church": 
				crossRoad2(); break;
			}
			break;
		case "reEnterChurchPostBattle":
			switch(command) {
			case "leave", "leave church", "leave the church": 
				crossRoad2(); break;
			}
			break;
		case "enterCrossingCorridor":
			switch(command) {
			case "enter kitchen", "enter the kitchen": 
				if(undeadDefeated==true) {
					enterKitchenPostBattle(); break;
				} else if(undeadDefeated==false) {
					enterKitchen(); break;
				}
				break;
			case "enter ballroom", "enter the ballroom", "enter ball room", "enter the ball room": 
				if(ballroomWerewolfDefeated==true) {
					enterBallroomPostBattle(); break;
				} else if(ballroomWerewolfDefeated==false) {
					enterBallroom(); break;
				}
				break;
			case "enter bathroom", "enter the bathroom", "enter bath room", "enter the bath room": 
				if(goblinDefeated==true) {
					enterBathroomPostBattle(); break;
				} else if(goblinDefeated==false) {
					enterBathroom(); break;
				}
				break;
			case "enter hall", "enter the hall", "enter manorhall", "enter the manorhall", "enter manor hall", "enter the manor hall", "leave", "leave crossing corridor", "leave the crossing corridor", "leave crossingcorridor", "leave the crossingcorridor": 
				enterManorHall(); break;
			}
			break;		
		case "enterKitchen":
			switch(command) {
			case "next": engageCombat_Undead(); break;
			}
			break;
		case "kitchenPostBattle":
			switch(command) {
			case "enter dining room", "enter diningroom", "enter the dining room", "enter the diningroom": 
				if(skeletonDefeated==true) {
					enterDiningRoomPostBattle(); break;
				} else if(skeletonDefeated==false) {
					enterDiningRoom(); break;
				}
				break;
			case "inspect table", "inspect the table": 
				inspectKitchenTable(); break;
			case "enter crossing corridor", "enter crossingcorridor", "enter corridor", "enter the crossing corridor", "enter the crossingcorridor", "enter the corridor": 
				enterCrossingCorridor(); break;
			case "leave", "leave the kitchen", "leave kitchen": 
				enterCrossingCorridor(); break;
			}
			break;
		case "fight_Undead": 
			switch(command) {
			case "1": player.setDamage(playerDamage = new java.util.Random().nextInt(player.getWeapon().getAttack1DamageMax()-player.getWeapon().getAttack1DamageMin()+1)+player.getWeapon().getAttack1DamageMin()); 
			attackAccuracy = player.getWeapon().getAttack1Accuracy();
			if(undead1_Kitchen.isDead()==false && undead2_Kitchen.isDead()==false && undead3_Kitchen.isDead()==false) {
				chooseTarget_Undead4(); break;
			} else if(undead1_Kitchen.isDead()==false && undead2_Kitchen.isDead()==false && undead3_Kitchen.isDead()==true) {
				chooseTarget_Undead1(); break;
			} else if(undead1_Kitchen.isDead()==false && undead2_Kitchen.isDead()==true && undead3_Kitchen.isDead()==false) {
				chooseTarget_Undead2(); break;
			} else if(undead1_Kitchen.isDead()==true && undead2_Kitchen.isDead()==false && undead3_Kitchen.isDead()==false) {
				chooseTarget_Undead3(); break;
			} else if(undead1_Kitchen.isDead()==false && undead2_Kitchen.isDead()==true && undead3_Kitchen.isDead()==true) {
				monsterTakesDamage_Undead1(); break;
			} else if(undead1_Kitchen.isDead()==true && undead2_Kitchen.isDead()==false && undead3_Kitchen.isDead()==true) {
				monsterTakesDamage_Undead2(); break;
			} else if(undead1_Kitchen.isDead()==true && undead2_Kitchen.isDead()==true && undead3_Kitchen.isDead()==false) {
				monsterTakesDamage_Undead3(); break;
			} 
			break;
			case "2": player.setDamage(playerDamage = new java.util.Random().nextInt(player.getWeapon().getAttack2DamageMax()-player.getWeapon().getAttack2DamageMin()+1)+player.getWeapon().getAttack2DamageMin()); 
			attackAccuracy = player.getWeapon().getAttack2Accuracy();
			if(undead1_Kitchen.isDead()==false && undead2_Kitchen.isDead()==false && undead3_Kitchen.isDead()==false) {
				chooseTarget_Undead4(); break;
			} else if(undead1_Kitchen.isDead()==false && undead2_Kitchen.isDead()==false && undead3_Kitchen.isDead()==true) {
				chooseTarget_Undead1(); break;
			} else if(undead1_Kitchen.isDead()==false && undead2_Kitchen.isDead()==true && undead3_Kitchen.isDead()==false) {
				chooseTarget_Undead2(); break;
			} else if(undead1_Kitchen.isDead()==true && undead2_Kitchen.isDead()==false && undead3_Kitchen.isDead()==false) {
				chooseTarget_Undead3(); break;
			} else if(undead1_Kitchen.isDead()==false && undead2_Kitchen.isDead()==true && undead3_Kitchen.isDead()==true) {
				monsterTakesDamage_Undead1(); break;
			} else if(undead1_Kitchen.isDead()==true && undead2_Kitchen.isDead()==false && undead3_Kitchen.isDead()==true) {
				monsterTakesDamage_Undead2(); break;
			} else if(undead1_Kitchen.isDead()==true && undead2_Kitchen.isDead()==true && undead3_Kitchen.isDead()==false) {
				monsterTakesDamage_Undead3(); break;
			} 
			break;
			case "3": player.setDamage(playerDamage = new java.util.Random().nextInt(player.getWeapon().getAttack3DamageMax()-player.getWeapon().getAttack3DamageMin()+1)+player.getWeapon().getAttack3DamageMin());
			attackAccuracy = player.getWeapon().getAttack3Accuracy();
			if(undead1_Kitchen.isDead()==false && undead2_Kitchen.isDead()==false && undead3_Kitchen.isDead()==false) {
				chooseTarget_Undead4(); break;
			} else if(undead1_Kitchen.isDead()==false && undead2_Kitchen.isDead()==false && undead3_Kitchen.isDead()==true) {
				chooseTarget_Undead1(); break;
			} else if(undead1_Kitchen.isDead()==false && undead2_Kitchen.isDead()==true && undead3_Kitchen.isDead()==false) {
				chooseTarget_Undead2(); break;
			} else if(undead1_Kitchen.isDead()==true && undead2_Kitchen.isDead()==false && undead3_Kitchen.isDead()==false) {
				chooseTarget_Undead3(); break;
			} else if(undead1_Kitchen.isDead()==false && undead2_Kitchen.isDead()==true && undead3_Kitchen.isDead()==true) {
				monsterTakesDamage_Undead1(); break;
			} else if(undead1_Kitchen.isDead()==true && undead2_Kitchen.isDead()==false && undead3_Kitchen.isDead()==true) {
				monsterTakesDamage_Undead2(); break;
			} else if(undead1_Kitchen.isDead()==true && undead2_Kitchen.isDead()==true && undead3_Kitchen.isDead()==false) {
				monsterTakesDamage_Undead3(); break;
			} 
			break;
			case "4": 
				if (player.isMaxHealth()) {
				} else if(smallHealthPotion.hasConsumable() == true) {
					useSmallHealthPotion_Undead(); break;
				} 
				break;
			case "5": 
				if (player.isMaxHealth()) {
				} else if(largeHealthPotion.hasConsumable() == true) {
					useLargeHealthPotion_Undead(); break;
				}
				break;
			}
			break;	
		case "fight_Undead_noSpellbook": 
			switch(command) {
			case "1": player.setDamage(playerDamage = new java.util.Random().nextInt(player.getWeapon().getNoSpellbookAttackDamageMax()-player.getWeapon().getNoSpellbookAttackDamageMin()+1)+player.getWeapon().getNoSpellbookAttackDamageMin()); 
			attackAccuracy = player.getWeapon().getNoSpellbookattackAccuracy();
			if(undead1_Kitchen.isDead()==false && undead2_Kitchen.isDead()==false && undead3_Kitchen.isDead()==false) {
				chooseTarget_Undead4(); break;
			} else if(undead1_Kitchen.isDead()==false && undead2_Kitchen.isDead()==false && undead3_Kitchen.isDead()==true) {
				chooseTarget_Undead1(); break;
			} else if(undead1_Kitchen.isDead()==false && undead2_Kitchen.isDead()==true && undead3_Kitchen.isDead()==false) {
				chooseTarget_Undead2(); break;
			} else if(undead1_Kitchen.isDead()==true && undead2_Kitchen.isDead()==false && undead3_Kitchen.isDead()==false) {
				chooseTarget_Undead3(); break;
			} else if(undead1_Kitchen.isDead()==false && undead2_Kitchen.isDead()==true && undead3_Kitchen.isDead()==true) {
				monsterTakesDamage_Undead1(); break;
			} else if(undead1_Kitchen.isDead()==true && undead2_Kitchen.isDead()==false && undead3_Kitchen.isDead()==true) {
				monsterTakesDamage_Undead2(); break;
			} else if(undead1_Kitchen.isDead()==true && undead2_Kitchen.isDead()==true && undead3_Kitchen.isDead()==false) {
				monsterTakesDamage_Undead3(); break;
			} 
			break;
			case "4": 
				if (player.isMaxHealth()) {
				} else if(smallHealthPotion.hasConsumable() == true) {
					useSmallHealthPotion_Undead(); break;
				}
				break;
			case "5": 
				if (player.isMaxHealth()) {
				} else if(largeHealthPotion.hasConsumable() == true) {
					useLargeHealthPotion_Undead(); break;
				}
				break;
			}
			break;
		case "chooseTarget_Undead1":
			switch(command) {
			case "1": monsterTakesDamage_Undead1(); break;
			case "2": monsterTakesDamage_Undead2(); break;
			}
			break;
		case "chooseTarget_Undead2":
			switch(command) {
			case "1": monsterTakesDamage_Undead1(); break;
			case "2": monsterTakesDamage_Undead3(); break;
			}
			break;
		case "chooseTarget_Undead3":
			switch(command) {
			case "1": monsterTakesDamage_Undead2(); break;
			case "2": monsterTakesDamage_Undead3(); break;
			}
			break;
		case "chooseTarget_Undead4":
			switch(command) {
			case "1": monsterTakesDamage_Undead1(); break;
			case "2": monsterTakesDamage_Undead2(); break;
			case "3": monsterTakesDamage_Undead3(); break;
			}
			break;
		case "defeatedUndead":
			switch(command) {
			case "next": kitchenPostBattle(); break;
			}
			break;
		case "enterBathroom":
			switch(command) {
			case "attack goblin", "attack the goblin", "attack": engageCombat_Goblin(); break;
			case "leave", "leave bathroom", "leave the bathroom", "enter crossing corridor", "enter the crossing corridor", "enter crossingcorridor", "enter the crossingcorridor": 
				enterCrossingCorridor(); break;
			}
			break;
		case "enterBathroomPostBattle":
			switch(command) {
			case "inspect goblin", "inspect the goblin", "inspect", "inspect corpse", "inspect the corpse": 
				if(rustyKeyTaken==true) {
					inspectGoblinEmpty(); break;
				} else if(rustyKeyTaken==false) {
					inspectGoblin(); break;
				}
				break;
			case "leave", "leave bathroom", "leave the bathroom", "enter crossing corridor", "enter the crossing corridor", "enter crossingcorridor", "enter the crossingcorridor": 
				enterCrossingCorridor(); break;
			} 
			break;
		case "bathroomPostBattle":
			switch(command) {
			case "inspect goblin", "inspect the goblin", "inspect", "inspect corpse", "inspect the corpse": 
				if(rustyKeyTaken==true) {
					inspectGoblinEmpty(); break;
				} else if(rustyKeyTaken==false) {
					inspectGoblin(); break;
				}
			break;
			case "enter crossing corridor", "enter the crossing corridor", "leave", "leave the bathroom", "leave bathroom": 
				enterCrossingCorridor(); break;
			} 
			break;
		case "fight_Goblin":
			switch(command) {
			case "1": player.setDamage(playerDamage = new java.util.Random().nextInt(player.getWeapon().getAttack1DamageMax()-player.getWeapon().getAttack1DamageMin()+1)+player.getWeapon().getAttack1DamageMin()); 
			attackAccuracy = player.getWeapon().getAttack1Accuracy();
			monsterTakesDamage_Goblin(); break;
			case "2": player.setDamage(playerDamage = new java.util.Random().nextInt(player.getWeapon().getAttack2DamageMax()-player.getWeapon().getAttack2DamageMin()+1)+player.getWeapon().getAttack2DamageMin()); 
			attackAccuracy = player.getWeapon().getAttack2Accuracy();
			monsterTakesDamage_Goblin(); break;
			case "3": player.setDamage(playerDamage = new java.util.Random().nextInt(player.getWeapon().getAttack3DamageMax()-player.getWeapon().getAttack3DamageMin()+1)+player.getWeapon().getAttack3DamageMin());
			attackAccuracy = player.getWeapon().getAttack3Accuracy();
			monsterTakesDamage_Goblin(); break;
			case "4": 
				if (player.isMaxHealth()) {
				} else if(smallHealthPotion.hasConsumable() == true) {
					useSmallHealthPotion_Goblin(); break;
				}
				break;
			case "5": 
				if (player.isMaxHealth()) {
				} else if(largeHealthPotion.hasConsumable() == true) {
					useLargeHealthPotion_Goblin(); break;
				}
				break;
			}
			break;
		case "fight_Goblin_noSpellbook":
			switch(command) {
			case "1": player.setDamage(playerDamage = new java.util.Random().nextInt(player.getWeapon().getNoSpellbookAttackDamageMax()-player.getWeapon().getNoSpellbookAttackDamageMin()+1)+player.getWeapon().getNoSpellbookAttackDamageMin()); 
			attackAccuracy = player.getWeapon().getNoSpellbookattackAccuracy();
			monsterTakesDamage_Goblin(); break;	
			case "2": 
				if (player.isMaxHealth()) {
				} else if(smallHealthPotion.hasConsumable() == true) {
					useSmallHealthPotion_Goblin(); break;
				}
				break;
			case "3": 
				if (player.isMaxHealth()) {
				} else if(largeHealthPotion.hasConsumable() == true) {
					useLargeHealthPotion_Goblin(); break;
				}
				break;
			}	
		case "defeatedGoblin":
			switch(command) {
			case "next": bathroomPostBattle(); break;
			}
			break;	
		case "enterBallroom": 
			switch(command) {
			case "attack werewolf", "attack", "attack the werewolf", "fight", "fight the werewolf", "fight werewolf":  
				engageCombat_BallroomWerewolf(); break;
			case "inspect viewing floor", "inspect the viewing floor", "inspect viewingfloor", "inspect the viewingfloor": 
				if(gauntletTaken==true) {
					inspectViewingFloorEmpty(); break;
				} else if(gauntletTaken==false) {
					inspectViewingFloor(); break;
				}
				break;
			case "leave", "leave ballroom", "leave the ballroom", "enter crossing corridor", "enter the crossing corridor", "enter crossingcorridor", "enter the crossingcorridor":  
				enterCrossingCorridor(); break;
			} 
			break;		
		case "enterBallroomPostBattle":
			switch(command) {
			case "open chest", "open the chest", "interact chest", "interact with chest", "inspect chest", "inspect the chest": 
				if(churchKeyTaken==true) {
					openChestEmpty(); break;
				} else if(churchKeyTaken==false) {
					openChest(); break;
				}
				break;
			case "inspect viewing floor", "inspect viewingfloor", "inspect upper floor", "inspect upperfloor":  
				if(gauntletTaken==true) {
					inspectViewingFloorEmpty(); break;
				} else if(gauntletTaken==false) {
					inspectViewingFloor(); break;
				}
				break;
			case "leave", "leave ballroom", "leave the ballroom", "enter crossing corridor", "enter the crossing corridor", "enter crossingcorridor", "enter the crossingcorridor":  
				enterCrossingCorridor(); break;
			} 
			break;
		case "inspectViewingFloor":
			switch(command) {
			case "take gauntlet", "take the gauntlet", "pick up gauntlet", "pick up the gauntlet", "pickup gauntlet", "pickup the gauntlet": 
				takeGauntlet(); break;
			case "back": 
				if(ballroomWerewolfDefeated==true) {
					enterBallroomPostBattle(); break;
				} else if(ballroomWerewolfDefeated==false) {
					enterBallroom(); break;
				}
				break;	
			} 
			break;
		case "inspectViewingFloorEmpty":
			switch(command) {
			case "back": 
				if(ballroomWerewolfDefeated==true) {
					enterBallroomPostBattle(); break;
				} else if(ballroomWerewolfDefeated==false) {
					enterBallroom(); break;
				}
				break;
			} 
			break;
		case "ballroomPostBattle":
			switch(command) {
			case "open chest", "open the chest": 
				if(churchKeyTaken==true) {
					openChestEmpty(); break;
				} else if(churchKeyTaken==false) {
					openChest(); break;
				}
				break;		
			case "inspect viewing floor", "inspect viewingfloor", "inspect upper floor", "inspect upperfloor": 
				if(gauntletTaken==true) {
					inspectViewingFloorEmpty(); break;
				} else if(gauntletTaken==false) {
					inspectViewingFloor(); break;
				}
			}
			break;	
		case "fight_BallroomWerewolf":
			switch(command) {
			case "1": player.setDamage(playerDamage = new java.util.Random().nextInt(player.getWeapon().getAttack1DamageMax()-player.getWeapon().getAttack1DamageMin()+1)+player.getWeapon().getAttack1DamageMin()); 
			attackAccuracy = player.getWeapon().getAttack1Accuracy();
			monsterTakesDamage_BallroomWerewolf(); break;
			case "2": player.setDamage(playerDamage = new java.util.Random().nextInt(player.getWeapon().getAttack2DamageMax()-player.getWeapon().getAttack2DamageMin()+1)+player.getWeapon().getAttack2DamageMin()); 
			attackAccuracy = player.getWeapon().getAttack2Accuracy();
			monsterTakesDamage_BallroomWerewolf(); break;
			case "3": player.setDamage(playerDamage = new java.util.Random().nextInt(player.getWeapon().getAttack3DamageMax()-player.getWeapon().getAttack3DamageMin()+1)+player.getWeapon().getAttack3DamageMin());
			attackAccuracy = player.getWeapon().getAttack3Accuracy();
			monsterTakesDamage_BallroomWerewolf(); break;
			case "4": 
				if (player.isMaxHealth()) {
				} else if(smallHealthPotion.hasConsumable() == true) {
					useSmallHealthPotion_BallroomWerewolf(); break;
				}
				break;
			case "5": 
				if (player.isMaxHealth()) {
				} else if(largeHealthPotion.hasConsumable() == true) {
					useLargeHealthPotion_BallroomWerewolf(); break;
				}
				break;
			}
			break;
		case "fight_BallroomWerewolf_noSpellbook":
			switch(command) {
			case "1": player.setDamage(playerDamage = new java.util.Random().nextInt(player.getWeapon().getNoSpellbookAttackDamageMax()-player.getWeapon().getNoSpellbookAttackDamageMin()+1)+player.getWeapon().getNoSpellbookAttackDamageMin()); 
			attackAccuracy = player.getWeapon().getNoSpellbookattackAccuracy();
			monsterTakesDamage_BallroomWerewolf(); break;	
			case "2": 
				if (player.isMaxHealth()) {
				} else if(smallHealthPotion.hasConsumable() == true) {
					useSmallHealthPotion_BallroomWerewolf(); break;
				}
				break;
			case "3": 
				if (player.isMaxHealth()) {
				} else if(largeHealthPotion.hasConsumable() == true) {
					useLargeHealthPotion_BallroomWerewolf(); break;
				}
				break;
			}
		case "defeatedBallroomWerewolf":
			switch(command) {
			case "next": ballroomPostBattle(); break;
			}
			break;	
		case "inspectGoblin":
			switch(command) {
			case "take key", "take the key", "pick up key", "pick up the key", "pickup key", "pickup the key": 
				takeRustyKey(); break; 
			case "back": enterBathroomPostBattle(); break;
			} 
			break;
		case "inspectGoblinEmpty":
			switch(command) {
			case "back": enterBathroomPostBattle(); break;
			} 
			break;		
		case "enterKitchenPostBattle":
			switch(command) {
			case "enter dining room", "enter the dining room", "enter diningroom", "enter the diningroom": 
				if(skeletonDefeated==true) {
					enterDiningRoomPostBattle(); break;
				} else if(skeletonDefeated==false) {
					enterDiningRoom(); break;
				}
				break;
			case "inspect table", "inspect the table": inspectKitchenTable(); break;
			case "enter crossing corridor", "enter the crossing corridor", "leave", "leave the kitchen", "leave kitchen": 
				enterCrossingCorridor(); break;
			}
			break;
		case "inspectKitchenTable":
			switch(command) {
			case "back": enterKitchenPostBattle(); break;
			}
			break;
		case "enterEntranceHall": 
			switch(command) {
			case "leave", "leave manor", "leave the manor": 
				exitManor(); break;
			case "enter manor", "enter the manor": 
				enterManorHall(); break;
			case "open closet", "open the closet", "open closet door", "open the closet door": 
				if(rustyKeyTaken==true) {
					if(amuletTaken==false) {
						openClosetDoor(); break;
					} else if(amuletTaken==true) {
						openClosetDoorEmpty(); break;
					}
				}
			break;
			}
			break;
		case "openClosetDoor":
			switch(command) {
			case "take amulet", "take the amulet", "pick up amulet", "pick up the amulet", "pickup amulet", "pickup the amulet": 
				takeAmulet(); break;
			case "back": enterEntranceHall(); break;
			}
			break;
		case "openClosetDoorEmpty":
			switch(command) {
			case "back": enterEntranceHall(); break;
			}
			break;
		case "exitManor":
			switch(command) {
			case "enter forest", "enter the forest", "enter pathway", "enter the pathway": 
				crossRoad1(); break;
			case "enter manor", "enter the manor": 
				enterManorHall(); break;
			}
			break;
		case "crossRoad1": 
			switch(command) {
			case "go north", "north": crossRoad2(); break;
			case "go east", "east": enterLake(); break;
			case "go south", "south": manorFront(); break;
			}
			break;
		case "manorFront":
			switch(command) {
			case "enter manor", "enter the manor":  
				enterManorHall(); break;
			case "enter forest", "enter the forest", "enter pathway", "enter the pathway", "go west", "west":  
				crossRoad1(); break;
			}
			break;
		case "enterLake":
			switch(command) {
			case "rest", "rest at lake", "rest lake": 
				restLake(); break;
			case "leave", "back", "leave lake", "leave the lake": 
				crossRoad1(); break;
			}
			break;
		case "restLake":
			switch(command) {
			case "next": restLake2(); break;
			}
			break;
		case "restLake2":
			switch(command) {
			case "1": changeWeapon_Lake(); break;
			case "2": crossRoad1(); break;
			}
			break;
		case "changeWeapon_Lake":
			switch(command) {
			case "1": 
				if (secretRoomDaggerTaken==true) {
					takeWeapon1_Lake(); break;
				}
				break;
			case "2": 
				if(artifactDaggersTaken==true) {
					takeWeapon2_Lake(); break;
				}
				break;
			case "3": 
				if(artifactBladeTaken==true) {
					takeWeapon3_Lake(); break;
				}
				break;
			case "4": 
				if(artifactWandTaken==true) {
					takeWeapon4_Lake(); break;
				}
				break;
			case "5": 
				if(scytheTaken==true) {
					takeWeapon5_Lake(); break;
				}
				break;
			case "6": crossRoad1(); break;
			}
			break;	
		case "crossRoad2": 
			switch(command) {
			case "go north-east", "go north east", "go northeast", "north-east", "north east", "northeast": 
				outsideChurch(); break;
			case "go north", "north": purificationGrounds(); break;
			case "go west", "west": caravan(); break;
			case "go south", "south": crossRoad1(); break;
			}
			break;
		case "caravan":
			switch(command) {
			case "talk", "talk to the trader", "talk trader", "talk to trader", "talk to the figure", "talk figure", "talk to figure", 
			"speak", "speak to the trader", "speak trader", "speak to trader", "speak to the figure", "speak figure", "speak to figure": 
				traderTalk(); break;
			case "back", "leave", "leave caravan", "leave the caravan", "leave swamp", "leave the swamp", "go east", "east":
				crossRoad2(); break;
			}
			break;
		case "traderTalk":
			switch(command) {
			case "1": traderTrade(); break;
			case "2": talkAboutArea1(); break;
			case "3": crossRoad2(); break;
			}
			break;	
		case "traderTrade":
			switch(command) {
			case "1":		
				if(player.hasEnoughGold(300)==true&&greatSwordTaken==false) {
					traderTradeWeapon(); break;
				}
				break;
			case "2": 
				if(player.hasEnoughGold(140)==true&&potionsTakenTrader==false) {
					traderTradePotions(); break;
				}
				break;
			case "3": 
				if(pocketWatchTaken==true && bootsTaken==false) {
					traderTradeBoots(); break;
				}
				break;
			case "4": 
				if(ringTaken==true && chestplateTaken==false) {
					traderTradeChestplate(); break;
				}
				break;
			case "5": 
				if(amuletTaken==true && helmetTaken==false) {
					traderTradeHelmet(); break;
				}
				break;
			case "6": traderTalk(); break;
			}
			break;	
		case "talkAboutArea1":
			switch(command) {
			case "next": talkAboutArea2(); break;
			}
			break;
		case "talkAboutArea2":
			switch(command) {
			case "next": traderTalk(); break;
			}
			break;	
		case "outsideChurch":
			switch(command) {
			case "open door", "open the door", "door", "open", "enter church", "enter the church": 
				if(churchKeyTaken==true) {
					if(hunterDefeated==false) {
						enterChurch(); break;
					} else if(hunterDefeated==true) {
						reEnterChurchPostBattle();
					}		
				}
			break;
			case "back", "leave", "leave church", "leave the church", "go south-west", "go south west", "go southwest", "south-west", "south west", "southwest": 
				crossRoad2(); break;
			}
			break;
		case "enterChurch":
			switch(command) {
			case "next": engageCombat_Hunter(); break;
			}
			break;
		case "fight_Hunter":
			switch(command) {
			case "1": player.setDamage(playerDamage = new java.util.Random().nextInt(player.getWeapon().getAttack1DamageMax()-player.getWeapon().getAttack1DamageMin()+1)+player.getWeapon().getAttack1DamageMin()); 
			attackAccuracy = player.getWeapon().getAttack1Accuracy();
			monsterTakesDamage_Hunter(); break;
			case "2": player.setDamage(playerDamage = new java.util.Random().nextInt(player.getWeapon().getAttack2DamageMax()-player.getWeapon().getAttack2DamageMin()+1)+player.getWeapon().getAttack2DamageMin()); 
			attackAccuracy = player.getWeapon().getAttack2Accuracy();
			monsterTakesDamage_Hunter(); break;
			case "3": player.setDamage(playerDamage = new java.util.Random().nextInt(player.getWeapon().getAttack3DamageMax()-player.getWeapon().getAttack3DamageMin()+1)+player.getWeapon().getAttack3DamageMin());
			attackAccuracy = player.getWeapon().getAttack3Accuracy();
			monsterTakesDamage_Hunter(); break;
			case "4": 
				if (player.isMaxHealth()) {
				} else if(smallHealthPotion.hasConsumable() == true) {
					useSmallHealthPotion_Hunter(); break;
				}
				break;
			case "5": 
				if (player.isMaxHealth()) {
				} else if(largeHealthPotion.hasConsumable() == true) {
					useLargeHealthPotion_Hunter(); break;
				}
				break;
			}
			break;
		case "fight_Hunter_noSpellbook":
			switch(command) {
			case "1": player.setDamage(playerDamage = new java.util.Random().nextInt(player.getWeapon().getNoSpellbookAttackDamageMax()-player.getWeapon().getNoSpellbookAttackDamageMin()+1)+player.getWeapon().getNoSpellbookAttackDamageMin()); 
			attackAccuracy = player.getWeapon().getNoSpellbookattackAccuracy();
			monsterTakesDamage_Hunter(); break;	
			case "2": 
				if (player.isMaxHealth()) {
				} else if(smallHealthPotion.hasConsumable() == true) {
					useSmallHealthPotion_Hunter(); break;
				}
				break;
			case "3": 
				if (player.isMaxHealth()) {
				} else if(largeHealthPotion.hasConsumable() == true) {
					useLargeHealthPotion_Hunter(); break;
				}
				break;
			}	
			break;
		case "defeatedHunter":
			switch(command) {
			case "next": churchPostBattle(); break;
			}
			break;
		case "gameOver":
			switch(command) {
			case "restart": defaultSetup(); break;
			}
			break;	
		case "winGame":
			switch(command) {
			case "next": winGame2(); break;
			}
			break;
		case "winGame2":
			switch(command) {
			case "restart": defaultSetup(); break;
			case "quit": System.exit(0); break;
			}
			break;
		case "purificationGrounds":
			switch(command) {
			case "use cube": 
				if(purificationCubeTaken==true) {
					winGame(); break;
				} 
				break;
			case "leave", "leave shrine", "leave the shrine", "back": crossRoad2(); break;
			}
			break;		
			}
		}
	
	public void instructions() {
		player.setPosition("instructions");
		Game.getGameFrame().getInGamePanel().changeGameText("List of possible commands:"
				+ "\n\nNext - Continues dilogue or story text"
				+ "\nBack - Returns to previous diologue or story text"
				+ "\nLeave - Leave current area"
				+ "\nEnter - Enter a new area"
				+ "\nAttack - Attack an enemy"
				+ "\nInspect - Inspect objects for more information and options"
				+ "\nGo north/east/south/west - Directional navigation"
				+ "\nTalk - Interact with NPCs"
				+ "\nRest - Rest to recover health and switch weapons"
				+ "\nTake/Pick up - Take items and add them into your inventory"
				+ "\nThere may be more commands depending on the environment you can try"
				+ "\n\nType \"start\" to begin the game.");
	}
	
	public void wakeUp() {
		player.setPosition("wakeUp");
		playInGameSoundtrack();
		animateText("\"Old blood... the cure for it all...\" a voice croak in the darkness beyond."
				+ "\n\"You must find a way to end it all...\" the dark figure turns towards you and begins to make its way towards you with a needle in hand. "
				+ "\n\"The plague won't stop spreadying, you must severe it at its core!\""
				+ "\n\nYour vision begins to fade once again..."
				+ "\n\nType \"next\" to continue...");
	}
	
	public void wakeUp2() {
		player.setPosition("wakeUp2");
		animateText("You slowly open your eyes as you begin to regain consciousness, feeling a throbbing pain in your head. "
                + "\nThe room around you is shrouded in darkness, only illuminated by a few scattered flickering candles. "
                + "\nAs your eyes adjust you notice the straps binding you to an operating table are no longer there. "
                + "\nThe walls are lined with shelves containing jars filled with strange, unrecognizable specimens. "
                + "\nThe air is thick with a musty smell, and the sound of dripping water echoes off the stone walls. "
                + "\nIn the distance you also notice surgical equipment neatly organized on a back table with blood scattered all around. "
                + "\n\nYou slowly sit up, feeling a chill run down your spine as you realize you have no recollection of how you got here. ");
	}
	
	public void secretRoomInspectTable1() {
		player.setPosition("secretRoomInspectTable1");
		animateText("You approach a nearby table and notice a collection of neatly organized surgical tools, all carefully laid out with blood scattered all over. "
				+ "\nA ceremonial dagger with intricate engravings catches your eye, its edges glinting in the dim light. Amongst the tools and blood, you also notice several small, crimson red vials, each filled with a viscous liquid. "
				+ "\nThe sight of the health potions immediately brings a sense of relief to your mind, realizing they could be vital to your survival in the dark and treacherous world outside.");
	}
	
	public void secretRoomInspectTable2() {
		player.setPosition("secretRoomInspectTable2");
		animateText("You approach a nearby table and notice a collection of neatly organized surgical tools, all carefully laid out with blood scattered all over. "
				+ "A ceremonial dagger with intricate engravings catches your eye, its edges glinting in the dim light. ");
	}
	
	public void secretRoomInspectTable3() {
		player.setPosition("secretRoomInspectTable3");
		animateText("You approach a nearby table and notice a collection of neatly organized surgical tools, all carefully laid out with blood scattered all over. "
				+ "Amongst the tools and blood, you also notice several small, crimson red vials, each filled with a viscous liquid. "
				+ "The sight of the health potions immediately brings a sense of relief to your mind, realizing they could be vital to your survival in the dark and treacherous world outside.");
	}
	
	public void secretRoomInspectTableEmpty() {
		player.setPosition("secretRoomInspectTableEmpty");
		animateText("You approach a nearby table and notice a collection of neatly organized surgical tools, all carefully laid out with blood scattered all over. "
				+ "\nThere is nothing else on the table.");
	}

	public void secretRoomTakeDagger() {
		player.setPosition("secretRoomTakeDagger");
		animateText("You pick up the ceremonial dagger.");
	    secretRoomDaggerTaken=true;
	    new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                if(secretRoomDaggerTaken==false&&secretRoomPotionsTaken==false) {
						secretRoomInspectTable1(); 
					} else if(secretRoomDaggerTaken==true&&secretRoomPotionsTaken==true) {
						secretRoomInspectTableEmpty(); 
					} else if(secretRoomDaggerTaken==false&&secretRoomPotionsTaken==true) {
						secretRoomInspectTable2();  
					} else if(secretRoomDaggerTaken==true&&secretRoomPotionsTaken==false) {
						secretRoomInspectTable3(); 
					}
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void secretRoomTakePotions() {
		player.setPosition("secretRoomTakePotions");
		animateText("You pick up three small health potions and a small pouch of gold next to it.");
		player.addGold(85);
		Game.getGameFrame().getInGamePanel().changeGoldNum(player.getGold());
		secretRoomPotionsTaken=true;
		smallHealthPotion.addConsumableCount(3);
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                if(secretRoomDaggerTaken==false&&secretRoomPotionsTaken==false) {
						secretRoomInspectTable1(); 
					} else if(secretRoomDaggerTaken==true&&secretRoomPotionsTaken==true) {
						secretRoomInspectTableEmpty(); 
					} else if(secretRoomDaggerTaken==false&&secretRoomPotionsTaken==true) {
						secretRoomInspectTable2();  
					} else if(secretRoomDaggerTaken==true&&secretRoomPotionsTaken==false) {
						secretRoomInspectTable3(); 
					}
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void secretRoomLeave() {
		player.setPosition("secretRoomLeave");
		animateText("You leave the secret room and walk through a narrow, dimly lit corridor on the other side of the room. "
				+ "\nAs you approach the end of the corridor, you see a wooden door in front of you. "
				+ "\nYou push the door open and peer inside, your heart racing at the sight before you. "
				+ "\nOn the left side of the room a werewolf is visible, its fur matted with blood, hunched over a fresh corpse, tearing chunks of flesh from its bones with sharp, jagged teeth. "
				+ "\nThe stench of decay and death is overpowering, making you gag. "
				+ "\nYou know you must either get past the creature or fight it to continue your journey. "
				+ "\nYour eyes scan the room, and you spot several large crates stacked against the wall on the right. "
				+ "\nThey might provide just enough cover for you to sneak past the werewolf without alerting it to your presence. ");
	}
	
	//From secret room to library (first time)
	public void sneakByStorageRoomWerewolf() {
		player.setPosition("sneakByStorageRoomWerewolf");
		animateText("You move cautiously, your heart pounding as you slip silently past the oblivious werewolf, using the crates as cover. "
				+ "\nYour relief is palpable as you finally make it to the other side of the room and slip out through a door, closing it softly behind you. "
				+ "\nYou find yourself in a long, narrow hallway, the walls lined with flickering torches that barely illuminate the gloomy surroundings. "
				+ "\nYou continue down the hallway, listening to the sound of your own footsteps echoing eerily off the stone walls. "
				+ "\nAfter what seems like an eternity, you come to a stop in front of a massive door that seems to stretch up to the very ceiling itself. "
				+ "\nYou search for a handle, but find none, just a lever on the side that seems to be the only way to open the door.");
	}
	
	//From Library to secret room
	public void sneakByStorageRoomWerewolf2() {
		player.setPosition("sneakByStorageRoomWerewolf2");
		animateText("You move cautiously, your heart pounding as you slip silently past the oblivious werewolf, using the crates as cover. "
				+ "Your relief is palpable as you finally make it to the other side of the room and slip out through a door, closing it softly behind you. "
				+ "\n\nType “next” to continue.");
	}
	
	//From secret room to library
	public void sneakByStorageRoomWerewolf3() {
		player.setPosition("sneakByStorageRoomWerewolf3");
		animateText("You move cautiously, your heart pounding as you slip silently past the oblivious werewolf, using the crates as cover. "
				+ "Your relief is palpable as you finally make it to the other side of the room and slip out through a door, closing it softly behind you. "
				+ "\n\nType “next” to continue.");
	}
	
// STORAGE WEREWOLF
	public void engageCombat_StorageWerewolf() { 
		player.setPosition("engageCombat_StorageWerewolf");

		inGameMusicClip.close();
		playCombatSoundtrack();
		
		animateText("You engage combat with the Werewolf");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(3000);
	                if(librarySpellbookTaken==true) {
						fight_StorageWerewolf();
					} else if(librarySpellbookTaken==false) {
						fight_StorageWerewolf_noSpellbook();
					}
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void fight_StorageWerewolf() { 
		player.setPosition("fight_StorageWerewolf");
		animateText("You are in combat with the Werewolf."
				+ "\n\nWhat do you do?"
				+ "\n\n1. " + player.getWeapon().getAttack1Description() + " (" + player.getWeapon().getAttack1DamageMin() + "-" + player.getWeapon().getAttack1DamageMax() + " damage) (" + player.getWeapon().getAttack1Accuracy() + "% accuracy)"
				+ "\n2. " + player.getWeapon().getAttack2Description() + " (" + player.getWeapon().getAttack2DamageMin() + "-" + player.getWeapon().getAttack2DamageMax() + " damage) (" + player.getWeapon().getAttack2Accuracy() + "% accuracy)"
				+ "\n3. " + player.getWeapon().getAttack3Description() + " (" + player.getWeapon().getAttack3DamageMin() + "-" + player.getWeapon().getAttack3DamageMax() + " damage) (" + player.getWeapon().getAttack3Accuracy() + "% accuracy)"
				+ "\n4. Use " + smallHealthPotion.getName() + "to heal for " + smallHealthPotion.getHealthGain() + " (" + smallHealthPotion.getCount() +" remaining)"
				+ "\n5. Use " + largeHealthPotion.getName() + "to heal for " + largeHealthPotion.getHealthGain() + " (" + largeHealthPotion.getCount() +" remaining)");
	}
	
	public void fight_StorageWerewolf_noSpellbook() { 
		player.setPosition("fight_StorageWerewolf_noSpellbook");
		animateText("You are in combat with the Werewolf."
				+ "\n\nWhat do you do?"
				+ "\n\n1. " + player.getWeapon().getNoSpellbookAttackDescription() + " (" + player.getWeapon().getNoSpellbookAttackDamageMin() + "-" + player.getWeapon().getNoSpellbookAttackDamageMax() + " damage) (" + player.getWeapon().getNoSpellbookattackAccuracy() + "% accuracy)"
				+ "\n2. Use " + smallHealthPotion.getName() + "to heal for " + smallHealthPotion.getHealthGain() + " (" + smallHealthPotion.getCount() +" remaining)"
				+ "\n3. Use " + largeHealthPotion.getName() + "to heal for " + largeHealthPotion.getHealthGain() + " (" + largeHealthPotion.getCount() +" remaining)");
	}
	
	public void monsterTakesDamage_StorageWerewolf() {  
		player.setPosition("monsterTakesDamage_StorageWerewolf");

		int accuracy = new java.util.Random().nextInt(100)+1;
		
		if (accuracy<attackAccuracy) {
			werewolf_Storage.takeDamage(player.getDamage());
			
			if(werewolf_Storage.getHealth() < 0) {
				werewolf_Storage.setHealth(0);
			}
			
			animateText("You attack the Werewolf, it takes " + player.getDamage() + " damage." + "\n\nWerewolf health = " + werewolf_Storage.getHealth());
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                Thread.sleep(7000);
		                if(werewolf_Storage.isDead()==true) {
		                	storageRoomWerewolfDefeated=true;
		                	defeatedStorageWerewolf();
		                } else if(werewolf_Storage.isDead()==false) {
		                	playerTakesDamage_StorageWerewolf();
		                }
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
		} else if (accuracy>attackAccuracy) {
			animateText("The Werewolf dodges your attack! "
					+ "You missed!"
					+ "\n\nWerewolf health = " + werewolf_Storage.getHealth());
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                Thread.sleep(7000);
		                if(werewolf_Storage.isDead()==true) {
		                	storageRoomWerewolfDefeated=true;
		                	defeatedStorageWerewolf();
		                } else if(werewolf_Storage.isDead()==false) {
		                	playerTakesDamage_StorageWerewolf();
		                }
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
		}
	}
	
	public void playerTakesDamage_StorageWerewolf() {
		player.setPosition("playerTakesDamage_StorageWerewolf");
		
		int monsterDamage = new java.util.Random().nextInt(werewolf_Storage.getDamageMax() - werewolf_Storage.getDamageMin() + 1) + werewolf_Storage.getDamageMin();	
		int accuracy = new java.util.Random().nextInt(100)+1;
		
		if (accuracy<werewolf_Storage.getAttackAccuracy()) {
			animateText("The Werewolf strikes you with its sharp claws."
					+ "\n\nThe Werewolf deals " + monsterDamage + " damage to you.");
			player.takeDamage(monsterDamage);
			Game.getGameFrame().getInGamePanel().changeHealthNum(player.getHealth());
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                Thread.sleep(7000);
		                if(player.isDead()) {
		                	gameOver();
		                } else {
		                	if (librarySpellbookTaken==true) {
		                		fight_StorageWerewolf();
		                	} else if (librarySpellbookTaken==false) {
		                		fight_StorageWerewolf_noSpellbook();
		                	}	
		                }
		                
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
		} else if(accuracy>werewolf_Storage.getAttackAccuracy()) {
			animateText("The Werewolf attempts to strike you with its sharp claws."
					+ "\nYou dodge the Werewolf's attack!");
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                Thread.sleep(7000);
		                if(player.isDead()) {
		                	gameOver();
		                } else {
		                	if (librarySpellbookTaken==true) {
		                		fight_StorageWerewolf();
		                	} else if (librarySpellbookTaken==false) {
		                		fight_StorageWerewolf_noSpellbook();
		                	}
		                } 
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
		}
	}
	
	public void defeatedStorageWerewolf() {  
		player.setPosition("defeatedStorageWerewolf");
		animateText("You have slain the Werewolf!"
				+ "\n\nType \"next\" to continue");
	}
	
	public void storageRoomPostBattle() {  
		combatClip.close();
		playInGameSoundtrack();
		player.setPosition("storageRoomPostBattle");

		animateText("After defeating the Werewolf, you look around the room once again, the stale smell of dust and decay hits your nostrils. "
				+ "The Werewolf’s body lays motionless on the ground, its once menacing teeth and claws now seem dull and lifeless. "
				+ "Your attention is drawn to the corpse the werewolf was feasting on. "
				+ "You notice that the body is clad in tattered hunter's garb. "
				+ "You recognize the insignia on the clothing, the same as the one you wear. "
				+ "It appears that the corpse was once one of your fellow hunters, perhaps a failed experiment of the same sinister forces that created the werewolf.");
	}
	
	public void useSmallHealthPotion_StorageWerewolf() { 
		player.setPosition("useSmallHealthPotion_StorageWerewolf");
		
		player.heal(smallHealthPotion.getHealthGain());
		if(player.getHealth() > player.getMaxHealth()) {
			player.setHealth(player.getMaxHealth());
		}
		smallHealthPotion.useConsumable();
		Game.getGameFrame().getInGamePanel().changeHealthNum(player.getHealth());
		animateText("You used a " + smallHealthPotion.getName() + ", you heal " + smallHealthPotion.getHealthGain() + " health.");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                fight_StorageWerewolf();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void useLargeHealthPotion_StorageWerewolf() { 
		player.setPosition("useLargeHealthPotion_StorageWerewolf");
		
		player.heal(largeHealthPotion.getHealthGain());
		if(player.getHealth() > player.getMaxHealth()) {
			player.setHealth(player.getMaxHealth());
		}
		largeHealthPotion.useConsumable();
		Game.getGameFrame().getInGamePanel().changeHealthNum(player.getHealth());
		animateText("You used a " + largeHealthPotion.getName() + ", you heal " + largeHealthPotion.getHealthGain() + " health.");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                fight_StorageWerewolf();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	// LOUNGE GHOULS
	public void engageCombat_Ghouls() { 
		player.setPosition("engageCombat_Ghouls");
		
		inGameMusicClip.close();
		playCombatSoundtrack();
		
		animateText("You engage combat with the Ghouls");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(3000);
	                if(librarySpellbookTaken==true) {
	                	fight_Ghouls();
					} else if(librarySpellbookTaken==false) {
						fight_Ghouls_noSpellbook();
					}
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void fight_Ghouls() {
		player.setPosition("fight_Ghouls");
		animateText("You are in combat with the Ghouls."
				+ "\n\nWhat do you do?"
				+ "\n\n1. " + player.getWeapon().getAttack1Description() + " (" + player.getWeapon().getAttack1DamageMin() + "-" + player.getWeapon().getAttack1DamageMax() + " damage) (" + player.getWeapon().getAttack1Accuracy() + "% accuracy)"
				+ "\n2. " + player.getWeapon().getAttack2Description() + " (" + player.getWeapon().getAttack2DamageMin() + "-" + player.getWeapon().getAttack2DamageMax() + " damage) (" + player.getWeapon().getAttack2Accuracy() + "% accuracy)"
				+ "\n3. " + player.getWeapon().getAttack3Description() + " (" + player.getWeapon().getAttack3DamageMin() + "-" + player.getWeapon().getAttack3DamageMax() + " damage) (" + player.getWeapon().getAttack3Accuracy() + "% accuracy)"
				+ "\n4. Use " + smallHealthPotion.getName() + "to heal for " + smallHealthPotion.getHealthGain() + " (" + smallHealthPotion.getCount() +" remaining)"
				+ "\n5. Use " + largeHealthPotion.getName() + "to heal for " + largeHealthPotion.getHealthGain() + " (" + largeHealthPotion.getCount() +" remaining)");
	}
	
	public void fight_Ghouls_noSpellbook() { 
		player.setPosition("fight_Ghouls_noSpellbook");
		animateText("You are in combat with the Ghouls."
				+ "\n\nWhat do you do?"
				+ "\n\n1. " + player.getWeapon().getNoSpellbookAttackDescription() + " (" + player.getWeapon().getNoSpellbookAttackDamageMin() + "-" + player.getWeapon().getNoSpellbookAttackDamageMax() + " damage) (" + player.getWeapon().getNoSpellbookattackAccuracy() + "% accuracy)"
				+ "\n4. Use " + smallHealthPotion.getName() + "to heal for " + smallHealthPotion.getHealthGain() + " (" + smallHealthPotion.getCount() +" remaining)"
				+ "\n5. Use " + largeHealthPotion.getName() + "to heal for " + largeHealthPotion.getHealthGain() + " (" + largeHealthPotion.getCount() +" remaining)");
	}
	
	public void chooseTarget_Ghouls() {
		player.setPosition("chooseTarget_Ghouls");
		animateText("Which monster would you like to target?"
				+ "\n\nWhat do you do?"
				+ "\n\n1. First ghoul"
				+ "\n2. Second ghoul");
	}
	
	public void monsterTakesDamage_Ghoul1() {
		player.setPosition("monsterTakesDamage_Ghoul1");
		
		int accuracy = new java.util.Random().nextInt(100)+1;
		
		if (accuracy<attackAccuracy) {
			ghoul1_Lounge.takeDamage(player.getDamage());
			
			if(ghoul1_Lounge.getHealth() < 0) {
				ghoul1_Lounge.setHealth(0);
			}
			
			animateText("You attack the first Ghoul, it takes " + player.getDamage() + " damage" + "\n\nFirst Ghoul health = " + ghoul1_Lounge.getHealth());
			
			new Thread(new Runnable() {
			    public void run() {
			        try {
			            Thread.sleep(7000);
			            if(ghoul1_Lounge.isDead()==true && ghoul2_Lounge.isDead()==true) {
			            	ghoulsDefeated=true;
			            	defeatedGhouls();
			            } else if(ghoul1_Lounge.isDead()==true) {
			            	defeatedGhoul1();
			            } else if(ghoul1_Lounge.isDead()==false || ghoul2_Lounge.isDead()==false) {
			            	playerTakesDamage_Ghouls();
			            }
			        } catch (InterruptedException e) {
			            e.printStackTrace();
			        }
			    }
			}).start();
		} else if(accuracy>attackAccuracy) {
			
			animateText("The Ghoul dodges your attack! "
					+ "You missed!"
					+ "\n\nFirst Ghoul health = " + ghoul1_Lounge.getHealth());
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                Thread.sleep(7000);
		                if(ghoul1_Lounge.isDead()==true && ghoul2_Lounge.isDead()==true) {
		                	ghoulsDefeated=true;
		                	defeatedGhouls();
		                } else if(ghoul1_Lounge.isDead()==true) {
		                	defeatedGhoul1();
		                } else if(ghoul1_Lounge.isDead()==false || ghoul2_Lounge.isDead()==false) {
		                	playerTakesDamage_Ghouls();
		                }
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
		}
		
		
	}

	public void monsterTakesDamage_Ghoul2() {
		player.setPosition("monsterTakesDamage_Ghoul2");
		
		int accuracy = new java.util.Random().nextInt(100)+1;
		
		if (accuracy<attackAccuracy) {
			ghoul2_Lounge.takeDamage(player.getDamage());
			
			if(ghoul2_Lounge.getHealth() < 0) {
				ghoul2_Lounge.setHealth(0);
			}
			
			animateText("You attack the second Ghoul, it takes " + player.getDamage() + " damage" + "\n\nSecond Ghoul health = " + ghoul2_Lounge.getHealth());
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                Thread.sleep(7000);
		                if(ghoul1_Lounge.isDead()==true && ghoul2_Lounge.isDead()==true) {
		                	ghoulsDefeated=true;
		                	defeatedGhouls();
		                } else if(ghoul2_Lounge.isDead()==true) {
		                	defeatedGhoul2();
		                } else if(ghoul1_Lounge.isDead()==false || ghoul2_Lounge.isDead()==false) {
		                	playerTakesDamage_Ghouls();
		                }
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
		} else if(accuracy>attackAccuracy) {
			animateText("The Ghoul dodges your attack! "
					+ "You missed!"
					+ "\n\nSecond Ghoul health = " + ghoul2_Lounge.getHealth());
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                Thread.sleep(7000);
		                if(ghoul1_Lounge.isDead()==true && ghoul2_Lounge.isDead()==true) {
		                	ghoulsDefeated=true;
		                	defeatedGhouls();
		                } else if(ghoul2_Lounge.isDead()==true) {
		                	defeatedGhoul2();
		                } else if(ghoul1_Lounge.isDead()==false || ghoul2_Lounge.isDead()==false) {
		                	playerTakesDamage_Ghouls();
		                }
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
		}
	}
	
	public void playerTakesDamage_Ghouls() {
		player.setPosition("playerTakesDamage_Ghouls");
		
		int monsterDamage = new java.util.Random().nextInt(ghoul1_Lounge.getDamageMax() - ghoul1_Lounge.getDamageMin() + 1) + ghoul1_Lounge.getDamageMin();
		int monsterDamage2 = new java.util.Random().nextInt(ghoul2_Lounge.getDamageMax() - ghoul2_Lounge.getDamageMin() + 1) + ghoul2_Lounge.getDamageMin();
		int accuracy = new java.util.Random().nextInt(100)+1;
		
		if (accuracy<ghoul1_Lounge.getAttackAccuracy()) {
			if(ghoul1_Lounge.isDead()==false && ghoul2_Lounge.isDead()==false) {
				animateText("The first Ghoul and second Ghoul strike you."
						+ "\n\nThe first Ghoul deals " + monsterDamage + " damage to you"
						+ "\nThe second Ghoul deals" + monsterDamage2 + " damage to you");
				player.takeDamage(monsterDamage);
				player.takeDamage(monsterDamage2);	
			} else if(ghoul1_Lounge.isDead()==true) {
				animateText("The second Ghoul strikes you."
						+ "\n\nThe second ghoul deals " + monsterDamage2 + " damage to you");
				player.takeDamage(monsterDamage2);	
			} else if(ghoul2_Lounge.isDead()==true) {
				animateText("The first Ghoul strikes you."
						+ "The first Ghoul deals " + monsterDamage + " damage to you");
				player.takeDamage(monsterDamage);
			}
			
			Game.getGameFrame().getInGamePanel().changeHealthNum(player.getHealth());
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                Thread.sleep(7000);
		                if(player.isDead()) {
		                	gameOver();
		                } else {
		                	if (librarySpellbookTaken==true) {
		                		fight_Ghouls();
		                	} else if (librarySpellbookTaken==false) {
		                		fight_Ghouls_noSpellbook();
		                	}
		                }
		                
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
		} else if(accuracy>ghoul1_Lounge.getAttackAccuracy()) {
			animateText("The Ghoul monsters attempt to strike you."
					+ "\nYou dodge the Ghoul monster's attacks!");
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                Thread.sleep(7000);
		                if(player.isDead()) {
		                	gameOver();
		                } else {
		                	if (librarySpellbookTaken==true) {
		                		fight_Ghouls();
		                	} else if (librarySpellbookTaken==false) {
		                		fight_Ghouls_noSpellbook();
		                	}
		                } 
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
		}
	}
	
	public void defeatedGhoul1() {
		player.setPosition("defeatedGhoul1");

		animateText("You have slain the first Ghoul!");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                playerTakesDamage_Ghouls();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void defeatedGhoul2() {
		player.setPosition("defeatedGhoul2");
		
		animateText("You have slain the second Ghoul!");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                playerTakesDamage_Ghouls();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void defeatedGhouls() {
		player.setPosition("defeatedGhouls");
		
		animateText("You have slain both Ghouls!"
				+ "\n\nType \"next\" to continue");
	}
	
	public void loungePostBattle() {
		combatClip.close();
		playInGameSoundtrack();
		player.setPosition("loungePostBattle");
		
		animateText("After defeating the Ghouls, you look around the room once again. "
				+ "You notice the worn yet comfortable furniture with plush cushions and intricate patterns on the upholstery. "
				+ "The room is dimly lit by the flickering flames of the fireplace, casting swaying shadows throughout the room. "
				+ "The walls are adorned with ornate tapestries, depicting scenes of ancient battles and hunts. "
				+ "The corpses of the ghouls still lay on the floor where they were slain. "
				+ "The warmth emanating from the dwindling fire still burns bright.");
	}
	
	public void useSmallHealthPotion_Ghouls() { 
		player.setPosition("useSmallHealthPotion_Ghouls");

		player.heal(smallHealthPotion.getHealthGain());
		if(player.getHealth() > player.getMaxHealth()) {
			player.setHealth(player.getMaxHealth());
		}
		smallHealthPotion.useConsumable();
		Game.getGameFrame().getInGamePanel().changeHealthNum(player.getHealth());
		animateText("You used a " + smallHealthPotion.getName() + ", you heal " + smallHealthPotion.getHealthGain() + " health.");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                fight_Ghouls();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void useLargeHealthPotion_Ghouls() { 
		player.setPosition("useLargeHealthPotion_Ghouls");
		
		player.heal(largeHealthPotion.getHealthGain());
		if(player.getHealth() > player.getMaxHealth()) {
			player.setHealth(player.getMaxHealth());
		}
		largeHealthPotion.useConsumable();
		Game.getGameFrame().getInGamePanel().changeHealthNum(player.getHealth());
		animateText("You used a " + largeHealthPotion.getName() + ", you heal " + largeHealthPotion.getHealthGain() + " health.");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                fight_Ghouls();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	
	// DINNER SKELETON
	public void engageCombat_Skeleton() { 
		player.setPosition("engageCombat_Skeleton");		
		
		inGameMusicClip.close();
		playCombatSoundtrack();
		animateText("You engage combat with the Skeleton");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(3000);
	                if(librarySpellbookTaken==true) {
	                	fight_Skeleton();
					} else if(librarySpellbookTaken==false) {
						fight_Skeleton_noSpellbook();
					}
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void fight_Skeleton() {
		player.setPosition("fight_Skeleton");
		animateText("You are in combat with the Skeleton."
				+ "\n\nWhat do you do?"
				+ "\n\n1. " + player.getWeapon().getAttack1Description() + " (" + player.getWeapon().getAttack1DamageMin() + "-" + player.getWeapon().getAttack1DamageMax() + " damage) (" + player.getWeapon().getAttack1Accuracy() + "% accuracy)"
				+ "\n2. " + player.getWeapon().getAttack2Description() + " (" + player.getWeapon().getAttack2DamageMin() + "-" + player.getWeapon().getAttack2DamageMax() + " damage) (" + player.getWeapon().getAttack2Accuracy() + "% accuracy)"
				+ "\n3. " + player.getWeapon().getAttack3Description() + " (" + player.getWeapon().getAttack3DamageMin() + "-" + player.getWeapon().getAttack3DamageMax() + " damage) (" + player.getWeapon().getAttack3Accuracy() + "% accuracy)"
				+ "\n4. Use " + smallHealthPotion.getName() + "to heal for " + smallHealthPotion.getHealthGain() + " (" + smallHealthPotion.getCount() +" remaining)"
				+ "\n5. Use " + largeHealthPotion.getName() + "to heal for " + largeHealthPotion.getHealthGain() + " (" + largeHealthPotion.getCount() +" remaining)");
	}
	
	public void fight_Skeleton_noSpellbook() { 
		player.setPosition("fight_Skeleton_noSpellbook");
		animateText("You are in combat with the Skeleton."
				+ "\n\nWhat do you do?"
				+ "\n\n1. " + player.getWeapon().getNoSpellbookAttackDescription() + " (" + player.getWeapon().getNoSpellbookAttackDamageMin() + "-" + player.getWeapon().getNoSpellbookAttackDamageMax() + " damage) (" + player.getWeapon().getNoSpellbookattackAccuracy() + "% accuracy)"
				+ "\n4. Use " + smallHealthPotion.getName() + "to heal for " + smallHealthPotion.getHealthGain() + " (" + smallHealthPotion.getCount() +" remaining)"
				+ "\n5. Use " + largeHealthPotion.getName() + "to heal for " + largeHealthPotion.getHealthGain() + " (" + largeHealthPotion.getCount() +" remaining)");
	}
	
	public void monsterTakesDamage_Skeleton() {
		player.setPosition("monsterTakesDamage_Skeleton");
		
		int accuracy = new java.util.Random().nextInt(100)+1;
		
		if (accuracy<attackAccuracy) {
			skeleton_Dining.takeDamage(player.getDamage());
			
			if(skeleton_Dining.getHealth() < 0) {
				skeleton_Dining.setHealth(0);
			}
			
			animateText("You attack the Skeleton, it takes " + player.getDamage() + " damage." + "\n\nSkeleton health = " + skeleton_Dining.getHealth());
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                Thread.sleep(7000);
		                if(skeleton_Dining.isDead()==true) {
		                	skeletonDefeated=true;
		                	defeatedSkeleton();
		                } else if(skeleton_Dining.isDead()==false) {
		                	playerTakesDamage_Skeleton();
		                }
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
		} else if(accuracy>attackAccuracy) {
			animateText("The Skeleton dodges your attack! "
					+ "You missed!"
					+ "\n\nSkeleton health = " + skeleton_Dining.getHealth());
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                Thread.sleep(7000);
		                if(skeleton_Dining.isDead()==true) {
		                	skeletonDefeated=true;
		                	defeatedSkeleton();
		                } else if(skeleton_Dining.isDead()==false) {
		                	playerTakesDamage_Skeleton();
		                }
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
		}
	}

	public void playerTakesDamage_Skeleton() {
		player.setPosition("playerTakesDamage_Skeleton");
		
		int monsterDamage = new java.util.Random().nextInt(skeleton_Dining.getDamageMax() - skeleton_Dining.getDamageMin() + 1) + skeleton_Dining.getDamageMin();		
		int accuracy = new java.util.Random().nextInt(100)+1;
		
		if (accuracy<skeleton_Dining.getAttackAccuracy()) {
			animateText("The Skeleton strikes you with its royal caine."
					+ "\n\nThe Skeleton deals " + monsterDamage + " damage to you.");
			player.takeDamage(monsterDamage);
			Game.getGameFrame().getInGamePanel().changeHealthNum(player.getHealth());
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                Thread.sleep(4000);
		                if(player.isDead()) {
		                	gameOver();
		                } else {
		                	if (librarySpellbookTaken==true) {
		                		fight_Skeleton();
		                	} else if (librarySpellbookTaken==false) {
		                		fight_Skeleton_noSpellbook();
		                	}
		                }
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
		} else if(accuracy>skeleton_Dining.getAttackAccuracy()) {
			animateText("The Skeleton attempts to strike you with its royal caine."
					+ "\nYou dodge the Skeleton's attack!");
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                Thread.sleep(7000);
		                if(player.isDead()) {
		                	gameOver();
		                } else {
		                	if (librarySpellbookTaken==true) {
		                		fight_Skeleton();
		                	} else if (librarySpellbookTaken==false) {
		                		fight_Skeleton_noSpellbook();
		                	}
		                }
		                
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
		}
	}
	
	public void defeatedSkeleton() {
		player.setPosition("defeatedSkeleton");
		animateText("You have slain the Skeleton!"
				+ "\n\nType \"next\" to continue");
	}
	
	public void diningRoomPostBattle() { 
		combatClip.close();
		playInGameSoundtrack();
		player.setPosition("diningRoomPostBattle");
		
		animateText("After defeating the Skeleton, you look around the room once again, the musty scent of old food and stale air invades your nostrils. "
				+ "The first thing that strikes you is the ornate dining table that dominates the centre of the room. "
				+ "The large table is adorned with a pristine white tablecloth and crystal dinnerware, but years of neglect and dust have dulled their shine. "
				+ "The uniform chairs on each side are sturdy, but their once-beautiful fabric has faded to a dull grey. "
				+ "Your eyes are drawn to the end of the table, where a large, ornate chair sits. "
				+ "The chair is empty as you have already slain the skeleton. Its bones lie scattered on the floor, and the royal jewels and clothing it once wore are nowhere to be seen. "
				+ "On the side, you also see a door conjoining the dining room with the kitchen.");
	}
	
	public void useSmallHealthPotion_Skeleton() { 
		player.setPosition("useSmallHealthPotion_Skeleton");
		
		player.heal(smallHealthPotion.getHealthGain());
		if(player.getHealth() > player.getMaxHealth()) {
			player.setHealth(player.getMaxHealth());
		}
		smallHealthPotion.useConsumable();
		Game.getGameFrame().getInGamePanel().changeHealthNum(player.getHealth());
		animateText("You used a " + smallHealthPotion.getName() + ", you heal " + smallHealthPotion.getHealthGain() + " health.");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                fight_Skeleton();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}

	public void useLargeHealthPotion_Skeleton() { 
		player.setPosition("useLargeHealthPotion_Skeleton");
		
		player.heal(largeHealthPotion.getHealthGain());
		if(player.getHealth() > player.getMaxHealth()) {
			player.setHealth(player.getMaxHealth());
		}
		largeHealthPotion.useConsumable();
		Game.getGameFrame().getInGamePanel().changeHealthNum(player.getHealth());
		animateText("You used a " + largeHealthPotion.getName() + ", you heal " + largeHealthPotion.getHealthGain() + " health.");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                fight_Skeleton();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	// KITCHEN UNDEAD
	
	public void engageCombat_Undead() {
		player.setPosition("engageCombat_Undead");

		inGameMusicClip.close();
		playCombatSoundtrack();
		
		animateText("You engage combat with three Undead");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(3000);
	                if(librarySpellbookTaken==true) {
	                	fight_Undead();
					} else if(librarySpellbookTaken==false) {
						fight_Undead_noSpellbook();
					}
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void fight_Undead() {
		player.setPosition("fight_Undead");
		animateText("You are in combat with Undead monsters."
				+ "\n\nWhat do you do?"
				+ "\n\n1. " + player.getWeapon().getAttack1Description() + " (" + player.getWeapon().getAttack1DamageMin() + "-" + player.getWeapon().getAttack1DamageMax() + " damage) (" + player.getWeapon().getAttack1Accuracy() + "% accuracy)"
				+ "\n2. " + player.getWeapon().getAttack2Description() + " (" + player.getWeapon().getAttack2DamageMin() + "-" + player.getWeapon().getAttack2DamageMax() + " damage) (" + player.getWeapon().getAttack2Accuracy() + "% accuracy)"
				+ "\n3. " + player.getWeapon().getAttack3Description() + " (" + player.getWeapon().getAttack3DamageMin() + "-" + player.getWeapon().getAttack3DamageMax() + " damage) (" + player.getWeapon().getAttack3Accuracy() + "% accuracy)"
				+ "\n4. Use " + smallHealthPotion.getName() + " to heal for " + smallHealthPotion.getHealthGain() + " (" + smallHealthPotion.getCount() +" remaining)"
				+ "\n5. Use " + largeHealthPotion.getName() + " to heal for " + largeHealthPotion.getHealthGain() + " (" + largeHealthPotion.getCount() +" remaining)");
	}
	
	public void fight_Undead_noSpellbook() { 
		player.setPosition("fight_Undead_noSpellbook");
		animateText("You are in combat with Undead monsters."
				+ "\n\nWhat do you do?"
				+ "\n\n1. " + player.getWeapon().getNoSpellbookAttackDescription() + " (" + player.getWeapon().getNoSpellbookAttackDamageMin() + "-" + player.getWeapon().getNoSpellbookAttackDamageMax() + " damage) (" + player.getWeapon().getNoSpellbookattackAccuracy() + "% accuracy)"
				+ "\n4. Use " + smallHealthPotion.getName() + " to heal for " + smallHealthPotion.getHealthGain() + " (" + smallHealthPotion.getCount() +" remaining)"
				+ "\n5. Use " + largeHealthPotion.getName() + " to heal for " + largeHealthPotion.getHealthGain() + " (" + largeHealthPotion.getCount() +" remaining)");
	}

	public void chooseTarget_Undead1() {
		player.setPosition("chooseTarget_Undead1");
		animateText("Which monster would you like to target?"
				+ "\n\nWhat do you do?"
				+ "\n\n1. First Undead"
				+ "\n2. Second Undead");	
	}
	
	public void chooseTarget_Undead2() {
		player.setPosition("chooseTarget_Undead2");
		animateText("Which monster would you like to target?"
				+ "\n\nWhat do you do?"
				+ "\n\n1. First Undead"
				+ "\n2. Third Undead");
	}
	
	public void chooseTarget_Undead3() {
		player.setPosition("chooseTarget_Undead3");
		animateText("Which monster would you like to target?"
				+ "\n\nWhat do you do?"
				+ "\n\n1. Second Undead"
				+ "\n2. Third Undead");
	}
	
	public void chooseTarget_Undead4() {
		player.setPosition("chooseTarget_Undead4");
		animateText("Which monster would you like to target?"
				+ "\n\nWhat do you do?"
				+ "\n\n1. First Undead"
				+ "\n2. Second Undead"
				+ "\n3. Third Undead");
	}
	
	public void monsterTakesDamage_Undead1() {
		player.setPosition("monsterTakesDamage_Undead1");

		int accuracy = new java.util.Random().nextInt(100)+1;
		
		if (accuracy<attackAccuracy) {
			undead1_Kitchen.takeDamage(player.getDamage());
			
			if(undead1_Kitchen.getHealth() < 0) {
				undead1_Kitchen.setHealth(0);
			}
			
			animateText("You attack the first Undead, it takes " + player.getDamage() + " damage." + "\n\nFirst Undead health = " + undead1_Kitchen.getHealth());
			
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                Thread.sleep(7000);
		                if(undead1_Kitchen.isDead()==true && undead2_Kitchen.isDead()==true && undead3_Kitchen.isDead()==true) {
		                	undeadDefeated=true;
		                	defeatedUndead();
		                } else if(undead1_Kitchen.isDead()==true) {
		                	defeatedUndead1();
		                } else if(undead1_Kitchen.isDead()==false || undead2_Kitchen.isDead()==false || undead3_Kitchen.isDead()==false) {
		                	playerTakesDamage_Undead();
		                }
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
		} else if(accuracy>attackAccuracy) {
			animateText("The Undead monster dodges your attack! "
					+ "You missed!"
					+ "\n\nFirst Undead health = " + undead1_Kitchen.getHealth());
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                Thread.sleep(7000);
		                if(undead1_Kitchen.isDead()==true && undead2_Kitchen.isDead()==true && undead3_Kitchen.isDead()==true) {
		                	undeadDefeated=true;
		                	defeatedUndead();
		                } else if(undead1_Kitchen.isDead()==true) {
		                	defeatedUndead1();
		                } else if(undead1_Kitchen.isDead()==false || undead2_Kitchen.isDead()==false || undead3_Kitchen.isDead()==false) {
		                	playerTakesDamage_Undead();
		                }
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
		}
	}
	
	public void monsterTakesDamage_Undead2() {
		player.setPosition("monsterTakesDamage_Undead2");
		
		int accuracy = new java.util.Random().nextInt(100)+1;
		if (accuracy<attackAccuracy) {
			undead2_Kitchen.takeDamage(player.getDamage());
			
			if(undead2_Kitchen.getHealth() < 0) {
				undead2_Kitchen.setHealth(0);
			}
			
			animateText("You attack the second Undead, it takes " + player.getDamage() + " damage." + "\n\nSecond Undead health = " + undead2_Kitchen.getHealth());
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                Thread.sleep(7000);
		                if(undead1_Kitchen.isDead()==true && undead2_Kitchen.isDead()==true && undead3_Kitchen.isDead()==true) {
		                	undeadDefeated=true;
		                	defeatedUndead();
		                } else if(undead2_Kitchen.isDead()==true) {
		                	defeatedUndead2();
		                } else if(undead1_Kitchen.isDead()==false || undead2_Kitchen.isDead()==false || undead3_Kitchen.isDead()==false) {
		                	playerTakesDamage_Undead();
		                }
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
		} else if(accuracy>attackAccuracy) {
			animateText("The Undead monster dodges your attack! "
					+ "You missed!"
					+ "\n\nSecond Undead health = " + undead2_Kitchen.getHealth());
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                Thread.sleep(7000);
		                if(undead1_Kitchen.isDead()==true && undead2_Kitchen.isDead()==true && undead3_Kitchen.isDead()==true) {
		                	undeadDefeated=true;
		                	defeatedUndead();
		                } else if(undead2_Kitchen.isDead()==true) {
		                	defeatedUndead2();
		                } else if(undead1_Kitchen.isDead()==false || undead2_Kitchen.isDead()==false || undead3_Kitchen.isDead()==false) {
		                	playerTakesDamage_Undead();
		                }
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
		}
	}
	
	public void monsterTakesDamage_Undead3() {
		player.setPosition("monsterTakesDamage_Undead3");
		
		int accuracy = new java.util.Random().nextInt(100)+1;
		
		if (accuracy<attackAccuracy) {
			undead3_Kitchen.takeDamage(player.getDamage());
			
			if(undead3_Kitchen.getHealth() < 0) {
				undead3_Kitchen.setHealth(0);
			}
			
			animateText("You attack the third Undead, it takes " + player.getDamage() + " damage." + "\n\nThird Undead health = " + undead3_Kitchen.getHealth());
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                Thread.sleep(7000);
		                if(undead1_Kitchen.isDead()==true && undead2_Kitchen.isDead()==true && undead3_Kitchen.isDead()==true) {
		                	undeadDefeated=true;
		                	defeatedUndead();
		                } else if(undead3_Kitchen.isDead()==true) {
		                	defeatedUndead3();
		                } else if(undead1_Kitchen.isDead()==false || undead2_Kitchen.isDead()==false || undead3_Kitchen.isDead()==false) {
		                	playerTakesDamage_Undead();
		                }
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
		} else if(accuracy>attackAccuracy) {
			animateText("The Undead monster dodges your attack! "
					+ "You missed!"
					+ "\n\nThird Undead health = " + undead3_Kitchen.getHealth());
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                Thread.sleep(7000);
		                if(undead1_Kitchen.isDead()==true && undead2_Kitchen.isDead()==true && undead3_Kitchen.isDead()==true) {
		                	undeadDefeated=true;
		                	defeatedUndead();
		                } else if(undead3_Kitchen.isDead()==true) {
		                	defeatedUndead3();
		                } else if(undead1_Kitchen.isDead()==false || undead2_Kitchen.isDead()==false || undead3_Kitchen.isDead()==false) {
		                	playerTakesDamage_Undead();
		                }
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
		}
	}

	public void playerTakesDamage_Undead() {
		player.setPosition("playerTakesDamage_Undead");

		int monsterDamage = new java.util.Random().nextInt(undead1_Kitchen.getDamageMax() - undead1_Kitchen.getDamageMin() + 1) + undead1_Kitchen.getDamageMin();
		int monsterDamage2 = new java.util.Random().nextInt(undead2_Kitchen.getDamageMax() - undead2_Kitchen.getDamageMin() + 1) + undead2_Kitchen.getDamageMin();
		int monsterDamage3 = new java.util.Random().nextInt(undead3_Kitchen.getDamageMax() - undead3_Kitchen.getDamageMin() + 1) + undead3_Kitchen.getDamageMin();
		int accuracy = new java.util.Random().nextInt(100)+1;

		if (accuracy<undead1_Kitchen.getAttackAccuracy()) {
			if(undead1_Kitchen.isDead()==false && undead2_Kitchen.isDead()==false && undead3_Kitchen.isDead()==false) {
				animateText("The Undead monsters growl as they strike you."
						+ "\n\nThe first Undead deals " + monsterDamage + " damage to you"
						+ "\nThe second Undead deals " + monsterDamage2 + " damage to you"
						+ "\nThe third Undead deals " + monsterDamage3 + " damage to you");
				player.takeDamage(monsterDamage);
				player.takeDamage(monsterDamage2);	
				player.takeDamage(monsterDamage3);	
			} else if(undead1_Kitchen.isDead()==true && undead2_Kitchen.isDead()==false && undead3_Kitchen.isDead()==false) {
				animateText("The second undead deals " + monsterDamage2 + " damage to you"
						+ "\nThe third undead deals " + monsterDamage3 + " damage to you");
				player.takeDamage(monsterDamage2);	
				player.takeDamage(monsterDamage3);	
				
			} else if(undead1_Kitchen.isDead()==false && undead2_Kitchen.isDead()==true && undead3_Kitchen.isDead()==false) {
				animateText("The Undead monsters growl as they strike you."
						+ "\n\nThe first undead deals " + monsterDamage + " damage to you"
						+ "\nThe third undead deals " + monsterDamage3 + " damage to you");
				player.takeDamage(monsterDamage2);	
				player.takeDamage(monsterDamage3);	
				
			} else if(undead1_Kitchen.isDead()==false && undead2_Kitchen.isDead()==false && undead3_Kitchen.isDead()==true) {
				animateText("The Undead monsters growl as they strike you."
						+ "\n\nThe first undead deals " + monsterDamage + " damage to you"
						+ "\nThe second undead deals " + monsterDamage2 + " damage to you");
				player.takeDamage(monsterDamage);	
				player.takeDamage(monsterDamage2);	
				
			} else if(undead1_Kitchen.isDead()==true && undead2_Kitchen.isDead()==true && undead3_Kitchen.isDead()==false) {
				animateText("The Undead monster growls as it strikes you."
						+ "\n\nThe third undead deals " + monsterDamage3 + " damage to you");
				player.takeDamage(monsterDamage3);		
				
			} else if(undead1_Kitchen.isDead()==false && undead2_Kitchen.isDead()==true && undead3_Kitchen.isDead()==true) {
				animateText("The Undead monster growls as it strikes you."
						+ "\n\nThe first undead deals " + monsterDamage + " damage to you");	
				player.takeDamage(monsterDamage);
				
			} else if(undead1_Kitchen.isDead()==true && undead2_Kitchen.isDead()==false && undead3_Kitchen.isDead()==true) {
				animateText("The Undead monster growls as it strikes you."
						+ "\n\nThe second undead deals " + monsterDamage2 + " damage to you");	
				player.takeDamage(monsterDamage2);	
			}
			
			Game.getGameFrame().getInGamePanel().changeHealthNum(player.getHealth());
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                Thread.sleep(7000);
		                if(player.isDead()) {
		                	gameOver();
		                } else {
		                	if (librarySpellbookTaken==true) {
		                		fight_Undead();
		                	} else if (librarySpellbookTaken==false) {
		                		fight_Undead_noSpellbook();
		                	}
		                }
		                
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
		} else if(accuracy>undead1_Kitchen.getAttackAccuracy()) {
			animateText("The Undead monsters attempts to strike."
					+ "\nYou dodge the Undead monsters' attacks!");
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                Thread.sleep(7000);
		                if(player.isDead()) {
		                	gameOver();
		                } else {
		                	if (librarySpellbookTaken==true) {
		                		fight_Undead();
		                	} else if (librarySpellbookTaken==false) {
		                		fight_Undead_noSpellbook();
		                	}
		                }
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
		}
	}
	
	public void defeatedUndead1() {
		player.setPosition("defeatedUndead1");
		
		animateText("You have slain the first Undead!");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(5000);
	                playerTakesDamage_Undead();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void defeatedUndead2() {
		player.setPosition("defeatedUndead2");

		animateText("You have slain the second Undead!");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(5000);
	                playerTakesDamage_Undead();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void defeatedUndead3() {
		player.setPosition("defeatedUndead3");

		animateText("You have slain the third Undead!");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(5000);
	                playerTakesDamage_Undead();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	
	public void defeatedUndead() {
		player.setPosition("defeatedUndead");
		animateText("You have slain all the Undead!"
				+ "\n\nType \"next\" to continue");
	}
	
	public void kitchenPostBattle() { 
		combatClip.close();
		playInGameSoundtrack();
		player.setPosition("kitchenPostBattle");
		
		animateText("After defeating the Undead monsters, you look around the room once again, the smell of rotting food hits your nose. "
				+ "The tables in the centre and along the walls are cluttered with pots, pans, and utensils, some of them covered in a thick layer of grime. "
				+ "The room is silent except for the occasional creaking of the floorboards and the faint sound of dripping water. "
				+ "Since the undead creatures you previously encountered have been slain, the kitchen now seems abandoned and desolate.");
	}
	
	public void useSmallHealthPotion_Undead() { 
		player.setPosition("useSmallHealthPotion_Undead");

		player.heal(smallHealthPotion.getHealthGain());
		if(player.getHealth() > player.getMaxHealth()) {
			player.setHealth(player.getMaxHealth());
		}
		smallHealthPotion.useConsumable();
		Game.getGameFrame().getInGamePanel().changeHealthNum(player.getHealth());
		animateText("You used a " + smallHealthPotion.getName() + ", you heal " + smallHealthPotion.getHealthGain() + " health.");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                fight_Undead();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void useLargeHealthPotion_Undead() { 
		player.setPosition("useLargeHealthPotion_Undead");

		player.heal(largeHealthPotion.getHealthGain());
		if(player.getHealth() > player.getMaxHealth()) {
			player.setHealth(player.getMaxHealth());
		}
		largeHealthPotion.useConsumable();
		Game.getGameFrame().getInGamePanel().changeHealthNum(player.getHealth());
		animateText("You used a " + largeHealthPotion.getName() + ", you heal " + largeHealthPotion.getHealthGain() + " health.");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                fight_Undead();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	// BALLROOM WEREWOLF
	public void engageCombat_BallroomWerewolf() { 
		inGameMusicClip.close();
		playCombatSoundtrack();
		player.setPosition("engageCombat_BallroomWerewolf");

		
		animateText("You engage combat with the Werewolf");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(3000);
	                if(librarySpellbookTaken==true) {
	                	fight_BallroomWerewolf();
					} else if(librarySpellbookTaken==false) {
						fight_BallroomWerewolf_noSpellbook();
					}
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void fight_BallroomWerewolf() {		
		player.setPosition("fight_BallroomWerewolf");
		animateText("You are in combat with the Werewolf."
				+ "\n\nWhat do you do?"
				+ "\n\n1. " + player.getWeapon().getAttack1Description() + " (" + player.getWeapon().getAttack1DamageMin() + "-" + player.getWeapon().getAttack1DamageMax() + " damage) (" + player.getWeapon().getAttack1Accuracy() + "% accuracy)"
				+ "\n2. " + player.getWeapon().getAttack2Description() + " (" + player.getWeapon().getAttack2DamageMin() + "-" + player.getWeapon().getAttack2DamageMax() + " damage) (" + player.getWeapon().getAttack2Accuracy() + "% accuracy)"
				+ "\n3. " + player.getWeapon().getAttack3Description() + " (" + player.getWeapon().getAttack3DamageMin() + "-" + player.getWeapon().getAttack3DamageMax() + " damage) (" + player.getWeapon().getAttack3Accuracy() + "% accuracy)"
				+ "\n4. Use " + smallHealthPotion.getName() + "to heal for " + smallHealthPotion.getHealthGain() + " (" + smallHealthPotion.getCount() +" remaining)"
				+ "\n5. Use " + largeHealthPotion.getName() + "to heal for " + largeHealthPotion.getHealthGain() + " (" + largeHealthPotion.getCount() +" remaining)");
	}
	
	public void fight_BallroomWerewolf_noSpellbook() { 
		player.setPosition("fight_BallroomWerewolf_noSpellbook");
		animateText("You are in combat with the Werewolf."
				+ "\n\nWhat do you do?"
				+ "\n\n1. " + player.getWeapon().getNoSpellbookAttackDescription() + " (" + player.getWeapon().getNoSpellbookAttackDamageMin() + "-" + player.getWeapon().getNoSpellbookAttackDamageMax() + " damage) (" + player.getWeapon().getNoSpellbookattackAccuracy() + "% accuracy)"
				+ "\n4. Use " + smallHealthPotion.getName() + "to heal for " + smallHealthPotion.getHealthGain() + " (" + smallHealthPotion.getCount() +" remaining)"
				+ "\n5. Use " + largeHealthPotion.getName() + "to heal for " + largeHealthPotion.getHealthGain() + " (" + largeHealthPotion.getCount() +" remaining)");
	}
	
	public void monsterTakesDamage_BallroomWerewolf() {
		player.setPosition("monsterTakesDamage_BallroomWerewolf");
		int accuracy = new java.util.Random().nextInt(100)+1;
		
		if (accuracy<attackAccuracy) {
			werewolf_Ballroom.takeDamage(player.getDamage());
			
			if(werewolf_Ballroom.getHealth() < 0) {
				werewolf_Ballroom.setHealth(0);
			}
			
			animateText("You attack the Werewolf, it takes " + player.getDamage() + " damage." + "\n\nWerewolf health = " + werewolf_Ballroom.getHealth());
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                Thread.sleep(7000);
		                if(werewolf_Ballroom.isDead()==true) {
		                	ballroomWerewolfDefeated=true;
		                	defeatedBallroomWerewolf();
		                } else if(werewolf_Ballroom.isDead()==false) {
		                	playerTakesDamage_BallroomWerewolf();
		                }
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
		} else if(accuracy>attackAccuracy) {
			animateText("The Werewolf dodges your attack! "
					+ "You missed!"
					+ "\n\nWerewolf health = " + werewolf_Ballroom.getHealth());
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                Thread.sleep(7000);
		                if(werewolf_Ballroom.isDead()==true) {
		                	ballroomWerewolfDefeated=true;
		                	defeatedBallroomWerewolf();
		                } else if(werewolf_Ballroom.isDead()==false) {
		                	playerTakesDamage_BallroomWerewolf();
		                }
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
		}	
	}

	public void playerTakesDamage_BallroomWerewolf() {
		player.setPosition("playerTakesDamage_BallroomWerewolf");

		int monsterDamage = new java.util.Random().nextInt(werewolf_Ballroom.getDamageMax() - werewolf_Ballroom.getDamageMin() + 1) + werewolf_Ballroom.getDamageMin();
		int accuracy = new java.util.Random().nextInt(100)+1;

		if (accuracy<werewolf_Ballroom.getAttackAccuracy()) {
			animateText("The Werewolf strikes you with its sharp claws."
					+ "\n\nThe Werewolf deals " + monsterDamage + " damage to you.");
			player.takeDamage(monsterDamage);
			Game.getGameFrame().getInGamePanel().changeHealthNum(player.getHealth());
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                Thread.sleep(7000);
		                if(player.isDead()) {
		                	gameOver();
		                } else {
		                	if (librarySpellbookTaken==true) {
		                		fight_BallroomWerewolf();
		                	} else if (librarySpellbookTaken==false) {
		                		fight_BallroomWerewolf_noSpellbook();
		                	}
		                }
		                
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
		} else if(accuracy>werewolf_Ballroom.getAttackAccuracy()) {
			animateText("The Werewolf attempts to strike you with its sharp claws."
					+ "\nYou dodge the Werewolf's attack!");
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                Thread.sleep(7000);
		                if(player.isDead()) {
		                	gameOver();
		                } else {
		                	if (librarySpellbookTaken==true) {
		                		fight_BallroomWerewolf();
		                	} else if (librarySpellbookTaken==false) {
		                		fight_BallroomWerewolf_noSpellbook();
		                	}
		                }
		                
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
		}
	}
	
	public void defeatedBallroomWerewolf() {
		player.setPosition("defeatedBallroomWerewolf");
		animateText("You have slain the Werewolf!"
				+ "\n\nType \"next\" to continue");
	}
	
	public void ballroomPostBattle() { 
		combatClip.close();
		playInGameSoundtrack();
		player.setPosition("ballroomPostBattle");
		
		animateText("After defeating the Werewolf, you look around the room once again. "
				+ "You notice the impressive upper viewing floor and the elegant decor. "
				+ "The grandeur of the ballroom takes your breath away, crystal chandeliers hang from the vaulted ceiling, casting a soft glow over the room. "
				+ "The werewolf’s dead corpse still lies in the middle of the dancefloor with a chest standing next to it.");
	}
	
	public void useSmallHealthPotion_BallroomWerewolf() { 
		player.setPosition("useSmallHealthPotion_BallroomWerewolf");

		player.heal(smallHealthPotion.getHealthGain());
		if(player.getHealth() > player.getMaxHealth()) {
			player.setHealth(player.getMaxHealth());
		}
		smallHealthPotion.useConsumable();
		Game.getGameFrame().getInGamePanel().changeHealthNum(player.getHealth());
		animateText("You used a " + smallHealthPotion.getName() + ", you heal " + smallHealthPotion.getHealthGain() + " health.");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                fight_BallroomWerewolf();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void useLargeHealthPotion_BallroomWerewolf() { 
		player.setPosition("useLargeHealthPotion_BallroomWerewolf");

		player.heal(largeHealthPotion.getHealthGain());
		if(player.getHealth() > player.getMaxHealth()) {
			player.setHealth(player.getMaxHealth());
		}
		largeHealthPotion.useConsumable();
		Game.getGameFrame().getInGamePanel().changeHealthNum(player.getHealth());
		animateText("You used a " + largeHealthPotion.getName() + ", you heal " + largeHealthPotion.getHealthGain() + " health.");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                fight_BallroomWerewolf();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	// TOILET GOBLIN
	public void engageCombat_Goblin() { 
		player.setPosition("engageCombat_Goblin");
		inGameMusicClip.close();
		playCombatSoundtrack();
		
		animateText("You engage combat with the Goblin");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(3000);
	                if(librarySpellbookTaken==true) {
	                	fight_Goblin();
					} else if(librarySpellbookTaken==false) {
						fight_Goblin_noSpellbook();
					}
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void fight_Goblin() {
		player.setPosition("fight_Goblin");
		animateText("You are in combat with the Goblin."
				+ "\n\nWhat do you do?"
				+ "\n\n1. " + player.getWeapon().getAttack1Description() + " (" + player.getWeapon().getAttack1DamageMin() + "-" + player.getWeapon().getAttack1DamageMax() + " damage) + (100% accuracy)"
				+ "\n2. " + player.getWeapon().getAttack2Description() + " (" + player.getWeapon().getAttack2DamageMin() + "-" + player.getWeapon().getAttack2DamageMax() + " damage) + (100% accuracy)"
				+ "\n3. " + player.getWeapon().getAttack3Description() + " (" + player.getWeapon().getAttack3DamageMin() + "-" + player.getWeapon().getAttack3DamageMax() + " damage) + (100% accuracy)"
				+ "\n4. Use " + smallHealthPotion.getName() + "to heal for " + smallHealthPotion.getHealthGain() + " (" + smallHealthPotion.getCount() +" remaining)"
				+ "\n5. Use " + largeHealthPotion.getName() + "to heal for " + largeHealthPotion.getHealthGain() + " (" + largeHealthPotion.getCount() +" remaining)");
	}
	
	public void fight_Goblin_noSpellbook() {
		player.setPosition("fight_Goblin_noSpellbook");
		animateText("You are in combat with the Goblin."
				+ "\n\nWhat do you do?"
				+ "\n\n1. " + player.getWeapon().getNoSpellbookAttackDescription() + " (" + player.getWeapon().getNoSpellbookAttackDamageMin() + "-" + player.getWeapon().getNoSpellbookAttackDamageMax() + " damage) (" + player.getWeapon().getNoSpellbookattackAccuracy() + "% accuracy)"
				+ "\n4. Use " + smallHealthPotion.getName() + "to heal for " + smallHealthPotion.getHealthGain() + " (" + smallHealthPotion.getCount() +" remaining)"
				+ "\n5. Use " + largeHealthPotion.getName() + "to heal for " + largeHealthPotion.getHealthGain() + " (" + largeHealthPotion.getCount() +" remaining)");
	}
	
	public void monsterTakesDamage_Goblin() {
		player.setPosition("monsterTakesDamage_Goblin");

		goblin_Bathroom.takeDamage(player.getDamage());
		
		if(goblin_Bathroom.getHealth() < 0) {
			goblin_Bathroom.setHealth(0);
		}
		
		animateText("You attack the Goblin, it takes " + player.getDamage() + " damage." + "\n\nGoblin health = " + goblin_Bathroom.getHealth());
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(7000);
	                if(goblin_Bathroom.isDead()==true) {
	                	goblinDefeated=true;
	                	defeatedGoblin();
	                } else if(goblin_Bathroom.isDead()==false) {
	                	playerTakesDamage_Goblin();
	                }
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void playerTakesDamage_Goblin() {
		player.setPosition("playerTakesDamage_Goblin");

		int monsterDamage = new java.util.Random().nextInt(goblin_Bathroom.getDamageMax() - goblin_Bathroom.getDamageMin() + 1) + goblin_Bathroom.getDamageMin();

		animateText("The Goblin attempts to swing at you from the toilet."
				+ "\n\nThe Goblin deals " + monsterDamage + " damage to you.");
		player.takeDamage(monsterDamage);
		Game.getGameFrame().getInGamePanel().changeHealthNum(player.getHealth());
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(7000);
	                if(player.isDead()) {
	                	gameOver();
	                } else {
	                	if (librarySpellbookTaken==true) {
	                		fight_Goblin();
	                	} else if (librarySpellbookTaken==false) {
	                		fight_Goblin_noSpellbook();
	                	}
	                }	             
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void defeatedGoblin() {
		player.setPosition("defeatedGoblin");
		animateText("You have slain the Goblin!"
				+ "\n\nType \"next\" to continue");
	}

	public void bathroomPostBattle() { 
		combatClip.close();
		playInGameSoundtrack();
		player.setPosition("bathroomPostBattle");
		
		animateText("After defeating the Goblin, you look around the room once again, the foul stench of filth overwhelms your senses. "
				+ "The walls are coated in grime and the tiles on the floor are cracked and dirty. "
				+ "The stench of the goblin's filth has been replaced by the unmistakable odour of death. "
				+ "You see the lifeless body of the goblin slumped against the wall, a pool of blood spreading out beneath it. "
				+ "You notice the goblin still has a grasp on a small rusty key.");
	}
	
	public void useSmallHealthPotion_Goblin() { 
		player.setPosition("useSmallHealthPotion_Goblin");

		player.heal(smallHealthPotion.getHealthGain());
		if(player.getHealth() > player.getMaxHealth()) {
			player.setHealth(player.getMaxHealth());
		}
		smallHealthPotion.useConsumable();
		Game.getGameFrame().getInGamePanel().changeHealthNum(player.getHealth());
		animateText("You used a " + smallHealthPotion.getName() + ", you heal " + smallHealthPotion.getHealthGain() + " health.");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                fight_Goblin();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void useLargeHealthPotion_Goblin() { 
		player.setPosition("useLargeHealthPotion_Goblin");

		player.heal(largeHealthPotion.getHealthGain());
		if(player.getHealth() > player.getMaxHealth()) {
			player.setHealth(player.getMaxHealth());
		}
		largeHealthPotion.useConsumable();
		Game.getGameFrame().getInGamePanel().changeHealthNum(player.getHealth());
		animateText("You used a " + largeHealthPotion.getName() + ", you heal " + largeHealthPotion.getHealthGain() + " health.");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                fight_Goblin();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	// CHURCH CORRUPTED HUNTER
	public void engageCombat_Hunter() { 
		player.setPosition("engageCombat_Hunter");
		inGameMusicClip.close();
		playFinalBossSoundtrack();
		
		animateText("You engage combat with the Corrupted Hunter");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                if(librarySpellbookTaken==true) {
	                	fight_Hunter();
					} else if(librarySpellbookTaken==false) {
						fight_Hunter_noSpellbook();
					}
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void fight_Hunter() {
		player.setPosition("fight_Hunter");
		animateText("You are in combat with the Corrupted Hunter."
				+ "\n\nWhat do you do?"
				+ "\n\n1. " + player.getWeapon().getAttack1Description() + " (" + player.getWeapon().getAttack1DamageMin() + "-" + player.getWeapon().getAttack1DamageMax() + " damage) (" + player.getWeapon().getAttack1Accuracy() + "% accuracy)"
				+ "\n2. " + player.getWeapon().getAttack2Description() + " (" + player.getWeapon().getAttack2DamageMin() + "-" + player.getWeapon().getAttack2DamageMax() + " damage) (" + player.getWeapon().getAttack2Accuracy() + "% accuracy)"
				+ "\n3. " + player.getWeapon().getAttack3Description() + " (" + player.getWeapon().getAttack3DamageMin() + "-" + player.getWeapon().getAttack3DamageMax() + " damage) (" + player.getWeapon().getAttack3Accuracy() + "% accuracy)"
				+ "\n4. Use " + smallHealthPotion.getName() + "to heal for " + smallHealthPotion.getHealthGain() + " (" + smallHealthPotion.getCount() +" remaining)"
				+ "\n5. Use " + largeHealthPotion.getName() + "to heal for " + largeHealthPotion.getHealthGain() + " (" + largeHealthPotion.getCount() +" remaining)");	
	}
	
	public void fight_Hunter_noSpellbook() { 
		player.setPosition("fight_Hunter_noSpellbook");
		animateText("You are in combat with the Corrupted Hunter."
				+ "\n\nWhat do you do?"
				+ "\n\n1. " + player.getWeapon().getNoSpellbookAttackDescription() + " (" + player.getWeapon().getNoSpellbookAttackDamageMin() + "-" + player.getWeapon().getNoSpellbookAttackDamageMax() + " damage) (" + player.getWeapon().getNoSpellbookattackAccuracy() + "% accuracy)"
				+ "\n4. Use " + smallHealthPotion.getName() + "to heal for " + smallHealthPotion.getHealthGain() + " (" + smallHealthPotion.getCount() +" remaining)"
				+ "\n5. Use " + largeHealthPotion.getName() + "to heal for " + largeHealthPotion.getHealthGain() + " (" + largeHealthPotion.getCount() +" remaining)");
	}
	
	public void monsterTakesDamage_Hunter() {
		player.setPosition("monsterTakesDamage_Hunter");
		int accuracy = new java.util.Random().nextInt(100)+1;
		
		if (accuracy<attackAccuracy) {
			hunter_Church.takeDamage(player.getDamage());
			
			if(hunter_Church.getHealth() < 0) {
				hunter_Church.setHealth(0);
			}
			
			animateText("You attack the hunter, it takes " + player.getDamage() + " damage." + "\n\nHunter health = " + hunter_Church.getHealth());
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                Thread.sleep(7000);
		                if(hunter_Church.isDead()==true) {
		                	hunterDefeated=true;
		                	defeatedHunter();
		                } else if(hunter_Church.isDead()==false) {
		                	playerTakesDamage_Hunter();
		                }
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
		} else if(accuracy>attackAccuracy) {
			animateText("The Hunter dodges your attack! "
					+ "You missed!"
					+ "\n\nHunter health = " + hunter_Church.getHealth());
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                Thread.sleep(7000);
		                if(hunter_Church.isDead()==true) {
		                	hunterDefeated=true;
		                	defeatedHunter();
		                } else if(hunter_Church.isDead()==false) {
		                	playerTakesDamage_Hunter();
		                }
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
		}	
	}
	
	public void playerTakesDamage_Hunter() {
		player.setPosition("playerTakesDamage_Hunter");

		int monsterDamage = new java.util.Random().nextInt(hunter_Church.getDamageMax() - hunter_Church.getDamageMin() + 1) + hunter_Church.getDamageMin();
		int accuracy = new java.util.Random().nextInt(100)+1;

		if (accuracy<hunter_Church.getAttackAccuracy()) {
			animateText("The Corrupted Hunter strikes you with a greatsword."
					+ "\n\nThe Hunter deals " + monsterDamage + " damage to you.");
			player.takeDamage(monsterDamage);
			Game.getGameFrame().getInGamePanel().changeHealthNum(player.getHealth());
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                Thread.sleep(7000);
		                if(player.isDead()) {
		                	gameOver();
		                } else {
		                	if (librarySpellbookTaken==true) {
		                		fight_Hunter();
		                	} else if (librarySpellbookTaken==false) {
		                		fight_Hunter_noSpellbook();
		                	}
		                }
		                
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
		} else if(accuracy>hunter_Church.getAttackAccuracy()) {
			animateText("The Hunter attempts to strike you with its great sword."
					+ "\nYou dodge the Hunter's attack!");
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                Thread.sleep(7000);
		                if(player.isDead()) {
		                	gameOver();
		                } else {
		                	if (librarySpellbookTaken==true) {
		                		fight_Hunter();
		                	} else if (librarySpellbookTaken==false) {
		                		fight_Hunter_noSpellbook();
		                	}
		                }
		                
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
		}	
	}

	public void defeatedHunter() {
		player.setPosition("defeatedHunter");
		animateText("You have slain the Corrupted Hunter!"
				+ "\n\nType \"next\" to continue");
	}
	
	public void useSmallHealthPotion_Hunter() { 
		player.setPosition("useSmallHealthPotion_Hunter");

		player.heal(smallHealthPotion.getHealthGain());
		if(player.getHealth() > player.getMaxHealth()) {
			player.setHealth(player.getMaxHealth());
		}
		smallHealthPotion.useConsumable();
		Game.getGameFrame().getInGamePanel().changeHealthNum(player.getHealth());
		animateText("You used a " + smallHealthPotion.getName() + ", you heal " + smallHealthPotion.getHealthGain() + " health.");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                fight_Hunter();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void useLargeHealthPotion_Hunter() { 
		player.setPosition("useLargeHealthPotion_Hunter");

		player.heal(largeHealthPotion.getHealthGain());
		if(player.getHealth() > player.getMaxHealth()) {
			player.setHealth(player.getMaxHealth());
		}
		largeHealthPotion.useConsumable();
		Game.getGameFrame().getInGamePanel().changeHealthNum(player.getHealth());
		animateText("You used a " + largeHealthPotion.getName() + ", you heal " + largeHealthPotion.getHealthGain() + " health.");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                fight_Hunter();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	
	public void leaveStorageRoom() {
		player.setPosition("leaveStorageRoom");
		animateText("You leave the storage room through a door and find yourself in a long, narrow hallway, the walls lined with flickering torches that barely illuminate the gloomy surroundings. "
				+ "You continue down the hallway, listening to the sound of your own footsteps echoing eerily off the stone walls. "
				+ "After what seems like an eternity, you come to a stop in front of a massive door that seems to stretch up to the very ceiling itself. "
				+ "You search for a handle, but find none, just a lever on the side that seems to be the only way to open the door.");
	}
	
	public void pullLeaver() {
		player.setPosition("pullLeaver");
		animateText("As you pull the lever, you hear a low rumble as the bookshelves slowly begin to shift and move, revealing a pathway that was previously hidden. "
				+ "The door swings open, revealing a vast library on the other side. "
				+ "You realize that you must have awoken within a secret, hidden room. "
				+ "\n\nType \"next\" to continue...");
	}
	
	public void enterLibrary() {
		reEnteringSecretRoom=true;
		player.setPosition("enterLibrary");
		animateText("You step into a grand library, with towering shelves stretching up to the high ceilings, filled with countless books and manuscripts. "
				+ "\nThe musty scent of old paper and leather fills the air, and the flickering light of candles casts eerie shadows across the room. "
				+ "\nYou can't help but feel a sense of awe and wonder at the knowledge that surrounds you. "
				+ "\nThe room is illuminated by the soft glow of flickering candles, casting eerie shadows on the walls. "
				+ "\nYour attention is drawn to a large, ornate table in the centre of the room, adorned with a unique book and a few crimson potions. ");
	}
	
	public void inspectLibraryBookshelf() {
		player.setPosition("inspectLibraryBookshelf");
		animateText("As you run your fingers along the spines of the books on the shelf, you realize that most of them are written in a language you don't understand. "
				+ "The letters are strange and twisted, and the words seem to writhe on the page. "
				+ "Despite your best efforts, you cannot make sense of them. "
				+ "\n\nHowever, you do manage to find a few books that are written in a language you recognize. "
				+ "They seem to be about ancient rituals and forbidden knowledge, with titles like \"The Rites of Blood\" and \"The Forbidden Tome of Arcane Lore.\" "
				+ "You can't help but feel a sense of unease as you handle these tomes, knowing that they contain dangerous secrets best left forgotten. "
				+ "The contents don’t seem to be of any help to you."
				+ "\n\nType \"back\" to continue...");
	}
	
	public void inspectLibraryTable1() {
		player.setPosition("inspectLibraryTable1");
		animateText("As you approach the table, you notice that there are numerous books and scrolls scattered haphazardly across it, their spines facing upwards. "
				+ "\nThe table looks like it hasn't been cleared in a long time. "
				+ "\nHowever, one particular book catches your eye. It has a unique binding, made of dark leather with a symbol etched on its cover in gold. "
				+ "\nBeside the book, you notice a few crimson potions, their small vials glinting in the dim light of the library. "
				+ "\nThey seem to possibly have healing properties. "
				+ "\nAs you examine the book, you realize that it's a spellbook, containing various incantations and magical abilities that you've never seen before. "
				+ "\nThis could be the key to unlocking new powers and defeating the enemies that lie ahead.");
	}
	
	public void inspectLibraryTable2() {
		player.setPosition("inspectLibraryTable2");
		animateText("As you approach the table, you notice that there are numerous books and scrolls scattered haphazardly across it, their spines facing upwards. "
				+ "The table looks like it hasn't been cleared in a long time. "
				+ "In the middle of the books, you notice a few crimson potions, their small vials glinting in the dim light of the library. "
				+ "They seem to possibly have healing properties. ");
	}
	
	public void inspectLibraryTable3() {
		player.setPosition("inspectLibraryTable3");
		animateText("As you approach the table, you notice that there are numerous books and scrolls scattered haphazardly across it, their spines facing upwards. "
				+ "The table looks like it hasn't been cleared in a long time. However, one particular book catches your eye. "
				+ "It has a unique binding, made of dark leather with a symbol etched on its cover in gold. "
				+ "As you examine the book, you realize that it's a spellbook, containing various incantations and magical abilities that you've never seen before. "
				+ "This could be the key to unlocking new powers and defeating the enemies that lie ahead.");
	}
	
	public void inspectLibraryTableEmpty() {
		player.setPosition("inspectLibraryTableEmpty");
		animateText("As you approach the table, you notice that there are numerous books and scrolls scattered haphazardly across it, their spines facing upwards. "
				+ "The table looks like it hasn't been cleared in a long time. "
				+ "There doesn’t seem to be anything of use here.");
	}
	
	public void libraryTakePotions() {
		player.setPosition("libraryTakePotions");

		animateText("You pick up two large health potions and a large pouch of gold.");
		libraryPotionsTaken = true;
		player.addGold(105);
		Game.getGameFrame().getInGamePanel().changeGoldNum(player.getGold());
		largeHealthPotion.addConsumableCount(2);
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                if(librarySpellbookTaken==false&&libraryPotionsTaken==true) {
	                	inspectLibraryTable3();
					} else if(librarySpellbookTaken==true&&libraryPotionsTaken==true) {
						inspectLibraryTableEmpty();
					}
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void libraryTakeSpellbook() {
		player.setPosition("libraryTakeSpellbook");

		animateText("You pick up the spellbook. You can now cast spells within combat.");
		librarySpellbookTaken = true;
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                if(librarySpellbookTaken==true&&libraryPotionsTaken==false) {
	                	inspectLibraryTable2();
					} else if(librarySpellbookTaken==true&&libraryPotionsTaken==true) {
						inspectLibraryTableEmpty();
					}
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void reEnterStorageRoom1() {
		player.setPosition("reEnterStorageRoom1");
		animateText("You leave the library through a narrow hallway, the walls lined with flickering torches. "
				+ "As you approach the end of the corridor, you see a wooden door in front of you. "
				+ "You push the door open and peer inside, your heart begins to race once again at the sight before you. "
				+ "On the right side of the room the werewolf is still viciously feasting on a corpse, tearing chunks of flesh from its bones with sharp, jagged teeth. "
				+ "On the left side of the room there are several large crates stacked against the wall. "
				+ "They might provide just enough cover for you to sneak past the werewolf without alerting it to your presence.");
	}
	
	public void reEnterStorageRoom2() {
		player.setPosition("reEnterStorageRoom2");
		animateText("You leave the secret room through a narrow, dimly lit corridor on the other side of the room. "
				+ "As you approach the end of the corridor, you see a wooden door in front of you. "
				+ "You push the door open and peer inside, your heart begins to race once again at the sight before you. "
				+ "On the left side of the room the werewolf is still viciously feasting on a corpse, tearing chunks of flesh from its bones with sharp, jagged teeth. "
				+ "On the right side of the room there are several large crates stacked against the wall. "
				+ "They might provide just enough cover for you to sneak past the werewolf without alerting it to your presence.");
	}
	
	public void reEnterStorageRoomPostBattle() {
		player.setPosition("reEnterStorageRoomPostBattle");
		animateText("You step into the storage room; the stale smell of dust and decay hits your nostrils. "
				+ "You glance over at the corner of the room where the previously slain werewolf lays motionless on the ground. "
				+ "Its once menacing teeth and claws now seem dull and lifeless. "
				+ "Your attention is drawn to the corpse the werewolf was feasting on. "
				+ "You notice that the body is clad in tattered hunter's garb. "
				+ "You recognize the insignia on the clothing, the same as the one you wear. "
				+ "It appears that the corpse was once one of your fellow hunters, perhaps a failed experiment of the same sinister forces that created the werewolf.");
	}
	
	public void inspectStorageRoomCorpse() {
		player.setPosition("inspectStorageRoomCorpse");
		animateText("As you approach the lifeless body of the hunter, the stench of death hits your nostrils. "
				+ "The corpse is dressed in tattered clothes and covered in dirt and blood like yourself. "
				+ "Gruesome wounds cover the hunter’s entire body, its face unrecognizable and chunks of flesh scattered everywhere. "
				+ "You notice the hunter's equipment is scattered haphazardly around him, including a bow that has been snapped in two and a scythe, its sharp edges still glinting in the dim light.");
	}
	
	public void inspectStorageRoomCorpseEmpty() {
		player.setPosition("inspectStorageRoomCorpseEmpty");
		animateText("As you approach the lifeless body of the hunter, the stench of death hits your nostrils. "
				+ "The corpse is dressed in tattered clothes and covered in dirt and blood like yourself. "
				+ "Gruesome wounds cover the hunter’s entire body, its face unrecognizable and chunks of flesh scattered everywhere. ");
	}
	
	public void takeStorageRoomWeapon() {
		player.setPosition("takeStorageRoomWeapon");

		animateText("You pick up the scythe alongside some gold.");
		scytheTaken=true;
		player.addGold(70);
		Game.getGameFrame().getInGamePanel().changeGoldNum(player.getGold());
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                reEnterStorageRoomPostBattle();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void reEnterSecretRoom() {
		player.setPosition("reEnterSecretRoom");
		animateText("You step into the secret room, you feel a shiver run down your spine, the same one from before. "
				+ "The room around you is shrouded in darkness, only illuminated by a few scattered flickering candles. "
				+ "The air is thick with the smell of disinfectant and foul odours coming from the jars filled with strange, unrecognizable specimens on the shelves lining the walls. "
				+ "The sound of dripping water echoes off the stone walls. "
				+ "You notice the operating table you previously awoke from and the back table with the neatly organized surgical equipment.");
	}
	
	public void enterManorHall() {
		player.setPosition("mainHall");
		animateText("You step out and find yourself in the main manor hall. "
				+ "\nThe grandeur of the space takes your breath away. "
				+ "\nDespite the evident signs of wear and tear, the hall still maintains its rich appearance, with polished marble flooring and elegant chandeliers hanging from the high ceilings. "
				+ "\nAs you take in your surroundings, you can't help but feel a sense of eeriness, as if you're being watched. "
				+ "\nYou soon realize the cause of your discomfort: the hall is adorned with several eerie statues, their cold, lifeless eyes following your every move. "
				+ "\nThe numerous doors leading to different areas of the manor make you wonder what secrets the manor holds behind each one. "
				+ "\nYou see the exit of the manor on the left, while on the right side there is a door labelled \"crossing corridor\" with a set of stairs next to it leading upwards. "
				+ "\nYou also notice two doors opposite each other, one labelled \"Lounge\" and the other \"Dining Room.\"");
	}
	
	public void enterLounge() {
		player.setPosition("enterLounge");
		animateText("You step into the Lounge, the worn yet comfortable furniture catches your eye, with plush cushions and intricate patterns on the upholstery. "
				+ "The room is dimly lit by the flickering flames of the fireplace, casting swaying shadows throughout the room. "
				+ "The walls are adorned with ornate tapestries, depicting scenes of ancient battles and hunts. "
				+ "Before you sit two figures on the furniture, dressed in old-fashioned formal attire. "
				+ "The floorboards creak beneath your feet, but they remain completely oblivious to your presence. "
				+ "They too are mesmerized by the dancing flames.");
	}
	
	public void enterLoungePostBattle() {
		player.setPosition("enterLoungePostBattle");
		animateText("You enter the Lounge, you notice the worn yet comfortable furniture with plush cushions and intricate patterns on the upholstery. "
				+ "The room is dimly lit by the flickering flames of the fireplace, casting swaying shadows throughout the room. "
				+ "The walls are adorned with ornate tapestries, depicting scenes of ancient battles and hunts. "
				+ "You recall the ghouls that were once here, but now they lay slain, their remains nowhere to be seen. "
				+ "The floorboards creak beneath your feet, but there are no figures to be found, as if they had vanished into thin air. "
				+ "The only thing left is the warmth emanating from the dwindling fire.");
	}
	
	public void restAtFireplace() {
		player.setPosition("restAtFireplace");
		player.setHealth(player.getMaxHealth());
		Game.getGameFrame().getInGamePanel().changeHealthNum(player.getHealth());
		
		animateText("You sit on the furniture before the campfire and let out a deep sigh of relief. "
				+ "The warmth of the fire washes over you, and you feel your muscles relaxing as you let yourself rest. "
				+ "You gaze at the flames, mesmerized by the way they dance and flicker. "
				+ "As you begin to drift off, your mind feels clear and free from worry. "
				+ "When you awaken, you feel rejuvenated and refreshed. "
				+ "You take a deep breath, feeling as though all of your injuries and ailments have been healed. "
				+ "You have been healed to full health."
				+ "\n\nType \"next\" to continue...");
	}
	
	public void restAtFireplace2() {
		player.setPosition("restAtFireplace2");
		animateText("It is safe to change weapons here."
				+ "\n\n1. Change weapon"
				+ "\n2. Leave fireplace");
	}
	
	public void takeWeapon1_Fireplace() {
		player.setPosition("takeWeapon1_Fireplace");
		
		player.setWeapon(ceremonialDagger);
		animateText("You have changed your weapon to the Ceremonial Dagger");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                restAtFireplace();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void takeWeapon2_Fireplace() {
		player.setPosition("takeWeapon2_Fireplace");

		player.setWeapon(dualDaggers);
		animateText("You have changed your weapon to the Dual Daggers");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                restAtFireplace2();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void takeWeapon3_Fireplace() {
		player.setPosition("takeWeapon3_Fireplace");

		player.setWeapon(soulBlade);
		animateText("You have changed your weapon to the Soul Blade");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                restAtFireplace2();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void takeWeapon4_Fireplace() {
		player.setPosition("takeWeapon4_Fireplace");

		player.setWeapon(sorcerersWand);
		animateText("You have changed your weapon to the Sorcerers Wand");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                restAtFireplace2();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void takeWeapon5_Fireplace() {
		player.setPosition("takeWeapon5_Fireplace");

		player.setWeapon(scythe);
		animateText("You have changed your weapon to the Scythe");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                restAtFireplace2();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void takeWeapon6_Fireplace() {
		player.setPosition("takeWeapon6_Fireplace");

		player.setWeapon(greatSword);
		animateText("You have changed your weapon to the Great Sword");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                restAtFireplace2();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void changeWeapon_Fireplace() {
		player.setPosition("changeWeapon_Fireplace");
		
		String daggerAvailable = "Not found";
		String dualDaggersAvailable = "Not found";
		String soulBladeAvailable = "Not found";
		String sorcerersWandAvailable = "Not found";
		String scytheAvailable = "Not found";
		String greatSwordAvailable = "Not found";
		
		if(secretRoomDaggerTaken==true) {
			daggerAvailable = "Available";
		}
		if(artifactDaggersTaken==true) {
			dualDaggersAvailable = "Available";
		}
		if(artifactBladeTaken==true) {
			soulBladeAvailable = "Available";
		}
		if(artifactWandTaken==true) {
			sorcerersWandAvailable = "Available";
		}
		if(scytheTaken==true) {
			scytheAvailable = "Available";
		}
		if(greatSwordTaken==true) {
			greatSwordAvailable = "Available";
		}
		
		animateText("Choose a weapon to equip:"
				+ "\n\n1. Ceremonial Dagger" + "(" + daggerAvailable + ")" 
				+ "\n2. Soul Blade" + "(" + dualDaggersAvailable + ")" 
				+ "\n3. Dual Daggers" + "(" + soulBladeAvailable + ")" 
				+ "\n4. Sorcerers Wand" + "(" + sorcerersWandAvailable + ")" 
				+ "\n5. Scythe" + "(" + scytheAvailable + ")"
				+ "\n6. Great Sword" + "(" + greatSwordAvailable + ")" 
				+ "\n7. Leave");
	}
	
	public void inspectGhouls() {
		player.setPosition("inspectGhouls");
		animateText("You approach the lifeless ghouls; their putrid stench disturbs your nostrils. "
				+ "With disgust, you carefully rummage through their tattered and torn clothing. "
				+ "Your eyes gleam as you spot a small sack with some gold coins and a vintage pocket watch, intricately designed with intricate engravings and delicate hands.");
	}
	
	public void inspectGhoulsEmpty() {
		player.setPosition("inspectGhoulsEmpty");
		animateText("You approach the lifeless ghouls; their putrid stench disturbs your nostrils. "
				+ "With disgust, you carefully rummage through their tattered and torn clothing. "
				+ "You find nothing worth taking.");
	}
	
	public void takePocketWatch() {
		player.setPosition("takePocketWatch");

		animateText("You take the sack of gold and the pocket watch.");
		pocketWatchTaken=true;
		player.addGold(60);
		Game.getGameFrame().getInGamePanel().changeGoldNum(player.getGold());
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                enterLoungePostBattle();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void enterGallery() {
		player.setPosition("enterGallery");
		animateText("You step into the gallery; you are struck by the beauty of the artwork that adorns the walls. "
				+ "Each painting is a masterpiece, depicting a range of subjects from peaceful landscapes to dramatic battles. "
				+ "Your eyes are immediately drawn to the largest painting, which stands out in the middle of the opposite wall. "
				+ "It depicts a grand ballroom, with elegant guests dancing in their finest attire. "
				+ "As you approach the centre of the room, you notice three glass containers, used for the display of relics.");
	}
	
	public void inspectArtifacts() {
		player.setPosition("inspectArtifacts");
		animateText("You approach the three glass containers; your eyes are immediately drawn to the artifacts they hold. "
				+ "\n\n1. The first container holds a long, curved blade with intricate carvings etched into the handle. "
				+ "You can almost feel the weight of the weapon in your hand as you study it. "
				+ "\n\n2. The second container contains a pair of violet wickedly sharp daggers, their dual blades glinting in the light. "
				+ "You imagine the ease with which they could slice through flesh and bone. "
				+ "\n\n3. Finally, your gaze lands on the third container, which holds a slender wand crafted from what appears to be polished oak wood. "
				+ "You can't help but wonder what kind of magical abilities the wand might possess in the right hands.");
	}
	
	public void inspectArtifactsEmpty() {		
		player.setPosition("inspectArtifactsEmpty");
		animateText("As you approach the three glass containers used for the display of relics, you notice that they are empty. "
				+ "There are no signs of the artifacts previously displayed. ");
	}
	
	public void takeArtifact1() {
		artifactBladeTaken=true;
		player.setPosition("takeArtifact1");
		animateText("As you place your hand on the smooth glass surface of the container, you feel a sudden rush of energy coursing through your body. "
				+ "The glass begins to pulsate as if responding to your touch, and a dark, ominous smoke seeps out of the seams of the container. "
				+ "In a blink of an eye, the other artefacts vanish into thin air, leaving only the blade behind, which materializes in your hand with a sudden weight. "
				+ "The air around you is thick with a sense of otherworldly power, and you can't help but wonder what other secrets this place might hold."
				+ "\n\nType \"next\" to continue...");
	}
	
	public void takeArtifact2() {
		artifactDaggersTaken=true;
		player.setPosition("takeArtifact2");
		animateText("As you place your hand on the smooth glass surface of the container, you feel a sudden rush of energy coursing through your body. "
				+ "The glass begins to pulsate as if responding to your touch, and a dark, ominous smoke seeps out of the seams of the container. "
				+ "In a blink of an eye, the other artefacts vanish into thin air, leaving only the dual daggers behind, which materializes in your hands with a sudden weight. "
				+ "The air around you is thick with a sense of otherworldly power, and you can't help but wonder what other secrets this place might hold."
				+ "\n\nType \"next\" to continue...");
	}
	
	public void takeArtifact3() {
		artifactWandTaken=true;
		player.setPosition("takeArtifact3");
		animateText("As you place your hand on the smooth glass surface of the container, you feel a sudden rush of energy coursing through your body. "
				+ "The glass begins to pulsate as if responding to your touch, and a dark, ominous smoke seeps out of the seams of the container. "
				+ "In a blink of an eye, the other artefacts vanish into thin air, leaving only the wand behind, which materializes in your hand with a sudden weight. "
				+ "The air around you is thick with a sense of otherworldly power, and you can't help but wonder what other secrets this place might hold."
				+ "\n\nType \"next\" to continue...");
	}
	
	public void enterDiningRoom() {
		player.setPosition("enterDiningRoom");
		animateText("You step into the manor's dining room, the musty scent of old food and stale air greets you. "
				+ "The first thing that strikes you is the ornate dining table that dominates the centre of the room. "
				+ "The large table is adorned with a pristine white tablecloth and crystal dinnerware, but years of neglect and dust have dulled their shine. "
				+ "The uniform chairs on each side are sturdy, but their once-beautiful fabric has faded to a dull grey. "
				+ "Your eyes are drawn to the end of the table, where a large, ornate chair sits. "
				+ "A skeleton, frozen in time sits mid dine. "
				+ "The undead appears possibly be the manor's former owner, slumped in the chair, adorned with royal jewels and clothing, as if he were still the king of his long-forgotten kingdom. "
				+ "On the side you also see a door conjoining the dining room with the kitchen.");
	}
	
	public void enterDiningRoomPostBattle() {		
		player.setPosition("enterDiningRoom");
		animateText("You step into the manor's dining room; the musty scent of old food and stale air greets you. "
				+ "The first thing that strikes you is the ornate dining table that dominates the centre of the room. "
				+ "The large table is adorned with a pristine white tablecloth and crystal dinnerware, but years of neglect and dust have dulled their shine. "
				+ "The uniform chairs on each side are sturdy, but their once-beautiful fabric has faded to a dull grey. "
				+ "Your eyes are drawn to the end of the table, where a large, ornate chair sits. "
				+ "The chair is empty, and you notice that the skeleton that once sat there has been slain. "
				+ "Its bones lie scattered on the floor, and the royal jewels and clothing it once wore are nowhere to be seen. "
				+ "On the side, you also see a door conjoining the dining room with the kitchen.");
	}
	
	public void inspectSkeleton() {		
		player.setPosition("inspectSkeleton");
		animateText("You closely examine the frozen skeleton; you notice intricate engravings on the royal garbs it is wearing. "
				+ "The fabric appears to be made of fine silk with golden thread sewn into it. "
				+ "The rings on the skeleton's fingers are massive, adorned with precious gems. ");
	}
	
	public void inspectDeadSkeleton() {		
		player.setPosition("inspectDeadSkeleton");
		animateText("You examine the shattered skeleton that you have previously slain. "
				+ "The royal garbs it was once wearing are laying on the floor near the grand table. "
				+ "The large rings adorned with precious gems are still attached to the fingers.");
	}
	
	public void inspectSkeletonEmpty() {		
		player.setPosition("inspectSkeletonEmpty");
		animateText("You closely examine the frozen skeleton; you notice intricate engravings on the royal garbs it is wearing. "
				+ "The fabric appears to be made of fine silk with golden thread sewn into it. "
				+ "There is nothing worth taking here. ");
	}
	
	public void inspectDeadSkeletonEmpty() {		
		player.setPosition("inspectDeadSkeletonEmpty");
		animateText("You examine the shattered skeleton that you have previously slain. "
				+ "The royal garbs it was once wearing are laying on the floor near the grand table. "
				+ "There is nothing worth taking here.");
	}
	
	public void takeRing() {
		player.setPosition("takeRing");

		animateText("You take the precious ring off the skeleton and a large sack of gold on the table.");
		ringTaken=true;
		player.addGold(120);
		Game.getGameFrame().getInGamePanel().changeGoldNum(player.getGold());
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                if(skeletonDefeated==true) {
	                	enterDiningRoomPostBattle();
	                } else if(skeletonDefeated==false) {
	                	enterDiningRoom();
	                }
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();

	}
	
	public void enterKitchen() { 
		player.setPosition("enterKitchen");
		animateText("You step into the kitchen, the smell of rotting food hits your nose. "
				+ "The tables in the centre and along the walls are cluttered with pots, pans, and utensils, some of them covered in a thick layer of grime. "
				+ "Suddenly, you hear shuffling and groaning, and turning around you see several workers, now turned undead, hard at work. "
				+ "Their skin is mottled and grey, and their eyes are clouded over, yet they move with an unnatural speed. "
				+ "They charge towards you, their hands reaching out to grab you."
				+ "\n\nType “next” to continue.");
	}
	
	public void enterKitchenPostBattle() {
		player.setPosition("enterKitchenPostBattle");
		animateText("You step into the kitchen, the smell of rotting food hits your nose. "
				+ "The tables in the centre and along the walls are cluttered with pots, pans, and utensils, some of them covered in a thick layer of grime. "
				+ "The room is silent except for the occasional creaking of the floorboards and the faint sound of dripping water. "
				+ "The undead creatures you previously encountered have been slain, and the kitchen now seems abandoned and desolate.");
	}
	
	public void inspectKitchenTable() {
		player.setPosition("inspectKitchenTable");
		animateText("As you inspect the kitchen tables, you notice that they are covered with a thick layer of dust and grime. "
				+ "Old, rotting food is scattered about the surface, with some plates and cups still holding remnants of what used to be a meal. "
				+ "The smell is nauseating, and you have to fight the urge to gag as you search for anything useful. "
				+ "Unfortunately, it seems that there is nothing worth taking from this filthy and abandoned kitchen.");
	}
	
	public void enterCrossingCorridor() {
		player.setPosition("enterCrossingCorridor");
		animateText("You step into the crossing corridor; the air grows colder and the atmosphere more ominous. "
				+ "The walls seem to close in on you, and the doors appear almost menacing. "
				+ "The many doors lining the walls seem to beckon to you, yet at the same time, they fill you with a sense of foreboding. "
				+ "The kitchen door is slightly ajar, and the overgrown vines and thorns creeping in from the garden entrance have begun to take over the frame. "
				+ "There is a larger leading to a ballroom with a much smaller door labelled “bathroom”. "
				+ "You hear low volume groans and signs of struggle from within.");
	}
	
	public void enterBathroom() {
		player.setPosition("enterBathroom");
		animateText("You step into the bathroom; the foul stench of filth overwhelms your senses. "
				+ "The walls are coated in grime and the tiles on the floor are cracked and dirty. "
				+ "Your eyes land on the source of the groaning noise and you see a small goblin, its squat body stuck on the toilet bowl. "
				+ "It struggles in vain to free itself, its eyes pleading for help. "
				+ "The smell in the room seems to have intensified around the poor creature, and you realize that it has been trapped there for some time.");
	}
	
	public void enterBathroomPostBattle() {
		player.setPosition("enterBathroomPostBattle");
		animateText("You step back into the bathroom; the foul stench of filth overwhelms your senses. "
				+ "The walls are coated in grime and the tiles on the floor are cracked and dirty. The stench of the goblin's filth has been replaced by the unmistakable odour of death. "
				+ "You see the lifeless body of the goblin slumped against the wall, a pool of blood spreading out beneath it. ");
	}
	
	public void inspectGoblin() {
		player.setPosition("inspectGoblin");
		animateText("You inspect the dead Goblin, it's pathetically small body still stuck on the toilet. "
				+ "You notice a rusty key within the grasp of its tiny hand.");
	}

	public void inspectGoblinEmpty() {
		player.setPosition("inspectGoblinEmpty");
		animateText("You inspect the dead Goblin, it's pathetically small body still stuck on the toilet. "
				+ "There doesn't seem to be anything useful on him.");
	}
	
	public void takeRustyKey() {
		player.setPosition("takeRustyKey");

		rustyKeyTaken=true;
		animateText("You take the rusty key.");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(5000);
	                enterBathroomPostBattle();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void enterBallroom() {
		player.setPosition("enterBallroom");
		animateText("You enter the ballroom; you notice the impressive upper viewing floor and the elegant decor. "
				+ "However, your attention is quickly drawn to the center of the dance floor where a large chest is sitting. "
				+ "Your eyes trail to the werewolf standing guard over it, pacing back and forth with a low growl emanating from its throat. "
				+ "Its eyes glint in the dim light, warning anyone not to come close to the treasure. ");
	}
	
	public void enterBallroomPostBattle() {
		player.setPosition("enterBallroomPostBattle");
		animateText("You enter the ballroom; you notice the impressive upper viewing floor and the elegant decor. "
				+ "The grandeur of the ballroom takes your breath away, crystal chandeliers hang from the vaulted ceiling, casting a soft glow over the room. "
				+ "The werewolf’s dead corpse still lies in the middle of the dancefloor with a chest standing next to it.");
	}
	
	public void takeGauntlet() { 
		player.setPosition("takeGauntlet");

		animateText("You take and equip the gauntlet and the pouch of gold. Your max health has increased by 25 health.");
		gauntletTaken=true;
		player.addExtraMaxHealth(25);
		player.addGold(55);
		Game.getGameFrame().getInGamePanel().changeGoldNum(player.getGold());
		Game.getGameFrame().getInGamePanel().changeHealthNum(player.getHealth());
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(7000);
	                inspectViewingFloorEmpty();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void openChest() {
		player.setPosition("openChest");

		animateText("You open the chest and take the church key.");
		churchKeyTaken=true;
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                enterBallroomPostBattle();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void openChestEmpty() {
		player.setPosition("openChestEmpty");

		animateText("You open the chest and see its empty.");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                enterBallroomPostBattle();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void inspectViewingFloor() {
		player.setPosition("inspectViewingFloor");
		animateText("You inspect the viewing floor of the ballroom. "
				+ "There seems to be nothing around other than a small bag with some gold in it. "
				+ "You look a bit further and find a pair of gauntlets.");
	}

	public void inspectViewingFloorEmpty() {
		player.setPosition("inspectViewingFloorEmpty");
		animateText("You inspect the viewing floor of the ballroom. "
				+ "There seems to be nothing around.");
	}
	
	public void goUpstairs() {
		player.setPosition("goUpstairs");
		animateText("You stand at the foot of the staircase leading to the second floor of the manor. "
				+ "The steps creak under your weight as you take the first few steps up. "
				+ "However, as you ascend further, you notice a pile of debris blocking your way. "
				+ "Fallen rubble, shattered glass, and decaying wooden planks create an insurmountable barrier. "
				+ "It's clear that this part of the house has been long abandoned and neglected. "
				+ "\n\nType \"back\" to go back to the main hall...");
	}
	
	public void enterEntranceHall() {
		player.setPosition("enterEntranceHall");
		animateText("You enter the entrance hall, drawn in by the thought of escaping the eerie atmosphere that surrounds you. "
				+ "The large door that appears to lead outside, you can't help but notice the intricate details etched into the woodwork. "
				+ "The door is massive, with iron hinges that look like they could withstand centuries of use. "
				+ "You also notice a smaller door to your left, which appears to be a broom closet with a small lock on the right hand side. "
				+ "The closet's door looks old and slightly warped, as if it hasn't been used in a long time.");
	}
	
	public void openClosetDoor() {
		player.setPosition("openClosetDoor");
		animateText("You unlock the closet door with the rusty key, you push it open to reveal a small, musty space. "
				+ "Inside, you see an array of brooms, mops, and cleaning supplies scattered about the small space. "
				+ "Amidst the clutter, your eyes are drawn to a gleaming amulet resting on a hook. "
				+ "It looks ancient, with intricate runes etched into its surface, and a soft, pulsing light emanating from within.");
	}
	
	public void openClosetDoorEmpty() {
		player.setPosition("openClosetDoorEmpty");
		animateText("You unlock the closet door with the rusty key, you push it open to reveal a small, musty space. "
				+ "Inside, you see an array of brooms, mops, and cleaning supplies scattered about the small space. "
				+ "There doesn’t seem to be anything of use here.");
	}
	
	public void takeAmulet() {
		player.setPosition("takeAmulet");

		animateText("You take the amulet off the hook.");
		amuletTaken=true;
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(5000);
	                openClosetDoorEmpty();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void exitManor() {
		player.setPosition("exitManor");
		animateText("As you step out of the manor, the cool breeze hits your face and you take in the sight before you. "
				+ "You're greeted by an forest infinite forest that seems to stretch out forever. "
				+ "The trees are tall and imposing, the canopy overhead blocking out most of the sunlight and casting the forest floor in dappled shadows. "
				+ "Despite the forest being dense and foreboding, a pathway cuts through the centre of the forest like a sharp blade, slicing through the trees and underbrush with ease.");
	}
	
	public void crossRoad1() {  
		player.setPosition("crossRoad1");
		animateText("You approach the crossroad, you see three paths diverging in different directions. "
				+ "There is one path that leads north, another leads east and the final one leads south back towards the manor.");
	}
	
	public void enterLake() {
		player.setPosition("enterLake");
		animateText("You approach a lake, you can feel a cool breeze blowing over the water. "
				+ "The sun is setting behind the trees, casting a warm orange glow over the surface of the lake. "
				+ "The ripples created by the breeze give the water an almost hypnotic quality, and you find yourself transfixed by its calming beauty. "
				+ "The sound of water lapping against the shore is soothing, you suddenly feel like resting.");
	}
	
	public void manorFront() {
		player.setPosition("manorFront");
		animateText("You approach the front of the manor, the imposing structure looms above you, its dark and Gothic architecture casting eerie shadows across the grounds. "
				+ "The size of the building is more apparent up close, and you can see the impressive scale of the structure. "
				+ "The doors are slightly ajar, and you can hear a faint creaking sound as they sway in the wind. ");
	}
	
	public void crossRoad2() { 
		player.setPosition("crossRoad2");
		animateText("You approach the crossroad, you see four paths diverging in different directions. "
				+ "There is one path that leads north, another leads west, a third one north-east and the final one leads south back towards the manor.");
		
	}
	
	public void restLake() {
		player.setPosition("restLake");
		player.setHealth(player.getMaxHealth());
		Game.getGameFrame().getInGamePanel().changeHealthNum(player.getHealth());
		
		animateText("As you sit by the tranquil lake, your eyes are drawn to the gentle ebb and flow of the water. "
				+ "The soothing rhythm lulls you into a peaceful trance, and before you know it, your head begins to nod as your eyelids grow heavy. "
				+ "The world around you fades away, replaced by the peacefulness of the lake. "
				+ "The last thing you remember is the sound of the lapping waves before drifting off into a deep slumber. "
				+ "You reawaken some time later. "
				+ "You have been healed to full health. "
				+ "\n\nType \"next\" to continue");
	}
	
	public void restLake2() {
		player.setPosition("restLake2");
		animateText("It is safe to change weapons here."
				+ "\n\n1. Change weapon"
				+ "\n2. Leave lake");
	}
	
	public void takeWeapon1_Lake() {
		player.setPosition("takeWeapon1_Lake");

		player.setWeapon(ceremonialDagger);
		animateText("You have changed your weapon to the Ceremonial Dagger");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                restLake2();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void takeWeapon2_Lake() {
		player.setPosition("takeWeapon2_Lake");

		player.setWeapon(dualDaggers);
		animateText("You have changed your weapon to the Dual Daggers");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                restLake2();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void takeWeapon3_Lake() {
		player.setPosition("takeWeapon3_Lake");

		player.setWeapon(soulBlade);
		animateText("You have changed your weapon to the Soul Blade");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                restLake2();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void takeWeapon4_Lake() {
		player.setPosition("takeWeapon4_Lake");

		player.setWeapon(sorcerersWand);
		animateText("You have changed your weapon to the Sorcerers Wand");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                restLake2();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}

	public void takeWeapon5_Lake() {
		player.setPosition("takeWeapon5_Lake");

		player.setWeapon(scythe);
		animateText("You have changed your weapon to the Scythe");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                restLake2();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	
	
	public void changeWeapon_Lake() {
		String daggerAvailable = "Not found";
		String dualDaggersAvailable = "Not found";
		String soulBladeAvailable = "Not found";
		String sorcerersWandAvailable = "Not found";
		String scytheAvailable = "Not found";
		
		if(secretRoomDaggerTaken==true) {
			daggerAvailable = "Available";
		}		
		if(artifactDaggersTaken==true) {
			dualDaggersAvailable = "Available";
		}		
		if(artifactBladeTaken==true) {
			soulBladeAvailable = "Available";
		}	
		if(artifactWandTaken==true) {
			sorcerersWandAvailable = "Available";
		}
		if(scytheTaken==true) {
			scytheAvailable = "Available";
		}
		
		player.setPosition("changeWeapon_Lake");
		animateText("Choose a weapon to equip:"
				+ "\n\n1. Ceremonial Dagger" + "(" + daggerAvailable + ")" 
				+ "\n2. Soul Blade" + "(" + dualDaggersAvailable + ")" 
				+ "\n3. Dual Daggers" + "(" + soulBladeAvailable + ")" 
				+ "\n4. Sorcerers Wand" + "(" + sorcerersWandAvailable + ")" 
				+ "\n5. Scythe" + "(" + scytheAvailable + ")" 
				+ "\n6. Leave"); 
	}
	
	public void outsideChurch() {
		player.setPosition("outsideChurch");
		animateText("As you approach the small church, the surrounding ruins speak of a time long past. "
				+ "You notice the intricate stone carvings and archaic symbols etched into the walls. "
				+ "The door to the church is tall and imposing, made of heavy wood with iron studs and hinges. "
				+ "However, as you try the handle, you find it won't budge - the door is locked tight. "
				+ "You seem to require some sort of key.");
	}

	public void enterChurch() {
		reEnterChurch=true;
		player.setPosition("enterChurch");
		animateText("You slide the key into the rusted lock and turn it, the heavy doors of the abandoned church slowly creak open. "
				+ "You step inside the abandoned church, and the musty smell of decay fills your nostrils. "
				+ "A figure begins to emerge from the shadows. "
				+ "The figure seems to be wearing the same hunter's robes as you, but its eyes glow with a sickly crimson hue and its skin is a sickly grey pallor. "
				+ "The fierce hunter seems to be corrupted by the plague. "
				+ "The hunter leaps towards you, great sword in hand and swings towards you."
				+ "\n\nType \"next\" to continue");
	}

	public void churchPostBattle() {
		finalBossClip.close();
		playInGameSoundtrack();
		purificationCubeTaken = true;
				
		player.setPosition("churchPostBattle");
		animateText("After defeating the Hunter, you look around the room once again, your eyes slowly adjust to the dim light filtering through the stained glass windows. "
				+ "The interior of the church is vast, with rows of wooden pews lining the aisle leading up to a podium. "
				+ "The walls are adorned with intricate frescoes and statues of saints and martyrs, many of which have been defaced by time and neglect. "
				+ "The air is thick with dust and the musty scent of decay, giving the impression that no one has set foot in this place in ages. "
				+ "Despite its dilapidated state, the church still exudes an aura of reverence and solemnity, a reminder of the faith and devotion that once filled these hallowed halls. "
				+ "In the centre of the room, the motionless body of the Corrupted Hunter still lays there. "
				+ "You walk up to the podium and take a cubic shaped artifact and stash it in your pockets.");
	}
	
	public void reEnterChurchPostBattle() {
		player.setPosition("churchPostBattle");
		animateText("You step inside the abandoned church, your eyes slowly adjust to the dim light filtering through the stained glass windows. "
				+ "The interior of the church is vast, with rows of wooden pews lining the aisle leading up to a podium. "
				+ "The walls are adorned with intricate frescoes and statues of saints and martyrs, many of which have been defaced by time and neglect. "
				+ "The air is thick with dust and the musty scent of decay, giving the impression that no one has set foot in this place in ages. "
				+ "Despite its dilapidated state, the church still exudes an aura of reverence and solemnity, a reminder of the faith and devotion that once filled these hallowed halls. "
				+ "In the centre of the room, you see the motionless body of the Corrupted Hunter, still in the same spot where you left it after the fierce battle. ");
	}
	
	public void purificationGrounds() {		
		player.setPosition("purificationGrounds");
		animateText("You enter the shrine, you can feel a sense of serenity washing over you. "
				+ "The air is thick with the smell of incense and the faint flicker of candles illuminates the room. "
				+ "In the center of the shrine, you see the altar, adorned with intricate carvings and surrounded by offerings left by devotees. "
				+ "You can feel the power emanating from the altar, and you know that it is a place of great spiritual significance. "
				+ "You approach it with reverence, feeling a sense of awe and wonder at the sacredness of the space. "
				+ "There is a slot in the middle that seems to be missing sommething in the shape of a cube.");
	}
	
	public void winGame() {		
		player.setPosition("winGame");
		animateText("You carefully slide the cube into the incision on the altar, and a low hum emanates from the stone structure. "
				+ "A sudden surge of energy makes the air tremble, and the artefact begins to pulsate with a light blue hue. "
				+ "You watch with amazement as the top half of the cube begins to rotate, a hum grows louder, filling the air around you with a sense of energy and excitement. "
				+ "As you step back, you notice the world around you beginning to change. "
				+ "The grey, lifeless landscape is slowly transformed, colours returning to the trees and grasses, and the sky taking on a vivid blue tone. "
				+ "You can feel the weight of corruption lifting, and a renewed sense of hope filling your heart."
				+ "\n\nType \"next\" to continue");
	}
	
	public void winGame2() {		
		player.setPosition("winGame2");
		animateText("Thank you for playing! You have completed my game, hope you enjoyed it! "
				+ "\n\n<YOU WIN>"
				+ "\n\nType \"restart\" to play again or \"quit\" to exit.");
	}
	
	public void caravan() {		
		player.setPosition("caravan");
		animateText("You make your way through the murky waters of the swamp, you spot a caravan parked off to the side. "
				+ "You wade over to investigate and see a man sitting in the driver's seat, his face obscured by the shadows. ");
	}
	
	public void traderTalk() {		
		player.setPosition("traderTalk");
		animateText("\"Good evening hunter, they call me the Rat King around here. I have all sorts of useful items to trade, how can i help you?\""
				+ "\n\n1. Trade"
				+ "\n2. Ask about the area"
				+ "\n3. Leave");
	}
	
	public void traderTrade() {
		String greatSwordAvailable = "(300 gold)";
		String potionsAvailable = "(140 gold)";
		String bootsAvailable = "(+20 max health) (requres pocket watch)";
		String chestplateAvailable = "(+50 max health) (requres ring)";
		String helmetAvailable = "(+35 max health) (requres amulet)";
		
		if(greatSwordTaken==true) {
			greatSwordAvailable = "(aquired)";
		}
		if(potionsTakenTrader==true) {
			potionsAvailable = "(aquired)";
		}	
		if(bootsTaken==true) {
			bootsAvailable = "(aquired)";
		}
		if(chestplateTaken==true) {
			chestplateAvailable = "(aquired)";
		}
		if(helmetTaken==true) {
			helmetAvailable = "(aquired)";
		}		
		
		player.setPosition("traderTrade");	
		animateText("Here is what I have to offer:"
				+ "\n\n1. GreatSword " + greatSwordAvailable
				+ "\n2. 3 Large health potions " + potionsAvailable
				+ "\n3. Boots " + bootsAvailable
				+ "\n4. Chestplate " + chestplateAvailable
				+ "\n5. Helmet " + helmetAvailable
				+ "\n6. Back");
	}
	
	public void traderTradeWeapon() {
		player.setPosition("traderTradeWeapon");	
		player.addGold(-300);
		Game.getGameFrame().getInGamePanel().changeGoldNum(player.getGold());
		greatSwordTaken=true;
		animateText("Thank you for your business!");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                traderTrade();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void traderTradePotions() {
		player.setPosition("traderTradePotions");	
		player.addGold(-140);
		Game.getGameFrame().getInGamePanel().changeGoldNum(player.getGold());
		potionsTakenTrader=true;
		largeHealthPotion.addConsumableCount(3);
		animateText("Thank you for your business!");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                traderTrade();  
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void traderTradeBoots() {
		player.setPosition("traderTradeBoots");	
		bootsTaken=true;
		player.addExtraMaxHealth(30);
		animateText("Thank you for your business!");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                traderTrade();                
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void traderTradeChestplate() {
		player.setPosition("traderTradeChestplate");	
		chestplateTaken=true;
		player.addExtraMaxHealth(50);
		animateText("Thank you for your business!");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                traderTrade();
	                
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}

	public void traderTradeHelmet() {
		player.setPosition("traderTradeHelmet");	
		helmetTaken=true;
		player.addExtraMaxHealth(35);
		animateText("Thank you for your business!");
		new Thread(new Runnable() {
	        public void run() {
	            try {
	                Thread.sleep(4000);
	                traderTrade();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
	}
	
	public void talkAboutArea1() {
		player.setPosition("talkAboutArea1");
		animateText("\"You know, this land used to be so much different,\" he says with a hint of nostalgia in his voice. "
				+ "\"We had a king, a strong ruler who kept the land safe and prosperous. "
				+ "But the king fell ill, and despite all efforts, he couldn't be saved. "
				+ "It was like the land itself mourned his passing, and slowly but surely, the corruption began to take over.\" "
				+ "The trader pauses for a moment, lost in thought before continuing. "
				+ "\"Now, it seems like every day brings a new challenge to those of us still trying to survive in this cursed place.\""
				+ "\n\nType \"next\" to continue");
	}
	
	public void talkAboutArea2() {
		player.setPosition("talkAboutArea2");
		animateText("The trader looks up at you with a solemn expression and begins to speak. "
				+ "\"The land you see around you wasn't always like this,\" he says, \"Once it was lush and green, full of life and hope. "
				+ "Creatures that once roamed peacefully became twisted and violent. "
				+ "There is hope yet! A rumour whispers that an ancient cubic artifact is still around, that has the power to purify the land and restore it to its former glory.\" "
				+ "He pauses, his eyes locked on yours as he continues. "
				+ "“They say the hunters were formed to restore the land…”"
				+ "\n\nType \"next\" to go continue");
	}
	
	public void gameOver() {
		player.setPosition("gameOver");
		animateText("You have been slain! \n\n<GAME OVER!>"
				+ "\n\nType \"restart\" to start again.");
	}
	
	public void animateText(String text) {
	    // Stop any existing animation
	    stopAnimation();

	    // Clear the JTextArea
	    JTextArea gameText = Game.getGameFrame().getInGamePanel().getGameArea();
	    gameText.setText("");

	    // Set up the timer to fire every 50 milliseconds
	    animationTimer = new Timer(45, new ActionListener() {
	        int index = 0;
	        StringBuilder builder = new StringBuilder();

	        @Override
	        public void actionPerformed(ActionEvent e) {
	            // Add the next character to the builder
	            builder.append(text.charAt(index));
	            index++;

	            // Update the gameText with the new text
	            gameText.setText(builder.toString());

	            // Stop the timer if all the text has been added
	            if (index >= text.length()) {
	                stopAnimation();
	            }
	        }
	    });
	    animationTimer.start();
	}

	public void stopAnimation() {
	    // Stop the animation timer if running
	    if (animationTimer != null && animationTimer.isRunning()) {
	        animationTimer.stop();
	    }
	}
	
	public void playInGameSoundtrack() {
		//Soundtrack
        try {
            File file = new File("res/Omen.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            inGameMusicClip = AudioSystem.getClip();
            inGameMusicClip.open(audioStream);
            inGameMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Error playing sound: " + e.getMessage());
        }
	}
	
	public void playCombatSoundtrack() {
		//Soundtrack
        try {
        	//clip.close();
            File file = new File("res/TheOneReborn.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            combatClip = AudioSystem.getClip();
            combatClip.open(audioStream);
            combatClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Error playing sound: " + e.getMessage());
        }
	}
	
	public void playFinalBossSoundtrack() {
		//Soundtrack
        try {
            File file = new File("res/LudwigTheHolyBlade.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            finalBossClip = AudioSystem.getClip();
            finalBossClip.open(audioStream);
            finalBossClip.start();
        } catch (Exception e) {
            System.out.println("Error playing sound: " + e.getMessage());
        }
	}
	
}

