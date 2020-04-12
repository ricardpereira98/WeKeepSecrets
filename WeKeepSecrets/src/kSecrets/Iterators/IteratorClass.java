package kSecrets.Iterators;

import kSecrets.Users.*;

/**
 * 
 * @author Nuno Costa 54620 && Jose Pereira 55204
 *
 */

public class IteratorClass implements Iterator {

	// Instance variables
	private int counter;
	private int current;
	private User[] users;

	// Creates an iterator of users
	public IteratorClass(User[] allUsers, int counter) {
		this.users = allUsers;
		this.counter = counter;
		current = 0;
	}

	@Override
	public boolean hasNext() {
		return current < counter;
	}

	@Override
	public User next() {
		return users[current++];
	}

}
