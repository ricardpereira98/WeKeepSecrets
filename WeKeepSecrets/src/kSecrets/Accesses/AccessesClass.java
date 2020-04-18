package kSecrets.Accesses;

import kSecrets.Users.*;

/**
 * 
 * @author Nuno Costa 54620 && Jose Pereira 55204
 *
 */

public class AccessesClass implements Accesses {

	private static final String READ = "read";
	private static final String WRITE = "write";

	private User user;

	private String docName;
	private int accessesNum;
	private String readerID;
	private String readerClearanceLvl;
	private String accessType;

	public AccessesClass(User user) {
		this.user = user;
	}

	@Override
	public String getUserID() {
		return user.getID();
	}

	public AccessesClass(String readerID, String readerClearanceLvl, String accessType) {
		this.readerID = readerID;
		this.accessType = accessType;
		this.readerClearanceLvl = readerClearanceLvl;
	}
	
	public AccessesClass(String docName, int accessesNum, String readerClearanceLvl) {
		this.docName = docName;
		this.accessesNum = accessesNum;
		this.readerClearanceLvl = readerClearanceLvl;
	}
	
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
	public String getReaderClearanceLvl() {
		return readerClearanceLvl;
	}

	@Override
	public String getAccessType() {
		return accessType;
	}

	@Override
	public boolean isReadWriteAccess(Accesses access) {
		boolean type;
		
		if(access.getAccessType().equals(READ) || access.getAccessType().equals(WRITE)) {
			type = true;
		}
		else {
			type = false;
		}
		return type;
	}

}
