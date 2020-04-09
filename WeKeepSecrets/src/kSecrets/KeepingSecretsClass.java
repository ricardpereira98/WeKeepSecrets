package kSecrets;

public class KeepingSecretsClass implements KeepingSecrets {
	private static final String TOPSECRET = "TOPSECRET";
	private static final String SECRET = "SECRET";
	private static final String CONFIDENTIAL = "CONFIDENTIAL";
	private static final String OFFICIAL = "OFFICIAL";
	private static final String OFFICER = "OFFICER";
	private static final String CLERK = "CLERK";
	private static final int TOPSECRET_VALUE = 3;
	private static final int SECRET_VALUE = 2;
	private static final int CONFIDENTIAL_VALUE = 1;
	private static final int OFFICIAL_VALUE = 0;
	private User[] users;
	private Document[] docs;
	static final int DEFAULT_SIZE = 50;
	private int counter;

	public KeepingSecretsClass() {
		counter = 0;
		users = new User[DEFAULT_SIZE];
		docs = new Document[DEFAULT_SIZE];
	}

	@Override
	public boolean hasUserID(String id) {
		return searchIndexUserID(id) >= 0;
	}

	@Override
	public void addClerk(String kind, String userId, String clearanceLevel) {
		if (isFull()) {
			resize();
		}
		users[counter] = new Clerk(kind, userId, clearanceLevel);
		counter++;
	}

	@Override
	public void addOfficer(String kind, String userId, String clearanceLevel) {
		if (isFull()) {
			resize();
		}
		users[counter] = new Officer(kind, userId, clearanceLevel);
		counter++;
	}

	@Override
	public boolean isEmpty() {
		return counter == 0;
	}

	@Override
	public Iterator listUsers() {
		return new IteratorClass(users, counter);
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
		return docs[searchIndexDoc(documentName, userID)].getSecurityLevel().toUpperCase().equals(OFFICIAL);
	}

	@Override
	public String getDocSecurityLevel(String documentName, String userID) {
		return docs[searchIndexDoc(documentName, userID)].getSecurityLevel();
	}

	@Override
	public void updateDescription(String documentName, String managerID, String newDescription) {
		docs[searchIndexDoc(documentName, managerID)].newDescription(newDescription);
	}

	@Override
	public String getDescription(String documentName, String managerID) {
		return docs[searchIndexDoc(documentName, managerID)].getDescription();
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
		int i = 0;
		int result = -1;
		boolean found = false;
		while (i < counter && !found)
			if (users[i].getID().toUpperCase().equals(id.toUpperCase()))
				found = true;
			else
				i++;
		if (found)
			result = i;
		return result;
	}

	/**
	 * 
	 * @param documentName
	 * @param userID
	 * @return the documentName of the user userID
	 */

	private int searchIndexDoc(String documentName, String userID) {
		int i = 0;
		int result = -1;
		boolean found = false;

		String indexDocsName = docs[i].getDocName().toUpperCase();
		String givenDocsName = documentName.toUpperCase();
		String indexDocsManager = docs[i].getManager().toUpperCase();
		String givenManager = userID.toUpperCase();

		while (i < counter && !found)
			if (indexDocsName.equals(givenDocsName) && indexDocsManager.equals(givenManager))
				found = true;
			else
				i++;
		if (found)
			result = i;
		return result;
	}

	private boolean isFull() {
		return counter == users.length;
	}

	private void resize() {
		User tmp[] = new User[2 * users.length];
		for (int i = 0; i < counter; i++)
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//ACESSOS
	
	@Override
	public boolean hasAcess(String documentName, String managerID, String grantedID) {
		return docs[searchIndexDoc(documentName, managerID)].hasAcess(grantedID);
	}

	@Override
	public boolean isRevoked(String documentName, String managerID, String grantedID) {
		return docs[searchIndexDoc(documentName, managerID)].isRevoked(grantedID);
	}
	
	@Override
	public void getAcess(String documentName, String managerID, String grantedID) {
		User user1 = users[searchIndexUserID(grantedID)];
		docs[searchIndexDoc(documentName, managerID)].getAccess(user1);
		users[searchIndexUserID(managerID)].grant();
	}

	@Override
	public void getRevoked(String documentName, String managerID, String grantedID) {
		User user1 = users[searchIndexUserID(grantedID)];
		docs[searchIndexDoc(documentName, managerID)].removeAcess(user1);
	}
	

}
