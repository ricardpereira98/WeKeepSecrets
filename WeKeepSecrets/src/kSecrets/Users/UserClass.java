package kSecrets.Users;

import kSecrets.Documents.*;
import kSecrets.Iterators.*;

/**
 * 
 * @author Nuno Costa 54620 && Jose Pereira 55204
 *
 */

public class UserClass implements User {

	// constant
	private static final int DEFAULT_SIZE = 10;


	// instance variables
	private String id;
	private String kind;
	private String clearanceLevel;
	private int timesGranted;
	private int timesRevoked;
	
	private Document[] docs;
	private int counterDocs;
	
	private Document[] officialDocs;
	private int counterOfficialDocs;
	
	private Document[] classifiedDocs;
	private int counterClassifiedDocs;

	// constructor - initializes instance variables
	public UserClass(String kind, String id, String clearanceLevel) {
		this.kind = kind;
		this.id = id;
		this.clearanceLevel = clearanceLevel;
		timesGranted = 0;
		
		counterDocs = 0;
		docs = new Document[DEFAULT_SIZE];
		
		officialDocs = new Document[DEFAULT_SIZE];
		counterOfficialDocs = 0;
		
		classifiedDocs = new Document[DEFAULT_SIZE];
		counterClassifiedDocs = 0;
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
	public void addOfficialDoc(String docName, String manager, String securityLevel, String description) {
		if (isFullDocs()) { //criar um novo
			resizeDocs();
		}
		officialDocs[counterOfficialDocs++] = new DocumentClass(docName, manager, securityLevel, description);
	}
	
	@Override
	public void addClassifiedDoc(String docName, String manager, String securityLevel, String description) {
		if (isFullDocs()) { //criar um novo
			resizeDocs();
		}
		classifiedDocs[counterClassifiedDocs++] = new DocumentClass(docName, manager, securityLevel, description);
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
	public Document getDocument(int index) {
		return docs[index];
	}
	
	@Override
	public Document getOfficialDocument(String docName) {
		return officialDocs[searchIndexOfficialDoc(docName)];
	}
	
	@Override
	public Document getClassifiedDocument(String docName) {
		return classifiedDocs[searchIndexClassifiedDoc(docName)];
	}
	
	@Override
	public int getDocsNum() {
		return counterDocs;
	}
	
	
	@Override
	public DocumentIterator docsIterator() {
		return new DocumentIteratorClass(docs, counterDocs);
	}

	@Override
	public String getType() {
		return kind;
	}

	@Override
	public void increaseGrantGiven() {
		timesGranted++;
	}

	@Override
	public int getGrantsGiven() {
		return timesGranted;
	}
	
	@Override
	public int getRevokesGiven() {
		return timesRevoked;
	}
	
	@Override
	public DocumentIterator listOfficialDocsIterator() {
		return new DocumentIteratorClass(officialDocs, counterDocs);
	}
	
	@Override
	public boolean hasOfficialDocs() {
		return counterOfficialDocs>0;
	}
	
	@Override
	public boolean hasClassifiedDocs() {
		return counterClassifiedDocs>0;
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
	
	private int searchIndexOfficialDoc(String documentName) {
		int result = -1;
		boolean found = false;

		for (int i = 0; i < counterOfficialDocs && !found; i++) {

			String indexDocsName = officialDocs[i].getDocName().toUpperCase();
			String givenDocsName = documentName.toUpperCase();

			if (indexDocsName.equals(givenDocsName)) {
				found = true;
				result = i;
			}
		}
		return result;
	}
	
	private int searchIndexClassifiedDoc(String documentName) {
		int result = -1;
		boolean found = false;

		for (int i = 0; i < counterClassifiedDocs && !found; i++) {

			String indexDocsName = classifiedDocs[i].getDocName().toUpperCase();
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

	@Override
	public void increaseRevokesGiven() {
		timesRevoked++;		
	}

	@Override
	public DocumentIterator listClassifiedDocsIterator() {
		return new DocumentIteratorClass(classifiedDocs, counterDocs);
	}
	
}
