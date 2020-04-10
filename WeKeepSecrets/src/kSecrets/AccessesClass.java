package kSecrets;

public class AccessesClass implements Accesses {
	private String managerID, grantingID, docName;
	
	static final int DEFAULT_SIZE = 50;
	private User[] acesses;
	private int counter;
	//ACESSOS
	private User[] revoked;
	private int counter_granted;
	private int counter_revoked;
	
	public AccessesClass(String docName, String managerID, String grantingID) {
		this.acesses = new Accesses[DEFAULT_SIZE];
		counter = 0;
		
		
	}
	@Override
	public void getAcess(Accesses userID) {
		acesses[counter] = userID;
		counter++;
	}
	
	@Override
	public boolean hasAcess(String userID) {
		return searchIndex(userID)>=0; 
	}
	
	private int searchIndex(String userID) {
		int i = 0;
		int result = -1;
		boolean found = false;
		while (i<counter && !found)
			if (acesses[i].getID().equals(userID))
				found = true;
			else
				i++;
		if (found) result = i;
		return result;
	}
	@Override
	public void getAcess(User userID) {
		acesses[counter] = userID;
		counter++;
		counter_granted++;
	}
	
	
	@Override
	public boolean isRevoked(String userID) {
		return searchIndexUser(userID)>=0; 
	}
	
	private int searchIndexUser(String userID) {
		int i = 0;
		int result = -1;
		boolean found = false;
		while (i<counter && !found)
			if (acesses[i].getID().equals(userID))
				found = true;
			else
				i++;
		if (found) result = i;
		return result;
	}
	
	@Override
	public void removeAcess(User userID) {
		revoked[counter_revoked] = userID;
		String user = userID.toString();
		
		int pos = searchIndexUser(user);
		
		for(int i=pos; i<counter-1; i++) {
			acesses[i] = acesses[i+1]; 
		}
		counter_revoked++;
		counter--;
	}

	@Override
	public int revokedTimes() {
		return this.counter_revoked;
	}

	@Override
	public int grantedTimes() {
		return this.counter_granted;
	}

	
	
	
	
	
	
	
	
	
}
