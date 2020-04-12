package kSecrets.Users;

/**
 * 
 * @author Nuno Costa 54620 && Jose Pereira 55204
 *
 */

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
