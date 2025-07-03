import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.util.*;
import javax.swing.*;

public class Page_GUI extends JFrame {

	// ����� 
	
	private JPanel panel;                // �� ������ panel ��� �������� ��� �� ����
	private JPanel panel1;               // panel ��� �������� �� �������� ��� user, � button ���������� ���� ������ �����, ��� 
	                                     // ������� ��� ��� �������� ��� post ��� �o button ��� ��� ���������� ���
	private JPanel panel2;               // panel ��� �������� �� label ��� ��� ������� ��� �� post ��� ����� ��� ��� user
	private JPanel panel3;               // panel ��� �������� �� label ��� ��� ����� �� ���� �������������� ������
	private JPanel panel4;               // panel ��� �������� �� �������� ��� ��� �������� �������� �����
	private JPanel panel5;               // panel ��� �������� �� �������� ��� ��� �������� �� group ��� �����

	private User theUser = null ;        // � user ��� ��� ����� ������� � ������� ����������
	private JTextArea name;              // ������� �� �� ����� ��� user
	private JTextArea email;             // ������� �� �� email ��� user
	private ArrayList<User> users = null;      // ����� �� ����� ���� users
	
	private JButton login_page;          // button ��� ��� ��������� ���� ������ �����
	
	private JTextArea friends_name;      // ������� ��� ��� �������� �������� user ��� ��� ���������� ������
	private JButton add;                 // ���������� ��� ����� ������ ������ ��� 2 user

	private ArrayList<Group> groups;     // ����� �� �� groups ��� ��������
	private JList<String> list_groups;           // ����� �� �� group ��� ����� ������ �� �������� � user
	private JButton subscribe;           // button ��� ��� �������� �� group (�� ���������� �� ��������� �� ������������
	
	private JTextArea user_post;         // ������� ��� ��������� � user �� post ���
	private JButton post;                // button ��� ��� ���������� ��� post
	
	private JLabel friends_posts_label;  // label ��� ��� �������� ��� ����� ���������� �� post ��� ����� ��� ��� user
	private JTextArea friends_posts;     // ������� ��� ������������ �� post ��� ����� ��� ��� user
	private JScrollPane scroll;          // ��� ��� ��������� ���� ������� ��� post
	
	private JLabel sugg_friends_label;   // label ��� ��� �������� ��� ����� ���������� �� ������������� ����� ��� user
	private HashSet<User> sugg_users;    // HashSet �� �� ����� ������ ���� �������������� ������
	private JList<String> sugg_friends;  // JList ��� ��� ���������� ��� ������ ��� ������������� �����

	// �������������
	
	Page_GUI(User user_1,  ArrayList<User> users , ArrayList<Group> groups) {
		
		// �������������� 3 panel ��� ��� �������� ������� ��� ��������� ��� �������� �� ���� 
		// �� ������� ��� ���� �� ������. �������������, ������, ���������� �������� ��� ��� 
		// ��������� ���� ������ ����� ��� �� button login_page. ������ �������������� �� ��������
		// ��������� ��� �� GUI ��� ���� ��� ���������� ���� ��������� ���� ��� �������.
		
		// GUI Data
		theUser = user_1;
		this.groups = groups;
		this.users = users;

		
		// Setting Window
		JFrame frame = new JFrame("������ ������");
		Container contentPane = frame.getContentPane();
		
		
		// contentPane.setLayout();
		panel = new JPanel();
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();
        panel5 = new JPanel();
        
		
        // Listeners
		Post_Listener post_list = new Post_Listener();
		Add_Listener add_list = new Add_Listener();
		Group_Listener group_list = new Group_Listener();
	
		
		// Panel1
		name = new JTextArea(1, 8);
		name.setText(theUser.getName());
		name.setEditable(false);
		
		email = new JTextArea(1, 15);
		email.setText(theUser.getEmail());
		email.setEditable(false);
		
		login_page = new JButton("Back to Login Screen");
		login_page.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e)
		    {
		       frame.dispose();
		    }}
		    );
		
		user_post = new JTextArea(10, 18);
		user_post.setLineWrap(true);
		
		post = new JButton("Post");
		post.addActionListener(post_list);
		
		panel1.add(name, FlowLayout.LEFT);
		panel1.add(email, FlowLayout.CENTER);
		panel1.add(login_page, FlowLayout.RIGHT);
		panel1.add(user_post, BorderLayout.WEST);
		panel1.add(post, BorderLayout.CENTER);
		
		
		// Panel4
		friends_name = new JTextArea(1, 8);
		friends_name.setText("Friend's Name");
		
		add = new JButton("Add");
		add.addActionListener(add_list);
		
		panel4.add(friends_name, BorderLayout.WEST);
		panel4.add(add, BorderLayout.CENTER);
		
		
		// Panel2
		friends_posts_label = new JLabel("Recent Posts by Friends");
		
		friends_posts = new JTextArea(10, 19);
		friends_posts.setEditable(false);
		friends_posts.setLineWrap(true);

		scroll = new JScrollPane(friends_posts);
		
		panel2.add(friends_posts_label, BorderLayout.WEST);
		panel2.add(scroll, BorderLayout.CENTER);
		
		
		// Panel5
		list_groups = new JList<>();
		DefaultListModel<String> group_model = new DefaultListModel<>();
		for ( Group g : groups ) {
			group_model.addElement(g.getName());
		}
		list_groups.setModel(group_model);
		
		subscribe = new JButton("Subscribe in Group");
		subscribe.addActionListener(group_list);
		
		panel5.add(list_groups, BorderLayout.WEST);
		panel5.add(subscribe, BorderLayout.CENTER);
		
		
		// Panel3
		sugg_friends_label = new JLabel("Suggested Friends");
		sugg_users = theUser.findSuggestedFriends();
		sugg_friends = new JList<>();
		DefaultListModel<String> sugg_friendsModel = new DefaultListModel<String>();
		for (User u : sugg_users) {
			
			sugg_friendsModel.addElement(u.getName());
		}
		sugg_friends.setModel(sugg_friendsModel);
		friends_posts.setText(theUser.friendsPosts());
		panel3.add(sugg_friends_label, BorderLayout.WEST);
		panel3.add(sugg_friends, BorderLayout.CENTER);
		
		
		// ContentPane
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS) );
		
		panel1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel2.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel3.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel4.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel5.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		panel.add(panel1);
		panel.add(panel4);
		panel.add(panel2);
		panel.add(panel5);
		panel.add(panel3);
		
		
		contentPane.add(panel);
		
		// Frame Settings
		frame.setSize(600, 600);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	// �������� ��� ��� ���������� POST
	class Post_Listener implements ActionListener {
		
		// �������� ��� ��������� ���� �� ������� �� button post
		// ��� �� ������������ �� post 

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			String text;
			Post new_post; 
			
			text = user_post.getText();
			new_post = new Post(LocalDateTime.now(), text, theUser);
		}
	}
	
	// �������� ��� ��� �������� USER �� GROUP
	class Group_Listener implements ActionListener {

		// ��������� ��� user ��� group ��� ������� ��� ��� �����.
		// �� ���������� ������� ��� ���� ����� ��� group ��� �� ����
		// � ����� ��� ����� ��� group ����������� ������
		@Override
		public void actionPerformed(ActionEvent arg0) {

			String selected_group  = list_groups.getSelectedValue();
			Group group = null;
			
			for ( Group g : groups ) {
				if ( g.getName().equals(selected_group) ) {
					group = g;
					break;
				}
			}
			
			if ( group != null ) {
				group.addInGroup(theUser);
			}
			else 
				JOptionPane.showMessageDialog(null,  "No group selected!");
			
		}
	}
	
	// �������� ��� ��� �������� ����� ���� ����� �� ���� ������ ��� USER
	class Add_Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			String selected_name = friends_name.getText();
			User user = null;
			boolean found = false;
			
			for ( User u : users ) {
				
				if ( u.getName().equals(selected_name) ) {
					found = true;
					user = u;
					break;
				}
			}
			
			if ( !found ) {
				JOptionPane.showMessageDialog(null,  "No such user found!");
			}
			else {
				
				if ( theUser.alreadyFriends(user) ) {
					JOptionPane.showMessageDialog(null,  "You are already friends!");
				}
				else {
					theUser.addFriend(user);
					System.out.println(user.getName() + " is added to your friends!");
				}
			}
				
		} 
		
	}
}
