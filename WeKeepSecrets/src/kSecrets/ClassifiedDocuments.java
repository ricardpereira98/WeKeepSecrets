package kSecrets;

public class ClassifiedDocuments extends AbstractDocumentClass{
	static final int DEFAULT_SIZE = 50;
	private Accesses[] acesses;
	private int counter;

	public ClassifiedDocuments(String docName, String manager, String securityLevel, String description) {
		super(docName, manager, securityLevel, description);
		acesses = new Accesses[DEFAULT_SIZE];
		counter = 0;
	}


	@Override
	public void getAccess(User user1) {
	}

}
