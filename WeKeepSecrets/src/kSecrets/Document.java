package kSecrets;

public interface Document {

	String getDocName();
	String getSecurityLevel();
	String getDescription();
	User getAccesses();
	User getManager();
	
	
}
