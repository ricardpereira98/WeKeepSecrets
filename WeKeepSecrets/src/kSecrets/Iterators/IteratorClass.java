package kSecrets.Iterators;

import kSecrets.Users.*;

public class IteratorClass implements Iterator{

	
	private int counter;
	private int current;
	private User[] users;
	
	
	public IteratorClass(User[] allUsers, int counter) {
		this.users = allUsers;
		this.counter = counter;
		initialize();
	}
	
	@Override
	public void initialize() {
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
