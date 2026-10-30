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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
 

public final class Validator {
 
    public static final List<String> ALLOWED_FOODS =
            Arrays.asList("Burger", "Pizza", "Fries", "Sandwich", "Hotdog");
 
    public static final double MIN_WEIGHT           = 1.0;      // grams
    public static final double MAX_WEIGHT           = 5000.0;   // grams
    public static final int    MAX_SHELF_LIFE_DAYS  = 14;       // two weeks
 
    // STRICT resolver rejects impossible dates (e.g. 31/02/2026).
    // "uuuu" (proleptic year) is required when using STRICT mode.
    private static final DateTimeFormatter INPUT_FMT =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
 
    private Validator() { }   // utility class - no instances
 
    public static String validateName(String raw) {
        if (raw == null || raw.trim().isEmpty())
            throw new IllegalArgumentException("Food name cannot be empty.");
        String cleaned = raw.trim();
        for (String allowed : ALLOWED_FOODS)
            if (allowed.equalsIgnoreCase(cleaned)) return allowed;   // normalise spelling
        throw new IllegalArgumentException(
            "Unknown food '" + cleaned + "'. Allowed values: " + ALLOWED_FOODS);
    }
 
    public static double validateWeight(String raw) {
        if (raw == null || raw.trim().isEmpty())
            throw new IllegalArgumentException("Weight cannot be empty.");
        double w;
        try {
            w = Double.parseDouble(raw.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Weight must be a number (e.g. 250.5).");
        }
        if (Double.isNaN(w) || Double.isInfinite(w))
            throw new IllegalArgumentException("Weight must be a finite number.");
        if (w < MIN_WEIGHT || w > MAX_WEIGHT)
            throw new IllegalArgumentException(String.format(
                "Weight must be between %.1f g and %.1f g.", MIN_WEIGHT, MAX_WEIGHT));
        return w;
    }
 
    public static LocalDate validateBestBefore(String raw) {
        if (raw == null || raw.trim().isEmpty())
            throw new IllegalArgumentException("Best-before date cannot be empty.");
        LocalDate today = LocalDate.now();
        LocalDate date;
        try {
            date = LocalDate.parse(raw.trim(), INPUT_FMT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date. Use a real date in dd/MM/yyyy (e.g. "
                + today.plusDays(5).format(FoodItem.DATE_FMT) + ").");
        }
        if (date.isBefore(today))
            throw new IllegalArgumentException("Best-before date cannot be in the past.");
        long days = ChronoUnit.DAYS.between(today, date);
        if (days > MAX_SHELF_LIFE_DAYS)
            throw new IllegalArgumentException("Best-before date must be within "
                + MAX_SHELF_LIFE_DAYS + " days (latest "
                + today.plusDays(MAX_SHELF_LIFE_DAYS).format(FoodItem.DATE_FMT) + ").");
        return date;
    }
}
 