package com.point_of_sales.inventory_service.entity;

public enum Unit {
    PIECE,   // Individual items (e.g., 1 apple, 1 bottle)
    KG,      // Kilograms (e.g., 5kg of rice)
    GRAM,    // Grams (e.g., 500g of cheese)
    LITRE,   // Liters (e.g., 2L of milk)
    MILLILITRE, // Milliliters (e.g., 500ml of juice)
    PACK,    // Packaged items (e.g., 1 pack of biscuits)
    BOX,     // Boxes (e.g., 1 box of chocolates)
    CAN,     // Canned items (e.g., 1 can of soda)
    BOTTLE   // Bottled items (e.g., 1 bottle of water)
}
