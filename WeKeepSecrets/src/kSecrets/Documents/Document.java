package kSecrets.Documents;

import kSecrets.Users.*;

/**
 * 
 * @author Nuno Costa 54620 && Jose Pereira 55204
 *
 */

public interface Document {
	/**
	 * 
	 * @return the document's name
	 */
	String getDocName();

	/**
	 * 
	 * @return the document's security level
	 */
	String getSecurityLevel();

	/**
	 * 
	 * @return the document's description
	 */
	String getDescription();

	/**
	 * 
	 * @return the document's manager
	 */
	String getManager();

	/**
	 * Changes the description of the document
	 * 
	 * @param newDescription - the document's new description
	 */
	void setNewDescription(String newDescription);

	/**
	 * @Pre user != null
	 * @param user - refers to a given user
	 * @return true is this user has access, false otherwise
	 */
	boolean hasAccess(User user);

	/**
	 * Grants access to a given user
	 * 
	 * @param user - refers to a user
	 */
	void grant(User user);

	boolean isRevoked(User user);

	void removeAccess(User user);

	int revokedTimes();

	int grantedTimes();

	boolean hasBeenGranted();
}
