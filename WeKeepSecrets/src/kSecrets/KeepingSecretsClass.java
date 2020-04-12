package kSecrets;

import kSecrets.Users.*;
import kSecrets.Iterators.*;

/**
 * 
 * @author Nuno Costa 54620 && Jose Pereira 55204
 *
 */

public class KeepingSecretsClass implements KeepingSecrets {

	// Constants
	private static final String TOPSECRET = "TOPSECRET";
	private static final String SECRET = "SECRET";
	private static final String CONFIDENTIAL = "CONFIDENTIAL";
	private static final String OFFICIAL = "OFFICIAL";
	private static final String CLERK = "CLERK";
	private static final int TOPSECRET_VALUE = 3;
	private static final int SECRET_VALUE = 2;
	private static final int CONFIDENTIAL_VALUE = 1;
	private static final int OFFICIAL_VALUE = 0;
	static final int DEFAULT_SIZE = 10;

	// Instance variables
	private User[] users;
	private int counterUsers;

	// Constructor of the top class - initiates the instance variables
	public KeepingSecretsClass() {
		counterUsers = 0;
		users = new User[DEFAULT_SIZE];
	}

	@Override
	public boolean hasUserID(String id) {
		return searchIndexUserID(id) >= 0;
	}

	@Override
	public void addClerk(String kind, String userID, String clearanceLevel) {
		if (isFull()) {
			resize();
		}
		users[counterUsers] = new Clerk(kind, userID, clearanceLevel);
		counterUsers++;
	}

	@Override
	public void addOfficer(String kind, String userID, String clearanceLevel) {
		if (isFull()) {
			resize();
		}
		users[counterUsers] = new Officer(kind, userID, clearanceLevel);
		counterUsers++;
	}

	@Override
	public boolean isEmpty() {
		return counterUsers == 0;
	}

	@Override
	public Iterator usersIterator() {
		return new IteratorClass(users, counterUsers);
	}

	@Override
	public boolean hasDocumentUploaded(String userID, String docName) {
		return users[searchIndexUserID(userID)].hasThisDoc(docName);
	}

	@Override
	public boolean isClearanceHighEnough(String userID, String secLvl) {
		return getUserClearanceValue(userID) >= getDocsSecurityValue(secLvl);
	}

	@Override
	public void uploadDoc(String documentName, String userID, String secLvl, String description) {
		users[searchIndexUserID(userID)].addDoc(documentName, userID, secLvl, description);
	}

	@Override
	public boolean isDocOfficial(String documentName, String userID) {
		return users[searchIndexUserID(userID)].getDocument(documentName).getSecurityLevel().toUpperCase()
				.equals(OFFICIAL);
	}

	@Override
	public String getDocSecurityLevel(String documentName, String userID) {
		return users[searchIndexUserID(userID)].getDocument(documentName).getSecurityLevel();
	}

	@Override
	public void updateDescription(String documentName, String managerID, String newDescription) {
		users[searchIndexUserID(managerID)].getDocument(documentName).setNewDescription(newDescription);
	}

	@Override
	public String getDescription(String documentName, String managerID) {
		return users[searchIndexUserID(managerID)].getDocument(documentName).getDescription();
	}

	@Override
	public boolean isClearanceOfficial(String clearanceLevel) {
		return OFFICIAL.equalsIgnoreCase(clearanceLevel);
	}

	@Override
	public boolean isClerkUser(String userID) {
		return getUserKind(userID).equalsIgnoreCase(CLERK);
	}

	@Override
	public boolean hasAccess(String documentName, String managerID, String otherUserID) {
		User aux = users[searchIndexUserID(otherUserID)];
		return users[searchIndexUserID(managerID)].getDocument(documentName).hasAccess(aux);

	}

	@Override
	public boolean isRevoked(String documentName, String managerID, String otherUserID) {
		User aux = users[searchIndexUserID(otherUserID)];
		return users[searchIndexUserID(managerID)].getDocument(documentName).isRevoked(aux);
	}

	@Override
	public void grant(String documentName, String managerID, String grantedID) {
		User aux = users[searchIndexUserID(grantedID)];
		users[searchIndexUserID(managerID)].getDocument(documentName).grant(aux);

	}

	@Override
	public void revoke(String documentName, String managerID, String grantedID) {
		User aux = users[searchIndexUserID(grantedID)];
		users[searchIndexUserID(managerID)].getDocument(documentName).removeAccess(aux);
	}

	// PRIVATE METHODS BELOW

	/**
	 * @Pre searchIndexUser(userID) >= 0
	 * @param userID - The user's identifier
	 * @return the user's kind
	 */
	private String getUserKind(String userID) {
		return users[searchIndexUserID(userID)].getKind();
	}

	/**
	 * Searches in the users array if there is a user whose ID matches the ID given
	 * by the parameter
	 * 
	 * @param id - the user's identifier
	 * @return the index of the user with the ID given by the parameter
	 */
	private int searchIndexUserID(String id) {
		int result = -1;
		boolean found = false;
		for (int i = 0; i < counterUsers && !found; i++) {
			if (users[i].getID().toUpperCase().equals(id.toUpperCase())) {
				found = true;
				result = i;
			}
		}
		return result;
	}

	/**
	 * 
	 * @return <code>true</code> if the number of users in the array is equal to the
	 *         array's length, <code>false</code> otherwise
	 */
	private boolean isFull() {
		return counterUsers == users.length;
	}

	/**
	 * Increases the size of the array of users
	 */
	private void resize() {
		User tmp[] = new User[2 * users.length];
		for (int i = 0; i < counterUsers; i++)
			tmp[i] = users[i];
		users = tmp;
	}

	/**
	 * @Pre hasUserID(userID)
	 * @param userID - The user's identifier
	 * @return the value of the user's clearance level
	 */
	private int getUserClearanceValue(String userID) {
		return Clearance.valueOf(users[searchIndexUserID(userID)].getClearanceLevel().toUpperCase()).getValue();
	}

	/**
	 * Compares the security level given by the parameter and assigns a respective
	 * security value to it
	 * 
	 * @param secLvl - the Document's security level
	 * @return a security value that depends on the security level of the document
	 */
	private int getDocsSecurityValue(String secLvl) {
		int value = OFFICIAL_VALUE;

		if (secLvl.toUpperCase().equals(OFFICIAL)) {
			return value;
		}

		else if (secLvl.toUpperCase().equals(CONFIDENTIAL)) {
			value = CONFIDENTIAL_VALUE;
		}

		else if (secLvl.toUpperCase().equals(SECRET)) {
			value = SECRET_VALUE;
		}

		else if (secLvl.toUpperCase().equals(TOPSECRET)) {
			value = TOPSECRET_VALUE;
		}

		return value;
	}
	

}
