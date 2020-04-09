package kSecrets;

public interface KeepingSecrets {

	boolean hasUserID(String userId);

	void addClerk(String kind, String userId, String clearanceLevel);

	void addOfficer(String kind, String userId, String clearanceLevel);

	boolean isEmpty();

	Iterator listUsers();

	boolean hasDocumentUploaded(String userID, String document);

	public boolean isClearanceHighEnough(String userID, String secLvl);

	void uploadDoc(String documentName, String userID, String secLvl, String description);

	boolean isDocOfficial(String documentName, String managerID);

	String getDocSecurityLevel(String documentName, String managerID);

	void updateDescription(String documentName, String managerID, String newDescription);

	String getDescription(String documentName, String managerID);

	void getAcess(String documentName, String managerID, String grantedID);

	boolean hasAcess(String documentName, String managerID, String grantedID);
	
	boolean isRevoked(String documentName, String managerID, String grantedID);

	boolean isClearanceOfficial(String clearanceLevel);

	boolean isClerkUser(String userID);

	void getRevoked(String documentName, String managerID, String grantedID);
}
