package kSecrets.Accesses;

import kSecrets.Users.*;

public class AccessesClass implements Accesses {
	
	private User user;
	
	public AccessesClass(User user) {
		this.user = user;

	}

	@Override
	public String getUserID() {
		return user.getID();
	}

	


}
