package kSecrets;

public class DocumentClass implements Document {
	private String docName;
	private String manager;
	private String securityLevel;
	private String description;
	static final int DEFAULT_SIZE = 50;
	
	
	//ACESSOS
	private User[] acesses;
	private User[] revoked;
	private int counter;	
	private int counter_granted;
	private int counter_revoked;
	
	
	public DocumentClass(String docName, String manager, String securityLevel, String description) {
		this.docName = docName;
		this.manager = manager;
		this.securityLevel = securityLevel;
		this.description = description;
		
		
		//ACESSOS
		this.acesses = new User[DEFAULT_SIZE];
		this.revoked = new User[DEFAULT_SIZE];
		counter_granted = 0;
		counter_revoked = 0;
	}

	
	@Override
	public String getDocName() {
		return docName;
	}

	@Override
	public String getSecurityLevel() {
		return securityLevel;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getManager() {
		return manager;
	}
	
	@Override
	public void setNewDescription(String description) {
		this.description = description;
	}
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	//ACESSOS
	
	@Override
	public void getAcess(User userID) {
		acesses[counter] = userID;
		counter++;
		counter_granted++;
	}
	
	@Override
	public boolean hasAcess(String userID) {
		return searchIndexUser(userID)>=0; 
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
