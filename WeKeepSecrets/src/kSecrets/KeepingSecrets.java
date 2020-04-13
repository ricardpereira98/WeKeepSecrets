package kSecrets;

import kSecrets.Iterators.*;

/**
 * 
 * @author Nuno Costa 54620 && Jose Pereira 55204
 *
 */

public interface KeepingSecrets {
	/**
	 * 
	 * @param userId - the user's identifier
	 * @return true if there is a user with such ID in the array of users
	 */
	boolean hasUserID(String userId);

	/**
	 * Creates a new user of the kind clerk
	 * 
	 * @param kind           - the user's kind
	 * @param userID         - the user's identifier
	 * @param clearanceLevel - the user's clearance level
	 */
	void addClerk(String kind, String userID, String clearanceLevel);

	/**
	 * Creates a new user of the kind officer
	 * 
	 * @param kind           - the user's kind
	 * @param userID         - the user's identifier
	 * @param clearanceLevel - the user's clearance level
	 */
	void addOfficer(String kind, String userID, String clearanceLevel);

	/**
	 * 
	 * @return true if there are no users in the user's array, false otherwise
	 */
	boolean isEmpty();

	/**
	 * 
	 * @return an iterator of users
	 */
	UserIterator usersIterator();

	/**
	 * @Pre searchIndexUser(userID) >= 0
	 * @param userID       - A user's identifier
	 * @param documentName - The document's name
	 * @return true if the user has a document with such name, false otherwise
	 */
	boolean hasDocumentUploaded(String userID, String documentName);

	/**
	 * @Pre searchIndexUser(userID) >= 0
	 * @param userID - the user's identifier
	 * @param secLvl - document's security level
	 * @return true if the user has enough clearance relative to the document's
	 *         security level, false otherwise
	 */
	boolean isClearanceHighEnough(String userID, String secLvl);

	/**
	 * Uploads the document attached to a specific userID, and also add the document
	 * to the array of documents in the system
	 * 
	 * @Pre searchIndexUser(userID) >= 0
	 * @param documentName - The document's name
	 * @param userID       - The user's identifier
	 * @param secLvl       - The document's security level
	 * @param description  - The document's description
	 */
	void uploadDoc(String documentName, String userID, String secLvl, String description);

	/**
	 * @Pre searchIndexUser(userID) >= 0
	 * @param documentName - The document's name
	 * @param managerID    - the document's manager's name
	 * @return true if the document is an official document, false otherwise
	 */
	boolean isUserDocOfficial(String documentName, String managerID);

	/**
	 * @Pre searchIndexUser(userID) >= 0
	 * @param documentName - The document's name
	 * @param managerID    - The document's manager's id
	 * @return the security level pertaining to the manager's document
	 */
	String getDocSecurityLevel(String documentName, String managerID);

	/**
	 * Updates the document's description
	 * 
	 * @Pre searchIndexUser(userID) >= 0
	 * @param documentName   - The document's name
	 * @param managerID      - The manager's identifier
	 * @param newDescription - The document's new description
	 * @param updaterID      - The user who updates the document's description
	 */
	void updateDescription(String documentName, String managerID, String newDescription, String updaterID);

	/**
	 * @Pre searchIndexUser(userID) >= 0
	 * @param documentName - The document's name
	 * @param managerID    - The manager's ID
	 * @param readerID     - The reader's ID
	 * @return the description of the manager's document
	 */
	String getDescription(String documentName, String managerID, String readerID);

	/**
	 * 
	 * @param clearanceLevel - A string representing a clearance level
	 * @return <code>true</code> if the clearance level is <code>official</code>,
	 *         <code>false</code> otherwise
	 */
	boolean isClearanceOfficial(String clearanceLevel);

	/**
	 * 
	 * @param userID - The user's identifier
	 * @return true if the user is a clerk, false otherwise
	 */
	boolean isClerkUser(String userID);

	/**
	 * @Pre hasUserID(managerID) && hasUserID(otherUserID)
	 * 
	 * @param documentName - The document's name
	 * @param managerID    - The document's manager's identifier
	 * @param otherUserID  - The identifier of another user
	 * @return true if this other user has access to the document, false otherwise
	 */
	boolean hasAccess(String documentName, String managerID, String otherUserID);

	/**
	 * @Pre hasUserID(managerID) && hasUserID(otherUserID)
	 * 
	 * @param documentName - The document's name
	 * @param managerID    - The document's manager's identifier
	 * @param otherUserID  - The identifier of another user
	 * @return true if this user is revoked, false otherwise
	 */
	boolean isRevoked(String documentName, String managerID, String otherUserID);

	/**
	 * Grants this user access to the document
	 * 
	 * @Pre hasUserID(managerID) && hasUserID(otherUserID)
	 * 
	 * @param documentName - The document's name
	 * @param managerID    - The document's manager's identifier
	 * @param otherUserID  - The identifier of another user
	 */
	void grant(String documentName, String managerID, String otherUserID);

	/**
	 * 
	 * Revokes the access this user had on the document
	 * 
	 * @Pre hasUserID(managerID) && hasUserID(otherUserID)
	 * 
	 * @param documentName - The document's name
	 * @param managerID    - The document's manager's identifier
	 * @param otherUserID  - The identifier of another user
	 */
	void revoke(String documentName, String managerID, String otherUserID);

	/**
	 * 
	 * @return an iterator of documents that have been leaked
	 */
	DocumentIterator leakedDocsIterator();

	/**
	 * 
	 * @return an iterator of users that have granted access to documents to others
	 */
	UserIterator topGrantersIterator();

	/**
	 * 
	 * @return true if no documents have been leaked, false otherwise
	 */
	boolean isLeakedDocsEmpty();

	/**
	 * 
	 * @return true if there are fewer than 10 documents leaked, false otherwise
	 */
	boolean isFewerThan10DocLeaked();

}
