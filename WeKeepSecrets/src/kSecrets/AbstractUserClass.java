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
	public String getID() {
		return id;
	}

	/**
	 * @return
	 */
	public String getKind() {
		return kind.toLowerCase();
	}

	public String getClearanceLevel() {
		return clearanceLevel;
	}

	public abstract void addDoc(String docName, String manager, String securityLevel, String description);

	public abstract boolean hasThisDoc(String docName);
}
