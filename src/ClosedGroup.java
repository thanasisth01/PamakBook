import java.io.Serializable;

import javax.swing.JOptionPane;

public class ClosedGroup extends Group implements Serializable {
	
	// �����
	
	boolean first;

	// �������������
	
	public ClosedGroup(String name, String description) {
		
		super(name, description);
		first = true;
	}
	
	// ������� 
	
	public boolean addInGroup(User aUser) {
		
		// ����� Override ��� ������ addInGroup ��� ���������� Group.
		// �� ����� �� ����� ����� ��� Closed Group ��� �������. ������,
		// �� � �������� User ���� ����� ���� �� ������� ����� User, ��� �����
		// ��� ����� ��� ClosedGroup ��� �������, ����������� ��� ����������.
		
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
		
		// ������� �� ������� ���� ������ ����� ��� User ��� ������� ��� ������ 
		// �� ��� User ��� ������� ���� ��� ���������� aUser
		
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

