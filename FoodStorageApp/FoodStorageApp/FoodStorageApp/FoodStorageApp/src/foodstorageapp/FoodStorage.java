/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstorageapp;

/**
 *
 * @author Power SHOP
 */
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FoodStorage {

    public static final int MAX_CAPACITY = 8;

    public enum Side { FRONT, OPPOSITE }

    // ArrayDeque has no capacity limit, so the 8-tray limit is checked in add().
    private final Deque<FoodItem> trays = new ArrayDeque<>(MAX_CAPACITY);

    // ---------- State checks : O(1) ----------
    public int size()        { return trays.size(); }
    public boolean isEmpty() { return trays.isEmpty(); }
    public boolean isFull()  { return trays.size() >= MAX_CAPACITY; }

    public void add(FoodItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Food item cannot be null.");
        }
        if (isFull()) {
            try {
                throw new FoodStorageFullException(
                        "Storage is full (" + MAX_CAPACITY + "/" + MAX_CAPACITY + " trays). Remove an item first.");
            } catch (FoodStorageFullException ex) {
                Logger.getLogger(FoodStorage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        trays.addFirst(item);
    }

    public FoodItem remove(Side side) {
        checkNotEmpty("remove");
        return (side == Side.FRONT) ? trays.removeFirst() : trays.removeLast();
    }

    public FoodItem peek(Side side) {
        checkNotEmpty("peek");
        return (side == Side.FRONT) ? trays.peekFirst() : trays.peekLast();
    }

    /** Linear search, O(n).Matches part of a name, ignoring case.
     * @param name
     * @return  */
    public List<FoodItem> searchByName(String name) {
        checkNotEmpty("search");
        List<FoodItem> found = new ArrayList<>();
        String key = name.trim().toLowerCase();
        for (FoodItem item : trays) {
            if (item.getName().toLowerCase().contains(key)) {
                found.add(item);
            }
        }
        return found;
    }

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

    public String statusReport() {
        String state = isEmpty() ? "EMPTY" : (isFull() ? "FULL" : "IN USE");
        return String.format("Trays used : %d/%d%nFree trays : %d%nState      : %s",
                size(), MAX_CAPACITY, MAX_CAPACITY - size(), state);
    }

    private void checkNotEmpty(String operation) {
        if (isEmpty()) {
            throw new FoodStorageEmptyException("Storage is empty - cannot " + operation + ".");
        }
    }
}
