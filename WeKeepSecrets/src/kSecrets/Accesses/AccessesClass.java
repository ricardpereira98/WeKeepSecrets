package kSecrets.Accesses;

import kSecrets.Users.*;

/**
 * 
 * @author Nuno Costa 54620 && Jose Pereira 55204
 *
 */

public class AccessesClass implements Accesses {

	private User user;

	private String docName;
	private int accessesNum;
	private String readerID;
	private String readerClearanceLvl;
	private String accessType;
	private String docSecLvl;

	public AccessesClass(User user) {
		this.user = user;

	}

	@Override
	public String getUserID() {
		return user.getID();
	}

	// NEW

	public AccessesClass(String docName, int accessesNum, String readerID, String clearanceLvl) {
		this.docName = docName;
		this.accessesNum = accessesNum;
		this.readerID = readerID;
		this.readerClearanceLvl = clearanceLvl;
		// String docName, int accessesNum, String userID, String user clearanceLvl
	}

	public AccessesClass(String docName, String docSecLvl, int accessesNum, String readerID, String readerClearanceLvl,
			String accessType) {

		this.docName = docName;
		this.docSecLvl = docSecLvl;
		this.accessesNum = accessesNum;
		this.readerID = readerID;
		this.readerClearanceLvl = readerClearanceLvl;
		this.accessType = accessType;

		// String docName, String docSecLvl, int accessesNum,
		// String readerID, String readerSecLvl, String readerAccessType
	}

	// doc.name, doc.securityLvl, doc.numAccesses
	// reader.id, reader. secLvl, reader.accessType
	// grants -
	// revokes -

	// doc.grantedTimes, doc. revokedTimes

	/**
	 * The third line presents the grants given and revoked. For each grant, it
	 * should be shown the user id and its security level, with the more recent
	 * actions shown last. Again, the documents should be presented in the order
	 * they where uploaded.
	 * 
	 * 1. secretDoc secret 3 2. alice [secret, read], alice [secret, write], bob
	 * [confidential, read] 3. bob [confidential, grant], john [confidential,
	 * grant], bob [confidential, revoked]
	 */

	@Override
	public String getDocName() {
		return docName;
	}

	@Override
	public int getAccessesNum() {
		return accessesNum;
	}

	@Override
	public String getReaderID() {
		return readerID;
	}

	@Override
	public String getDocSecLvl() {
		return docSecLvl;
	}

	@Override
	public String getReaderClearanceLvl() {
		return readerClearanceLvl;
	}

	@Override
	public String getAccessType() {
		return accessType;
	}

}
