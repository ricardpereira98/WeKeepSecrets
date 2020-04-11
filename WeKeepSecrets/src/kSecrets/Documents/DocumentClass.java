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
	private int counter;
	private int counter_granted;

	public DocumentClass(String docName, String manager, String securityLevel, String description) {
		this.docName = docName;
		this.manager = manager;
		this.securityLevel = securityLevel;
		this.description = description;
		accesses = new AccessesClass[DEFAULT_VALUE];
		counter = 0;
		counter_granted = 0;

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
	public void grant(User grantedUser) {
		
	accesses[counter++] = new AccessesClass()
		counter_granted++;
	}

	@Override
	public boolean hasAccess(User user) {
		return searchIndexGrantedUsers(user) >= 0;
	}

	@Override
	public boolean isRevoked(String userID) {
		return searchIndexGrantedUsers(userID) >= 0;
	}

	private int searchIndexGrantedUsers(User user) {
		int result = -1;
		boolean found = false;
		for (int i = 0; i < counter && !found; i++){
			if (accesses[i].getUserID().toUpperCase().equals(user.getID().toUpperCase())) {
				found = true;
				result = i;
			}
		}
		return result;
	}

	@Override
	public void removeAcess(User userID) {
		revoked[counter_revoked] = userID;
		String user = userID.toString();

		int pos = searchIndexGrantedUsers(user);

		for (int i = pos; i < counter - 1; i++) {
			acesses[i] = acesses[i + 1];
		}
		counter_revoked++;
		counter--;
	}

	@Override
	public int revokedTimes() {
		return this.counter_revoked;
	}

	@Override
	public int grantedTimes() {
		return this.counter_granted;
	}

}
