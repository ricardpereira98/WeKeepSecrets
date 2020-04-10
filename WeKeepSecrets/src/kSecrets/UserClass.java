package kSecrets;

public class UserClass implements User {
	private static final int DEFAULT_SIZE = 10;
	private String id;
	private String kind;
	private String clearanceLevel;
	private int grantingTimes;
	private Document[] docs;
	private int counterDocs;

	public UserClass(String kind, String id, String clearanceLevel) {
		this.kind = kind;
		this.id = id;
		this.clearanceLevel = clearanceLevel;
		grantingTimes = 0;
		counterDocs = 0;
		docs = new Document[DEFAULT_SIZE];
	}

	/**
	 * @return the user's identifier
	 */
	public String getID() {
		return id;
	}

	/**
	 * @return the user's kind
	 */
	public String getKind() {
		return kind.toLowerCase();
	}

	public String getClearanceLevel() {
		return clearanceLevel;
	}

	public void addDoc(String docName, String manager, String securityLevel, String description) {
		if (isFullDocs()) {
			resizeDocs();
		}
		docs[counterDocs++] = new DocumentClass(docName, manager, securityLevel, description);

	}

	public boolean hasThisDoc(String docName, String userID) {
		return searchIndexDoc(docName, userID) >= 0;

	}

	public void grant() {
		grantingTimes++;
	}

	public int grantingTimes() {
		return this.grantingTimes;

	}

	@Override
	public Document getDocument(String docName, String userID) {
		return docs[searchIndexDoc(docName, userID)];
	}

	// private shit

	private int searchIndexDoc(String documentName, String userID) {
		int result = -1;
		boolean found = false;

		for (int i = 0; i < counterDocs && !found; i++) {

			String indexDocsName = docs[i].getDocName().toUpperCase();
			String givenDocsName = documentName.toUpperCase();
			String indexDocsManager = docs[i].getManager().toUpperCase();
			String givenManager = userID.toUpperCase();

			if (indexDocsName.equals(givenDocsName) && indexDocsManager.equals(givenManager)) {
				found = true;
				result = i;
			}
		}
		return result;
	}

	private boolean isFullDocs() {
		return counterDocs == docs.length;
	}

	private void resizeDocs() {
		Document tmp[] = new Document[2 * docs.length];
		for (int i = 0; i < counterDocs; i++)
			tmp[i] = docs[i];
		docs = tmp;
	}

}
