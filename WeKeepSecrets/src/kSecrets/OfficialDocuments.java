package kSecrets;

public class OfficialDocuments extends AbstractDocumentClass {
	private Document[] officialDocs;
	private int counter;

	public OfficialDocuments(String docName, String manager, String securityLevel, String description) {
		super(docName, manager, securityLevel, description);
		counter = 0;

	}

	@Override
	public User getAccesses() {
		// TODO Auto-generated method stub
		return null;
	}

}
