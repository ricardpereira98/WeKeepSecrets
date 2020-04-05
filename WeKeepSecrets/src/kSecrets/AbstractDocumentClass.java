package users;

public abstract class AbstractDocumentClass implements Document {
	private String docName;
	private User manager;
	private String securityLevel;
	private String description;

	protected AbstractDocumentClass(User manager, String docName, String securityLevel, String description) {
		this.docName = docName;
		this.manager = manager;
		this.securityLevel = securityLevel;
	}

	@Override
	public String getDocName() {
		return docName;
	}

	@Override
	public String getSecurityLevel() {

		return securityLevel;
	}

	@Override
	public String getDescription() {

		return description;
	}

	@Override
	public User getManager() {
		return manager;
	}

	@Override
	public abstract User getAccesses();

}
