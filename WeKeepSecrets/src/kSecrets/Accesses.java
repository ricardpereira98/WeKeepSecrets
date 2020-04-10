package kSecrets;

public interface Accesses {

	void getAcess(Accesses userID);

	boolean hasAcess(String userID);

	void removeAcess(User userID);

	boolean isRevoked(String userID);

	void getAcess(User userID);

	int revokedTimes();

	int grantedTimes();

}
