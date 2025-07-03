import java.io.Serializable;
import java.time.LocalDateTime;

public class Post implements Comparable<Post>, Serializable {

	// ΠΕΔΙΑ
	
	private LocalDateTime timestamp;  // η ημερομηνία και ώρα που δημιουργήθηκε το post
	private String text;              // το post
	private User theUser;             // ο user που το δημιούργησε
	
	@Override
	public int compareTo(Post other) {

		// αλλάζω τον τρόπο με τον οποίο εισάγονται τα στοιχεία στο δέντρο
		// ανάλογα με το αν το χρονικό όριο είναι που συγκρίνονται τα post
		// είναι πριν, μετά ή την ίδια χρονική στιγμή
		
		if ( timestamp.isAfter(other.getTimestamp()))
			return -1;
		else if (timestamp.isBefore(other.getTimestamp()))
				return 1;
			 else 
				return 0;	
	}
	
	// ΚΑΤΑΣΚΕΥΑΣΤΗΣ
	
	public Post(LocalDateTime timestamp, String text, User theUser) {
		
		// δημιουργεί το post τοποθετώντας τις τιμές των παραμέτρων στα
		// κατάλληλα πεδία
		
		this.timestamp = timestamp;
		this.text = text;
		this.theUser = theUser;
		theUser.addPostinList(this);
	}

	// SETTERS & GETTERS
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getTheUser() {
		return theUser;
	}

	public void setTheUser(User theUser) {
		this.theUser = theUser;
	}
	
}

