package kSecrets;

public class Clerk extends AbstractUserClass {
	private static final int DEFAULT_SIZE = 50;

	private int counter;

	public Clerk(String kind, String id, String clearanceLevel) {
		super(kind, id, clearanceLevel);
		docs = new OfficialDocuments[DEFAULT_SIZE];
		counter = 0;
	}

	@Override
	public void addDoc(String docName, String manager, String securityLevel, String description) {
		if (isFull()) {
			resize();
		}
		docs[counter] = new OfficialDocuments(docName, manager, securityLevel, description);
		counter++;

	}

	/**
	 * Checks if the documents array is full
	 * 
	 * @return true if the array is full, false otherwise
	 */
	private boolean isFull() {
		return docs.length == counter;
	}

	/**
	 * Increases the capacity of the array of documents
	 */
	private void resize() {
		Document tmp[] = new OfficialDocuments[2 * DEFAULT_SIZE];
		for (int i = 0; i < counter; i++) {
			tmp[i] = docs[i];
		}
		docs = tmp;
	}

	@Override
	public boolean hasThisDoc(String docName) {
		return searchIndexDocs(docName) >= 0;
	}
	

	private int searchIndexDocs(String docName) {
		int result = -1;
		boolean found = false;

		for (int i = 0; i < counter && !found; i++) {
			if (docs[i].getDocName().toUpperCase().equals(docName.toUpperCase())) {
				found = true;
				result = i;
			}
		}
		return result;
	}

	@Override
	public Document getDocument(String docName) {
		return docs[searchIndexDocs(docName)];
	}

}
