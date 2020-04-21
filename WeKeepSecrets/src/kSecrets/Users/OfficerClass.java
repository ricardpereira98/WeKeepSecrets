package kSecrets.Users;


public class OfficerClass extends UserClass implements Officer {
	
	private static final String OFFICER = "OFFICER";

	public OfficerClass(String kind, String id, String clearanceLevel) {
		super(kind, id, clearanceLevel);
		
		
	}

	@Override
	public String getType() {
		return OfficerClass.OFFICER;
	}
	
}
