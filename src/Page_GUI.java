import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.util.*;
import javax.swing.*;

public class Page_GUI extends JFrame {

	// ΠΕΔΙΑ 
	
	private JPanel panel;                // το τελικό panel που περιέχει όλα τα άλλα
	private JPanel panel1;               // panel που περιέχει τα στοιχεία του user, ο button επιστροφής στην αρχική οθόνη, την 
	                                     // περιοχή για την συγγραφή του post και τo button για την δημοσίευσή του
	private JPanel panel2;               // panel που περιέχει το label και την περιοχή για τα post των φίλων και του user
	private JPanel panel3;               // panel που περιέχει το label και την λίστα με τους προτεινόμενους φίλους
	private JPanel panel4;               // panel που περιέχει τα στοιχεία για την εισαγωγή ονόματος φίλου
	private JPanel panel5;               // panel που περιέχει τα στοιχεία για την εισαγωγή σε group από λίστα

	private User theUser = null ;        // ο user για τον οποίο κλήθηκε η γραφική διασύνδεση
	private JTextArea name;              // περιοχή με το όνομα του user
	private JTextArea email;             // περιοχή με το email του user
	private ArrayList<User> users = null;      // λίστα με όλους τους users
	
	private JButton login_page;          // button για την επιστροφή στην αρχική οθόνη
	
	private JTextArea friends_name;      // περιοχή για την εισαγώγη ονόματος user για την δημιουργία φιλίας
	private JButton add;                 // δημιουργεί μία σχέση φιλίας μεταξύ των 2 user

	private ArrayList<Group> groups;     // λίστα με τα groups που υπάρχουν
	private JList<String> list_groups;           // λίστα με τα group στα οποία μπορεί να εισαχθεί ο user
	private JButton subscribe;           // button για την εισαγωγή σε group (αν πληρούνται σε ορισμένες οι προϋποθέσεις
	
	private JTextArea user_post;         // περιοχή που συντάσσει ο user το post του
	private JButton post;                // button για την δημοσίευση του post
	
	private JLabel friends_posts_label;  // label για την υπόδειξη ότι δεξιά βρίσκονται τα post των φίλων και του user
	private JTextArea friends_posts;     // περιοχή που εμφανίζονται τα post των φίλων και του user
	private JScrollPane scroll;          // για την περιήγηση στην περιοχή των post
	
	private JLabel sugg_friends_label;   // label για την υπόδειξη ότι δεξιά βρίσκονται οι προτεινόμενοι φίλοι του user
	private HashSet<User> sugg_users;    // HashSet με το οποίο βρίσκω τους προτεινόμενους φίλους
	private JList<String> sugg_friends;  // JList για την απεικόνιση της λίστας των προτεινόμενων φίλων

	// ΚΑΤΑΣΚΕΥΑΣΤΗΣ
	
	Page_GUI(User user_1,  ArrayList<User> users , ArrayList<Group> groups) {
		
		// δημιουργούνται 3 panel για την καλύτερη διάταξη των στοιχείων του πλαισίου με βάση 
		// το πρότυπο που θέλω να φτιάξω. Δημιουργείται, επίσης, εσωτερικός ακροατής για την 
		// επιστροφή στην αρχική οθόνη για το button login_page. Έπειτα δημιουργούνται τα στοιχεία
		// κατάλληλα για το GUI που θέλω και εισάγονται στην κατάλληλη θέση στο πλαίσιο.
		
		// GUI Data
		theUser = user_1;
		this.groups = groups;
		this.users = users;

		
		// Setting Window
		JFrame frame = new JFrame("Σελίδα Χρήστη");
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
	
	// ΑΚΡΟΑΤΗΣ ΓΙΑ ΤΗΝ ΔΗΜΙΟΥΡΓΙΑ POST
	class Post_Listener implements ActionListener {
		
		// ακροατής που περιμένει πότε θα πατηθεί το button post
		// για να δημιουργήσει το post 

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			String text;
			Post new_post; 
			
			text = user_post.getText();
			new_post = new Post(LocalDateTime.now(), text, theUser);
		}
	}
	
	// ΑΚΡΟΑΤΗΣ ΓΙΑ ΤΗΝ ΠΡΟΣΘΗΚΗ USER ΣΕ GROUP
	class Group_Listener implements ActionListener {

		// προσθέτει τον user στο group που επέλεξε από την λίστα.
		// Αν χρειάζεται έγκριση από άλλο μέλος του group για να μπει
		// ή είναι ήδη μέλος του group εμφανίζεται μήνυμα
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
	
	// ΑΚΡΟΑΤΗΣ ΓΙΑ ΤΗΝ ΠΡΟΣΘΗΚΗ ΦΙΛΟΥ ΣΤΗΝ ΛΙΣΤΑ ΜΕ ΤΟΥΣ ΦΙΛΟΥΣ ΤΟΥ USER
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
