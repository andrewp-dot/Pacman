package utils;

/**
 * Common interface for Observer.
 * @author Ondřej Vrána
 */
public interface Observer {
    /**
     * Updates given object.
     *
     * @param obj Object to update.
     */
    void update(Object obj);
}
