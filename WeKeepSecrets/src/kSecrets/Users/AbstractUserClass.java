package kSecrets.Users;

import kSecrets.Documents.*;
import kSecrets.Iterators.DocumentIterator;
import kSecrets.Iterators.DocumentIteratorClass;

/**
 * 
 * @author Nuno Costa 54620 && Jose Pereira 55204
 *
 */

public abstract class AbstractUserClass implements User {

	// constant
	private static final int DEFAULT_SIZE = 10;

	// instance variables
	private String id;
	private String kind;
	private String clearanceLevel;
	private Document[] docs;
	private int counterDocs;
	private int timesGranted;

	// constructor - initializes instance variables
	protected AbstractUserClass(String kind, String id, String clearanceLevel) {
		this.kind = kind;
		this.id = id;
		this.clearanceLevel = clearanceLevel;
		counterDocs = 0;
		docs = new Document[DEFAULT_SIZE];
		timesGranted = 0;
	}

	@Override
	public String getID() {
		return id;
	}

	@Override
	public String getKind() {
		return kind.toLowerCase();
	}

	@Override
	public String getClearanceLevel() {
		return clearanceLevel;
	}

	@Override
	public void addDoc(String docName, String manager, String securityLevel, String description) {
		if (isFullDocs()) {
			resizeDocs();
		}
		docs[counterDocs++] = new DocumentClass(docName, manager, securityLevel, description);

	}

	@Override
	public boolean hasThisDoc(String docName) {
		return searchIndexDoc(docName) >= 0;

	}

	@Override
	public Document getDocument(String docName) {
		return docs[searchIndexDoc(docName)];
	}
	
	@Override
	public DocumentIterator docsIterator() {
		return new DocumentIteratorClass(docs, counterDocs);
	}

	@Override
	public abstract String getType();

	@Override
	public void grantGiven() {
		timesGranted++;
	}

	@Override
	public int getGrantsGiven() {
		return timesGranted;
	}

	// private methods

	/**
	 * 
	 * @param documentName - refers to the name of the Document to be searched for
	 * @return the index of the document with the name given by the parameter
	 */
	private int searchIndexDoc(String documentName) {
		int result = -1;
		boolean found = false;

		for (int i = 0; i < counterDocs && !found; i++) {

			String indexDocsName = docs[i].getDocName().toUpperCase();
			String givenDocsName = documentName.toUpperCase();

			if (indexDocsName.equals(givenDocsName)) {
				found = true;
				result = i;
			}
		}
		return result;
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
}
