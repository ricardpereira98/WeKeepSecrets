package kSecrets.Documents;

import kSecrets.Accesses.*;
import kSecrets.Iterators.AccessesIterator;
import kSecrets.Iterators.AccessesIteratorClass;


public class ClassifiedDocumentsClass extends AbstractDocumentClass implements ClassifiedDocument{

	// constant defining the original size of the array
	private static final int DEFAULT_VALUE = 20;

	private Accesses[] classifiedHistory;
	private int classifiedHistoryCounter;

	protected ClassifiedDocumentsClass(String docName, String manager, String securityLevel, String description) {
		super(docName, manager, securityLevel, description);

		classifiedHistory = new AccessesClass[DEFAULT_VALUE];
		classifiedHistoryCounter = 0;
	}

	@Override
	public void docHistory(String readerID, String readerClearanceLvl, String accessType, int numAccesses) {
		if (isFullHistory()) {
			resizeHistory();
		}

		classifiedHistory[classifiedHistoryCounter++] = new AccessesClass(readerID, readerClearanceLvl, accessType,
				numAccesses);

	}
	
	@Override
	public AccessesIterator listClassifiedAccessesIterator() {

		return new AccessesIteratorClass(classifiedHistory, classifiedHistoryCounter);
	}

	
	private boolean isFullHistory() {
		return classifiedHistoryCounter == classifiedHistory.length;
	}

	/**
	 * Increases the size of the array of history
	 */
	private void resizeHistory() {
		Accesses tmp[] = new Accesses[2 * classifiedHistory.length];
		for (int i = 0; i < classifiedHistoryCounter; i++)
			tmp[i] = classifiedHistory[i];
		classifiedHistory = tmp;
	}
	

}
