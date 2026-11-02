package foodstorageapp;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * FoodStorage - the two-sided storage unit, modelled as a Deque (ArrayDeque).
 *
 *   FRONT side    : addFirst / removeFirst  -> Stack mode (LIFO)
 *   OPPOSITE side : removeLast              -> Queue mode (FIFO)
 *
 * Time complexity:
 *   add, remove, peek, size, isEmpty, isFull : O(1)
 *   searchByName, display                    : O(n), n <= 8
 *
 * @author Group 124 - Tsolmon Jargalsaikhan, Tuguldur Azzaya, Khongorzul Oktyabri
 */
public class FoodStorage {

    /** Maximum number of trays the unit can hold. */
    public static final int MAX_CAPACITY = 8;

    /** The two sides of the storage unit. */
    public enum Side { FRONT, OPPOSITE }

    // ArrayDeque has no capacity limit, so the 8-tray limit is checked in add().
    private final Deque<FoodItem> trays = new ArrayDeque<>(MAX_CAPACITY);

    // ---------- State checks : O(1) ----------
    public int size()        { return trays.size(); }
    public boolean isEmpty() { return trays.isEmpty(); }
    public boolean isFull()  { return trays.size() >= MAX_CAPACITY; }

    /**
     * Adds a tray at the FRONT side. O(1).
     *
     * @param item the tray to add
     * @throws FoodStorageFullException if 8 trays are already stored
     */
    public void add(FoodItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Food item cannot be null.");
        }
        if (isFull()) {
            throw new FoodStorageFullException(
                    "Storage is full (" + MAX_CAPACITY + "/" + MAX_CAPACITY + " trays). Remove an item first.");
        }
        trays.addFirst(item);
    }

    /**
     * Removes a tray from the chosen side. O(1).
     * FRONT = newest tray (Stack), OPPOSITE = oldest tray (Queue).
     *
     * @param side the side to remove from
     * @return the removed tray
     * @throws FoodStorageEmptyException if the storage is empty
     */
    public FoodItem remove(Side side) {
        checkNotEmpty("remove");
        return (side == Side.FRONT) ? trays.removeFirst() : trays.removeLast();
    }

    /**
     * Looks at the tray on the chosen side without removing it. O(1).
     *
     * @param side the side to look at
     * @return the tray on that side
     * @throws FoodStorageEmptyException if the storage is empty
     */
    public FoodItem peek(Side side) {
        checkNotEmpty("peek");
        return (side == Side.FRONT) ? trays.peekFirst() : trays.peekLast();
    }

    /**
     * Linear search by full or partial name, ignoring case. O(n).
     *
     * @param name text to search for
     * @return all matching trays (empty list if none or if the text is blank)
     */
    public List<FoodItem> searchByName(String name) {
        checkNotEmpty("search");
        List<FoodItem> found = new ArrayList<>();
        if (name == null || name.trim().isEmpty()) {
            return found;
        }
        String key = name.trim().toLowerCase();
        for (FoodItem item : trays) {          // FRONT -> OPPOSITE
            if (item.getName().toLowerCase().contains(key)) {
                found.add(item);
            }
        }
        return found;
    }

    /**
     * Lists every tray from the FRONT side to the OPPOSITE side. O(n).
     *
     * @return formatted list of trays
     */
    public String display() {
        checkNotEmpty("display");
        StringBuilder sb = new StringBuilder();
        sb.append("========== FRONT SIDE ==========\n");
        int pos = 1;
        for (FoodItem item : trays) {
            sb.append(String.format("[%d] %s%n", pos++, item));
        }
        sb.append("======== OPPOSITE SIDE =========\n");
        sb.append(String.format("Trays used: %d/%d", size(), MAX_CAPACITY));
        return sb.toString();
    }

    /**
     * Short summary of how full the storage is. O(1).
     *
     * @return trays used, free trays and state (EMPTY / IN USE / FULL)
     */
    public String statusReport() {
        String state = isEmpty() ? "EMPTY" : (isFull() ? "FULL" : "IN USE");
        return String.format("Trays used : %d/%d%nFree trays : %d%nState      : %s",
                size(), MAX_CAPACITY, MAX_CAPACITY - size(), state);
    }

    /** Error handling when there is no data in the system. */
    private void checkNotEmpty(String operation) {
        if (isEmpty()) {
            throw new FoodStorageEmptyException("Storage is empty - cannot " + operation + ".");
        }
    }
}
