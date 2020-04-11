package kSecrets.Accesses;

import kSecrets.Users.*;

public class AccessesClass implements Accesses {
	
	private User user;
	private String accessType;
	
	public AccessesClass(User user, String accessType) {
		this.user = user;
		this.accessType = accessType;
	}

	@Override
	public String getUserID() {
		return user.getID();
	}
	
	public String getAccessType() { //usar enums
		return null;
	}
	


}
