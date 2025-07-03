import java.awt.event.*;
import javax.swing.*;

public class Infections_GUI extends JFrame {

	// �����
	
	private JPanel panel;                // �� panel ��� ����� ������������� �� ��������
	
	private JTextArea infected_list;     // � ������� ���� ����� ����������� � �������������� user 
	                                     // ��� � ����� �� ����� ��������������� ������ ���
	private JButton login_page;          // button ��� ��������� ��� ������ ��������
	private User theUser;                // � user ��� ��� ����� �� ���������� � ����� �� ���� �����
	                                     // ��������������� ������ ���
	
	// �������������
	
	public Infections_GUI(User aUser) {
		
		// ����� ��� printInfectedUsers ��� ������ User (��� ��� User ��� ������ ���� �� GUI)
		// ��� �� ��������� ���� ������� ��� ����� �� ���� ����� ��������������� user. �� �������
		// �� button login_page ���������� ���� ������ ����� ���� ��� ���������� ������� ���
		// �������������
		
		theUser = aUser;
		
		JFrame frame = new JFrame("������ �������� ���");
		panel = new JPanel();
		
		infected_list = new JTextArea(theUser.printInfectedUsers());
		panel.add(infected_list);
		
		login_page = new JButton("Back to Login Screen");
		login_page.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
		
            	 frame.dispose();
             }}
		     );
		panel.add(login_page);
			
		frame.setContentPane(panel);
			
		frame.setSize(450, 270);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}


