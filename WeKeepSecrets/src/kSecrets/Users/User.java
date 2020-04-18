package kSecrets.Users;

import kSecrets.Documents.*;
import kSecrets.Iterators.DocumentIterator;

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
	void increaseGrantGiven();

	/**
	 * 
	 * @return number of times a user has given a grant
	 */
	int getGrantsGiven();
	/**
	 * 
	 * @return an iterator of a user's documents
	 */
	DocumentIterator docsIterator();

	int getDocsNum();

	void increaseRevokesGiven();

	int getRevokesGiven();

	boolean hasOfficialDocs();

	boolean hasClassifiedDocs();

	void addOfficialDoc(String documentName, String userID, String secLvl, String description);

	void addClassifiedDoc(String docName, String manager, String securityLevel, String description);

	Document getOfficialDocument(String docName);

	Document getClassifiedDocument(String docName);

	Document getDocument(int index);

	DocumentIterator listOfficialDocsIterator();

	DocumentIterator listClassifiedDocsIterator();

	
}
