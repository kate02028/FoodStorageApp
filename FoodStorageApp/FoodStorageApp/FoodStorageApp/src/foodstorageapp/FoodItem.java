/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstorageapp;

/**
 *
 * @author Power SHOP
 */
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


public class FoodItem {

    public static final DateTimeFormatter DATE_FMT  = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter STAMP_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final String        name;
    private final double        weightGrams;
    private final LocalDate     bestBeforeDate;
    private final LocalDateTime timePlaced;   // captured automatically

    public FoodItem(String name, double weightGrams, LocalDate bestBeforeDate) {
        this.name           = name;
        this.weightGrams    = weightGrams;
        this.bestBeforeDate = bestBeforeDate;
        this.timePlaced     = LocalDateTime.now();
    }

    // ---------- Getters (no setters -> immutable) ----------
    public String        getName()           { return name; }
    public double        getWeightGrams()    { return weightGrams; }
    public LocalDate     getBestBeforeDate() { return bestBeforeDate; }
    public LocalDateTime getTimePlaced()     { return timePlaced; }

    /** Days left until best-before date (negative = expired).O(1).
     * @return  */
    public long daysUntilExpiry() {
        return ChronoUnit.DAYS.between(LocalDate.now(), bestBeforeDate);
    }

    /** True if the best-before date has passed.O(1).
     * @return  */
    public boolean isExpired() {
        return daysUntilExpiry() < 0;
    }
    
        /** Status label used in search results.O(1).
     * @return  */
    public String getStatus() {
        long days = daysUntilExpiry();
        if (days < 0)  return "EXPIRED";
        if (days <= 2) return "EXPIRES SOON";
        return "FRESH";
    }
    @Override
    public String toString() {
        return String.format("%-9s | %7.1f g | Best before: %s (%d days left) | Placed: %s",
                name,
                weightGrams,
                bestBeforeDate.format(DATE_FMT),
                daysUntilExpiry(),
                timePlaced.format(STAMP_FMT));
    }
}