package kSecrets.Iterators;

import kSecrets.Users.*;

/**
 * 
 * @author Nuno Costa 54620 && Jose Pereira 55204
 *
 */

public interface Iterator {
	/**
	 * 
	 * @return true if there still are users to be iterated in the array of users
	 */
	public boolean hasNext();

	/**
	 * 
	 * @return the next user in the array of users
	 */
	public User next();

}
