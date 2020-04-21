package kSecrets.Documents;

import kSecrets.Accesses.*;
import kSecrets.Users.*;

/**
 * 
 * @author Nuno Costa 54620 && Jose Pereira 55204
 *
 */

public abstract class AbstractDocumentClass implements Document {

	// constant defining the original size of the array
	private static final int DEFAULT_VALUE = 20;
	private static final String OFFICIAL = "OFFICIAL";

	// instance variables
	private String docName;
	private String manager;
	private String securityLevel;
	private String description;
	private Accesses[] accesses;
	private Accesses[] revokes;
	private int counter_accesses;
	private int counter_revokes;
	private int numGrants;
	private int numRevokes;
	private int numAccesses;
	private int totalAccesses;

	// constructor - initializes the instance variables
	protected AbstractDocumentClass(String docName, String manager, String securityLevel, String description) {
		this.docName = docName;
		this.manager = manager;
		this.securityLevel = securityLevel;
		this.description = description;
		accesses = new AccessesClass[DEFAULT_VALUE];
		revokes = new AccessesClass[DEFAULT_VALUE];

		counter_accesses = 0;
		counter_revokes = 0;
		numGrants = 0;
		numRevokes = 0;
		numAccesses = 0;
		totalAccesses = 0;

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
	public void setNewDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean hasAccess(User user) {
		return searchIndexGrantedUsers(user) >= 0;
	}

	@Override
	public boolean isRevoked(User user) {
		return searchIndexRevokedUsers(user) >= 0;
	}

	@Override
	public void grant(User user) {
		if (isRevoked(user)) {
			int pos = searchIndexRevokedUsers(user);
			for (int i = pos; i < counter_revokes - 1; i++) {
				revokes[i] = revokes[i + 1];
			}
		}
		accesses[counter_accesses++] = new AccessesClass(user);
		numGrants++;
	}

	@Override
	public void removeAccess(User user) {
		revokes[counter_revokes++] = new AccessesClass(user);

		int pos = searchIndexGrantedUsers(user);

		for (int i = pos; i < counter_accesses - 1; i++) {
			accesses[i] = accesses[i + 1];
		}
		counter_accesses--;
		numRevokes++;
	}

	@Override
	public int revokedTimes() {
		return numRevokes;
	}

	@Override
	public int getActualAccesses() {
		return counter_accesses;
	}

	@Override
	public int grantedTimes() {
		return numGrants;
	}

	@Override
	public void increaseGrantedTimes() {
		numGrants++;
	}

	@Override
	public int getNumAccesses() {
		return numAccesses;
	}

	@Override
	public int getTotalNumAccesses() {
		return totalAccesses;
	}

	@Override
	public void increaseNumAccesses() {
		numAccesses++;
		totalAccesses++;
	}

	@Override
	public void increaseRevokedTimes() {
		numRevokes++;
	}

	@Override
	public boolean isOfficial() {
		return securityLevel.toUpperCase().equals(OFFICIAL);
	}

	@Override
	public void increaseTotalNumAccesses() {
		totalAccesses++;
	}

	@Override
	public abstract void docHistory(String readerID, String readerClearanceLvl, String accessType, int numAccesses);

// Private Methods

	private int searchIndexGrantedUsers(User user) {
		int result = -1;
		boolean found = false;
		for (int i = 0; i < counter_accesses && !found; i++) {
			if (accesses[i].getUserID().toUpperCase().equals(user.getID().toUpperCase())) {
				found = true;
				result = i;
			}
		}
		return result;
	}

	private int searchIndexRevokedUsers(User user) {
		int result = -1;
		boolean found = false;
		for (int i = 0; i < counter_revokes && !found; i++) {
			if (revokes[i].getUserID().toUpperCase().equals(user.getID().toUpperCase())) {
				found = true;
				result = i;
			}
		}
		return result;
	}

}
