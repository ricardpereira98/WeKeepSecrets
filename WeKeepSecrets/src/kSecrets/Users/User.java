package kSecrets.Users;

import kSecrets.Documents.*;

/**
 * 
 * @author Nuno Costa 54620 && Jose Pereira 55204
 *
 */

public interface User {

	/**
	 * 
	 * @return the user's identifier
	 */
	String getID();

	/**
	 * 
	 * @return the user's kind
	 */
	String getKind();

	/**
	 * 
	 * @return the user's clearance level
	 */
	String getClearanceLevel();

	/**
	 * If the array of documents is full, firstly the array is resized then the
	 * document is added
	 * 
	 * @param docName       - refers to the name of the document
	 * @param userID        - refers to the manager of the document
	 * @param securityLevel - refers to the security level of the document
	 * @param description   - refers to the description of the document
	 */
	void addDoc(String docName, String userID, String securityLevel, String description);

	/**
	 * 
	 * @param docName - refers to the name of the document
	 * @return true if a document with the given docName exists in the array of
	 *         documents
	 */
	boolean hasThisDoc(String docName);

	/**
	 * 
	 * @param docName - refers to the name of the document
	 * @return the document with the given docName
	 */
	Document getDocument(String docName);

	/**
	 * 
	 * @return the type of user
	 */
	String getType();

	/**
	 * Increases the number of times this user has granted access to another user
	 */
	void grantGiven();

	/**
	 * 
	 * @return number of times a user has given a grant
	 */
	int getGrantsGiven();
}
