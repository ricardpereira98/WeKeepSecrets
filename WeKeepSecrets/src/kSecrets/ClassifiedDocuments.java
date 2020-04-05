package kSecrets;

public class ClassifiedDocuments extends AbstractDocumentClass{
	private int counter;

	public ClassifiedDocuments(User manager, String docName, String securityLevel, String description) {
		super(manager, docName, securityLevel, description);
		counter = 0;
	}

	@Override
	public User getAccesses() {
		// TODO Auto-generated method stub
		return null;
	}

}
