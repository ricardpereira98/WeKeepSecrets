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
	private static final String WRITE = "write";
	private static final String GRANT = "grant";
	private static final String REVOKE = "revoked";

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

	private ClassifiedDocument[] topLeaked;
	private int counterGrantedDocs;
	private int counterLeakedDocs;

	private Officer[] topGranters;
	private int counterGranters;
	private int numGranters;

	// Constructor of the top class - initiates the instance variables
	public KeepingSecretsClass() {
		users = new UserClass[DEFAULT_SIZE];
		counterUsers = 0;

		topGranters = new OfficerClass[DEFAULT_SIZE];
		counterGranters = 0;
		numGranters = 0;

		topLeaked = new ClassifiedDocumentsClass[DEFAULT_SIZE];
		counterGrantedDocs = 0;
		counterLeakedDocs = 0;

	}

	@Override
	public boolean hasUserID(String id) {
		return searchIndexUserID(id) >= 0;
	}

	@Override
	public void addUser(String kind, String userID, String clearanceLevel) {
		if (isFull()) {
			resize();
		}
		users[counterUsers] = new UserClass(kind, userID, clearanceLevel);
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
	public boolean isNumGrantersEmpty() {
		return numGranters == 0;
	}

	@Override
	public UserIterator usersIterator() {
		return new UserIteratorClass(users, counterUsers);
	}

	@Override
	public DocumentIterator leakedDocsIterator() {
		setTopLeakedDocs();
		if (counterLeakedDocs > 10) {
			counterLeakedDocs = 10;
		}
		return new DocumentIteratorClass(topLeaked, counterLeakedDocs);
	}

	@Override
	public UserIterator topGrantersIterator() {
		setTopGranters();
		if (counterGranters > 10) {
			counterGranters = 10;
		}
		return new UserIteratorClass(topGranters, counterGranters);
	}

	@Override
	public DocumentIterator listOfficialDocsIterator(String userID) {
		return users[searchIndexUserID(userID)].listOfficialDocsIterator();
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
	public void uploadOfficialDoc(String documentName, String userID, String secLvl, String description) {
		users[searchIndexUserID(userID)].addOfficialDoc(documentName, userID, secLvl, description);
	}

	@Override
	public void uploadClassifiedDoc(String documentName, String userID, String secLvl, String description) {
		users[searchIndexUserID(userID)].addClassifiedDoc(documentName, userID, secLvl, description);
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

	// not sure se e preciso
	public Document getDocument(String managerID, String documentName) {
		return users[searchIndexUserID(managerID)].getDocument(documentName);
	}

	@Override
	public void updateDescription(String documentName, String managerID, String newDescription, String updaterID) {

		User manager = users[searchIndexUserID(managerID)];
		Document doc = manager.getDocument(documentName);
		User aux = users[searchIndexUserID(updaterID)];

		doc.setNewDescription(newDescription);
		doc.increaseNumAccesses();
		doc.docHistory(updaterID, aux.getClearanceLevel(), WRITE, doc.getNumAccesses());

		Document classified = manager.getClassifiedDocument(documentName);
		classified.setNewDescription(newDescription);
		classified.increaseNumAccesses();
		classified.docHistory(updaterID, aux.getClearanceLevel(), WRITE, doc.getNumAccesses());

	}

	@Override
	public String getDescription(String documentName, String managerID, String readerID) {

		User manager = users[searchIndexUserID(managerID)];
		Document doc = manager.getDocument(documentName);
		User aux = users[searchIndexUserID(readerID)];

		doc.increaseNumAccesses();
		doc.docHistory(readerID, aux.getClearanceLevel(), READ, doc.getNumAccesses());

		if (doc.isOfficial()) {
			Document official = manager.getOfficialDocument(documentName);
			official.increaseNumAccesses();
			official.docHistory(readerID, aux.getClearanceLevel(), READ, doc.getNumAccesses());
		}

		else {
			Document classified = manager.getClassifiedDocument(documentName);
			classified.increaseNumAccesses();
			classified.docHistory(readerID, aux.getClearanceLevel(), READ, doc.getNumAccesses());

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
	public void grant(String documentName, String managerID, String readerID) {

		User manager = users[searchIndexUserID(managerID)];
		Document doc = manager.getDocument(documentName);
		User aux = users[searchIndexUserID(readerID)];

		doc.grant(aux);
		doc.docHistory(readerID, aux.getClearanceLevel(), GRANT, doc.getNumAccesses());
		doc.increaseTotalNumAccesses();
		manager.increaseGrantGiven();

		Document classified = manager.getClassifiedDocument(documentName);
		classified.grant(aux);
		classified.docHistory(readerID, aux.getClearanceLevel(), GRANT, doc.getNumAccesses());
		classified.increaseTotalNumAccesses();

		counterGrantedDocs++;
		numGranters++;
	}

	@Override
	public void revoke(String documentName, String managerID, String readerID) {

		User manager = users[searchIndexUserID(managerID)];
		User aux = users[searchIndexUserID(readerID)];
		Document doc = manager.getDocument(documentName);

		doc.removeAccess(aux);
		manager.increaseRevokesGiven();
		doc.increaseTotalNumAccesses();
		doc.docHistory(readerID, aux.getClearanceLevel(), REVOKE, doc.getNumAccesses());

		Document classified = manager.getClassifiedDocument(documentName);
		classified.removeAccess(aux);
		classified.docHistory(readerID, aux.getClearanceLevel(), REVOKE, doc.getNumAccesses());
		classified.increaseTotalNumAccesses();
	}

	@Override
	public boolean hasGrants() {
		boolean found = false;

		for (int i = 0; i < counterUsers && !found; i++) {
			User index = users[i];
			for (int j = 0; j < index.getDocsNum() && !found; j++) {
				Document doc = index.getDocument(j);
				if (doc.grantedTimes() > 0) {
					found = true;
				}
			}
		}
		return found;
	}

	@Override
	public boolean userHasOfficialDocs(String userID) {
		return users[searchIndexUserID(userID)].hasOfficialDocs();
	}

	@Override
	public boolean userHasClassifiedDocs(String userID) {
		return users[searchIndexUserID(userID)].hasClassifiedDocs();
	}

	@Override
	public DocumentIterator listClassifiedDocsIterator(String userID) {
		return users[searchIndexUserID(userID)].listClassifiedDocsIterator();
	}

	@Override
	public boolean isClearanceAppropriated(String userID) {
		String clearence = users[searchIndexUserID(userID)].getClearanceLevel().toUpperCase();

		return !clearence.equals(OFFICIAL);
	}

	@Override
	public boolean isReadWriteAccess(Accesses access) {
		boolean isRW = false;

		if (access.getAccessType().equalsIgnoreCase(READ) || access.getAccessType().equalsIgnoreCase(WRITE)) {
			isRW = true;
		}
		return isRW;
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
		ClassifiedDocument tmp[] = new ClassifiedDocumentsClass[2 * topLeaked.length];
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
		Officer tmp[] = new OfficerClass[2 * topGranters.length];
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

	private void setTopLeakedDocs() {
		resetTopLeaked();
		for (int i = 0; i < counterUsers; i++) {
			User index = users[i];
			for (int j = 0; j < index.getDocsNum(); j++) {
				Document doc = index.getDocument(j);
				if (doc.grantedTimes() > 0) {
					if (isFullTopLeaked()) {
						resizeTopLeaked();
					}
					topLeaked[counterLeakedDocs++] = (ClassifiedDocument) doc;
				}
			}
		}
		bubbleSortLeakedDocs();
		bubbleSortAlphabeticallyLeakedDocs();
	}

	private void setTopGranters() {
		resetTopGranters();
		for (int i = 0; i < counterUsers; i++) {
			if (users[i].getGrantsGiven() > 0) {
				if (isFullTopGranters())
					resizeTopGranters();
				topGranters[counterGranters++] = (Officer) users[i];
			}
		}
		bubbleSortOfficerGrants();
		bubbleSortAlphabeticallyOfficersWhoGranted();
	}

	private void resetTopLeaked() {
//		topLeaked = new DocumentClass[DEFAULT_SIZE];
		topLeaked = new ClassifiedDocumentsClass[DEFAULT_SIZE];
		counterLeakedDocs = 0;
	}

	private void resetTopGranters() {
//		topGranters = new UserClass[DEFAULT_SIZE];
		topGranters = new OfficerClass[DEFAULT_SIZE];
		counterGranters = 0;
	}

	/**
	 * Sorts the documents that have been leaked based on grant times
	 */
	private void bubbleSortLeakedDocs() {
		for (int i = 1; i < counterLeakedDocs; i++) {

			for (int j = counterLeakedDocs - 1; j >= i; j--) {

				if (topLeaked[j - 1].grantedTimes() < topLeaked[j].grantedTimes()) {
					ClassifiedDocument tmp = topLeaked[j - 1];
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
						ClassifiedDocument aux = topLeaked[j - 1];
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
					Officer tmp = topGranters[j - 1];
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

			for (int j = counterGranters - 1; j >= i; j--) {
				// orders alphabetically in case of tie
				if (topGranters[j - 1].getGrantsGiven() == topGranters[j].getGrantsGiven()) {
					if (topGranters[j - 1].getID().compareTo(topGranters[j].getID()) > 0) {
						Officer aux = topGranters[j - 1];
						topGranters[j - 1] = topGranters[j];
						topGranters[j] = aux;
					}
				}
			}
		}
	}

}
