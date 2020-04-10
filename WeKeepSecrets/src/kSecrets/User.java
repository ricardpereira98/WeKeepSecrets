package kSecrets;

public interface User {

	String getID();

	String getKind();

	String getClearanceLevel();

	void addDoc(String docName, String userID, String securityLevel, String description);

	boolean hasThisDoc(String docName, String userID);
	
	Document getDocument(String docName, String userID);

	void grant();

}
