package com.keystrokedynamics;

import java.awt.GridLayout;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class KeystrokeLogger extends JFrame implements KeyListener {

	private static final long serialVersionUID = 1L;

	private JFrame mainFrame;
	private JPanel textPanel;
	private JPanel inputPanel;
	private JPanel btnPanel;
	private JLabel textArea;
	private JTextField jtfInput;
	private JButton okBtn;
	private JButton canBtn;

	private long pressTime;
	private long releaseTime;
	private long pressDuration;

	private static ArrayList<Character> keyEvents;
	private static ArrayList<Long> pressEvents;
	private static ArrayList<Long> releaseEvents;

	private static final String TEXT_MESSAGE = "Click Okay button or Press Enter key to submit your typing rhythm." + 
			" Click Cancel button or Press Escape key to exit.";

	private static File textFile;

	public KeystrokeLogger() { createGui(); }

	private void createGui() {
		try {
			mainFrame = new JFrame("Keystroke Dynamics");
			mainFrame.setSize(1000, 200);
			mainFrame.setLayout(new GridLayout(3, 1));

			mainFrame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent windowEvent) {
					System.exit(0);
				}
			});

			textPanel = new JPanel();
			inputPanel = new JPanel();
			btnPanel = new JPanel();
			mainFrame.add(textPanel);
			mainFrame.add(inputPanel);
			mainFrame.add(btnPanel);	

			textArea = new JLabel(TEXT_MESSAGE);
			jtfInput = new JTextField(20);
			jtfInput.addKeyListener(this);
			textPanel.add(textArea);
			inputPanel.add(jtfInput);

			okBtn = new JButton("Okay");
			canBtn = new JButton("Cancel");
			okBtn.setActionCommand("Okay");
			canBtn.setActionCommand("Cancel");
			okBtn.addActionListener(new ButtonClickListener());
			canBtn.addActionListener(new ButtonClickListener());
			btnPanel.add(okBtn);
			btnPanel.add(canBtn);

			mainFrame.setVisible(true);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void keyTyped(KeyEvent ke) {
		try {
			Character ch = new Character(ke.getKeyChar());
			System.out.println("KeyTyped = " + ch.charValue());
			keyEvents.add(ch);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void keyPressed(KeyEvent ke) {
		try {
			Character ch = new Character(KeyEvent.getKeyText(ke.getKeyCode()).toCharArray()[0]);
			System.out.println("KeyPressed = " + ch.charValue());
			System.out.print("Time in milliseconds for key pressed = ");
			pressTime = System.currentTimeMillis();
			System.out.println(pressTime);

			pressEvents.add(pressTime);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void keyReleased(KeyEvent ke) {
		try {
			Character ch = new Character(KeyEvent.getKeyText(ke.getKeyCode()).toCharArray()[0]);
			System.out.println("KeyReleased = " + ch);
			System.out.print("Time in milliseconds for key released = ");
			releaseTime = System.currentTimeMillis();
			System.out.println(releaseTime);
			pressDuration = releaseTime - pressTime;
			System.out.println("Duration :" + pressDuration);

			releaseEvents.add(releaseTime);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private class ButtonClickListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			try {
				String command = ae.getActionCommand();
				if(command.equals("Okay")) {
					mainFrame.dispose();
					processLogs();
				}
				else if(command.equals("Cancel")) {
					mainFrame.dispose();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void processLogs() {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("Character,PressTime,ReleaseTime,Duration\n");

			System.out.println("\nSize : " + keyEvents.size() + "\n\n");
			System.out.println("\n\n\n--------------------PROCESS LOGS----------------------\n\n");

			for(int i=0; i<keyEvents.size(); i++) {
				System.out.print(keyEvents.get(i).charValue()+ "\t");
				System.out.print(pressEvents.get(i).longValue() + "\t");
				System.out.print(releaseEvents.get(i).longValue() + "\t");
				System.out.println(releaseEvents.get(i).longValue() - pressEvents.get(i).longValue());

				sb.append(keyEvents.get(i).charValue() + ",");
				sb.append(pressEvents.get(i).longValue() + ",");
				sb.append(releaseEvents.get(i).longValue() + ",");
				sb.append(releaseEvents.get(i).longValue() - pressEvents.get(i).longValue() + "\n");
			}

			textFile = new File("keystroke-dynamics.txt");
			if(!textFile.exists()) {
				textFile.createNewFile();
			} else {
				textFile.delete();
				textFile.createNewFile();
			}
			FileWriter fw = new FileWriter(textFile);
			fw.write(sb.toString());
			fw.close();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			keyEvents = new ArrayList<Character>();
			pressEvents = new ArrayList<Long>();
			releaseEvents = new ArrayList<Long>();

			KeystrokeLogger keystrokeLogger = new KeystrokeLogger();
			keystrokeLogger.pack();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}