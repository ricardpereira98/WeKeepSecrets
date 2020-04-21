package kSecrets.Iterators;

import kSecrets.Documents.*;

/**
 * 
 * @author Nuno Costa 54620 && Jose Pereira 55204
 *
 */

public interface DocumentIterator {
	/**
	 * 
	 * @return true if there still are documents to be iterated in the array of
	 *         documents
	 */
	public boolean hasNext();

	/**
	 * 
	 * @return the next document in the array of documents
	 */
	public ClassifiedDocument next();

}
