package memoGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;

public class StatusBar extends JPanel {
	
	
	JPanel leftBar, middleBar, rightBar;
	JLabel leftLabel, middleLabel, rightLabel;
	JTextArea ta;
	
	public StatusBar(JFrame F, JTextArea ta) {
		this.ta = ta;
		leftLabel = new JLabel();
		middleLabel = new JLabel();
		rightLabel = new JLabel();
		
		leftBar = new JPanel();
		leftBar.setBackground(new Color(185, 185, 185));
		middleBar = new JPanel(); 
		middleBar.setBackground(new Color(165, 165, 165));
		rightBar = new JPanel();
		rightBar.setBackground(new Color(155, 155, 155));
		
		leftBar.add(leftLabel);
		middleBar.add(middleLabel);
		rightBar.add(rightLabel);
		
		this.setLayout(new GridLayout(1,3));
		this.add(leftBar);
		this.add(middleBar);
		this.add(rightBar);
		
		ta.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				// TODO Auto-generated method stub
				// 커서의 위치값 가져와서 mid 레이블에 표시	
				int x=0, y=0;
				try {
//					int cor = ta.getCaretPosition(); //커서의 열(y) 값
//					x = ta.getLineOfOffset(cor); //커서 열값 위치에 해당되는 행(x) 값
//					int startcor = ta.getLineStartOffset(x);
//					y = cor - startcor;
					
					y = ta.getCaretPosition(); 
					x = ta.getLineOfOffset(y);
					String text = ta.getText();
					int index = y - 1;
					while (index >= 0 && text.charAt(index) != '\n') {
						index--;
					}
					if (index >= 0 && text.charAt(index) == '\n') {
						y = y - index -1;
					}
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				} 
				
				middleLabel.setText("행 : " + (x+1) + " 열 : " + (y+1));	
			}
			
		});;
		
		Timer timer = new Timer(900, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				repaint();
			}
		});
		timer.start();
	}
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    // 날짜를 생성해서 left 레일블에 표시
	    Date d = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd aa HH:mm:ss");				
		leftLabel.setText(sd.format(d));		
		
		// 글짜크기 비율값 계산해서 right 레이블에 표시
		Font ft = ta.getFont();
		double rate = ft.getSize()/12.0 * 100;
		rightLabel.setText(String.format("%.0f", rate)+"%");
	}
	
	
}