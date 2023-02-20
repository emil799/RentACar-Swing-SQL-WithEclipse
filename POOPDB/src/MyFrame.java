import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MenuBar;
import java.awt.MenuComponent;
import java.awt.Rectangle;
import java.awt.Shape;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class MyFrame extends JFrame {
	
	Connection conn = null;
	PreparedStatement state = null;
	ResultSet result = null;
	int id=-1;
	
	JPanel upPanel = new JPanel();
	JPanel midPanel = new JPanel();
	JPanel downPanel = new JPanel();
	
	JLabel fnameL = new JLabel("���:");
	JLabel  lnameL = new JLabel("�������:");
	JLabel sexL = new JLabel("���:");
	JLabel ageL = new JLabel("������:");
	JLabel salaryL = new JLabel("�������:");
	
	JTextField fnameTF = new JTextField();
	JTextField lnameTF = new JTextField();
	JTextField ageTF = new JTextField();
	JTextField salaryTF = new JTextField();
	
	String[] item = {"���","����"};
	JComboBox<String> sexCombo = new JComboBox<String>(item);
	
	JButton addBt = new JButton("��������");
	JButton deleteBt = new JButton("���������");
	JButton editBt = new JButton("�����������");
	JButton searchBt = new JButton("������� �� ���");
	JButton refreshBt = new JButton("������");
	
	JTable table = new JTable();
	JScrollPane myScroll = new JScrollPane(table);
	
	public MyFrame() {
		this.setSize(400,600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(3,1));
		// ----------------------------------------
		upPanel.setLayout(new GridLayout(5,2));
		
		upPanel.add(fnameL);
		upPanel.add(fnameTF);
		upPanel.add(lnameL);
		upPanel.add(lnameTF);
		upPanel.add(sexL);
		upPanel.add(sexCombo);
		upPanel.add(ageL);
		upPanel.add(ageTF);
		upPanel.add(salaryL);
		upPanel.add(salaryTF);
		
		//------------------------------------------
		midPanel.add(addBt);
		midPanel.add(deleteBt);
		midPanel.add(editBt);
		midPanel.add(searchBt);
		midPanel.add(refreshBt);
		//-------------------------------------------
		myScroll.setPreferredSize(new Dimension(350,150));
		downPanel.add(myScroll);
		
		this.add(upPanel);
		this.add(midPanel);
		this.add(downPanel);
		
		addBt.addActionListener(new AddAction());
		table.addMouseListener(new MouseAction());
		deleteBt.addActionListener(new DeleteAction());
		editBt.addActionListener(new EditAction());
		searchBt.addActionListener(new SearchAction());
		refreshBt.addActionListener(new RefreshAction());
		
		refreshTable();
		this.setVisible(true);
		
		
	}
	
	public void refreshTable() {
		conn = DBConnection.getConnection();
		
		try {
			state = conn.prepareStatement("select * from persom");
			result = state.executeQuery();
			table.setModel(new MyModel(result));
			
		} catch(SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void clearForm() {
		fnameTF.setText("");
		lnameTF.setText("");
		ageTF.setText("");
		salaryTF.setText("");
	}
	class AddAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			conn = DBConnection.getConnection();
			String sql = "insert into persom(fnam, lname, sex, age, salary) values(?,?,?,?,?)";
			try {
				state = conn.prepareStatement(sql);
				state = conn.prepareStatement(sql);
				state.setString(1, fnameTF.getText());
				state.setString(2, lnameTF.getText());
				state.setString(3,sexCombo.getSelectedItem().toString());
				state.setInt(4, Integer.parseInt(ageTF.getText()));
				state.setFloat(5, Float.parseFloat(salaryTF.getText()));
				
				state.execute();
				refreshTable();
				clearForm();
			}catch(SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	class MouseAction implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			int row = table.getSelectedRow();
			id = Integer.parseInt(table.getValueAt(row, 0).toString());
			fnameTF.setText(table.getValueAt(row, 1).toString());
			lnameTF.setText(table.getValueAt(row, 2).toString());
			ageTF.setText(table.getValueAt(row, 4).toString());
			salaryTF.setText(table.getValueAt(row, 5).toString());
			if(table.getValueAt(row, 3).equals("���")) {
			sexCombo.setSelectedIndex(0);
			}
			else {
				sexCombo.setSelectedIndex(1);
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class DeleteAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			conn = DBConnection.getConnection();
			String sql = "delete from persom where id=?";
			
			try {
				state = conn.prepareStatement(sql);
				state.setInt(1, id);
				state.execute();
				refreshTable();
				clearForm();
				id = -1;
			} 
			catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
	}
	class EditAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			conn = DBConnection.getConnection();
			String sql = "update persom set fnam=?,lname=?,sex=?,age=?,salary=? where id=?";
			
			try {
				state = conn.prepareStatement(sql);
				state.setInt(6, id);
				state.setString(1, fnameTF.getText());
				state.setString(2,lnameTF.getText());
				state.setString(3, sexCombo.getSelectedItem().toString());
				state.setInt(4, Integer.parseInt(ageTF.getText()));
				state.setFloat(5, Float.parseFloat(salaryTF.getText()));
				state.execute();
				refreshTable();
				clearForm();
				id = -1;
			} 
			catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}
	
	class SearchAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			conn = DBConnection.getConnection();
			String sql = "select * from persom where fnam=?";
			
			try {
				state = conn.prepareStatement(sql);
				state.setString(1, fnameTF.getText());
				result = state.executeQuery();
				table.setModel(new MyModel(result));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
	}
	class RefreshAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			refreshTable();
			clearForm();
			
		}
		
	}

}
