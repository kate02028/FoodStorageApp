/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package foodstorageapp;

/**
 *
 * @author Power SHOP
 */

public class FoodStorageEmptyException extends RuntimeException {
    public FoodStorageEmptyException(String message) {
        super(message);
    }
}