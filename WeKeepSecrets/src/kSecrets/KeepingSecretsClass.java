package kSecrets;

public class KeepingSecretsClass implements KeepingSecrets {
	private User[] users;
	static final int DEFAULT_SIZE = 50;
	private int counter;

	public KeepingSecretsClass() {
		counter = 0;
		users = new User[DEFAULT_SIZE];
	}

	public boolean hasId(String id) {
		return searchIndex(id) >= 0;
	}

	private int searchIndex(String id) {
		int i = 0;
		int result = -1;
		boolean found = false;
		while (i < counter && !found)
			if (users[i].getId().toUpperCase().equals(id.toUpperCase()))
				found = true;
			else
				i++;
		if (found)
			result = i;
		return result;
	}

	private boolean isFull() {
		return counter == users.length;
	}

	public void addClerk(String kind, String userId, String clearanceLevel) {
		if (isFull()) {
			resize();
		}
		users[counter] = new Clerk(kind, userId, clearanceLevel);
		counter++;
	}

	public void addOfficer(String kind, String userId, String clearanceLevel) {
		if (isFull()) {
			resize();
		}
		users[counter] = new Officer(kind, userId, clearanceLevel);
		counter++;
	}

	private void resize() {
		User tmp[] = new User[2 * users.length];
		for (int i = 0; i < counter; i++)
			tmp[i] = users[i];
		users = tmp;
	}

	public boolean isEmpty() {
		return counter == 0;
	}

	public Iterator listUsers() {
		return new IteratorClass(users, counter);
	}
}
