package kSecrets.Accesses;

import kSecrets.Users.*;

/**
 * 
 * @author Nuno Costa 54620 && Jose Pereira 55204
 *
 */

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
