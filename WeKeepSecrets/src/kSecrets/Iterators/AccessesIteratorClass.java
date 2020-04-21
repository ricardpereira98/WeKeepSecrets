package kSecrets.Iterators;

import kSecrets.Accesses.*;

/**
 * 
 * @author Nuno Costa 54620 && Jose Pereira 55204
 *
 */

public class AccessesIteratorClass implements AccessesIterator{

    // Instance variables
    private int counter;
    private int current;
    private Accesses[] access;

    // Creates an iterator of accesses
    public AccessesIteratorClass(Accesses[] access, int counter) {
        this.access = access;
        this.counter = counter;
        current = 0;
    }

    @Override
    public boolean hasNext() {
        return current < counter;
    }

    @Override
    public Accesses next() {
        return access[current++];
    }

}