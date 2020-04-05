package kSecrets;

public class KeepingSecretsClass implements KeepingSecrets {
	private static final String TOPSECRET = "TOPSECRET";
	private static final String SECRET = "SECRET";
	private static final String CONFIDENTIAL = "CONFIDENTIAL";
	private static final String OFFICIAL = "OFFICIAL";
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

	public boolean hasUserID(String id) {
		return searchIndexUserID(id) >= 0;
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

	private boolean isFull() {
		return counter == users.length;
	}

	private void resize() {
		User tmp[] = new User[2 * users.length];
		for (int i = 0; i < counter; i++)
			tmp[i] = users[i];
		users = tmp;
	}

	public void addClerk(String kind, String userId, String clearanceLevel) {
		if (isFull()) {
			resize();
		}
		users[counter] = new Clerk(kind, userId, clearanceLevel);
		counter++;
	}

	public void addOfficer(String kind, String userId, String clearanceLevel) {
		if (isFull()) {
			resize();
		}
		users[counter] = new Officer(kind, userId, clearanceLevel);
		counter++;
	}

	public boolean isEmpty() {
		return counter == 0;
	}

	public Iterator listUsers() {
		return new IteratorClass(users, counter);
	}

	@Override
	public boolean hasDocumentUploaded(String userID, String docName) {
		return users[searchIndexUserID(userID)].hasThisDoc(docName);
	}

	@Override
	public int getUserClearanceLvl(String userID) {
		return Clearance.valueOf(users[searchIndexUserID(userID)].getClearanceLevel()).getValue();
	}

	@Override
	public int getDocsSecurityValue(String secLvl) {
		int value = OFFICIAL_VALUE;

		if (secLvl.toUpperCase().equals(OFFICIAL)) {
			return value;
		} else {
			if (secLvl.toUpperCase().equals(CONFIDENTIAL)) {
				value = CONFIDENTIAL_VALUE;
			} else {
				if (secLvl.toUpperCase().equals(SECRET)) {
					value = SECRET_VALUE;
				} else {
					if (secLvl.toUpperCase().equals(TOPSECRET)) {
						value = TOPSECRET_VALUE;
					}
				}

			}
		}
		return value;
	}

	@Override
	public void uploadDoc(String documentName, String userID, String secLvl, String description) {
		users[searchIndexUserID(userID)].addDoc(documentName, userID, secLvl, description);

	}

}
