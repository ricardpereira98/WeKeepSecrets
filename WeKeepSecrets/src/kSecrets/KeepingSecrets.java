package kSecrets;

public interface KeepingSecrets {

	boolean hasUserID(String userId);

	void addClerk(String kind, String userId, String clearanceLevel);

	void addOfficer(String kind, String userId, String clearanceLevel);

	boolean isEmpty();

	Iterator listUsers();

	boolean hasDocumentUploaded(String documentName);

	int getUserClearanceLvl(String userID);

	int getDocsSecurityValue(String secLvl);

	void uploadDoc(String documentName, String userID, String secLvl, String description);
}
