package kSecrets.Users;


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
