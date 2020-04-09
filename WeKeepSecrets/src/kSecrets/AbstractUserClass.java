package kSecrets;

public abstract class AbstractUserClass implements User {
	private String id;
	private String kind;
	private String clearanceLevel;
	private int grantingTimes;

	public AbstractUserClass(String kind, String id, String clearanceLevel) {
		this.kind = kind;
		this.id = id;
		this.clearanceLevel = clearanceLevel;
		grantingTimes = 0;
	}

	/**
	 * @return the user's identifier
	 */
	public String getID() {
		return id;
	}

	/**
	 * @return the user's kind
	 */
	public String getKind() {
		return kind.toLowerCase();
	}

	public String getClearanceLevel() {
		return clearanceLevel;
	}

	public abstract void addDoc(String docName, String manager, String securityLevel, String description);

	public abstract boolean hasThisDoc(String docName);
	
	
	public void grant() {
		grantingTimes++;
	}
	
	
	public int grantingTimes() {
		return this.grantingTimes;
		
	}
	
	
}
