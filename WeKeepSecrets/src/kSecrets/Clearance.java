package kSecrets;

/**
 * 
 * @author Nuno Costa 54620 && Jose Pereira 55204
 *
 */

public enum Clearance {
	// constants
	OFFICIAL(0), CONFIDENTIAL(1), SECRET(2), TOPSECRET(3);

	// instance variable
	private final int value;

	// Constructor - initializes the instance variable
	private Clearance(int value) {
		this.value = value;
	}
	/**
	 * 
	 * @return the value from a security level
	 */
	public int getValue() {
		return value;
	}
}
