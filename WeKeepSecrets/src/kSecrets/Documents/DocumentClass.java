package kSecrets.Documents;

import kSecrets.Accesses.*;
import kSecrets.Users.*;

public class DocumentClass implements Document {
	private static final int DEFAULT_VALUE = 10;
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

	public DocumentClass(String docName, String manager, String securityLevel, String description) {
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

	// ACESSOS

	@Override
	public boolean hasAccess(User user) {
		return searchIndexGrantedUsers(user) >= 0;
	}

	@Override
	public boolean isRevoked(User userID) {
		return searchIndexRevokedUsers(userID) >= 0;
	}

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

	@Override
	public void grant(User grantedUser) {
		if (isRevoked(grantedUser)) {
			int pos = searchIndexRevokedUsers(grantedUser);
			for (int i = pos; i < counter_revokes - 1; i++) {
				revokes[i] = revokes[i + 1];
			}
		}
		accesses[counter_accesses++] = new AccessesClass(grantedUser);
		numGrants++;
	}

	@Override
	public void removeAccess(User userID) {
		revokes[counter_revokes++] = new AccessesClass(userID);

		int pos = searchIndexGrantedUsers(userID);

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
	public int grantedTimes() {
		return numGrants;
	}

}