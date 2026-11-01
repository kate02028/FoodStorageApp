package foodstorageapp;

/**
 * Thrown when a tray is added but the storage already holds 8 trays.
 * Unchecked (extends RuntimeException), like FoodStorageEmptyException,
 * so add() can simply throw it and the menu catches it.
 *
 * @author Group 124
 */
public class FoodStorageFullException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FoodStorageFullException(String message) {
        super(message);
    }
}
