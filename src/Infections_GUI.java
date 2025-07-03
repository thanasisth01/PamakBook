import java.awt.event.*;
import javax.swing.*;

public class Infections_GUI extends JFrame {

	// ΠΕΔΙΑ
	
	private JPanel panel;                // το panel στο οποίο τοποθετούνται τα στοιχεία
	
	private JTextArea infected_list;     // η περιοχή στην οποία εμφανίζεται ο προσβεβλημένος user 
	                                     // και η λίστα με τυχών προσβεβλημένους φίλους του
	private JButton login_page;          // button για επιστροφή στο αρχικό παράθυρο
	private User theUser;                // ο user για τον οποίο θα εμφανιστεί η λίστα με τους τυχών
	                                     // προσβεβλημένους φίλους του
	
	// ΚΑΤΑΣΚΕΥΑΣΤΗΣ
	
	public Infections_GUI(User aUser) {
		
		// καλεί την printInfectedUsers της κλάσης User (για τον User που κάλεσε αυτό το GUI)
		// για να εκτυπώσει στην περιοχή της λίστα με τους τυχών προσβεβλημένους user. Αν πατηθεί
		// το button login_page επιστρέφει στην αρχική οθόνη μέσω του εσωτερικού ακροατή που
		// δημιουργήθηκε
		
		theUser = aUser;
		
		JFrame frame = new JFrame("Πιθανή Μετάδοση Ιού");
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


