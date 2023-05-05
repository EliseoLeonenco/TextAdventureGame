package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class StartScreenPanel extends JPanel {

    private JLabel titleLabel;
    private Font buttonsFont;
    private JButton startButton;
	GameFrame gameFrame;
	Clip musicClip, hoverClip, clickClip;
    
    public StartScreenPanel(GameFrame gFrame) {
		gameFrame = gFrame;
    	buttonsFont = new Font("Courier New", Font.PLAIN, 40);
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());

        // Create title label
        titleLabel = new JLabel("The Paleblood Hunt", SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Courier New", Font.BOLD, 64));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.insets.set(0, 0, 80, 0);
        add(titleLabel, gbc);

        //Soundtrack
        playStartScreenSoundtrack();
        
        // Create start button
        startButton = new JButton("Start");
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(Color.BLACK);
        startButton.setBorder(BorderFactory.createEmptyBorder());
        startButton.setFocusPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setFont(buttonsFont);
        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startButton.setForeground(Color.blue);
                playMouseHoverSoundeffect();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                startButton.setForeground(Color.white);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	gFrame.switchToPanel(gFrame.getInGamePanel());
            	musicClip.stop();
            	playMouseClickSoundeffect();
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.insets.set(0, 0, 20, 0);
        add(startButton, gbc);

        // Create quit button
        JButton quitButton = new JButton("Quit");
        quitButton.setForeground(Color.WHITE);
        quitButton.setBackground(Color.BLACK);
        quitButton.setBorder(BorderFactory.createEmptyBorder());
        quitButton.setFocusPainted(false);
        quitButton.setContentAreaFilled(false);
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.setFont(buttonsFont);
        quitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	quitButton.setForeground(Color.blue);
            	playMouseHoverSoundeffect();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	quitButton.setForeground(Color.white);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                System.exit(0);
            }
        });
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.insets.set(0, 0, 0, 0);
        add(quitButton, gbc);      
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 600);
    }
    
    public void playStartScreenSoundtrack() {
    	try {
            File file = new File("res/HuntersDream.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            musicClip = AudioSystem.getClip();
            musicClip.open(audioStream);
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Error playing sound: " + e.getMessage());
        }
    }
    
    public void playMouseHoverSoundeffect() {
    	try {
            File file = new File("res/ButtonHover.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            hoverClip = AudioSystem.getClip();
            hoverClip.open(audioStream);
            hoverClip.start();
        } catch (Exception e) {
            System.out.println("Error playing sound: " + e.getMessage());
        }
    }
    
    public void playMouseClickSoundeffect() {
    	try {
            File file = new File("res/GameStartSoundEffect.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clickClip = AudioSystem.getClip();
            clickClip.open(audioStream);
            clickClip.start();
        } catch (Exception e) {
            System.out.println("Error playing sound: " + e.getMessage());
        }
    }
}

