package kSecrets;

public class Officer extends AbstractUserClass {
	private static final int DEFAULT_SIZE = 500;
	private Document[] documents;
	private int counter;

	public Officer(String kind, String id, String clearanceLevel) {
		super(kind, id, clearanceLevel);
		documents = new ClassifiedDocuments[500];
		counter = 0;

	}

	@Override
	public void addDoc(String docName, String manager, String securityLevel, String description) {
		if (isFull()) {
			resize();
		}
		documents[counter] = new ClassifiedDocuments(docName, manager, securityLevel, description);
		counter++;

	}

	/**
	 * Checks if the documents array is full
	 * 
	 * @return true if the array is full, false otherwise
	 */
	private boolean isFull() {
		return documents.length == counter;
	}

	/**
	 * Increases the capacity of the array of documents
	 */
	private void resize() {
		Document tmp[] = new ClassifiedDocuments[2 * DEFAULT_SIZE];
		for (int i = 0; i < counter; i++) {
			tmp[i] = documents[i];
		}
		documents = tmp;
	}

}
