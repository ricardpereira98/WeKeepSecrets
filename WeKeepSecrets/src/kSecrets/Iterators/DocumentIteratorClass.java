package kSecrets.Iterators;

import kSecrets.Documents.*;

/**
 * 
 * @author Nuno Costa 54620 && Jose Pereira 55204
 *
 */

public class DocumentIteratorClass implements DocumentIterator {

	// Instance variables
	private int counter;
	private int current;
	private Document[] docs;

	// Creates an iterator of documents
	public DocumentIteratorClass(Document[] docs, int counter) {
		this.docs = docs;
		this.counter = counter;
		current = 0;
	}

	@Override
	public boolean hasNext() {
		return current < counter;
	}

	@Override
	public Document next() {
		return docs[current++];
	}

}
