package utils;

/**
 * Common interface for Observable object.
 * @author Ondřej Vrána
 */
public interface Observable {
    /**
     * Adds {@link Observer} to list of observers.
     * @param observer Observer to add.
     */
    void addObserver(Observer observer);

    /**
     * Removes {@link Observer} from the list of observers.
     * If observer is not in the list, returns false.
     *
     * @param observer Observer to remove.
     * @return True if observer was successfully removed, false otherwise.
     */
    boolean removeObserver(Observer observer);

    /**
     * Calls update method on all {@link Observer Observers} in the list.
     */
    void notifyObservers();
}
