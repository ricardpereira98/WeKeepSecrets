package kSecrets;

import kSecrets.Accesses.*;
import kSecrets.Documents.*;
import kSecrets.Users.*;
import kSecrets.Iterators.*;

public class KeepingSecretsClass implements KeepingSecrets {
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
	private User[] users;
	private int counterUsers;

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
	public Iterator listUsers() {
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

	// PRIVATE METHODS BELOW

	private String getUserKind(String userID) {
		return users[searchIndexUserID(userID)].getKind();
	}

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

	private boolean isFull() {
		return counterUsers == users.length;
	}

	private void resize() {
		User tmp[] = new User[2 * users.length];
		for (int i = 0; i < counterUsers; i++)
			tmp[i] = users[i];
		users = tmp;
	}

	private int getUserClearanceValue(String userID) {
		return Clearance.valueOf(users[searchIndexUserID(userID)].getClearanceLevel().toUpperCase()).getValue();
	}

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

	@Override
	//pre esta merda toda existe
	public boolean hasAccess(String documentName, String managerID, String grantedID) {
		//return docs[searchIndexDoc(documentName, managerID)].hasAcess(grantedID);
		
		User grantedUser = users[searchIndexUserID(grantedID)];
		return users[searchIndexUserID(managerID)].getDocument(documentName).hasAccess(grantedUser);
		
	}

	@Override
	public boolean isRevoked(String documentName, String managerID, String grantedID) {
		return docs[searchIndexDoc(documentName, managerID)].isRevoked(grantedID);
	}

	@Override
	public void grant(String documentName, String managerID, String grantedID) {
		User user1 = users[searchIndexUserID(grantedID)];
		//docs[searchIndexDoc(documentName, managerID)].getAccess(user1);
		users[searchIndexUserID(managerID)].getDocument(documentName).grant(grantedID);

	}

	@Override
	public void getRevoked(String documentName, String managerID, String grantedID) {
		User user1 = users[searchIndexUserID(grantedID)];
		docs[searchIndexDoc(documentName, managerID)].removeAcess(user1);
	}

}
