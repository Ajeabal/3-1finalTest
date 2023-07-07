package memoGUI;

import java.awt.Color;

import javax.swing.*;

public class HelpMe extends JFrame {
	JTabbedPane t = new JTabbedPane(JTabbedPane.BOTTOM);
	
	public HelpMe() {
		this.setTitle("도움!");
		
		
		
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		
		t.add("도움!", p1);
		p1.setBackground(Color.RED);
		t.add("도움이 필요하다!", p2);
		p2.setBackground(Color.GREEN);
		t.add("살려줘!", p3);
		p3.setBackground(Color.BLUE);
		
		this.add(t);
		setSize(450, 350);
		setLocationRelativeTo(this);
		setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
}
