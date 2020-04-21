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
	private ClassifiedDocument[] docs;

	// Creates an iterator of documents
	public DocumentIteratorClass(ClassifiedDocument[] topLeaked, int counter) {
		this.docs = topLeaked;
		this.counter = counter;
		current = 0;
	}

	@Override
	public boolean hasNext() {
		return current < counter;
	}

	@Override
	public ClassifiedDocument next() {
		return docs[current++];
	}

}
