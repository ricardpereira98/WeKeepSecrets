package kSecrets.Users;

import kSecrets.Documents.*;

public interface User {

	String getID();

	String getKind();

	String getClearanceLevel();

	void addDoc(String docName, String userID, String securityLevel, String description);

	boolean hasThisDoc(String docName);

	Document getDocument(String docName);

	void grant();

	String getType();
}
