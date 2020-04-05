package kSecrets;

public enum Clearance {

	OFFICIAL(0), CONFIDENTIAL(1), SECRET(2), TOPSECRET(3);

	private final int value;

	private Clearance(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
