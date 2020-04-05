package kSecrets;

public class OfficialDocuments extends AbstractDocumentClass {
	private Document[] officialDocs;
	private int counter;
	
	public OfficialDocuments(User manager, String docName, String securityLevel, String description) {
		super(manager, docName, securityLevel, description);
		counter = 0;
		
	}

	@Override
	public User getAccesses() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
