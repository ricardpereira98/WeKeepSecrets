package kSecrets;

import kSecrets.Users.*;
import kSecrets.Iterators.*;
import kSecrets.Documents.*;
import kSecrets.Accesses.*;

/**
 * 
 * @author Nuno Costa 54620 && Jose Pereira 55204
 *
 */

public class KeepingSecretsClass implements KeepingSecrets {

	private static final String READ = "read";
	private static final String REVOKE = "revoke";
	private static final String GRANT = "grant";
	private static final String WRITE = "write";
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

	private Document[] docs;
	private int counterDocs;

	private Document[] topLeaked;
	private int counterGrantedDocs;
	private int counterLeakedDocs;

	private User[] topGranters;
	private int counterGranters;

	private Accesses[] officialAccesses;
	private int counterOfficialDocs;

	private Accesses[] classifiedAccesses;
	private int counterClassifiedDocs;

	// Constructor of the top class - initiates the instance variables
	public KeepingSecretsClass() {
		users = new User[DEFAULT_SIZE];
		counterUsers = 0;

		topGranters = new User[DEFAULT_SIZE];
		counterGranters = 0;

		docs = new Document[DEFAULT_SIZE];
		counterDocs = 0;

		topLeaked = new Document[DEFAULT_SIZE];
		counterGrantedDocs = 0;
		counterLeakedDocs = 0;

		officialAccesses = new Accesses[DEFAULT_SIZE];
		counterOfficialDocs = 0;

		classifiedAccesses = new Accesses[DEFAULT_SIZE];
		counterClassifiedDocs = 0;
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
	public boolean isGrantedDocsEmpty() {
		return counterGrantedDocs == 0;
	}

	@Override
	public UserIterator usersIterator() {
		return new UserIteratorClass(users, counterUsers);
	}

	@Override
	public DocumentIterator leakedDocsIterator() {
		setTopLeakedDocs();
		return new DocumentIteratorClass(topLeaked, counterLeakedDocs);
	}

	@Override
	public UserIterator topGrantersIterator() {
		setTopGranters();
		return new UserIteratorClass(topGranters, counterGranters);
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
		if (isFullDocs())
			resizeDocs();
		docs[counterDocs++] = new DocumentClass(documentName, userID, secLvl, description);
	}

	@Override
	public boolean isUserDocOfficial(String documentName, String userID) {
		return users[searchIndexUserID(userID)].getDocument(documentName).getSecurityLevel().toUpperCase()
				.equals(OFFICIAL);
	}

	@Override
	public String getDocSecurityLevel(String documentName, String userID) {
		return users[searchIndexUserID(userID)].getDocument(documentName).getSecurityLevel();
	}

	@Override
	public void updateDescription(String documentName, String managerID, String newDescription, String updaterID) {
		Document doc = users[searchIndexUserID(managerID)].getDocument(documentName);
		User user = users[searchIndexUserID(updaterID)];
		Document kSecretsDoc = docs[searchIndexDocs(managerID, documentName)];

		doc.setNewDescription(newDescription);
		doc.increaseNumAccesses();
		

		if (isFullClassifiedAccesses())
			resizeClassifiedAccesses();
		kSecretsDoc.increaseNumAccesses();
		classifiedAccesses[counterClassifiedDocs++] = new AccessesClass(doc.getDocName(), doc.getSecurityLevel(),
				doc.getNumAccesses(), updaterID, user.getClearanceLevel(), WRITE); 
	

	}
	

	@Override
	public String getDescription(String documentName, String managerID, String readerID) {
		Document doc = users[searchIndexUserID(managerID)].getDocument(documentName);
		User user = users[searchIndexUserID(readerID)];

		doc.increaseNumAccesses();
		

		if (isUserDocOfficial(documentName, managerID)) {

			if (isFullOfficialAccesses())
				resizeOfficialAccesses();

			for (int i = countOfficialDocs(); i > 0; i--) {

				officialAccesses[i + 1] = officialAccesses[i];
			}

			officialAccesses[0] = new AccessesClass(doc.getDocName(), doc.getNumAccesses(), readerID,
					user.getClearanceLevel());
		}

		else {
			if (isFullClassifiedAccesses())
				resizeClassifiedAccesses();
			classifiedAccesses[counterClassifiedDocs++] = new AccessesClass(doc.getDocName(), doc.getSecurityLevel(),
					doc.getNumAccesses(), readerID, user.getClearanceLevel(), READ);
		}

		return doc.getDescription();

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
	public void grant(String documentName, String managerID, String user) {
		User aux = users[searchIndexUserID(user)];
		User manager = users[searchIndexUserID(managerID)];
		Document doc = manager.getDocument(documentName);
		

		doc.grant(aux);
		manager.grantGiven();
		doc.increaseNumAccesses();

		if (isFullClassifiedAccesses())
			resizeClassifiedAccesses();
		classifiedAccesses[counterClassifiedDocs++] = new AccessesClass(doc.getDocName(), doc.getSecurityLevel(),
				doc.getNumAccesses(), user, aux.getClearanceLevel(), GRANT);
		counterGrantedDocs++;
		docs[searchIndexDocs(managerID, documentName)].increaseGrantedTimes();

	}

	private int searchIndexDocs(String managerID, String docName) {
		int result = -1;
		boolean found = false;
		for (int i = 0; i < counterDocs && !found; i++) {
			if (docs[i].getManager().equalsIgnoreCase(managerID) && docs[i].getDocName().equalsIgnoreCase(docName)) {
				found = true;
				result = i;
			}
		}
		return result;
	}

	@Override
	public void revoke(String documentName, String managerID, String user) {
		User aux = users[searchIndexUserID(user)];
		Document doc = users[searchIndexUserID(managerID)].getDocument(documentName);

		doc.removeAccess(aux);
		doc.increaseNumAccesses();
		//Document kSecretsDoc = docs[searchIndexDocs(managerID, documentName)];

		if (isFullClassifiedAccesses())
			resizeClassifiedAccesses();
		
		docs[searchIndexDocs(managerID, documentName)].increaseRevokedTimes();
		classifiedAccesses[counterClassifiedDocs++] = new AccessesClass(doc.getDocName(), doc.getSecurityLevel(),
				doc.getNumAccesses(), user, aux.getClearanceLevel(), REVOKE);

	}

	@Override
	public boolean isFewerThan10DocLeaked() {
		return counterLeakedDocs < 10;
	}

	private void setTopLeakedDocs() {

		for (int i = 0; i < counterDocs; i++) {
			if (docs[i].grantedTimes() > 0) {
				if (isFullTopLeaked())
					resizeTopLeaked();
				topLeaked[counterLeakedDocs++] = docs[i];
			}
		}

		bubbleSortLeakedDocs();
		bubbleSortAlphabeticallyLeakedDocs();
	}

	private void setTopGranters() {
		for (int i = 0; i < counterUsers; i++) {
			if (users[i].getGrantsGiven() > 0) {

				if (isFullTopGranters())
					resizeTopGranters();
				topGranters[counterGranters++] = users[i];
			}
		}
		bubbleSortOfficerGrants();
		bubbleSortAlphabeticallyOfficersWhoGranted();
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
	 * 
	 * @return <code>true</code> if the number of leaked documents in the array is
	 *         equal to the array's length, <code>false</code> otherwise
	 */
	private boolean isFullTopLeaked() {
		return counterLeakedDocs == topLeaked.length;
	}

	/**
	 * Increases the size of the array of top leaked documents
	 */
	private void resizeTopLeaked() {
		Document tmp[] = new Document[2 * topLeaked.length];
		for (int i = 0; i < counterLeakedDocs; i++)
			tmp[i] = topLeaked[i];
		topLeaked = tmp;
	}

	/**
	 * 
	 * @return <code>true</code> if the number of top granters in the array is equal
	 *         to the array's length, <code>false</code> otherwise
	 */
	private boolean isFullTopGranters() {
		return counterGranters == topGranters.length;
	}

	/**
	 * Increases the size of the array of top leaked documents
	 */
	private void resizeTopGranters() {
		User tmp[] = new User[2 * topGranters.length];
		for (int i = 0; i < counterGranters; i++)
			tmp[i] = topGranters[i];
		topGranters = tmp;
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

	/**
	 * 
	 * @return true if the number of documents in the array is equal to the array's
	 *         length, false otherwise
	 */
	private boolean isFullDocs() {
		return counterDocs == docs.length;
	}

	/**
	 * Increases the size of the array of documents
	 */
	private void resizeDocs() {
		Document tmp[] = new Document[2 * docs.length];
		for (int i = 0; i < counterDocs; i++)
			tmp[i] = docs[i];
		docs = tmp;
	}

	/**
	 * 
	 * @return true if the number of official accesses in the array is equal to the
	 *         array's length, false otherwise
	 */
	private boolean isFullOfficialAccesses() {
		return counterOfficialDocs == officialAccesses.length;
	}

	/**
	 * Increases the size of the array of official accesses
	 */
	private void resizeOfficialAccesses() {
		Accesses tmp[] = new AccessesClass[2 * officialAccesses.length];
		for (int i = 0; i < counterOfficialDocs; i++)
			tmp[i] = officialAccesses[i];
		officialAccesses = tmp;
	}

	/**
	 * 
	 * @return true if the number of classified accesses in the array is equal to
	 *         the array's length, false otherwise
	 */
	private boolean isFullClassifiedAccesses() {
		return counterClassifiedDocs == classifiedAccesses.length;
	}

	/**
	 * Increases the size of the array of classified accesses
	 */
	private void resizeClassifiedAccesses() {
		Accesses tmp[] = new AccessesClass[2 * classifiedAccesses.length];
		for (int i = 0; i < counterClassifiedDocs; i++)
			tmp[i] = classifiedAccesses[i];
		classifiedAccesses = tmp;
	}

	/**
	 * Sorts the documents that have been leaked based on grant times
	 */
	private void bubbleSortLeakedDocs() {
		for (int i = 1; i < counterLeakedDocs; i++) {

			for (int j = counterLeakedDocs - 1; j >= i; j--) {

				if (topLeaked[j - 1].grantedTimes() < topLeaked[j].grantedTimes()) {
					Document tmp = topLeaked[j - 1];
					topLeaked[j - 1] = topLeaked[j];
					topLeaked[j] = tmp;
				}

			}
		}
	}

	/**
	 * Sorts the documents lexicographically
	 */
	private void bubbleSortAlphabeticallyLeakedDocs() {

		for (int i = 1; i < counterLeakedDocs; i++) {

			for (int j = counterLeakedDocs - 1; j >= i; j--) {
				// orders alphabetically in case of tie
				if (topLeaked[j - 1].grantedTimes() == topLeaked[j].grantedTimes()) {
					if (topLeaked[j - 1].getDocName().compareToIgnoreCase(topLeaked[j].getDocName()) > 0) {
						Document aux = topLeaked[j - 1];
						topLeaked[j - 1] = topLeaked[j];
						topLeaked[j] = aux;
					}
				}
			}
		}
	}

	/**
	 * Sorts the officers who have granted access to documents by the number of
	 * grants given
	 */
	private void bubbleSortOfficerGrants() {
		for (int i = 1; i < counterGranters; i++) {

			for (int j = counterGranters - 1; j >= i; j--) {
				// orders based on grant times
				if (topGranters[j - 1].getGrantsGiven() < topGranters[j].getGrantsGiven()) {
					User tmp = topGranters[j - 1];
					topGranters[j - 1] = topGranters[j];
					topGranters[j] = tmp;
				}

			}
		}
	}

	/**
	 * Sorts the officers who have granted access to documents lexicographically
	 */
	private void bubbleSortAlphabeticallyOfficersWhoGranted() {

		for (int i = 1; i < counterGranters; i++) {

			for (int j = --counterGranters; j >= i; j--) {
				// orders alphabetically in case of tie
				if (topGranters[j - 1].getGrantsGiven() == topGranters[j].getGrantsGiven()) {
					if (topGranters[j - 1].getID().compareTo(topGranters[j].getID()) > 0) {
						User aux = topGranters[j - 1];
						topGranters[j - 1] = topGranters[j];
						topGranters[j] = aux;
					}
				}
			}
		}
	}

	private int countClassifiedDocs() {
		for (int i = 0; i < counterDocs; i++) {
			if (!docs[i].getSecurityLevel().equals(OFFICIAL)) {
				counterClassifiedDocs++;
			}
		}
		return counterClassifiedDocs;
	}

	private int countOfficialDocs() {
		for (int i = 0; i < counterDocs; i++) {
			if (docs[i].getSecurityLevel().equals(OFFICIAL)) {
				counterOfficialDocs++;
			}
		}
		return counterOfficialDocs;
	}
}
