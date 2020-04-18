package kSecrets.Accesses;

/**
 * 
 * @author Nuno Costa 54620 && Jose Pereira 55204
 *
 */

public interface Accesses {

	String getUserID();

	String getDocName();

	int getAccessesNum();

	String getReaderID();


	String getReaderClearanceLvl();

	String getAccessType();
	
	boolean isReadWriteAccess(Accesses access);

}
