package kSecrets.Documents;

import kSecrets.Accesses.*;
import kSecrets.Iterators.*;

public class OfficialDocumentsClass extends AbstractDocumentClass implements OfficialDocument {
	// constant defining the original size of the array
	private static final int DEFAULT_VALUE = 20;

	private Accesses[] officialHistory;
	private int officialHistoryCounter;

	protected OfficialDocumentsClass(String docName, String manager, String securityLevel, String description) {
		super(docName, manager, securityLevel, description);
		officialHistory = new AccessesClass[DEFAULT_VALUE];
		officialHistoryCounter = 0;
	}

	@Override
	public void docHistory(String readerID, String readerClearanceLvl, String accessType, int numAccesses) {
		for (int i = officialHistoryCounter; i > 0; i--) {
			officialHistory[i] = officialHistory[i - 1];
		}
		if (officialHistoryCounter > 10) {
			officialHistoryCounter = 10;
		}
		officialHistory[0] = new AccessesClass(readerID, readerClearanceLvl, accessType, numAccesses);
		officialHistoryCounter++;
	}
	
	@Override
	public AccessesIterator listOfficialAccessesIterator() {
		if (officialHistoryCounter > 10)
			officialHistoryCounter = 10;
		return new AccessesIteratorClass(officialHistory, officialHistoryCounter);
	}

}
