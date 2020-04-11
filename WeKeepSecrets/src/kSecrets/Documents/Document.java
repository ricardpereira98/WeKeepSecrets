package kSecrets.Documents;

import kSecrets.Users.*;

public interface Document {

	String getDocName();

	String getSecurityLevel();

	String getDescription();

	String getManager();

	void setNewDescription(String newDescription);

	boolean hasAccess(User user);

	void grant(String userID);
}
