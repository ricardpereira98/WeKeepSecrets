package kSecrets;

public class AccessesClass implements Accesses {
	private String managerID, grantingID, docName;
	
	static final int DEFAULT_SIZE = 50;
	private Accesses[] acesses;
	private int counter;
	
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
	
	
	
	
	
	
	
	
	
	
}
