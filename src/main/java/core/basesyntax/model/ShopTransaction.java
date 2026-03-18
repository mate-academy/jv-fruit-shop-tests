package core.basesyntax.model;

import java.util.Objects;

public record ShopTransaction(Operation operation, String product, int quantity) {
    public ShopTransaction {
        Objects.requireNonNull(operation, "operation is null");
        Objects.requireNonNull(product, "product is null");

        if (product.isBlank()) {
            throw new RuntimeException("Product can't be blank");
        }

        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than 0, got: " + quantity);
        }
    }

    public enum Operation {
        BALANCE("b"),
        SUPPLY("s"),
        PURCHASE("p"),
        RETURN("r");

        private final String code;

        Operation(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}
