package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import java.util.Objects;

public class ShopHandlerValidator {
    static void validate(Storage storage, String product, Integer quantity) {
        Objects.requireNonNull(storage, "storage is null");
        Objects.requireNonNull(product, "product is null");
        Objects.requireNonNull(quantity, "quantity is null");

        if (product.isBlank()) {
            throw new RuntimeException("Product must contain product name");
        }

        if (quantity < 0) {
            throw new RuntimeException("Quantity must be positive number, got: " + quantity);
        }
    }
}
