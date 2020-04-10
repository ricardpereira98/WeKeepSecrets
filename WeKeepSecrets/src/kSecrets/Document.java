package kSecrets;

public interface Document {

	String getDocName();
	String getSecurityLevel();
	String getDescription();
	String getManager();
	void setNewDescription(String newDescription);
	
}
