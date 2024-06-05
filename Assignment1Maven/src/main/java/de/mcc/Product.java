package de.mcc;

import lombok.*;

@ToString
@Setter
@Getter
@AllArgsConstructor
public class Product {
    @NonNull
    String name;
    int price;
    @NonNull
    String category;
    @NonNull
    int amount; // for the products sold by amount
    double weight; // for the products sold by weight
    double storageCostPerUnit;
    boolean specialStorageRequired;
}
