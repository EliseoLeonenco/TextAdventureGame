package controller;

import view.GameFrame;

public class Game {

	static GameFrame gameFrame = new GameFrame();
	static StoryManager storyManager;	
	
	public Game() {
		storyManager = new StoryManager(this, getGameFrame());
		gameFrame.switchToPanel(gameFrame.getStartScreenPanel());
		storyManager.defaultSetup();
	}
	
	public static void main(String[] args){
		new Game();
	}

	public static GameFrame getGameFrame() {
		return gameFrame;
	}
	
	public static StoryManager getStoryManager() {
		return storyManager;
	}
}
