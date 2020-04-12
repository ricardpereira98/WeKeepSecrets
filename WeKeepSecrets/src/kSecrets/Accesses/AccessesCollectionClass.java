package kSecrets.Accesses;

import kSecrets.Users.*;

/**
 * 
 * @author Nuno Costa 54620 && Jose Pereira 55204
 *
 */

public class AccessesCollectionClass implements AccessesCollection {

	private static final int DEFAULT_VALUE = 10;
	private Accesses[] accesses;
	private int counter;

	public AccessesCollectionClass() {
		accesses = new Accesses[DEFAULT_VALUE];
		counter = 0;
	}

	@Override
	public boolean hasAccess(User user) {

	}

	@Override
	public void grantAccess(User user) {

	}

	private boolean isFull() {
		return accesses.length == counter;
	}

	private void resize() {
		Accesses[] aux = new Accesses[2 * DEFAULT_VALUE];
		for (int i = 0; i < counter; i++) {
			aux[i] = accesses[i];
		}
		accesses = aux;
	}

}