import java.io.Serializable;
import java.time.LocalDateTime;

public class Post implements Comparable<Post>, Serializable {

	// �����
	
	private LocalDateTime timestamp;  // � ���������� ��� ��� ��� ������������� �� post
	private String text;              // �� post
	private User theUser;             // � user ��� �� �����������
	
	@Override
	public int compareTo(Post other) {

		// ������ ��� ����� �� ��� ����� ���������� �� �������� ��� ������
		// ������� �� �� �� �� ������� ���� ����� ��� ������������ �� post
		// ����� ����, ���� � ��� ���� ������� ������
		
		if ( timestamp.isAfter(other.getTimestamp()))
			return -1;
		else if (timestamp.isBefore(other.getTimestamp()))
				return 1;
			 else 
				return 0;	
	}
	
	// �������������
	
	public Post(LocalDateTime timestamp, String text, User theUser) {
		
		// ���������� �� post ������������ ��� ����� ��� ���������� ���
		// ��������� �����
		
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

