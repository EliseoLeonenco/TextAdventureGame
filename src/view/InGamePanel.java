package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import controller.Game;

public class InGamePanel extends JPanel {
	
	private String command;
	private Font gameFont, gameTextFont;
	private Color backgroundColor, foregroundColor;
	private JPanel topPanel, gamePanel, bottomPanel;
	private JTextField commandTextField;
	JLabel healthLabel, healthLabelNum, goldNumLabelName, weaponLabel, weaponLabelName, goldLabelName;
	JTextArea gameTextArea;
    
	public InGamePanel() {
		gameTextFont = new Font("Courier New", Font.PLAIN, 32);
		gameFont = new Font("Courier New", Font.PLAIN, 26);
        backgroundColor = Color.BLACK;
        foregroundColor = Color.WHITE;
        this.setLayout(new BorderLayout());
        this.setBackground(Color.black);
        
        // Top panel
        topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 20));
        topPanel.setBackground(backgroundColor);
        
        //Health labels
        healthLabel = new JLabel("HEALTH:");
        healthLabel.setFont(gameFont);
        healthLabel.setForeground(foregroundColor);
        healthLabelNum = new JLabel(""+100);
        healthLabelNum.setFont(gameFont);
        healthLabelNum.setForeground(foregroundColor);
    
        //Weapon labels
        weaponLabel = new JLabel("WEAPON:");
        weaponLabel.setFont(gameFont);
        weaponLabel.setForeground(foregroundColor);
        weaponLabelName = new JLabel("");
        weaponLabelName.setFont(gameFont);
        weaponLabelName.setForeground(foregroundColor);
        
        //Gold labels
        goldLabelName = new JLabel("GOLD:");
        goldLabelName.setFont(gameFont);
        goldLabelName.setForeground(foregroundColor);
        goldNumLabelName = new JLabel("0");
        goldNumLabelName.setFont(gameFont);
        goldNumLabelName.setForeground(foregroundColor);
        
        //Add labels to top panel
        topPanel.add(healthLabel);
        topPanel.add(healthLabelNum);
        topPanel.add(weaponLabel);
        topPanel.add(weaponLabelName);
        topPanel.add(goldLabelName);
        topPanel.add(goldNumLabelName);
        this.add(topPanel, BorderLayout.NORTH);
         
        // Game panel
        gamePanel = new JPanel(new BorderLayout());
        gamePanel.setBackground(Color.black);

        //Create text area
        gameTextArea = new JTextArea(30, 60);
        gameTextArea.setEditable(false);
        gameTextArea.setLineWrap(true);
        gameTextArea.setWrapStyleWord(true);
        gameTextArea.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 200));
        gameTextArea.setFont(gameTextFont);
        gameTextArea.setForeground(foregroundColor);
        gameTextArea.setBackground(Color.black);
        gameTextArea.setText("");        
        gamePanel.add(gameTextArea, BorderLayout.CENTER);

        this.add(gamePanel, BorderLayout.CENTER);
        
        // Bottom panel
        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        bottomPanel.setBackground(backgroundColor);
        
        //Create text field
        commandTextField = new JTextField();
        commandTextField.setPreferredSize(new Dimension(700, 50));
        commandTextField.setFont(gameFont);
        commandTextField.setForeground(foregroundColor);
        commandTextField.setBackground(backgroundColor);
        commandTextField.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {

        		command = commandTextField.getText().toLowerCase();
        		System.out.println(command);
        		Game.getStoryManager().selectPosition(command);
        		commandTextField.setText("");
        	}
        	});
		
        //Create text panel and add it to main panel
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBackground(backgroundColor);
        textPanel.add(commandTextField, BorderLayout.WEST);
        bottomPanel.add(textPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
	}
	
	public String getCommand() {
		return command;
	}
	
	public JTextArea getGameArea() {
		return gameTextArea;
	}
	
	public void changeGameText(String text) {
		gameTextArea.setText(text);
	}

	public void changeHealthNum(int num) {
		healthLabelNum.setText(""+num);
	}

	public void changeWeaponName(String name) {
		weaponLabelName.setText(name);
	}
	
	public void changeGoldNum(int num) {
		goldNumLabelName.setText(""+num);
	}
	
	public void resetCommandTextField() {
		commandTextField.setText("");
	}
}

