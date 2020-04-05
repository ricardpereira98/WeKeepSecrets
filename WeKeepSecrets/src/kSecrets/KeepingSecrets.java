package kSecrets;

public interface KeepingSecrets {

	boolean hasId(String userId);

	void addClerk(String kind, String userId, String clearanceLevel);

	void addOfficer(String kind, String userId, String clearanceLevel);

	boolean isEmpty();

	Iterator listUsers();
}
