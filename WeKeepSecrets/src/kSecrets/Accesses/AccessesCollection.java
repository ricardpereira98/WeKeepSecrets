package kSecrets.Accesses;

import kSecrets.Users.*;

public interface AccessesCollection {
	
	boolean hasAccess(User user);
	
	void grantAccess(User user);
	
}
