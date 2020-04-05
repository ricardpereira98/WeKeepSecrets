package kSecrets;

public class ClassifiedDocuments extends AbstractDocumentClass{
	private int counter;

	public ClassifiedDocuments(String docName, String manager, String securityLevel, String description) {
		super(docName, manager, securityLevel, description);
		counter = 0;
	}

	@Override
	public User getAccesses() {
		// TODO Auto-generated method stub
		return null;
	}

}
