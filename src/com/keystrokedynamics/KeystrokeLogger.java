package com.keystrokedynamics;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Container;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class KeystrokeLogger extends JFrame implements KeyListener
{
	private static final long serialVersionUID = 1L;
	
	JTextField jtfInput;
	long milli2;
	long milli3;
	long b;
	
	public KeystrokeLogger() 
	{
		createGui();
	}
	
	public void createGui() 
	{
		jtfInput = new JTextField(20);
		jtfInput.addKeyListener(this);
		GridBagLayout gridBag = new GridBagLayout();
		Container contentPane = getContentPane();
		contentPane.setLayout(gridBag);
		GridBagConstraints gridCons1 = new GridBagConstraints();
		gridCons1.gridwidth = GridBagConstraints.REMAINDER;
		gridCons1.fill = GridBagConstraints.HORIZONTAL;
		contentPane.add(jtfInput, gridCons1);
	}
	
	public void keyTyped(KeyEvent e) {}
	
	public void keyPressed(KeyEvent e) 
	{
		System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
		System.out.print("Time in milliseconds for key pressed = ");
		milli2 = System.currentTimeMillis();
		System.out.println(milli2);
	}
	
	public void keyReleased(KeyEvent e) {
		System.out.println("keyReleased="+KeyEvent.getKeyText(e.getKeyCode()));
		System.out.print("Time in milliseconds for key released = ");
		milli3 =System.currentTimeMillis();
		System.out.println(milli3);
		b=milli3-milli2;
		System.out.println("Duration :"+b);
	}
	
	public static void main(String[] args) 
	{
		KeystrokeLogger ehDemo = new KeystrokeLogger();
		ehDemo.pack();
		ehDemo.setVisible(true);
	}
}