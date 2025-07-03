import java.io.Serializable;

import javax.swing.JOptionPane;

public class ClosedGroup extends Group implements Serializable {
	
	// ΠΕΔΙΑ
	
	boolean first;

	// ΚΑΤΑΣΚΕΥΑΣΤΗΣ
	
	public ClosedGroup(String name, String description) {
		
		super(name, description);
		first = true;
	}
	
	// ΜΕΘΟΔΟΙ 
	
	public boolean addInGroup(User aUser) {
		
		// κάνει Override την μέθοδο addInGroup της υπερκλάσης Group.
		// Αν είναι το πρώτο μέλος του Closed Group τον δέχεται. Έπειτα,
		// αν ο επόμενος User έχει κοινό φίλο με κάποιον άλλον User, που είναι
		// ήδη μέλος του ClosedGroup τον εισάγει, διαφορετικά τον απορρίπτει.
		
		boolean same = false;
		boolean subscribed = false;
		
		if (first) {
			
			participants.add(aUser);
			aUser.addInGroup(this);
			first = false;
			subscribed = true;
		}
		else if ( !alreadyInGroup(aUser) ){
			
			same = searchSameFriend(aUser);
			if (same) {
				
				participants.add(aUser);
				aUser.addInGroup(this);
				subscribed = true;
			}
			else {
				
				JOptionPane.showMessageDialog(null, aUser.getName() + " needs a friend who is already subscribed in this group!");
				subscribed = false;
			}
		} 
		else
			JOptionPane.showMessageDialog(null, aUser.getName() + " is already subscribed in this group!");
		
		return subscribed;
	}
	
	public boolean searchSameFriend(User aUser) {
		
		// βρίσκει αν υπάρχει ένας κοινός φίλος του User που κάλεσαι την μέθοδο 
		// με τον User που δίνεται μέσω της παραμετρου aUser
		
		boolean same_friend = false;
		
		for ( User u : aUser.getFriends() ) {
			
			for ( User u2 : participants ) {
				
				if ( u.getName().equals(u2.getName()) ) {
					
					same_friend = true;
					break;
				}
			}
			
			if ( same_friend )
				break;
		}
		
		return same_friend;
	}
}

