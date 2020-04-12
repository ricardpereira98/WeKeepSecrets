package kSecrets.Accesses;

import kSecrets.Users.*;

/**
 * 
 * @author Nuno Costa 54620 && Jose Pereira 55204
 *
 */

public interface AccessesCollection {

	boolean hasAccess(User user);

	void grantAccess(User user);

}
