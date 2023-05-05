package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameFrame extends JFrame {

	InGamePanel inGamePanel = new InGamePanel();
	StartScreenPanel startScreenPanel = new StartScreenPanel(this);
	
	public GameFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(1920, 1080));
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setLayout(new BorderLayout());
        this.setTitle("Game Demo4");
        this.setBackground(Color.black);
        this.pack();
        this.setVisible(true);
	}

	public void switchToPanel(JPanel panel) {
	    this.getContentPane().removeAll();
	    this.getContentPane().add(panel);
	    this.revalidate();
	    this.repaint();
	}
	
	public InGamePanel getInGamePanel() {
		return inGamePanel;
	}
	
	public StartScreenPanel getStartScreenPanel() {
		return startScreenPanel;
	}

}

