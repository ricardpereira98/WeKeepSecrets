package kSecrets;

import kSecrets.Iterators.*;

public interface KeepingSecrets {

	boolean hasUserID(String userId);

	boolean isEmpty();

	Iterator listUsers();

	boolean hasDocumentUploaded(String userID, String document);

	boolean isClearanceHighEnough(String userID, String secLvl);

	void uploadDoc(String documentName, String userID, String secLvl, String description);

	boolean isDocOfficial(String documentName, String managerID);

	String getDocSecurityLevel(String documentName, String managerID);

	void updateDescription(String documentName, String managerID, String newDescription);

	String getDescription(String documentName, String managerID);

	boolean isRevoked(String documentName, String managerID, String grantedID);

	void revoke(String documentName, String managerID, String grantedID);

	boolean isClearanceOfficial(String clearanceLevel);

	boolean isClerkUser(String userID);

	void addClerk(String kind, String userID, String clearanceLevel);

	void addOfficer(String kind, String userID, String clearanceLevel);

	boolean hasAccess(String documentName, String managerID, String grantedID);

	void grant(String documentName, String managerID, String grantedID);

}
