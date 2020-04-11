package kSecrets.Users;

public class Officer extends AbstractUserClass {

	public static final String OFFICER = "OFFICER";

	public Officer(String kind, String id, String clearanceLevel) {
		super(kind, id, clearanceLevel);
	}

	@Override
	public String getType() {
		return Officer.OFFICER;

	}
}
