package kSecrets.Documents;

import kSecrets.Users.*;

public interface Document {

	String getDocName();

	String getSecurityLevel();

	String getDescription();

	String getManager();

	void setNewDescription(String newDescription);

	boolean hasAccess(User user);

	void grant(User userID);

	boolean isRevoked(User userID);

	void removeAccess(User userID);

	int revokedTimes();

	int grantedTimes();
}
