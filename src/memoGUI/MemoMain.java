package memoGUI;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javax.swing.*;
import javax.swing.event.*;

import myCalendar.celestialCalendar;

public class MemoMain extends JFrame implements ActionListener {
	JTextArea ta;
	JFileChooser jfc;
	boolean isNew = false;
	File file;
	FontStyleView fontStyleView;
	StatusBar statusBar;
	JButton newBtn, openBtn, saveBtn, saveAsBtn, exitBtn, chFindBtn, findBtn, fontBtn, colorBtn;

	public MemoMain() {
		setTitle("이승한");
		MainView();
 		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ta = new JTextArea();
		JScrollPane jsp = new JScrollPane(ta);
		jfc = new JFileChooser();
		this.add(jsp);
		statusBar = new StatusBar(this, this.ta);
		this.add(BorderLayout.SOUTH, statusBar);
		setBounds(800, 200, 500, 700);
		setVisible(true);
	}

	public void MainView() {
		KeyStroke key;
		JMenuBar mb = new JMenuBar();
		JMenu m1, m2, m3, m4, m5, m6;
		JMenuItem item;

		JToolBar tb = new JToolBar();
		ImageIcon newF = new ImageIcon("image/new.PNG");
		ImageIcon saveF = new ImageIcon("image/save.PNG");
		ImageIcon openF = new ImageIcon("image/open.PNG");
		ImageIcon saveAsF = new ImageIcon("image/saveAs.PNG");
		ImageIcon exitF = new ImageIcon("image/exit.PNG");
		ImageIcon findF = new ImageIcon("image/find.PNG");
		ImageIcon fontF = new ImageIcon("image/font.PNG");
		ImageIcon chFindF = new ImageIcon("image/chFind.PNG");
		ImageIcon colorF = new ImageIcon("image/color.PNG");

		newBtn = new JButton(newF);
		openBtn = new JButton(openF);
		saveBtn = new JButton(saveF);
		saveAsBtn = new JButton(saveAsF);
		exitBtn = new JButton(exitF);
		findBtn = new JButton(findF);
		chFindBtn = new JButton(chFindF);
		fontBtn = new JButton(fontF);
		colorBtn = new JButton(colorF);

		newBtn.addActionListener(this);
		openBtn.addActionListener(this);
		saveBtn.addActionListener(this);
		saveAsBtn.addActionListener(this);
		exitBtn.addActionListener(this);
		findBtn.addActionListener(this);
		chFindBtn.addActionListener(this);
		fontBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				fontStyleView = new FontStyleView(ta);
				fontStyleView.setBounds(200, 200, 400, 350);
				fontStyleView.setVisible(true);

			}
		});
		colorBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Color setColor = JColorChooser.showDialog(getParent(), "색상표", Color.yellow);
				if (setColor != null) {
					ta.setForeground(setColor);
				}
			}
		});

		newBtn.setToolTipText("새파일을 작성합니다.");
		openBtn.setToolTipText("파일을 열어줍니다.");
		saveBtn.setToolTipText("현재 내용을 저장합니다.");
		saveAsBtn.setToolTipText("현재 내용을 다른 이름으로 저장합니다.");
		exitBtn.setToolTipText("종료합니다.");
		findBtn.setToolTipText("원하는 텍스트를 찾습니다.");
		chFindBtn.setToolTipText("원하는 텍스트를 찾아바꿔줍니다.");
		fontBtn.setToolTipText("폰트의 스타일을 바꾸는 창을 띄워줍니다.");
		colorBtn.setToolTipText("색상을 바꿀 수 있는 창을 띄워줍니다.");
		tb.add(newBtn);
		tb.add(openBtn);
		tb.add(saveBtn);
		tb.add(saveAsBtn);
		tb.addSeparator();
		tb.add(exitBtn);
		tb.add(findBtn);
		tb.add(chFindBtn);
		tb.add(fontBtn);
		tb.add(colorBtn);

		m1 = new JMenu("파일(F)");
		m1.setMnemonic(KeyEvent.VK_F);
		item = new JMenuItem("새로 만들기", KeyEvent.VK_N);
		key = KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(this);
		m1.add(item);
		item = new JMenuItem("열기", KeyEvent.VK_O);
		key = KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(this);
		m1.add(item);
		m1.addSeparator();
		item = new JMenuItem("저장", KeyEvent.VK_S);
		key = KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(this);
		m1.add(item);
		item = new JMenuItem("다른 이름으로 저장", KeyEvent.VK_S);
		key = KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK);
		item.setAccelerator(key);
		item.addActionListener(this);
		m1.add(item);
		m1.addSeparator();
		item = new JMenuItem("끝내기", KeyEvent.VK_X);
		key = KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(this);
		m1.add(item);

		m2 = new JMenu("편집(E)");
		m2.setMnemonic(KeyEvent.VK_E);
		
		item = new JMenuItem("잘라내기(X)", KeyEvent.VK_T);
		key = KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ta.cut();
			}
		});
		m2.add(item);
		item = new JMenuItem("복사", KeyEvent.VK_C);
		key = KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ta.copy();
			}
		});
		m2.add(item);
		item = new JMenuItem("붙여넣기", KeyEvent.VK_P);
		key = KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ta.paste();
			}
		});
		m2.add(item);
		item = new JMenuItem("삭제", KeyEvent.VK_DELETE);
		key = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ta.cut();
			}
		});
		m2.add(item);
		m2.addSeparator();
		item = new JMenuItem("찾기", KeyEvent.VK_F);
		key = KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(this);
		m2.add(item);
		item = new JMenuItem("찾아 바꾸기", KeyEvent.VK_R);
		key = KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(this);
		m2.add(item);
		m2.addSeparator();
		item = new JMenuItem("모두 선택", KeyEvent.VK_A);
		key = KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ta.selectAll();
			}
		});
		m2.add(item);
		item = new JMenuItem("시간/날짜", KeyEvent.VK_D);
		key = KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("aa HH:mm yyyy-MM-dd");
				ta.append(sdf.format(d));
			}
		});
		m2.add(item);

		m3 = new JMenu("서식(O)");
		m3.setMnemonic(KeyEvent.VK_O);
		item = new JMenuItem("배경색", KeyEvent.VK_B);
		key = KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Color setColor = JColorChooser.showDialog(getParent(), "색상표", Color.yellow);
				if (setColor != null) {
					ta.setBackground(setColor);
				}
			}
		});
		m3.add(item);
		item = new JMenuItem("글자색", KeyEvent.VK_E);
		key = KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Color setColor = JColorChooser.showDialog(getParent(), "색상표", Color.yellow);
				if (setColor != null) {
					ta.setForeground(setColor);
				}
			}
		});
		m3.add(item);
		item = new JMenuItem("글꼴", KeyEvent.VK_F);
		key = KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				fontStyleView = new FontStyleView(ta);
				fontStyleView.setBounds(200, 200, 400, 350);
				fontStyleView.setVisible(true);

			}
		});
		m3.add(item);

		m4 = new JMenu("보기(V)");
		m4.setMnemonic(KeyEvent.VK_V);
		JMenu submenu = new JMenu("확대 / 축소");
		m4.add(submenu);
		submenu.add(new JMenuItem("확대(I)")).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Font ft = ta.getFont();
				String font_name = ft.getName();
				int font_style = ft.getStyle();
				int font_size = ft.getSize() + 2;
				ft = new Font(font_name, font_style, font_size);
				ta.setFont(ft);
			}

		});
		submenu.add(new JMenuItem("축소(O)")).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Font ft = ta.getFont();
				String font_name = ft.getName();
				int font_style = ft.getStyle();
				int font_size = ft.getSize() - 2;
				ft = new Font(font_name, font_style, font_size);
				ta.setFont(ft);
			}

		});
		submenu.add(new JMenuItem("확대/축소 복원(I)")).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Font ft = ta.getFont();
				String font_name = ft.getName();
				int font_style = ft.getStyle();
				int font_size = 12;
				ft = new Font(font_name, font_style, font_size);
				ta.setFont(ft);

			}

		});
		JCheckBoxMenuItem cb = new JCheckBoxMenuItem("상태표시줄(S)");
		cb.setState(true);
		cb.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				if (cb.getState() == true) {
					statusBar.setVisible(true);
				} else {
					statusBar.setVisible(false);
				}
			}

		});
		m4.add(cb);

		m5 = new JMenu("도움말(H)");
		m5.setMnemonic(KeyEvent.VK_H);
		item = new JMenuItem("도움말 보기", KeyEvent.VK_H);
		key = KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new HelpMe();
			}
		});

		m5.add(item);
		m5.addSeparator();
		item = new JMenuItem("메모장 정보", KeyEvent.VK_D);
		key = KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK);
		item.setAccelerator(key);
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame jf = new JFrame();
				JTextArea nta1 = new JTextArea();
				JTextArea nta2 = new JTextArea();
				JTextArea nta3 = new JTextArea();

				jf.add(nta1);
				jf.add(nta2);
				jf.add(nta3);

				jf.setTitle("이승한의 메모장");
				jf.setSize(600, 300);
				jf.setLocationRelativeTo(null);

				JLabel label = new JLabel("");
				Image img = new ImageIcon("./main.JPG").getImage();
				label.setIcon(new ImageIcon(img));
				label.setBounds(0, 0, 50, 50);
				jf.getContentPane().add(label);

				nta1.setText("-메모장 기능들 구현중-");
				nta1.setBounds(230, 0, 200, 100);
				nta1.setEditable(false);
				nta2.setText("-2023-04~(개발중)");
				nta2.setBounds(230, 100, 200, 100);
				nta2.setEditable(false);
				nta3.setText("-ver0.16 이승한 제작-");
				nta3.setBounds(230, 200, 200, 100);
				nta3.setEditable(false);
				jf.setVisible(true);
			}
		});
		m5.add(item);

		m6 = new JMenu("잡동사니");
		item = new JMenuItem("폰북");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new HellowSwing();
			}
		});
		m6.add(item);

		item = new JMenuItem("계산기");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Calculator();

			}
		});
		m6.add(item);

		item = new JMenuItem("스케쥴러");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new MemoCalendar();

			}
		});
		m6.add(item);
		
		item = new JMenuItem("천문달력");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new celestialCalendar();

			}
		});
		m6.add(item);

		mb.add(m1);
		mb.add(m2);
		mb.add(m3);
		mb.add(m4);
		mb.add(m5);
		mb.add(m6);
		this.add(BorderLayout.NORTH, tb);
		this.setJMenuBar(mb);
	}

	void open() {
		int re = jfc.showOpenDialog(this);
		if (re == 0) {
			file = jfc.getSelectedFile();
			String data = "";
			int ch;
			try {
				FileReader fr = new FileReader(file);
				while ((ch = fr.read()) != -1)
					data = data + (char) ch;
				ta.setText(data);
				isNew = true;
				setTitle("이승한-" + file.getName());
				fr.close();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}

		}
	}

	int check() {
		int a = 0;
		String data = "";
		int ch;
		try {
			if (isNew == true) {
				FileReader fr = new FileReader(file);
				while ((ch = fr.read()) != -4)
					data = data + (char) ch;
				fr.close();

				if (!ta.getText().equals(data))
					a = 1;
			} else if (isNew == false && !ta.getText().equals(""))
				a = 1;
		} catch (Exception e) {
			e.getMessage();
		}
		return a;
	}

	void save() {
		// 새파일 일 때, 기존 파일 저장할 때 대화상자가 나타나거나 안나타나게 hint. is new
		if (isNew == false) {
			int re = jfc.showSaveDialog(this);
			if (re == 0) {
				file = jfc.getSelectedFile();
				try {
					FileWriter fw = new FileWriter(file);
					fw.write(ta.getText());
					fw.close();
					JOptionPane.showMessageDialog(this, "파일을 저장했습니다.");
					setTitle("이승한-" + file.getName());
					isNew = true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.getMessage();
				}
			}
		} else if (isNew == true) {
			file = jfc.getSelectedFile();
			try {
				FileWriter fw = new FileWriter(file);
				fw.write(ta.getText());
				fw.close();
				JOptionPane.showMessageDialog(this, "파일을 저장했습니다.");
				setTitle("이승한-" + file.getName());
				isNew = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
		}
	}

	void saveAs() {
		isNew = false;
		save();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String cmd = e.getActionCommand();
		if (cmd.equals("새로 만들기") || e.getSource() == newBtn) {
			int a = check();
			if (a == 1) {
				int b = JOptionPane.showConfirmDialog(this, "변경 내용을 저장하시겠습니까?", "메모장", 1);
				if (b == 0) {
					save();
				} else if (b == 1) {
					ta.setText("");
					isNew = false;
					file = null;
				}
			}
		} else if (cmd.equals("열기") || e.getSource() == openBtn) {
			int a = check();
			if (a == 1) {
				int b = JOptionPane.showConfirmDialog(this, "변경 내용을 저장하시겠습니까?", "메모장", 1);
				if (b == 0) {
					save();
				} else if (b == 1) {
					open();
				}
			} else {
				open();
			}
		} else if (cmd.equals("저장") || e.getSource() == saveBtn) {
			save();
		} else if (cmd.equals("다른 이름으로 저장") || e.getSource() == saveAsBtn) {
			saveAs();
		} else if (cmd.equals("끝내기") || e.getSource() == exitBtn) {
			int a = check();
			if (a == 1) {
				int b = JOptionPane.showConfirmDialog(this, "변경 내용을 저장하시겠습니까?", "메모장", 1);
				if (b == 0) {
					save();
				} else if (b == 1) {
					System.exit(0);
				}
			} else {
				System.exit(0);
			}
		}
		else if (cmd.equals("찾기") || e.getSource() == findBtn) {
			Find fi = new Find(this, ta);
			fi.showFind();
		} else if (cmd.equals("찾아 바꾸기") || e.getSource() == chFindBtn) {
			Find fi = new Find(this, ta);
			fi.showReplace();
		}

	}

	private String String(Object source) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MemoMain();

	}

}
