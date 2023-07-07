package memoGUI;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class HellowSwing extends JFrame implements ActionListener {

	JTextField jtf, in_jtf1, in_jtf2, in_jtf3, in_jtf4;
	JTable jt;
	DefaultTableModel dtm;
	Connection con = null;
	PreparedStatement ps = null;
	
	public HellowSwing() {
		this.setTitle("Frame HellowSwing객체");
		
//		this.setSize(1000, 500);
//		this.setLocation(800, 400);
//		Container ct = this.getContentPane();
//		ct.setBackground(Color.black);
		
		this.setBounds(400, 200, 700, 800);
		this.setLayout(null);
		
		JPanel jp1 = new JPanel();
		JPanel jp2 = new JPanel();
		JPanel insertP = new JPanel();
		
		
		
		Object[] title = {"이름", "전화번호", "이메일", "나이"};
		
		Object[][] value = new Object[0][4];
		dtm = new DefaultTableModel(value, title);
		
		jt = new JTable(dtm);
		
		JScrollPane jp3 = new JScrollPane(jt);
		
		jp1.setBackground(Color.DARK_GRAY);
		jp2.setBackground(Color.LIGHT_GRAY);
		insertP.setBackground(Color.MAGENTA);
		
		JButton jbtn1 = new JButton("전체조회");
		JButton jbtn2 = new JButton("추가");
		JButton jbtn3 = new JButton("검색");
		JButton jbtn4 = new JButton("삭제");
		JButton jbtn5 = new JButton("수정");
		
		jbtn1.addActionListener(this);
		jbtn2.addActionListener(this);
		jbtn3.addActionListener(this);
		jbtn4.addActionListener(this);
		jbtn5.addActionListener(this);
		
		jp1.add(jbtn1);
		jp1.add(jbtn2);
		jp1.add(jbtn3);
		jp1.add(jbtn4);
		jp1.add(jbtn5);
		
//		this.add(jbtn1, BorderLayout.NORTH);
//		this.add(jbtn2, BorderLayout.WEST);
//		this.add(jbtn3);
//		this.add(jbtn4, BorderLayout.EAST);		
//		this.add(jbtn5, BorderLayout.SOUTH);
		
		jtf = new JTextField(20);
		jp2.add(jtf);
		JLabel in_la1 = new JLabel("이름 : ");
		JLabel in_la2 = new JLabel("전화번호 : ");
		JLabel in_la3 = new JLabel("이메일 : ");
		JLabel in_la4 = new JLabel("나이 : ");
		
		in_jtf1 = new JTextField(20);
		in_jtf2 = new JTextField(20);
		in_jtf3 = new JTextField(20);
		in_jtf4 = new JTextField(20);
		
		insertP.add(in_la1); insertP.add(in_jtf1);
		insertP.add(in_la2); insertP.add(in_jtf2);
		insertP.add(in_la3); insertP.add(in_jtf3);
		insertP.add(in_la4); insertP.add(in_jtf4);
		
		jp1.setBounds(5, 0, 675, 40);
		jp2.setBounds(5, 45, 675, 40);
		insertP.setBounds(5, 90, 500, 60);
		jp3.setBounds(5, 155, 675, 600);
		
		this.add(jp1);//버튼 디스플레이 패널
		this.add(jp2);
		this.add(insertP);// 정보추가 패널
		this.add(jp3);
		
		this.setVisible(true);
		
		jt.addMouseListener(new MouseAdapter() {//익명 중첩 클래스
			public void mouseClicked(MouseEvent e) {
				int row = jt.getSelectedRow();
				in_jtf1.setText((String)jt.getValueAt(row, 0));
				in_jtf2.setText((String)jt.getValueAt(row, 1));
				in_jtf3.setText((String)jt.getValueAt(row, 2));
				in_jtf4.setText((String)jt.getValueAt(row, 3));
			}
		});
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new HellowSwing();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String cmd = e.getActionCommand();
		
		if(cmd.equals("전체조회")) {
			display(1);
		}
		else if(cmd.equals("추가")) {
			if(in_jtf1.getText() != null && in_jtf2.getText() != null) {
				insert();
				display(1);
			}
		}
		else if(cmd.equals("검색")) {
			search();
			display(2);
		}
		else if(cmd.equals("삭제")) {
			delete();
			display(1);
		}
		else if(cmd.equals("수정")) {
			update();
			display(1);
		}
	}
	
	public void display(int key) {
		dtm.setNumRows(0);
		con = makeCon();
		ResultSet rs = null;
		if(key==1) {
			rs = select();
		}
		else {
			rs = search();
		}
		try {
			String info[] = new String[4];
			while(rs.next()) {
				info[0] = rs.getString(1);
				info[1] = rs.getString(2);
				info[2] = rs.getString(3);
				info[3] = rs.getString(4);
				dtm.addRow(info);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public Connection makeCon() {
		// TODO Auto-generated method stub
		String url = "jdbc:mysql://localhost:3306/app?serverTimezone=Asia/Seoul";
		String user = "root";
		String pass = "1234";
		Connection con = null;		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, pass);
			System.out.println("DB연결");
			return con;
		}
		catch(Exception e) {
			e.printStackTrace();
			return con;			
		}

	}
	
	public void insert() {
		con = makeCon();
		ResultSet rs = null;
		try {
			String sql = "select * from person where phone=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, in_jtf2.getText());
			rs = ps.executeQuery();
			if(!rs.next()) {
				if(!in_jtf4.getText().equals("")) {
					sql = "insert into person values(?, ?, ?, ?)";
				}
				else {
					sql = "insert into person(name, phone, email) values(?, ?, ?)";
				}
				ps = con.prepareStatement(sql);
				ps.setString(1, in_jtf1.getText());
				ps.setString(2, in_jtf2.getText());
				ps.setString(3, in_jtf3.getText());
				if(!in_jtf4.getText().equals(""))
					ps.setInt(4, Integer.parseInt(in_jtf4.getText()));
				int a = ps.executeUpdate();
				if(a==1) {
					System.out.println("삽입성공");
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
		
	public ResultSet select() {
		ResultSet rs = null;
		String sql = "select * from person";
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			return rs;
		}
		catch (SQLException e){
			e.printStackTrace();
			return rs;
		}
	}
	public ResultSet search() {
		con = makeCon();
		ResultSet rs = null;
		String sql = "select * from person where name=?";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, jtf.getText());
			rs = ps.executeQuery();
			return rs;
		}
		catch (SQLException e){
			e.printStackTrace();
			return rs;
		}
	}
	public void delete() {
		con = makeCon();
		String sql = "delete from person where name=?";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, in_jtf1.getText());
			int a = ps.executeUpdate();
			if(a != 0) {
				System.out.println("삭제됨");
			}
			
		}
		catch (SQLException e){
			e.printStackTrace();
			
		}
		
	}
	public void update() {	
		String sql ="update person set name=?, email = ?, age=? where phone=?";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, in_jtf1.getText());
			ps.setString(2, in_jtf3.getText());
			ps.setString(3, in_jtf4.getText());
			ps.setString(4, in_jtf2.getText());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
