package kSecrets;

public interface Document {

	String getDocName();
	String getSecurityLevel();
	String getDescription();
	String getManager();
	void setNewDescription(String newDescription);
	void getAccess(User user1);
	boolean hasAcess(String userID);
	void getAcess(User userID);
	int revokedTimes();
	int grantedTimes();
	public boolean isRevoked(String userID);
	void removeAcess(User userID);

	
}
