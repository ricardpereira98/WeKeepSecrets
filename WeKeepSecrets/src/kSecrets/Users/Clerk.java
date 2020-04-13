package kSecrets.Users;

/**
 * 
 * @author Nuno Costa 54620 && Jose Pereira 55204
 *
 */

public class Clerk extends AbstractUserClass {

	private static final String CLERK = "CLERK";

	public Clerk(String kind, String id, String clearanceLevel) {
		super(kind, id, clearanceLevel);
	}

	@Override
	public String getType() {
		return Clerk.CLERK;
	}


}
