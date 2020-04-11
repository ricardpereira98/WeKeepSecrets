package kSecrets.Iterators;

import kSecrets.Users.*;

public interface Iterator {

	public void initialize();
	public boolean hasNext();
	public User next();
	
}