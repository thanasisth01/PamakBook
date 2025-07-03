import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class User implements Serializable {

	// ΠΕΔΙΑ

	private String name;              // όνομα User
	private String email;             // email User
	private ArrayList<User> friends;  // λίστα φίλων του User
	private boolean first_friend;     // true αν προσθέτω τον πρώτο φίλο στην λίστα, false αν όχι
	private ArrayList<Group> groups;  // λίστα Group στα οποία συμμετέχει ο User
	private TreeSet<Post> posts;      // δέντρο με τα post που έχει κάνει ο user
	
	// ΚΑΤΑΣΚΕΥΑΣΤΗΣ
	
    public User(String name, String email) {
		
		// αν το email έχει τις προϋποθέσεις που πρέπει, εισάγεται ο χρήστης,
		// διαφορετικά όχι.
    	
		if ( correctEmail(email) ) {
		  
		  this.name = name;
		  this.email = email;
		  first_friend = true;
		  friends = new ArrayList<>();
		  groups = new ArrayList<>();
		  posts = new TreeSet<>();
		}
		else 
			System.out.println("User " + name + " has not been created. Email format is not acceptable");
	}
    
	// SETTERS & GETTERS
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<User> getFriends() {
		return friends;
	}

	public void setFriends(ArrayList<User> friends) {
		this.friends = friends;
	}

	public boolean isFirst_friend() {
		return first_friend;
	}

	public void setFirst_friend(boolean first_friend) {
		this.first_friend = first_friend;
	}

	public TreeSet<Post> getPosts() {
		return posts;
	}

	public void setPosts(TreeSet<Post> posts) {
		this.posts = posts;
	}

    // ΜΕΘΟΔΟΙ
    
    public boolean correctEmail(String email) {
		
		// ελέγχει, αν το email περιέχει τα εξής βασικά στοιχεία:
		// ics/iis/dai, αν δίπλα από αυτά περιέχονται
		// τουλάχιστον 3 και το πολύ 5 νούμερα και το @uom.edu.gr
    	
		boolean check1 = false;
		boolean check2 = false;
		boolean check3 = false;
		boolean check4 = false;
		boolean check_uom = false;
		
		check_uom = email.contains("@uom.edu.gr");
		
		check1 = email.contains("ics");
		check2 = email.contains("iis");
		check3 = email.contains("dai");
		
		check4 = checkNumbersInEmail(email);
		
		if ( (check1 || check2 || check3) && check_uom && check4)
			return true;
		else 
			return false;
	}
	
	public boolean checkNumbersInEmail(String email) {
		
		// καλείται από την correctEmail για να ελέγχει τον αριθμό 
		// τον ψηφίων έπειτα από το ics/iis/dai. Επιστρέφει true, 
		// αν είναι ανάμεσα στο 3 και το 5, διαφορετικά επιστρέφει 
		// false.
		
		char[] str = new char[20];
		str = email.toCharArray();
		int count = 0;
		
		if ( !email.isEmpty() )
			
			for ( int i=3; i<8; i++ ) {
				
				if ( str[i] == '@' )
					break;
				else
					count++;
			}
		
		if ( count < 3 || count > 5 )
			return false;
		else
			return true;
	}

	public void addFriend(User aUser) {
		
		// προσθέτει το aUser στην λίστα με τους φίλους του χρήστη που καλεί την
		// μέθοδο, μόνο αν δεν είναι ήδη φίλοι ή αν είναι ο πρώτος φίλος που
		// προσθέτει.
		
		boolean already_friends = false;

		already_friends = alreadyFriends(aUser);
		
		if ( aUser != this )
		  if (!already_friends) {
		    
			  friends.add(aUser);
		      aUser.getFriends().add(this);
		      System.out.println(name + " and " + aUser.getName() + " are now friends!");
		  }
		
	}
	
	public boolean alreadyFriends(User aUser) {
		
		// επιστρέφει true, αν είναι φίλοι με τον χρήστη που έρχεται
		// ως παράμετρος, και false αν δεν είναι.
		
		boolean friends_ = false;
		
		if (first_friend) {
			 
			first_friend = false;
			friends_ = false;
		}
		else 
            for (User u : friends) {
        	
        	  if ( u.equals(aUser)) {
        		
        		  friends_ = true;
        		  break;
        	  }
            }
		
		return friends_;
	}
	
	public void printFriends() {
		
		// εκτυπώνει την λίστα με τους φίλους του User που καλεί
		// την μέθοδο.
			
		int i = 1;
		
		System.out.println("************************");
		System.out.println("Friends of " + name);
		System.out.println("************************");
		for (User u : friends) {
			
			System.out.println(i + ": " + u.getName());
			i++;
		}
		System.out.println("-----------------------");
	}
	
	public void addPostinList(Post new_post) {
		
		// εισαγωγή του της παραμέτρου new_post στo δέντρο με τα post
		
		posts.add(new_post);
	}
	
    public void addInGroup(Group g) {
		
		// προσθέτει σε λίστα με τα Group που συμμετέχει ο User, το
		// Group g που έρχεται ως παράμετρος.
    	
		groups.add(g);
	}
	
	public void printGroups() {
		
		// εκτυπώνει την λίστα με τα Group στα οποία συμμετέχει ο User
		
		int i = 1;
		
		System.out.println("**************************************");
		System.out.println("Groups that " + name + " has been enrolled in");
		System.out.println("**************************************");
		for (Group g : groups) {
			
			System.out.println(i + ": " + g.getName());
			i++;
		}
		System.out.println("--------------------------------------");
	}
	
	public ArrayList<User> findInfectedUsers(User aUser) {
			
		// βρίσκει τους Users που έχουν προσβεβληθεί από τον ιό
		// που μεταφέρει ο aUser και επιστρέφει μία λίστα.
		
		ArrayList<User> infected_users = new ArrayList<>();
		boolean already_in_list = false;
		
		for (User u : aUser.getFriends() )
			infected_users.add(u);
		
		for (User u : aUser.getFriends() ) {
			
			for ( User u1 : u.getFriends()) {
				
				if (!u1.equals(aUser) ) {
				 
				  already_in_list = false;
				  for ( User u2 : infected_users ) {
				  
				    if (u2.equals(u1) ) {
					 
					    already_in_list = true;
					    break;
			        }
				  }   
				  if ( !already_in_list )
					infected_users.add(u1);
				}
			}
			
		}
			
		return infected_users;
	}
	
	public String printInfectedUsers() {
		
		// εκτυπώνει την λίστα με τους προσβεβλημένους Users που έχει
		// επιστρεφεί από την κλήση της μεθόδου findInfectedUsers
		
		ArrayList<User> infected_users = new ArrayList<>();
		String text = "";
		String names = "";
		
		infected_users = findInfectedUsers(this);
		
		text = "*********************************************************************************"
				+ "\n" + name + " has been infected. The following users have to be "
						+ "tested\n*********************************************************************************\n";
		for ( User u : infected_users ) {
			
			names = names + u.getName() + "\n";
		}
		
		text = text + names;
		return text;
	}
	
	@Override
	public boolean equals (Object object) {
		
		User aUser = (User)object;
		
		if ( this.name.equals(aUser.getName()) && this.email.equals(aUser.getEmail()) ) 
		  return true;
	    else 
	      return false;
	}
	
	public HashSet<User> findSuggestedFriends() {
		
		// βρίσκει τους προτεινόμενους φίλους του user που την κάλεσε με βάση τους φίλους
		// του και επιστρέφει ένα HashSet για να μην υπάρχουν διπλότυπες εγγραφές
		
		HashSet<User> sugg_list = new HashSet<>();
		
		for ( User f1 : friends ) {
			
			for ( User f2 : f1.getFriends() ) {
				
				if ( f2 != this && !this.alreadyFriends(f2) ) {
					
					sugg_list.add(f2);
				}
			}
		}
		
		return sugg_list;
	}
	
	public String friendsPosts() {
		
		// επιστρέφει ένα String με όλα τα Post των φίλων και του ίδιου του user
		
		TreeSet<Post> all_the_posts = new TreeSet<>();
		String us = "";
		String date = "";
		String text = "";
		String final_post = "";
		String whole_post = "";
	    DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

	    for (Post p : posts) {
	    	
	    	all_the_posts.add(p);
	    }
	    
		for (User u: friends) {
			
			for (Post p : u.getPosts()) {
				
				all_the_posts.add(p);
			}
		}
		
		for (Post p : all_the_posts) {
			
			us = p.getTheUser().getName();
			date = p.getTimestamp().format(myFormat);
		    text = p.getText();
			whole_post = us + ", " + date + ", " + text + "\n";
			final_post += whole_post;
		}
		
		return final_post;	
}


    public ArrayList<User> commonFriends(User aUser) {
	
	    // επιστρέφει μία λίστα με τους κοινούς φίλους μεταξύ του User που
	    // καλεί την μέθοδο και τον aUser που δίνεται ως παράμετρος.
	
	    ArrayList<User> common_friends = new ArrayList<>();

	   for ( User user1 : friends ) {
		
		   for ( User user2 : aUser.getFriends() ) {
			
			   if ( user1.equals(user2) ) {
				
				   common_friends.add(user2);
				   break;
			   }
		   }
	    }
	
	    return common_friends;
     }

    public void printCommonFriends(User aUser) {
	
	   // εκτυπώνει την λίστα με τους κοινούς φίλους μεταξύ του User που
	   // καλεί την μέθοδο και τον aUser που δίνεται ως παράμετρος.
	
	   ArrayList<User> common_friends = new ArrayList<>();
	   int i = 1;
	
	   common_friends = commonFriends(aUser);
	
   	   System.out.println("**************************************");
	   System.out.println("Common friends of " + name + " and " + aUser.getName());
       System.out.println("**************************************");
       for (User u : common_friends) {
    	
    	   System.out.println(i + ": " + u.getName());
    	   i++;
       }
       System.out.println("--------------------------------------");
    }   

}

