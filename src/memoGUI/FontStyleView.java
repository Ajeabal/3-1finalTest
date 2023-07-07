package memoGUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;

public class FontStyleView extends JFrame implements ActionListener, ListSelectionListener { //액션 리스너와 리스트가 눌렸는지 확인하는 리스너
	String[] fontName = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames(); //폰트의 이름을 담아주는 배열
	String[] fontStyleName = {"PLAIN", "BOLD", "ITALIC"}; //폰트 스타일 이름을 담아줄 배열
	String[] fontSize = {"6","7","8","9","10","12","14","17","20","24","30","40"}; //폰트 사이즈를 담아줄 배열
	
	JList listFontName, listFontStyle, listFontSize; //각 각 패널에 달아줄 리스트들
	JPanel listPanel, centerPanel, southPanel; // 각 각 리스트를 넣어줄 패널
	JScrollPane listScrollPane; //스크롤을 위한 패널
	private JTextArea ta; //텍스트가 적힐 공간
	JLabel textLabel; //변경되는 예시가 되어줄 라벨
	JButton bConfirm, bCancel; //버튼들
	Font newFont = null; //값의 변경을 위한
	
	public FontStyleView(JTextArea ta) {
		this.ta = ta; //JTextArea선언
		
		Container con = getContentPane(); //컴포넌트르 담을 그릇 Container con 선언
		centerPanel = new JPanel(new GridLayout(2, 1)); //가운데에 올 패널 선언
		listPanel = new JPanel(); //리스트 패널 선언
		listPanel.setLayout(new GridLayout(0,3)); //위치
		
		listFontName = new JList(fontName);//폰트 이름 리스트 삽입
		listFontName.addListSelectionListener(this);//선택 리스너
		listScrollPane = new JScrollPane(listFontName);//스크롤 패널에 추가
		listScrollPane.setBorder(new TitledBorder("Font Name"));//제목 추가
		listPanel.add(listScrollPane);//listScrollPane에 listPanel추가
		listFontName.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);//한번에 하나씩 선택되게 설정
		listFontName.setSelectedValue(ta.getFont().getName(), false);//선택된 값 가져옴
		
		listFontStyle = new JList(fontStyleName); 
		listFontStyle.addListSelectionListener(this);
		listScrollPane = new JScrollPane(listFontStyle);
		listScrollPane.setBorder(new TitledBorder("Font Style Name"));
		listPanel.add(listScrollPane);
		listFontStyle.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		listFontStyle.setSelectedIndex(ta.getFont().getStyle());
		
		listFontSize = new JList(fontSize);
		listFontSize.addListSelectionListener(this);
		listScrollPane = new JScrollPane(listFontSize);
		listScrollPane.setBorder(new TitledBorder("Font Size"));
		listPanel.add(listScrollPane);
		listFontSize.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		listFontSize.setSelectedValue(""+ta.getFont().getSize(), false);
		
		textLabel = new JLabel("TEST 테스트"); //테스트 화면 띄워줄 JLabel
		textLabel.setHorizontalAlignment(JLabel.CENTER); //가운데 정렬
		
		textLabel.setFont(new Font((String)(listFontName.getSelectedValue()), listFontStyle.getSelectedIndex(),
				Integer.parseInt((String)(listFontSize.getSelectedValue())))); //각 각의 선택된 값으로 폰트 설정
		centerPanel.add(listPanel);
		centerPanel.add(textLabel);//centerPanel에 listPanel, textLabel 배치
		bConfirm = new JButton("확인");//버튼들 선언
		bCancel = new JButton("취소");
		bConfirm.addActionListener(this);//버튼들에 클릭을 확인할 엑션 리스너 추가
		bCancel.addActionListener(this);
		southPanel = new JPanel();//화면에서 아래쪽에 넣을 패널 선언
		southPanel.add(bConfirm);//버튼 추가
		southPanel.add(bCancel);
		con.add(centerPanel, "Center"); //getContentPane의 값을 가진 Container con에 중앙과 하단에 각 각의 패널들 배치
		con.add(southPanel, "South");
		newFont = textLabel.getFont();//textLabel에 넣어진 값을 newFont넣어줌
	}
	

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		try {
			textLabel.setFont(new Font((String)(listFontName.getSelectedValue()), listFontStyle.getSelectedIndex(),
					Integer.parseInt((String)(listFontSize.getSelectedValue()))));
			newFont = textLabel.getFont();
		}catch(NullPointerException e1) {
			e1.getMessage();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("확인"))
			ta.setFont(newFont);//확인 버튼이 눌리면 ta에 newFont값을 넣어줌
		this.dispose();
	}

}
