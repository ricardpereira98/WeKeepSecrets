package kSecrets;

public abstract class AbstractDocumentClass implements Document {
	private String docName;
	private String manager;
	private String securityLevel;
	private String description;

	protected AbstractDocumentClass(String manager, String docName, String securityLevel, String description) {
		this.docName = docName;
		this.manager = manager;
		this.securityLevel = securityLevel;
		this.description = description;
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
	public String getManager() {
		return manager;
	}

	@Override
	public abstract User getAccesses();

}
