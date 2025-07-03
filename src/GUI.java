import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class GUI extends JFrame {

	// �����
	
	private JPanel panel1;             // panel ��� ��� ���������� ��� ���������
	
	private JTextField user_name;      // ������� ��� ��� �������� ��� �������� ��� user
	private JTextField user_email;     // ������� ��� ��� �������� ��� email ��� user
	
	private JButton enter_page;        // �������� ���� ������ ��� user
	private JButton enter_infections;  // �������� ����� ����� ��������������� ������
	private JButton new_user;          // ���������� ���� user �� �� �������� ���
	private JButton save_book;         // ���������� ��������� �� ������
	
	private ArrayList<User> users;     // ����� �� ����� ���� users
	private ArrayList<Group> groups;   // ����� �� ��� �� groups
	
	
	// �������������
	public GUI() {
		
		// ��������� �� ����� ��� ������ ���� ������� ��� ������ ��� ������ �������
		// �� button ��� �� ������� � ������� �� �������� �� ��� ��� ��� �����
		// �������� ������������. �������������� ��� panel ��� ��� �������� �������
		// ��� ��������� �� ���� �� ���������
		
		JFrame frame = new JFrame("������� ������");
		Page_Listener page_list = new Page_Listener();
		Infections_Listener infections_list = new Infections_Listener();
		Create_User new_user_list = new Create_User();
		Save_PamakBook save_list = new Save_PamakBook();
		Container contentPane = frame.getContentPane();
		
		try {
			FileInputStream in_file = new FileInputStream("data.ser");
	        ObjectInputStream in = new ObjectInputStream(in_file);
		    users = (ArrayList<User>) in.readObject();
		    groups = (ArrayList<Group>) in.readObject();
	        in.close();
		    in_file.close();
	   }
		catch(IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		panel1 = new JPanel();

		new_user = new JButton("New User");
		panel1.add(new_user);
		
		user_name = new JTextField("User Name", 7);
		panel1.add(user_name);
		
		user_email = new JTextField("User Email", 7);
		panel1.add(user_email);
		
		enter_page = new JButton("Enter User page");
		panel1.add(enter_page);
		
		enter_infections = new JButton("Show Potential Infections");
	    panel1.add(enter_infections);
	    
	    save_book = new JButton("Save PamakBook");
	    panel1.add(save_book);
		
		enter_page.addActionListener(page_list);
		enter_infections.addActionListener(infections_list);
		new_user.addActionListener(new_user_list);
		save_book.addActionListener(save_list);
	
		contentPane.add(panel1);

		frame.setSize(330, 200);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	// �������� ��� ��� ������������ ��� �������� ���������� �� ��� ������ ��� USER
	class Page_Listener implements ActionListener {

		// �������������� ���� ���� ������� �� button enter_page
		// ������� ��� user. �� ��� ������� ��������� ������ ��� ��� ��������
		// ���������� ��������, ����������� ���������� ������� ���������� �� 
		// ��� ������ ��� user
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			String selected_name;
			User selected_user = null;
			boolean found = false;
			
			selected_name = user_name.getText();
			for (User u : users) {
				
				if ( selected_name.equals(u.getName()) ) {
					
					found = true;
					selected_user = u;
					break;
				}
			}
			
			if ( found ) 
				new Page_GUI(selected_user, users, groups);
			else 
				JOptionPane.showMessageDialog(null, "User " + selected_name + " Not Found");
		}
		
	}
	
	// �������� ��� ��� ������������ ��� �������� ���������� �� ����� ��� ����� 
	// �������������� ����� ��� USER
	class Infections_Listener implements ActionListener {
		
		// �������������� ���� ���� ������� �� button enter_infectious
		// ������� ��� user. �� ��� ������� ��������� ������ ��� ��� ��������
		// ���������� ��������, ����������� ���������� ������� ���������� �� 
		// ��� ����� ��� ����� �������������� ����� ��� user
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			String selected_name;
			User selected_user = null;
			boolean found = false;

			selected_name = user_name.getText();
			for (User u : users) {
				
				if ( selected_name.equals(u.getName()) ) {

					found = true;
					selected_user = u;
					break;
				}
			}
			
			if ( found ) 
				new Infections_GUI(selected_user);
			else 
				JOptionPane.showMessageDialog(null, "Enter a Valid Username");
		}
	}
	
	// �������� ��� ��� ���������� USER 
	class Create_User implements ActionListener {

		// �� ������� �� button create user, ���� o �������� ������� ��
		// �������� ��� �� �� JTextArea user_name ��� user_email. �� �������
		// ��� ������� �� �� ���� �����, ���� ��� ������������� ���� user ��� 
		// ����������� ������, ������ �������������.
		
		@Override
		public void actionPerformed(ActionEvent arg0) {

			String selected_name = user_name.getText();
			String selected_email = user_email.getText();
			boolean found_same = false;
			
			for ( User u : users ) {
				
				if ( u.getName().equals(selected_name) ) {
					found_same = true;
					break;
				}
			}
			
			if ( found_same )
				JOptionPane.showMessageDialog(null, "User already exists!");
			else { 
			    
				User new_user = new User(selected_name, selected_email);
				
				users.add(new_user);
			}
		}
	}
	
	// ��������� ��� ��� ���������� ��������� ��� PAMAKBOOK �� ������� ������
	class Save_PamakBook implements ActionListener {

		// ���� �������� �� button save, ���� �� �������� ��� ������������
		// ������������� �� ������� ����� ��� ������ data.ser
		@Override
		public void actionPerformed(ActionEvent arg0) {

			try {
				FileOutputStream out_file = new FileOutputStream("data.ser");
				ObjectOutputStream out = new ObjectOutputStream(out_file);
				out.writeObject(users);
				out.writeObject(groups);
				out.close();
				out_file.close();
			}
			catch(IOException i) {
				i.printStackTrace();
			} 
		}
		
		
	}
}

