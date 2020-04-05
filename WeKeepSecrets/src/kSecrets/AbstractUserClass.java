package kSecrets;

public abstract class AbstractUserClass implements User {
	private String id;
	private String kind;
	private String clearanceLevel;

	public AbstractUserClass(String kind, String id, String clearanceLevel) {
		this.kind = kind;
		this.id = id;
		this.clearanceLevel = clearanceLevel;
	}

	/**
	 * @return the user's identifier
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return
	 */
	public String getKind() {
		return kind;
	}

	public String getClearanceLevel() {
		return clearanceLevel;
	}

	public abstract void addDoc(User manager, String docName, String securityLevel, String description);
}
